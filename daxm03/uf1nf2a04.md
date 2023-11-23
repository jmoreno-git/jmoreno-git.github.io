# Estructures complexes

## Arrays amb diferents dimensions (arrays en dent de serra)

Com que cada element d'un array bidimensional és a la seva vegada un altre array, pot passar que cada array-element tingui diferent nombre d'elements, és a dir, que cada fila tingui un nombre d'elements diferent.

```java
int[][] arrName = {
    {10, 20, 30},
    {40, 50, 60, 70, 80},
    {90, 100}
};
```

Per recórrer aquests arrays podem fer servir l'atribut ***length*** de cada element array.

```java
// declaració d'un array 2D amb 2 files
int arr[][] = new int[2][];
//declarar primera fila amb 3 elements
arr[0] = new int[3];
//declarar segona fila amb 2 elements 
arr[1] = new int[2];
//inicialitzar l'array amb valor seqüencials a cada element
int value = 0;
for (int i=0; i<arr.length; i++)
    for (int j=0; j<arr[i].length; j++) {
        arr[i][j] = value++;
    }
}
//mostrar els elements de l'array
System.out.println("Contingut de l'array");
for (int i = 0; i<arr.length; i++) {
    for (int j=0; j<arr[i].length; j++) {
        System.out.print(arr[i][j] + " ");
    }
    System.out.println();
}
```