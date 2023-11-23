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
