import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * EscriureBytes.java
 * Example writing bytes to a file
 * @author ProvenSoft
 */
public class WriteBytes {
	public static void main(String[] args) {
		byte [] list ={10, 15, 25, 30, 45};
		if(args.length == 1) {	 //check parameter length 
			File f = new File(args[0]);
			try{
				FileOutputStream fos = new FileOutputStream(f);
				for(int i = 0; i < list.length; i++){
					fos.write(list[i]);
					fos.flush();
				}
				fos.close();
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
