# Les classes Optional

Una classe [Optional](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Optional.html) és un contenidor que pot contenir o no un valor no nul.

El constructor és privat i disposa de mètodes *factory* estàtics per generar objectes.

```java
static<T> Optional<T> empty()
static <T> Optional<T> ofNullable(T value)
static <T> Optional<T> of(T value)
```

El mètode get ens retorna l'objecte contingut i disposa de mètodes per verificar si l'objecte hi és (no és nul) o si no hi és (és nul).

```java
boolean isPresent()
boolean isEmpty()
T get()
```

Els següents mètodes ens proporcionen de forma segura el valor, sigui nul o no.

```java
T orElse(T other)
T orElseGet(Supplier<? extends T> supplier)
T orElseThrow()
```
Per tractar els casos d'existència o no del valor disposem de diversos mètodes

```java
void ifPresent(Consumer<? super T> action)
void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)
```

Podem aplicar filtres i funcions

```java
Optional<T> filter(Predicate<? super T> predicate)
<U> Optional<U> map(Function<? super T,? extends U> mapper)
```

El mètode *map* no farà res si el valor no està present (és nul).

## Exemple

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Example of using  of Optional<T> to avoid NullPointerException with null values
 * @author jose
 */
public class ExampleOptional {

    public static void main(String[] args) {
        System.out.println("Getting optional from data source");
        List<Optional<String>> result = getOptionalList();
        result.stream()
                .filter(Optional::isPresent)  //filter non empty elements
                .map(Optional::get)    //get the value
                .forEach(System.out::println);
        System.out.println("Getting data from data source");
        List<String> result2 = getObjectList();  //list of objects with possibly null values
        result2.stream()
                .map(Optional::ofNullable)  //create an optional for each element
                .filter(Optional::isPresent)  //filter non empty elements
                .map(Optional::get)     //get the value
                .forEach(System.out::println);
        System.out.println("Getting data from data source and converting to optionals");
        List<String> result3 = getObjectList();   //list of objects with possibly null values
        List<Optional<String>> result4 = result3.stream()
                .map(Optional::ofNullable)   //create an optional for each element
                .toList();  //convert to list
        result4.forEach(System.out::println);
    }
    
    private static List<Optional<String>> getOptionalList() {
        List<Optional<String>> data = new ArrayList<>();
        data.add(Optional.of("hello"));
        data.add(Optional.ofNullable(null));
        data.add(Optional.of("everybody"));
        data.add(Optional.of("goodbye"));
        data.add(Optional.ofNullable(null));
        data.add(Optional.of("good morning vietnam"));
        return data;
    }
    
    private static List<String> getObjectList() {
        List<String> data = new ArrayList<>();
        data.add("hello");
        data.add(null);
        data.add("everybody");
        data.add("goodbye");
        data.add(null);
        data.add("good morning vietnam");
        return data;
    }
}
```
