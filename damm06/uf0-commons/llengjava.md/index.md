# Llenguatge Java

## Classes enniuades, internes i anònimes

**Classe java OuterClass.java**

```java
/**
 * Nested classes exemple.
 * @author Jose
 */
package cat.proven.nestedclasses;

/**
* OuterClass.
* @author Jose
 */
public class OuterClass {
    private String x;
    private static String y;
    private NestedClass nested;

    public OuterClass(String x) {
        this.x = x;
        OuterClass.y = "Outer."+x;
        this.nested = new NestedClass("Nested."+x);
    }

    /**
  * example of variable access.
  * @return information string
     */
    public String show() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nFrom Outer:\n");
        sb.append("Outer {");
        sb.append(String.format("[Outer.x=%s]", this.x));
        sb.append(String.format("[Outer.y=%s]", OuterClass.y));
        sb.append(String.format("[Nested.nx=%s]", nested.nx));  
        //sb.append(String.format("[StaticInner.six=%s]", StaticInnerClass.six)); //static not allowed here
        sb.append(String.format("[StaticInner.siy=%s]", StaticInnerClass.siy));
        sb.append("}");
        sb.append(nested.show());
        return sb.toString();
    }

    /**
  * NestedClass
     */
    private class NestedClass {
        private String nx;
        // private static String ny = "Nested.ny";  illegal static in an inner class.

        public NestedClass(String nx) {
            this.nx = nx;
        }

        /**
    * example of variable access.
    * @return information string
         */
        public String show() {
            StringBuilder sb = new StringBuilder();
            sb.append("\nFrom Nested:\n");
            sb.append("Outer {");
            sb.append(String.format("[Outer.x=%s]", x));
            sb.append(String.format("[Outer.y=%s]", OuterClass.y));
            sb.append(String.format("[Nested.nx=%s]", this.nx));  
            sb.append(String.format("[StaticInner.siy=%s]", StaticInnerClass.siy));
            sb.append("}");
            return sb.toString();
        }

    }

    /**
  * Public nested class. It can be instantiated out of outer class.
     */
    public class PublicNestedClass {
        private String nx;
        // private static String ny = "Nested.ny";  illegal static in an inner class.

        public PublicNestedClass(String nx) {
            this.nx = nx;
        }

        /**
    * example of variable access.
    * @return information string
         */
        public String show() {
            StringBuilder sb = new StringBuilder();
            sb.append("\nFrom PublicNested:\n");
            sb.append("Outer {");
            sb.append(String.format("[Outer.x=%s]", x));
            sb.append(String.format("[Outer.y=%s]", OuterClass.y));
            sb.append(String.format("[PublicNested.nx=%s]", this.nx));  
            sb.append(String.format("[StaticInner.siy=%s]", StaticInnerClass.siy));
            sb.append("}");
            return sb.toString();
        }  

    }

    /**
  * Static inner class
     */
    public static class StaticInnerClass {
        private static String siy = "StaticInner.siy";

        public StaticInnerClass() {
        }

        /**
    * example of variable access.
    * @return information string
         */
        public static String show() {
            StringBuilder sb = new StringBuilder();
            sb.append("\nFrom StaticInner:\n");
            sb.append("Outer {");
            //sb.append(String.format("[Outer.x=%s]", x)); //illegal access a non static variable.
            sb.append(String.format("[Outer.y=%s]", OuterClass.y));  
            sb.append(String.format("[StaticInner.siy=%s]", StaticInnerClass.siy));
            sb.append("}");
            return sb.toString();
        }  

    }

    /**
  * interface to be implemented by anonimous inner classes.
     */
    public interface Greeter {
        String greet(String name);
    }

    /**
  * exemple of anonimous local inner classes implementing an interface.
  * @param name
  * @return
     */
    public String doGreet(String name) {
        /**
    * Anonimous class implementing Greeter interface
         */
        Greeter greeterCA = new Greeter() {
            String greet = "hola";
            @Override
            public String greet(String name) {
                return String.format("%s %s", greet, name);
            }
        };
        /**
    * Anonimous class implementing Greeter interface
         */
        Greeter greeterEN = new Greeter() {
            String greet = "hello";
            @Override
            public String greet(String name) {
                return String.format("%s %s", greet, name);
            }
        };
        return String.format("greeterCA:%s; greeterEN:%s", greeterCA.greet(name), greeterEN.greet(name));
    }

}
```

**Classe Main.java**

```java
package cat.proven.nestedclasses;

/**

* Nested classes exemple. Main class.
* @author Jose
 */
public class Main {

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    private void run() {
        OuterClass outerObj = new OuterClass("x");
        System.out.println(outerObj.show());
        System.out.println(OuterClass.StaticInnerClass.show());
        OuterClass.PublicNestedClass publicNested = outerObj.new PublicNestedClass("Public.x");
        System.out.println(publicNested.show());
        System.out.println("doGreet:"+outerObj.doGreet("Paul"));
    }

}
```

## Reflection

* Apunts de reflexió. [dax2_m03-a422-reflexio.pdf](/damm06/assets/0.1/dax2_m03-a422-reflexio.pdf)
* Exemple d'ús de reflexió per fer un formulari dinàmic adaptat al tipus d'objecte. [reflectform.zip](/damm06/assets/0.1/reflectform.zip)

## Logger

A continuació s'exemplifica com definir un Logger que redireccioni la sortida a fitxers (amb un màxim de 3) i límit de tamany especificat, així com la manera d'evitar heretar els handler pares.

```java
private Logger configLogger() {
    //file path to log.
    logfile = "resources/log.txt";
    //create and configure (if necessary) the message logger.
    Logger logger = Logger.getLogger(this.getClass().getName());
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
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    } catch (IOException | SecurityException ex) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }
    return logger;
}
```

## Tractament d'errors

Ús de **logger** per enviar errors a un fitxer (veure més amunt).

Utilització de **enum** per a codificar errors:

```java
public enum DbResultCode {
    /**
     * result codes: code and value.
     */
    OK  (0),
    DB_CONN_FAIL (-1),
    BAD_QUERY (-2),
    INTEG_VIOLATION (-3),
    NOT_FOUND (-4);

    private final int code;
 
    DbResultCode(int code) {
        this.code = code;
    }
 
    public int code() { return code; }
}
```

Ús d'excepcions específiques per comunicar errors amb un codi:

```java
public class ResultCodeException extends Exception {
    private int resultCode;

    public ResultCodeException(String message, int resultCode) {
        super(message);
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

}

```

Si es prefereix no haver de registrar l'excepció (declaració a tots els mètodes pel que passen sense ser capturada) es pot extendre de ***RuntimeException*** en comptes de ***Exception***.

Exemple de mètode per generar missatges a partir de codis de resultat d'una acció:

```java
public String getMessage(int code) {
    String msg = null;
    switch (code) {
        case 0:
            msg = "action completed successfully";
            break;
        case -1:
            msg = "fail to connect to database";
            break;
        case -2:
            msg = "the query to the database has some problem";
            break;
        case -3:
            msg = "Some restriction in database has prohibited query";
            break;
        case -4:
            msg = "That object has not been found in database";
            break;
        default:
            msg = "unexpected error";
            break;
    }
    return msg;
}
```

Exemple de com capturar l'excepció específica i informar a l'usuari.

```java
public void testRetrieveAllCategories() {
    try {
        //test retrieve all categories
        System.out.println("Retrieve all categories");
        List<Category> allCategories = categoryDao.selectAll();
        printList(allCategories);
    } catch (ResultCodeException ex) {
        //Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println(getMessage(ex.getResultCode()));
    }
}
```

## Class tester
  
Classe de test per provar les funcionalitats de qualsevol classe. Usa reflection. [testutils.zip](/damm06/assets/0.1/testutils.zip)

## Internacionalització d'aplicacions

### Dates i temps

Convé utilitzar les classes del paquet ***java.time***, les quals estan basades en el calendari ISO, el qual segueix les regles del calendari Gregorià introduit l’any 1582.

```java
//get current data
LocalDate today = LocalDate.now();
System.out.println(today);
//output: 2022-11-05
```

```java
//get current time
LocalTime now = LocalTime.now();
System.out.println(now);
//output: 18:35:38.330782700
```

```java
//get current data and time
LocalDateTime todayNow = LocalDateTime.now();
System.out.println(todayNow);
//output: 2022-11-05T18:35:38.330782700
//use a formatter to convert into string with specific format
DateTimeFormatter dtFormat1 = DateTimeFormatter.ofPattern("EEEE yyyy/MMMM/dd, hh:mm:ss");
System.out.println(todayNow.format(dtFormat1));
//output: sábado 2022/noviembre/05, 06:35:38
```

```java
//get current date and time with time zone
ZonedDateTime todayNowHere = ZonedDateTime.now();
DateTimeFormatter dtFormat2 = DateTimeFormatter
      .ofLocalizedDateTime(FormatStyle.FULL)  //format
      .withLocale(Locale.forLanguageTag("ca-ES"));  //locale
System.out.println(todayNowHere.format(dtFormat2));
//output: dissabte, 5 de novembre de 2022, a les 18:35:38 (Hora estàndard del Centre d’Europa)
//especify format with language tag
DateTimeFormatter dtFormat3 = DateTimeFormatter.ofPattern("EEEE yyyy/MMMM/dd, hh:mm:ss")
        .withLocale(Locale.forLanguageTag("ca-ES"));
System.out.println(todayNowHere.format(dtFormat3));
//output: dissabte 2022/de novembre/05, 06:35:38
//especify format with DateTimeFormatter constants
System.out.println(todayNowHere.format(DateTimeFormatter.ISO_DATE_TIME));
//output: 2022-11-05T18:35:38.363622+01:00[Europe/Madrid]
```

```java
//determine is a year is leap year
int year = 2024, month=3, day=5;
LocalDate date = LocalDate.of(year, month, day);
System.out.format("Is %d leap year?: %s ", date.getYear(), date.isLeapYear()?"yes":"no");
```

### Traducció de texts dels programes

L'objectiu d'aquest apartat és aconseguir definir els texts de l'aplicació en diferents idiomes.

Per això usem la classe ***ResourceBundle*** i la classe ***Locale***, així com la classe ***Properties*** per a emmagatzemar les traduccions.

Creem els fitxers de traducció amb els missatges com a parelles clau:valor en fitxers amb un nom base comú i l'etiqueta de l'idioma com a sufix.

**message.properties**
```java
hello=hello
goodbye=goodbye
inputname=input your name
```

**messages_en_US.properties**
```java
hello=hello
goodbye=goodbye
inputname=input your name
```

**messages_es_ES.properties**
```java
hello=hola
goodbye=adiós
inputname=entra tu nombre
```

**messages_ca_ES.properties**
```java
hello=hola
goodbye=adéu
inputname=entra el teu nom
```

Suposem que hem desat aquests fitxers de traducció en el paquet 'cat.proven.i18example.i18n', és a dir, en el directori '/cat/proven/i18nexamples/i18n' (al projecte estarà a sota de src).

Llavors el següent programa il·lustra l'ús d'aquests fitxers per aconseguir traduir missatges a qualsevol idioma.

**Main.java**
```java
package cat.proven.i18nexample;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *

* @author Jose
 */
public class Main {

    public static void main(String[] args) {
        Main ap = new Main();
        ap.start();
    }

    private void start() {
        String basepath = "cat.proven.i18nexample.i18n."; //package notation
        ResourceBundle i18nBundle;
        //default locale
        i18nBundle = ResourceBundle.getBundle(basepath+"messages", Locale.getDefault());
        System.out.println("Locale default: "+i18nBundle.getString("goodbye"));
        //US locale
        i18nBundle = ResourceBundle.getBundle(basepath+"messages", Locale.US);
        System.out.println("Locale es_US: "+i18nBundle.getString("goodbye"));
        //getting locale by language tag
        i18nBundle = ResourceBundle.getBundle(basepath+"messages", Locale.forLanguageTag("es-ES"));
        System.out.println("Locale es_ES: "+i18nBundle.getString("goodbye"));  
        //using a loader to access bundle in any directory
        ClassLoader loader = new URLClassLoader(new URL[]{ getClass().getResource("/cat/proven/i18nexample/i18n/")});
        i18nBundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag("ca-ES"), loader);
        System.out.println("Locale ca_ES: "+i18nBundle.getString("goodbye"));
    }
}
```
