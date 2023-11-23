import java.io.*;
/**
 * WriteChars.java
 * Example writing characters to a file
 * @author ProvenSoft
 */
public class WriteChars {
	public static void main(String[] args) {
		char [] list = {'a', 'e', 'i', 'o', 'u'};
		if(args.length == 1) {	//check parameter length 
			try{
				BufferedWriter bw = new BufferedWriter(new FileWriter(args[0]));
				for(int i = 0; i < list.length; i++){
					bw.write(list[i]);
				}
				bw.close();
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
