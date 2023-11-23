# Exercicis d'estructures de dades (arrays)


## Exercicis d'arrays unidimensionals

[Enunciats dels exercicis](assets/1.2/dam-m03-exerc-estruct_dades-arrays_1d.pdf)

### Exemples de solucions als exercicis d'arrays unidimensionals

```java
import java.util.Scanner;

/**
 * Programa que entra dos valors i crea un array amb tots els valors enters
 * compresos entre els dos valors entrats (ambdós inclosos)
 *
 * @author Jose
 */
public class Ex05 {

    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        //llegir el nombre de números a entrar
        System.out.print("Entra el primer valor: ");
        int primer = lector.nextInt();
        //llegir el valor a posar a l'array
        System.out.print("Entra el segon valor: ");
        int segon = lector.nextInt();
        if (primer < segon) {  //comprovar que els valors entrats estan ordenats
            //calcular longitud requerida per a l'array
            int longitud = segon - primer + 1;
            //declarar i instanciar array
            int[] dades = new int[longitud];
            //inicialitzar l'array amb enters entre el primer i el segon número
            for (int i = 0; i < dades.length; i++) {
                dades[i] = primer + i;
            }
            //mostrar l'array
            for (int i = 0; i < dades.length; i++) {
                System.out.format("dades[%d]=%d\n", i, dades[i]);
            }
        } else {  //informar error números no correctament ordenats
            System.out.println("El primer ha de ser menor que el segon");
        }

    }

}
```


```java
import java.util.Random;
import java.util.Scanner;

/**
 * Programa per crear un array de longitud donada, inicialitzar-lo amb dades
 * aleatòries menors que un enter donat, mostrar l'array resultant, demanar un
 * número a l'usuari i comptar i mostrar quants elements de l'array són menors
 * al número introduït.
 *
 * @author Jose
 */
public class Ex06 {

    public static void main(String[] args) {
        //número d'elements de l'array (longitud)
        final int NUM_ELEMS = 100;
        //valor màxim a generar aleatòriament
        final int MAX_VALOR = 1000;
        Scanner lector = new Scanner(System.in);
        //declarar i instanciar array
        int[] dades = new int[NUM_ELEMS];
        //omplir array amb dades aleatòries
        ompleArrayAmbNombresAleatoris(dades, MAX_VALOR);
        //imprimir l'array
        String stringArray = arrayToString(dades);
        System.out.println(stringArray);
        //llegir el valor
        System.out.print("Entra el valor: ");
        int valor = lector.nextInt();
        //comptar els que són menors que el valor introduït
        int comptadorMenors = comptarMenors(dades, valor);
        //mostrar comptador
        System.out.format("El nombre d'elements menors que %d és %d\n",
                valor, comptadorMenors);
    } //fi main()

    /**
     * omple un array amb dades aleatòries amb el valor màxim indicat
     *
     * @param arr l'array a omplir
     * @param maxim el valor màxim a generar aleatòriament
     */
    private static void ompleArrayAmbNombresAleatoris(int[] arr, int maxim) {
        Random rnd = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rnd.nextInt(maxim);
        }
    }

    /**
     * converteix array en String
     *
     * @param arr l'array a convertir
     * @return format string de l'array
     */
    private static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < arr.length - 1; i++) {
            sb.append(arr[i]);
            sb.append(",");
        }
        sb.append(arr[arr.length - 1]);
        sb.append("]");
        return sb.toString();
    }

    /**
     * compta els elements de 'dades' menors que 'valor'
     *
     * @param dades l'array amb els elements a comptar
     * @param valor el valor límit per comptar
     * @return el nombre d'elements menors que el valor
     */
    private static int comptarMenors(int[] dades, int valor) {
        int comptador = 0;
        for (int i = 0; i < dades.length; i++) {
            if (dades[i] < valor) {
                comptador++;
            }
        }
        return comptador;
    }

}
```
Observeu que es poden modificar els valors d'un array passat a un mètode. Tot i que el mètode rep una còpia de la referència a l'array, a través d'aquesta referència pot accedir als seus elements i modificar-los. El que no es pot es modificar la referència original a l'array (la del mètode main que s'ha passat com a argument).

### Mètodes i arrays: creació i retorn des del mètode versus pas com a paràmetre

```java
import java.util.Scanner;

/**
 * Crear array amb dades de progressió aritmètica
 * Creem l'array i l'inicialitzem amb les dades de la progressió,
 * tot dintre d'una funció, la qual retorna l'array.
 * @author José
 */
public class Ex07 {

    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        //llegir dades
        //llegir el nombre d'elements de la progressió
        System.out.print("Entra la longitud: ");
        int longitud = lector.nextInt();
        //llegir el primer element
        System.out.print("Entra el primer element: ");
        double primer = lector.nextDouble();
        //llegir la diferència
        System.out.print("Entra la diferència: ");
        double diferencia = lector.nextDouble();      
        //generar progressió aritmètica
        double [] progrArit = generarProgAritm(longitud, primer, diferencia);
        //mostrar progressió aritmètica
        imprimirProgressio(progrArit);
    }
    
    /**
     * instancia un array de la longitud especificada.
     * i l'omple amb les dades d'una progressió aritmètica
     * de valor inicial 'a0' i diferència 'dif'
     * @param longitud el nombre d'elements a generar
     * @param a0 el primer element
     * @param dif la diferència
     * @return array amb la progressió aritmètica
     */
    private static double [] generarProgAritm(int longitud, double a0, double dif) {
        double [] pa = new double[longitud];
        for (int i = 0; i < pa.length; i++) {
            pa[i] = a0 + i*dif;
        }
        return pa;
    }
    
    /**
     * imprimeix una progressió
     * @param dades la progressió a imprimir
     */
    private static void imprimirProgressio(double [] dades) {
        for (int i = 0; i < dades.length; i++) {
            System.out.format(" %.2f",dades[i]);
        }
        System.out.println("");
    }
    
}
```

```java
import java.util.Scanner;

/**
 * Crear array amb dades de progressió aritmètica
 * Passem l'array a una funció (còpia de la referència)
 * i omplir l'array amb les dades de la progressió a la funció
 * @author José
 */
public class Ex07b {

    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        //llegir dades
        //llegir el nombre d'elements de la progressió
        System.out.print("Entra la longitud: ");
        int longitud = lector.nextInt();
        //llegir el primer element
        System.out.print("Entra el primer element: ");
        double primer = lector.nextDouble();
        //llegir la diferència
        System.out.print("Entra la diferència: ");
        double diferencia = lector.nextDouble();      
        //generar progressió aritmètica
        //crear array
        double [] progrArit = new double[longitud];
        omplirProgAritm(progrArit, primer, diferencia);
        //mostrar progressió aritmètica
        imprimirProgressio(progrArit);
    }
    
    /**
     * omple un array amb les dades d'una progressió aritmètica
     * de valor inicial 'a0' i diferència 'dif'
     * @param pa array a omplir
     * @param a0 el primer element
     * @param dif la diferència
     */
    private static void omplirProgAritm(double [] pa, double a0, double dif) {
        for (int i = 0; i < pa.length; i++) {
            pa[i] = a0 + i*dif;
        }
    }
    
    /**
     * imprimeix una progressió
     * @param dades la progressió a imprimir
     */
    private static void imprimirProgressio(double [] dades) {
        for (int i = 0; i < dades.length; i++) {
            System.out.format(" %.2f",dades[i]);
        }
        System.out.println("");
    }
    
}
```
El mètode ```void omplirProgAritm(double [] pa, double a0, double dif)``` rep al paràmetre ```double [] pa``` una còpia de la referència de l'array instanciat al main ```double [] progrArit```. Amb aquesta referència pot accedir a l'array i modificar els seus elements. El que no es pot modificar és la referència original progrArit, ja que el mètode només en té una còpia (el pas de paràmetres és per valor).

## Exercicis d'arrays bidimensionals

[Enunciats dels exercicis](assets/1.2/dam-m03-exerc-estruct_dades-arrays_2d.pdf)

### Exemples de solucions als exercicis d'arrays bidimensionals

```java
import java.util.Arrays;
import java.util.Random;

/**
 * generar 3 vectors, copiar-los en una matriu
 * i afegir una columna amb la suma de cada vector
 * @author Jose
 */
public class Ex01 {

    public static void main(String[] args) {
        //definir dimensió
        final int N = 10;
        //declarar vectors d'enters (i instanciar-los)
        int [] v1 = new int[N];
        int [] v2 = new int[N];
        int [] v3 = new int[N];
        //inicialitzar dades als vectors
        inicialitzarVectorAleatori(v1);
        inicialitzarVectorAleatori(v2);
        inicialitzarVectorAleatori(v3);
        //mostrar dades generades
        System.out.println("v1: "+Arrays.toString(v1));
        System.out.println("v2: "+Arrays.toString(v2));
        System.out.println("v3: "+Arrays.toString(v3));
        //generar matriu de dimensions 3x(N+1)
        int [][] m = new int[3][N+1];
        //declarar variable acumulador per a les sumes
        int suma;
        //omplir fila 0 de la matriu amb vector v1
        suma = 0;  //inicialitzar acumulador a 0
        for (int i = 0; i < N; i++) {
            m[0][i] = v1[i];  //copiar vector a matriu
            suma += v1[i];  //acumular suma elements vector
        }
        m[0][N] = suma;
        //omplir fila 1 de la matriu amb vector v2
        suma = 0;  //inicialitzar acumulador a 0
        for (int i = 0; i < N; i++) {
            m[1][i] = v2[i];
            suma += v2[i]; 
        }
        m[1][N] = suma;        
        //omplir fila 2 de la matriu amb vector v3
        suma = 0;  //inicialitzar acumulador a 0
        for (int i = 0; i < N; i++) {
            m[2][i] = v3[i];
            suma += v3[i]; 
        }
        m[2][N] = suma; 
        //mostrar resultat
        imprimirMatriu(m);
    }
    
    /**
     * inicialitza array amb dades aleatòries
     * @param v array a inicialitzar
     */
    public static void inicialitzarVectorAleatori(int [] v) {
        Random rnd = new Random();
        final int MAXIM = 100;  //valor màxim a generar aleatóriament
        for (int i = 0; i < v.length; i++) {
            v[i] = rnd.nextInt(MAXIM);
        }
    }
    
    /**
     * imprimeix una taula bidimensional 
     * @param dades la taula a imprimir
     */
    public static void imprimirMatriu(int [][] dades) {
        for (int i = 0; i < dades.length; i++) { //files
            for (int j = 0; j < dades[i].length; j++) { //columnes
                System.out.print("\t"+dades[i][j]);
            }
            System.out.println("");
        }
    }
    
}
```

```java

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Manages student grades for a subject
 * Interacts with user through a menu
 * Stores student's names in an array 
 * and student grades in a bidimensional array
 * @author Jose
 */
public class GradeManager1 {
    
    /* CLASS ATTRIBUTES */

    //list of student's names
    public static String [] studentNames;
    //table of student's grades (one student per row, columns are uf grades)
    public static int [][] studentGrades;
    //current number of students
    public static int numStudents;
    
    /* METHODS */

    public static void main(String[] args) {
        /*    DATA DEFINITION    */
        //màximum number of students
        final int maxStudents = 30;
        //current number of students
        numStudents = 0;
        //màximum number of grades to manage (one per UF)
        final int numGrades = 6;
        //program menu
        final String [] menuOptions = {
            "Exit", 
            "List name of all students",
            "List all grades of a student (given their name)",
            "List all student's grades for a given UF",
            "List all grades of all students",
            "Set grade for a student and UF"
        };
        //array of student's names
        studentNames = new String[maxStudents];
        //array of grades
        studentGrades = new int[maxStudents][numGrades];
        //exit flag
        boolean exit = false;
        //load data (student's names and grades)
        /*    PROGRAM EXECUTION    */
        loadData();  //set initial test data
        //control loop
        do {
            //display menu and read user's option
            int optionSelected = displayMenuAndReadUserChoice(menuOptions);
            //execute option selected by user
            switch (optionSelected) {
                case 0:  //exit program
                    exit = true;
                    break;
                case 1: //list name of all students
                    listAllStudentNames();
                    break;
                case 2: //list all grades of a student (given their name)
                    listAllGradesOfAStudent();
                    break;
                case 3: //list all student's grades for a given UF
                    listAllGradesByUf();
                    break;
                case 4: //list all grades of all students
                    listAllGradesOfAllStudents();
                    break;
                case 5: //Set grade for a student and UF
                    gradeForStudentAndUF();
                    break;
                default:
                    System.out.println("Bad option");
                    break;
            }
        } while (!exit);
        System.out.println("Closing program");
    }
    
    /**
     * displays options of program menu
     * and inputs user's choice
     * @param options the array of options of the menu
     * @return index of selected option
     */
    private static int displayMenuAndReadUserChoice(String [] options) {
        //display menu title
        System.out.println("===== Grade manager menu =====");
        //display options
        for (int i = 0; i < options.length; i++) {
            System.out.format("[%d]. %s\n", i, options[i]);
        }
        //read user's choice
        Scanner scan = new Scanner(System.in);
        int choice = -1;
        while (choice < 0) {
            try {
                //ask user's choice
                System.out.print("Select an option: ");
                choice = scan.nextInt();
                //validate choice
                if ((choice<0) || (choice>=options.length)) {
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                scan.nextLine();
                choice = -1;
            }
        }
        return choice;
    }

    /*    CONTROL METHODS    */    
    
    /**
     * lists names of all students
     */
    private static void listAllStudentNames() {
        System.out.println("Student's names");
        for (int i = 0; i < numStudents; i++) {
            System.out.println(studentNames[i]);
        }
    }

    /**
     * inputs student's name and lists all studentGrades of that student
     */
    private static void listAllGradesOfAStudent() {
        Scanner scan = new Scanner(System.in);
        //input student's name
        System.out.print("Student's name: ");
        String name = scan.nextLine();
        //find student index (row)
        int studentRow = indexOfStudentName(name);
        if (studentRow < 0) {  //not found
            System.out.format("There is no student with name '%s'\n", name);
        } else {
            //list studentGrades of found student
            System.out.println("Grades of student: "+name);
            for (int i = 0; i < getNumCols(studentGrades); i++) {
                System.out.format("%d ", studentGrades[studentRow][i]);
            }
            System.out.println("");
        }
    }
    
    /**
     * lists all studentGrades of all students
     */
    private static void listAllGradesOfAllStudents() {
        for (int i = 0; i < numStudents; i++) {
            System.out.println("Grades of student: "+studentNames[i]);
            for (int j = 0; j < getNumCols(studentGrades); j++) {
                System.out.format("%d ", studentGrades[i][j]);
            }
            System.out.println("");
        }
    }
    
    /*     USEFUL METHODS     */
    
    /**
     * searches a student by name
     * @param name the name to search
     * @return index of student or -1 if not found
     */
    private static int indexOfStudentName(String name) {
        int index = -1;
        for (int i = 0; i < numStudents; i++) {
            if (studentNames[i].equals(name)) {
                index = i;
                break;
            }
        }
        return index;
    }
    
    /**
     * gets the number of rows of a bidimensional array
     * @param data the array
     * @return number of rows
     */
    private static int getNumRows(int [][] data) {
        return data.length;
    }
    
    /**
     * gets the number of columns of a bidimensional array
     * assuming there is at least one row
     * @param data the array
     * @return number of columns
     */
    private static int getNumCols(int [][] data) {
        return data[0].length;
    }
    
    /**
     * loads initial data to test the program
     * @return the actual number of students after operation
     */
    private static void loadData() {
        addStudent("StudName01");
        addStudent("StudName02");
        addStudent("StudName03");
        addStudent("StudName04");
        addStudent("StudName05");
        addStudent("StudName06");
        addStudent("StudName07");
        addStudent("StudName08");
        addStudent("StudName09");
        addStudent("StudName10");
        addStudent("StudName11");
        addStudent("StudName12");
    }

    /**
     * adds a new student and set random grades (for testing)
     * @param name the name of the student to set
     */
    private static void addStudent(String name) {
        if (numStudents<studentNames.length) {
            studentNames[numStudents] = name;
            Random rnd = new Random();
            for (int i = 0; i < getNumCols(studentGrades); i++) {
                studentGrades[numStudents][i] = rnd.nextInt(1, 10);
            }
            numStudents++;
        }
    }    

    /**
     * inputs the index of the grade to list (UF),
     * and lists the name of each student 
     * with their grade for that UF
     */
    private static void listAllGradesByUf() {
        //input index of grade (column of studentGrades array to display)
        Scanner scan = new Scanner(System.in);
        System.out.print("Index of grade to list: ");
        int gradeIndex = scan.nextInt();
        //TODO validate 'gradeIndex'
        //display name and grade for each student
        for (int i = 0; i < numStudents; i++) {  //for each row (student)
            //display name and grade
            System.out.format("%s\t%d\n", studentNames[i], studentGrades[i][gradeIndex]);
        }
        
    }

    /**
     * input name of student, UF to grade and grade to set,
     * find student's name index;
     * if found, it sets the given grade to given value,
     * if not found, it reports that to user.
     * Finally,it reports result of action to user
     */
    private static void gradeForStudentAndUF() {
        Scanner scan = new Scanner(System.in);
        //input student's name
        System.out.print("Student's name: ");
        String name = scan.nextLine();
        //input grade index (UF)
        System.out.print("UF: ");
        int uf = scan.nextInt();
        //input grade value
        System.out.print("Grade value :");
        int grade = scan.nextInt();
        //search student's name in students list
        int row = indexOfStudentName(name);
        if (row == -1) {  //not found
            System.out.println("There is no student with name "+name);
        } else {
            //set grade
            studentGrades[row][uf] = grade;
            //report result to user
            System.out.println("Grade successfully set!");
        }
    }
    
}
```