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

El que necessita gestionar aquesta empresa és el següent.

Les funcionalitats que cal implementar son altes, baixes, modificacions i consultes de categories, productes i les que gestionen la relació entre categoria i producte

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

## La classe de connexió

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

``` java
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
        if (getClass() != obj.getClass()) {
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

    public int update(Category actualCategory, Category updatedCategory) {
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
            st.setLong(3, actualCategory.getId());
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

## La classe principal de test

``` java
package cat.proven.categprods;

import cat.proven.categprods.model.Category;
import cat.proven.categprods.model.Product;
import cat.proven.categprods.model.persist.CategoryDao;
import cat.proven.categprods.model.persist.ProductDao;
import java.util.List;
import java.util.Scanner;

/**
 * Main class to test Category and Product persistence
 * @author ProvenSoft
 */
public class Tester {

    private final CategoryDao categoryDao;
    private final ProductDao productDao;

    public Tester() {
        categoryDao = new CategoryDao();
        productDao = new ProductDao();        
    }

    public <T> void printList(List<T> data) {
        if (data != null) {
            data.forEach(System.out::println);
        } else {
            System.out.println("Null data");
        }   
    }
 
    public <T> void printSingle(T data) {
        if (data != null) {
            System.out.println(data.toString());
        } else {
            System.out.println("Null data");
        }
    }
    
    public static void main(String[] args) {
        Tester main = new Tester();
        main.runTests();
    }

    private void runTests() {
        testRetrieveAllCategories();
        testRetrieveAllProducts();
        testRetrieveProductsByCategory();
        testRetrieveProductWithCategory();
        testInsertCategory();
        testInsertProduct();
        testUpdateCategory();
    }

    public void testRetrieveAllCategories() {
        //test retrieve all categories
        System.out.println("Retrieve all categories");
        List<Category> allCategories = categoryDao.selectAll();
        printList(allCategories);        
    }

    public void testRetrieveAllProducts() {
        //test retrieve all products
        System.out.println("Retrieve all products");
        List<Product> allProducts = productDao.selectAll();
        printList(allProducts);        
    }

    public void testRetrieveProductsByCategory() {
        //test retrieve products by category
        System.out.println("Retrieve products by category");
        Scanner scan = new Scanner(System.in);
        System.out.println("Input category id: ");
        long categoryId = scan.nextLong();
        List<Product> products = productDao.selectWhereCategory(new Category(categoryId));
        printList(products);
    }
    
    public void testRetrieveProductWithCategory() {
        //test retrieve product with category
        System.out.println("Retrieve product with category");
        Scanner scan = new Scanner(System.in);
        System.out.println("Input product id: ");
        long productId = scan.nextLong();
        Product product = productDao.select(new Product(productId));
        printSingle(product); 
        if (product != null) {
            Category category = categoryDao.select(new Category(product.getId()));
            product.setCategory(category);
            printSingle(product);            
        }
    }
    
    public void testInsertCategory() {
        //test insert a new category
        System.out.println("Insert a new category");
        Scanner scan = new Scanner(System.in);
        System.out.print("Category code: ");
        String code = scan.next();
        System.out.print("Category name: ");
        String name = scan.next();
        Category category = new Category(0, code, name);
        int result = categoryDao.insert(category);
        String resultMsg = (result==1)? "Category saved":"Category not saved";
        System.out.println( resultMsg );
    }

    public void testInsertProduct() {
        //test insert a new product
        System.out.println("Insert a new product");
        Scanner scan = new Scanner(System.in);
        System.out.print("Product code: ");
        String code = scan.next();
        System.out.print("Product name: ");
        String name = scan.next();
        System.out.print("Product stock: ");
        int stock = scan.nextInt();
        System.out.print("Product price: ");
        double price = scan.nextDouble();
        System.out.print("Product category id: ");
        long categoryId = scan.nextLong();
        Product product = new Product(0, code, name, stock, price, new Category(categoryId));
        int result = productDao.insert(product);
        String resultMsg = (result==1)? "Product saved":"Product not saved";
        System.out.println( resultMsg );
    }
    
    public void testUpdateCategory() {
        //test update a category
        System.out.println("Update a category");
        Scanner scan = new Scanner(System.in);
        System.out.print("Category id (of category to be updated): ");
        long id = scan.nextLong();
        Category actualCategory = new Category(id);
        System.out.print("New category code: ");
        String code = scan.next();
        System.out.print("New category name: ");
        String name = scan.next();
        Category updatedCategory = new Category(0, code, name);
        int result = categoryDao.update(actualCategory, updatedCategory);
        String resultMsg = (result==1)? "Category updated":"Category not updated";
        System.out.println( resultMsg );
    }
    
}
```

![Descàrrega del codi explicat fins aquí](/docencia/dam/m06/uf2/nf1/categproduct-sense_model.zip)

Proposta d'exercici: Completar totes les consultes possibles i
implementar els tests.

## Encapsulant els serveis de dades: El model

La lògica de l'aplicació (**capa de control**) i la interacció amb
l'usuari (**vista**) constitueixen, si no estan separades, la **capa de
presentació**.

Cal encapsular els detalls del funcionament de la capa d'accés a dades
per tal que la capa de presentació sigui totalment independent i es
pugui desenvolupar per separat.

Per aquest motiu, introduirem una classe (el **model**) que farà de
servidor de dades per a la resta de l'aplicació.

``` java
package cat.proven.categprods.model;

import cat.proven.categprods.model.persist.CategoryDao;
import cat.proven.categprods.model.persist.ProductDao;
import java.util.List;

/**
 * Model for store application. Provides data services.
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
     * adds a category to data source, 
     * preventing duplicates in unique keys and null values
     * @param category the category to add
     * @return result code: 1 for success, 0 if fail (change as necessary)
     */
    public int addCategory(Category category) {
        int result = 0;
        if (category != null) { //perform proper validations before attempting insertion
            result = categoryDao.insert(category);
        }
        return result;
    }
    
    /**
     * modifies a category in the data source, performing proper validations
     * @param oldC the actual category to update
     * @param newC the new values to update
     * @return result code: 1 for success, 0 if fail (change as necessary)
     */
    public int modifyCategory(Category oldC, Category newC) {
        int result = 0;
        if ( (oldC != null) && (newC != null) ) { //perform proper validations before attempting insertion
            result = categoryDao.update(oldC, newC);
        }
        return result;        
    }

    /**
     * finds all categories in data source
     * @return list with all categories or null in case of error
     */
    public List<Category> findAllCategories() {
        return categoryDao.selectAll();
    }
    
    /**
     * finds a category with the given code
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
     * adds a product to data source, 
     * preventing duplicates in unique keys and null values
     * @param product the category to add
     * @return result code: 1 for success, 0 if fail (change as necessary)
     */
    public int addProduct(Product product) {
        int result = 0;
        if (product != null) { //perform proper validations before attempting insertion
            result = productDao.insert(product);
        }
        return result;
    }    
    
    /**
     * finds all products in data sources 
     * @return list of all products or null in case of error
     */
    public List<Product> findAllProducts() {
        return productDao.selectAll();
    }
    
    /**
     * finds a product with the given code
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
    
    /**
     * finds a product and retrieves all its information,
     * including that corresponding to its category
     * @param product the product to find
     * @return product found or null in case of error
     */
    public Product findProductWithCategory(Product product) {
        Product p = null;
        if (product != null) {
            p = productDao.select(product);
            if (p != null) {
                Category c = categoryDao.select(p.getCategory());
                if (c != null) {
                    p.setCategory(c);
                }
            }
        }
        return p;
    }
}
```

I ara, la classe de test per provar-ne el funcionament serà la següent:

``` java
package cat.proven.categprods;

import cat.proven.categprods.model.Category;
import cat.proven.categprods.model.Product;
import cat.proven.categprods.model.StoreModel;
import java.util.List;
import java.util.Scanner;

/**
 * Main class to test Category and Product persistence
 * @author ProvenSoft
 */
public class Tester2 {

    private final StoreModel model;

    public Tester2() {
        model = new StoreModel();
    }

    public <T> void printList(List<T> data) {
        if (data != null) {
            data.forEach(System.out::println);
        } else {
            System.out.println("Null data");
        }   
    }
 
    public <T> void printSingle(T data) {
        if (data != null) {
            System.out.println(data.toString());
        } else {
            System.out.println("Null data");
        }
    }
    
    public static void main(String[] args) {
        Tester2 main = new Tester2();
        main.runTests();
    }

    private void runTests() {
        testRetrieveAllCategories();
        testRetrieveAllProducts();
        testRetrieveProductsByCategory();
        testRetrieveProductWithCategory();
        testInsertCategory();
        testInsertProduct();
        testUpdateCategory();
    }

    public void testRetrieveAllCategories() {
        //test retrieve all categories
        System.out.println("Retrieve all categories");
        List<Category> allCategories = model.findAllCategories();
        printList(allCategories);        
    }

    public void testRetrieveAllProducts() {
        //test retrieve all products
        System.out.println("Retrieve all products");
        List<Product> allProducts = model.findAllProducts();
        printList(allProducts);        
    }

    public void testRetrieveProductsByCategory() {
        //test retrieve products by category
        System.out.println("Retrieve products by category");
        Scanner scan = new Scanner(System.in);
        System.out.println("Input category id: ");
        long categoryId = scan.nextLong();
        List<Product> products = model.findProductsByCategory(new Category(categoryId));
        printList(products);
    }
    
    public void testRetrieveProductWithCategory() {
        //test retrieve product with category
        System.out.println("Retrieve product with category");
        Scanner scan = new Scanner(System.in);
        System.out.println("Input product id: ");
        long productId = scan.nextLong();
        Product product = model.findProductWithCategory(new Product(productId));
        printSingle(product); 
    }
    
    public void testInsertCategory() {
        //test insert a new category
        System.out.println("Insert a new category");
        Scanner scan = new Scanner(System.in);
        System.out.print("Category code: ");
        String code = scan.next();
        System.out.print("Category name: ");
        String name = scan.next();
        Category category = new Category(0, code, name);
        int result = model.addCategory(category);
        String resultMsg = (result==1)? "Category saved":"Category not saved";
        System.out.println( resultMsg );
    }

    public void testInsertProduct() {
        //test insert a new product
        System.out.println("Insert a new product");
        Scanner scan = new Scanner(System.in);
        System.out.print("Product code: ");
        String code = scan.next();
        System.out.print("Product name: ");
        String name = scan.next();
        System.out.print("Product stock: ");
        int stock = scan.nextInt();
        System.out.print("Product price: ");
        double price = scan.nextDouble();
        System.out.print("Product category id: ");
        long categoryId = scan.nextLong();
        Product product = new Product(0, code, name, stock, price, new Category(categoryId));
        int result = model.addProduct(product);
        String resultMsg = (result==1)? "Product saved":"Product not saved";
        System.out.println( resultMsg );
    }
    
    public void testUpdateCategory() {
        //test update a category
        System.out.println("Update a category");
        Scanner scan = new Scanner(System.in);
        System.out.print("Category id (of category to be updated): ");
        long id = scan.nextLong();
        Category actualCategory = new Category(id);
        System.out.print("New category code: ");
        String code = scan.next();
        System.out.print("New category name: ");
        String name = scan.next();
        Category updatedCategory = new Category(0, code, name);
        int result = model.modifyCategory(actualCategory, updatedCategory);
        String resultMsg = (result==1)? "Category updated":"Category not updated";
        System.out.println( resultMsg );
    }
    
}
```

Exercici: 

1. Completar totes les consultes possibles i implementar els tests.
2. Substituir la classe principal de test per una classe principal que implementi la interacció amb l'usuari a través d'un menú que li permeti executar totes les funcionalitats de l'aplicació.

