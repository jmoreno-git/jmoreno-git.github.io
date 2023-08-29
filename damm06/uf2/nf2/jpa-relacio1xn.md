# ORM Categories i productes amb relació 1xn

L'exemple que anem a desenvolupar implementa la persistència de dos tipus d'objectes: categories i productes.

La relació entre els dos és 1xn, és a dir, una categoria pot tenir molts productes, però cada producte pertany a una única categoria.

## La base de dades

Crear la base de dades, l'usuari per a l'aplicació i assignar els permisos. Després crear les taules i inserir les dades de test.

Cal definir una clau primària per a cada entitat i definir la clau forana de la relació 1xn i la seva restricció d'integritat.

**sql storedb.sql**
```sql
CREATE USER storeusr@localhost IDENTIFIED BY 'storepsw';
 
CREATE DATABASE storedb
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;
 
GRANT ALL PRIVILEGES ON storedb.* TO storeusr@localhost;
 
USE storedb;
 
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
    category_code VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

ALTER TABLE products ADD FOREIGN KEY (category_code) REFERENCES categories(code) ON DELETE NO ACTION ON UPDATE CASCADE;

INSERT INTO categories (code, name) VALUES
    ('c01', 'c01_desc'),
    ('c02', 'c02_desc'),
    ('c03', 'c03_desc'),
    ('c04', 'c04_desc'),
    ('c05', 'c05_desc'),
    ('c06', 'c06_desc');
    
INSERT INTO products (code, description, price, category_code) VALUES 
    ('a01', 'a01_desc', 101, 'c01'),
    ('a02', 'a02_desc', 102, 'c01'),
    ('a03', 'a03_desc', 103, 'c01'),
    ('a04', 'a04_desc', 104, 'c02'),
    ('a05', 'a05_desc', 105, 'c02'),
    ('a06', 'a06_desc', 106, 'c03'),
    ('a07', 'a07_desc', 107, 'c04'),
    ('a08', 'a08_desc', 108, 'c04'),
    ('a09', 'a09_desc', 109, 'c05'),
    ('a10', 'a10_desc', 110, 'c05');
```

## Creació del projecte 

Crear un projecte ordinari de Java. 

Afegir al projecte una unitat de persistència. En aquest moment cal afegir el servidor de bases de dades, seleccionar-ne el driver i definir-ne les propietats.

**persistence.xml**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.1" xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
  <persistence-unit name="StoreJPAPU" transaction-type="RESOURCE_LOCAL">
    <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
    <class>cat.proven.storejpa.model.Category</class>
    <class>cat.proven.storejpa.model.Product</class>
    <properties>
      <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/storedb?zeroDateTimeBehavior=CONVERT_TO_NULL"/>
      <property name="javax.persistence.jdbc.user" value="storeusr"/>
      <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
      <property name="javax.persistence.jdbc.password" value="storepsw"/>
      <property name="eclipselink.logging.level" value="WARNING"/>
    </properties>
  </persistence-unit>
</persistence>
```

Convé utilitzar un usuari específic per a l'aplicació i amb els permisos estrictament necessaris.

També és convenient establir la propietat *logging.level* al valor desitjat per no tenir massa missatges informatius de JPA a la consola. A l'exemple, s'ha establert que no es mostrin missatges amb severitat menor que *warning*.

## Les classes del model

Cal crear les classes del model de l'aplicació. L'assistent ens permet generar-les automàticament a partir de les taules de la base de dades, juntament amb les annotacions que defineixen el mapeig objecte-relacional (ORM) sobre l'API JPA.

Després de petites modificacions (no imprescindibles per al funcionament), s'obté aquest codi:

**Category.java**
```java
package cat.proven.storejpa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ProvenSoft
 */
@Entity
@Table(name = "categories")
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
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "category")
    private List<Product> products;

    public Category() {
        this.products = new ArrayList<>();
    }

    public Category(String code) {
        this.code = code;
        this.products = new ArrayList<>();
    }

    public Category(String code, String name) {
        this.code = code;
        this.name = name;
        this.products = new ArrayList<>();
    }

    public Category(Category other) {
        this.code = other.code;
        this.name = other.name;
        this.products = other.products;        
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category{code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", products=");
        sb.append("[");
        for (Product p: products) {
            sb.append("Product{");
            sb.append("id="+p.getId());
            sb.append("}");
        }
        sb.append("]");
        sb.append('}');
        return sb.toString();
    }

}
```

**Product.java**
```java
package cat.proven.storejpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ProvenSoft
 */
@Entity
@Table(name = "products")
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
    @Column(name = "code", unique = true, nullable = false)
    private String code;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @JoinColumn(name = "category_code", referencedColumnName = "code")
    @ManyToOne(optional = false)
    private Category category;

    public Product() {
    }

    public Product(Integer id) {
        this.id = id;
    }

    public Product(Integer id, String code, String description, Double price, Category category) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.category = category;
    }    
    
    public Product(Integer id, String code, String description, Double price) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
    }

    public Product(Product other) {
        this.id = other.id;
        this.code = other.code;
        this.description = other.description;
        this.price = other.price;
        this.category = other.category;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product{id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", description=").append(description);
        sb.append(", price=").append(price);
        sb.append(", category=").append(category.getCode());
        sb.append('}');
        return sb.toString();
    }

}
```

Les consultes es realitzen utilitzant el llenguatge ***JPQL***. Tot i que es poden crear les consultes de forma específica en el moment d'executar-les en cada cas, és bona idea definir consultes amb nom (***NamedQuery***) com les que ens proposa el framework.

## El servidor de dades del model

Crear la classe que servirà peticions de dades sobre la capa de persistència.

La classe té com a atribut un objecte ***EntityManagerFactory*** per generar instàncies de ***EntityManager***. S'ha escollir l'opció de generar un objecte ***EntityManager*** a cada mètode. Aquest objecte és el que permet realitzar les operacions de persistència.

Per defecte (i amb caràcter general és convenient fer-ho així), el framework treballa amb persistència tardana (*LAZY*) en comptes de propera (*EAGER*). Això implica que els objectes relacionats no es consulten a la base de dades fins el moment en què és necessari. Així, llegir un producte no proveeix totes les dades de la categoria a la qual pertany, sinó només una referència per accedir-hi específicament quan calgui. Per assegurar la consistència i integritat de les dades, totes les operacions de persistència es realitzen dintre de transaccions. Quan es modifica una entitat, cal també propagar els canvis a les entitats relacionades.

Abans de fer operacions sobre un objecte, pot ser convenient treure'l del context de persistència (***detach***) i introduir-lo novament un cop acabades les operacions (***merge***).

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
    /**
     * flag to treat properly cascade restrictions
     */
    private final boolean IS_PRODUCT_CATEGORY_NULLABLE;

    public StoreModel() {
        this.IS_PRODUCT_CATEGORY_NULLABLE = false;
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

A la classe anterior cal afegir-li els mètodes per implementar la persistència de categories i productes.

Cal definir les classes de les excepcions específiques, extenent la classe ***Exception***.

A continuació es mostren exemples de com haurien de ser els mètodes per fer cerques i persistència dels objectes, tenint en compte les seves relacions. Cal parar especial atenció al tractament diferenciat al costat 1 i al costat n de la relació.

## Consultes de cerca d'objectes 

El mètode //EntityManager.find()// proporciona objectes de la classe especificada, donada la seva clau primària.

Altres consultes es poden fer amb la classe //Query//.

Consultes de cerca de categories:

```java
    /**
     * finds a category by code
     *
     * @param code the code to find
     * @return category found or null if not found
     */
    public Category searchCategoryByCode(String code) {
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
    public List<Category> searchAllCategories() {
        EntityManager em = getEntityManager();
        List<Category> categories = null;
        try {
            TypedQuery<Category> query = em.createNamedQuery("Category.findAll",Category.class);
            categories = query.getResultList();
        } finally {
            em.close();
        }
        return categories;
    }
    
    
     /**
     * Finds all categories with name 
     * @param name to search
     * @return list of categories with name. If no category is found,
     * it returns and empty list.
     */
    public List<Category>searchCategoriesByName (String name){
        List<Category> result= new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Category> query = em.createNamedQuery("Category.findByName", Category.class);
            query.setParameter("name", name);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
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
    public Product searchProductById(Integer id) {
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
    public List<Product> searchAllProducts() {
        EntityManager em = getEntityManager();
        List<Product> products = null;
        try {
            TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
            products = query.getResultList();
        } finally {
            em.close();
        }
        return products;
    }
```

Com a exercici, completar amb els mètodes per fer cerques per altres atributs.

## Inserir objectes

La inserció d'objectes es fa amb el mètode ***EntityManager.persist()***.

Mètode per inserir categories:

```java
/**
 * inserts a category
 *
 * @param category the category to insert
 * @return number of categories persisted
 * @throws PreexistingEntityException if category already exists
 */
public int addCategory(Category category) throws PreexistingEntityException {
    int result = 0;
    if (category.getProducts() == null) {
        category.setProducts(new ArrayList<Product>());
    }
    EntityManager em = getEntityManager();
    try {
        em.getTransaction().begin();
        //get attached products
        List<Product> attachedProducts = new ArrayList<>();
        //attach category products in persistence context.
        for (Product p : category.getProducts()) {
            //get product instance
            p = em.getReference(p.getClass(), p.getId());
            //add to list of attached products
            attachedProducts.add(p);
        }
        category.setProducts(attachedProducts);
        //persist category
        em.persist(category);
        //change category of related products
        for (Product p : category.getProducts()) {
            //save old value for category
            Category oldCategory = p.getCategory();
            //change product to new category
            p.setCategory(category);
            //merge
            p = em.merge(p);
            if (oldCategory != null) {
                //remove product from old category
                oldCategory.getProducts().remove(p);
                //update old category
                oldCategory = em.merge(oldCategory);
            }
        }
        em.getTransaction().commit();
        result = 1;
    } catch (Exception ex) {
        if (searchCategoryByCode(category.getCode()) != null) {
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

Mètode per inserir productes:

```java
/**
 * inserts a product
 *
 * @param product the product to insert
 * @return number of products inserted
 * @throws PreexistingEntityException if product already exists
 * @throws EntityNotFoundException if related category does not exist
 */
public int addProduct(Product product) throws PreexistingEntityException, EntityNotFoundException {
    int result = 0;
    if (product.getCategory() == null) {
        product.setCategory(new Category());
    }
    EntityManager em = getEntityManager();
    try {
        em.getTransaction().begin();
        //get attached category
        Category category = product.getCategory();
        if (category != null) {
            category = em.getReference(category.getClass(), category.getCode());
            product.setCategory(category);
        }
        //update category (add product to their list of products)
        if (category != null) {
            category.getProducts().add(product);
            category = em.merge(category);
        }
        //persist product
        em.persist(product);
        em.getTransaction().commit();
        result = 1;
    } catch (Exception ex) {
        if (searchProductById(product.getId()) != null) {
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

Cal fixar-se en la manera com es fan persistents els objectes mantenint les seves relacions.

## Modificar objectes

Els objectes es mantenen sincronitzats amb la base de dades mentre es troben dintre del context de persistència.

Per introduir un objecte al context de persistència cal usar el mètode ***EntityManager.merge()***.

Amb el mètode ***EntityManager.detach()*** poden treure un objecte del context de persistència.

Els objectes de la base de dades (obtinguts a través de consultes) queden dintre del context de persistència.

Modificar categories:

```java
/**
 * updates a category
 *
 * @param category the category to update
 * @return number of categories updated
 * @throws NonexistentEntityException if category does not exist
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
            //remove old products from category
            List<String> illegalOrphanMessages = null;
            for (Product p : oldProducts) {
                if (!products.contains(p)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Product " + p + " since its category field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            //add new products to list of products of category
            List<Product> attachedProducts = new ArrayList<Product>();
            //get instances of products
            for (Product p : products) {
                p = em.getReference(p.getClass(), p.getId());
                attachedProducts.add(p);
            }
            products = attachedProducts;
            category.setProducts(products);
            //merge category
            category = em.merge(category);
            //update category in products
            for (Product p : products) {
                if (!oldProducts.contains(p)) {
                    //assign category to products that are new in the list
                    Category oldCategory = p.getCategory();
                    p.setCategory(category);
                    p = em.merge(p);
                    //remove product from category list where it was previously assigned
                    if (oldCategory != null && !oldCategory.equals(category)) {
                        oldCategory.getProducts().remove(p);
                        oldCategory = em.merge(oldCategory);
                    }
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

Modificar productes:

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
            //get old category
            Category oldCategory = persistentProduct.getCategory();
            //get new category
            Category category = product.getCategory();
            if (category != null) {
                //get attached category
                category = em.getReference(category.getClass(), category.getCode());
                product.setCategory(category);
            }
            //remove product from list of products of old category
            if (oldCategory != null && !oldCategory.equals(category)) {
                oldCategory.getProducts().remove(product);
                oldCategory = em.merge(oldCategory);
            }
            //add product to list of product of new category
            if (category != null && !category.equals(oldCategory)) {
                category.getProducts().add(product);
                category = em.merge(category);
            }
            //persist product
            em.merge(product);
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

Esborrar categories:

```java
/**
 * removes a category
 *
 * @param category the category to remove
 * @return number of categories removed
 * @throws NonexistentEntityException if category does not exist
 * @throws IllegalOrphanException if category has orphan entities that
 * cannot be null
 */
public int removeCategory(Category category) throws NonexistentEntityException, IllegalOrphanException {
    int result = 0;
    EntityManager em = null;
    try {
        em = getEntityManager();
        em.getTransaction().begin();
        try {
            category = em.getReference(Category.class, category.getCode());
            
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("Category with code " + category.getCode() + " does not exist.");
        }
        //ckeck if category has products related to it.
        List<String> illegalOrphanMessages = null; //error messages
        //get list of products of category
        List<Product> products = category.getProducts();
        for (Product p : products) {
            if (IS_PRODUCT_CATEGORY_NULLABLE) { //set category of related products to null
                p.setCategory(null);
                em.merge(p);
            } else { //if category of product cannot be null->throw exception
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This Category (" + category + ") cannot be destroyed since the Product " + p + " in its products field has a non-nullable category field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        //remove category
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

Esborrar productes:

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
        //get category
        Category category = product.getCategory();
        if (category != null) {
            //remove product from list of products of related category
            category.getProducts().remove(product);
            //update category
            category = em.merge(category);
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

## Les excepcions

```java
public class IllegalOrphanException extends Exception {
    private List<String> messages;
    public IllegalOrphanException(List<String> messages) {
        super((messages != null && messages.size() > 0 ? messages.get(0) : null));
        if (messages == null) {
            this.messages = new ArrayList<String>();
        }
        else {
            this.messages = messages;
        }
    }
    public List<String> getMessages() {
        return messages;
    }
}
```

```java
public class NonexistentEntityException extends Exception {
    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public NonexistentEntityException(String message) {
        super(message);
    }
}
```

```java
public class PreexistingEntityException extends Exception {
    public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public PreexistingEntityException(String message) {
        super(message);
    }
}
```


## El programa principal per provar la persistència

Escrivim un programa principal que executi diversos tests per provar les diferents funcionalitats i provar el correcte funcionament de la persistència i la solidesa del mapeig ORM que hem definit.

**java StoreJPATester.java**
```java
/**
 *
 * @author ProvenSoft
 */
public class StoreJPATester {

    private StoreModel model;
    private Scanner scan;

    public static void main(String[] args) {
        StoreJPATester ap = new StoreJPATester();
        ap.run();
    }

    private void run() {
        scan = new Scanner(System.in);
        model = new StoreModel();
        //*** Test categories
        //
        testSearchCategory();
        //
        testSearchAllCategories();
        //
        testSearchCategoriesByName();
        //
        testAddCategory();
        //
        testSearchAllCategories();
        //
        testModifyCategory();
        //
        testSearchAllCategories();
        //
        testRemoveCategory();
        //
        testSearchAllCategories();
        //
        //*** Test products
        //
        testSearchProduct();
        //
        testSearchAllProducts();
        //
        testAddProduct();
        //
        testSearchAllProducts();
        //
        testModifyProduct();
        //
        testSearchAllProducts();
        //
        testRemoveProduct();
        //
        testSearchAllProducts();
    }

    public void showOne(Object obj) {
        if (obj != null) {
            System.out.println(obj.toString());
        } else {
            System.out.println("Null object");
        }
    }

    public void showList(List<?> lst) {
        if (lst != null) {
            lst.forEach(o -> {
                System.out.println(o.toString());
            });
            System.out.println("Number of elements: " + lst.size());
        } else {
            System.out.println("Null data");
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scan.next();
    }

    /**
     * ==== Tests related to Category
     */
    private void testSearchCategory() {
        System.out.println("===Test: Search category by code===");
        String code = readString("Input category code to search: ");
        Category category = model.searchCategoryByCode(code);
        if (category != null) {
            model.fetchCategoryProducts(category);
            showOne(category);
        } else {
            System.out.println("Code not found: " + code);
        }
    }

    private void testSearchAllCategories() {
        System.out.println("===Test: Search all categories===");
        List<Category> categories = model.searchAllCategories();
        categories.forEach(c -> {
            model.fetchCategoryProducts(c);
        });
        showList(categories);
    }
    
    private void testSearchCategoriesByName() {
        System.out.println("===Test: Search catagory by name===");
        String name = readString("Input category name to search: ");
        List<Category> categories = model.searchCategoriesByName(name);
        if (categories != null) {
         
           this.showList(categories);
             
        } else {
            System.out.println("Error searching categories");
        } 
    }

    private void testAddCategory() {
        System.out.println("===Test: Add category===");
        //read data
        String code = readString("Category code: ");
        String description = readString("Category description: ");
        Category categoryNew = new Category(code, description);
        List<Product> productList = new ArrayList<>();
        String sNumP = readString("Number of products: ");
        //persist
        try {
            int numP = Integer.parseInt(sNumP);
            for (int i=0; i<numP; i++) {
                int productId = Integer.parseInt(readString("Product id: "));
                productList.add(new Product(productId));
            }
            categoryNew.setProducts(productList);
            int result = model.addCategory(categoryNew);
            System.out.println("Result:" + result);
        } catch (PreexistingEntityException ex) {
            System.out.println(ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void testRemoveCategory() {
        System.out.println("===Test: Remove category===");
        String code = readString("Input category code to remove: ");
        Category category = new Category(code);
        try {
            int result = model.removeCategory(category);
            System.out.println("Result:" + result);
        } catch (NonexistentEntityException ex) {
            System.out.println(ex.getMessage());
        } catch (IllegalOrphanException ex) {
            System.out.println(ex.getMessages());
        }
    }

    private void testModifyCategory() {
        System.out.println("===Test: Modify category===");
        String code = readString("Input category code to modify: ");
        Category category = model.searchCategoryByCode(code);
        if (category != null) {
           //read data
           String description = readString("Category description: ");
           Category categoryNew = new Category(code, description);
           List<Product> productList = new ArrayList<>();
           String sNumP = readString("Number of products: ");
           try {
               int numP = Integer.parseInt(sNumP);
               for (int i=0; i<numP; i++) {
                   int productId = Integer.parseInt(readString("Product id: "));
                   productList.add(new Product(productId));
               }
               categoryNew.setProducts(productList);
               //merge
               int result = model.modifyCategory(categoryNew);
               System.out.println("Result:" + result);
           } catch (NonexistentEntityException ex) {
               System.out.println(ex.getMessage());
           } catch (IllegalOrphanException ex) {
               System.out.println(ex.getMessages());
           } catch (NumberFormatException ex) {
               System.out.println(ex.getMessage());
           }       
        } else {
            System.out.println("Category with code="+code+" not found");
        } 

    }

    /**
     * ====Tests related to Product
     */
    private void testSearchProduct() {
        System.out.println("===Test: Search product by id===");
        String sid = readString("Input product id to search: ");
        try {
            Integer id = Integer.parseInt(sid);
            Product product = model.searchProductById(id);
            if (product != null) {
                showOne(product);
            } else {
                System.out.println("Id not found: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid data" + e.getMessage());
        }
    }

    private void testSearchAllProducts() {
        System.out.println("===Test: Search all products===");
        List<Product> products = model.searchAllProducts();
        showList(products);
    }

    private void testAddProduct() {
        System.out.println("===Test: Add product===");
        //read data
        //String sid = readString("Product id: ");  //PK
        String code = readString("Product code: ");
        String description = readString("Product description: ");
        String sprice = readString("Product price: ");
        String catCode = readString("Product category code: ");
        try {
            //Integer id = Integer.parseInt(sid);   //PK
            Double price = Double.parseDouble(sprice);
            Product product = new Product(0, code, description, price);
            product.setCategory(new Category(catCode));
            //persist
            try {
                int result = model.addProduct(product);
                System.out.println("Result:" + result);
            } catch (PreexistingEntityException | EntityNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid data" + e.getMessage());
        }
    }

    private void testRemoveProduct() {
        System.out.println("===Test: Remove product===");
        String sid = readString("Input product id to remove: ");
        try {
            Integer id = Integer.parseInt(sid);
            Product product = new Product(id);
            try {
                int result = model.removeProduct(product);
                System.out.println("Result:" + result);
            } catch (NonexistentEntityException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid data" + e.getMessage());
        }

    }

    private void testModifyProduct() {
        System.out.println("===Test: Modify product===");
        //read data
        String sid = readString("Product id: ");
        try {
            Integer id = Integer.parseInt(sid);
            Product product = model.searchProductById(id);
            String code = readString("Product code: ");
            String description = readString("Product description: ");
            String sprice = readString("Product price: ");
            String catCode = readString("Product category code: ");
            Double price = Double.parseDouble(sprice);
            Product productNew = new Product(id, code, description, price);
            productNew.setCategory(new Category(catCode));
            try {
                //merge
                int result = model.modifyProduct(productNew);
                System.out.println("Result:" + result);
            } catch (NonexistentEntityException | EntityNotFoundException ex) {
                System.out.println(ex.getMessage());
            } 
        } catch (NumberFormatException e) {
            System.out.println("Invalid data" + e.getMessage());
        }
    }

}
```

[Exemple complet](/damm06/assets/2.2/storejpa.zip)
