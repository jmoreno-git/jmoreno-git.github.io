# UF2-NF1. Model desconnectat: RowSet

[Using CachedRowSet](/damm06/assets/2.1/using_cachedrowseet.pdf)

[CachedRowSet: Java documentation](https://docs.oracle.com/javase/tutorial/jdbc/basics/cachedrowset.html)

## Exemple amb CachedRowSet: base de dades de països

Codi sql per a la base de dades: **db_country.sql**

```sql
-- Create user for local access.
CREATE USER 'usrcountry'@'localhost' IDENTIFIED BY 'pswcountry';
-- Create database.
CREATE DATABASE dbcountry
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
-- Grant permissions.
GRANT SELECT, INSERT, UPDATE, DELETE ON dbcountry.* TO 'usrcountry'@'localhost';
-- Use database.
USE dbcountry;
-- Create table 'countries'
CREATE TABLE `countries` (
`id` int(4) NOT NULL auto_increment,
`name` varchar(40) NOT NULL,
`capital` varchar(40) default NULL,
`surface` double default 0.0,
`inhabitants` int default 0,
`pib` double default 0.0,
`lifeexpectancy` int default 0,
PRIMARY KEY (`id`)
) ENGINE=InnoDB;
-- Data insertion.
INSERT INTO `countries` (`id`, `name`, `capital`, `surface`, `inhabitants`, `pib`, `lifeexpectancy`) 
VALUES
(1, 'Albania', 'Tirana', 28748, 3100000, 3189, 73),
(2, 'Alemania Federal', 'Berlín', 357021, 82000000, 23742, 77),
(3, 'Andorra', 'AndorraLaVieja', 453, 75000, 17420, 78),
(4, 'Austria', 'Viena', 83859, 8100000, 25089, 77),
(5, 'Belarús', 'Minsk', 207600, 10100000, 6876, 68),
(6, 'Bélgica', 'Bruselas', 30528, 10300000, 25443, 78),
(7, 'BosniayHerzegovina', 'Sarajevo', 51129, 4100000, 1086, 56),
(8, 'Bulgaria', 'Sofia', 110994, 7900000, 5071, 70),
(9, 'Croacia', 'Zagreb', 56610, 4700000, 7387, 73),
(10, 'Dinamarca', 'Copenhague', 44493, 5300000, 25869, 76),
(11, 'Eslovaquia', 'Bratislava', 49035, 5400000, 10591, 73),
(12, 'Eslovenia', 'Lubiana', 20258, 2000000, 15977, 75),
(13, 'España', 'Madrid', 505989, 39900000, 18079, 78),
(14, 'Estonia', 'Tallin', 45227, 1400000, 8355, 70),
(15, 'FederacióndeRusia', 'Moscú', 17075400, 144700000, 7473, 66),
(16, 'Finlandia', 'Helsinki', 338145, 5200000, 23, 77),
(17, 'Francia', 'París', 543965, 59500000, 22897, 78),
(18, 'Grecia', 'Atenas', 131957, 10600000, 15414, 78),
(19, 'Hungría', 'Budapest', 93030, 9900000, 11430, 71),
(20, 'Irlanda', 'Dublin', 70285, 3800000, 25918, 76),
(21, 'Islandia', 'Reykjavik', 102819, 281000, 27835, 79),
(22, 'Italia', 'Roma', 301308, 57500000, 22172, 78),
(23, 'Letonia', 'Riga', 64610, 2400000, 6264, 70),
(24, 'Liechtenstein', 'Vaduz', 160, 32015, 37000, 72),
(25, 'Lituania', 'Vilnius', 65300, 3700000, 6656, 71),
(26, 'Luxemburgo', 'Luxemburgo', 2586, 442000, 42769, 77),
(27, 'Macedonia', 'Skopje', 25713, 2000000, 4651, 73),
(28, 'Malta', 'La Valletta', 316, 392000, 15189, 77),
(29, 'Moldova, Rep. de', 'Kisinev', 33700, 4300000, 2037, 66),
(30, 'Mónaco', 'Mónaco', 2, 33000, 26470, 78),
(31, 'Noruega', 'Oslo', 323758, 4500000, 28433, 78),
(32, 'Países Bajos', 'Amsterdam', 41526, 15900000, 24215, 78),
(33, 'Polonia', 'Varsovia', 312685, 38600000, 8450, 73),
(34, 'Portugal', 'Lisboa', 91831, 10000000, 16064, 75),
(35, 'Reino Unido de Gran Bretaña', 'Londres', 244110, 59500000, 22093, 77),
(36, 'República Checa', 'Praga', 78866, 10300000, 13018, 74),
(37, 'Rumania', 'Bucarest', 238391, 22400000, 6041, 69),
(38, 'Suecia', 'Estocolmo', 449964, 8800000, 22636, 79),
(39, 'Suiza', 'Berna', 41285, 7200000, 27171, 78),
(40, 'Ucrania', 'Kiev', 603700, 49100000, 3458, 68);
```

Classe per encapsular les dades de connexió: **DbConnect.java**

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * encapsulates data for database connection.
 *
 * @author Jose
 */
public final class DbConnect {

    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String PROTOCOL = "jdbc:mysql:";
    static final String HOST = "127.0.0.1";
    static final String BD_NAME = "dbcountry";
    static final String USER = "usrcountry";
    static final String PASSWORD = "pswcountry";
    static final String BD_URL = String.format("%s//%s/%s", PROTOCOL, HOST, BD_NAME);;

    public static void loadDriver() throws ClassNotFoundException {
        Class.forName(DRIVER);
    }
    
    /**
     * gets and returns a connection to database
     *
     * @return connection
     * @throws java.sql.SQLException
     */
    public static Connection getConnection() throws SQLException {
        //DbConnect.BD_URL = String.format("%s//%s/%s", PROTOCOL, HOST, BD_NAME);
        Connection conn = null;
        conn = DriverManager.getConnection(BD_URL, USER, PASSWORD);
        return conn;
    }
}
```

Classe del model per al tipus de dada Country: **Country.java**

```java
/**
 * ADT for country.
 * @author Jose
 */
public class Country {
    private int id;
    private String name;
    private String capital;
    private double surface;
    private int inhabitants;
    private double pib;
    private int lifeexpectancy;

    public Country(int id, String name, String capital, double surface, int inhabitants, double pib, int lifeexpectancy) {
        this.id = id;
        this.name = name;
        this.capital = capital;
        this.surface = surface;
        this.inhabitants = inhabitants;
        this.pib = pib;
        this.lifeexpectancy = lifeexpectancy;
    }

    public Country(String name, String capital, double surface, int inhabitants, double pib, int lifeexpectancy) {
       
        this.name = name;
        this.capital = capital;
        this.surface = surface;
        this.inhabitants = inhabitants;
        this.pib = pib;
        this.lifeexpectancy = lifeexpectancy;
    }

    
    public Country() {
    }

    public Country(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public int getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(int inhabitants) {
        this.inhabitants = inhabitants;
    }

    public double getPib() {
        return pib;
    }

    public void setPib(double pib) {
        this.pib = pib;
    }

    public int getLifeexpectancy() {
        return lifeexpectancy;
    }

    public void setLifeexpectancy(int lifeexpectancy) {
        this.lifeexpectancy = lifeexpectancy;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + this.id;
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
        final Country other = (Country) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Country{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", capital=").append(capital);
        sb.append(", surface=").append(surface);
        sb.append(", inhabitants=").append(inhabitants);
        sb.append(", pib=").append(pib);
        sb.append(", lifeexpectancy=").append(lifeexpectancy);
        sb.append('}');
        return sb.toString();
    }

}
```

Classe d'accés a dades usant CachedRowSet per als objectes Country: **CountryCachedRowSet.java**

```java
//import com.sun.rowset.CachedRowSetImpl;
import javax.sql.rowset.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountryCachedRowSet {

    private final CachedRowSet crs;

    /**
     * constructor
     *
     * @param username The name a user supplies to a database as part of gaining
     * access
     * @param password The user's database password
     * @param url The JDBC URL for the database to which the user wants to
     * connect
     * @param command query to be executed
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public CountryCachedRowSet(String url, String username, String password, String command)
            throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        crs = RowSetProvider.newFactory().createCachedRowSet();

        crs.setUsername(username);
        crs.setPassword(password);
        crs.setUrl(url);
        crs.setCommand(command);

//      crs.setPageSize(20);
//        this.execute();
    }

    public CountryCachedRowSet(String command)
            throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        crs = RowSetProvider.newFactory().createCachedRowSet();

        crs.setUsername(DbConnect.USER);
        crs.setPassword(DbConnect.PASSWORD);
        crs.setUrl(DbConnect.BD_URL);
        crs.setCommand(command);
//      crs.setPageSize(20);
    }
    
    public CachedRowSet getCachedRowSet() {
        return crs;
    }

    /**
     * creates and returns a connection with the given autocommit option.
     *
     * @param autocommit
     * @return Connection
     * @throws java.sql.SQLException
     */
    public Connection getConnection(boolean autocommit) throws SQLException {
        Connection conn = DriverManager.getConnection(crs.getUrl(), crs.getUsername(), crs.getPassword());
        conn.setAutoCommit(autocommit);
        return conn;
    }

    /**
     * commits cachedrowset changes to database.
     *
     * @return true if successful, false otherwise
     */
    public boolean commitToDatabase() {
        boolean b;
        try ( Connection conn = getConnection(false)) {
            // propagate changes and close connection
            crs.acceptChanges(conn);
            // reload data.
            crs.execute();
            b = true;
        } catch (SQLException se) {
            Logger.getLogger(CountryCachedRowSet.class.getName()).log(Level.SEVERE, null, se);
            b = false;
        } catch (Exception e) {
            Logger.getLogger(CountryCachedRowSet.class.getName()).log(Level.SEVERE, null, e);
            b = false;
        }
        return b;
    }

    /**
     * updates current row with given object to the database using a
     * CachedRowSet
     *
     * @param country to update
     * @throws SQLException
     */
    public void updateInCachedRowSet(Country country) throws SQLException {
        crs.updateString("name", country.getName());
        crs.updateString("capital", country.getCapital());
        crs.updateDouble("surface", country.getSurface());
        crs.updateInt("inhabitants", country.getInhabitants());
        crs.updateDouble("pib", country.getPib());
        crs.updateInt("lifeexpectancy", country.getLifeexpectancy());
        crs.updateRow();
    }

    /**
     * inserts an object to the database through CachedRowSet
     *
     * @param country to insert
     * @throws SQLException
     */
    public void insertInCachedRowSet(Country country) throws SQLException {
        crs.moveToInsertRow();
        crs.updateInt("id", 0);
        crs.updateString("name", country.getName());
        crs.updateString("capital", country.getCapital());
        crs.updateDouble("surface", country.getSurface());
        crs.updateInt("inhabitants", country.getInhabitants());
        crs.updateDouble("pib", country.getPib());
        crs.updateInt("lifeexpectancy", country.getLifeexpectancy());
        crs.insertRow();
        crs.moveToCurrentRow();
    }

    /**
     * deletes current row in CachedRowSet
     *
     * @throws SQLException
     */
    public void deleteInCachedRowSet() throws SQLException {
        crs.deleteRow();
    }

    /**
     * moves cursos forwards
     *
     * @return true if successful, false otherwise
     */
    public boolean nextInPage() {
        boolean b = false;
        try {
            if (crs.isLast()) {
                if (crs.getPageSize() > 0) {
                    if (crs.nextPage()) {

                        crs.beforeFirst();
                        b = crs.next();
                    } else {
                        crs.previousPage();
                        this.lastInPage();
                    }
                }
            } else {
                b = crs.next();
            }
        } catch (SQLException ex) {
            b = false;
            Logger.getLogger(CountryCachedRowSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    /**
     * moves cursor backwards
     *
     * @return true if successful, false otherwise
     */
    public boolean previousInPage() {
        boolean b = false;
        try {
            if (crs.isFirst()) {
                if (crs.getPageSize() > 0) {
                    if (crs.previousPage()) {
                        crs.afterLast();
                        b = crs.previous();
                    }
                } else {
                    crs.nextPage();

                    this.firstInPage();
                }
            } else {
                b = crs.previous();
            }
        } catch (SQLException ex) {
            b = false;
            Logger.getLogger(CountryCachedRowSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    /**
     * moves cursor to first row
     *
     * @return true if successful, false otherwise
     */
    public boolean firstInPage() {
        boolean b;
        try {
            b = crs.first();
        } catch (SQLException ex) {
            b = false;
            Logger.getLogger(CountryCachedRowSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    /**
     * moves cursor to last row
     *
     * @return true if successful, false otherwise
     */
    public boolean lastInPage() {
        boolean b;
        try {
            b = crs.last();
        } catch (SQLException ex) {
            b = false;
            Logger.getLogger(CountryCachedRowSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    /**
     * gets a Country object with the data in the current row
     *
     * @return Country or null in case of error.
     */
    public Country getCurrentRowCountry() {
        Country c = null;
        try {
            int id = crs.getInt("id");
            String name = crs.getString("name");
            String capital = crs.getString("capital");
            double surface = crs.getDouble("surface");
            int inhabitants = crs.getInt("inhabitants");
            double pib = crs.getDouble("pib");
            int lifeexpectancy = crs.getInt("lifeexpectancy");
            c = new Country(id, name, capital, surface, inhabitants, pib, lifeexpectancy);
        } catch (SQLException ex) {
            c = null;
            Logger.getLogger(CountryCachedRowSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

}
```

Classe principal per provar el funcionament: **CachedRowSetMain.java**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CachedRowSetMain {

    private final String query = "select * from countries";  
    
    public static void main(String[] args) {
        CachedRowSetMain app = new CachedRowSetMain();
        app.run();      
    }

    private void run() {
        try {
            DbConnect.loadDriver();
            //instantiate cached row set
            CountryCachedRowSet helper = new CountryCachedRowSet(query);
            
            System.out.println("Showing first 20 countries");
            helper.getCachedRowSet().setPageSize(20);
            helper.getCachedRowSet().execute();
            //display cachedrowset content.
            displayCachedRowSet(helper);  //--1
            pause();
            //add a new country
            System.out.println("Adding a new country as last row: Wonderland");
            //create a new country object.
            Country country = new Country("Wonderland", "HappyCity", 1000.0, 1000000, 100000.0, 120);
            //insert country
            helper.insertInCachedRowSet(country);
            helper.commitToDatabase();
            //crs.acceptChanges(helper.getConnection(false));
            //display cachedrowset content.
            displayCachedRowSet(helper); //--2
            //update last row.
            helper.lastInPage();
            pause();
            System.out.println("Modifying the country in current row");
            country = new Country("Fakeland", "SucksCity", 500.0, 12000, 11000.0, 50);
            helper.updateInCachedRowSet(country);
            helper.commitToDatabase();
            //crs.acceptChanges(helper.getConnection(false));
            //
            //display cachedrowset content.
            displayCachedRowSet(helper); //--3
            pause();
            //
            System.out.println("Delete last row in page");
            //delete last row.
            helper.lastInPage();
            helper.deleteInCachedRowSet();
            helper.commitToDatabase();
            //crs.acceptChanges(helper.getConnection(false));
            
            //display cachedrowset content.
            displayCachedRowSet(helper); //--4
            
            //browse data.
            browseCachedRowSet(helper);
            
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * displays CachedRowSet content
     * @param crs CachedRowSet
     * @throws SQLException 
     */
    private void displayCachedRowSet(CountryCachedRowSet crs) throws SQLException {
        System.out.format("Listing %d countries\n", crs.getCachedRowSet().size());
        System.out.println("Page size: "+crs.getCachedRowSet().getPageSize());
        
        if (crs != null) {
            while (crs.getCachedRowSet().next()) {
                Country c = crs.getCurrentRowCountry();
                System.out.format("Id= %d, Name: %s, Capital: %s\n",
                        c.getId(), c.getName(), c.getCapital());
            }            
        }
    }

    
    /**
     * browses along a CachedRowSet
     * @param crs CachedRowSet to browse
     * @throws SQLException 
     */
    private void browseCachedRowSet(CountryCachedRowSet pcrs) throws SQLException {
        String c;
        if (pcrs != null) {
            pcrs.getCachedRowSet().first();
            do {
                c = moveMenu();
                switch (c) {
                    case "N" -> pcrs.nextInPage();
                    case "P" -> pcrs.previousInPage();
                    case "L" -> pcrs.lastInPage();
                    case "F" -> pcrs.firstInPage();
                }
                Country country = pcrs.getCurrentRowCountry();
                System.out.format("name: %s, capital: %s\n",
                        country.getName(), country.getCapital());   
            } while (!c.equals("Q"));            
        }
    }
    
    /**
     * Pauses execution
     */
    private void pause() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Press any key to continue...");
        sc.nextLine();
    }
    
    /**
     * Displays moveMenu and reads option from user.
     *
     * @return the option selected by user.
     */
    private String moveMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] options = {"Quit", "Next", "Previous", "Last in page", "First in page"};
        System.out.println("===Move menu===");
        for (String option : options) {
            System.out.println("[" + option.charAt(0) + "]" + " - " + option);
        }
        String op = "Q";
        try {
            op = ((br.readLine()).toUpperCase());
        } catch (IOException ioe) {
        }
        return op;
    }
    
}
```

[Codi complet de l'exemple](/damm06/assets/2.1/countries-catchedrowset.zip)
