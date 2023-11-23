
/**
 * Program to compare functionality between List and Set java classes
 * @author ProvenSoft
 */

import java.util.*;

public class ListVsSetTester {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ListVsSetTester ct = new ListVsSetTester();
        
               
       
        // ******* Deleting zeros *******************
        List <Integer> arrayNum = new ArrayList<>();
        
        //Adding data
        arrayNum.add(1);
        arrayNum.add(0);
        arrayNum.add(23);
        arrayNum.add(0);
        arrayNum.add(0);
        arrayNum.add(12);
        
        // Deleting zeros from an integer's array
        System.out.println("Deleting zeros from an integer's array");
        ct.deleteZeros(arrayNum);
        System.out.println(arrayNum);
        
      
		// ******* Removing elements *******************
		
		List <Integer> list1 = new ArrayList <> ();
		
		
        //Adding data
        list1.add(1);
        list1.add(3);
        list1.add(5);
        list1.add(8);
        list1.add(13);
        list1.add(21);
        list1.add(34);
		
		System.out.println("Removing even elements");
        ct.removeEven (list1);
        System.out.println(list1);
      
		// ******* Checking adding duplicates in set*******************
        Collection <Integer> setTest  = new HashSet <> ();
        boolean b;
        System.out.println("Checking add method");
        b = setTest.add(1);  System.out.println("Has the element 1 been added? \n" + b);
        b = setTest.add(3);  System.out.println("Has the element 3 been added? \n" + b);
        b = setTest.add(8);  System.out.println("Has the element 8 been added? \n" + b);
        b = setTest.add(13); System.out.println("Has the element 13 been added? \n" + b);
        b = setTest.add(8);  System.out.println("Has the element 8 been added? \n" + b);
        b = setTest.add(21); System.out.println("Has the element 21 been added? \n" + b);
        b = setTest.add(34); System.out.println("Has the element 34 been added? \n" + b);
        
            
        System.out.println ("Size setTest= " +setTest.size());
      
        System.out.println("Printing setTest with a for-each");
        for (Integer o:setTest){
            System.out.println(o);
        }
        
        
        
    }
    /**
     * Removes all 0 items of zeros collection
     * @param zeros source collection
     */
    private void deleteZeros (Collection <Integer> zeros) {
		
		 Iterator<Integer> it = zeros.iterator();
		 while (it.hasNext()){
			int i = it.next();
			if ( i== 0){
				it.remove();
			}	 
		}
	}
    
    /**
     * Removes even numbers of col collection
     * @param col source collection
     */
    private void removeEven (Collection <Integer> col){
    
            Iterator <Integer> it = col.iterator();
            while (it.hasNext()){
                int i = it.next();
                if ((i % 2) == 0){
                    it.remove();
                }
            }
    }
   
}
        
    

