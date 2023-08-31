# Biblioteca NIO. Channels i Buffers

A partir de la versió 1.4, Java inclou la llibreria *NIO*, una aproximació basada en canals i orientada a buffers de les operacions d'entrada/sortida. A partir de la versió JDK7, el sistema NIO es va ampliar i millorar, fins el punt que sovint es referencia com a NIO.2.

## Paquets i classes NIO

[https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/nio/package-summary.html](Java NIO package)

La següent taula mostra alguns paquets d'interès que inclou la llibreria *NIO*:

| nom                     | descripció                                                                                                                      |
| ------------------------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| java.nio                | Paquet arrel del sistema NIO. Encapsula diversos tipus de buffers que contenen dades que s'usen al sistema NIO.                  |
| java.nio.channels       | Dóna suport als canals, base de les connexions d'E/S.                                                                           |
| java.nio.charset        | Encapsula conjunts de caràcters. També incorpora codificadors i decodificadors per a convertir caràcters a bytes i viceversa. |
| java.nio.file           | Dóna suport a operacions amb fitxers.                                                                                           |
| java.nio.file.attribute | Dóna suport a operacions amb atributs de fitxers.                                                                               |

## A. Gestió de fitxers: paquet java.nio.file

Les seves classes principals son:

* **FileSystem**: representació del sistema de fitxers. Factoria d'objectes tipus Path.
* **Path**: representa un fitxer o directori del sistema de fitxers.
* **Files**: conté un conjunt de mètodes static per a la manipulació d'objectes tipus Path.

### FileSystem

Funció com a factoria de ***Path***:

```java
FileSystem fs = FileSystems.getDefault();
```

### Path

Un cop tenim el File System, el podem fer servir com a factoria de path:

```java
Path examplePath fs.getPath("/test/file.txt");
```

### Files

A través de l'objecte Path podem operar amb els mètodes *static* de la classe *Files* per convertir el path en un fitxer o directori, llegir o escriure en ell i consultar i modificar les seves propietats.

Alguns dels seus mètodes son: ***createFile(), createDirectory(), copy(), delete(), exists(), isDirectory(), isExecutable(), isHidden(), isReadable(), isRegularFile(), isWriteable()...***

Veure la documentació de Java per a obtenir la llista completa.

El fitxer [nioFileManagement.zip](/damm06/assets/1.1/1.1.nio/niofilemanagement.zip)
 conté els següents exemples:

* CreateDirectoryAndFile.java --> programa que mostra la creació d'una estructura de directoris i d'un fitxer usant la classe Files.
* FileDelete.java --> programa que esborra un fitxer usant la classe Files.

## B. Buffers i canals

El sistema *NIO* està basat en dos elements: **buffers** i **canals**. Els buffers contenen dades, mentre que els canals representen una connexió a un dispositiu d'E/S, com ara un fitxer o un *socket*.

Hi ha diversos tipus de canals i buffers.

De la classe **Buffer** deriven classes orientades a cada tipus bàsic de Java:

* ByteBuffer
* CharBuffer
* DoubleBuffer
* FloatBuffer
* IntBuffer
* LongBuffer
* ShortBuffer

Els principals canals de Java NIO són:

* FileChannel
* DatagramChannel
* SocketChannel
* ServerSocketChannel

### Channels

El funcionament dels ***channels*** és similar al dels *streams*, però amb petites diferències:

* Es pot llegir i escriure a un **Channel**, mentre que un **Stream** és típicament d'un sentit (read o write).
* Es pot llegir i escriure asíncronament a un **Channel**.
* Els **Channels** sempre llegeixen d'un **Buffer** i escriuen a un **Buffer**.

![](/damm06/assets/1.1/1.1.nio/javaniobufferchannel.png)

**FileChannel** és el canal que llegeix dades i escriu dades a fitxers.

Es poden transferir bytes d'un *FileChannel* a un altre de manera que s'optimitza el temps de processament.

Exemple de transferència de dades entre canals: [FileTransfer.zip](/damm06/assets/1.1/1.1.nio/filetransfer.zip)

### Buffers

Un ***buffer*** és essencialment un bloc de memòria en el qual es poden escriure i més tard llegir dades. El bloc de memòria s'encapsula usant la classe *Buffer*, que proveeix d'un conjunt de mètodes adients per treballar-hi.

El procediment per llegir i escriure dades en un **Buffer** segueix el següent patró de quatre passos:

- Escriure dades al Buffer
- Invocar **buffer.flip()**
- Llegir dades del Buffer
- Invocar **buffer.clear()** o bé **buffer.compact()**

Quan s'escriuen dades en un *buffer*, aquest manté un registre de la quantitat de dades que s'hi han escrit. Quan cal llegir les dades, s'ha de canviar el mode d'operació del buffer d'escriptura a lectura usant el mètode **flip()**.

En el mode lectura es poden llegir totes les dades que s'hi han escrit. Una vegada llegides, cal netejar el buffer per poder-hi escriure de nou. Hi ha dos mètodes: **clear()**, que esborra tot el buffer, o bé **compact()**, que allibera només les dades que s'han llegit (suposant que no se n'hagin llegit totes). Les dades no llegides es mouen a l'inici del buffer.

#### Buffer Capacity, Position i Limit

Un buffer té tres propietats fonamentals: capacitat, posició i límit.

* **Capacity.** És la mida del *Buffer* en tant que bloc de memòria. Quan el buffer és ple, cal buidar-lo (llegint-lo o invocant el mètode *clear()*) abans d'escriure-hi de nou.
* **Position.** Quan s'escriu en un *Buffer*, es fa en una posició determinada. Inicialment aquesta posició és 0 i arriba com a màxim a *capacity*–1. Quan es llegeix d'un buffer, es fa en una certa posició. La posició es fixa a 0 quan es fa un *flip* al buffer i s'incrementa en 1 cada vegada que es llegeix.
* **Limit.** En mode escriptura el límit d'un buffer és igual a la seva capacitat, mentre que en mode lectura és l'última posició escrita.

![](/damm06/assets/1.1/1.1.nio/buffermodewr.png)


#### Creació i ús de Buffers

S'obté un *Buffer* fent reserva de memòria, invocant el mètode **allocate()** que implementen totes les seves subclases. Un exemple seria:

```java
ByteBuffer buf = ByteBuffer.allocate(48);
```

Per **escriure** dades a un Buffer, hi ha dos mètodes:

1. Escriure dades al Buffer des d'un Channel.

```java
int bytesRead = inChannel.read(buf);
```

2. Escriure directament dades al Buffer, usant el mètode put().

```java
buf.put(127);
```

El mètode **flip()** canvia un *Buffer* del mode d'escriptura al de lectura. Quan s'invoca aquest mètode el paràmetre *limit* pren el valor de *position* i *position* passa a valer 0.

Anàlogament a l'escriptura, hi ha dues maneres de **llegir** dades d'un Buffer:

1. Llegir del Buffer i escriure a un Channel.

```java
int bytesWritten = inChannel.write(buf);
```

2. Llegir directament del Buffer, usant el mètode get().

```java
byte aByte = buf.get();
```

El mètode **Buffer.rewind()** posa la posició novament a 0, fent possible tornar a llegir novament les dades del Buffer, mantenint el valor limit.

### Java NIO FileChannel

Usant la classe **FileChannel** es pot escriure i llegir a fitxer, i per això cal primer obrir-lo. Es pot obtenir un **FileChannel** via **InputStream, OutputStream** o **RandomAccessFile**.

```java
RandomAccessFile aFile = new RandomAccessFile("nio-data.txt", "rw");
FileChannel inChannel = aFile.getChannel();
```

**Lectura.**
A través d'un dels mètodes **read()**. Prèviament cal reservar memòria per a un Buffer.

```java
ByteBuffer buf = ByteBuffer.allocate(48);
int bytesRead = inChannel.read(buf);
```

**Escriptura.**
Mitjançant el mètode **write()**, que té com a paràmetre un Buffer.

```java
String newData = "New String to write to file..."
ByteBuffer buf = ByteBuffer.allocate(48);
buf.clear();
buf.put(newData.getBytes());
buf.flip();
while(buf.hasRemaining()) {
    channel.write(buf);
}
```

**Tancar FileChannel**.
En acabar totes les operacions amb el Channel, cal tancar-lo:

```java
channel.close();
```

**Exemples**

* Exemples de lectura i escriptura amb un FileChannel [FileChannelExamples.zip](/damm06/assets/1.1/1.1.nio/filechannelexamples.zip)
* Exemple d'ús de SocketChannel [ObjectTransferSocketNIO.zip](/damm06/assets/1.1/1.1.nio/objecttransfersocketnio.zip)

