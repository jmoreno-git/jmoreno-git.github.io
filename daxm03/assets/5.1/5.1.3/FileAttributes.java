import java.io.*;
/**
 * FileAttributes.java
 * This program shows the properties of a file given by console.
 */
public class FileAttributes {
	
	public static void main(String [] args) throws IOException {
		
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
