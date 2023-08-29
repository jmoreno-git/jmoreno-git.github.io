# ORM Categories i productes amb relació mxn

L'exemple que anem a desenvolupar implementa la persistència de dos tipus d'objectes: categories i productes.

La relació entre els dos és mxn, és a dir, una categoria pot tenir molts productes, i un producte pot pertènyer a moltes categories.

## La base de dades

**storedb2.sql**
```sql
CREATE USER storeusr@localhost IDENTIFIED BY 'storepsw';

CREATE DATABASE storedb2
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;

GRANT ALL PRIVILEGES ON storedb2.* TO storeusr@localhost;

USE storedb2;

CREATE TABLE categories (
    code VARCHAR(10) NOT NULL,
    name VARCHAR(40) NOT NULL,
    PRIMARY KEY (code)
) ENGINE=InnoDB;

CREATE TABLE products (
    id INT(4) NOT NULL AUTO_INCREMENT,
    code VARCHAR(10) NOT NULL UNIQUE,
    description VARCHAR(40) NOT NULL,
    price DOUBLE DEFAULT 0.0,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE categoryproduct (
    category_code VARCHAR(10) NOT NULL,
    product_id INT(4) NOT NULL,
    PRIMARY KEY (category_code, product_id)
) ENGINE=InnoDB;

ALTER TABLE categoryproduct ADD FOREIGN KEY (category_code) REFERENCES categories(code) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE categoryproduct ADD FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE ON UPDATE CASCADE;

INSERT INTO categories (code, name) VALUES
    ('c01', 'c01_desc'),
    ('c02', 'c02_desc'),
    ('c03', 'c03_desc'),
    ('c04', 'c04_desc'),
    ('c05', 'c05_desc'),
    ('c06', 'c06_desc');

INSERT INTO products (code, description, price) VALUES 
    ('a01', 'a01_desc', 101),
    ('a02', 'a02_desc', 102),
    ('a03', 'a03_desc', 103),
    ('a04', 'a04_desc', 104),
    ('a05', 'a05_desc', 105),
    ('a06', 'a06_desc', 106),
    ('a07', 'a07_desc', 107),
    ('a08', 'a08_desc', 108),
    ('a09', 'a09_desc', 109),
    ('a10', 'a10_desc', 110);

INSERT INTO categoryproduct (category_code, product_id)  VALUES 
    ('c01', 1),
    ('c01', 2),
    ('c01', 3),
    ('c02', 1),
    ('c02', 4),
    ('c03', 3),
    ('c04', 3),
    ('c04', 4);
```

## Creació del projecte

Crear un projecte ordinari de Java.

Afegir al projecte una unitat de persistència. En aquest moment cal afegir el servidor de bases de dades, seleccionar-ne el driver i definir-ne les propietats. 

**persistence.xml**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.1" xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
  <persistence-unit name="StoreJPAmxnPU" transaction-type="RESOURCE_LOCAL">
    <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
    <class>cat.proven.storejpamxn.model.Product</class>
    <class>cat.proven.storejpamxn.model.Category</class>
    <properties>
      <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/storedb2?zeroDateTimeBehavior=CONVERT_TO_NULL"/>
      <property name="javax.persistence.jdbc.user" value="storeusr"/>
      <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
      <property name="javax.persistence.jdbc.password" value="storepsw"/>
      <property name="eclipselink.logging.level" value="WARNING"/>
      <property name="eclipselink.logging.file" value="resources/log.txt"/>
    </properties>
  </persistence-unit>
</persistence>
```

## Les classes del model

**Category.java**
```java
@Entity
@Table(name = "categories")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
    @NamedQuery(name = "Category.findByCode", query = "SELECT c FROM Category c WHERE c.code = :code"),
    @NamedQuery(name = "Category.findByName", query = "SELECT c FROM Category c WHERE c.name = :name")})
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @JoinTable(name = "categoryproduct", joinColumns = {
        @JoinColumn(name = "category_code", referencedColumnName = "code")}, inverseJoinColumns = {
        @JoinColumn(name = "product_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Product> products;
// ...
}
```

**Product.java**
```java
@Entity
@Table(name = "products")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
    @NamedQuery(name = "Product.findByCode", query = "SELECT p FROM Product p WHERE p.code = :code"),
    @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description"),
    @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @ManyToMany(mappedBy = "products")
    private List<Category> categories;
// ...
}
```

## El servidor de dades del model

**StoreModel.java**
```java
/**
 *
 * @author ProvenSoft
 */
public class StoreModel {
    
    /**
     * EntityManagerFactory to create EntityManager
     */
    private final EntityManagerFactory emFactory;
    
    public StoreModel() {
        this.emFactory = Persistence.createEntityManagerFactory("StoreJPAPU");
    }
    
    /**
     * creates entity manager
     *
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }
    
    //TODO: add here methods to implement persistence with categories and products
}
```

## Consultes de cerca d'objectes

Consultes de cerca de categories:

```java
/**
 * finds a categories by code
 *
 * @param code the code to find
 * @return categories found or null if not found
 */
public Category findCategoryByCode(String code) {
    EntityManager em = getEntityManager();
    Category category = null;
    try {
        category = em.find(Category.class, code);  //returns null if not found.
    } finally {
        em.close();
    }
    return category;
}

/**
 * finds all categories
 *
 * @return list of all categories or null in case of error
 */
public List<Category> findAllCategories() {
    EntityManager em = getEntityManager();
    List<Category> categories = null;
    try {
        TypedQuery<Category> query = 
                em.createNamedQuery("Category.findAll", Category.class);
        categories = query.getResultList();
    } finally {
        em.close();
    }
    return categories;
}  
```

Consultes de cerca de productes:

```java
/**
 * finds a product by id
 *
 * @param id the id to find
 * @return product found or null if not found
 */
public Product findProductById(Integer id) {
    EntityManager em = getEntityManager();
    Product product = null;
    try {
        product = em.find(Product.class, id);  //returns null if not found.
    } finally {
        em.close();
    }
    return product;
}

/**
 * finds all products
 *
 * @return list of all products or null in case of error
 */
public List<Product> findAllProducts() {
    EntityManager em = getEntityManager();
    List<Product> products = null;
    try {
        TypedQuery<Product> query = 
                em.createNamedQuery("Product.findAll", Product.class);
        products = query.getResultList();
    } finally {
        em.close();
    }
    return products;
}
```

Com a exercici, completar amb els mètodes per fer cerques per altres atributs

## Inserir objectes

Inserir categoria:

```java
/**
 * inserts a categories
 *
 * @param category the categories to insert
 * @return number of categories persisted
 * @throws PreexistingEntityException if categories already exists
 */
public int addCategory(Category category) throws PreexistingEntityException {
    int result = 0;
    if (category.getProducts() == null) {
        category.setProducts(new ArrayList<>());
    }
    EntityManager em = null;
    try {
        em = getEntityManager();
        em.getTransaction().begin();
        //get attached products
        List<Product> attachedProducts = new ArrayList<>();
        //attach categories products in persistence context.
        for (Product p : category.getProducts()) {
            //get product instance
            p = em.getReference(p.getClass(), p.getId());
            //add to list of attached products
            attachedProducts.add(p);
        }
        category.setProducts(attachedProducts);
        //persist categories
        em.persist(category);
        //change categories of related products
        for (Product p : category.getProducts()) {
            //add categories to each product
            p.getCategories().add(category);
            //merge
            p = em.merge(p);
        }
        em.getTransaction().commit();
        result = 1;
    } catch (Exception ex) {
        if (findCategoryByCode(category.getCode()) != null) {
            throw new PreexistingEntityException("Category " + category + " already exists.");
        }
        throw ex;
    } finally {
        if (em != null) {
            em.close();
        }
    }
    return result;
}
```

Inserir producte:

```java
/**
 * inserts a product
 *
 * @param product the product to insert
 * @return number of products inserted
 * @throws PreexistingEntityException if product already exists
 * @throws EntityNotFoundException if related categories does not exist
 */
public int addProduct(Product product) throws PreexistingEntityException, EntityNotFoundException {
    int result = 0;
    if (product.getCategories() == null) {
        product.setCategories(new ArrayList<>());
    }
    EntityManager em = null;
    try {
        em = getEntityManager();
        em.getTransaction().begin();
        //get attached categories
        List<Category> attachedCategories = new ArrayList<>();
        for (Category c: product.getCategories()) {
            c = em.getReference(c.getClass(), c.getCode());
            attachedCategories.add(c);
        }
        //persist product
        em.persist(product);
        //add product to each of categories in the list of the product categories
        for (Category c: product.getCategories()) {
            c.getProducts().add(product);
            c = em.merge(c);
        }
        em.getTransaction().commit();
        result = 1;
    } catch (Exception ex) {
        if (findProductById(product.getId()) != null) {
            throw new PreexistingEntityException("Product " + product + " already exists.");
        }
        throw ex;
    } finally {
        if (em != null) {
            em.close();
        }
    }
    return result;
}
```

## Modificar objectes

Modificar categoria:

```java
/**
 * updates a categories
 *
 * @param category the categories to update
 * @return number of categories updated
 * @throws NonexistentEntityException if categories does not exist
 * @throws IllegalOrphanException
 */
public int modifyCategory(Category category) throws NonexistentEntityException, IllegalOrphanException {
    int result = 0;
    EntityManager em = null;
    try {
        em = getEntityManager();
        em.getTransaction().begin();
        Category persistentCategory = em.find(Category.class, category.getCode());
        if (persistentCategory != null) {
            //get old list of products
            List<Product> oldProducts = persistentCategory.getProducts();
            //get new list of products
            List<Product> products = category.getProducts();
            //add new products to list of products of categories
            List<Product> attachedProducts = new ArrayList<>();
            //get instances of products
            for (Product p : products) {
                p = em.getReference(p.getClass(), p.getId());
                attachedProducts.add(p);
            }
            products = attachedProducts;
            category.setProducts(products);
            //merge categories
            category = em.merge(category);
            //remove categories in products that are not in the new list
            for (Product p: oldProducts) {
                if (!products.contains(p)) {
                    p.getCategories().remove(category);
                    p = em.merge(p);
                }  
            }
            //add categories in products that are new to the list
            for (Product p : products) {
                if (!oldProducts.contains(p)) {
                    p.getCategories().add(category);
                    p = em.merge(p);
                }
            }
            em.getTransaction().commit();
            result = 1;
        } else {
            throw new NonexistentEntityException("Category with code " + category.getCode() + " does not exist.");
        }
    } finally {
        if (em != null) {
            em.close();
        }
    }
    return result;
}
```

Modificar producte:

```java
/**
 * updates a product
 *
 * @param product the product to update
 * @return number of products updated
 * @throws NonexistentEntityException if product does not exist
 */
public int modifyProduct(Product product) throws NonexistentEntityException {
    int result = 0;
    EntityManager em = null;
    try {
        em = getEntityManager();
        em.getTransaction().begin();
        //get product into persistence context
        Product persistentProduct = em.find(Product.class, product.getId());
        if (persistentProduct != null) {
            //get old list of categories
            List<Category> oldCategories = persistentProduct.getCategories();
            //get new list of categories
            List<Category> categories = product.getCategories();
            //get attached categories
            List<Category> attachedCategories = new ArrayList<>();
            for (Category c: categories) {
                //get attached categories
                c = em.getReference(c.getClass(), c.getCode());
                attachedCategories.add(c);
            }
            categories = attachedCategories;
            product.setCategories(categories);
            //persist product
            em.merge(product);
            //remove product from list of products of old categories
            for (Category c: oldCategories) {
                c.getProducts().remove(product);
                c = em.merge(c);                    
            }
            //add product to list of product of new categories
            for (Category c: categories) {
                c.getProducts().add(product);
                c = em.merge(c);
            }
            em.getTransaction().commit();
            result = 1;
        } else {
            throw new NonexistentEntityException("Product with id " + product.getId() + " does not exist.");
        }
    } finally {
        if (em != null) {
            em.close();
        }
    }
    return result;
}
```

## Esborrar objectes

Esborrar categoria:

```java
/**
 * removes a categories
 *
 * @param category the categories to remove
 * @return number of categories removed
 * @throws NonexistentEntityException if categories does not exist
 */
public int removeCategory(Category category) throws NonexistentEntityException {
    int result = 0;
    EntityManager em = null;
    try {
        em = getEntityManager();
        em.getTransaction().begin();
        try {
            category = em.getReference(Category.class, category.getCode());
            category.getCode();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("Category with code " + category.getCode() + " does not exist.");
        }
        //ckeck if categories has products related to it.
        List<Product> products = category.getProducts();
        //for each product, remove categories from its list of categories
        for (Product p: products) {
            p.getCategories().remove(category);
            p = em.merge(p);
        }
        //remove categories
        em.remove(category);
        em.getTransaction().commit();
        result = 1;
    } finally {
        if (em != null) {
            em.close();
        }
    }
    return result;
}
```

Esborrar producte:

```java
/**
 * removes a product
 *
 * @param product the product to remove
 * @return numer of products removed
 * @throws NonexistentEntityException if product does not exist
 */
public int removeProduct(Product product) throws NonexistentEntityException {
    int result = 0;
    EntityManager em = null;
    try {
        em = getEntityManager();
        em.getTransaction().begin();
        try {
            product = em.getReference(product.getClass(), product.getId());
            product.getId();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("Product with id " + product.getId() + " does not exist.");
        }
        //get categories
        List<Category> categories = product.getCategories();
        //remove product from related categories
        for (Category c: product.getCategories()) {
            //remove product from list of products of related categories
            c.getProducts().remove(product);
            //update categories
            c = em.merge(c);
        }
        //remove product
        em.remove(product);
        em.getTransaction().commit();
        result = 1;
    } finally {
        if (em != null) {
            em.close();
        }
    }
    return result;
}
```
