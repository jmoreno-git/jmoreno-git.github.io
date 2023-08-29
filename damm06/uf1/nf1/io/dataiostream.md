# Seriació de tipus primitius

Utilitzem les classes [***DataInputStream***](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/DataInputStream.html) i [***DataOutStream***](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/DataOutputStream.html), les quals proveeixen mètodes per a la persistència de cada tipus primitiu.

La classe *DataInputStream* implementa la interfície [***DataInput***](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/DataInput.html), la qual defineix els següents mètodes:

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

La classe *DataOutputStream* implementa la interfície [***DataOutput***](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/DataOutput.html), la qual defineix els següents mètodes:

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

El següent exemple il·lustra com escriure i llegir dades primitives en fitxer. Per llegir, es pot usar el mètode ***available()*** per estimar el nombre de bytes restants que queden per llegir al fitxer i determinar si s'ha de continuar llegint dades. Tot i això, cal capturar l'excepció ***EOFException***, la qual es llança en arribar al final del fitxer per si malgrat quedar bytes per llegir, no siguin suficients per a la lectura del tipus de dada que hem demanat.

```
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Example of DataInputStream and DataOutputStream.
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

	/**
	 * write some stuff to file
	*/
    private static void writeInfo() {
		//some data to write
        int age = 30;
        double salary = 1000.0;
        String name = "Peter";
        System.out.println("Writing to file ...");
        try {
            DataOutputStream dos = new DataOutputStream(
                    new FileOutputStream("myData.txt")
            );
            // write data to the file.
            dos.writeInt(age);
            dos.writeDouble(salary);
            dos.writeUTF(name);
            // close the file.
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();   //TODO treat the exception
        }
    }

	/**
	 * read data from file and display it
	*/
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
            e.printStackTrace();   //TODO treat the exception
        }
    }

	/**
	* generate list of data and write it to file
	*/
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

	/**
	* read a list of data from file and display it
	*/
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

[Descàrrega de l'exemple](/damm06/assets/1.1/1.1.io/primitivefileuser.zip)

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
