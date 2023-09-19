# Fluxos d'accés directe

Permeten posicionar un punter de lectura i escriptura per decidir en quina posició del fitxer volem llegir o escriure de manera directa.

La classe [**RandomAccessFile**](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/RandomAccessFile.html) implementa les interfícies **DataInput** (tal com ho fa **DataInputStream**) i **DataOutput**. No obstant no està dins la jerarquia d’**InputStream**.

Aquesta classe ens permet realitzar accessos a una posició determinada del fitxer (accés relatiu/accés aleatori o accés directe) a diferència de l’accés seqüencial que ens proveeixen **FileInputStream** i **FileOutputStream**. 

![random_access_file_example_in_java.png](/damm06/assets/1.1/1.1.io/random_access_file_example_in_java.png)

Aquesta classe ens permet escriure i llegir bytes i tipus de dades primitives. 

**RandomAccesFile** treballa amb fitxers orientats a byte. Transforma el valor del tipus primitiu a una seqüència de bytes, segons el mètode invocat. El fitxer disposa d'un apuntador (*pointer*) que avança cada cop que llegim o escrivim dades. El seu valor es correspon a la distancia en bytes des de l'inici del fitxer fins la posició actual. Quan creem un objecte **RandomAccessFile**, l'apuntador val 0.

La posició de l'apuntador indica on començarà la següent lectura o escriptura.

Per saber exactament en quant ha variat l’apuntador del fitxer, cal saber quants bytes ocupa cada tipus primitiu del Java.

## Constructor i Modes d’accés

La classe **RandomAccesFile** ens ofereix dos constructors als quals hem d'indicar el recurs que llegirem i el mode d'accés.
 
```java
RandomAccessFile(File file, String mode)
RandomAccessFile(String name, String mode)
```

| Mode  |   ^ Significat      |
| r    | El fitxer s’obre en mode només lectura     | 
| rw    | El fitxer s’obre en mode lectura-escriptura. Si no existeix el fitxer, es crea. |

## Posicionament

**void seek(long pos)** 

Ubica l’apuntador exactament a la posició especificada pel paràmetre pos, mesurat en bytes, de manera que qualsevol accés a les dades serà sobre el byte següent. No hi ha cap restricció en el valor d’aquest paràmetre, essent possible ubicar l’apuntador molt mes enllà del final real del fitxer. 

**int skipBytes(int n)**

Avança la posició de l’apuntador en n bytes, de manera que aquest passa a valer valor actual + n. La seva invocació avalua el nombre de bytes que realment s’ha avançat. Cal tenir en compte que es pot donar el cas quan el valor resultant no sigui igual a n. 

**long getFilePointer()**

Retorna la posició actual del punter del fitxer. Indica la posició (en bytes) des d'on s’escriurà o llegirà.

**long length()**

Retorna la longitud del fitxer en bytes.

## Lectura i escriptura 

Per llegir tenim els mètodes, **read()** i  **readLine()** que permeten llegir byte a byte o per línia respectivament. També disposem d’un mètode per llegir cada tipus de dades primitiu: **readChar**, **readInt**, **readDouble**, **readBoolean**.

De forma anàloga per escriure tenim els mètodes **write(int b)**, que escriu el byte indicat per paràmetre i **writeBytes (String s)** que escriu la cadena de caràcters *s*. També disposem de mètodes *write* per cada tipus de dada primitiva: **writeChar**, **writeInt**, **writeDouble**, **writeBoolean**.

## Exemples

* [Exemple de programa que escriu i llegeix i modifica un fitxer amb la classe RandomAccessFile](/damm06/assets/1.1/1.1.io/randomwritereadfile.zip)
* [Programa que llegeix un número enter per la línia de comandes i l’afegeix al final d’un fitxer. Per validar el seu funcionament, el programa mostra el contingut del fitxer abans i després d’afegir el número introduït](/damm06/assets/1.1/1.1.io/randomex1.zip)

**Exercicis proposats:**
Feu un programa que escrigui una seqüència d'enters entrada per teclat.
Feu un programa que modifiqui un enter en un fitxer d’enters. Per a fer-ho, demana quina posició és la que ocupa l’enter que es vol modificar i demana el nou valor. Abans i després del canvi cal mostrar el contingut del fitxer.

**Exercici opcional:**
Feu un programa que, donat un fitxer orientat a byte que conté qualsevol nombre de valors reals, els ordeni de menor a major. Aquesta tasca l’ha de dur directament sobre el fitxer, i no pas carregant les dades a un array, ordenant i després escrivint-les de nou al fitxer. Per veure que funciona, fer que mostri per pantalla els valors continguts abans i després de l’ordenació. Cal que creeu també el fitxer de proves.
