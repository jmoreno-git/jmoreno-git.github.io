# Entitats amb relació mxn

## Desenvolupament Flight manager: Enunciat

Una empresa japonesa ens ha demanat que desenvolupem un sistema per gestionar els seus vols virtuals. Com que no es pot viatjar i hi ha gent que ho troba faltar han reproduït una cabina d'avió i han implementat tot un sistema de realitat virtual per simular un viatge.

<https://cnnespanol.cnn.com/video/simulacion-de-vuelo-realidad-virtual-avion-encuentro-propuesta-first-airlines-tokio-japon-tripulacion-guillermo-arduino-clix-cnne/>

El que necessita gestionar aquesta empresa és el següent.

**Passatgers**: Un passatger es caracteritza per un nom, un telèfon (únic) i un booleà per indicar si és menor d'edat.

**Vols**: Un vol es caracteritza per un codi de vol (únic), una capacitat, una data i una hora de sortida del vol.

Les funcionalitats que cal implementar son les següents:

  - Alta, baixa i modificació de passatgers.
  - Alta, baixa i modificació de vols.
  - Registrar un passatger en un vol fins al màxim de capacitat.
  - Eliminar un passatger d'un vol. Aquesta operació no es podrà fer efectiva si queda menys d'una hora per iniciar el vol.
  - Llistar tots els vols.
  - Llistar tots els passatgers.
  - Llistar els passatgers d'un vol.

Evidentment, cap passatger es pot registrar dos cops en un mateix vol.
Cal mantenir la integritat referencial entre vols i passatgers.

## Arquitectura MVC

L'arquitectura MVC ja ha estat explicada amb anterioritat. El punt de
partida seria, doncs, el següent, on només es presenten alguns mètodes a
títol d'exemple (cal completar la resta de funcionalitats):

**Classe principal**

``` java
/**
 * Main class for Flights application.
 * @author ProvenSoft
 */
public class Main {

    public static void main(String[] args) {
        try {
            Model model = new Model();
            MainController controller = new MainController(model);
            controller.start();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
```

D'acord amb l'arquitectura *MVC*, cal instanciar primer el model, després el controlador i passar-li el model.

La vista s'instancia normalment al controlador, tot i que també es pot instanciar al principal i connectar-la amb el controlador.

**Model**

``` java
/**
 * Model service for flights application.
 * @author ProvenSoft
 */
public class Model {
    /**
     * DAO object for Flight entity.
     */
    private final FlightDao flightDao;
    /**
     * DAO object for Passenger entity.
     */
    private final PassengerDao passengerDao;

    public Model() throws ClassNotFoundException {
        this.flightDao = new FlightDao();
        this.passengerDao = new PassengerDao();
    }
    
    /**
     * searches all flights in database.
     * @return list of all flights or null in case or error.
     */
    public List<Flight> searchAllFlights() {
        List<Flight> result = null;
        result = flightDao.selectAll();
        return result;
    }
    
    /**
     * adds a flight to database, prevents code duplicates.
     * @param flight the flight to add.
     * @return 1 if succesfull, 0 if not, -1 in case of error.
     */
    public int addFlight(Flight flight) {
        int result = 0;
        result = flightDao.insert(flight);
        return result;
    }
    
    /**
     * searches all passengers in database.
     * @return list of all flights or null in case or error.
     */
    public List<Passenger> searchAllPassengers() {
        List<Passenger> result = null;
        result = passengerDao.selectAll();
        return result;
    }
    
}
```

El model és el servei de dades del controlador. Quan es requereixen dades, les proveeix algun mètode del model.

**Controlador**

``` java
/**
 * Main controller for flights application.
 * @author ProvenSoft
 */
public class MainController {
    
    private final Model model;
    private final MainView view;

    public MainController(Model model) {
        this.model = model;
        this.view = new MainView(this);
    }

    public Model getModel() {
        return model;
    }
    
    /**
     * starts running controller.
     */
    public void start() {
        view.show();
    }
    
    /**
     * processes actions received from view.
     * @param action the action to process.
     */
    public void processAction(String action) {
        if (action != null) {
            switch (action) {
                case "exit": //exit application.
                    exitApplication();
                    break;
                case "listallflights": //list all flights.
                    doListAllFlights();
                    break;
                default:
                    view.showMessage("Unknown option");
                    break;
            }
        }
    }

    /**
     * exits application.
     */
    private void exitApplication() {
        String answer = view.inputString("Exit. Are you sure (Y/N): ");
        if (answer != null) {
            if (answer.equalsIgnoreCase("y")) {
              view.close();
            }
        }
    }

    /**
     * lists all flights from the database.
     */
    private void doListAllFlights() {
        //retrieve data from model.
        List<Flight> result = model.searchAllFlights();
        //display data to user.
        if (result != null) { //successfull retrieval of data.
            view.showFlightList(result);
        } else { //error retrieving data.
            view.showMessage("Error retrieving data");
        }
    }
    
}
```

El controlador rep les accions de la vista i actua sobre el model per canviar dades o recuperar-ne, segons sigui el cas.

**Vista**

``` java
/**
 * Main view for flights application.
 * @author ProvenSoft
 */
public class MainView {
    
    private final MainController controller;
    private boolean exit; //flag to exit application.
    private final MainMenu mainMenu;

    public MainView(MainController controller) {
        this.controller = controller;
        this.mainMenu = new MainMenu();
    }
    
    /**
     * makes the view visible and starts interacting with user.
     */
    public void show() {
        exit = false;
        // control loop for user interaction.
        do {
            mainMenu.show();
            String action = mainMenu.getSelectedOptionActionCommand();
            if (action != null) {
                controller.processAction(action);
            }
        } while (!exit);
    }

    /**
     * displays a message to user.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * displays a message and gets user's answer.
     * @param message the message to display.
     * @return the user's answer or null in case of error.
     */
    public String inputString(String message) {
        System.out.print(message);
        Scanner scan = new Scanner(System.in);
        return scan.next();   
    }
    
    /**
     * activates view closing.
     */
    public void close() {
        this.exit = true;
    }
    
    /**
     * displays a list.
     * @param data the list to display.
     */
    public void showFlightList(List<Flight> data) {
        if (data != null) {
            for (Flight elem: data) {
                System.out.println(elem.toString());
            } 
            System.out.format("%d elements displayed\n", data.size());
        } else {
            showMessage("Null data");
        }
    }
    
}
```

La vista conté la interfície gràfica amb la qual interactua l'usuari. En aquest cas, per simplificar, es tracta d'una interfície de text per consola.

**Menú**

``` java
/**
 * Main menu for flights application.
 * @author ProvenSoft
 */
public class MainMenu extends Menu {

    public MainMenu() {
        super("Flight application main menu");
        //
        addOption( new Option("Exit application", "exit") );
        //
        addOption( new Option("List all flights", "listallflights") );
        addOption( new Option("Add flight", "addflight") );
        addOption( new Option("Modify flight", "modifyflight") );
        addOption( new Option("Remove flight", "removeflight") );
        //
        addOption( new Option("List all passengers", "listallpassengers") );
        addOption( new Option("Add passenger", "addpassenger") );
        addOption( new Option("Modify passenger", "modifypassenger") );
        addOption( new Option("Remove passenger", "removepassenger") );        
        //
        addOption( new Option("List passengers by flight", "listpassengersbyflight") );
        addOption( new Option("Register passenger to flight", "registerpassengertoflight") ); 
        addOption( new Option("Unregister passenger from flight", "unregisterpassengerfromflight") ); 
    }
    
}
```

La classe *MainMenu* defineix el menú d'opcions de l'aplicació. Utilitza les classes auxiliars *Menu* i *Option*.

Classes auxiliars *Menu* i *Option*. ![Classes auxiliars Menu i Option](/docencia/dam/m03/uf6/flightsmanager/menuoption.zip)

## Base de dades

``` sql
CREATE USER 'flightssusr'@'localhost' IDENTIFIED BY 'flightspwd';
CREATE DATABASE flightsdb
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
GRANT SELECT, INSERT, UPDATE, DELETE ON flightsdb.* TO 'flightssusr'@'localhost';
USE flightsdb;
CREATE TABLE `flights` (
`id` INT(4) NOT NULL AUTO_INCREMENT,
`code` VARCHAR(10) NOT NULL UNIQUE,
`capacity` INT(2) DEFAULT 0,
`date` DATE NOT NULL,
`time` TIME NOT NULL,
PRIMARY KEY (`id`)
);
CREATE TABLE `passengers` (
`id` INT(4) NOT NULL AUTO_INCREMENT,
`phone` VARCHAR(15) NOT NULL UNIQUE,
`minor` BOOLEAN DEFAULT true,
PRIMARY KEY (`id`)
);
CREATE TABLE `flightspassengers` (
`flight_id` INT(4) NOT NULL,
`passenger_id` INT(4) NOT NULL,
PRIMARY KEY (`flight_id`, `passenger_id`)
);
ALTER TABLE flightspassengers ADD FOREIGN KEY fk_flight (flight_id) REFERENCES flights(id);
ALTER TABLE flightspassengers ADD FOREIGN KEY fk_passenger (passenger_id) REFERENCES passengers(id);
```

La relació entre passatgers i vols és mxn. Per això hi ha una taula de connexió entre les dues entitats amb les respectives claus primàries actuant com a foranes.

## Capa de persistència de dades

La classe ***DbConnect*** encapsula les dades per a la connexió a la base de dades. Té aplicat el patró ***Singleton*** perquè només existeixi una instancia de la classe.

La classe s'encarrega també de carregar el *driver* de la base de dades. Si no es troba el *driver*, es llança ***ClassNotFoundException***.

``` java
/**
 * encapsulates data for database connection.
 *
 * @author ProvenSoft
 */
public final class DbConnect {
    
    private static DbConnect instance;

    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String PROTOCOL = "jdbc:mysql:";
    static final String HOST = "127.0.0.1";
    static final String BD_NAME = "flightsdb";
    static final String USER = "flightssusr";
    static final String PASSWORD = "flightspwd";
    static final String PARAMS = "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private DbConnect() throws ClassNotFoundException {
        loadDriver();
    }

    public static DbConnect getInstance() throws ClassNotFoundException {
        if (instance == null) {
            instance = new DbConnect();
        }
        return instance;
    }
    
    public void loadDriver() throws ClassNotFoundException {
        Class.forName(DRIVER);
    }

    /**
     * gets and returns a connection to database
     *
     * @return connection
     * @throws java.sql.SQLException
     */
    public Connection getConnection() throws SQLException {
        final String BD_URL = String.format("%s//%s/%s?%s", PROTOCOL, HOST, BD_NAME, PARAMS);
        Connection conn = null;
        conn = DriverManager.getConnection(BD_URL, USER, PASSWORD);
        return conn;
    }
}
```

### Classes DAO

Les classes **DAO** (**Data Access Object**) encapsulen les consultes a la base de dades i la conversió entre les dades de la base de dades i els objectes del model.

En aquest exemple, les consultes *SQL* es defineixen i es desen en un **Map**.
Només es mostren alguns mètodes a títol d'exemple.

``` java
/**
 * DAO for passenger entity.
 * @author ProvenSoft
 */
public class PassengerDao {

    protected final Map<String, String> queries;
    protected final DbConnect dbConnect;
    private final String TABLE_NAME;

    public PassengerDao() throws ClassNotFoundException {
        this.TABLE_NAME = "passengers";
        this.dbConnect = DbConnect.getInstance();
        this.queries = new HashMap<>();
        initQueries();
    }

    /**
     * converts resultset entry to entity object.
     * @param rs resultset to get data from.
     * @return object with data in current position of resultset.
     */
    private Passenger fromResultSet(ResultSet rs) throws SQLException {
        Passenger p = null;
        long id = rs.getLong("id");
        String phone = rs.getString("phone");
        boolean minor = rs.getBoolean("minor");
        p = new Passenger(id, phone, minor);
        return p;
    }

    /**
     * selects all entities from database.
     * @return list of entities of null in case of error.
     */
    public List<Passenger> selectAll() {
        List<Passenger> result = new ArrayList<>();
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                String query = queries.get("sAll");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    Passenger obj = fromResultSet(rs);
                    if (obj != null) {
                        result.add(obj);
                    }
                }
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
            result = null;
        }
        return result;
    }
    
    /**
     * initiatizes queries to database.
     */
    private void initQueries() {
        queries.put("sAll", String.format("select * from %s", TABLE_NAME));
        queries.put("insert", String.format("insert into %s values (null, ?, ?)", TABLE_NAME));
    }
    
}
```

Cal escriure una classe *DAO* per a cada entitat que ha de fer-se persistent a la base de dades.

La pràctica consisteix en completar l'aplicació implementant totes les funcionalitats requerides i realitzant les proves pertinents que n'assegurin el correcte funcionament en totes les circumstàncies.

Cal definir l'estratègia de tractament d'errors: codis d'error, llançament d'excepcions, ..., de manera que es pugui informar a l'usuari amb prou detall del resultat de les accions que s'han realitzat.

### Relació entre vols i passatgers

Per implementar la relació mxn entre vols i passatgers crearem una classe amb les claus foranes que mapegen la taula de la relació.

``` java
/**
 * Transport persistence class for flight-passenger mxn relationship.
 * @author ProvenSoft
 */
public class FlightPassenger {
    private long flightId;
    private long passengerId;

    public FlightPassenger(long flightId, long passengerId) {
        this.flightId = flightId;
        this.passengerId = passengerId;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(long passengerId) {
        this.passengerId = passengerId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (int) (this.flightId ^ (this.flightId >>> 32));
        hash = 37 * hash + (int) (this.passengerId ^ (this.passengerId >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FlightPassenger other = (FlightPassenger) obj;
        if (this.flightId != other.flightId) {
            return false;
        }
        if (this.passengerId != other.passengerId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FlightPassenger{flightId=").append(flightId);
        sb.append(", passengerId=").append(passengerId);
        sb.append('}');
        return sb.toString();
    }
    
}
```

I per a la persistència de la relació, crearem la classe *FlightPassengerDao*.

``` java
/**
 * DAO for flight-passsenger relationship persistence.
 * @author ProvenSoft
 */
public class FlightPassengerDao {
    protected final Map<String, String> queries;
    protected final DbConnect dbConnect;
    private final String TABLE_NAME;

    public FlightPassengerDao() throws ClassNotFoundException {
        this.TABLE_NAME = "flightpassenger";
        this.dbConnect = DbConnect.getInstance();
        this.queries = new HashMap<>();
        initQueries();
    }    

    /**
     * converts resultset entry to entity object.
     * @param rs resultset to get data from.
     * @return object with data in current position of resultset.
     */
    private FlightPassenger fromResultSet(ResultSet rs) throws SQLException {
        FlightPassenger p = null;
        long flightId = rs.getLong("flight_id");
        long passengerId = rs.getLong("passenger_id");
        p = new FlightPassenger(flightId, passengerId);
        return p;
    }
    
    /**
     * selects all entities from database.
     * @return list of entities of null in case of error.
     */
    public List<FlightPassenger> selectAll() {
        List<FlightPassenger> result = new ArrayList<>();
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                String query = queries.get("sAll");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    FlightPassenger obj = fromResultSet(rs);
                    if (obj != null) {
                        result.add(obj);
                    }
                }
            }
        } catch (SQLException ex) {
            result = null;
        }
        return result;
    }
     
    /**
     * inserts a flightpassenger in database.
     * @param flightpassenger the flightpassenger to insert.
     * @return number of rows affected.
     */
    public int insert(FlightPassenger flightpassenger) {
        int result = 0;
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                String query = queries.get("insert");
                PreparedStatement st = conn.prepareStatement(query);
                st.setLong(1, flightpassenger.getFlightId());
                st.setLong(2, flightpassenger.getPassengerId());
                result = st.executeUpdate();
            }
        } catch (SQLException ex) {
            result = -1;
        }        
        return result;
    }
    
    public List<FlightPassenger> selectWhereFlightId(long id) {
        List<FlightPassenger> result = new ArrayList<>();
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                String query = queries.get("sWhereFlightId");
                PreparedStatement st = conn.prepareStatement(query);
                st.setLong(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    FlightPassenger obj = fromResultSet(rs);
                    if (obj != null) {
                        result.add(obj);
                    }
                }
            }
        } catch (SQLException ex) {
            result = null;
        }
        return result;
    }

    public List<FlightPassenger> selectWherePassengerId(long id) {
        List<FlightPassenger> result = new ArrayList<>();
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                String query = queries.get("sWherePassengerId");
                PreparedStatement st = conn.prepareStatement(query);
                st.setLong(1, id);
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    FlightPassenger obj = fromResultSet(rs);
                    if (obj != null) {
                        result.add(obj);
                    }
                }
            }
        } catch (SQLException ex) {
            result = null;
        }
        return result;
    }
    
    private void initQueries() {
        queries.put("sAll", String.format("select * from %s", TABLE_NAME));
        queries.put("insert", String.format("insert into %s values (?, ?)", TABLE_NAME));
        queries.put("sWhereFlightId", String.format("select * from %s where flight_id = ?", TABLE_NAME));
        queries.put("sWherePassengerId", String.format("select * from %s where passenger_id = ?", TABLE_NAME));
    }
}
```

Per fer les consultes creuades, crearem al Model mètodes que utilizin les classes anteriors.

``` java
    /**
     * search passengers in given flight.
     * @param flight the flight to search passengers in.
     * @return list of passengers or null in case of error.
     */
    public List<Passenger> searchPassengersByFlight(Flight flight) {
        List<Passenger> result = new ArrayList<>();
        List<FlightPassenger> flightpassengerList = flightpassengerDao.selectWhereFlightId(flight.getId());
        if (flightpassengerList != null) {
            for (FlightPassenger fp: flightpassengerList) {
                Passenger p = passengerDao.selectWhereId(fp.getPassengerId());
                if (p != null) {
                    result.add(p);
                }
            }            
        }
        return result;
    }
```

Exercici: A partir del codi mostrat, cal completar les consultes que falten.
