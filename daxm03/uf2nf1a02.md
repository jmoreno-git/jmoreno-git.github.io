# Àmbit, visibilitat i extensió de variables

## Pas de paràmetres a mètodes

El pas de paràmetres als mètodes es fa mitjançant el **pas per valor**. Els valors que s’utilitzen en la invocació al mètode s’anomenen **arguments**, mentre que els que utilitza el mètode són els **paràmetres** (també anomenats paràmetres formals). Java fa una còpia dels arguments en els paràmetres formals. 

El comportament en la invocació és diferent segons que el paràmetre tingui un tipus primitiu o referenciat. En el cas que els tipus del paràmetre sigui un tipus de dades **primitiu**, es passa una còpia del valor de l’argument. En canvi, si el tipus del paràmetre és un tipus de dades **referenciat**, el que es passa és una còpia d’una referència a l’objecte (apuntador).

Per il·lustrar aquesta qüestió, editem el programa *PassTest.java* i analitzem-ne la sortida.

```java
/**
 * Classe PassTest: Comprovació que el pas de paràmetres és per valor
 */
public class PassTest {
	float ptValue;
	//Métodes per canviar els valors actuals
	public void changeInt(int value) {
		value = 55;
	}
	public void changeStr(String value) {
		value = new String("diferent");
	}
	public void changeObjValue(PassTest ref) {
		ref.ptValue = 99.0f;
	}
	public void changeObjValue2(PassTest ref) {
		PassTest ptTemp = new PassTest();
		ptTemp.ptValue = 34;
		ref = ptTemp;
	}
	public static void main (String args []) {
		String str;
		int val;
		//Crea una instància de la classe
		PassTest pt = new PassTest();
		//Assigna un enter
		val = 11;
		//intenta canviar-lo
		pt.changeInt(val);
		//Mostra valor actual
		System.out.println("El valor de val es: "+val);
		//Assigna cadena de text
		str = new String("Hola");
		//intenta canviar-la
		pt.changeStr(str);
		//Mostra valor actual
		System.out.println("El valor de str es: "+ str);
		//Prova de canvi del valor de String
		str = new String("Adéu"); 
		System.out.println("El valor de str es: "+ str);
		//Assigna valor a ptValue
		pt.ptValue = 101.01f;
		//canvar el valor, a travès del punter a objecte
		pt.changeObjValue(pt);
		//Mostra valor actual
		System.out.println("El valor de pt.ptValue es: "+ pt.ptValue);
		//Assigna valor a ptValue
		pt.ptValue = 101.01f;
		//s'intenta canviar el valor, reassignant el punter a objecte
		pt.changeObjValue2(pt);
		//Mostra valor actual
		//Es comprova que el valor no ha canviat, perquè no permet canviar
		//el valor del punter a l'objecte
		System.out.println("El valor de pt.ptValue es: "+ pt.ptValue);
	}// Fi main()
}//Fi class        
```
El mètode *main()* crea un tipus primitiu (*int val*), un tipus referenciat (*String str*) i una instància de la pròpia classe (*PassTest pt = new PassTest()*) per utilitzar-la com a mitjà per accedir a l’atribut *float ptValue*.

La sortida del programa és la següent:

  El valor de val es: 11
  El valor de str es: Hola
  El valor de str es: Adéu
  El valor de pt.ptValue es: 99.0
  El valor de pt.ptValue es: 101.01

Com veieu, val no es modifica amb *pt.changeInt(val)* perquè el mètode només pot accedir i modificar la còpia local del paràmetre (*value*).

Malgrat ser una referència, *str* tampoc no es modifica amb *pt.changeStr(str)* perquè el que fa el mètode és canviar la referència (posició a on apunta) la còpia local del paràmetre value. En canvi, sí que es modifica des del main quan es canvia directament.

A través de la invocació pt.changeObjValue(pt) es modifica l’atribut ptValue perquè el paràmetre és una referència a l’objecte. 

En canvi, quan fem la crida *pt.changeObjValue2(pt)*, l’atribut *ptValue* no es modifica perquè el mètode crea un nou objecte local *ptTemp* i després l’assigna al paràmetre. Amb això el que es fa és fer apuntar la còpia local del paràmetre *ref* al nou objecte local, però la referència original de la invocació no es veu afectada.

## Àmbit d'una variable

L'ambit de definició d'una variable és la part del codi des d'on la variable és accessible i pot ser utilitzada en una expressió a través de l'identificador amb què ha estat declarada.

L'àmbit de les variables va des del punt en què són declarades fins al final del bloc que les conté (el bloc ve delimitat pels símbols {}).

Els blocs interns de codi tenen accés a les variables dels blocs que els contenen, llevat que es tracti de mètodes.

Totes les variables declarades dintre de mètodes són locals als mètodes. Els paràmetres dels mètodes també són locals. Per tant, no es poden utilitzar fora del mètode.

Els atributs d'un objecte declarats publics poden ser accedits des de fora del bloc de la classe, sempre usant la variable de l'objecte com a referència. 

## Visibilitat d'una variable

La visibilitat d'una declaració de variable és la part del codi on la variable té àmbit i l'identificador referencia la variable. 

La visibilitat acostuma a coincidir amb l'àmbit, però hi ha casos en què una declaració en un bloc intern usant el mateix identificador fa invisible la declaració feta al bloc exterior. En aquest cas, preval la declaració interna, i l'externa perd visibilitat al bloc intern, tot i que es manté l'ambit.

## Extensió o temps de vida d'una variable

L'extensió d'una variable fa referència a la part de codi on la variable té assignat emmagtzemament en memòria. 

Les variables locals als mètodes i els seus paràmetres es creen en entrar al mètode i es destrueixen en sortir-ne. Per tant, la seva extensió és el cos del mètode.

Els atributs dels objectes tenen extensió mentre el té l'objecte i la perden quan l'objecte és destruït.

El següent exemple il·lustra els diferents àmbits de definició de variables.

```java
/**
 * Exemples d'àmbits de variables
 * @author Jose
 */
public class Ambits {

    /**
     * scope: the whole class
     * visibility = scope except when being hidden by another variable with the same name and local scope 
     * extension (non-static): since object instantiation until destruction.
     * extension (static): the whole program execution.
    */
    private static String msg = "Class attribute"; //scope the whole class
    
    public static void main(String[] args) {
        /**
         * scope: since declaration to end of block
         * visibility = scope except when being hidden by another variable with the same name and local scope 
         * extension: since start of function execution until return
        */
        String msg = "Local in main"; //scope: main function. Hides visibility of msg class attribute.
        System.out.println("* f1(\"Function argument\")");
        f1("Function argument");
        System.out.println("* f1(msg)");
        f1(msg);
        System.out.println("* f2()");
        f2();
        System.out.println("* f3()");
        f3();
        System.out.println("* f4(msg)");
        f4(msg);
        System.out.println("* main:println(msg)");
        System.out.println(msg);
        System.out.println("* Ambits.msg");
        System.out.println(Ambits.msg);
    }
    
    public static void f1(String msg) {
        //msg parameter has local scope and hides visibility of msg class attribute in function
        System.out.println(msg);
    }
    
    public static void f2() {
        /**
         * scope: since declaration to end of block
         * visibility = scope except when being hidden by another variable with the same name and local scope 
         * extension: since start of function execution until return
        */
        String msg = "Local variable";
        //msg local variable has local scope and hides visibility of msg class attribute in function
        System.out.println(msg);
    }
    
    public static void f3() {
        //msg class attribute keeps visibility in function
        System.out.println(msg);
    }
    
    public static void f4(String msg) {
        msg = "Function parameter changed";
        System.out.println(msg);
    }
}
```

## Exemples

### Mètode per preguntar informació a l'usuari

```java
/**
 * mostra un missatge i llegeix una resposta de l'usuari
 * @param missatge el missatge a mostrar a l'usuari
 * @return la resposta de l'usuari
 */
public String entraString(String missatge) {
	Scanner lector = new Scanner(System.in);
	System.out.print(missatge);
	return lector.next();
}
```

### Programa amb menú per a interactuar amb l'usuari

Exemple d'un programa de conversió entre dues monedes usant un menú per interactuar amb l'usuari i mètodes de control per respondre a les seves accions.

```java
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Realitza conversions de moneda entre euros i dòlars. Presenta a l'usuari un
 * menú per escollir la conversió fins que demana sortir.
 *
 * @author ProvenSoft
 */
public class ConversorMoneda {

    public static void main(String[] args) {

        boolean sortir = false; //senyal per sortir

        do {
            //mostrar menu i llegir opcio
            int opcioSel = mostraMenu();
            //executar l'opció escollida
            switch (opcioSel) {
                case 0: //sortir
                    sortir = true;
                    break;
                case 1: //euros a dòlars
                    procConvEuroDolar();
                    break;
                case 2: //dòlars a euros
                    procConvDolarEuro();
                    break;
                default:
                    System.out.println("Opció no vàlida");
                    break;
            }
        } while (!sortir);

    } //final de main

    /**
     * mostra el menu del programa i llegeix l'opció escollida per l'usuari
     *
     * @return el número de l'opció escollida per l'usuari o -1 si no vàlida
     */
    public static int mostraMenu() {
        int opcio = -1;
        System.out.println("===Menú del programa de conversió euro-dòlar===");
        //mostrar opcions
        System.out.println("0. Sortir");
        System.out.println("1. Convertir d'euros a dòlars");
        System.out.println("2. Convertir de dòlars a euros");
        //llegir opció
        System.out.print("> Selecciona una opció: ");
        Scanner lector = new Scanner(System.in);
        try {
            opcio = lector.nextInt();
        } catch (InputMismatchException e ){
            opcio = -1;
        }
        return opcio;
    }

    /* ====== Mètodes de càlcul ====== */
    
    /**
     * converteix euros a dòlars
     *
     * @param euros els euros que s'han de convertir
     * @return la conversió a dòlars
     */
    public static double convEuroDolar(double euros) {
        final double EURO_DOLAR = 0.9;
        return euros * EURO_DOLAR;
    }

    /**
     * converteix dòllars to euros
     *
     * @param dolars els dòlars que s'han de convertir
     * @return la conversió a euros
     */
    public static double convDolarEuro(double dolars) {
        final double DOLAR_EURO = 1.0 / 0.9;
        return dolars * DOLAR_EURO;
    }

    /* ====== Mètodes de control ====== */
    
    /**
     * llegeix euros, converteix a dòlars i imprimeix resultat
     */
    private static void procConvEuroDolar() {
        Scanner lector = new Scanner(System.in);
        try {
            double euros = lector.nextDouble();
            double dolars = convEuroDolar(euros);
            System.out.format("%.2f euros equivalen a %.2f dòlars\n", euros, dolars);
        } catch (InputMismatchException e) {
            System.out.println("Entrada de dades incorrecta");
        }
    }

    /**
     * llegeix dòlars, converteix a euros i imprimeix resultat
     */
    private static void procConvDolarEuro() {
        Scanner lector = new Scanner(System.in);
        try {
            double dolars = lector.nextDouble();
            double euros = convDolarEuro(dolars);
            System.out.format("%.2f dòlars equivalen a %.2f euros\n", dolars, euros);
        } catch (InputMismatchException e) {
            System.out.println("Entrada de dades incorrecta");
        }
    }

}
```

## Exercici proposat: La ruleta

[Enunciat](assets/2.1/ruleta-enunciat.odt). [Javadoc de la solució](assets/2.1/ruleta-javadoc.pdf)

## Exercici proposat: Joc de pedra, paper i tissora

Implementar el [joc de pedra, paper i tissora](https://es.wikipedia.org/wiki/Piedra,_papel_o_tijera).

Cal implementar el joc d'un jugador contra la màquina.

Els  jugadors juguen simultàniament, es compara el resultat de les tirades i es decideix si hi ha empat o guanya un jugador. En aquest darrer cas s'incrementa el comptador del jugador guanyador.

A cada torn s'imprimeix el resultat dels comptadors dels jugadors.

Finalitza el joc quan un jugador guanya tres tirades (configurable) o quan s'arriba a un màxim de 6 tirades (configurable).

En finalitzar el joc es mostra el marcador final amb les puntuacions de cada jugador i s'indica quin és el guanyador o si hi ha empat.

En iniciar-se el joc, l'usuari determina el nombre de tirades que cal guanyar per acabar el joc i el nombre de tirades màximes abans de finalitzar.

Després s'inicia el bucle del joc, on l'ordinador fa la tirada escollint aleatòriament i pregunta la seva tirada a l'usuari. A continuació, es determina el resultat de la tirada i s'informa del resultat i dels marcadors.

Aquí teniu el [javadoc de la proposta de solució](assets/2.1/PedraPaperTissores-Javadoc.pdf).