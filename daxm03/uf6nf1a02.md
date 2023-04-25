# Mapeig objecte relacional i capa d'accés a dades

## Introducció
Les aplicacions treballen amb objectes en memòria. En canvi, les bases de dades relacionals treballen amb taules i relacions. A les tècniques de processament de la informació dels objectes per fer-los persistents en forma d'entrades de taules i relacions se les anomena mapeig objecte relacional (***ORM: object relational mapping***).

Un patró de disseny molt utilitzat i pràctic per a dissenyar la capa d'accés a dades és el patró ***DAO. Data Access Object***.

Anem a il·lustrar l'ORM amb un exemple d'aplicació amb dues entitats amb una relació 1xn.

## Objectius

  - Implementar el patró DAO per a accés a dades.
  - Crear la base de dades definint correctament les relacions entre les
    entitats i les restriccions d'integritat.
  - Crear un joc de dades de prova adient.

## Desenvolupament de l'aplicació Store: Enunciat

Una empresa ens ha encarregat que desenvolupem una aplicació per gestionar els seus productes, els quals estan agrupats en categories. Una categoria pot tenir 0 o diversos productes. Cada producte només pot pertànyer a una categoria.

El que necessita gestionar aquesta empresa és el següent:

Les funcionalitats que cal implementar son altes, baixes, modificacions i consultes de categories, productes i les que gestionen la relació entre categoria i producte.

## La base de dades

``` sql
CREATE USER 'storeusr'@'localhost' IDENTIFIED BY 'storepsw';
CREATE DATABASE storedb
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
GRANT SELECT, INSERT, UPDATE, DELETE ON storedb.* TO 'storeusr'@'localhost';
USE storedb;
CREATE TABLE `categories` (
    `id` INT(4) NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(10) NOT NULL UNIQUE,
    `name` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `products` (
    `id` INT(4) NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(10) NOT NULL UNIQUE,
    `name` VARCHAR(20) NOT NULL,
    `stock` INT DEFAULT 0,
    `price` DOUBLE DEFAULT 0.0,
    `category_id` INT(4),
    PRIMARY KEY (`id`)
);
ALTER TABLE `products` 
    ADD CONSTRAINT `fk_category` FOREIGN KEY (category_id) 
    REFERENCES categories(id)
    ON UPDATE CASCADE ON DELETE RESTRICT;
INSERT INTO categories VALUES 
    (1, "C01", "category01"),
    (2, "C02", "category02"),
    (3, "C03", "category03"),
    (4, "C04", "category04"),
    (5, "C05", "category05"),
    (6, "C06", "category06");
INSERT INTO products VALUES 
    (1, "P01", "product01", 101, 1001.0, 1),
    (2, "P02", "product02", 102, 1002.0, 2),
    (3, "P03", "product03", 103, 1003.0, 3),
    (4, "P04", "product04", 104, 1004.0, 4),
    (5, "P05", "product05", 105, 1005.0, 5),
    (6, "P06", "product06", 106, 1006.0, 1),
    (7, "P07", "product07", 107, 1007.0, 1),
    (8, "P08", "product08", 108, 1008.0, 2),
    (9, "P09", "product09", 109, 1009.0, 3);
```

Les claus primàries s'han escollit de tipus autoincremental.

Les restriccions d'integritat de la clau forana que relaciona producte amb categoria estan definides de manera que les actualitzacions es propaguen en cascada però els esborrats no, per evitar pèrdues massives de dades en esborrar.

S'ha definit un usuari per a l'accés des de l'aplicació, al qual s'han atorgat els permisos imprescindibles.

## La classe de connexió

Aquesta classe encapsula les dades de connexió a la base de dades des de l'aplicació i s'encarrega també de proporcionar connexions a través del mètode *getConnection()*.

``` java
package cat.proven.categprods.model.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * encapsulates data for database connection.
 *
 * @author ProvenSoft
 */
public final class DbConnect {

    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String PROTOCOL = "jdbc:mysql:";
    static final String HOST = "127.0.0.1";
    static final String BD_NAME = "storedb";
    static final String USER = "storeusr";
    static final String PASSWORD = "storepsw";

    public static void loadDriver() throws ClassNotFoundException {
        //getConnectionProperties(); better if connection properties are read from a configuration file
        Class.forName(DRIVER);
    }
    
    /**
     * gets and returns a connection to database
     *
     * @return connection
     * @throws java.sql.SQLException
     */
    public Connection getConnection() throws SQLException {
        final String BD_URL = String.format("%s//%s/%s", PROTOCOL, HOST, BD_NAME);
        Connection conn;
        conn = DriverManager.getConnection(BD_URL, USER, PASSWORD);
        return conn;
    }
}
```

## Les classes del model de dades

Són les classes que defineixen el tipus de dades (objectes) amb què treballa la nostra aplicació.

S'acostuma a anomenar aquesta capa *business layer*.

Definim una classe per a l'entitat *Category* i una altra per a l'entitat *Product*.

```java
package cat.proven.categprods.model;

import java.util.Objects;

/**
 *
 * @author ProvenSoft
 */
public class Category {

    private long id;
    private String code;
    private String name;

    public Category(long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Category() {
    }

    public Category(long id) {
        this.id = id;
    }

    public Category(Category other) {
        this.id = other.id;
        this.code = other.code;
        this.name = other.name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.code);
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
        if (!(obj instanceof Category)) {
            return false;
        }
        final Category other = (Category) obj;
        return Objects.equals(this.code, other.code);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category{");
        sb.append("id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append('}');
        return sb.toString();
    }

}
```

``` java
package cat.proven.categprods.model;

import java.util.Objects;

/**
 *
 * @author ProvenSoft
 */
public class Product {
    private long id;
    private String code;
    private String name;
    private int stock;
    private double price;
    private Category category;

    public Product(long id, String code, String name, int stock, double price, Category category) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.category = category;
    }

    public Product() {
    }

    public Product(long id) {
        this.id = id;
    }

    public Product(Product other) {
        this.id = other.id;
        this.code = other.code;
        this.name = other.name;
        this.stock = other.stock;
        this.price = other.price;
        this.category = other.category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.code);
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
        final Product other = (Product) obj;
        return Objects.equals(this.code, other.code);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product{");
        sb.append("id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", stock=").append(stock);
        sb.append(", price=").append(price);
        sb.append(", category=").append(category);
        sb.append('}');
        return sb.toString();
    }
    
}
```

## Capa d'accés a dades (DAL). Les classes DAO

La capa d'accés a dades (***Data Access Layer***) s'encarrega de la persistència de les dades del model (capa de negoci).

Apliquem el patró **DAO** (***Data Access Object***), el qual usa una classe per a encapsular les consultes a cada una de les taules de la base de dades.

Aquestes classes implementen les funcionalitats del conegut **CRUD** (*Create*, *Read*, *Update*, *Delete*).

Cas que la relació entre les entitats fos mxn, al model relacional apareixeria també una taula pivot *categoriesproducts*, per a la qual caldria també definir una classe del model *CategoyProduct*. En el nostre cas, com que la relació és 1xn, només hi ha una columna addicional a la taula *products* per emmagatzemar la clau forana que relaciona amb la primària de la taula *categories*.

Els mètodes ```T fromResultset(Resulset rs) throws SQLException``` són mètodes de conveniència per llegir els camps obtinguts a una fila del Resulset (després d'haver executat una consulta de dades) i crear amb ells un objecte de tipus T (Category o Product, segons el cas) que contingui la informació.

``` java
package cat.proven.categprods.model.persist;

import cat.proven.categprods.model.Category;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for category table
 *
 * @author ProvenSoft
 */
public class CategoryDao {

    private final DbConnect dbConnect;

    public CategoryDao() {
        this.dbConnect = new DbConnect();
    }

    private Category fromResultSet(ResultSet rs) throws SQLException {
        Category cat;
        long id = rs.getLong("id");
        String code = rs.getString("code");
        String name = rs.getString("name");
        cat = new Category(id, code, name);
        return cat;
    }

    public int insert(Category category) {
        int result = 0;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "insert into categories values (null, ?, ?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, category.getCode());
            st.setString(2, category.getName());
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }        
        return result;
    }

    public int update(Category currentCategory, Category updatedCategory) {
        int result = 0;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = """
                           update categories set 
                           code=?, name=?  
                           where id=?
                           """;
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, updatedCategory.getCode());
            st.setString(2, updatedCategory.getName());
            st.setLong(3, currentCategory.getId());
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }        
        return result;        
    }
    
    public Category select(Category category) {
        Category cat = null;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from categories where id=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, category.getId());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                cat = fromResultSet(rs);

            } else {
                cat = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cat;
    }
    
    public Category selectWhereCode(String code) {
        Category cat = null;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from categories where code=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, code);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                cat = fromResultSet(rs);
 
            } else {
                cat = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cat;
    }
    
    public Category selectWhereName(String name) {
        Category cat = null;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from categories where name=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                cat = fromResultSet(rs);

            } else {
                cat = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cat;
    }

    public List<Category> selectAll() {
        List<Category> result = new ArrayList<>();
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from categories";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Category cat = fromResultSet(rs);
                if (cat != null) {
                    result.add(cat);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
```

``` java
package cat.proven.categprods.model.persist;

import cat.proven.categprods.model.Category;
import cat.proven.categprods.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for product table
 *
 * @author ProvenSoft
 */
public class ProductDao {

    private final DbConnect dbConnect;

    public ProductDao() {
        this.dbConnect = new DbConnect();
    }

    private Product fromResultSet(ResultSet rs) throws SQLException {
        Product prod;
        long id = rs.getLong("id");
        String code = rs.getString("code");
        String name = rs.getString("name");
        int stock = rs.getInt("stock");
        double price = rs.getDouble("price");
        long categoryId = rs.getLong("category_id");
        prod = new Product(id, code, name, stock, price, new Category(categoryId));
        return prod;
    }

    public int insert(Product product) {
        int result = 0;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "insert into products values (null, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, product.getCode());
            st.setString(2, product.getName());
            st.setInt(3, product.getStock());
            st.setDouble(4, product.getPrice());
            st.setLong(5, product.getCategory().getId());
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }        
        return result;
    }    
    
    public Product select(Product product) {
        Product prod = null;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from products where id=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, product.getId());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                prod = fromResultSet(rs);

            } else {
                prod = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return prod;
    }

    public Product selectWhereCode(String code) {
        Product prod = null;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from products where code=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, code);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                prod = fromResultSet(rs);

            } else {
                prod = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return prod;
    }

    public List<Product> selectWhereMinStock(int minStock) {
        List<Product> result = new ArrayList<>();
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from products where stock<?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, minStock);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product prod = fromResultSet(rs);
                if (prod != null) {
                    result.add(prod);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public List<Product> selectAll() {
        List<Product> result = new ArrayList<>();
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from products";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Product prod = fromResultSet(rs);
                if (prod != null) {
                    result.add(prod);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public List<Product> selectWhereCategory(Category category) {
        List<Product> result = new ArrayList<>();
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from products where category_id=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, category.getId());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product prod = fromResultSet(rs);
                if (prod != null) {
                    result.add(prod);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
```

Les consultes poden ser bàsicament de dos tipus:

* consultes que retornen dades: s'executen amb *executeQuery()* i retornen un *ResultSet*.
* consultes que realitzen canvis a la base de dades: s'executen amb *executeUpdate()* i retornen el nombre de registres afectats per les modificacions

Per a encapsular les consultes utilitzem la classe [***Statement***](https://docs.oracle.com/en/java/javase/20/docs/api/java.sql/java/sql/class-use/Statement.html).

Un *Statement* es crea amb el mètode *createStatement()* de la classe [**Connection**](https://docs.oracle.com/en/java/javase/20/docs/api/java.sql/java/sql/Connection.html).

Per a consultes que admeten paràmetres variables (select ... where, etc) utilitzem [***PreparedStatement***](https://docs.oracle.com/en/java/javase/20/docs/api/java.sql/java/sql/PreparedStatement.html). Els paràmetres es defineixen a la consulta amb el símbol '*?*' i se'ls assigna valors amb mètodes *setXXX* del *PreparedStatement* (hi ha mètodes setXXX diferents segons els tipus de dades a assignar). Els PreparedStatement es creen amb el mètode *prepareStatement(String sql)* de *Connection*.

Les consultes de selecció de dades retornen un [***ResulSet***](https://docs.oracle.com/en/java/javase/20/docs/api/java.sql/java/sql/ResultSet.html), el qual és un cursor amb mètodes per recórrer els resultats de la consulta i per llegir-ne els valors i les seves propietats.

## Encapsulant els serveis de dades: El model

La lògica de l'aplicació (**capa de control**) i la interacció amb l'usuari (**vista**) constitueixen, si no estan separades, la **capa de presentació**.

Cal encapsular els detalls del funcionament de la capa d'accés a dades per tal que la capa de presentació sigui totalment independent i es pugui desenvolupar per separat.

Per aquest motiu, introduirem una classe (el **model**) que farà de servidor de dades per a la resta de l'aplicació.

``` java
package cat.proven.categprods.model;

import cat.proven.categprods.model.persist.CategoryDao;
import cat.proven.categprods.model.persist.ProductDao;
import java.util.List;

/**
 * Model for store application. Provides data services.
 *
 * @author ProvenSoft
 */
public class StoreModel {

    private final CategoryDao categoryDao;
    private final ProductDao productDao;

    public StoreModel() {
        this.categoryDao = new CategoryDao();
        this.productDao = new ProductDao();
    }

    /**
     * Data services related to category
     */
    
    /**
     * adds a category to data source, preventing duplicates in unique keys and
     * null values
     *
     * @param category the category to add
     * @return result code: 1 for success, 0 if fail (change as necessary)
     */
    public int addCategory(Category category) {
        int result = 0;
        if (category != null) { 
            //perform proper validations before attempting insertion
            boolean dataValid = true;
            String code = category.getCode();
            if (code==null) dataValid = false; //code must not be null
            else { //assess that code does not exist
                Category c = categoryDao.selectWhereCode(code);
                if (c != null) dataValid = false;
            }
            if (dataValid) {  //perform insertion
                result = categoryDao.insert(category);
            }
        }
        return result;
    }

    /**
     * modifies a category in the data source, performing proper validations
     *
     * @param oldC the actual category to update
     * @param newC the new values to update
     * @return result code: 1 for success, 0 if fail (change as necessary)
     */
    public int modifyCategory(Category oldC, Category newC) {
        int result = 0;
        if ((oldC != null) && (newC != null)) { //perform proper validations before attempting insertion
            result = categoryDao.update(oldC, newC);
        }
        return result;
    }

    /**
     * finds all categories in data source
     *
     * @return list with all categories or null in case of error
     */
    public List<Category> findAllCategories() {
        return categoryDao.selectAll();
    }

    /**
     * finds a category with the given code
     *
     * @param code the code to find
     * @return category found or null if not found or in case of error
     */
    public Category findCategoryByCode(String code) {
        Category c = null;
        if (code != null) {
            c = categoryDao.selectWhereCode(code);
        }
        return c;
    }

    /**
     * Data services related to product
     */
    /**
     * adds a product to data source, preventing duplicates in unique keys and
     * null values
     *
     * @param product the category to add
     * @return result code: 1 for success, 0 if fail (change as necessary)
     */
    public int addProduct(Product product) {
        int result = 0;
        if (product != null) {
            //perform proper validations before attempting insertion
            boolean dataValid = true;
            String code = product.getCode();
            if (code==null) dataValid = false; //code must not be null
            else { //assess that code does not exist
                Product p = productDao.selectWhereCode(code);
                if (p != null) dataValid = false;
            }
            //get category from database
            Category cat = categoryDao.select(product.getCategory());
            if (cat == null) dataValid = false;  //category must exist
            if (dataValid) {  //perform insertion
                result = productDao.insert(product);
            }
        }
        return result;
    }

    /**
     * finds all products in data sources
     *
     * @return list of all products or null in case of error
     */
    public List<Product> findAllProducts() {
        return productDao.selectAll();
    }

    /**
     * finds a product with the given code
     *
     * @param code the code to find
     * @return category found or null if not found or in case of error
     */
    public Product findProductByCode(String code) {
        Product c = null;
        if (code != null) {
            c = productDao.selectWhereCode(code);
        }
        return c;
    }

    /**
     * Data services related to category-product relationship
     */
    /**
     * finds all products belonging to given category
     *
     * @param category the category whose products are being searched
     * @return list of products of given category or null in case of error
     */
    public List<Product> findProductsByCategory(Category category) {
        List<Product> result = null;
        if (category != null) {
            result = productDao.selectWhereCategory(category);
        }
        return result;
    }
```

## Implementant la interacció amb l'usuari

El servei de dades el proveeix la classe *StoreModel* del model, abans definida. Ara hem d'implementar la capa de presentació, la qual comprén la lògica de l'aplicació (controlador) i la interfície d'usuari (vista).

Definirem una classe principal (és a dir, amb un mètode *main()*), la qual contindrà una referència del model (per accedir als serveis de dades) i un menú textual perquè l'usuari esculli quina opció vol executar en cada moment.

Aquesta classe contindrà el bucle principal de control, el qual mostrarà el menú, llegirà de l'usuari l'opció que vol executar i delegarà al mètode de control corresponent la realització de l'acció sol·licitada.

Les respostes a l'usuari s'encarreguen a mètodes de vista que presentaran els missatges i els resultats de les accions.

La comunicació de la classe d'interfície amb l'usuari (UI) amb model de dades de l'aplicació es pot aconseguir de diverses maneres:

* Instanciar el model al constructor de la UI
```java
public class CategProdUI {
    private final StoreModel model;
    public CategProdUI() {
        this.model = new StoreModel();
    }
    ...
}
```
* Instanciant el model al principal (main()) i passant-ne la referència a la UI com a paràmetre del seu constructor
```java
public class CategProdUI {
    private final StoreModel model;
    public CategProdUI(StoreModel model) {
        this.model = model;
    }
    ...
}
```

A l'exemple que segueix hem optat per la segona opció.

```java
package cat.proven.categprods;

import cat.proven.categprods.model.Category;
import cat.proven.categprods.model.Product;
import cat.proven.categprods.model.StoreModel;
import java.util.List;
import java.util.Scanner;

/**
 * Store application: user interface layer (control logic and view)
 *
 * @author ProvenSoft
 */
public class CategProdUI {

    private final Menu mainMenu;
    private final Scanner uiReader;
    
    private boolean exit;
    
    private final StoreModel model;

    public CategProdUI(StoreModel model) {
        this.model = model;
        mainMenu = new MainMenu();
        uiReader = new Scanner(System.in);
        uiReader.useDelimiter("\n");
    }

    /**
     * application logic entry point
     */
    public void start() {
        exit = false;  //set exit flag to false
        //control loop
        do {
            //display menu and read user's choice
            mainMenu.show();
            String action = mainMenu.getSelectedOptionActionCommand();
            if (action == null) {
                action = "nooption";  //default option if any valid option selected
            }
            //process user's choice: one control method for each functionality
            switch (action) {
                case "exit":  //exit application
                    doExit();
                    break;
                case "category/all":  //list all categories
                    doListAllCategories();
                    break;
                case "category/code":  //list category given its code
                    doListCategoryByCode();
                    break;
                case "category/add":  //add a new category
                    doAddCategory();
                    break;
                case "product/all":  //list all products
                    doListAllProducts();
                    break;
                case "product/add":  //add a new product
                    doAddProduct();
                    break;
                case "product/category":  //list products given their category
                    doListProductsByCategory();
                    break;
                default:  //default option
                    doDefault();
                    break;
            }
        } while (!exit);
    }

    /**
     * Main method
     * @param args argument for command line invocation (not necessari here)
     */
    public static void main(String[] args) {
        //instantiate model (data service)
        StoreModel model = new StoreModel();
        //instantiate presentation class (controller+view) and pass model to it
        CategProdUI ap = new CategProdUI(model);
        //start interacting with user
        ap.start();
    }

    /* ==== Control methods ==== */
    
    /**
     * asks for confirmation and exits application
     */
    public void doExit() {
        boolean confirm = doConfirm("Sure to exit? ");
        if (confirm) {
            exit = true;
        }
    }

    /**
     * process default action
     */
    public void doDefault() {
        //System.out.println("Unknown option!");
        System.out.println("Not implemented yet!");
    }

    /**
     * gets all categories and displays them
     */
    public void doListAllCategories() {
        List<Category> result = model.findAllCategories();
        if (result != null) {
            displayMultiple(result);
        } else {
            doAlert("No data has been obtained");
        }
    }

    /**
     * asks for a category code, gets category with given code and displays it
     */
    public void doListCategoryByCode() {
        String code = doInput("code: ");
        if (code != null) {
            Category result = model.findCategoryByCode(code);
            if (result != null) {
                displaySingle(result);
            } else {
                doAlert("Category not found");
            }
        }
    }


    /**
     * reads from user the data for a new category and adds it to database
     */
    public void doAddCategory() {
        Category cat = doInputCategory();
        if (cat != null) {
            int result = model.addCategory(cat);
            String message = (result == 1) ? "Successfully added" : "Not added";
            doAlert(message);
        } else {
            doAlert("Error validating data");
        }
    }

    /**
     * gets all products and displays them
     */
    public void doListAllProducts() {
        List<Product> result = model.findAllProducts();
        if (result != null) {
            displayMultiple(result);
        } else {
            doAlert("No data has been obtained");
        }        
    } 
    
    
    /**
     * reads from user the data for a new category and adds it to database
     */
    public void doAddProduct() {
        Product prod = doInputProduct();
        if (prod != null) {
            int result = model.addProduct(prod);
            String message = (result == 1) ? "Successfully added" : "Not added";
            doAlert(message);
        } else {
            doAlert("Error validating data");
        }
    }
    
    /**
     * asks for a category id, gets products with given category and displays them
     */
    public void doListProductsByCategory() {
        String sid = doInput("Category id: ");
        try {
            long id = Long.parseLong(sid);
            Category cat = new Category(id);
            List<Product> result = model.findProductsByCategory(cat);
            if (result != null) {
                displayMultiple(result);
            } else {
                doAlert("Error getting data");
            }
        } catch (NumberFormatException ex) {
            doAlert("Invalid input");
        }

    }

    /* ==== View methods ==== */
    
    /**
     * displays a message to user
     * @param message the message to display
     */
    public void doAlert(String message) {
        System.out.println(message);
    }

    /**
     * displays a message and gets ans answer from user
     * @param message the message to display
     * @return user's answer
     */
    public String doInput(String message) {
        System.out.print(message);
        return uiReader.next();
    }

    /**
     * displays a message to user and asks for confirmation
     * @param message the message to display
     * @return true is user confirms action, false otherwiser
     */
    public boolean doConfirm(String message) {
        final char yesAnswer = 'y';
        System.out.print(message);
        char answer = uiReader.next().toLowerCase().charAt(0);
        return (answer == yesAnswer);
    }

    /**
     * displays a single object
     * @param <T> the type of the object
     * @param t the object to display
     */
    public <T> void displaySingle(T t) {
        System.out.println(t);
    }

    /**
     * displays a list of objects
     * @param <T> the type of the object
     * @param data the list to display 
     */
    public <T> void displayMultiple(List<T> data) {
        for (T t : data) { 
            System.out.println(t);
        }
    }

    /**
     * reads from user data for a category
     * @return category object or null in case of error
     */
    public Category doInputCategory() {
        Category c;
        try {
//            String sid = doInput("id: ");
//            long id = Long.parseLong(sid);
            long id = 0;  //id is autoincrement
            String code = doInput("code: ");
            String name = doInput("name: ");
            c = new Category(id, code, name);            
        } catch (NumberFormatException ex) {
            c = null;
        }
        return c;
    }
    
    /**
     * reads from user data for a category
     * @return category object or null in case of error
     */
    public Product doInputProduct() {
        Product p;
        try {
            //get a number formatter for our locale
//            String sid = doInput("id: ");
//            long id = Long.parseLong(sid);
            long id = 0;  //id is autoincrement
            String code = doInput("code: ");
            String name = doInput("name: ");
            String sstock = doInput("stock: " );
            int stock = Integer.parseInt(sstock);
            String sprice = doInput("price: " );
            double price = Double.parseDouble(sprice);
            String scatId = doInput("category id: " );
            long catId = Long.parseLong(scatId);
            Category cat = new Category(catId); 
            p = new Product(id, code, name, stock, price, cat);            
        } catch (NumberFormatException ex) {
            p = null;
        }
        return p;
    }    
    
}
```

[Descàrrega del codi](assets/6.1/categproduct.zip)

Exercici proposat: 

1. Completar totes les funcionalitats possibles de l'aplicació. Assegurar que es proven totes les funcionalitats tenint en compte tots els fluxos alternatius de cada una d'elles, de manera que es controlin adequadament tots els errors.
2. Modificar el codi de manera que el control d'errors es faci mitjançant excepcions.

