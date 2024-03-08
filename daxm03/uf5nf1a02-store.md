# Exemple 'Store' d'aplicació amb menú i model de dades separat

Aquest exemple il·lustra la gestió de dades en una llista separada en una classe, amb una interacció amb l'usuari definida amb un menú també implementat amb una llista.

La classe ***Store*** contindrà el nom de la botiga i una llista de productes.

La classe ***Product*** conté les característiques dels productes:

* name: String
* description: String
* price: double
* stock: int

**Classe Product**
```java
public class Product {

    private String code; //unique
    private String description;
    private double price;
    private int stock;

    public Product(String code, String description, double price, int stock) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Product(String code) {
        this.code = code;
    }
    //...
}
```

**Classe Store**
```java
public class Store {

    private String name;
    private List<Product> products;

    public Store(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    
    public List<Product> getAllProducts() {
        List<Product> result = null;
        result = new ArrayList<>(products);
        return result;
    }
    
    /**
     * gets the product with the given code
     * @param code the code to search
     * @return the product with the given code or null if not found
     */
    public Product getProductByCode(String code) {
        Product result = null;
        //TODO
        return result;
    }
    
    public List<Product> getProductsWithLowStock(int stock) {
        List<Product> result = null;
        //TODO
        return result;
    }
    
    /**
     * adds a product to the list,
     * avoids null objects, objects with null code and code duplicates.
     * @param product the product to add
     * @return true if successfully added, false otherwiser
     */
    public boolean addProduct(Product product) {
        boolean b = false;
        //TODO
        return b;
    }

    public boolean modifyProduct(Product current, Product product) {
        boolean b = false;
        //TODO
        return b;
    }

    public boolean removeProduct(Product product) {
        boolean b = false;
        //TODO
        return b;
    }
    
    public void generateProducts() {
        products.add( new Product("code01", "desc01", 101.0, 11));
        products.add( new Product("code02", "desc02", 102.0, 12));
        products.add( new Product("code03", "desc03", 103.0, 13));
        products.add( new Product("code04", "desc04", 104.0, 14));
        products.add( new Product("code05", "desc05", 105.0, 15));    
    }
}
```

**Classe Menu**
```java
public class Menu {

    private final String title;
    private final List<String> options;

    public Menu(String title) {
        this.title = title;
        this.options = new ArrayList<>();
    }

    public void addOption(String option) {
        options.add(option);
    }

    /**
     * displays menu and gets user's option
     *
     * @return option number selected by user or -1 if invalid option
     */
    public int displayMenuAndReadOption() {
        int opt = -1;
        Scanner sc = new Scanner(System.in);
        System.out.format("======== %s =======%n", title);
        for (int i = 0; i < options.size(); i++) {
            System.out.format("%d. %s%n", i, options.get(i));
        }
        try {
            System.out.print("Select an option: ");
            opt = sc.nextInt();
            if ((opt < 0) || (opt >= options.size())) {  //out of range
                opt = -1;
            }
        } catch (InputMismatchException ex) {
            sc.next();
            opt = -1;
        }
        return opt;
    }

}
```

**Classe Principal StoreMain**
```java
public class StoreMain {

    private final Store myStore;  //Store object (data model)
    private boolean exit;  //flag to exit application.
    private final Scanner scan;  //Scanner to read from user

    public StoreMain() {
        myStore = new Store("La meva botigueta");
        scan = new Scanner(System.in);
    }

    public static void main(String[] args) {
        StoreMain sm = new StoreMain();
        sm.run();
    }

    private void run() {
        //instantiate store
        //myStore = new Store("La meva botigueta");
        myStore.generateProducts();
        //
        exit = false;
        //instantiate menu
        Menu mnu = createMenu();
        //control loop (interact with user)
        do {
            //display menu and read user's choice
            int option = mnu.displayMenuAndReadOption();
            switch (option) {
                case 0: //exit
                    doExit();
                    break;
                case 1: //list all products
                    doListAllProducts();
                    break;
                case 2: //list product by code
                    doListProductByCode();
                    break;
                case 3: //list products with low stock
                    doListProductsWithLowStock();
                    break;
                case 4: //add a new product
                    doAddProduct();
                    break;
                case 5: //modify a product
                    doModifyProduct();
                    break;
                case 6: //remove a product
                    doRemoveProduct();
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (!exit);
    }

    /*  ========= View methods =========*/
    /**
     * creates a menu with proper title and options
     *
     * @return menu
     */
    private Menu createMenu() {
        Menu mnu = new Menu("Store application");
        mnu.addOption("Exit");
        mnu.addOption("List all products");
        mnu.addOption("List product by code");
        mnu.addOption("List products with low stock");
        mnu.addOption("Add a new product");
        mnu.addOption("Modify a product");
        mnu.addOption("Remove a product");
        return mnu;
    }

    /**
     * displays a list of products
     *
     * @param data the list to display
     */
    private void displayProductList(List<Product> data) {
        for (Product p : data) {
            System.out.println(p);
        }
        System.out.format("%d elements shown%n", data.size());
    }

    /**
     * reads a string from user
     *
     * @param message the message to prompt to use
     * @return user's answer
     */
    private String readString(String message) {
        System.out.print(message);
        return scan.next();
    }

    /**
     * reads from user a product
     *
     * @return the product read or null in case of error
     */
    private Product readProduct() {
        Product p = null;
        try {
            String code = readString("Code: ");
            String description = readString("description: ");
            String sprice = readString("Price: ");
            String sstock = readString("Stock: ");
            double price = Double.parseDouble(sprice);
            int stock = Integer.parseInt(sstock);
            p = new Product(code, description, price, stock);
        } catch (NumberFormatException ex) {
            scan.next();
            p = null;
        }
        return p;
    }

    /*  ========= Control methods =========*/
    /**
     * asks for confirmation and exits application
     */
    private void doExit() {
        System.out.println("Are you sure? (yes/no)");
        String answer = scan.next();
        if (answer.equalsIgnoreCase("yes")) {
            exit = true;
        }
    }

    /**
     * gets all products and displays all of them
     */
    private void doListAllProducts() {
        List<Product> result = myStore.getAllProducts();
        displayProductList(result);
    }

    /**
     * reads from user the code of the product, then searches the product with
     * that code, if found, it displays the product, if not found, it reports to
     * user.
     */
    private void doListProductByCode() {
        //read the code
        System.out.print("Input the code: ");
        String code = scan.next();
        //
        Product result = myStore.getProductByCode(code);
        if (result != null) {  //found
            System.out.println(result);
        } else {  //not found
            System.out.printf("Product with code '%s' not found%n", code);
        }
    }

    /**
     * reads from user data for the new product, if correctly read instantiates
     * a product with that data, and adds the product to the list If not
     * correctly read, it reports the error to the user Finally, it reports
     * result to user.
     */
    private void doAddProduct() {
        //read the product
        Product prod = readProduct();
        if (prod != null) {  //correctly read
            boolean result = myStore.addProduct(prod);
            if (result) { //added
                System.out.println("Product successfully added");
            } else {  //not added
                System.out.println("Product not added");
            }
        } else {  //invalid data read
            System.out.println("Invalid data!");
        }
    }

    /**
     * TODO document the method
     */
    private void doListProductsWithLowStock() {
        //TODO remove following code and implement functionality
        String methodName = new Exception()
                .getStackTrace()[0]
                .getMethodName();
        System.out.format("'%s' not implemented yet!%n", methodName);
    }

    /**
     * TODO document the method
     */
    private void doModifyProduct() {
         //TODO remove following code and implement functionality
        String methodName = new Exception()
                .getStackTrace()[0]
                .getMethodName();
        System.out.format("'%s' not implemented yet!%n", methodName);    }

    /**
     * TODO document the method
     */    
    private void doRemoveProduct() {
        //TODO remove following code and implement functionality
        String methodName = new Exception()
                .getStackTrace()[0]
                .getMethodName();
        System.out.format("'%s' not implemented yet!%n", methodName);    }
}
```

L'atribut *myStore: Store* permet accedir a les dades de la botiga.

L'atribut *exit* permet decidir quan s'ha de sortir del programa.

El mètode no estàtic *run* genera productes de prova i instancia el menú de l'aplicació. 

A continuació entra al bucle de control d'interacció amb l'usuari:
* Presentar a cada iteració el menú d'opcions i llegir la seva elecció
* Processar l'elecció de l'usuari invocant el mètode de control adient

Els mètodes que només interactuen amb l'usuari estan agrupats al bloc 'mètodes de la vista'.

Els mètodes que executen les accions de cada funcionalitat estan agrupats al bloc 'mètodes de control'.

**Exercici**:
Completeu el codi que falta, documentant adequadament cada mètode.
