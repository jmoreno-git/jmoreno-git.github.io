/**
* Generic application. Main class.
* Here is a prototype of any simple application. 
* It can be used to test the application functional requirements.
*/
import java.util.Scanner;
import java.util.InputMismatchException; 
//change class name.
public class AppTemplate {
	
	/* ------- attributes, properties or fields ------- */
	
	/* the options in the main menu of the application 
	 * (i.e. the functional requirements) */
	//Change options
	private final String [] mainMenuOptions = {
		 "Exit", 
		 "Option 1", 
		 "Option 2",
		 "Option 3", 
		 "Option 4",
		 "Option 5"
	};
	
	/**
	 * main(). 
	 * Starts up application execution.
	 */
    public static void main(String[] args) {
		/* application object */
		AppTemplate myAp = new AppTemplate();
		/* run the application */
		myAp.run();
		
	}
	
    /* -------------- methods -------------- */
    
    /** run()
     * runs the application in non-static mode.
     */	
	private void run() {	
		/* exit flag */
		boolean exit = false;
		/* menu option to execute */
		int option;
		/* read initial data from persistent storage and/or initialize data*/
		loadData();
		/* user service loop  */
		do {
			//show menu and get option
			option = showMainMenu();
			//control block
			switch (option) {
				case 0: //exit
					exit = true;  //set the exit flag.
					break;
				case 1: //tell here what this option does
					//TODO
					alert("Executing option 1 ...\n");
					break;
				case 2: //tell here what this option does
					//TODO
					alert("Executing option 2 ...\n");
					break;
				case 3: //tell here what this option does
					//TODO
					alert("Executing option 3 ...\n");
					break;
				case 4: //tell here what this option does
					//TODO
					break;
				case 5: //tell here what this option does
					//TODO
					alert("Executing option 5 ...\n");
					break;
				default: //default or invalid option
					alert("Invalid option\n");
					break;
			}
		} while (!exit);
		/* save data to persistent storage */
		saveData();
		alert("Closing application ...\n");
    }  

    /** showMainMenu()
     * shows the application main menu and gets user's option
     * @return option to execute
     */
     private int showMainMenu() {
		int option=-1;	//option to return	
		try {
			Scanner scan = new Scanner(System.in);
			System.out.print("\n===== Main menu =====");
			for (int i=0; i<mainMenuOptions.length; i++) {
				System.out.format("\n(%d) %s", i, mainMenuOptions[i]);
			} 
			System.out.print("\n=============================");
			System.out.print("\nEnter option: ");
			option = scan.nextInt();
		} catch (InputMismatchException ime) {
			option = -1;
		}
		return option;
	}
    /** alert()
     * shows a message
     * @param String msg: message to show
     */
     private void alert(String msg) {
		System.out.print(msg);
	 }
    /** loadData()
     * loads and initializes data
     */
     private void loadData() {
		//TODO
		alert("Loading data ...\n");
	 }	
    /** loadData()
     * saves data
     */
     private void saveData() {
		//TODO
		alert("Saving data ...\n");
	 }
}
