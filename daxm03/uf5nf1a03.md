# Fluxos d'informació i XML

## Continguts

* Gestió del sistema de fitxers.
* Fluxos d'informació. Jerarquia de classes.
* Biblioteques de classes per a fitxers XML.

![](/images/disk_padre_usb.jpg)

[Apunts de fluxos d'informació](assets/5.1/5.1.3/dax2_m03-a513-Fluxos_informacio.pdf)

## Gestió del sistema de fitxers.

[Exemple de llistat del contingut d'un directori: DirList.java](assets/5.1/5.1.3/DirList.java)

[Exemple de llistat dels atributs d'un fitxer: FileAttributes.java](assets/5.1/5.1.3/FileAttributes.java)

Exercici proposat:
Programa que llisti els fitxers d'un directori amb els seus atributs, segons els paràmetres entrat des del terminal.
Format d'execució:  dla -[params] [path]
on

* params pot ser '1' per llistar només noms en columna o 'a' per llistar tots els atributs possibles en una línia per a cada fitxer.
* path és la ruta al directoria a mostrar (pot ser relativa o absoluta)

### Exemple de creació i esborrat de directoris i fitxers

[Exemple de creació i esborrat de directoris i fitxers: FileExample.java](assets/5.1/5.1.3/FileExample.java)

**Mètode per llistar en forma d'array els noms dels fitxers d'un directory**.

```java
/**
 * example of how to display the names of all files in a directory
 * @param dirPath path to directory
 */
public void listDirFilenames(String dirPath) {
	File dir = new File(dirPath);
	String[] filenames = dir.list();
	System.out.println(Arrays.toString(filenames));
}
```
**Mètode per crear un directory (o diversos) i omplir el més interior amb fitxers que tinguin el mateix nom i extensió però una numeració seqüencial.**

```java
/**
 * example of creating a directory (or some directories tree) 
 * and creating some files in it with same name and sequential number
 * @param dirPath path of directory
 * @param fileBaseName base name for files
 * @param fileExtension extension for files
 * @param numberOfFiles number of files to create
 * @param multiDir true if multiple directories are to be created
 * @throws IOException if some IO error occurs
 */
public void createDirAndFiles(String dirPath, 
		String fileBaseName, String fileExtension, 
		int numberOfFiles, boolean multiDir) 
		throws IOException {
	boolean success = false;
	//create directory
	File dir = new File(dirPath);
	success = (multiDir) ? dir.mkdirs() : dir.mkdir();
	if (success) {  //directory created by mkdir operation
		System.out.format("Directory %s successfully created\n", dir.getAbsolutePath());
	} else {
		System.out.format("File %s already exists\n", dir.getAbsolutePath());
	}
	if (dir.exists() && dir.isDirectory()) { //check directory existence
		//create some files with basename, extension and a sequential number
		for (int i = 0; i < numberOfFiles; i++) {
			//define f name using a sequential number
			String filename = String.format(
					"%s%02d.%s", 
					fileBaseName, 
					i, 
					fileExtension);
			File file = new File(dir, filename);
			//create f
			success = file.createNewFile();
			if (success) {
				System.out.format("File %s successfully created\n", file.getAbsolutePath());
			} else {
				System.out.format("File %s already exists\n", file.getAbsolutePath());
			}
		}
	}
}
```

**Mètode per esborrar un directory i tots els seus fitxers. Cal recordar que només es poden esborrar directoris buits.**

```java
/**
 * example of how to delete a directory and all of its files
 * (remember that only an empty directory can be deleted)
 * @param dirPath path to directory
 */
public void deleteDirectoryAndFiles(String dirPath) {
	boolean success = false;
	File dir = new File(dirPath);
	//get files in directory
	File[] files = dir.listFiles();
	//delete files
	for (File f : files) {
		success = f.delete();
		System.out.format("File %s successfully deleted\n", f.getAbsolutePath());
	}
	//delete directory
	success = dir.delete();
	System.out.format("Directory %s successfully deleted\n", dir.getAbsolutePath());
}
```

## Fluxos d'informació. Jerarquia de classes.

Els programes poden enviar fluxos de dades (*streams*) o rebre'ls cap a o des de diferents orígens i destinacions. Ens centrarem bàsicament en *streams* per llegir i escriure fitxers.

Els *streams* es categoritzen en quatre grans blocs segons dues propietats:

* Sentit: entrada o sortida
* Codificació: orientats a byte o orientats a caràcter

Byte input streams ![Byte input streams](assets/5.1/5.1.3/inputstream.png)

Byte output streams ![Byte output streams](assets/5.1/5.1.3/outputstream.png)

Char input streams ![Char input streams](assets/5.1/5.1.3/reader.png)

Char output streams ![Char output streams](assets/5.1/5.1.3/writer.png)

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

Mètodes útils proporcionats per la classe ***OutputStream***:

| mètode | descripció |
| ------ | ------ |
| public void write(int b) throws IOException     | escriu un byte   |
| public void write(byte[] b) throws IOException     | escriu un array de bytes   | 
| public void write(byte[] b, int off, int len) throws IOException     | escriu un array de bytes des de la posició 'off' amb longitud 'len'  | 
| public void flush() throws IOException     | descarrega el buffer del stream   |
| public void close()throws IOException     |  tanca el recurs    |

Mètodes útils proporcionats per la classe ***Reader***:

| mètode | descripció |
| ------ | ------ |
| public abstract int read() throws IOException     | llegeix i retorna el següent caràcter o -1 si ha arribat al final del fitxer    |
| public void close() throws IOException     |  tanca el recurs    |
| public long skip(long n) throws IOException     | salta i descasrta un nombre de bytes sense llegir i retorna el nombre realment saltat    |
| public int read(char[] b) throws IOException     | llegeix array de caràcters, retorna el nombre de caràcters llegits o -1 si s'arriba al final del fitxer   |
| public long transferTo(Writer out) throws IOException     | llegeix tots els caràcters del stream i els escriu a l'especificat; retorna el nombre de caràcters transferits  |

Mètodes útils proporcionats per la classe ***Writer***:

| mètode | descripció |
| ------ | ------ |
| public void write(int b) throws IOException     | escriu un byte   |
| public void write(byte[] b) throws IOException     | escriu un array de bytes   | 
| public void write(byte[] b, int off, int len) throws IOException     | escriu un array de bytes des de la posició 'off' amb longitud 'len'  | 
| public void flush() throws IOException     | descarrega el buffer del stream   |
| public void close()throws IOException     |  tanca el recurs    |


En primer lloc provem exemples d'escriptura i lectura senzilla de bytes i de caràcters. Més endavant provarem exemples per fer persistents dades primitives i objectes.

### Videos d'introducció a fitxers

(de Píldoras informáticas)

* [Lectura de text](https://youtu.be/etQN4EfYN7k)
* [Escriptura de text](https://youtu.be/E0H4OzW2_1Y)
* [Lectura i escriptura de text amb buffer](https://youtu.be/YCCE4sbmWrw)
* [Lectura binària](https://youtu.be/38YBRnJtQEw)
* [Escriptura binària](https://youtu.be/v6ctWhhTFrk)

### Byte output stream

```
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * WriteBytes.java
 * Example writing bytes to a file
 * @author Jose
 */
public class WriteBytes {
	public static void main(String[] args) {
		byte [] list ={10, 15, 25, 30, 45};
		if(args.length == 1) {	 //check parameter length 
			File f = new File(args[0]);
			try{
				FileOutputStream fs = new FileOutputStream(f);
				for(int i = 0; i < list.length; i++){
					fs.write(list[i]);
				}
				fs.flush();
				fs.close();
			} catch(IOException e) {
				System.out.println("Input or output problem related to this exception:");
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Usage: WriteBytes filename");
		}
	}
}
```

### Byte input stream

```
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * ReadBytes.java
 * Example reading bytes from file
 * @author Jose
 */
public class ReadBytes {
	public static void main(String[] args) {
		if(args.length == 1) {	 //check parameter length 
			File f = new File(args[0]);
			int x=0; //byte llegit 
			try{
				FileInputStream fs = new FileInputStream(f);
				while ((x = fs.read()) != -1) { //while not end of file,  keep reading
					System.out.print(" "+(byte)x);
				}
				System.out.print("\n");
				fs.close();
			} catch(FileNotFoundException e){
				System.out.println("File not found. Exception info:");
				e.printStackTrace();
			} catch(IOException e) {
				System.out.println("Input or output problem related to this exception:");
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Usage: ReadBytes filename");
		}
	}
}
```

### Char output stream

```
import java.io.*;
/**
 * WriteChars.java
 * Example writing characters to a file
 * @author Jose
 */
public class WriteChars {
	public static void main(String[] args) {
		char [] list = {'a', 'e', 'i', 'o', 'u'};
		if(args.length == 1) {	//check parameter length 
			try{
				FileWriter fs = new FileWriter(args[0]);
				//BufferedWriter fs = new BufferedWriter(new FileWriter(args[0]));
				for(int i = 0; i < list.length; i++){
					fs.write(list[i]);
				}
				fs.close();
			} catch(IOException e) {
				System.out.println("Input or output problem related to this exception:");
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Usage: WriteChars filename");
		}
	}
}
```

### Char input stream

```
/**
 * ReadChars.java
 * Example reading characters from file
 * @author Jose
 */
public class ReadChars {
	public static void main(String[] args) {
		int c; //character read
		if(args.length == 1) {	//check parameter length
			try{
				FileReader fs = new FileReader(arg[0]);
				//BufferedReader fs = new BufferedReader(new FileReader(args[0]));
				while ((c=fs.read()) != -1) {
					System.out.print((char)c);
				}
				System.out.print("\n");
				fs.close();
			} catch (FileNotFoundException e){
				System.out.println ("File not found. Exception info:");
				e.printStackTrace();
			} catch(IOException e) {
				System.out.println("Input or output problem related to this exception:");
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Usage: ReadChars filename");
		}
	}
}
```

### Fitxers amb format CSV

Aquest exempñe il·lustra com convertir objectes a format text CSV i convertir en objectes des de format text CSV.

**Person.java**
```java
/**
 * Person.java
 * TAD Person.
 *
 * @author Jose Moreno.
 */

public class Person {

    /*======  attributes =======*/
    private String nif;
    private String name;
    private String phone;

    /*======  constructors =======*/
    /**
     * Person() Default constructor.
     */
    public Person() {

    }

    /**
     * Person() Full initialitzer constructor.
     * @param nif
     * @param name
     * @param phone
     */
    public Person(String nif, String name, String phone) {
        this.nif = nif;
        this.name = name;
        this.phone = phone;
    }

    /**
     * Person() Copy constructor.
     * @param other
     */
    public Person(Person other) {
        this.nif = other.nif;
        this.name = other.name;
        this.phone = other.phone;
    }

    /*======  accessors =======*/
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /*======  methods =======*/
    /**
     * toString() Builds a String representation of the object.
     *
     * @return String representation.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person{");
        sb.append("nif=").append(nif);
        sb.append(", name=").append(name);
        sb.append(", phone=").append(phone);
        sb.append('}');
        return sb.toString();
    }

    /**
     * equals() Compares two objects. Two Person objects are equal if their
     * nif's are equal.
     *
     * @return true if the two objects are equal to each other, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        boolean b;
        if (obj == null) {
            b = false;
        } else {
            if (this == obj) {
                b = true; //the same object.
            } else {
                if (obj instanceof Person other) { //the same class.
                    //Person other = (Person) obj; //convert to Person.
                    b = this.nif.equals(other.nif);
                } else {
                    b = false;
                }
            }
        }
        return b;
    }

}
```

**CsvHelper.java**
```java
/**
 * CSVHelper.java
 * Utility class to perform convertions between csv and objects.
 *
 * @author Jose Moreno.
 */
public class CsvHelper {

    /**
     * toCsv() Converts object to CSV.
     *
     * @param obj: object to be converted.
     * @param delimiter: delimiter to be used between fields.
     * @return String with object data in CSV format.
     */
    public static String toCsv(Person obj, String delimiter) {
        return String.format("%s%s%s%s%s",
                obj.getNif(), delimiter, obj.getName(), delimiter, obj.getPhone());
    }

    /**
     * fromCsv() Converts CSV String to object.
     *
     * @param s: string to be converted.
     * @param delimiter: delimiter to be used between fields.
     * @return Person object with data contained in String s or null in case of error.
     */
    public static Person fromCsvPerson(String s, String delimiter) {
        final int objNumFields = 3;  //number of attributes of Person.
        Person p;
        String[] tokens = s.split(delimiter);
        if (tokens.length == objNumFields) {
            String nif = tokens[0];
            String name = tokens[1];
            String phone = tokens[2];
            p = new Person(nif, name, phone);
        } else {
            p = null;
        }
        return p;
    }
}
```

**PersonCsvMain.java**

```java
/**
 * PersonTextFilePersistMain.java
 * Exemple of conversion between objects and CSV string.
 *
 * @author Jose Moreno.
 */

public class PersonCsvMain {

    public static void main(String args[]) {
        final String myDelimiter = ";";  //delimiter used to separate fields.
        //instantiate a new Person
        Person p1 = new Person("000A", "Peter", "1111");
        //convert to csv.
        String csvFormat = CsvHelper.toCsv(p1, myDelimiter);
        System.out.println(csvFormat);
        //from a CSV string.
        String csv = "001B;John;2222";
        //convert to Person.
        Person p2 = CsvHelper.fromCsvPerson(csv, myDelimiter);
        System.out.println(p2.toString());
    }

}
```

### Apertura de fitxers per afegir informació

Totes les subclasses de *FileOutputStream* i *FileWriter* tenen constructors que permeten escollir si es vol obrir el fitxer en mode *append* per a afegir informació en comptes de sobreescriure-la.

```java
FileOutputStream(File file, boolean append)
FileOutputStream(String name, boolean append)
FileWriter(File file, boolean append)
FileWriter(String fileName, boolean append)
```

Si es defineix append=true, el fitxer es crea si no existeix prèviament, i si ja existeix, la informació que s'hi escriu s'afegeix al final del mateix sense sobreescriure l'existent.

```java
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 * Open a file in append mode
 * @author Jose
 */
public class CharAppend {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Input file name: ");
        String filename = scan.next();
        System.out.print("Input character: ");
        String sCharacter = scan.next();
        char character = sCharacter.charAt(0);
        
        //write to file
        try (FileWriter f = new FileWriter(filename, true)) {
            f.write(character);
        } catch (IOException ex) {
            System.out.println("Error writing");
        } 
        
        //read from file and display content
        try (FileReader f  = new FileReader(filename)) {
            int c;
            while ( (c = f.read())!=-1 )  {
                System.out.print((char)c);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (IOException ex) {
            System.out.println("Error reading");
        }
        
    }
    
}

```

### Seriació de tipus primitius

Utilitzem les classes [***DataInputStream***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/DataInputStream.html) i [***DataOutStream***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/DataOutputStream.html), les quals proveeixen mètodes per a la persistència de cada tipus primitiu.

La classe *DataInputStream* implementa l'interface [***DataInput***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/DataInput.html), la qual defineix els següents mètodes:

```java
boolean readBoolean()
byte readByte()
char readChar()
double readDouble()
float readFloat()
void readFully(byte[] b)
void readFully(byte[] b, int off, int len)
int readInt()
String readLine()
long readLong()
short readShort()
int readUnsignedByte()
int readUnsignedShort()
String readUTF()
int skipBytes(int n)
```

La classe *DataOutputStream* implementa l'interface [***DataOutput***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/DataOutput.html), la qual defineix els següents mètodes:

```java
void write(byte[] b)
void write(byte[] b, int off, int len)
void write(int b)
void writeBoolean(boolean v)
void writeByte(int v)
void writeBytes(String s)
void writeChar(int v)
void writeChars(String s)
void writeDouble(double v)
void writeFloat(float v)
void writeInt(int v)
void writeLong(long v)
void writeShort(int v)
void writeUTF(String s)
```

El següent exemple il·lustra com escriure i llegir dades primitives en fitxer. Per llegir, es pot usar el mètode ***available()*** per estimar el nombre de bytes restants per llegir al fitxer i saber si s'ha de continuar llegint dades. Tot i això, cal capturar l'exepció ***EOFException***, la qual es llança en arribar al final del fitxer per si malgrat quedar bytes per llegir, no siguin suficients per a la lectura del tipus de dada que hem demanat.

```
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Example of using DataInputStream and DataOutputStream.
 *
 * @author Jose
 */
public class DataStreamExample {

    public static void main(String[] args) {
        // write information to file.
        writeInfo();
        // read information from file.
        readAndShowInfo();
        // write array to file
        writeList();
        // read array from file
        readAndShowList();
    }

    private static void writeInfo() {
        int age = 30;
        double salary = 1000.0;
        String name = "Peter";
        System.out.println("Writing to file ...");
        try {
            DataOutputStream dos = new DataOutputStream(
                    new FileOutputStream("myData.txt")
            );
            // do some stuff with the file.
            dos.writeInt(age);
            dos.writeDouble(salary);
            dos.writeUTF(name);
            // close the file.
            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readAndShowInfo() {
        System.out.println("Reading from file ...");
        try {
            DataInputStream dis = new DataInputStream(
                    new FileInputStream("myData.txt")
            );
            // read data from file.
            int age = dis.readInt();
            double salary = dis.readDouble();
            String name = dis.readUTF();
            // show data to console.
            System.out.format("age=%d\n", age);
            System.out.format("salary=%f\n", salary);
            System.out.format("name=%s\n", name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeList() {
        //generate test data
        List<Double> data = Stream
                .of(1.0, 2.0, 3.0, 4.0, 5.0)
                .toList();
        //write data
        try (DataOutputStream dos = new DataOutputStream(
                    new FileOutputStream("myData2.txt"))) {
            for (int i = 0; i < data.size(); i++) {
                dos.writeDouble(data.get(i));
            }            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void readAndShowList() {
        List<Double> data = new ArrayList<>();
        //read data
        try (DataInputStream dis = new DataInputStream(
                new FileInputStream("myData2.txt"))) {
            while (dis.available() > 0) {                
                double d = dis.readDouble();
                data.add(d);
            }
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //display read data
        for (Double elem : data) {
            System.out.println(elem);
        }
    }

}
```

A continuació es mostra un exemple de com desar en fitxer part de la informació d'objectes i com recuperar-la després. En aquest cas, desem en fitxer la informació d'una llista d'usuaris exceptuant els passwords, els quals no volem emmagatzemar-los al fitxer.

[Descàrrega de l'exemple](assets/3.1/primitivefileuser.zip)

```java
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to persist part of an object (User) in a file
 * @author Jose
 */
public class PrimitiveFileUser {

    /**
     * saves to file list of users (password is not stored)
     *
     * @param users the list of users
     * @param filename the file name
     * @return the number of elements actually saved
     */
    public int saveUsersDataToFile(List<User> data, String filename) {
        int counter = 0;
        try (
                 DataOutputStream dos = new DataOutputStream(
                        new FileOutputStream(filename, false))) {
            for (User user : data) {
                //write name
                dos.writeUTF(user.getName());
                //write age
                dos.writeInt(user.getAge());
                counter++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return counter;
    }

    /**
     * reads data stored in file
     *
     * @param filename the name of the file to read
     * @return list of users with data read from file
     */
    public List<User> readUsersDataFromFile(String filename) {
        List<User> data = new ArrayList<>();
        try (
                 DataInputStream dis = new DataInputStream(
                        new FileInputStream(filename)
                )) {
            while (dis.available() > 0) {
                //read name
                String name = dis.readUTF();
                //read age
                int age = dis.readInt();
                //instantiate object (password is not read from file)
                User u = new User(name, "", age);
                data.add(u);
            }
        } catch (EOFException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
}
```

### Lectura i escriptura de línies de text

Per a la lectura utilitzarem un ***BufferedReader***, el qual proporciona un buffer de lectura per millorar l'eficiència dels accessos a disc. També es pot usar per a l'escriptura un ***BufferedWriter***, per les mateixes raons. En el nostre cas, però, usarem un ***PrintStream*** o un ***PrintWriter***, els quals proporciona mètodes per escriure línies.

De fet, l'objecte *System.out* que utilitzem per escriure a la sortida estàndard dels programes és un *PrintStream*.

Exemple d'escriptura i lectura de fitxers de línies de text. [Descàrrega de l'exemple](assets/3.1/LinesFile.zip)

```java
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to provide persistence of string texts in file line by line
 * @author Jose
 */
public class LinesFile {
    /**
     * saves array of String into a file, each element in a line
     * @param data the array of String to be saved
     * @param filename the name of the file
     * @return the number of lines actually written
     */
    public int saveLinesToFile(List<String> data, String filename) {
        int counter = 0;
        try (PrintStream out = new PrintStream(filename)) {
            for (String elem : data) {
                out.println(elem);
                counter++;
            }
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return counter;
    }
    
    /**
     * reads array of String from a file, each element from a line
     * @param filename the name of the file
     * @return the list of String
     */
    public List<String> readLinesFromFile(String filename) {
        List<String> data = new ArrayList<>();
        try (BufferedReader in = new BufferedReader( new FileReader(filename) )) {
            String elem;
            while ( (elem = in.readLine()) != null ) {
                data.add(elem);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
}
```

El mètode *readLine()* llegeix fins al canvi de línia i si arriba al final del fitxer retorna *null*.

Les classes *PrintStream* i *PrintWriter* també proporcionen sobrecàrregues dels mètodes ***print()*** i ***println()*** per escriure en format text tots els tipus de dades primitives.

```java
void print(boolean b)
void print(char c)
void print(char[] s)
void print(double d)
void print(float f)
void print(int i)
void print(long l)
void print(String s)
```
així com mètodes ***format*** per imprmir dades amb un format especificat:

```java
PrintWriter format(String format, Object... args)
```

### Seriació d'objectes

La **seriació** (*serialization*) és el procés d'escriure un objecte a un stream de bytes. És útil quan es vol fer persistent l'estat d'un programa a un fitxer per exemple. El procés invers s'anomena **deseriació** (*deserialization*).

Només els objectes que implementen l'interfície [***Serializable***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html) poden ser desats i restaurats usant la serialització. Si una classe és serialitzable, totes les seves subclasses també ho són.

Nota: les variables ***transient*** i ***static*** no es guarden durant la serialització.
Usarem les **interfícies** ***ObjectOutput*** i ***ObjectInput***, i les **classes** ***ObjectOutputStream*** i ***ObjectInputStream***, que les implementen.

Nota: les interfícies *ObjectOutput* i *ObjectInput* estenen respectivament *DataOutput* i *DataInput*, per la qual cosa hereten tots els seus mètodes.

![](assets/5.1/5.1.3/object-serialization.png)

La interfície [***ObjectOutput***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/ObjectOutput.html) estén les interfícies ***DataOutput*** i ***AutoCloseable*** i suporta serialització. Defineix els mètodes ***close()***, ***flush()***, ***write()*** i ***writeObject()***. Aquest últim s'invoca per serialitzar un objecte. Tots els mètodes llancen ***IOException*** si es produeix un error.

La interfície [***ObjectInput***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/ObjectInput.html) estén les interfícies ***DataInput*** i ***AutoCloseable*** i suporta serialització. Defineix els mètodes ***close()***, ***available()***, ***read()*** i ***readObject()***. Aquest últim s'invoca per llegir un objecte serialitzat. Tots els mètodes llancen ***IOException*** si es produeix un error. El mètode ***readObject()*** pot llançar també l'excepció ***ClassNotFoundException***.

La classe [***ObjectOutputStream***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/ObjectOutputStream.html) estén ***OutputStream*** i implementa la interfície ***ObjectOutput***. El seu constructor és: ***ObjectOutputStream(OutputStream outStream) throws IOException***
La classe [***ObjectInputStream***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/ObjectInputStream.html) estén ***InputStream*** i implementa la interfície ***ObjectInput***. El seu constructor és: ***ObjectInputStream(InputStream inStream) throws IOException***.

Consulteu la documentació en línia de Java per a la llista de mètodes que implementen les dues classes anteriors.

[Exemple de seriació d'objectes a fitxer en binari](assets/5.1/5.1.3/contactobjectstream.zip)

**Exercici**

Programar una aplicació amb menú que permeti carregar i desar en fitxer una llista de productes. Cal implementar tres formats: CSV (text, línies, delimitat per ';'), dades primitives, objectes. Completeu el codi de la plantilla següent: [StoreFile.zip](assets/3.1/StoreFile.zip)
Nota: s'utilitza interface per abstreure la funcionalitat comuna de les diferents classes que implementen la persistència en els diferents formats.

## Biblioteques de classes per a fitxers XML.

Per treballar amb fitxers en format [XML](https://en.wikipedia.org/wiki/XML), Java proporciona dues biblioteques:

* **SAX (Simple API for XML)**
* **DOM (Document Object Model)**

### SAX

[Manual de SAX](assets/5.1/5.1.3_xml/Simple_API_for_XML.pdf)

[Exemple de SAX parser sense i amb validació](assets/5.1/5.1.3_xml/sax_xml_parser.rar)

[Exemple de lectura XML i conversió a objectes: el banc](assets/5.1/5.1.3_xml/SaxParserBankAccount.zip)

### DOM

No el treballarem en aquest mòdul.
