# Estructures d'emmagatzematge

## Continguts

* Col·leccions i iteradors.
* Recorregut per col·leccions.
* Llistes, piles, cues, mapes, …

[Apunts d'Estructures de dades en Java](assets/5.1/5.1.2/dax2_m03-a512-Collections.pdf)

[Documentació de referència](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/doc-files/coll-overview.html)

Anem a conéixer el [**Java Collection Framework**](https://docs.oracle.com/javase/tutorial/collections/index.html), el qual proporciona interfaces i classes per gestionar col·leccions de dades.

## Ús de col·leccions i llistes 

La interface que ocupa la posició més alta a la jerarquia d'herència (i per tant la més general) és [***Collection***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collection.html). 

![](assets/5.1/5.1.2/collectionsframeworkoverview.png)

Collection esten la [interface ***Iterable***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html), la qual cosa permet a aquests objectes ser recorreguts amb bucles for avançats, com ara el bucle *for-each*.

La interface ***Collection*** ofereix aquests mètodes.

| Mètode | Descripció | 
|:----------:|----------|
| **add(T)**    | Afegeix un element |
| **iterator()**   | Obté un iterador que permet recórrer la col·lecció visitant cada element una vegada |
| **size()**     | Obté la quantitat d'elements que aquesta col·lecció emmagatzema  |
| **contains(t)**  | Pregunta si l'element t ja està dintre de la col·lecció |
| **remove(t)**  | Suprimeix un únic element de la col·lecció|
| **clear()**  | Suprimeix tots els elements de la col·lecció|
| **iterator()**  | Retorna un iterador per recórrer els elements de la col·lecció|
| **isEmpty()**  | Retorna *true* si la col·lecció és buida|
| **toArray()**  | Retorna un array amb tots els elements de la col·leccióH|

Per poder il·lustrar també mètodes que proporcionen les llistes però no les col·leccions, a l'exemple usarem classes que implementin llistes.

La interface ***List*** ofereix a més a més aquests mètodes.

| Mètode | Descripció | 
|:----------:|----------|
| **get(int)**    | Retorna l'objecte a la posició indicada |
| **indexOf(T)**    | Retorna l'index de la primera aparició de l'objecte |
| **lastIndexOf(T)**    | Retorna l'index de la darrera aparició de l'objecte |
| **listIterator()**    | Retorna un iterador de llista per recórrer tots els elements de la llista |
| **remove(int)**  | Suprimeix l'element de la col·lecció a la posició indicada|
| **set(int, T)**  | Reemplaça l'element a la posició indicada amb l'element donat|
| **add(int, T)**  | Insereix a la posició indicada l'element donat|

Hem de declarar la llista amb el tipus genèric de la interface [***List***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html), la qual ens permet gestionar per polimorfisme tots els tipus de llistes. Disposem de dues classes implementades a la biblioteca (paquet java.util): [***ArrayList***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayList.html) i [***LinkedList***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/LinkedList.html).

La instanciació la fem amb un tipus concret de llista.

La biblioteca de col·leccions de Java usa plantilles (*template*) per especificar el tipus d'objecte a emmagatzemar: List<Integer>.

Les dues implementacions són iguals i funcionen de la mateixa manera. La diferència està en la implementació de la llista en memòria. *ArrayList* implementa la llista amb un array, mentres que *LinkedList* utilitza una llista enllaçada. Els arrays permeten accessos ràpids i directes a la informació, donada la posició de l'element. En canvi, les llistes enllaçades són més eficients quan cal inserir i esborrar elements amb freqüència a la llista.

[Colec.java (descàrrega)](assets/5.1/5.1.2/Colec.java)
```
import java.util.*;

public class Colec {

    public static void main(String[] args) {
        //Declare collection or list, but instantiate concrete classes
        List<Integer> data = new ArrayList<>();
        //List<Integer> data = new LinkedList<>();
        //Both ArrayList and LinkedList work the same way and are interchangeable
        
        //collection methods
        System.out.println("Collection methods");
        
        //populate data.
        data.add(Integer.valueOf(23));
        data.add(Integer.valueOf(12));
        data.add(Integer.valueOf(10));
        data.add(Integer.valueOf(45));

        System.out.println("Number of elements: " + data.size());

        System.out.println("Iterate with for-each loop");
        for (Integer elem : data) {
            System.out.println(elem.toString());
        }

        System.out.println("Iterate with iterator");
        Iterator<Integer> iter = data.iterator();
        while (iter.hasNext()) {
            Integer elem = iter.next();
            System.out.println(elem.toString());
        }

        System.out.println("Collection contains value 23?: " + data.contains(23));
        System.out.println("Collection contains value 99?: " + data.contains(99));

        System.out.println("Collection is empty?: " + data.isEmpty());

        System.out.println("Remove element with value 23");
        data.remove(Integer.valueOf(23));
        //display collection after removal
        for (Integer elem : data) {
            System.out.println(elem.toString());
        }

        //list methods
        System.out.println("List methods");
        
        System.out.println("Iterate with listiterator");
        ListIterator<Integer> iter2 = data.listIterator();
        while (iter2.hasNext()) {
            Integer elem = iter2.next();
            System.out.println("Next elem: " + elem.toString());

        }

        System.out.println("Add an element at index 1");
        data.add(1, Integer.valueOf(55));
        printList(data);

        System.out.println("Add an element at the top of the list");
        data.add(Integer.valueOf(77));
        printList(data);

        System.out.println("Modify element at index 2");
        data.set(2, Integer.valueOf(44));
        printList(data);

        System.out.println("Iterate list with a counter and use method get");
        for (int i = 0; i < data.size(); i++) {
            Integer elem = data.get(i);
            System.out.println(elem.toString());
        }

        System.out.println("Method toString");
        System.out.println(data.toString());

        System.out.println("Method indexOf");
        int p = data.indexOf(Integer.valueOf(44));
        System.out.println("Index of elem 44: " + p);
    }

    private static void printList(List<Integer> l) {
        for (Integer elem : l) {
            System.out.println(elem.toString());
        }
    }

}
```

Per recórrer la llista es poden utilitzar diversos procediments.

### Recorregut amb un iterador (Collection)

Aquest és el procediment general per a qualsevol ***Collection***.

```java
Iterator<Integer> iter = data.iterator();
while (iter.hasNext()) {
	Integer elem = iter.next();
	System.out.println(elem.toString());
}
```

La [***interface Iterator***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Iterator.html) permet recórrer els elements de la col·lecció, assegurant que passa una única vegada per cada element de la mateixa.

*Iterator* proporciona els següents mètodes:

| Mètode | Descripció | 
|:----------:|----------|
| **hasNext()**    | Retorna true si la iteració té més elements |
| **next()**    | Retorna el següent element en la  iteració |
| **remove()**    | Suprimeix de la col·lecció el darrer element recorregut |

### Recorregut amb bucle foreach (Collection)

Si *collection* és la referència a la col·lecció de tipus *Integer* que es vol recórrer:

```java
for (Integer elem: collection) {
	System.out.println(elem.toString());	
}
```
on *elem* és l'identificador de la variable que anirà agafant els valors dels diferents elements de la col·lecció a mesura que avança el recorregut.

### Recorregut amb comptador de posició (List)

Accedim a cada posició de la llista amb el mètode ***get(int index)***.

```java
for (int i = 0; i<data.size(); i++){
	Integer elem = data.get(i);
	System.out.println(elem.toString());
}
```

# Cerca d'elements a una llista

[LinkedListTester.java (descàrrega)](assets/5.1/5.1.2/LinkedListTester.java)

```
import java.util.*;

/**
 * Program to test LinkedList functionality
 *
 * @author Jose
 */
public class LinkedListTester {

    public static void main(String[] args) {
        //declare and instantiate the list
        List<Integer> list1 = new LinkedList<>();
        //populate the list with some data
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(5);
        list1.add(8);
        list1.add(13);
        list1.add(21);
        list1.add(34);
        list1.add(3);

        System.out.println("Size = " + list1.size());

        System.out.println("Test get method");
        Integer elem = list1.get(3);
        System.out.println("Element at index 3 is = " + elem);

        System.out.println("\nTest get method  --> exception");
        try {
            Integer elem2 = list1.get(99);  //this element does not exist
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Exception message " + e.getMessage());
            // e.printStackTrace();
        }

        System.out.println("\nTest indexOf method");
        int i = list1.indexOf(13);
        System.out.println("The index of element 13 is " + i);

        int i2 = list1.indexOf(0);
        System.out.println("The index of element 0 is " + i2);

        System.out.println("\nTest lastIndexOf method");
        int i3 = list1.lastIndexOf(3);
        System.out.println("The last index of element 3 is " + i3);

        int i4 = list1.lastIndexOf(0);
        System.out.println("The last index of element 0 is " + i4);

        System.out.println("\nLoop using get");
        for (int j = 0; j < list1.size(); j++) {
            System.out.println(list1.get(j));
        }

        System.out.println("\nTest set method");
        list1.set(2, 22);
        for (int j = 0; j < list1.size(); j++) {
            System.out.println(list1.get(j));
        }

    }

}
```

Els mètodes **indexOf()** i **lastIndexOf()** ens proporcionen la ubicació de l'objecte buscat a la llista (la primera aparició o l'última, respectivament) o bé retornen -1 si no troben l'objecte. Cal recordar que per trobar l'objecte la implementació de la llista usa el mètode ***equals()*** definit a la classe dels objectes emmagatzemats a la llista. Cal definir allà correctament com es comparen els objectes de la llista.

## Comparació de List i Set

Un *Set* és una *Collection* on no pot haver-hi elements duplicats. El següent exemple il·lustra aquest fet en comparació amb una *List*.

[ListVsSetTester.java](assets/5.1/5.1.2/ListVsSetTester.java)
```java
import java.util.*;
/**
 * Program to compare List and Set java classes functionality
 * @author ProvenSoft
 */
public class ListVsSetTester {

    public static void main(String[] args) {
        
        ListVsSetTester ct = new ListVsSetTester();
       
        // ******* Deleting zeros *******************
        List<Integer> arrayNum = new ArrayList<>();
        
        //Adding data
        arrayNum.add(1);
        arrayNum.add(0);
        arrayNum.add(23);
        arrayNum.add(0);
        arrayNum.add(0);
        arrayNum.add(12);
        
        // Deleting zeros from an integer's array
        System.out.println("Deleting zeros from an integer's array");
        ct.deleteZeros(arrayNum);
        System.out.println(arrayNum);
        
      
		// ******* Removing elements *******************
		List<Integer> list1 = new ArrayList<> ();
		
        //Adding data
        list1.add(1);
        list1.add(3);
        list1.add(5);
        list1.add(8);
        list1.add(13);
        list1.add(21);
        list1.add(34);
		
		System.out.println("Removing even elements");
        ct.removeEven(list1);
        System.out.println(list1);
      
		// ******* Checking adding duplicates in set*******************
        Collection <Integer> setTest  = new HashSet <> ();
        boolean b;
        System.out.println("Checking add method");
        b = setTest.add(1);  System.out.println("Has the element 1 been added? \n" + b);
        b = setTest.add(3);  System.out.println("Has the element 3 been added? \n" + b);
        b = setTest.add(8);  System.out.println("Has the element 8 been added? \n" + b);
        b = setTest.add(13); System.out.println("Has the element 13 been added? \n" + b);
        b = setTest.add(8);  System.out.println("Has the element 8 been added? \n" + b);
        b = setTest.add(21); System.out.println("Has the element 21 been added? \n" + b);
        b = setTest.add(34); System.out.println("Has the element 34 been added? \n" + b);
        
        System.out.println("Size setTest= " +setTest.size());
      
        System.out.println("Printing setTest with a for-each");
        for (Integer o:setTest){
            System.out.println(o);
        }
        
    }
    /**
     * Removes all 0 items of zeros collection
     * @param zeros source collection
     */
    private void deleteZeros (Collection<Integer> zeros) {
		 Iterator<Integer> it = zeros.iterator();
		 while (it.hasNext()){
			int i = it.next();
			if ( i== 0){
				it.remove();
			}	 
		}
	}
    
    /**
     * Removes even numbers of col collection
     * @param col source collection
     */
    private void removeEven (Collection<Integer> col){
            Iterator<Integer> it = col.iterator();
            while (it.hasNext()){
                int i = it.next();
                if ((i % 2) == 0){
                    it.remove();
                }
            }
    }
   
}	    
```

## Comparació d'objectes

Per tal de poder realitzar ordenació de col·leccions, els objectes han d'implementar mètodes de comparació que permetin establir un ordre.

Hi ha dos mecanismes per fer-ho.

### Interface Comparable

L'interface ***Comparable*** s'ha d'utilitzar per definir el mecanisme natural de comparació d'una classe d'objectes.

Els objectes a comparar han d'implementar l'[***interface Comparable***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Comparable.html).

Es tracta d'un interface funcional, l'únic mètode del qual és:

```int compareTo(T o)```

El mètode retorna un enter negatiu, zero, o un enter positiu si aquest objecte és menor que, igual a, major que l'objecte especificat, respectivament. 

### Interface Comparator

L'interface ***Comparator*** s'utilitza per definir comparacions lògiques diferents de la natural o bé d'objectes que no controlem, és a dir, dels quals no podem modificar la definició.

Un objecte que implementi aquest interface ha de definir el mètode:

```int compare(T o1, T o2)```

El mètode retorna un enter negatiu, zero, o un enter positiu si l'objecte o1 és menor que, igual a, major que l'objecte o2, respectivament. 

Podem definir diferents comparadors que implementin diferents mecanismes de comparació per a un mateix tipus d'objectes.

## Ordenació d'elements de col·leccions

La interface *List* proveeix el mètode ***sort()*** per ordenar els elements. El paràmetre del mètode és un objecte d'una classe que implementi l'interface Comparator<T>, definint de forma adequada el mètode compare(T o1, T o2).

El retorn del mètode *compare()* és el següent:
  * enter negatiu si o1 < o2
  * 0 si o1 = o2
  * enter positiu si o1 > o2

[ListSort.java (descàrrega)](assets/5.1/5.1.2/ListSort.java)
```
/**Java program to demonstrate working of Comparator 
 * interface and Collections.sort() to sort according 
 * to user defined criteria.
 * @author Jose Moreno
 */ 
import java.util.*; 
import java.lang.*; 
import java.io.*; 

// A class to represent a student. 
class Student { 
	private int 	id; 
	private String 	name, address; 

	// Constructor 
	public Student(int id, String name, String address) { 
		this.id = id; 
		this.name = name; 
		this.address = address; 
	} 

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	// Used to print student details in main() 
	public String toString() { 
		return this.id + " " + this.name + " " + this.address; 
	} 
} 

// class to implement comparison by id
class SortById implements Comparator<Student> { 
	// Used for sorting in ascending order of id .
	public int compare(Student a, Student b) { 
		return a.getId() - b.getId(); 
	} 
} 

// class to implement comparison by name
class SortByName implements Comparator<Student> 
{ 
	// Used for sorting in ascending order of name.
	public int compare(Student a, Student b) { 
		return a.getName().compareTo(b.getName()); 
	} 
} 

// Main class.
public class ListSort { 
	public static void main (String[] args) { 
		List<Student> data = new ArrayList<Student>(); 
		data.add(new Student(111, "bbbb", "london")); 
		data.add(new Student(131, "aaaa", "nyc")); 
		data.add(new Student(121, "cccc", "jaipur")); 
//
		System.out.println("Unsorted"); 
		for (int i=0; i<data.size(); i++) 
			System.out.println(data.get(i)); 
//Java 7
		Collections.sort(data, new SortById()); 
		System.out.println("\nSorted by id"); 
		for (int i=0; i<data.size(); i++) 
			System.out.println(data.get(i));
//Java 7
		Collections.sort(data, new SortByName()); 
		System.out.println("\nSorted by name"); 
		for (int i=0; i<data.size(); i++) 
			System.out.println(data.get(i));			
//Java 8
		System.out.println("\nSorted by id (Java 8)"); 
		data.sort(Comparator.comparing(Student::getId));
		data.forEach(System.out::println);
		
		System.out.println("\nSorted by name (Java 8)"); 
		data.sort(Comparator.comparing(Student::getName));
		data.forEach(System.out::println);
		
		System.out.println("\nReverse sorted by name (Java 8)"); 
		data.sort(Comparator.comparing(Student::getName).reversed());
		data.forEach(System.out::println);
	}
} 
```

# Ús de mapes (Map)

La interface [***Map***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Map.html) relaciona dues col·leccions d'elements: claus (*key*) i valors (*value*), permeten cerques per les claus.

La interface proporciona tres vistes de les dades:
  * Set<K> keySet(): proporciona un *Set* amb les *keys*
  * Collection<V> values(): proporciona una *Collection* amb els *values*
  * Set<Map.Entry<K,V>> entrySet(): proporciona un *Set* amb les parelles clau-valor (tipus ***Map.Entry<K,V>***)

També proporciona diversos mètodes *getXX*, *putXX*, *replaceXX*, ... per accedir a la informació i per modificar-la.

[MapExample.java (descàrrega)](assets/5.1/5.1.2/MapExample.java)
```
import java.io.*;
import java.util.*;

/**
 * MapExample.java
 * This example illustrates the use of Map interface and how to iterate over it
 * @author José Moreno
 */
public class MapExample {
	
	public static void main (String args[]) {
		
		Map<String, Integer> items = new HashMap<>();
		
		//Populating the Map
		items.put("A", 10);
		items.put("B", 20);
		items.put("C", 30);
		items.put("D", 40);
		items.put("E", 50);
		items.put("F", 60);

		//Using an Iterator (previous to Java 8)
		System.out.println("Iterating a Map using an iterator:");
		
		Set<Map.Entry<String,Integer>>      set  = items.entrySet();
		Iterator<Map.Entry<String,Integer>> iter = set.iterator();
		
		while (iter.hasNext()) {
			Map.Entry<String,Integer> entry = iter.next();
			String  key                     = entry.getKey();
			Integer value                   = entry.getValue();
			System.out.println("[ Key: "+key+" ] [ Value: "+value+" ]");
		}
		
		//Getting the set of keys
		Set<String>  keys  = items.keySet();
		
		//Looping the set
		for (String key: keys) {
				Integer value = items.get(key);
				System.out.println("[ Key: "+key+" ] [ Value: "+value+" ]");
		}
		
		//Using for loop
		System.out.println("Iterating a Map using a foreach loop:");
		for (Map.Entry<String, Integer> entry : items.entrySet()) {
			System.out.println("[ Key: " + entry.getKey() + " ][ Value: " + entry.getValue()+" ]");
		}
		
		//Lambda expressions and passive iterators (since Java 8)
		
		//Output using a consumer
		System.out.println("Iterating a Map using lambda expressions, passive iterators and a simple action:");
		items.forEach((k,v)->System.out.println("[ Key: " + k + " ][ Value: " + v+" ]"));
		
		//Block of code
		System.out.println("Iterating a Map using lambda expressions, passive iterators and a block of code:");
		items.forEach((k,v)->{
			System.out.println("[ Key: " + k + " ][ Value : " + v+" ]");
			if("E".equals(k)){
				System.out.println("Hello E");
			}
		});
	
	}
}
```

Un altre exemple, aquest per il·lustrar com gestionar una llista de qualificacions d'alumnes, en la qual el nom de l'alumne és la clau i la nota el valor.

```java

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author jose
 */
public class MapGrades {

    private final Map<String, Integer> grades;

    public MapGrades() {
        grades = new HashMap<>();
    }

    public static void main(String[] args) {
        MapGrades mg = new MapGrades();
        mg.run();
    }

    private void run() {
        Scanner sc = new Scanner(System.in);
        //load test data
        initializeData();
        //display grades
        displayGrades();
        //calculate mean grade
        int meanGrade = calculateGlobalMean();
        System.out.println("Mean grade=" + meanGrade);
        //modify a grade by student name
        System.out.print("Input a student name to modify grade: ");
        String name = sc.next();
        System.out.print("Input new grade: ");
        Integer grade = sc.nextInt();
        if (grades.containsKey(name)) { //assess key exists
            grades.replace(name, grade);
        }
        displayGrades();
    }

    /**
     * initialize test data
     */
    private void initializeData() {
        grades.put("John", 6);
        grades.put("Mary", 7);
        grades.put("Laura", 4);
        grades.put("Peter", 3);
        grades.put("Joseph", 8);
        grades.put("Sophie", 5);
    }

    /**
     * displays all grades
     */
    public void displayGrades() {
        System.out.println("=== Grades ===");
        Set<String> names = grades.keySet();
        for (String name : names) {
            System.out.format("%s: %d\n", name, grades.get(name));
        }
        System.out.format("%d grades displayed\n", grades.size());
    }

    /**
     * calculates global mean grade
     */
    public Integer calculateGlobalMean() {
        Collection<Integer> values = grades.values();
        return calcMean(values);
    }

    /**
     * calculates mean of data
     *
     * @param data the data collection to calculate mean
     * @return mean of data
     */
    private Integer calcMean(Collection<Integer> data) {
        Integer result = 0;
        for (Integer e : data) {
            result += e;
        }
        result /= data.size();
        return result;
    }

}
```


## Programació amb estructures de dades

### Aplicacions amb menú d'usuari

Per tal de desenvolupar aplicacions que interactuin amb l'usuari convé utilitzar menús amb les diferents opcions o funcionalitats de l'aplicació. Per sistematitzar aquest procés podem generar classes que representin un menú d'usuari i les diferents opcions del programa.

[Classes Menu i MenuItem per programar menús d'usuari](assets/5.1/5.1.2/generic_menu.zip)

L'exemple anterior conté les classes *Menu* i *MenuItem*.

Les opcions del menú es representen amb la classe *MenuItem*, on *text* és el que es mostrarà a l'usuari i *actionCommand* és el codi amb què identifiquem l'opció al nostre programa.

```java
public class MenuItem {
	private String text;
	private String actionCommand;
}
```

La classe *Menu* té el títol del menú i la llista d'opcions.

```java
public class Menu {
	protected String title;
	protected List<MenuItem> options;
	//...
	public int getSelectedOptionIndex() {...}
	public String getSelectedOptionActionCommand() {...}
}
```
Els mètodes que es mostren retornen, respectivament, l'índex i l'actionCommand de l'opció que ha estat seleccionada per l'usuari.

### Exemple d'aplicació amb menú i gestió de llistes 

Exemple de gestió de clients en una llista. Plantilla a completar a classe com a exercici. [ClientManagerWithList.zip](assets/5.1/5.1.2/ClientManagerWithList.zip). Solució: [ClientManagerWithList-full.zip](assets/5.1/5.1.2/ClientManagerWithList-full.zip)

El mateix exemple dels clients en una llista, però separant dades a model i creant una classe menú per a l'aplicació. Plantilla a completar com a exercici. [ClientManagerWithList2.zip](assets/5.1/5.1.2/ClientManagerWithList2.zip). Solució sense excepcions: [ClientManagerWithList2-full.zip](assets/5.1/5.1.2/ClientManagerWithList2-full.zip). Solució amb excepcions: [ClientManagerWithList3-full.zip](assets/5.1/5.1.2/ClientManagerWithList3-full.zip)


## Classe Properties

La classe [***Properties***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Properties.html) representa un conjunt persistent de propietats, les quals es poden desar i recuperar d'un *stream* (per exemple, un fitxer). Cada propietat ve definida per una clau (key) i un valor (value).

Alguns dels mètodes que proporciona són:

* String getProperty(String key)
* String getProperty(String key, String defaultValue)
* void list(PrintStream out)
* void list(PrintWriter out)
* void load(InputStream inStream)
* void load(Reader reader)
* Enumeration<?> propertyNames()
* Object setProperty(String key, String value)
* void store(Writer writer, String comments)

Consulteu la documentació per a obtenir més detalls de l'ús de cada mètode.

[Exemple d'ús de Properties per mantenir usuari i paraula de pas: properties_example.zip](assets/5.1/5.1.2/properties_example.zip)

Exercici proposat:
Utilitzar els fitxers de Properties per desar la configuració d'un programa i les traduccions dels missatges. Caldrà un fitxer de propietats per desar l'idioma escollit i un fitxer de propietat per a cadascun dels idiomes.

## Col·leccions i streams (java 8+)

A partir de la versió 8 de Java, el tractament de seqüències de dades es potencia molt amb la [**interface java.util.Stream**](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/Stream.html), els [**interface funcionals**](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html) i les [**expressions lambda**](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html).

L'ús combinat d'aquestes eines permet la manipulació àgil i la realització d'operacions complexes de filtrat, modificació i transformació de dades d'una manera molt potent i efectiva. 

Al llarg del curs aniran apareixen algunes d'aquestes característiques.

[Tutorial de llistes amb stream](assets/5.1/5.1.2/java8tutorial-streams.pdf)

Exemple de tractament de llistes amb streams: [Java8Tester.java](assets/5.1/5.1.2/Java8Tester.java)

## Cues i piles

La biblioteca Java Collection Framework (JFC) proporciona també interfaces i classes per implementar cues i piles.

Les cues (*queue**) són memòries [FIFO](https://es.wikipedia.org/wiki/First_in%2C_first_out) (*First in First Out*) i les piles (*stack*) són memòries [LIFO](https://es.wikipedia.org/wiki/Last_in%2C_first_out) (*Last in Fist Out*).

### L'interface Queue<E>

Permet implementar una estructura en memòria que admet insercions de dades pel final i extraccions per l'inici.

![Mètodes de Queue](assets/5.1/5.1.2/queue_methods.png)

La classe LinkedList<E> implementa aquest interface.

### L'interface Deque<E>

Permet implementar una estructura en memòria que admet insercions i extraccions de dades pels dos extrems.

![Mètodes de Deque](assets/5.1/5.1.2/deque_methods.png)

Les classes [ArrayDeque<E>](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ArrayDeque.html) i [LinkedList<E>](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/LinkedList.html) implementen aquest interface.

