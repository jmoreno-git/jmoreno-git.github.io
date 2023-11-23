# Apertura de fitxers per afegir informació

Totes les subclasses de [*FileOutputStream*](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/FileOutputStream.html) i [*FileWriter*](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/FileWriter.html) tenen constructors que permeten escollir si es vol obrir el fitxer en mode *append* per a afegir informació en comptes de sobreescriure-la.

```java
FileOutputStream(File file, boolean append)
FileOutputStream(String name, boolean append)
FileWriter(File file, boolean append)
FileWriter(String fileName, boolean append)
```

Si es defineix *append=true*, el fitxer es crea si no existeix prèviament, i si ja existeix, la informació que s'hi escriu s'afegeix al final del mateix sense sobreescriure l'existent. Es llança *FileNotFoundException* si no es pot obrir o crear el fitxer per algun motiu (manca de permisos o perquè es tracta d'un directory).

```java
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
/**
 * Open a file in append mode
 * @author Jose
 */
public class CharAppend {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Input file name: ");
        String filename = scan.next();
        System.out.print("Input character: ");
        String sCharacter = scan.next();
        char character = sCharacter.charAt(0);
        //write to file
        try (FileWriter f = new FileWriter(filename, true)) {
            f.write(character);
        } catch (IOException ex) {
            System.out.println("Error writing");
        }       
        //read from file and display content
        try (FileReader f  = new FileReader(filename)) {
            int c;
            while ( (c = f.read())!=-1 )  {
                System.out.print((char)c);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (IOException ex) {
            System.out.println("Error reading");
        }
    }
}
```
