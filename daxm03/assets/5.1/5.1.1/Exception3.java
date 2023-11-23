
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Handling several exceptions. This class executes a division of 2 integers
 * (numerator and denominator) and assigns its result to an item of an array of
 * integers. Numerator, denominator and array index are entered by user.
 */
public class Exception3 {

    private int numerator;
    private int denominator;
    private int result;
    private int index;
    private int[] array;

    public static void main(String[] args) {
        Exception3 myApp = new Exception3();
        myApp.run();
    }

    public void run() {
        array = new int[5]; // Initialize array
        try {
            // Read input parameters
            inputData();
            // Execute function
            result = fDivide(numerator, denominator);
            fAssign(result, index);
            // Reports result
            System.out.format("Array [%d] = %d\n", index, result);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Out of bounds: " + ex.getMessage());
        } catch (ArithmeticException ex) {
            System.out.println("Arithmetic error: " + ex.getMessage());
        } 
//        } catch (IndexOutOfBoundsException | ArithmeticException ex) {  //multicatch
//            System.out.println("Error: "+ex.getMessage());
//        }
//        } catch (Exception e) {  //generic catch, since all exceptions inherit from Exception
//            System.out.println("Error: " + ex.getMessage());
//        }
        finally {
            array = null;
            System.out.println("Finally block");
        }

    }

    public void inputData() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Input numerator: ");
            numerator = sc.nextInt();
            System.out.print("Input denominator: ");
            denominator = sc.nextInt();
            System.out.print("Input index: ");
            index = sc.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Wrong input value: " + ime.getMessage());
            //ime.printStackTrace();
        }
    }

    public int fDivide(int a, int b) throws ArithmeticException {
        return a / b;
    }

    public void fAssign(int value, int pos) throws IndexOutOfBoundsException {
        array[pos] = value;
    }
}
