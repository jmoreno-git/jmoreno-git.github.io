# Conceptes bàsics de GUI

## Continguts

* Components i contenidors
* Disposicions (*layouts*)
* Esdeveniments i observadors

![](/images/no_programo_para_cobardes.gif)

Per programar interfícies gràfiques d'usuari (*GUI*) utilitzarem la biblioteca ***Swing***. Aquesta biblioteca està construida amb paradigma d'orientació a components, on cada component implementa una variant de l'arquitectura model-vista-controlador (*MVC*), semblant a la coneguda amb el nom model-vista-presentador.

[Apunts de Conceptes bàsics de programació amb GUI en Java](assets/5.2/dax2_m03-a521-GUI_conceptes_basics.pdf)

[Presentació de GUI amb Swing](assets/5.2/gui_amb_swing.pdf)

[Jerarquia de la biblioteca swing](assets/5.2/swing_hierarchy.jpg)

### Primera aplicació amb Swing

Les finestres que poden residir sobre el sistema operatiu són els [***JFrame***](https://docs.oracle.com/en/java/javase/20/docs/api/java.desktop/javax/swing/JFrame.html).

Un *JFrame* pot tenir un títol, una barra de menús i una capa de contingut (content pane) on ubicar el contingut de la finestra. També disposa dels botons habituals per minimitzar, maximitzar i tancar la finestra.

El comportament per defecte del botó de tancament es defineix amb el mètode *setDefaultCloseOperation()*.

Si cal forçar el repintat d'un contenidor, es pot fer amb el mètode *validate()*.

[HelloWorldSwing.java (descàrrega)](assets/5.2/HelloWorldSwing.java)
```
import javax.swing.*;        

public class HelloWorldSwing {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
```

La càrrega de la interfície gràfica es fa amb el mètode invokeLater(), el qual assegura que es carreguen els components amb el fil d'execució del tractament dels esdeveniments.

Per dimensionar la finestra utilitzem el mètode *setSize()*. Alternativament, el mètode *pack()* la redimensiona al menor tamany possible tenint en compte el seu contingut.

Como a contenidor de components s'acostuma a utilizar la classe [***JPanel***](https://docs.oracle.com/en/java/javase/20/docs/api/java.desktop/javax/swing/JPanel.html).


Per tal de facilitar la ubicació dels components dintre dels contenidors, disposem de *layouts* predefinits: [**FlowLayout**](https://docs.oracle.com/en/java/javase/20/docs/api/java.desktop/java/awt/FlowLayout.html), [**BorderLayout**](https://docs.oracle.com/en/java/javase/20/docs/api/java.desktop/java/awt/BorderLayout.html), [**GridLayout**](https://docs.oracle.com/en/java/javase/20/docs/api/java.desktop/java/awt/GridLayout.html), [**GridBagLayout**](https://docs.oracle.com/en/java/javase/20/docs/api/java.desktop/java/awt/GridBagLayout.html), ...

### Components

Els components més habituals són:
  * [***JButton***](https://docs.oracle.com/en/java/javase/20/docs/api/java.desktop/javax/swing/JButton.html)
  * JLabel
  * JTextField
  * JCheckbox
  * JRadioButton

### Layouts

[Layouts swing (from www.jairogarciarincon.com)](https://www.jairogarciarincon.com/clase/interfaces-de-usuario-con-java-swing/layout-managers-o-gestores-de-composicion)

#### BorderLayout

[BorderLayout](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/BorderLayout.html) disposa els elements d'un contenidor en 5 regions: nord, sud, est, oest i centre. En afegir cada component al contenidor, cal especificar a quina posició ha d'anar.

![](assets/5.2/borderlayout.png)

    Panel p = new Panel();
    p.setLayout(new BorderLayout());
    p.add(new Button("Okay"), BorderLayout.SOUTH);

[BorderLayoutExample.java (descàrrega)](assets/5.2/BorderLayoutExample.java)
```
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;        
/**
 * BorderLayoutExample.java
 * Exemple d'us de BorderLayout.
 * @author Jose Moreno
 */
public class BorderLayoutExample {

    private static void initGUI() {

        JFrame frame = new JFrame("BorderLayoutExample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//afegir adaptador per a l'esdeveniment de tancament de la finestra
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
        //obtenir el panell de contingut de la finestra.
		Container pane = frame.getContentPane();
		//configurar el layout (per defecte ja és BorderLayout).
		pane.setLayout(new BorderLayout());

		//crear array de referències a JLabel
		JLabel [] etiq = new JLabel[5];
		//inicialitzar etiquetes
		etiq[0] = new JLabel( "Etiqueta A" );
		etiq[0].setHorizontalAlignment( SwingConstants.CENTER );
		etiq[1] = new JLabel( "Etiqueta B" );
		etiq[1].setHorizontalAlignment( SwingConstants.CENTER );
		etiq[1].setBackground( Color.YELLOW );
		etiq[1].setOpaque(true); //per defecte, els components són transparents.
		etiq[2] = new JLabel( "Etiqueta C" );
		etiq[2].setHorizontalAlignment( SwingConstants.CENTER );
		etiq[2].setBackground( Color.BLUE );
		etiq[2].setForeground( Color.WHITE );
		etiq[2].setOpaque(true);
		etiq[3] = new JLabel( "Etiqueta D" );
		etiq[3].setHorizontalAlignment( SwingConstants.CENTER );
		etiq[3].setBackground( Color.CYAN );
		etiq[3].setOpaque(true);
		etiq[4] = new JLabel( "Etiqueta E" );
		etiq[4].setHorizontalAlignment( SwingConstants.CENTER );
		etiq[4].setBackground( Color.BLACK );
		etiq[4].setForeground( Color.PINK );
		etiq[4].setOpaque(true);
		//afegir components al panell de continguts de la finestra.
		pane.add( etiq[0], BorderLayout.NORTH );
		pane.add( etiq[1], BorderLayout.SOUTH );
		pane.add( etiq[2], BorderLayout.EAST );
		pane.add( etiq[3], BorderLayout.WEST );
		pane.add( etiq[4], BorderLayout.CENTER );
		
		//dimensionar i mostrar la finestra.
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initGUI();
            }
        });
    }
}
```

### Exemple captura clic en botons

A l'exemple següent es defineix un panell amb un camp de text i dos botons. 

En clicar cada botó, s'actualiza el contingut del camp de text amb un text diferent per a cada botó.

Cada botó té associat un ActionListener (amb el método *JButton.addActionListener()*), el qual dóna resposta a l'acció a través del seu mètode *actionPerformed(ActionEvent evt)*.

L'objecte [***ActionEvent***](https://docs.oracle.com/en/java/javase/19/docs/api/java.desktop/java/awt/event/ActionEvent.html) que rep com a paràmetre conté mètodes per identificar el component sobre el que s'ha executat l'acció:

  * **getSource()**: retorna l'objecte sobre el que s'ha actuat
  * **getActionCommand()**: retorna un String identificatiu de l'acció associada (aquest valor s'especifica amb el mètode *setActionCommand()* de l'objecte, en aquest cas, del botó)

A l'exemple, el botó és identificat amb el mètode *getSource()*, i usant el seu nom.

```java
/**
* TextBotonsAp.java
* Exemple de finestra amb dos botons i text
* @author Jose Moreno
* @version
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class PanellBotoText extends JPanel
{
	private JTextField text;
	
	public PanellBotoText()
	{
		//crear observador d'esdeveniments.
		ActionListener al = new ActionListener() {
			public void actionPerformed( ActionEvent evt ) {
				String nomBoto = ( (JButton)evt.getSource()).getText();
				text.setText( "Premut "+nomBoto );
			}
		};
		//instanciar camp de text.
		text = new JTextField( 20 );
		//definir text alternatiu del camp de text
		text.setToolTipText( "Camp de text" );
		//afegir camp de text al panell
		add( text );
		//crear boto1.
		JButton boto1 = new JButton( "Botó 1" );
		//afegir observador a boto1
		boto1.addActionListener( al );
		//definir text alternatiu boto1
		boto1.setToolTipText( "Botó 1" );
		//afegir boto1 al panell
		add( boto1 );
		//crear boto2.
		JButton boto2 = new JButton( "Botó 2" );
		//afegir observador a boto2
		boto2.addActionListener( al );
		//definir text alternatiu boto2
		boto2.setToolTipText( "Botó 2" );
		//afegir boto2 al panell
		add( boto2 );
	}
}
// classe principal
public class TextBotonsAp
{
	public static void initGui() {
		JFrame finestra = new JFrame( "Finestra amb dos botons i text" );
		// afegir observador tancament finestra
		finestra.addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent evt ){
				System.exit( 0 );
			}
		} );
		//afegir panell contenidor dels botons i camp de text
		finestra.getContentPane().add( new PanellBotoText(), BorderLayout.CENTER );
		//definir dimensions finestra
		finestra.setSize( 300,100 );
		//fer visible la finestra
		finestra.setVisible( true );
	}
	
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initGui();
            }
        });
    }
}
```

### Exemple aplicació càlcul índex de massa corporal

Exemple de desenvolupament d'una aplicació amb interfície gràfica d'usuari (*GUI*) amb un menú i un únic panell, el qual conté un formulari de càlcul de l'índex de massa corporal d'una persona, donats l'altura (en m) i el pes (en kg).

[Descàrrega de l'exemple complet](assets/5.2/dam2m06uf52-bmi.zip)

#### Classe principal

La classe principal només instancia i mostra la finestra principal de l'aplicació.

```java
public class BmiMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BmiFrame mainFrame = new BmiFrame();
                mainFrame.setVisible(true);
            }
        });
    }  
}
```

La classe *BmiFrame* és la que defineix la finestra principal.

#### La finestra principal

Creem una classe que estengui *JFrame*. El mètode *initComponents()* s'encarrega de crear i afegir al frame els components gràfics necessaris. 

Cal definir el títol, l'operació de tancament per defecte, les dimensions de la finestra i la ubicació a la pantalla, entre altres coses.

També definir una barra de menú per a l'aplicació. D'això s'encarrega el mètode *setUpMenu()*.

```java
public class BmiFrame extends JFrame {
    
    private BmiPanel bmiPanel;
    
    public BmiFrame() {
        initComponents();
    }
    
    private void initComponents() {
        //set window title
        setTitle("BMI application");
        //set default close operation when close button is clicked
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //configure menu bar
        setUpMenu();
        //display bmi panel
        displayBmiPanel();
        //set window size
        setSize(400, 300);
        //set window location
        setLocationRelativeTo(null);  //center in screen
    }

    private void setUpMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu;
        JMenuItem menuItem;
        //
        menu = new JMenu("File");
            //
            menuItem = new JMenuItem("Exit");
            menuItem.setActionCommand("exit");
            menuItem.addActionListener(listener);
            menu.add(menuItem);
            //
        menuBar.add(menu);
        //
        menu = new JMenu("Help");
            //
            menuItem = new JMenuItem("About");
            menuItem.setActionCommand("about");
            menuItem.addActionListener(listener);
            menu.add(menuItem);
            //
        menuBar.add(menu);
        //
        setJMenuBar(menuBar);
    }

    private void displayBmiPanel() {
        //TODO instantiate BmiPanel and put in frame content pane.
    }
    
}
```

#### El formulari de càlcul

El formulari de càlcul de l'índex de massa corporal estarà en un panell independent, el qual contindrà un títol i les parelles etiqueta:camp per introduir les dades d'altura i pes, així com dos botons, un per esborrar els camps del formulari i un altre per realitzar el càlcul de l'índex de massa corporal. El resultat s'escriu en un altre camp de text.

```java
public class BmiPanel extends JPanel {
    
    private JTextField tfWeight;
    private JTextField tfHeight;
    private JTextField tfBmi;

    private ActionListener listener;
    
    public BmiPanel() {
        initComponents();
        doClear();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        //create header label
        JLabel lbHeader = new JLabel("BMI calculation form");
        lbHeader.setHorizontalAlignment(JLabel.CENTER);
        add(lbHeader, BorderLayout.NORTH);
        //create calculation form
        JPanel form = createBmiForm();
        add(form, BorderLayout.CENTER);
    }
    
    private JPanel createBmiForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,2));
        //
        panel.add(new JLabel("Weight:"));
        tfWeight = new JTextField(20);
        panel.add(tfWeight);
        //
        panel.add(new JLabel("Height:"));
        tfHeight = new JTextField(20);
        panel.add(tfHeight);
        //
        JButton btClear = new JButton("Clear");
        btClear.setActionCommand("clear");
        panel.add(btClear);
        JButton btCalc = new JButton("Calculate");
        btCalc.setActionCommand("calculate");
        panel.add(btCalc);
        //
        panel.add(new JLabel("Body mass index:"));
        tfBmi = new JTextField(20);
        tfBmi.setEditable(false);
        panel.add(tfBmi);
        //
        return panel;
    }

    private void doClear() {
        tfWeight.setText("0.0");
        tfHeight.setText("0.0");
        tfBmi.setText("0.0");        
    }

}
```

#### El model de dades

La classe *Bmi* és l'encarregada de realitzar els càlculs.

```java
public class Bmi {
    public double bmiCalc(double weight, double height) {
        return weight / (height * height);
    }
}
```

Per realitzar els càlculs necessitarem, doncs, instanciar un objecte de la classe *Bmi* i invocar el seu mètode *bmiCalc()*.

#### Accions de la finestra principal

Per tractar les accions de l'usuari amb la finestra principal, assignem un *ActionListener* per a les accions de cada *MenuItem* i un *WindowListener* per al botó de tancament de la finestra.

Per respondre al botó de tancament de la finestra, creem un *WindowListener* independent, del qual cal definir el mètode *windowClosing()*. 

```java
//add window listener to close window when close button is clicked
addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent e) {
        doExit();
    }
});
```

El *ActionListener* per als menús serà el propi *JFrame*. Per això, fem que implementi l'interface *ActionListener*, la qual cosa implica afegir un mètode *actionPerformed()* que respongui a les accions. Per a cada acció es defineix un mètode de control que realitza la resposta.

```java
public class BmiFrame extends JFrame implements ActionListener {
    
    private BmiPanel bmiPanel;
    
    private ActionListener listener;
    
    public BmiFrame() {
        listener = this;
        initComponents();
    }
    //...
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "exit":
                doExit();
                break;
            case "about":
                displayAboutDialog();
                break;
            default:
                break;
        }
    }

    private void doExit() {
        //TODO ask for confirmation
        System.exit(0);
    }

}
```

A cada *MenuItem* dels diferents menús de la barra de menú els hem d'afegir un *ActionListener*: el propi *JFrame*.

```java
menuItem.addActionListener(listener);
``` 

El mètode *displayAboutDialog()* mostra una fiestra de diàleg (modal) amb la informació de l'aplicació.

La informació a mostrar és a l'atribut *aboutMessage*, el qual afegim al *JFrame* i inicialitzem al constructor. Podem afegir format *html* al missatge.

```java
private String aboutMessage;

private void displayAboutDialog() {
    JOptionPane.showMessageDialog(this, aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
}
```

La classe **JOptionPane** proporciona mètodes per a diversos tipus de quadres de diàleg modals:

* Confirmació (*showConfirmDialog*)
* Missatge (*showMessageDialog*)
* Entrada (*showInputDialog*)

El mètode *doExit()* s'encarrega de demanar a l'usuari confirmació abans de sortir de l'aplicació i sortir, si és el cas.

```java
private void doExit() {
    //ask for confirmation
    int answer = JOptionPane.showConfirmDialog(this, "Are you sure?", "Exit application", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
    if (answer == JOptionPane.OK_OPTION) {
        //exit
        System.exit(0);
    }
}
```

#### Accions del panell del formulari de càlcul

Afegim un atribut per al model (classe *Bmi* per encapsular i realitzar els càlculs) i per al *ActionListener* del panell, que en aquest cas serà el propi panell. Per això, fem que implementi *ActionListener* i afegim un mètode *actionPerformed()* per respondre a les accions.

A cada botó li afegim com a ActionListener el propi panell.

```java
public class BmiPanel extends JPanel implements ActionListener {
    
    private Bmi model;
    
    private JTextField tfWeight;
    private JTextField tfHeight;
    private JTextField tfBmi;

    private ActionListener listener;
    
    public BmiPanel() {
        model = new Bmi();
        listener = this;
        initComponents();
        doClear();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        //create header label
        JLabel lbHeader = new JLabel("BMI calculation form");
        lbHeader.setHorizontalAlignment(JLabel.CENTER);
        add(lbHeader, BorderLayout.NORTH);
        //create calculation form
        JPanel form = createBmiForm();
        add(form, BorderLayout.CENTER);
    }
    
    private JPanel createBmiForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,2));
        //
        panel.add(new JLabel("Weight:"));
        tfWeight = new JTextField(20);
        panel.add(tfWeight);
        //
        panel.add(new JLabel("Height:"));
        tfHeight = new JTextField(20);
        panel.add(tfHeight);
        //
        JButton btClear = new JButton("Clear");
        btClear.setActionCommand("clear");
        btClear.addActionListener(listener);
        panel.add(btClear);
        JButton btCalc = new JButton("Calculate");
        btCalc.setActionCommand("calculate");
        btCalc.addActionListener(listener);
        panel.add(btCalc);
        //
        panel.add(new JLabel("Body mass index:"));
        tfBmi = new JTextField(20);
        tfBmi.setEditable(false);
        panel.add(tfBmi);
        //
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "clear":
                doClear();
                break;
            case "calculate":
                doCalculate();
                break;
            default:
                break;
        }
    }

    private void doClear() {
        tfWeight.setText("0.0");
        tfHeight.setText("0.0");
        tfBmi.setText("0.0");        
    }

    private void doCalculate() {
        //TODO
    }
}
```

#### Realització dels càlculs

```java
private void doCalculate() {
    //read data from form
    String sWeight = tfWeight.getText();
    String sHeight = tfHeight.getText();
    try {
        //parse data
        double weight = Double.parseDouble(sWeight);
        double height = Double.parseDouble(sHeight);
        //calculate
        double bmi = model.bmiCalc(weight, height);
        //write result to form
        tfBmi.setText(String.valueOf(bmi));            
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid data", "Validation", JOptionPane.ERROR_MESSAGE);
    }

}
```

[Exemple BMI. Descàrrega del codi complet.](assets/5.2/damm03uf5nf2-bmi.zip)

## GUi i accés a dades

El següent exemple il·lustra com fer una interfície gràfica d'usuari per substituir una de text que permeti mostrar dades obtingudes de consultes a una base de dades. Si el codi està ben construït, seguint els patrons explicats fins ara, no caldrà modificar el codi de la capa de negoci de l'aplicació (model i capa de persistència).

[Store amb GUI: categories, productes i proveïdors](assets/5.2/damm03uf5nf2-categprodgui.zip)