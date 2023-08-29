# Lectura i escriptura de línies de text

Per a la lectura utilitzarem un ***BufferedReader***, el qual proporciona un buffer de lectura per millorar l'eficiència dels accessos a disc. També es pot usar per a l'escriptura un ***BufferedWriter***, per les mateixes raons. En el nostre cas, però, usarem un ***PrintStream*** o un ***PrintWriter***, els quals proporcionen mètodes per escriure línies.

De fet, l'objecte *System.out* que utilitzem per escriure a la sortida estàndard dels programes és un *PrintStream*.

Exemple d'escriptura i lectura de fitxers de línies de text. [Descàrrega de l'exemple](/damm06/assets/1.1/1.1.io/LinesFile.zip)

```java
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to provide persistence of string texts in file line by line
 * @author Jose
 */
public class LinesFile {
    /**
     * saves array of String into a file, each element in a line
     * @param data the array of String to be saved
     * @param filename the name of the file
     * @return the number of lines actually written
     */
    public int saveLinesToFile(List<String> data, String filename) {
        int counter = 0;
        try (PrintStream out = new PrintStream(filename)) {
            for (String elem : data) {
                out.println(elem);
                counter++;
            }
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return counter;
    }
    
    /**
     * reads array of String from a file, each element from a line
     * @param filename the name of the file
     * @return the list of String
     */
    public List<String> readLinesFromFile(String filename) {
        List<String> data = new ArrayList<>();
        try (BufferedReader in = new BufferedReader( new FileReader(filename) )) {
            String elem;
            while ( (elem = in.readLine()) != null ) {
                data.add(elem);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
}
```

El mètode *readLine()* llegeix fins al canvi de línia i si arriba al final del fitxer retorna *null*.

Les classes *PrintStream* i *PrintWriter* també proporcionen sobrecàrregues dels mètodes ***print()*** i ***println()*** per escriure en format text tots els tipus de dades primitives.

```java
void print(boolean b)
void print(char c)
void print(char[] s)
void print(double d)
void print(float f)
void print(int i)
void print(long l)
void print(String s)
```
així com mètodes ***format*** per imprimir dades amb un format especificat:

```java
PrintWriter format(String format, Object... args)
```
