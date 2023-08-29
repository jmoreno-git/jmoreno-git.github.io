# Connexió a bases dades. Patró DAO

Anem a il·lustrar com construir una capa d'accés a dades aplicant el patró [DAO](https://en.wikipedia.org/wiki/Data_access_object).

![](/damm06/assets/2.1/refactor-area51.jpg)

Segons aquest patró, cal una classe DAO per a cada entitat del nostre model de dades, així com també per a les relacions mxn.

L'exemple proporciona persistència en base de dades per a objectes **Subscriber**.

```java
package subscribermanager.model;

import java.util.Objects;

/**
 *
 * @author ProvenSoft
 */
public class Subscriber {
    
    private long   id;
    private String name;
    private String address;
    private String phone;
    private int    age;

    public Subscriber() {
    }

    public Subscriber(String name, String address, String phone, int age) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.age = age;
    }

    public Subscriber(long id, String name, String address, String phone, int age) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.age = age;
    }

    public Subscriber(long id) {
        this.id = id;
    }
    
    public Subscriber(Subscriber other) {
        this.id = other.id;
        this.name = other.name;
        this.address = other.address;
        this.phone = other.phone;
        this.age = other.age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.phone);
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
        final Subscriber other = (Subscriber) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Subscriber{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", address=").append(address);
        sb.append(", phone=").append(phone);
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
    
}
```

Les dades de connexió a la base de dades es llegeixen d'un fitxer de propietats. D'aquesta manera es poder modificar sense recompilar l'aplicació. 

**files/dbconn.properties**
```text
driver=com.mysql.cj.jdbc.Driver
protocol=jdbc:mysql:
host=127.0.0.1
dbname=dbleague
dbparams=useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
user=usrleague
password=pswleague
```

El codi sql per generar la base de dades, l'usuari d'accés per a l'aplicació, la taula i les dades és el següent:

**files/dbsubscriber.sql**
```sql
CREATE USER 'usrsubscribers'@'localhost' IDENTIFIED BY 'pswsubscribers';
CREATE DATABASE dbsubscribers
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
GRANT SELECT, INSERT, UPDATE, DELETE ON dbsubscribers.* TO 'usrsubscribers'@'localhost';
USE dbsubscribers;
CREATE TABLE `subscribers` (
`id` INT(4) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(40) NOT NULL,
`address` VARCHAR(40) DEFAULT NULL,
`phone` VARCHAR(40) UNIQUE DEFAULT NULL,
`age` INT DEFAULT 0,
PRIMARY KEY (`id`)
) ENGINE=InnoDB;
INSERT INTO `subscribers` (`id`, `name`, `address`, `phone`, `age`) 
VALUES
(1, 'name01', 'add01', 'phone01', 11),
(2, 'name02', 'add02', 'phone02', 12),
(3, 'name03', 'add03', 'phone03', 13),
(4, 'name04', 'add04', 'phone04', 14),
(5, 'name05', 'add05', 'phone05', 15),
(6, 'name06', 'add06', 'phone06', 16),
(7, 'name07', 'add07', 'phone07', 17),
(8, 'name08', 'add08', 'phone08', 18);
```

Per tal d'encapsular les dades de connexió a la base de dades dintre del nostre codi i disposar d'una classe que generi connexions, creem la següent classe:

```java
package subscribermanager.model.persistence;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Generic class to encapsulate properties of database connection.
 *
 * @author ProvenSoft
 */
public class DbConnect {

    /**
     * the path to properties file with database connection data.
     */
    private String propsFile;
    /**
     * database url
     */
    private String dbUrl;
    /**
     * database user name
     */
    private String user;
    /**
     * database user password
     */
    private String password;

    public DbConnect() {
    }

    public String getPropsFile() {
        return propsFile;
    }

    /**
     *
     * @param filename the path to file with connection properties
     * @throws FileNotFoundException if filename not found
     * @throws IOException in case of error reading file
     */
    public void setPropsFile(String filename)
            throws FileNotFoundException, IOException {
        propsFile = filename;
        Properties connProps = new Properties();
        connProps.load(new FileReader(propsFile));
        dbUrl = String.format("%s//%s/%s?%s",
                connProps.getProperty("protocol"),
                connProps.getProperty("host"),
                connProps.getProperty("dbname"),
                connProps.getProperty("dbparams"));
        user = connProps.getProperty("user");
        password = connProps.getProperty("password");
    }

    /**
     * gets a connection from database
     *
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, user, password);
    }

}
```

Les dades de connexió es llegeixen des del fitxer de propietats a través del mètode setPropsFile(), el qual rep com a paràmetre la ruta al fitxer.

La classe *SubscriberDao* és la responsable d'executar les consultes a la base de dades i generar objectes *Subscriber* amb el resultat.

```java
package subscribermanager.model.persistence;

import subscribermanager.model.Subscriber;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
//import java.util.Map;
//import java.util.HashMap;

public class SubscriberDao {
    /**
     * queries (can be defined using properties or map)
     */
    //private final Map<String, String> queries;
    private final Properties queries;
    /**
     * connect class to database
     */
    private final DbConnect dbConnect;
    
    public SubscriberDao(DbConnect dbConnect) throws ClassNotFoundException {
        this.dbConnect = dbConnect;
        //this.queries = new HashMap<>();
        this.queries = new Properties();
        initQueries();
    }
    
    /**
     * gets a query given its name
     * @param queryName the name of the query
     * @return query or empty string
     */
    private String getQuery(String queryName) {
        //return queries.getOrDefault(queryName, "");  //with map
        return queries.getProperty(queryName, "");  //with properties
    }
    
    /**
     * initializes all queries
     */
    private void initQueries() {
        //using properties
        queries.setProperty("findAll", "select * from subscribers");
        queries.setProperty("findById", "select * from subscribers where id = ?");
        queries.setProperty("findLikeName", "select * from subscribers where name LIKE ?");
        queries.setProperty("findByPhone", "select * from subscribers where phone = ?");
        queries.setProperty("findByAge", "select * from subscribers where age = ?");
        queries.setProperty("findById", "select * from subscribers where id = ?");
        queries.setProperty("insert", "insert into subscribers values (null, ?, ?, ?, ?)");
        queries.setProperty("update", "update subscribers set name = ?, address = ?, phone = ?, age = ? where id = ?");
        queries.setProperty("delete", "delete from subscribers where id = ?");
/*        //using map        
        queries.put("findAll", "select * from subscribers");
        queries.put("findById", "select * from subscribers where id = ?");
        queries.put("findLikeName", "select * from subscribers where name LIKE ?");
        queries.put("findByPhone", "select * from subscribers where phone = ?");
        queries.put("findByAge", "select * from subscribers where age = ?");
        queries.put("findById", "select * from subscribers where id = ?");
        queries.put("insert", "insert into subscribers values (null, ?, ?, ?, ?)");
        queries.put("update", "update subscribers set name = ?, address = ?, phone = ?, age = ? where id = ?");
        queries.put("delete", "delete from subscribers where id = ?");  
*/
    }
    
    /**
     * gets subscriber data from a resultset
     * @param resulSet the resultset to get data from
     * @return subscriber object
     * @throws SQLException in case of error
     */
    private Subscriber resultsetToSubscriber(ResultSet resulSet) throws SQLException {
        Subscriber subs = null;
        long id = resulSet.getLong("id");
        String name = resulSet.getString("name");
        String address = resulSet.getString("address");
        String phone = resulSet.getString("phone");
        int age = resulSet.getInt("age");
        subs = new Subscriber(id, name, address, phone, age);
        return subs;
    }
    /**
     * gets a subscriber given its id
     * @param id the id to search
     * @return the subscriber found or null if not found
     * @throws SQLException in case of SQL error
     */       
    public Subscriber findById(long id) throws SQLException {
        Subscriber subs=null;       
        try ( Connection conn=dbConnect.getConnection() ) {
            if ( conn != null ) {
                String query = getQuery("findById");
                PreparedStatement st = conn.prepareStatement(query);
                st.setLong(1, id);
                ResultSet rs = st.executeQuery();
                if ( rs.next() ) {
                    subs = resultsetToSubscriber(rs);
                }
            }
        } 
        return subs;
    }

    /**
     * gets all subscribers
     * @return a list with all subscribers found
     * @throws SQLException in case of SQL error 
     */
    public List<Subscriber> findAll() throws SQLException {
        List<Subscriber> subscribers = new ArrayList<>();
        try ( Connection conn=dbConnect.getConnection() ) {
            if ( conn != null ) {
                String query = getQuery("findAll");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while ( rs.next() ) {
                    Subscriber s = resultsetToSubscriber(rs);
                    subscribers.add(s);
                }
            }
        } 
        return subscribers;
    }
    
    /**
     * gets all subscribers with name containing given substring
     * @param name the substring to search
     * @return a list with all subscribers that match given cryteria
     * @throws SQLException in case of SQL error 
     */
    public List<Subscriber> findLikeName(String name) throws SQLException {
        List<Subscriber> subscribers = new ArrayList<>();
        try ( Connection conn=dbConnect.getConnection() ) {
            if ( conn != null ) {
                String query = getQuery("findLikeName");
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, "%"+name+"%");
                ResultSet rs = st.executeQuery();
                while ( rs.next() ) {
                    Subscriber s = resultsetToSubscriber(rs);
                    subscribers.add(s);
                }
            }
        } 
        return subscribers;
    }
    
    /**
     * gets all subscribers with given phone
     * @param phone the phone to search
     * @return a list with all subscribers that match given cryteria
     * @throws SQLException in case of SQL error 
     */
    public List<Subscriber> findByPhone(String phone) throws SQLException {
        List<Subscriber> subscribers = new ArrayList<>();
        try ( Connection conn=dbConnect.getConnection() ) {
            if ( conn != null ) {
                String query = getQuery("findByPhone");
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, phone);
                ResultSet rs = st.executeQuery();
                while ( rs.next() ) {
                    Subscriber s = resultsetToSubscriber(rs);
                    subscribers.add(s);
                }
            }
        } 
        return subscribers;
    }

    /**
     * gets all subscribers with given age 
     * @param age the age to search
     * @return a list with all subscribers that match given cryteria
     * @throws SQLException in case of SQL error 
     */
    public List<Subscriber> findByAge(int age) throws SQLException {
        List<Subscriber> subscribers = new ArrayList<>();
        try ( Connection conn=dbConnect.getConnection() ) {
            if ( conn != null ) {
                String query = getQuery("findByAge");
                PreparedStatement st = conn.prepareStatement(query);
                st.setInt(1, age);
                ResultSet rs = st.executeQuery();
                while ( rs.next() ) {
                    Subscriber s = resultsetToSubscriber(rs);
                    subscribers.add(s);
                }
            }
        } 
        return subscribers;
    }
    
    /**
     * inserts a new subscriber
     * @param entity the subscriber to insert
     * @return number of rows affected
     * @throws SQLException in case of SQL error 
     */
    public int insert (Subscriber entity) throws SQLException {
        int result = 0;
        try ( Connection conn=dbConnect.getConnection() ) {
            if ( conn != null ) {
                String query = getQuery("insert");
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, entity.getName());
                st.setString(2, entity.getAddress());
                st.setString(3, entity.getPhone());
                st.setInt(4, entity.getAge());
                result = st.executeUpdate();
            }
        } 
        return result;
    }

    /**
     * updates a subscriber
     * @param entity the subscriber to update
     * @return number of rows affected
     * @throws SQLException in case of SQL error 
     */    
    public int update (Subscriber entity) throws SQLException {
        int result = 0;
        try ( Connection conn=dbConnect.getConnection() ) {
            if ( conn != null ) {
                String query = getQuery("update");
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, entity.getName());
                st.setString(2, entity.getAddress());
                st.setString(3, entity.getPhone());
                st.setInt(4, entity.getAge());
                st.setLong(5, entity.getId());
                result = st.executeUpdate();
            }
        } 
        return result;
    }

    /**
     * deletes a subscriber
     * @param entity the subscriber to delete
     * @return number of rows affected
     * @throws SQLException in case of SQL error 
     */    
    public int delete (Subscriber entity) throws SQLException {
        int result = 0;
        try ( Connection conn=dbConnect.getConnection() ) {
            if ( conn != null ) {
                String query = getQuery("delete");
                PreparedStatement st = conn.prepareStatement(query);
                st.setLong(1, entity.getId());
                result = st.executeUpdate();
            }
        }
        return result;
    }
     
}//end of class
```

Hem optat per no capturar l'excepció [SQLException](https://docs.oracle.com/en/java/javase/19/docs/api/java.sql/java/sql/SQLException.html) a la classe DAO i propagar-la fins a la classe de control, on sabrem de quina manera informar l'usuari.

Programem una classe *Model* que encapsuli les peticions a la classe o classes DAO. En aquest cas, no captura les excepcions SQLException, però podríem haver escollit l'opció oposada i tractar aquí aquests errors (decidint què es comunica i com al controlador).

També hem decidit en aquest exemple que en aquesta classe *Model* es crea l'objecte de la classe *DbConnect* i es llegeix el fitxer de propietats de connexió a la base de dades.

```java
package subscribermanager.model;

import java.io.IOException;
import java.sql.SQLException;
import subscribermanager.model.persistence.SubscriberDao;
import java.util.List;
import subscribermanager.model.persistence.DbConnect;
/**
 * Model to use data access layer to get access to data in database.
 * In this example, SQLException is passed to caller, who would be responsible of catching it and treat error.
 * Another solution would be catching it and deciding here what to do.
 * @author ProvenSoft
 */
public class Model {

    private final String DBCONNPROPS = "files/dbconn.properties";
    private final SubscriberDao subscriberDao;
    
    public Model() throws ClassNotFoundException, IOException {
        DbConnect dbConn = new DbConnect();
        dbConn.setPropsFile(DBCONNPROPS);
        this.subscriberDao = new SubscriberDao(dbConn);
    }
    
    public Subscriber searchSubscriberById(long id) throws SQLException {
        return subscriberDao.findById(id);
    }

    public List<Subscriber> searchAllSubscribers() throws SQLException {
        List<Subscriber> result;
        result = subscriberDao.findAll();
        return result;
    }
    
    public List<Subscriber> searchSubscribersLikeName(String name) throws SQLException {
        List<Subscriber> result;
        result = subscriberDao.findLikeName(name);
        return result;
    } 
    
    public List<Subscriber> searchSubscribersByPhone(String phone) throws SQLException {
        List<Subscriber> result;
        result = subscriberDao.findByPhone(phone);
        return result;
    }

    public List<Subscriber> searchSubscribersByAge( int age ) throws SQLException {
        List<Subscriber> result;
        result = subscriberDao.findByAge(age);
        return result;
    }
    
    public int addSubscriber(Subscriber s) throws SQLException {
        int result = subscriberDao.insert(s);
        return result;
    }
    
    public int updateSubscriber(Subscriber s) throws SQLException {
        int result = subscriberDao.update(s);
        return result;
    }
    
    public int deleteSubscriber(Subscriber s) throws SQLException {
        int result = subscriberDao.delete(s);
        return result;
    }
    
}//end of class
```

Per últim, creem una classe principal que faci de capa de presentació (controlador i vista).

```java
package subscribermanager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import subscribermanager.model.Model;
import subscribermanager.model.Subscriber;

public class Main {

    public static void main(String[] args) {
        try {
            Model model = new Model();
            List<Subscriber> list;
            Subscriber subs;
            int result;
            //search by id that exists.
            System.out.println("====Search id="+3+"====");
            subs = model.searchSubscriberById(3);
            System.out.println(subs);
            //search by name that exists.
            System.out.println("====Search name="+"4"+"====");
            list = model.searchSubscribersLikeName("4");
            printList(list);
            //search by phone that exists.
            System.out.println("====Search phone="+"phone05"+"====");
            list = model.searchSubscribersByPhone("phone05");
            printList(list);
            //search by age that exists.
            System.out.println("====Search age="+"12"+"====");
            list = model.searchSubscribersByAge(12);
            printList(list);
            //add a new subscriber.
            System.out.println("====Add a new subscriber====");
            subs = new Subscriber(0, "name09", "address09", "phone09", 19);
            result = model.addSubscriber(subs);
            System.out.println("result="+result);
            //update a subscriber.
            System.out.println("====Update subscriber with id=4====");
            subs = model.searchSubscriberById(4);
            if (subs != null) {
                subs.setName("name44"); subs.setAddress("add44"); subs.setAge(44);
                result = model.updateSubscriber(subs);
                System.out.println("result="+result);
            } else {
                System.out.println("Not found");
            }
            //delete a subscriber.
            System.out.println("====Delete subscriber with id=9====");
            subs = new Subscriber(9);
            result = model.deleteSubscriber(subs);
            System.out.println("result="+result);
            //search all subscribers
            list = model.searchAllSubscribers();
            printList(list);
        } catch (FileNotFoundException  ex) {
            //database connexion properties configuration file not found
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException  ex) {
            //database connexion properties configuration file unable to be read
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            //database driver not found and not loaded.
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            //SQL error generated in data access layer
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("SQLState: " + ex.getSQLState());
        }
    }
    
    public static void printList(List<Subscriber> list) {
        System.out.format("Listing %d elements\n", list.size());
        list.forEach(System.out::println);
    }

}
```

Amb aquesta classe podem provar les funcionalitats implementades.

[Codi complet de l'exemple](/damm06/assets/2.1/subscribermanager.zip).
