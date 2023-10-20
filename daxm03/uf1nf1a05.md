# Estructures iteratives

[Eina per fer diagrames de flux: diagrams](https://app.diagrams.net/)

## Introducció

Els ordinadors estan especialment dissenyats per a les aplicacions en les quals una operació o una sèrie s'han de repetir moltes vegades. La construcció de programació corresponent a aquest cas és el llaç o el **bucle**.

Els bucles es poden classificar en funció de la condició de sortida del mateix de dues maneres:

- Bucles condicionals.
- Bucles comptats.

## Bucles condicionals

El bloc de codi a repetir s'executa mentres que se satisfà una certa condició. Quan la condició és falsa, se surt del bucle i es continua executant la següent instrucció que segueix.

A cada iteració (repetició) s'avalua novament la condició. En funció del moment en què s'avalua la condició de manteniment del bucle es classifiquen en:

- Bucles condicionals provats a l'inici
- Bucles condicionals provals al final

### Bucle condicional provat a l'inici (while)

La condició s'avalua abans d'executar el bloc intern. Per tant, si la condició no es compleix, el codi del bucle no s'executa cap vegada.

![Representació gràfica bucle condicional provat a l'inici](assets/1.1/diag_flux-while.jpg)

```java
while (condicio) {
  //bloc a executar mentre es compleixi la condicio
  //si no es compleix la primera vegada, aquest bloc no s'executa mai
}
```

### Bucle condicional provat al final (do-while)

La condició s'avalua al final del bloc intern (després de la seva execució). Per tant, sempre s'executa el bloc intern almenys una vegada.

![Representació gràfica bucle condicional provat al final](assets/1.1/diag_flux-do_while.png)

```java
do {
  //bloc a executar mentre es compleixi la condicio
  //encara que la condició sigui falsa la primera vegada, el bloc s'executa una vegada
} while (condicio);
```

## Bucles comptats (for)

S'utilitzen generalment quan es coneix el nombre de vegades que el bloc del bucle s'ha de repetir. Fan servir un comptador per controlar el nombre d'iteracions.

![Representació gràfica bucle comptat](assets/1.1/diag_flux-for.gif)

Els bucles *for* en llenguatge *Java* són molt potents i tenen moltes més possibilitats d´us que l'especificat per al recompte d'iteracions.

```java
for (inicialitzacio; condicio; actualitzacio) {
  //bloc a executar mentre es compleixi la condicio
  //si no es compleix la primera vegada, aquest bloc no s'executa mai
}
```

La inicialització s'executa només la primera vegada, en entrar al bucle.

La condició s'avalua i es comprova cada vegada que s'itera.

L'actualització s'executa cada vegada que s'itera al bucle, al igual que el bloc intern.

## Niuament de bucles

El codi de l'interior del bucle pot també contenir altres bucles, generant estructures per respondre a problemes complexos.

[El algorisme de l'amistat - Sheldon Cooper (The Big Bang Theory)](https://www.youtube.com/watch?v=H3z3HDbl5QU)

## Exemples

```java
/**
 * Exemple de bucles for niats
 * Il·lustra les iteracions que es produeixen als bucles (extern i intern)
 * @author Jose
 */
public class BuclesForNiats {

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            System.out.format("Bucle extern i=%d%n", i);
            for (int j = 0; j < 5; j++) {
                System.out.format("\tBucle intern i=%d, j=%d%n", i, j);
            }
        }
    }
    
}

```

La sortida del programa BuclesforNiats és la següent:

```text
Bucle extern i=0
 Bucle intern i=0, j=0
 Bucle intern i=0, j=1
 Bucle intern i=0, j=2
 Bucle intern i=0, j=3
 Bucle intern i=0, j=4
Bucle extern i=1
 Bucle intern i=1, j=0
 Bucle intern i=1, j=1
 Bucle intern i=1, j=2
 Bucle intern i=1, j=3
 Bucle intern i=1, j=4
Bucle extern i=2
 Bucle intern i=2, j=0
 Bucle intern i=2, j=1
 Bucle intern i=2, j=2
 Bucle intern i=2, j=3
 Bucle intern i=2, j=4
```

```java
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Programa que llegeix un nombre enter positiu N
 * i mostra els primers N nombres enters positius
 * @author Jose
 */
public class PrimersNNaturals {
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        //llegir el número
        System.out.print("Entra el número enter positiu: ");
        try {
            int limit = lector.nextInt();
            if (limit > 0) {
                //amb bucle for
                System.out.println("\nAmb bucle for");
                for (int num = 1; num <= limit; num++) {
                    System.out.format("%d ", num);
                }
                //amb bucle while (provat a l'inici)
                System.out.println("\nAmb bucle while (provat a l'inici)");
                int iter1=1;
                while (iter1 <= limit) {
                    System.out.format("%d ", iter1);
                    iter1++;
                }
                //amb bucle do while (provat al final)
                System.out.println("\nAmb bucle do while (provat al final)");
                int iter2=1;
                do {
                    System.out.format("%d ", iter2);
                    iter2++;
                } while (iter2 <= limit);
            } else {
                System.out.println("Has d'entrar un nombre positiu");
            }            
        } catch (InputMismatchException e) {
            System.out.println("Has d'entrar un número enter positiu");
        }
    }
}
```

```java
/**
 * Imprimeix els primers 20 nombres naturals
 * @author Jose
 */
public class Print20Enters {
    public static void main(String[] args) {
        final int LIMIT = 20;
        //amb bucle while
        //inicialitzar comptador
//        int comptador = 1;
//        while (comptador <= LIMIT) {
//            System.out.println(comptador);
//            comptador++;
//        }  
        //amb bucle for
//        for (int i=1; i<=LIMIT; i++) {
//            System.out.println(i);
//        }
        //amb bucle do while
        int comptador = 1;
        do {
            System.out.println(comptador);
            comptador++;
        } while (comptador <= LIMIT);
    }
}
```

## Generació de nombres aleatoris

Introduim aquí la generació de nombres aleatoris, ja que és necessària per al desenvolupament de molts jocs, els quals impliquen que la interacció amb l'usuari sigui iterativa.

Per generar nombres aleatoris podem utilitzar el mètode [Math.random()](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/lang/Math.html#random()), el qual retorna un nombre pseudoaleatori real de l'interval [0, 1). Si cal canviar l'interval, s'ha de reescalar el resultat, tal com es fa a l'exemple.

Alternativament, podem usar els mètodes de la classe [Random](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/util/Random.html).

```java
import java.util.Random;

/**
 * Random generation example
 * @author Jose
 */
public class RandomNumbers {

    public static void main(String[] args) {
        //generate a floating point random number using Math.random()
        double r1 = Math.random();
        System.out.println("Random float number in [0, 1) using Math.random(): " + r1);
        //generate an integer random number in interval [MIN, MAX) using Math.random()
        final int MIN = 100;
        final int MAX = 200;
        int r2 = (int) (MIN + (MAX-MIN) * Math.random());
        System.out.format("Random integer number in [%d, %d) using Math.random(): %d\n", MIN, MAX, r2);
        //generate an integer random number in interval [MIN, MAX) using class Random
        Random rnd = new Random();
        int r3 = rnd.nextInt(MIN, MAX);
        System.out.format("Random integer number in [%d, %d) using Random.nextInt(): %d\n", MIN, MAX, r3);
    }
    
}
```
