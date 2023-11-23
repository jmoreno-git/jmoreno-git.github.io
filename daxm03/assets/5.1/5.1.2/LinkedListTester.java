
import java.util.*;

/**
 * Program to test LinkedList functionality
 *
 * @author Jose
 */
public class LinkedListTester {

    public static void main(String[] args) {
        //declare and instantiate the list
        List<Integer> list1 = new LinkedList<>();
        //populate the list with some data
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(5);
        list1.add(8);
        list1.add(13);
        list1.add(21);
        list1.add(34);
        list1.add(3);

        System.out.println("Size = " + list1.size());

        System.out.println("Test get method");
        Integer elem = list1.get(3);
        System.out.println("Element at index 3 is = " + elem);

        System.out.println("\nTest get method  --> exception");
        try {
            Integer elem2 = list1.get(99);  //this element does not exist
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Exception message " + e.getMessage());
            // e.printStackTrace();
        }

        System.out.println("\nTest indexOf method");
        int i = list1.indexOf(13);
        System.out.println("The index of element 13 is " + i);

        int i2 = list1.indexOf(0);
        System.out.println("The index of element 0 is " + i2);

        System.out.println("\nTest lastIndexOf method");
        int i3 = list1.lastIndexOf(3);
        System.out.println("The last index of element 3 is " + i3);

        int i4 = list1.lastIndexOf(0);
        System.out.println("The last index of element 0 is " + i4);

        System.out.println("\nLoop using get");
        for (int j = 0; j < list1.size(); j++) {
            System.out.println(list1.get(j));
        }

        System.out.println("\nTest set method");
        list1.set(2, 22);
        for (int j = 0; j < list1.size(); j++) {
            System.out.println(list1.get(j));
        }

    }

}
