# Cadenes alfanumèriques i conversions de format

## Cadenes de caràcters (String)

[Apunts de Strings en Java](assets/1.2/Strings_in_Java.pdf)

[Classe String](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html)

[Apunts d'Expresions regulars en Java](assets/1.2/Expressions_regulars_en_Java.pdf)

### Exemples amb String

```java
Scanner lector = new Scanner(System.in);
//declarar i instanciar String (són objectes, cal invocar el constructor)
String nom = new String("Lluis");
//instanciació i inicialització abreujada amb constant
String salutacio = "Hola";
//ús de l'operador de concatenació
String missatge = salutacio + " " + nom;
System.out.println(missatge);
//obtenir la longitud del string
System.out.println("La longitud del missatge és "+missatge.length());
//obtenir el caràcter en una posició
System.out.print("Quin índex? ");
int index = lector.nextInt();
try {
    char c = missatge.charAt(index);  //pot llançar StringIndexOutOfBoundsException si l'índex està fora de límits
    System.out.println("El caràcter a la posició "+index+" és "+ c);
} catch (StringIndexOutOfBoundsException e) {
    System.out.println("Índex incorrecte");
}
//comparar strings (negatiu, zero o positiu segons el resultat de la comparació)
int comp = "Hola".compareTo("Holo");
System.out.println(comp);
//ús del mètode concat() per concatenar String
System.out.println(salutacio.concat(nom));
//mètodes per analitzar el contingut
String frase = "En un lugar de la Mancha de cuyo nombre no quiero acordarme";
System.out.println("Comença per En? "+ frase.startsWith("En"));
System.out.println("Acaba per rme? "+ frase.endsWith("rme"));
//igualtat de strings
String a = "Taula";
String b = "taula";
System.out.println("Son iguals? "+ a.equals(b));
System.out.println("Son iguals (ignorant case)? "+ a.equalsIgnoreCase(b));
//ús del mètode format() per obtenir un string amb dades formatades
int edat = 22;
double salari = 1800.0;
String informacio = 
        String.format("%s tens %d anys i salari %.2f\n", 
                nom, edat, salari);
System.out.println(informacio);
//trobar la posició d'un caràcter o string (indexOf(), lastIndexOf)())
System.out.println("La primera 'u' és a l'índex: "+frase.indexOf('u'));
System.out.println("La darrera 'u' és a l'índex: "+frase.lastIndexOf('u'));
System.out.println("La primera 'de' és a l'índex: "+frase.indexOf("de"));
//ús del mètode replace()
System.out.println(frase.replace('e', '3'));
//extracció de fragments del string (mètodes substring())
System.out.println(frase.substring(4, 20));
//conversió a majusc/minusc
System.out.println(frase.toUpperCase());
System.out.println(frase.toLowerCase());
```

La sortida del codi anterior és:
```
Hola Lluis
La longitud del missatge és 10
Quin índex? 3
El caràcter a la posició 3 és a
-14
HolaLluis
Comença per En? true
Acaba per rme? true
Son iguals? false
Son iguals (ignorant case)? true
Lluis tens 22 anys i salari 1800,00

La primera 'u' és a l'índex: 3
La darrera 'u' és a l'índex: 44
La primera 'de' és a l'índex: 12
En un lugar d3 la Mancha d3 cuyo nombr3 no qui3ro acordarm3
n lugar de la Ma
EN UN LUGAR DE LA MANCHA DE CUYO NOMBRE NO QUIERO ACORDARME
en un lugar de la mancha de cuyo nombre no quiero acordarme
```

### Conversió entre String i Number

Java proveeix tota una sèria de classes envolupants (***wrappers***) dels tipus de dades primitives (int, long, ...).

Totes aquestes classes són subclasses de la classe [***Number***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Number.html):

* Integer
* Short
* Long
* Float
* Double
etc.

Per convertir números a format string i viceversa, utilitzem aquestes classes envolupants i la classe String.

```java
//donat un text que conté un número
String intText = "23";
//s'obté el valor numèric amb el mètode estàtic parseXXX de la classe envolupant del tipus primitiu corresponent
int intValue = Integer.parseInt(intText);
```

```java
//donat un valor numèric
int intValue = 23;
//s'obté la representació en format String amb el mètode toString de la classe envolupant del tipus primitiu corresponent
String intText = Integer.toString(intValue);
//alternativament, la classe String proveeix els mètodes valueOf, els quals fan el mateix que toString del la classe envolupant
String intText = String.valueOf(intValue);
```

### Proposta d'exercici: El xifrat per desplaçament (xifrat Cèsar)

El xifrat utilitzat per Juli Cèsar per comunicar-se sense que els missatges puguessin ser llegits per l'enemic aplicava l'algorisme de xifrat per desplaçament.

Consulteu el seu funcionament [aquí](https://es.wikipedia.org/wiki/Cifrado_C%C3%A9sar).

Codifiqueu un programa que demani a l'usuari un missatge (String) i un desplaçament (enter), codifiqui el missatge, el mostri codificat, després el decodifiqui i el mostri decodificat.

Haurà de contenir dos mètodes: un per codificar i un altre per decodificar.

```java
/**
 * xifra el missatge amb l'algorisme de desplaçament
 * @param missatge el missatge a xifrar
 * @param desp el desplaçament a aplicar
 * @return el missatge xifrat
 */
public static String xifrarCesar(String missatge, int desp) {
    String result="";
    //TODO
    return result;
}

/**
 * desxifra el missatge amb l'algorisme de desplaçament
 * @param missatge el missatge a desxifrar
 * @param desp el desplaçament a aplicar
 * @return el missatge desxifrat
 */    
public static String desxifrarCesar(String missatge, int desp) {
    String result="";
    //TODO
    return result;        
}
```

Solució: [Xifrat César](./assets/1.2/XifratCesar.java)

## Dates i temps

Convé utilitzar les classes del paquet [**java.time**](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/package-summary.html), les quals estan basades en el calendari ISO, el qual segueix les regles del calendari Gregorià introduit l'any 1582.

Les classes més destacades del paquet *java.time* són:

* **LocalDate**: representa dates sense hora i permet declarar-les, sumar-les, restar-les i comparar-les.
* **LocalTime**: l'equivalent a l'anterior, però per representar hores sense data.
* **LocalDateTime**: combinació de les dues anteriors, representa data i hora.
* **Instant**: emmagatzema un moment determinat en el temps amb data i hora com un timestamp.
* **ZonedDateTime**: Com LocalDateTime però tenint en consideració la zona horària.
* **Period**: representa diferències entre moments en el temps.
* **Duration**: similar a l'anterior, però només per a hores.

Totes aquestes classes produeixen instàncies immutables i no tenen constructors públics, sinó que es construeixen a partir de mètodes ***factory***.

Els mètodes més importants són:

* **now()**: crea instàncies noves a partir de la data i hora actuals.
* **of()**: construeix dates i hores a partir de les seves parts.
* **with()**: modifica la data o hora actual segons el paràmetre.

Per a obtenir les parts d'una data o d'una hora disposen de mètodes **getHour()**, **getMinute()**, **getMonth()**, etc.

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
//obtenir la data i l'hora actuals
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

Per a analitzar (*parse*) dates disposen del mètode ***parse()***.

```java
LocalDate hoy = LocalDate.parse("2023-10-25");
LocalDate seisNov = LocalDate.parse("25/10/2023", DateTimeFormatter.ofPattern("d/M/yyyy") );
```

Per a més exemples: [Cómo manejar correctamente fechas en Java: el paquete java.time](https://www.campusmvp.es/recursos/post/como-manejar-correctamente-fechas-en-java-el-paquete-java-time.aspx)

## La classe StringBuilder

[Classe StringBuilder](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/StringBuilder.html)

```java
/**
 * Exemple d'ús de StringBuilder
 * @author Jose
 */
public class StringBuilderExemple {

    public static void main(String[] args) {
        //instanciar un StringBuilder amb un String
        StringBuilder sb = new StringBuilder("En un lugar de la Mancha");
        //convertir a String i mostrar
        System.out.println("=contingut inicial=");
        System.out.println(sb.toString());
        //append
        System.out.println("=append=");
        sb.append(" de cuyo nombre no quiero acordarme");
        System.out.println(sb.toString());
        //insert
        System.out.println("=insert=");
        sb.insert(12, "(esto es de El Quijote) " );
        System.out.println(sb.toString());
        //delete
        System.out.println("=delete=");
        int index = sb.indexOf("la");
        sb.delete(index, index+13);
        System.out.println(sb.toString());
        //demostració d'encadenament d'accions
        System.out.println("=encadenament d'accions=");
        sb.append("aaaa").append("bbbb").insert(5, "cccc");
        System.out.println(sb.toString());
        //reverse
        System.out.println("=reverse=");
        sb.reverse();
        System.out.println(sb.toString());
    }

}
```

Sortida del codi d'exemple anterior:

```text
=contingut inicial=
En un lugar de la Mancha
=append=
En un lugar de la Mancha de cuyo nombre no quiero acordarme
=insert=
En un lugar (esto es de El Quijote) de la Mancha de cuyo nombre no quiero acordarme
=delete=
En un lugar (esto es de El Quijote) de cuyo nombre no quiero acordarme
=encadenament d'accions=
En uncccc lugar (esto es de El Quijote) de cuyo nombre no quiero acordarmeaaaabbbb
=reverse=
bbbbaaaaemradroca oreiuq on erbmon oyuc ed )etojiuQ lE ed se otse( ragul ccccnu nE
```
