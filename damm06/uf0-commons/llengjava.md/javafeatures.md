# Característiques de Java segons versió

## API java.util.function

*A partir de java 8*

Proveeix tipus per a expressions lambda i referències a mètodes.

Paquet [java.util.function](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/util/function/package-summary.html)

Conté definicions per a interfícies funcionals (@FunctionalInterface).

Cada interfície funcional té un sol mètode, amb paràmetres i tipus de retorn que coincideixen amb els de l'expressió lambda.

## Expressions lambda

*A partir de java 8*

Segueixen la següent sintaxi:

   parameter -> expression body

Són molt útils per definir mètodes anònims i per abstreure els mètodes a executar.

Característiques principals:

* Declaració de tipus opcional. S'infereix dels paràmetres que se li passen.
* Per a un únic paràmetre els parèntesis són opcionals.
* Les claus no són necessàries si el cos només té una sentència.
* Les claus són necessàries per indicar que es retorna un valor. La sentència //return// no és obligatòria si només hi ha una sentència al cos.

```java
@FunctionalInterface
public interface CalculatorFunctionalInterface {
    public int operation(int x,int y);
}
```

```java
CalculatorFunctionalInterface sum = (x,y) -> x+y;
CalculatorFunctionalInterface product= (x,y) -> x*y;

System.out.println(sum.operation(3,4));
System.out.println(product.operation(3,4));
```

Vet aquí un altre exemple: (de tutorialspoint)

```java
public class LambdaTester {

   public static void main(String args[]) {
      LambdaTester tester = new LambdaTester ();
  
      //with type declaration
      MathOperation addition = (int a, int b) -> a + b;
  
      //with out type declaration
      MathOperation subtraction = (a, b) -> a - b;
  
      //with return statement along with curly braces
      MathOperation multiplication = (int a, int b) -> { return a * b; };
  
      //without return statement and without curly braces
      MathOperation division = (int a, int b) -> a / b;
  
      System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
      System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
      System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
      System.out.println("10 / 5 = " + tester.operate(10, 5, division));
  
      //without parenthesis
      GreetingService greetService1 = message ->
      System.out.println("Hello " + message);
  
      //with parenthesis
      GreetingService greetService2 = (message) ->
      System.out.println("Hello " + message);
  
      greetService1.sayMessage("Mahesh");
      greetService2.sayMessage("Suresh");
   }

   interface MathOperation {
      int operation(int a, int b);
   }

   interface GreetingService {
      void sayMessage(String message);
   }

   private int operate(int a, int b, MathOperation mathOperation) {
      return mathOperation.operation(a, b);
   }
}
```

## Mètodes per defecte en interficies

*A partir de java 8*

Es poden definir mètodes per defecte en interfícies, de manera que les classes que implementen l'interfície hereten el mètode amb la seva implementació per defecte, tot i que poden redefinir-ne el comportament.

```java
public interface CalculatorInterface {
  public default int sum(int x, int y) {
      return x+y;
  }
  public int product(int x, int y);
}
```

## Streams

*A partir de java 8*

[Interface Stream<T>](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/util/stream/Stream.html)

Els streams són seqüències d'objectes que s''obtenen a partir d'un origen de dades. Com a origen de dades poden usar col·leccions, arrays i recursos d'entrada i sortida.

Disposen d'operacions d'agregació: **filter**, **map**, **limit**, **reduce**, **find**, **match** i altres.

Les operacions es poden encadenar unas darrera de les altres. Algunes són intermediàries i altres són finalistes.

Les iteracions sobre els elements dels streams són automàtiques, no cal programar-les.

### Generació de streams

Les col·leccions disposen de dos mètodes per a la creació de streams:

* **stream**()
* **parallelStream**()

```java
List<Product> products = Arrays.asList(arrayOfProducts);
products.stream();
```

```java
Stream.Builder<Product> productStreamBuilder = Stream.builder();
productStreamBuilder.accept(arrayOfProducts[0]);
productStreamBuilder.accept(arrayOfProducts[1]);
productStreamBuilder.accept(arrayOfProducts[2]);
Stream<Product> productStream = productStreamBuilder.build();
```

### Operacions sobre streams

#### forEach

```java
void forEach​(Consumer<? super T> action)
```

Aplica una funció sobre cada element del stream.

```java
productList.stream().forEach(p -> p.updatePrice());
```

#### map

```java
<R> Stream<R> map​(Function<? super T,​? extends R> mapper)
```

Aplica una funció a cada element del stream i retorna un stream amb els resultats.

```java
Integer[] productIds = { ... };  //array of id to retrieve
List<Product> products= Stream.of(productIds )
  .map(ProductDao::findById)
  .collect(Collectors.toList());
```

#### filter

```java
Stream<T> filter​(Predicate<? super T> predicate)
```

Retorna un stream amb els elements de l'original que compleixen amb el predicat.

```java
List<String> names= Arrays.asList("peter", "", "john", "mary", "laura","", "david");
//get count of empty string
int count = strings.stream().filter(string -> string.isEmpty()).count();
```

#### limit

```java
Stream<T> limit​(long maxSize)
```

Retorna un stream amb les dades de l'original en nombre limitat pel tamany especificat.

#### sorted

```java
Stream<T> sorted()
```

Retorna un stream amb els elements del stream original ordenats.

#### collectors

```java
<R,​ A> R collect​(Collector<? super T,​A,​R> collector)
```

```java
List<Integer> collect = sourceStream
      .limit(5)
      .collect(Collectors.toList());
```

## Mètodes privats en interficies

*A partir de java 9*

Es poden definir mètodes privats dintre dels interfícies com a utilitats per als mètodes a implementar.

## Organització modular de les classes

*A partir de java 9*

Les classes es poden agrupar en mòduls en construir l'aplicació.

## Inferència de tipus

*A partir de java 10*

S'incorpora la inferència de tipus en la definició de variables.

```java
var name = "Peter";
var product = new Product();
```

## Client HTTP

*A partir de java 11*

Es troba al paquet [java.net.http](https://docs.oracle.com/en/java/javase/20/docs/api/java.net.http/java/net/http/package-summary.html)

## Rule Switch

*A partir de Java 13*

Cada sentència del switch retorna un valor, es poden agrupar casos i no és necessària la sentència ***break***.

```java
int intValue;
String numericString = switch (intValue) {  
    case 0 -> "zero";  
    case 1, 3, 5, 7, 9 -> "odd";  
    case 2, 4, 6, 8, 10 -> "even";  
    default -> "N/A";  
};
```

Si els casos requereixen blocs de codi, s'utilitza **yield** per retornar un valor.

```java
String numericString = switch(integer) {
   case 0 -> {
       String value = calculateZero();
       yield value;
   } ;
   case 1, 3, 5, 7, 9 -> {
       String value = calculateOdd();
       yield value;
   };
   case 2, 4, 6, 8, 10 -> {
       String value = calculateEven();
       yield value;
   };
   default -> {
       String value = calculateDefault();
       yield value;
   };
};
```

## Format numèric compacte

*A partir de java 12*

Es poden utilitzar formats compactes de números amb la classe **CompactNumberFormat**.

```java
// `CompactNumberFormat` has a constructor, but getting an instance
// from `NumberFormat::getCompactNumberInstance` is easier
NumberFormat followers = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
followers.setMaximumFractionDigits(1);
System.out.println(followers.format(5412) + " followers");

// Resultado: 5.4K followers

NumberFormat number = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
System.out.println(number.format(1000));

// Resultado: 1K
```

## Strings multilínia (blocs de text)

*A partir de java 13*

```java
public String createHtml() {
    // same as before...
    return html = ```
        <body>
            <h1>Header</h1>
        </body>```
        // ... but here we call `transform`
        // which expects a `Function<String, T>`
        .transform(s -> setIndentationToDepth(s, 0));
}
```

## Records

*A partir de java 14*

Els **registres** són classes que no contenen més dades que les declarades. Eviten gran part del codi que és necessari en Java per definir constructors, mètodes //getter//, //setters// i implementar correctament mètodes //equals// i //hashCode//.

Per reduir el codi de les classes de dades, els registres defineixen automàticament diversos membres:

* Un camp privat i final per a cada element de la descripció.
* Un mètode d'accés de lectura per a cada component de l'estat de descripció, amb el mateix nom i tipus.
* Un constructor públic d'inicialització completa.
* Una implementació dels mètodes *equals* i *hashCode*.

Restriccions:

* No poden extendre altres classes ni definir nous atributs que no siguin estàtics.
* Són finals i no es poden extendre ni denifir-se com a abstractes.
* Els atributs són finals (inmutables).

## instanceof sense cast

*A partir de java 14 i 15*

```java
//amb versions anteriors.
if (obj instanceof Product) {
   Product p = (Product) obj
   //use p
}
//amb java 14
if (obj instanceof Product p) {
   //use p
}
```

## Classes sealed

*A partir de java 16*

Permet limitar quines classes poden extendre una classe determinada.

```java
public abstract sealed class Shape permits Circle, Rectangle, Square { ... }

public class Circle extends Shape { ... }
public class Rectangle extends Shape { ... }
public class Square extends Shape { ... }
```
