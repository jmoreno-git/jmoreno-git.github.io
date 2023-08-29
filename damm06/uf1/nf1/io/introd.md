# Fluxos d'informació: bytes i chars

Els programes poden enviar fluxos de dades (**streams**) o rebre’ls cap a o des de diferents orígens i destinacions. Ens centrarem bàsicament en *streams* per llegir i escriure fitxers.

Els *streams* es categoritzen en quatre grans blocs segons dues propietats:

* Sentit: entrada o sortida
* Codificació: orientats a byte o orientats a caràcter

**Streams d'entrada orientats a bytes**

![](/damm06/assets/1.1/1.1.io/inputstream.png)

Mètodes útils proporcionats per la classe ***InputStream***:

| mètode | descripció |
| ------ | ------ |
| public abstract int read() throws IOException     | llegeix i retorna el següent byte o -1 si ha arribat al final del fitxer    |
| public int available() throws IOException     |  retorna el nombre de bytes que queden per llegir    | 
| public void close() throws IOException     |  tanca el recurs    |
| public long skip(long n) throws IOException     | salta i descarta un nombre de bytes sense llegir i retorna el nombre realment saltat    |
| public int read(byte[] b) throws IOException     | llegeix array de bytes, retorna el nombre de bytes llegits o -1 si s'arriba al final del fitxer   |
| public byte[] readAllBytes() throws IOException     | llegeix tots els bytes restants    |
| public byte[] readANBytes(int len) throws IOException     | llegeix el nombre de bytes especificat    |
| public long transferTo(OutputStream out) throws IOException     | llegeix tots els bytes del stream i els escriu a l'especificat; retorna el nombre de bytes transferits  |



```java
/**
 * reads a list of byte from file
 *
 * @param filename the name of the file to read from
 * @return list of bytes read
 * @throws FileNotFoundException if file not found
 * @throws IOException in case an input/output error occurs
 */
private static List<Byte> readBytesToFile(String filename) throws FileNotFoundException, IOException {
	File f = new File(filename);
	List<Byte> data = new ArrayList<>();
	try (FileInputStream fs = new FileInputStream(f)) {
		int x;  //variable to store each byte read
		while ((x = fs.read()) != -1) { //while not end of file,  keep reading
			data.add((byte)x);
		}
	}
	return data;
}
```

**Streams de sortida orientats a bytes**

![](/damm06/assets/1.1/1.1.io/outputstream.png)

Mètodes útils proporcionats per la classe ***OutputStream***:

| mètode | descripció |
| ------ | ------ |
| public void write(int b) throws IOException     | escriu un byte   |
| public void write(byte[] b) throws IOException     | escriu un array de bytes   | 
| public void write(byte[] b, int off, int len) throws IOException     | escriu un array de bytes des de la posició 'off' amb longitud 'len'  | 
| public void flush() throws IOException     | descarrega el buffer del stream   |
| public void close()throws IOException     |  tanca el recurs    |



```java
/**
 * writes a list of byte to file
 * @param filename the name of the file to write to
 * @param data the list of byte to write
 * @throws FileNotFoundException if file not found
 * @throws IOException in case an input/output error occurs
 */
public void writeBytesToFile(String filename, List<Byte> data) throws FileNotFoundException, IOException {
	File f = new File(filename);
	try (FileOutputStream fs = new FileOutputStream(f)) {
		for (Byte b : data) {
			fs.write(b);
		}
	}
}
```

Fragment de codi principal per provar els mètodes anteriors:

```java
//define filename
String filename = "bytes.txt";
//define data to be written
List<Byte> data = List.of((byte) 10, (byte) 15, (byte) 25, (byte) 30, (byte) 45);
try {
	System.out.println("Writing data");
	writeBytesToFile(filename, data);
	System.out.println("Reading data");
	List<Byte> data2 = readBytesToFile(filename);
	System.out.println("Data read");
	System.out.println(data2);
} catch (FileNotFoundException e) {
	System.out.println("File not found");
} catch (IOException e) {
	System.out.println("Input or output problem related to this operation");
}
```

**Streams d'entrada orientats a caràcters**

![](/damm06/assets/1.1/1.1.io/reader.png)

Mètodes útils proporcionats per la classe ***Reader***:

| mètode | descripció |
| ------ | ------ |
| public abstract int read() throws IOException     | llegeix i retorna el següent caràcter o -1 si ha arribat al final del fitxer    |
| public void close() throws IOException     |  tanca el recurs    |
| public long skip(long n) throws IOException     | salta i descasrta un nombre de bytes sense llegir i retorna el nombre realment saltat    |
| public int read(char[] b) throws IOException     | llegeix array de caràcters, retorna el nombre de caràcters llegits o -1 si s'arriba al final del fitxer   |
| public long transferTo(Writer out) throws IOException     | llegeix tots els caràcters del stream i els escriu a l'especificat; retorna el nombre de caràcters transferits  |



```java
/**
 * writes a list of characters to file
 * @param filename the name of the file to write to
 * @param data the list of characters to write
 * @throws FileNotFoundException if file not found
 * @throws IOException in case an input/output error occurs
 */
public void writeCharsToFile(String filename, List<Character> data) throws FileNotFoundException, IOException {
	File f = new File(filename);
	try (FileWriter fs = new FileWriter(f)) {
		for (Character b : data) {
			fs.write(b);
		}
	}
}
```


**Streams de sortida orientats a caràcters**

![](/damm06/assets/1.1/1.1.io/writer.png)

Mètodes útils proporcionats per la classe ***Writer***:

| mètode | descripció |
| ------ | ------ |
| public void write(int b) throws IOException     | escriu un byte   |
| public void write(byte[] b) throws IOException     | escriu un array de bytes   | 
| public void write(byte[] b, int off, int len) throws IOException     | escriu un array de bytes des de la posició 'off' amb longitud 'len'  | 
| public void flush() throws IOException     | descarrega el buffer del stream   |
| public void close()throws IOException     |  tanca el recurs    |



```java
/**
 * reads a list of characters from file
 * @param filename the name of the file to read from
 * @return list of characters read
 * @throws FileNotFoundException if file not found
 * @throws IOException in case an input/output error occurs
 */
public List<Character> readCharsToFile(String filename) throws FileNotFoundException, IOException {
	File f = new File(filename);
	List<Character> data = new ArrayList<>();
	try (FileReader fs = new FileReader(f)) {
		int x;  //variable to store each char read
		while ((x = fs.read()) != -1) { //while not end of file,  keep reading
			data.add((char)x);
		}
	}
	return data;
}
```

Fragment de codi principal per provar els mètodes anteriors:

```java
String filename = "chars.txt";
List<Character> data = List.of('a', 'e', 'i', 'o', 'u');
try {
	System.out.println("Writing data");
	writeCharsToFile(filename, data);
	System.out.println("Reading data");
	List<Character> data2 = readCharsToFile(filename);
	System.out.println("Data read");
	System.out.println(data2);
} catch (FileNotFoundException e) {
	System.out.println("File not found");
} catch (IOException e) {
	System.out.println("Input or output problem related to this operation");
}
```
