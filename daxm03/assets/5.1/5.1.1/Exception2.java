/**
 * Throwing exception and catching in other method
 *
 * @author Jose 
 */
public class Exception2 {

    public static void main(String[] args) {
        int x, y, z;
        //initialize variables
        x = 10;
        y = 0;  //change this value to assess when exception is thrown
        try {
            z = f(x, y);  //division by zero
            System.out.println("z=" + z);  //not executed if exception is thrown
        } catch (ArithmeticException ex) {
            //code to execute if exception is thrown
            System.out.println("Divide error: " + ex.getMessage());
        }
    }

    /**
     * performs integer division
     * @param a numerator
     * @param b denominator
     * @return quotient of integer division
     * @throws ArithmeticException if b is zero 
     * (optional declaration as AritmeticException is not required to be verified, it inherits from RuntimeException)
     */
    public static int f(int a, int b) throws ArithmeticException {
        return a / b;
    }
}
