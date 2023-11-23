import java.io.*;
/**
 * DirList.java:
 * This program lists the contents of a directory given by console, if exists.
 */
public class DirList {
	
		public static void main ( String [] args ) {
			
			String result=""; //gets the list of directories
			DirList myAp = new DirList();	
			
			if ( args.length > 0 ) {
				result = myAp.listFilesInDir(args[0]);
				}
			else {
				result = "Usage: DirList <dir>";
				}
			
			System.out.println(result);
		} //end of main
	
		/**
		 * listFilesInDir():
		 * Lists the files contained in the given directory.
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
	
} //end of class
