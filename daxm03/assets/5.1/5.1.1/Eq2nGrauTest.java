/**
 * Eq2nGrauTest.java
 * Programa per resoldre una equacio de segon grau
 * @author Jose Moreno
 * @version 2
 */
import java.io.*;
public class Eq2nGrauTest {
    public static void main(String[] args) {
        BufferedReader br = 
        	new BufferedReader(new InputStreamReader(System.in));
        //Entrada de coeficients
        System.out.println("Entra els coeficients de l'equació ax2+bx+c=0");
        try {
        	 System.out.print("a = ");
        	 double a = Double.valueOf(br.readLine()).doubleValue();
        	 System.out.print("b = ");
        	 double b = Double.valueOf(br.readLine()).doubleValue();
        	 System.out.print("c = ");
        	 double c = Double.valueOf(br.readLine()).doubleValue();  
        	 try {
        	 	Eq2nGrau eq2g = new Eq2nGrau(a, b, c);
        	 	eq2g.solucionar();
        	 	System.out.println(
        	 		"Solucions: "+eq2g.getSolucio(1)+", "+eq2g.getSolucio(2)
        	 	);
        	 }
        	 catch (PrimerGrauException pg) {
        	 	System.out.println(pg.getMessage());
        	 }
        	 catch (CapSolucioRealException csr) {
        	 	System.out.println(csr.getMessage());
        	 }      	 
        }
        catch (NumberFormatException nfe) {
        	System.out.println("La dada entrada no es numerica. " + nfe.getMessage());
        }   
        catch (IOException ioe) {
        	System.out.println("Error en lectura de coeficients. " + ioe.getMessage());
        }   
    }
}
class Eq2nGrau { 
	private double a, b, c; // coeficients de l'equacio ax2+bx+c=0  
	private double [] x; // solucions de l'equacio    
    public Eq2nGrau(double a, double b, double c) {
    	this.a=a; this.b=b; this.c=c;
    	x = new double[2];
    }
    public double getSolucio(int n) {
    	return x[n-1];
    }
    public void solucionar() throws PrimerGrauException, CapSolucioRealException {
    	if (a==0.0) throw new PrimerGrauException("a=0. L'equacio es de primer grau");
    	else { //l'equacio es de segon grau
    		double discriminant=b*b-4*a*c;
    		if (discriminant<0) 
    			throw new CapSolucioRealException("Discriminant negatiu. Cap solució real");
    		else { //l'equacio te solucio (discriminant no negatiu)
    			double d = Math.sqrt(discriminant);
    			// calcular les solucions
    			x[0] = (-b+d)/(2*a);
    			x[1] = (-b-d)/(2*a);
    		}
    	}	
    }
}
class PrimerGrauException extends Exception {
	public PrimerGrauException(String s) {
		super(s);
	}
}
class CapSolucioRealException extends Exception {
	public CapSolucioRealException(String s) {
		super(s);
	}
}