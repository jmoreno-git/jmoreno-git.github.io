
package stringexerc;
import java.util.Scanner;
import cypher.XifratCesar;

/**
 *
 * @author ProvenSoft
 */
public class XifratDeMissatges {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");
        System.out.print("Entra la frase a xifrar: ");
        String textAXifrar = sc.next();
        System.out.print("Entra el desplaçament: ");
        int desplacament = sc.nextInt();
        //
        System.out.println("Text a xifrar: "+ textAXifrar);
        //
        String textXifrat = XifratCesar.xifrarCesar(textAXifrar, desplacament);
        System.out.println("Text xifrat: "+ textXifrat);
        //
        String textDesxifrat = XifratCesar.desxifrarCesar(textXifrat, desplacament);
        System.out.println("Text desxifrat: "+ textDesxifrat);
        
    }
    
}
