# Javabeans

Els **JavaBeans** són un model de components per a la construcció d'aplicacions en Java.

S'usen per encapsular diversos objectes en un únic objecte, per fer ús d'un sol objecte en lloc de diversos més simples.

L'especificació de JavaBeans els defineix com "components de programari reutilitzables que es puguin manipular visualment en una eina de construcció".

Tot i haver-hi moltes semblances, els JavaBeans no s'han de confondre amb els Enterprise JavaBeans (EJB), una tecnologia de components de la banda servidor que és part de Java EE.

[Javabeans tutorial](http://docs.oracle.com/javase/tutorial/javabeans/index.html)

[Java EE 6 tutorial](/damm06/assets/4.1/javaeetutorial6.pdf)

## Especificacions dels JavaBeans

Per funcionar com un JavaBean, una classe ha d'obeir certes especificacions sobre nomenclatura de mètodes, construcció i comportamen, les quals permeten tenir eines que puguin utilitzar, reutilitzar, substituir i connectar JavaBeans:

* Ha de tenir un constructor sense arguments.
* Els seus atributs de classe han de ser privats.
* Les seves propietats han de ser accessibles mitjançant mètodes get i set que segueixen una convenció de nomenclatura estàndard.
* Ha de ser serialitzable.

L'estructura d'un JavaBean ha d'incloure:

* Propietats: Els atributs que conté.
* Mètodes: S'estableixen els mètodes get i set per accedir de lectura i escriptura respectivament sobre els atributs.
* Esdeveniments: Permeten la comunicació amb altres JavaBeans.

[JavaBeans API documentation](https://docs.oracle.com/javase/8/docs/technotes/guides/beans/reference.html)

## Primer exemple

En llenguatge Java, els components s'acostumen a anomenar ***beans***.

Aquí tenim un exemple d'una classe que encapsula un tipus de dada 'producte' incorporant allò necessari per esdevenir un *bean*.

```java
import java.beans.*;
import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    
    private PropertyChangeSupport propertySupport;
    private VetoableChangeSupport vetoableSupport;
    
    private String code;
    private String description;
    private double costPrice;
    private double stock;
    private double minStock;
    
    public Product() {
        propertySupport = new PropertyChangeSupport(this);
        vetoableSupport = new VetoableChangeSupport(this);
    }

    public Product(String code, String description, double costPrice, 
            double stock, double minStock) {
        propertySupport = new PropertyChangeSupport(this);
        vetoableSupport = new VetoableChangeSupport(this);
        this.code = code;
        this.description = description;
        this.costPrice = costPrice;
        this.stock = stock;
        this.minStock = minStock;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) throws PropertyVetoException {
        String previous = getCode();
        vetoableSupport.fireVetoableChange("code", previous, code);
        this.code = code;
        propertySupport.firePropertyChange("code", previous, code);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) throws PropertyVetoException {
        double previous = getCostPrice();
        vetoableSupport.fireVetoableChange("costPrice", previous, costPrice);
        this.costPrice = costPrice;
        propertySupport.firePropertyChange("costPrice", previous, costPrice);    
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) throws PropertyVetoException {
        double previous = getStock();
        vetoableSupport.fireVetoableChange("stock", previous, stock);
        this.stock = stock;
        propertySupport.firePropertyChange("stock", previous, stock);      
    }

    public double getMinStock() {
        return minStock;
    }

    public void setMinStock(double minStock) {
        this.minStock = minStock;
    }
   
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vetoableSupport.addVetoableChangeListener(listener);
    }
    
    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        vetoableSupport.removeVetoableChangeListener(listener);
    }  
    
    @Override
    public int hashCode() { ... }

    @Override
    public boolean equals(Object obj) { ... }

    @Override
    public String toString() { ... }
    
}
```

La següent classe permet provar la seva funcionalitat.

```java
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class ProductBeanDemo {

    public static void main(String[] args) throws PropertyVetoException {
        PropertyChangeListener pcl = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Property change: "+evt.getPropertyName()+" old value: "+evt.getOldValue()+" new value: "+evt.getNewValue());                
            }
        };
        VetoableChangeListener vcl = new VetoableChangeListener() {
            @Override
            public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
                if (((double)evt.getNewValue())<0.0) throw new PropertyVetoException("Negative values not allowed", evt);
            }
        };
        Product p = new Product("P1", "Desc P1", 10.0, 100.0, 5.0);    
        p.addPropertyChangeListener(pcl);
        p.addVetoableChangeListener(vcl);
        p.setCostPrice(20.0);
        p.setCostPrice(-30.0);
    }
    
}
```

Codi complet de l'exemple: [beans_for_category_and_product.zip](/damm06/assets/4.1/beans_for_category_and_product.zip)

## Persistència de JavaBeans en XML

A continuació teniu un exemple d'un JavaBean senzill i del procediment per fer-lo persistent en format xml.

Com es pot comprovar, la classe implementa la interfície Serializable, té els seus atributs privats i proveeix accés de lectura i escriptura als atributs mitjançant els mètodes accessors, els quals segueixen el conveni de nom getX, setX on X és el nom de l'atribut.

**Person.java**
```java
/**
 * TAD Person
 * @author Jose
 */
 
 import java.io.Serializable;
 
 public class Person implements Serializable {
	/* fields, attributes, properties */
	private String nif;
	private String name;
	private int age;
	/* constructors */
	/** 
	 * full initializer constructor
	 * @param String nif: the nif id of this person
	 * @param String name: the name of this person
	 * @param int age: the age of this person
	 */
	public Person(String nif, String name, int age) {
		this.nif = nif;
		this.name = name;
		this.age = age; 
	}
	/** 
	 * constructor without any initialization (default constructor)
	 */
	public Person() {
		this.nif = "";
		this.name = "";
		this.age = 0; 
	}
	/** 
	 * copy constructor
	 * @param p: person to copy from
	 */
	public Person(Person p) {
		nif = p.nif;
		name = p.name;
		age = p.age; 
	}
	/* accessors */
	/** 
	 * @return nif
	 */
	public String getNif() {
		return nif;
	}
	/** 
	 * @param nif: the nif to set
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}
	/** 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/** 
	 * @param name: the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/** 
	 * @param none
	 * @return age
	 */
	public int getAge() {
		return age;
	}
	/** 
	 * @param int age: the age to set
	 */
	public void setAge(int age) {
		if ( age >= 0 ) this.age = age;
		else age = 0;
	}
	/* methods */
	
	/** toString()
	 * gives a String representations of this person
	 * @param none
	 * @return a string representation of this person
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{Person: [nif=");
		sb.append(nif);
		sb.append("][name=");
		sb.append(name);
		sb.append("][age=");
		sb.append(age);
		sb.append("]}");
		return sb.toString();
	}
	/** equals()
	 * compares this person to another one
	 * two persons are equals if their nifs are equals.
	 * @param Person other: the other person to compare to
	 * @return true if they are equals, false otherwise
	 */
	public boolean equals(Object obj) {
		boolean b = false;
		if (obj == null) {
			b= false;
		} else {
			if (obj == this) {
				b = true;
			} else {
				if (obj instanceof Person) {
				    Person other = (Person) obj;
				    b = (this.nif.equals(other.nif);
				} else {
					b = false;
				}
			}
		}
		return b;
	}
}
```

Com a primer exemple d'ús de *Javabeans*, aquí teniu el codi per fer persistent un *JavaBean* en format *xml* en fitxer.

**XmlPersistence.java**
```java
/**
 * XmlPersistence.java
 * Example: persistence of a bean to a xml file.
 * @author Jose
 */

import java.io.*;
import java.beans.*;

public class XmlPersistence {

 public static void main (String args[]) {
		// instantiate an object.
		Object object1 = new Person("12345678Z", "Peter", 20);
		// show the object an its type.
		System.out.println("Initial->"+object1.toString());
		System.out.println("Initial->Class:"+object1.getClass().getName());
		try {
			// persist object to xml file
			XMLEncoder encoder = new XMLEncoder(
			   new BufferedOutputStream(
			   new FileOutputStream("XmlPersistence.xml")));

			encoder.writeObject(object1);
			encoder.close(); 
			
			// recover object from xml file
			XMLDecoder decoder = new XMLDecoder(
				new BufferedInputStream(
				new FileInputStream("XmlPersistence.xml")));

			Object object2 = decoder.readObject();
			decoder.close();
			
			// show the recovered object an its type.
			System.out.println("Final->"+object2.toString());
			System.out.println("Final->Class:"+object2.getClass().getName());
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
```

El fitxer generat és el següent:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<java version="11.0.4" class="java.beans.XMLDecoder">
 <object class="Person">
  <void property="age">
   <int>20</int>
  </void>
  <void property="name">
   <string>Peter</string>
  </void>
  <void property="nif">
   <string>12345678Z</string>
  </void>
 </object>
</java>
```

Codi sencer de l'exemple: [xmlPersistence.zip](/damm06/assets/4.1/xmlpersistence.zip)

## Sincronització (binding) entre components

Aquest exemple il·lustra el mecanisme de comunicació entre components utilitzant els esdeveniments de canvi d'una propietat.

Per habilitar aquesta funcionalitat, cal que en modificar una propietat es llanci un esdeveniment [**PropertyChangeEvent**](https://docs.oracle.com/javase/8/docs/api/java/beans/PropertyChangeEvent.html). Prèviament, cal que s'hagi registrat en l'objecte observat un observador [**PropertyChangeListener**](https://docs.oracle.com/javase/8/docs/api/java/beans/PropertyChangeListener.html), el qual, mitjançant el seu mètode **void propertyChange(PropertyChangeEvent evt)** realitza alguna acció en resposta a aquest canvi en la propietat. Els **PropertyChangeListener** es poden registrar per a qualsevol propietat membre del *bean* o per a qualsevol propietat especificada per nom.

Per facilitar totes aquestes tasques, disposem de la classe [**PropertyChangeSupport**](https://docs.oracle.com/javase/8/docs/api/java/beans/PropertyChangeSupport.html). Aquesta classe gestiona una llista d'observadors (*listeners*) i els envia esdeveniments *PropertyChangeEvent*. Normalment, definim una instància d'aquesta classe com a atribut membre de la classe del *bean* i li deleguem aquesta feina.

A continuació tenim el codi mínim per a un *JavaBean* amb gestió d'esdeveniments de canvi de propietat.

**BeanA.java**
```java
import java.beans.*;
import java.io.Serializable;
/**
 * Class BeanA
 * @author Jose
 */
public class BeanA implements Serializable {
    
    public static final String X_PROPERTY = "x";
    private final PropertyChangeSupport propertySupport;
    
    private String x;
        
    public BeanA() {
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public String getX() {
        return x;
    }
    
    public void setX(String value) {
        String oldValue = x;
        x = value;
        propertySupport.firePropertyChange(X_PROPERTY, oldValue, x);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    
    @Override
    public String toString() {
        return "BeanA{" + "x=" + x + '}';
    }    
    
}
```

Un cop s'ha modificat una propietat del bean, es dispara l'esdeveniment (es comunica als listeners) mitjançant el mètode **public PropertyChangeSupport(Object sourceBean)** de la classe *PropertyChangeSuport*.

En el següent exemple, es creen dos objectes JavaBean i s'inicialitza la seva propietat x amb valors inicials diferents. Es crea un *PropertyChangeListener handler*, per tractar què s'ha de fer en resposta al canvi de la propietat x. En aquest exemple senzill, l'únic que fa és copiar el valor del bean *source* al de *target*, de manera que els valors estiguin sempre sincronitzats. Després, el listener es registra al *bean* *source* per rebre esdeveniments de canvi de la propietat x. Per últim, canviem la propietat x i mostrem els objectes *source* i *target* per verificar que estan sincronitzats.

**BeanBindingExample01.java**
```java
import java.beans.PropertyChangeListener;

public class BeanBindingExample01 {
  
    public static void main(String[] args) {
        
        BeanA source, target;
        //instantiate objects.
        source = new BeanA();
        target = new BeanA();        
        //initialize values.
        source.setX("B1-inic");
        target.setX("B2-inic");
        //create property change listener.
        PropertyChangeListener handler = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //copy property value of event source object to target object.
                target.setX((String)evt.getNewValue());                
            }           
        };
        
        alert("b1="+source.toString());
        alert("b2="+target.toString());
        //add property change listener to source object.
        source.addPropertyChangeListener(handler);
        //change source object.
        source.setX("B1-changed");
        //show objects.
        alert("b1="+source.toString());
        alert("b2="+target.toString());
        //change source object.
        source.setX("B1-final");
        //show objects.
        alert("b1="+source.toString());
        alert("b2="+target.toString());        
        
    }
    
    private static void alert(String s) {
        System.out.println(s);
    }
}
```

Codi complet de l'exemple: [beanbindingexample01.zip](/damm06/assets/4.1/beanbindingexample01.zip)


## Esdeveniments de canvi i veto de propietats

A més de disparar esdeveniments de canvi de propietats, també es poden disparar esdeveniments de veto, els quals es disparen abans de produir-se el canvi i permeten prendre mesures de control abans de la modificació de la propietat.

El mecanisme és semblant al del canvi. Cal crear i registrar al *bean* un [VetoableChangeListener](https://docs.oracle.com/javase/8/docs/api/java/beans/VetoableChangeListener), el cual tracta l'esdeveniment amb el seu mètode ***void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException*** . Normalment, en aquest mètode es realitzen les verificacions que siguin pertinents i, cas que es vulgui vetar el canvi en la propietat, es llança l'excepció ***PropertyVetoException***. La resposta final a aquest veto la farà el fragment de codi que capturi aquesta excepció.

Per facilitar la gestió dels esdeveniments de veto disposem de la classe [VetoableChangeSupport](https://docs.oracle.com/javase/8/docs/api/java/beans/VetoableChangeSupport)., la qual informa els listeners a través del seu mètode **fireVetoableChange**.

A continuació es mostra un bean per a encapsular un objecte Product amb les capacitat de comunicació d'esdeveniments de canvi i de veto.

**Product.java**
```java
/* Bean: Product.
 * Example of bean definition.
 * @author Jose
 */

import java.beans.*;
import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    
    private PropertyChangeSupport propertySupport;
    private VetoableChangeSupport vetoableSupport;
    
    private String code;
    private String description;
    private double costPrice;
    private double stock;
    private double minStock;
    
    public Product() {
        propertySupport = new PropertyChangeSupport(this);
        vetoableSupport = new VetoableChangeSupport(this);
    }

    public Product(String code, String description, double costPrice, 
            double stock, double minStock) {
        propertySupport = new PropertyChangeSupport(this);
        vetoableSupport = new VetoableChangeSupport(this);
        this.code = code;
        this.description = description;
        this.costPrice = costPrice;
        this.stock = stock;
        this.minStock = minStock;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) throws PropertyVetoException {
        String previous = getCode();
        vetoableSupport.fireVetoableChange("code", previous, code);
        this.code = code;
        propertySupport.firePropertyChange("code", previous, code);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) throws PropertyVetoException {
        double previous = getCostPrice();
        vetoableSupport.fireVetoableChange("costPrice", previous, costPrice);
        this.costPrice = costPrice;
        propertySupport.firePropertyChange("costPrice", previous, costPrice);    
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) throws PropertyVetoException {
        double previous = getStock();
        vetoableSupport.fireVetoableChange("stock", previous, stock);
        this.stock = stock;
        propertySupport.firePropertyChange("stock", previous, stock);      
    }

    public double getMinStock() {
        return minStock;
    }

    public void setMinStock(double minStock) {
        this.minStock = minStock;
    }
   
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vetoableSupport.addVetoableChangeListener(listener);
    }
    
    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        vetoableSupport.removeVetoableChangeListener(listener);
    }  
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.code);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "code=" + code + ", description=" + description + ", costPrice=" + costPrice + ", stock=" + stock + ", minStock=" + minStock + '}';
    }
    
}
```

El següent programa principal il·lustra com usar la classe anterior. En aquest cas, el veto impedeix valors negatius on no són permesos.

**ProductVetoExample.java**
```java
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class ProductVetoExample {

    public static void main(String[] args) throws PropertyVetoException {
        PropertyChangeListener pcl = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Property change: "+evt.getPropertyName()+" old value: "+evt.getOldValue()+" new value: "+evt.getNewValue());                
            }
        };
        VetoableChangeListener vcl = new VetoableChangeListener() {
            @Override
            public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
                if (((double)evt.getNewValue())<0.0) throw new PropertyVetoException("Negative values not allowed", evt);
            }
        };
        Product p = new Product("P1", "Desc P1", 10.0, 100.0, 5.0);    
        p.addPropertyChangeListener(pcl);
        p.addVetoableChangeListener(vcl);
        p.setCostPrice(20.0);
        p.setCostPrice(-30.0);
    }
    
}
```

Codi complet de l'exemple: [javabeansevents.zip](/damm06/assets/4.1/javabeansevents.zip)

## Més exemples

### Sincronització de components gràfics amb el seu model de dades

[Formulari sincronitzat amb el seu model (objecte Person)](formbind.md)

### Comunicació entre panells amb PropertyChange

[Programa amb dos panells per a càlculs de preus amb i sense IVA](/damm06/assets/4.1/calc_iva-dos_panells.zip).

### Component gràfic interactiu

Seguiu pas a pas aquest tutorial de com crear un bean gràfic i interactuar amb ell.

[FaceBean.zip](/damm06/assets/4.1/FaceBean.zip), [FaceFrame.zip](/damm06/assets/4.1/FaceFrame.zip), [Biblioteca BeansBinding](/damm06/assets/4.1/jar_files.zip)

### Llista amb punt d'interès com a JavaBean

[Llista amb punt d'interès](/damm06/assets/4.1/scrollablelist.zip)

### GUI Master-Detail

Exemple de component compost de dos panells:
* llista de productes (es mostra el codi)
* formulari de producte (es mostren totes les dades del producte seleccionat a la llista)

[master-detail.zip](/damm06/assets/4.1/master-detail.zip)

[School groups-students (GUI master-detail)](/damm06/assets/4.1/school-groupsstudents.zip)

### Component Data-Browser

Conté dos projectes, un amb el navegador de dades i un altre amb un exemple d'ús.

Les dades del DataBrowser s'inicialitzen al constructor però es poden canviar i es mantenen sincronitzades. Serveix per a qualsevol tipus de dada. Utilitza *reflection*.

Versió completa, no gràfica: [component-databrowser_example.zip](/damm06/assets/4.1/component-databrowser_example.zip)

Versió simplificada, gràfica: [component-guidatabrowser_example.zip](/damm06/assets/4.1/component-guidatabrowser_example.zip)