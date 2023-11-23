# Gestió del sistema de fitxers

En aquest apartat anem a treballar el sistema de fitxers, és a dir, la gestió de directoris, creació, esborrar i llistat del seu contingut.

La classe ***File*** representa objectes de tipus fitxer, que encapsulen el nom del fitxer i les seves propietats: dimensió, data de la darrera actualització, permisos de lectura i/o escriptura, etc.

Permet fer la representació abstracta de fitxers, crear-los, esborrar-los, canviar les seves propietats, però no llegir ni escriure en ells.

Podem invocar de diverses maneres al constructor de la classe amb un arxiu determinat, donada la seva ruta completa. Per exemple, si es tracta del fitxer "\docs\carta.doc":

```java
File unArxiu = new File("\docs\carta.doc");
File unArxiu = new File("\docs", "carta.doc");
File unDir = new File("\docs");
File unArxiu = new File(unDir, "carta.doc");
```

Les taules següents reuneixen els principals mètodes de la classe *File*:

**Mètodes relatius als noms dels fitxers**

| Mètode                    | Descripció                    |
| :--------------------------- | -------------------------------- |
| String toString()          | Retorna la conversió a String |
| String getName()           | Retorna el nom                 |
| String getPath()           | Retorna la ruta                |
| String getAbsolutePath()   | Retorna la ruta absoluta       |
| String getParent()         | Retorna la ruta pare           |
| boolean renameTo(File nom) | Canvia el nom                  |

**Mètodes relatius a verificacions**

| Mètode                    | Descripció                    |
| :--------------------------- | -------------------------------- |
| boolean exists()          | Existència |
| boolean canWrite()          | Permís d’escriptura                 |
| boolean canRead()           | Permís de lectura                |
| boolean isFile()   | Correspon a un directori       |
| boolean isDirectory()         | Retorna la ruta pare           |
| boolean isAbsolute() | És una ruta absoluta                  |
| boolean isHidden() | És ocult                 |

**Mètodes relatius a propietats**

| Mètode                    | Descripció                    |
| :--------------------------- | -------------------------------- |
| long lastModified()          | Data i hora de la darrera modificació |
| boolean setLastModified(long time)          | Canvia la data i hora de la darrera modificació                 |
| long length()           | Tamany del fitxer                |
| boolean setReadOnly()   | Activa atribut de només lectura       |

**Mètodes relatius a directoris i altres**

| Mètode                    | Descripció                    |
| :--------------------------- | -------------------------------- |
| boolean mkdir()          | Crea el directori |
| boolean mkdirs()          | Crea el directori i els directoris pare necessaris                 |
| String[] list()          | Array de noms dels fitxers que conté el directori              |
| File[] listFiles()   | Array de File dels fitxers que conté el directori       |
| boolean delete()   | Esborra el fitxer o directori       |

### Exemples


**Mostrar informació d'un fitxer**

```java
import java.io.*;
	
public class FileAttributes {
	
	public static void main(String [] args) throws IOException
	{
	
		StringBuilder sb = new StringBuilder(); //output string
	
		if (args.length==1){
			File file = new File(args[0]); //new file
			sb.append("\nPath: ");
			sb.append(file.getPath());
			sb.append("\nName: ");
			sb.append(file.getName());
			sb.append("\nAbsolute path: ");
			sb.append(file.getAbsolutePath());
	
	
			if (file.exists()) {
				sb.append("\nAttributes: ");
				sb.append(file.isDirectory()?"+d":"-d");
				sb.append(file.isFile()?"+f":"-f");
				sb.append(file.isHidden()?"+h":"-h");
				sb.append(file.canRead()?"+r":"-r");
				sb.append(file.canWrite()?"+w":"-w");
				sb.append("\nSize: ");
				sb.append(file.length());
	
				java.util.Date data = new java.util.Date(file.lastModified());
				sb.append("\nLast modification: ");
				sb.append(data.toString());
			}
			else {
				sb.append("This file or directory does not exist.");
			}
		}
		else {
			sb.append("Usage: FileAttributes <path>");
		}
		//write output
		System.out.println(sb.toString());
	}	 //end of main()
}	 //end of class
```

**Mostrar informació d'un directory i el nom dels seus fitxers (i subdirectoris)**

```java
/**
 * listFilesInDir():
 * Shows directory information and filenames in directory.
 * @param str: is the name of the directory to list.
 * @return a string containing the list of directories.
 */
 
public String listFilesInDir ( String str ) {
	File          dir  =  new File (str);
	StringBuilder sb   =  new StringBuilder();
	if ( dir.exists() && dir.isDirectory() ) {
		sb.append("\nAbsolute path: ");
		sb.append(dir.getAbsolutePath());
		sb.append("\nRelative path: ");
		sb.append(dir.getPath());
		sb.append("\nDirectory Name: ");
		sb.append(dir.getName());
		sb.append("\nContents: ");
		sb.append("\n------------------");
		String [] fileList = dir.list();
		for (int i=0; i<fileList.length; i++) {
			sb.append("\n");
			sb.append(fileList[i]);
			}
		}
	return sb.toString();
}//end of listFilesInDir
```

**Obtenir i mostrar (en format array) els noms de les entrades d'un subdirectory donat**

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

**Esborrar un directory i tots els seus fitxers**

Cal recordar que només es poden esborrar directoris buits.

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