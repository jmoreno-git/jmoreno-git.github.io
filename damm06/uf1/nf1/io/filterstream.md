# Filter Stream

Un **Filter Stream,** es construeix sobre un altre stream (anomenat *underlying stream*) per filtrar les dades a mesura que es van llegint o escrivint. Un **filter stream** té diverses aplicacions en funció de la classe que usem per implementar-lo. Per exemple poden convertir les dades a un altre format o realitzar tasques de *buffer*. Dins la jerarquia de java, aquesta funcionalitat s’encapsula en les classes [**FilterOutputStream**](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/FilterOutputStream.html) i [**FilterInputStream**](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/FilterInputStream.html). 

![filteriostream.png](/damm06/assets/1.1/1.1.io/filteriostream.png)

Una de les aplicacions d’aquestes classes és la **compressió de dades**, és a dir  la possibilitat de transmetre i llegir dades comprimides de manera transparent. La manera més simple de fer-ho és mitjançant les classes [**GzipInputStream**](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/zip/GZIPInputStream.html) i [**GzipOutputStream**](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/zip/GZIPOutputStream.html), que usen l’algorisme de compressió GZIP. Cap de les dues classes inclou nous mètodes fora dels definits en les superclasses *InputStream* i *OutputStream*. A mesura que s’escriuen o es llegeixen dades amb els mètodes **write()** o **read()**, aquestes es comprimeixen automàticament sense que sigui necessari fer cap altra tasca addicional.

Anàlogament, les clases [**ZIPOutputStream**](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/zip/ZipOutputStream.html) i [**ZIPInputStream**](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/zip/ZipInputStream.html) permeten escriure i llegir dades en format ZIP.

Per llegir les entrades d'un fitxer comprimit zip disposem de la classe [ZipFile](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/zip/ZipFile.html).
El paquet [java.util.zip](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/zip/package-summary.html]) proveeix tot el conjunt de classes necessàries per escriure i llegir fitxers ZIP i GZIP.

**Exemple**

[ZipExamples.zip](/damm06/assets/1.1/1.1.io/zipexamples.zip)


