
import java.util.Scanner;

/**
 * Example of how to throw an exception
 *
 * @author Jose
 */
public class Exception4 {

    public static void main(String[] args) {
        Exception4 a = new Exception4();
        a.run();
    }

    private void run() {
        try {
            int age = readAge();  //this method can throw Exception
            System.out.println("Your age is: " + age);
        } catch (Exception ex) {
            System.out.println("Age must be a positive number: "+ex.getMessage());
        }
    }

    /**
     * inputs age from user
     * @return age
     * @throws Exception 
     */
    public int readAge() throws Exception {  //declare exception that could be thrown
        Scanner sc = new Scanner(System.in);
        System.out.print("Input you age: ");
        int age = sc.nextInt();
        if (age < 0) {  //throw some exception
            throw new Exception("age should not be negative");
        }
        return age;
    }
}
