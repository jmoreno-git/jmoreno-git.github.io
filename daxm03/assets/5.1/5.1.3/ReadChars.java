import java.io.*;
/**
 * ReadChars.java
 * Example reading characters from file
 * @author ProvenSoft
 */
public class ReadChars {
	public static void main(String[] args) {
		int c; //character read
		if(args.length == 1) {	//check parameter length
			try{
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			while ((c=br.read()) != -1) {
				System.out.print((char)c);
			}
			System.out.print("\n");
			br.close();
			} catch (FileNotFoundException e){
				System.out.println ("File not found. Exception info:");
				//e.printStackTrace();
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
