# Biblioteques de funcions i classes

Les funcions en llenguatge Java sempre pertanyen a alguna classe. 

Podem agrupar les funcions que anem programant i que ens seran d'utilitat en futurs programes dintre de classes.

De la mateixa manera, les classes s'organitzen en paquets (*package*) i es poden reutilitzar en futurs programes.

El disseny de les classes ha de permetre el seu ús el diversos programes sense necessitat de tocar el seu codi.

Per definir a quina classe pertany la classe que estem programant cal declarar-ho amb la paraula clau **package**.

```java
package cat.proven.store.model;
public class Product {
    //...
}
```
Els paquets poden contenir subpaquets al seu interior. Es declaren separant els noms amb punts. L'estructura de paquets i subpaquets es trasllada al disc amb la mateixa estructura de directoris i subdirectoris.

Per usar la classe abans definida cal importar la seva declaració especificant el paquet on es troba.

```java
import cat.proven.store.model.Product;
```

També es poden fer importacions conjuntes de totes les classes, interface, etc. d'un paquet.

```java
import cat.proven.store.model.*;
```

## Exemples

### Classe auxiliar per a entrada i sortida estàndard i classe principal per provar el seu funcionament.

```java
import java.io.PrintStream;
import java.util.Scanner;
/**
 * Auxiliar class to perform standard input and output
 * @author Jose
 */
public class IOHelper {
    public static Scanner input = new Scanner(System.in);;
    public static PrintStream output = System.out;
}
```

```java
/**
 * Test input/output with IOHelper
 * @author Jose
 */
public class IOTest {
    public static void main(String[] args) {
        IOHelper.output.print("Input your name: ");
        String name = IOHelper.input.next();
        IOHelper.output.format("You wrote that your name was %s\n", name);
        IOHelper.output.print("Input your age: ");
        int age = IOHelper.input.nextInt();
        IOHelper.output.format("You wrote that your age was %d\n", age);
    }
}
```

### Biblioteca de mètodes per a càlculs amb figures

Podem definir una classe amb mètodes estàtics i públics que proveeixei càlculs de perímetres i àrees de figures planes.

```java
package cat.proven.geometria;

/**
 * Biblioteca de mètodes per al càlcul amb figures planes
 * @author ProvenSoft
 */
public class Geom2d {
    /**
     * calcula el perímetre d'un quadrat, donat el costat
     * @param costat el costat del quadrat
     * @return el perímetre
     */
    public static double perimetreQuadrat(double costat) {
        return 4.0 * costat;
    }
    
    /**
     * calcula l'àrea d'un quadrat, donat el costat
     * @param costat el costat del quadrat
     * @return l'àrea
     */
    public static double areaQuadrat(double costat) {
        return costat * costat;
    }    
    
    /**
     * calcula el perímetre del rectangle
     * @param base la base del rectangle
     * @param altura l'altura del rectangle
     * @return el perímetre
     */
    public static double perimetreRectangle(double base, double altura) {
        return 2.0 * (base + altura);
    }
    
    /**
     * calcula l'àrea del rectangle
     * @param base la base del rectangle
     * @param altura l'altura del rectangle
     * @return l'àrea
     */
    public static double areaRectangle(double base, double altura) {
        return base * altura;
    } 
    
    /**
     * calcula el perímetre de la circumferència
     * @param radi el radi de la circumferència
     * @return el perímetre
     */
    public static double perimetreCircumferencia(double radi) {
        return 2.0 * Math.PI * radi;
    }
    
    /**
     * calcula l'àrea del cercle
     * @param radi el radi del cercle
     * @return l'àrea
     */
    public static double areaCercle(double radi) {
        return Math.PI * radi * radi;
    }      
    
    
}
```

Per provar-ne el funcionament i il·lustrar-ne l'ús, tenim la classe principal segúent:

```java
import cat.proven.geometria.Geom2d;

/**
 * Tester per a càlculs amb figures
 *
 * @author ProvenSoft
 */
public class GeomTester {

    public static void main(String[] args) {
        //calcular amb quadrat
        double costatQuadrat = 5.0;
        double perimetreQuadrat = Geom2d.perimetreQuadrat(costatQuadrat);
        System.out.format(
                "El perímetre del quadrat de costat %.2f és %.2f\n",
                costatQuadrat, perimetreQuadrat);
        double areaQuadrat = Geom2d.areaQuadrat(costatQuadrat);
        System.out.format(
                "L'àrea del quadrat de costat %.2f és %.2f\n",
                costatQuadrat, areaQuadrat);
        //calcular amb rectangle
        double a = 4.0, b = 3.0;
        double perimetreRectangle = Geom2d.perimetreRectangle(a, b);
        System.out.format(
                "El perímetre del rectangle de base %.2f i altura %.2f és %.2f\n",
                a, b, perimetreRectangle);
        double areaRectangle = Geom2d.areaRectangle(a, b);
        System.out.format(
                "L'àrea del rectangle de base %.2f i altura %.2f és %.2f\n",
                a, b, areaRectangle);
        //calcular amb cercle
        double radiCercle = 3.0;
        double perimetreCercle = Geom2d.perimetreCircumferencia(radiCercle);
        System.out.format(
                "El perímetre de la circumferència de radi %.2f és %.2f\n",
                radiCercle, perimetreCercle);
        double areaCercle = Geom2d.areaCercle(radiCercle);
        System.out.format(
                "L'àrea del cercle de radi %.2f és %.2f\n",
                radiCercle, areaCercle);

    }

}
```

El resultat en pantalla seria:
```
El perímetre del quadrat de costat 5,00 és 20,00
L'àrea del quadrat de costat 5,00 és 25,00
El perímetre del rectangle de base 4,00 i altura 3,00 és 14,00
L'àrea del rectangle de base 4,00 i altura 3,00 és 12,00
El perímetre de la circumferència de radi 3,00 és 18,85
L'àrea del cercle de radi 3,00 és 28,27
```
