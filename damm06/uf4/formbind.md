# Formulari GUI sincronitzat amb el seu model

Aquest exemple il·lustra com generar un formulari per a entrada de dades per a un objecte (*Person*) i mantenir sincronitzats els valors de les propietats de l'objecte amb els valors dels camps del formulari.

A continuació el codi de la classe *Person*:

**Person.java**
```java
public class Person implements Serializable {

    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    private final transient VetoableChangeSupport vetoableChangeSupport = new java.beans.VetoableChangeSupport(this);
    public static final String PROP_ID = "id";
    public static final String PROP_NAME = "name";
    public static final String PROP_AGE = "age";
    private int id;
    private String name;
    private int age;

    public Person() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     * @throws java.beans.PropertyVetoException
     */
    public void setId(int id) throws PropertyVetoException {
        int oldId = this.id;
        vetoableChangeSupport.fireVetoableChange(PROP_ID, oldId, id);
        this.id = id;
        propertyChangeSupport.firePropertyChange(PROP_ID, oldId, id);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     * @throws java.beans.PropertyVetoException
     */
    public void setName(String name) throws PropertyVetoException {
        java.lang.String oldName = this.name;
        vetoableChangeSupport.fireVetoableChange(PROP_NAME, oldName, name);
        this.name = name;
        propertyChangeSupport.firePropertyChange(PROP_NAME, oldName, name);
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     * @throws java.beans.PropertyVetoException
     */
    public void setAge(int age) throws PropertyVetoException {
        int oldAge = this.age;
        vetoableChangeSupport.fireVetoableChange(PROP_AGE, oldAge, age);
        this.age = age;
        propertyChangeSupport.firePropertyChange(PROP_AGE, oldAge, age);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }
    
    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.addVetoableChangeListener(listener);
    }

    public void addVetoableChangeListener(String propertyName, VetoableChangeListener listener) {
        vetoableChangeSupport.addVetoableChangeListener(propertyName, listener);
    }

    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.removeVetoableChangeListener(listener);
    }

    public void removeVetoableChangeListener(String propertyName, VetoableChangeListener listener) {
        vetoableChangeSupport.removeVetoableChangeListener(propertyName, listener);
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", name=" + name + ", age=" + age + '}';
    }
    
}
```

La classe *Person* és un *JavaBean*. És serialitzable, té els atributs privats i accessors amb la nomenclatura típica, un constructor sense paràmetres i incorpora la gestió d'esdeveniments de canvi i veto de les propietats.

I el codi del formulari:

**PersonForm.java**
```java
public class PersonForm extends javax.swing.JPanel {

    private Person personObj;  //the object connected to form fields.
    
    // Variables declaration - do not modify                     
    private javax.swing.JLabel ageLbl;
    private javax.swing.JTextField ageText;
    private javax.swing.JLabel idLbl;
    private javax.swing.JTextField idText;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JTextField nameText;
    private javax.swing.JButton saveBtn;
    // End of variables declaration 
    
    /**
     * Creates new form PersonForm
     */
    public PersonForm() {
        personObj = new Person();
        initComponents();
        personObj.addPropertyChangeListener((evt) -> {
            String propertyName = evt.getPropertyName();
            switch (propertyName) {
                case "id":
                    idText.setText(String.valueOf(evt.getNewValue()));
                    break;
                case "name":
                    nameText.setText(String.valueOf(evt.getNewValue()));
                    break;
                case "age":
                   ageText.setText(String.valueOf(evt.getNewValue()));
                    break;
                default:
                    break;
            }
            System.out.println(personObj.toString());
        });
    }
                         
    private void initComponents() {

        idLbl = new javax.swing.JLabel();
        idText = new javax.swing.JTextField();
        nameLbl = new javax.swing.JLabel();
        nameText = new javax.swing.JTextField();
        ageLbl = new javax.swing.JLabel();
        ageText = new javax.swing.JTextField();
        saveBtn = new javax.swing.JButton();

        setToolTipText("id");

        idLbl.setText("Id: ");

        idText.setToolTipText("id");
        idText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                idTextFocusLost(evt);
            }
        });

        nameLbl.setText("Name");

        nameText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameTextFocusLost(evt);
            }
        });

        ageLbl.setText("Age: ");

        ageText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ageTextFocusLost(evt);
            }
        });

        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        
//...   WRITE HERE CODE TO LAYOUT FORM COMPONENTS.

    }                       

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {                                        
        try {
            personObj.setId(Integer.parseInt(idText.getText()));
            personObj.setName(nameText.getText());
            personObj.setAge(Integer.parseInt(ageText.getText()));
        } catch (PropertyVetoException ex) {
            Logger.getLogger(PersonForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                       

    private void idTextFocusLost(java.awt.event.FocusEvent evt) {                                 
        try {
            personObj.setId(Integer.parseInt(idText.getText()));
        } catch (PropertyVetoException ex) {
            Logger.getLogger(PersonForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                

    private void nameTextFocusLost(java.awt.event.FocusEvent evt) {                                   
        try {
            personObj.setName(nameText.getText());
        } catch (PropertyVetoException ex) {
            Logger.getLogger(PersonForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                  

    private void ageTextFocusLost(java.awt.event.FocusEvent evt) {                                  
        try {
            personObj.setAge(Integer.parseInt(ageText.getText()));
        } catch (PropertyVetoException ex) {
            Logger.getLogger(PersonForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                 
                  
    /**
     * @return the personObj
     */
    public Person getPersonObj() {
        return personObj;
    }

    /**
     * @param personObj the personObj to set
     */
    public void setPersonObj(Person personObj) {
        this.personObj = personObj;
    }
}
```

El mètode ***saveBtnActionPerformed()*** respon a l'esdeveniment del botó ***save*** copiant les dades dels camps del formulari en les propietats de l'objecte *Person*.

A més, l'exemple il·lustra com respondre a altres esdeveniments, com ara el de perdre el focus d'un camp, copiant la informació d'aquest camp a l'atribut corresponent de l'objecte.

**MainFrame.java**
```java
public class MainFrame extends javax.swing.JFrame {

    // Variables declaration - do not modify                     
    private javax.swing.JButton chgBtn;
    private cat.proven.formbind.PersonForm personForm;
    // End of variables declaration   
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }
                        
    private void initComponents() {

        personForm = new cat.proven.formbind.PersonForm();
        chgBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FormBind");

        chgBtn.setText("Change");
        chgBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chgBtnActionPerformed(evt);
            }
        });

// ...  WRITE HERE CODE TO LAYOUT COMPONENTS.

        pack();
    }                       

    private void chgBtnActionPerformed(java.awt.event.ActionEvent evt) {                                       
        try {
            Person p = personForm.getPersonObj();
            p.setId(20);
            p.setName("Paul");
            p.setAge(80);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                      
                    
}
```

La interacció amb el formulari es produeix amb l'acció del botó ***change***, el qual envia al formulari les dades de l'objecte *Person* que s'han de mostrar. En aquest senzill exemple, les dades són inventades, però en una aplicació real, aquestes dades podrien provenir d'alguna consulta a un medi de persistència.

El programa arranca amb aquest principal:

**Main.java**
```java
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame main = new MainFrame();
            main.setVisible(true); 
        });
    
    }
}
```

A mesura que aneu fent canvis al formulari o interactuar amb els botons, es van generant esdeveniments i es va escrivint en consola el valor de l'objecte *Person* perquè pugueu traçar què va passant en cada moment.

Codi complet de l'exemple: [formbind.zip](/damm06/assets/4.1/formbind.zip)
