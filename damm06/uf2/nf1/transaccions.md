# Transaccions

Documentació sobre transaccions:

[ACID control with transactions](https://mariadb.com/kb/en/acid-concurrency-control-with-transactions/)

[Transaction management](https://docs.oracle.com/cd/B19306_01/server.102/b14220/transact.htm)

Per defecte una *Connection* es crea en mode **auto-commit**. Això implica que les sentències *sql* s'executen una a una a mesura que s'envien. Es pot canviar el mode perquè només s'executin en fer **commit** a la transacció,

A partir d'una *Connection* podem usar els següents mètodes:

* **void setAutoCommit(boolean autoCommit)**: Posa el mode auto-commit de la connexió en el mode indicat.
* **boolean getAutoCommit()**: obté el mode actual d'auto-commit de la connexió.
* **void commit()**: realitza tots els canvis efectuats de del darrer commit/rollback permanent i allibera els bloquejos de la base de dades fets per la connexió.
* **void rollback()**: Desfa tots els canvis fets durant l'actual transacció i allibera els bloquejos establerts a la base de dades per la connexió.

## Exemple

Anem a exemplificar l'ús de les transaccions per implementar un sistema de reserves de butaques (per a un cinema, transport, etc.) que admeti concurrència en les sol·licituds.

### La base de dades

Tenim les entitats Reservation i Seat amb relació mxn. Per això ens cal també una taula pivot que les relacioni.

```sql
CREATE USER 'usrseatreservation'@'localhost' IDENTIFIED BY 'pswseatreservation';
CREATE DATABASE dbseatreservation
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
GRANT SELECT, INSERT, UPDATE, DELETE ON dbseatreservation.* TO 'usrseatreservation'@'localhost';
USE dbseatreservation;
CREATE TABLE `seats` (
`id` INT(4) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(40) NOT NULL UNIQUE,
PRIMARY KEY (`id`)
);
CREATE TABLE `reservations` (
`id` INT(4) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(40) NOT NULL,
PRIMARY KEY (`id`)
);
CREATE TABLE `reservationseat` (
`reservation_id` INT(4) NOT NULL,
`seat_id` INT(4) NOT NULL,
PRIMARY KEY (`reservation_id`, `seat_id`)
);
ALTER TABLE reservationseat ADD FOREIGN KEY fk_reservation (reservation_id) REFERENCES reservations(id);
ALTER TABLE reservationseat ADD FOREIGN KEY fk_seat (seat_id) REFERENCES seats(id);
INSERT INTO `seats` (`id`, `name`) 
VALUES
(1, 'name01'),
(2, 'name02'),
(3, 'name03'),
(4, 'name04'),
(5, 'name05'),
(6, 'name06'),
(7, 'name07'),
(8, 'name08');
```

### El model de dades

El model de dades està format per les classes *Reservation* (modelitza una reserva), *Seat* (modelitza una butaca) i la classe de relació entre les dues *ReservationSeat*:

```java
public class Reservation {
    private long id;
    private String name;
//...
}
public class Seat {
    private long id;
    private String name;
//...
}
public class ReservationSeat {
    private long reservationId;
    private long seatId;
//...
}
```

Aquest model permet contemplar el cas més complet de relació mxn entre reserves i butaques.

### Les classes DAO

Cada una d'aquestes classes tindrà una classe *DAO* per executar les consultes a la base de dades.

* *ReservationDao*
* *SeatDao*
* *ReservationSeatDao*

Com que les consultes als diferents *DAO* han de compartir l'objecte ***Connection*** per tal de pertànyer a la mateixa transacció, han de rebre aquest objecte al constructor en comptes de obrir i tancar una connexió independent a cada consulta.

```java
public ReservationDao(Connection connection) throws IOException {
  this.connection = connection;
  this.queries = new Properties();
  this.queries.load(new FileReader(QUERY_FILE));
}
```
També fem servir *Properties* per llegir i emmagatzemar les consultes assossiades a l'entitat.

### La classe o classes de control de les transaccions a la cada d'accés a dades

A més, a la capa d'accés a dades, ens caldrà una classe per iniciar les transaccions i invocar els *DAO* segons els casos.

```java
public class DbSeatsHelper {
  private Logger logger; 
  public DbSeatsHelper() {
      logger = Logger.getLogger("seatreservation");
  }
  public boolean book(String name, List<Seat> seats) {
      boolean success = false;
      Connection conn = null;
      try {
          //get a connection
          conn = DbConnect.getInstance().getConnection();
          //save autocommit initial state
          boolean isAutoCommit = conn.getAutoCommit();
          //disable autocommit
          conn.setAutoCommit(false);
          //save initial point on transaction
          Savepoint savePoint = conn.setSavepoint();
          //insert reservation
          logger.log(Level.INFO,"Creating reservation");
          ReservationDao rDao = new ReservationDao(conn);
          Reservation r = new Reservation(0, name);
          long reservId = rDao.insertAndGetGeneratedKey(r);
          logger.log(Level.INFO, "Reservation id: {0}", reservId);
          //insert seat reservations
          ReservationSeatDao rsDao = new ReservationSeatDao(conn);
          int seatCounter = 0;
          for (Seat seat : seats) {
              logger.log(Level.INFO, "Adding to reservation {0} seat {1}", new Object[]{reservId, seat.getId()});
              ReservationSeat rs = new ReservationSeat(reservId, seat.getId());
              logger.log(Level.INFO, "ReservationSeat: {0}", rs.toString());
              seatCounter += rsDao.insert(rs);
          }
          System.out.format("Seat counter: "+seatCounter);
          //perform commit
          conn.commit();
          logger.info("commit performed");
          //restore initial autocommit state
          conn.setAutoCommit(isAutoCommit);
      } catch (IOException ex) {
          logger.log(Level.SEVERE, null, ex);
      } catch (SQLException ex) {
          logger.log(Level.SEVERE, null, ex);
          try {
              if (conn != null) {
                  logger.info("rollback performed");
                  conn.rollback();
              }

          } catch (SQLException e) {
              logger.log(Level.SEVERE, null, ex);
          }
      }
      return success;
  }
}
```
### La classe principal i el Logger per al registre d'errors

Per poder anar veient el progrés de la transacció, hi ha un logger que va informant. Un cop passat a producció, aquest codi ja no hauria d'emetre aquests missatges. 

Això es pot fer modificant el nivell del logger.

Per últim, es mostra la classe principal amb el mètode main(), el qual defineix les característiques del logger i realitza una reserva de prova.

```java
public class Main {

    public static void main(String[] args) {
        configLogger("files/log");
        DbSeatsHelper dbHelper = new DbSeatsHelper();
        String name = "Peter";
        List<Seat> seats = Arrays.asList(new Seat(1), new Seat(3), new Seat(1));
        dbHelper.book(name, seats);
    }

    private static Logger configLogger(String logfile) {
        //create and configure (if necessary) the message logger.
        Logger logger = Logger.getLogger("seatreservation");
        //set to false to avoid inheritance of parent handlers.
        logger.setUseParentHandlers(false);
        try {
            FileHandler handler;
            int limit = 1000000;
            int maxFiles = 3;
            handler = new FileHandler(logfile, limit, maxFiles, true);
            handler.setLevel(Level.ALL);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (FileNotFoundException ex) {
            logger.setUseParentHandlers(true);
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logger;
    }

}
```

Fent diferents proves amb el codi anterior podem veure com la reserves es comporten de forma atòmica. O bé es realitzen totalment, o bé no es fa cap canvi a la base de dades si alguna de les consultes no té èxit.

### Obtenció de les claus generades en les consultes

Per associar les butaques amb la reserva, ens cal l'id d'aquesta. Com que és autoincremental, a la consulta insert no passem aquest valor, ja que el genera la base de dades.

Podem obtenir de la pròpia consulta el conjunt de claus generades, en aquest cas, només es tractarà de la clau primària de la reserva introduïda a la taula de reserves.

En crear la consulta, afegim un paràmetre al mètode *prepareStatement()*:
```java
PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
```
Més endavant, un cop executada la consulta, recuperem les claus:
```java
ResultSet rs = st.getGeneratedKeys();
if (rs.next()) {
  pk = rs.getLong(1);
}
```


