# Exemple 'CurrencyConverter' d'aplicació amb menú i model de dades separat usant Map

Aquest exemple il·lustra la gestió de dades en un map separada en una classe, amb una interacció amb l'usuari definida amb un menú implementat amb una llista.

L'aplicació s'ubica al paquet principal *cat.proven.currencyconverter*, al qual hi ha la classe principal *Main* i la classe del menú *Menu*.

Al subpaquet *cat.proven.currencyconverter.model* s'ubica la classe *CurrencyConverter*.

La classe ***CurrencyConverter*** conté el el *Map* amb els noms de les monedes com a clau i el seu canvi respecte de l'euro com a valor.

També conté mètodes per a les diferents operacions que es poden fer amb les monedes.

També al paquet *model* hi ha la classe *CurrencyNotFoundException*, excepció que es llançarà quan s'intenti accedir a una moneda que no existeix.

**Classe CurrencyNotFoundException**
```java
package cat.proven.currencyconverter.model;

/**
 * Exception thrown when a currency is not found
 * @author ProvenSoft
 */
public class CurrencyNotFoundException extends Exception {

    public CurrencyNotFoundException(String message) {
        super(message);
    }
    
}
```

**Classe CurrencyConverter**

```java
package cat.proven.currencyconverter.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Stores currency changes and performs conversions
 * @author ProvenSoft
 */
public class CurrencyConverter {
    /**
     * map containing al currencies and changes (related to EUR)
     */
    private final Map<String, Double> changes;

    public CurrencyConverter() {
        changes = new HashMap<>();
        loadCurrencies();
    }

    /**
     * gets all currency names
     * @return a Set with all currency names
     */
    public Set<String> getCurrencies() {
        return changes.keySet();
    }
    
    /**
     * gets value (change) of given currency
     * @param currency the currency name to search
     * @return the value of the change of that currency
     * @throws CurrencyNotFoundException if currency not found
     */
    public Double getCurrencyChange(String currency) throws CurrencyNotFoundException {
        return 0.0; //TODO
    }
    
    /**
     * calculates value of change from 'cSource' currency to 'cTarget'
     * @param cSource the source currency
     * @param cTarget the target currency 
     * @param value the amount in 'cSource' currency to change
     * @return the calculated change
     * @throws CurrencyNotFoundException if some currency is not found
     */
    public Double change(String cSource, String cTarget, Double value) 
            throws CurrencyNotFoundException {
        Double result = 0.0;
        //TODO
        return result;
    }
    
    /**
     * adds a new currency and its value to the map
     * @param name the name of the currency
     * @param value the value of the currency
     * @return true if successfully added, false otherwise
     */
    public boolean addCurrency(String name, Double value) {
        //TODO
        return false;
    }
    
    /**
     * modifies a currency value
     * @param name the name of the currency
     * @param value the value of the currency
     * @return true if successfully modified, false otherwise
     * @throws CurrencyNotFoundException if currency name is not found
     */
    public boolean modifyCurrency(String name, Double value) 
            throws CurrencyNotFoundException {
        //TODO
        return false;
    }

    /**
     * removes a currency and its value from the map
     * @param name the name of the currency
     * @return true if successfully removed, false otherwise
     * @throws CurrencyNotFoundException if currency name is not found
     */
    public boolean removeCurrency(String name)
            throws CurrencyNotFoundException {
        //TODO
        return false;
    }    
    
    /**
     * generate currency changes
     */
    private void loadCurrencies() {
        changes.put("EUR", 1.0);
        changes.put("USD", 0.91);
        changes.put("GBP", 0.85);
        //TODO add more currencies
    }
    
}
```

**Classe Menu**
```java
package cat.proven.currencyconverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class to provide a menu to user interaction
 * @author ProvenSoft
 */
public class Menu {

    /**
     * title of menu
     */
    private final String title;
    /**
     * options of menu
     */
    private final List<String> options;

    public Menu(String title) {
        this.title = title;
        this.options = new ArrayList<>();
    }

    /**
     * adds an option to the menu
     *
     * @param option the option to add
     */
    public void addOption(String option) {
        options.add(option);
    }

    /**
     * displays menu and gets user's option
     *
     * @return option number selected by user or -1 if invalid option
     */
    public int displayMenuAndReadOption() {
        Scanner sc = new Scanner(System.in);
        int opt = -1;
        System.out.printf("======== %s =======%n", title);
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("%d. %s%n", i, options.get(i));
        }
        try {
            System.out.print("Select an option: ");
            opt = sc.nextInt();
            if ( (opt<0) || (opt>=options.size()) ) {
                opt = -1;
            }
        } catch (NumberFormatException ex) {
            sc.next();
            opt = -1;
        }
        return opt;
    }

}
```

**Classe Principal Main**
```java
package cat.proven.currencyconverter;

import cat.proven.currencyconverter.model.CurrencyConverter;
import java.util.Scanner;
import java.util.Set;

/**
 * Main class for currency converter application
 * @author ProvenSoft
 */
public class Main {

    /**
     * the data service (model)
     */
    private final CurrencyConverter converter;
    /**
     * flag to exit application
     */
    private boolean exit;
    /**
     * Scanner to read from user
     */
    private final Scanner scan;

    public Main() {
        converter = new CurrencyConverter();
        exit = false;
        scan = new Scanner(System.in);
    }
    
    public static void main(String[] args) {
        Main ap = new Main();
        ap.init();
    }

    /**
     * init method
     */
    private void init() {
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
                case 1: //list all currency names
                    doListAllCurrencies();
                    break;
                case 2:  //show value of a currency
                    doShowValueOfCurrency();
                    break;
                case 3: //calculate change
                    doCalculateChange();
                    break;
                case 4: //add a currency
                    doAddCurrency();
                    break;
                case 5: //modify a currency
                    doModifyCurrency();
                    break;
                case 6: //remove a currency
                    doRemoveCurrency();
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (!exit);
    }

    /*  ========= View methods =========*/
    
    /**
     * creates a menu for currency converter application
     * @return a menu
     */
    private Menu createMenu() {
        Menu mnu = new Menu("Store application");
        mnu.addOption("Exit");
        mnu.addOption("List all currency names");
        mnu.addOption("Show value of a currency");
        mnu.addOption("Calculate change");
        mnu.addOption("Add a currency");
        mnu.addOption("Modify a currency");
        mnu.addOption("Remove a currency");
        return mnu;
    }

     /*  ========= Control methods =========*/
    
    /**
     * asks for confirmation and exits application
     */
    private void doExit() {
        System.out.print("Are you sure? (yes/no)");
        String answer = scan.next();
        if (answer.equalsIgnoreCase("yes")) {
            exit = true;
        }
    }

    /**
     * gets all currency names and displays them
     */
    private void doListAllCurrencies() {
        Set<String> names = converter.getCurrencies();
        for (String name : names) {
            System.out.println(name);
        }
    }

    /**
     * read from user a currency name,
     * gets its value (if found)
     * and displays it
     * If not found, it reports error to user
     */
    private void doShowValueOfCurrency() {
        //TODO
        System.out.println("Not implemented yet!");
    }

    /**
     * reads from user currency names (source and target) and the amount to change,
     * then calculates change in target currency and displays result to user
     */
    private void doCalculateChange() {
        //TODO
        System.out.println("Not implemented yet!");
    }

    /**
     * reads currency name and value from user
     * adds the information to map of currencies and 
     * report result to user
     */
    private void doAddCurrency() {
        //TODO
        System.out.println("Not implemented yet!");
    }

    /**
     * reads currency name and new value from user,
     * searches currency and displays name and value to user,
     * asks for confirmation and, if so, 
     * modifies value of currency
     */
    private void doModifyCurrency() {
        //TODO
        System.out.println("Not implemented yet!");
    }

    /**
     * reads currency name from user,
     * searches currency and displays name and value to user,
     * asks for confirmation and, if so, 
     * removes currency
     */
    private void doRemoveCurrency() {
        //TODO
        System.out.println("Not implemented yet!");
    }
    
}
```
Descàrrega del codi: [Currency converter](assets/5.1/5.1.2/CurrencyConverterAp.zip)

**Exercici**:
Completeu el codi que falta seguint la documentació i completant-la quan s'escaigui.
