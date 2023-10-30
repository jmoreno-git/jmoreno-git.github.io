# Funcions i procediments

## Introducció

L'ús de subalgorismes o subprogrames permet dividir problemes complexos en problemes més petits que es poden tratar millor de manera separada i que permet fer proves unitàries del seu funcionament amb independència de la resta del codi.

Han de tenir una interfície clara amb la resta del codi, és a dir, tenen un únic punt d'entrada, un únic punt de sortida, reben dades d'entrada preferiblement només com a paràmetres i poden retornar un valor.

El codi extern invoca les funcions i procediments utilitzant el seu nom (identificador) i passant-li els arguments d'entrada en forma de llista de paràmetres entre parèntesis.

Quan són invocats, s'executa el codi del seu cos. Un cop finalitzat, el control es retorna a la instrucció següent a la de la seva invocació.

En funció del retorn, alguns llenguatges els classifiquen en:

- Funcions
- Procediments

D'ara endavant en direm **mètodes** als dos tipus de subprogrames.

## Funcions

Retornen un valor i poden usar-se en expressions, ja que avaluen al valor retornat.

Exemple: funció que calcula i retorna el valor promig de tres valors.

```java
//[tipus de retorn] [identificador] (paràmetres [tipus identificador])
float mitjana(float x, float y, float z) {
    //cos (body) de la funció
    float suma = x + y +z;
    return suma/3.0;  //instrucció de retorn amb el valor a retornar.
}
```

Per invocar aquest mètode des del programa principal:

```java
// amb valors constants
float resultat1 = mitjana(2.0, 3.0, 5.0);
//amb valors provinents de variables o constants prèviament definides
float a = 2.0;
float b = 3.0;
float c = 5.0;
float resultat2 = mitjana(a, b, c);
```

## Procediments

S'utilizen com les funcions però no es poden usar en expressions ja que no retornen cap valor.

```java
void mostraSalutacio(String missatge) {
  System.out.println(missatge);
}
```

## Pas de paràmetres

Els mètodes treballen amb els **paràmetres** com si fossin variables locals. Els valors passats a aquests paràmetres en el moment de la invocació es diuen **arguments**.

La correspondència entre paràmetres i arguments es pot establir de dues maneres:

- Correspondència posicional: s'estableix tenint en compte l'ordre d'escriptura de paràmetres i arguments.
- Correspondència per nom: en la invocació d'indica el nom del paràmetre i el seu valor (argument).

El sistema més habitual en la majoria de llenguatge és el posicional.

Hi ha també diversos mètodes d'associació entre paràmetres i arguments:

- **Pas per valor**: El valor de l'argument es copia directament al paràmetre (recordeu que els paràmetres funcionen com variables locals del mètode). El mètode no té, doncs, accés als arguments originals del subprograma invocant, sinó només a una còpia dels mateixos. Això implica que el mètode no pot modificar els valor de les variables usades com a arguments. Es diu que són **paràmetres d'entrada**, ja que només faciliten informació al mètode.
- **Pas per referència**: Els paràmetres reben com a valor, no el valor dels arguments, sinó referències a les variables originals (una referència és un apuntador que permet accedir a la ubicació de la variable en memòria). Dintre del mètode, s'utilizen en general de la mateixa manera (pot dependre del llenguatge de programació), però cal tenir present que ara els canvis en els valor dels paràmetres afecten a les variables del programa principal usades com a arguments. En aquest cas, es diu que són **paràmetres d'entrada i sortida**.

## Mètodes en Java

La sintaxi de Java per definir mètodes consisteix en una primera línia amb la declaració del prototip del mètode, seguida del bloc de codi del mètode tancat entre {}.

```java
/**
 * area()
 * calculates and returns area of sphere with radius passed as a parameter.
 * @param double radius the radius of the sphere.
 * @return calculated area of the sphere.
 */
    public double area(double radius) {
        return (4.0 * Math.PI *radius * radius);
    }
```

El prototip conté els següents elements:

- modificador d'accés: public, protected, private
- el tipus de dada que retorna
- l'identificador del mètode
- la llista ordenada dels paràmetres del mètode, especificant el tipus i el nom

Per invocar el mètode des de la mateixa classe, la sintaxi és la següent:

```java
double resultat = area(3.0);
```

## Exemples

```java
import java.util.Scanner;

/**
 * Entra dades d'un rectangle i en calcula el perímetre i l'àrea.
 * @author ProvenSoft
 */
public class Geometria {

    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        //entrada de dades
        System.out.print("Entra la base: ");
        double baseRect = lector.nextDouble();
        System.out.print("Entra l'altura: ");
        double alturaRect = lector.nextDouble();
        //calcular el perímetre
        double perimetreRect = 
                calculaPerimetreRectangle(baseRect, alturaRect);
        //calcular l'àrea
        double areaRect = calculaAreaRectangle(baseRect, alturaRect);
        //mostrar resultats
        System.out.println("El perímetre del rectangle és: "+perimetreRect);
        System.out.println("L'àrea del rectangle és: "+areaRect);
    }
    
    /**
     * calcula area rectangle amb la base i altura indicades
     * @param base la base del rectangle
     * @param altura l'altura del rectangle
     * @return l'àrea del rectangle
     */
    public static double calculaAreaRectangle(double base, double altura) {
        double area = base * altura;
        return area;   //return base*altura;
    }

    /**
     * calcula perímetre rectangle amb la base i l'altura indicades
     * @param base la base del rectangle
     * @param altura l'altura del rectangle
     * @return el perímetre del rectangle
     */
    public static double calculaPerimetreRectangle(double base, double altura) {
        double perimetre = 2.0 * (base + altura);
        return perimetre;
    }
    
}
```

**Activitat**

Utilitzar el depurarador (*debug*) de l'editor per executar línia a línia el programa, inspeccionant els valors de les variables i observant el flux d'execució. En particular, observar el salt des de la funció principal cap a les funcions invocades i el seu retorn. Observar també les variables que es poden inspeccionar en cada situació.

```java
/**
 * Exemple de diferents tipus de funcions i procediments segons els paràmetres i retorn
 * @author ProvenSoft
 */
public class FuncProc {
    
    public static void main(String[] args) {
        int a = doble(3);
        System.out.println("Valor de a:"+a);
        saluda("Peter");
        avui();
        String dia = dema();
        System.out.println(dia);
    }
    
    /**
     * mètode 1 paràmetre i retorna 1 valor
     */
    public static int doble(int x) {
        return 2*x;
    }
    
    /**
     * mètode 1 paràmetre i cap retorn
     */
    public static void saluda(String nom) {
        System.out.println("Hola "+nom);
    }
    
    /**
     * mètode cap paràmetre i cap retorn
     */
    public static void avui() {
        System.out.println("Avui és dimecres");
    }
    
    /**
     * mètode amb cap paràmetre i retorna 1 valor
     */
    public static String dema(){
        return "dijous";
    }
}
```
