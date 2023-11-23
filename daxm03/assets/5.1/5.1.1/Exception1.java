/**
 * Throwing and catching exception in the same method
 *
 * @author Jose
 */
public class Exception1 {

    public static void main(String[] args) {
        int x, y, z;
        //initialize variables
        x = 10;
        y = 0;  //change this value to assess when exception is thrown
        try {
            z = x / y;  //division by zero
            System.out.println("z=" + z); //not executed if exception is thrown
        } catch (ArithmeticException ex) {
            //code to execute if exception is thrown
            System.out.println("Divide error: " + ex.getMessage());
        } finally {
            //code to execute irrespective of exception throwing
            System.out.println("This code is always executed!");
        }
    }
}
