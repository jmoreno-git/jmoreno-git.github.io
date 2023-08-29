# Transformacions

El llenguatge XML serveix bàsicament per intercanviar i enmagatzemar informació.  Tot i que és un format mínimament llegible a simple vista, sovint cal presentar la informació dels fitxers XML amb una altra estructura o bé de forma més amena. 

El procés de modificar un fitxer XML convertint-lo a un altre format s'anomena **transformació**. 

Aquest procés també pot ser necessari en el cas d'haver de preparar un fitxer perquè pugui ser llegit per un programa específic.

## La clsse Transformer

El paquet [javax.xml.transform](https://docs.oracle.com/en/java/javase/17/docs/api/java.xml/javax/xml/transform/package-summary.html) conté les classes per efectuar transformacions.

Utilitzarem la classe [Transformer](https://docs.oracle.com/en/java/javase/19/docs/api/java.xml/javax/xml/transform/Transformer.html) per realitzar transformacions entre un arbre origen [Source](https://docs.oracle.com/en/java/javase/19/docs/api/java.xml/javax/xml/transform/Source.html) i un aarbre destí [Result](https://docs.oracle.com/en/java/javase/19/docs/api/java.xml/javax/xml/transform/Result.html).

![](/damm06/assets/1.2/sourceresulttransformation.jpg)

### Exemples

[Descàrrega de l'exemple sencer: TransformDemo](/damm06/assets/1.2/transform_demo.zip)

Per il·lustrar el funcionament d'aquesta API usarem les següents dades:

Fitxer de dades **staff.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<company>
    <staff nif="1A">
        <firstname>Peter</firstname>
        <lastname>Johnson</lastname>
        <nickname>pjohnson</nickname>
        <salary>1000.0</salary>
        <address>
            <street>A. Diagonal</street>
            <number>100</number>
        </address>
    </staff>
    <staff nif="2B">
        <firstname>Mary</firstname>
        <lastname>Anderson</lastname>
        <nickname>manderson</nickname>
        <salary>2000.0</salary>
    </staff>
    <staff nif="3B">
        <firstname>Lucas</firstname>
        <lastname>Morgan</lastname>
        <nickname> </nickname>
        <salary>1500.0</salary>
    </staff>
</company>
```

Fitxer DTD: **staff.dtd**

```dtd
<?xml version='1.0' encoding='UTF-8'?>
<!ELEMENT company (staff)*>
<!ELEMENT staff (firstname|lastname|nickname|salary|address)*>
<!ATTLIST staff
    nif CDATA #IMPLIED
  >
>
<!ELEMENT firstname (#PCDATA)>
<!ELEMENT lastname (#PCDATA)>
<!ELEMENT nickname (#PCDATA)>
<!ELEMENT salary (#PCDATA)>
<!ELEMENT address (street|number)*>
<!ELEMENT street (#PCDATA)>
<!ELEMENT number (#PCDATA)>
```

El mètode principal presenta un **menú** per escollir l'exemple que es vol provar:

```java
private void run() {
    try {
        // define menu.
        String [] demos = {
            "Document to file", "Node to file", "Document to string"
        };
        // display menu.
        for (int i=0; i<demos.length; i++) {
            System.out.format("%d. %s\n", i+1, demos[i]);
        }
        // read user's option.
        String option = inputString("Choose a demo: ");
        switch (option) {
            case "1": documentToFileDemo(); break;
            case "2": nodeToFileDemo(); break;
            case "3": documentToStringDemo(); break;
        }
    } catch (IOException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    }
}
```

**Exemple de lectura de fitxer xml en objecte Document i escriptura en un altre fitxer xml**

```java
/**
 * reads xml document from file and writes it to file.
 */
private void documentToFileDemo() {
     try {
        // Read file names from user.;
        String xmlInputFilename = inputString("Enter source XML file name: ");
        String xmlOutputFilename = inputString("Enter destination XML file name: ");
        //
        File xmlFile = new File(xmlInputFilename);
        if (xmlFile.exists()) {
            // Create a document builder factory..
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            // Use document builder factory.
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            // Parse the document and get a document.
            Document document = documentBuilder.parse(xmlFile); 
            // Create a transformer factory.
            TransformerFactory tranformerFactory = TransformerFactory.newInstance(); 
            // Get a transformer.
            Transformer transformer = tranformerFactory.newTransformer(); 
            // Configure transformer -> set transformer properties.
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            // Create Source and Result for transformer.
            Source source = new DOMSource(document);
            Result result = new StreamResult(new File(xmlOutputFilename));
            // Perform transformation.
            transformer.transform(source, result);
        } else {
            System.out.format("File '%s' not found.\n", xmlInputFilename);
        }
    } catch (ParserConfigurationException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SAXException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (TransformerConfigurationException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (TransformerException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    }

}
```

**Exemple de lectura de fitxer xml en objecte Document i escriptura en un altre fitxer del primer node amb etiqueta a escollir per l'usuari**

```java
/**
 * reads xml document from file and writes a given node to file.
 */
private void nodeToFileDemo() {
    try {
        // Read file names from user.;
        String xmlInputFilename = inputString("Enter source XML file name: ");
        String xmlOutputFilename = inputString("Enter destination XML file name: ");
        //
        File xmlFile = new File(xmlInputFilename);
        if (xmlFile.exists()) {
            // Create a document builder factory..
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            // Use document builder factory.
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            // Parse the document and get a document.
            Document document = documentBuilder.parse(xmlFile); 
            // Ask node tag to get.
            String tag = inputString("Input tag to get first element: ");
            // Get nodes with given tag.
            NodeList nodes = document.getElementsByTagName(tag);
            if (nodes.getLength()>0) {
                // Get first node with given tag.
                Node node = nodes.item(0);
                // Create a transformer factory.
                TransformerFactory tranformerFactory = TransformerFactory.newInstance(); 
                // Get a transformer.
                Transformer transformer = tranformerFactory.newTransformer(); 
                // Configure transformer -> set transformer properties.
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty(OutputKeys.INDENT, "no");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                // Create Source and Result for transformer.
                Source source = new DOMSource(node);
                Result result = new StreamResult(new File(xmlOutputFilename));
                // Perform transformation.
                transformer.transform(source, result);                    
            } else {
                System.out.format("Node with tag '%s' no found\n", tag);
            }
        } else {
            System.out.format("File '%s' not found.\n", xmlInputFilename);
        }
    } catch (ParserConfigurationException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SAXException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (TransformerConfigurationException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (TransformerException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    }

}
```

**Exemple de lectura de fitxer xml en objecte Document i escriptura en String amb un StringWriter**

```java
/**
 * reads xml document from file and writes it to file.
 */
private void documentToStringDemo() {
    try {
        // Read file names from user.;
        String xmlInputFilename = inputString("Enter source XML file name: ");;
        //
        File xmlFile = new File(xmlInputFilename);
        if (xmlFile.exists()) {
            // Create a document builder factory..
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            // Use document builder factory.
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            // Parse the document and get a document.
            Document document = documentBuilder.parse(xmlFile); 
            // Create a transformer factory.
            TransformerFactory tranformerFactory = TransformerFactory.newInstance(); 
            // Get a transformer.
            Transformer transformer = tranformerFactory.newTransformer(); 
            // Configure transformer -> set transformer properties.
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            // Create Source and Result for transformer.
            Source source = new DOMSource(document);
            StreamResult result = new StreamResult(new StringWriter());
            // Perform transformation.
            transformer.transform(source, result);
            System.out.println(result.getWriter().toString());
        } else {
            System.out.format("File '%s' not found.\n", xmlInputFilename);
        }
    } catch (ParserConfigurationException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SAXException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (TransformerConfigurationException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (TransformerException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(TransformDemo.class.getName()).log(Level.SEVERE, null, ex);
    }
}
```

## Creació de documents XML

La interfície [**Document**](https://docs.oracle.com/en/java/javase/19/docs/api/java.xml/org/w3c/dom/Document.html), la qual estén la interfície [**Node**](https://docs.oracle.com/en/java/javase/19/docs/api/java.xml/org/w3c/dom/Node.html) proporciona mètodes per crear nodes en funció del seu tipus i connectar-los a l'estructura del *DOM*. 

  * **createTextNode(String data):Text**: crea nodes de text.
  * **createElement(String tagName):Element**: crea nodes tipus element.
  * **createComment (String comment):Comment**: crea nodes tipus comment.
  * **createAttribute (String name):Attr**: crea un atribut identificat per //name//.

Donat un node, podem afegir-li un nou node com a descendent:
  * **appendChild (Node newChild):Node**: afegeix el node newChild com a darrer node del node donat.

### Exemple

Anem a crear dades de prova per a l'exemple:

```java
private List<Staff> generateTestData() {
    List<Staff> data = new ArrayList<>();
    data.add(new Staff("1A", "firstname01", "lastname01", "nickname01", 1001.0, new Address("street01", 101)));
    data.add(new Staff("2A", "firstname02", "lastname02", "nickname02", 1002.0, new Address("street02", 102)));
    data.add(new Staff("3A", "firstname03", "lastname03", "nickname03", 1003.0, new Address("street03", 103)));
    data.add(new Staff("4A", "firstname04", "lastname04", "nickname04", 1004.0, new Address("street04", 104)));
    data.add(new Staff("5A", "firstname05", "lastname05", "nickname05", 1005.0, new Address("street05", 105)));
    data.add(new Staff("6A", "firstname06", "lastname06", "nickname06", 1006.0, new Address("street06", 106)));
    return data;
}
```

Cal tractar de manera diferent cada element de l'arbre. Definirem un mètode específic per crear un node Element per a cada tipus complex. També caldrà un mètode genèric per als tipus simples, ja que aquests només tenen node de text.

**Creació d'elements de tipus simple**

```java
public Element createSimpleTypeElement(Document document, String elementName, String elementValue) {
    // create element.
    Element element = document.createElement(elementName);
    // create text node.
    Text textNode = document.createTextNode(elementValue);
    // append text node to element.
    element.appendChild(textNode);
    return element;
}
```

**Creació de l'element complex per a l'adreça**

L'adreça (*address*) és un element complex ja que conté subelements.

```java
public Element createAddressElement(Document document, Address address) {
    // create address element.
    Element addressElement = document.createElement("address");
    // create simple type elements.
    Element child;
    child = createSimpleTypeElement(document, "street", address.getStreet());
    addressElement.appendChild(child);
    child = createSimpleTypeElement(document, "number", String.valueOf(address.getNumber()));
    addressElement.appendChild(child);
    return addressElement;
}
```

**Creació de l'element complex per al personal**

Els elements *staff* són també tipus complexos ja que contenen sublements, alguns dels quals són també complexos (com l'adreça).

```java
private Element createStaffElement(Document document, Staff staff) {
    // create staff element.
    Element staffElement = document.createElement("staff");
    // add attribute 'nif' to element.
    staffElement.setAttribute("nif", staff.getNif());
    // create simple type elements.
    Element child;
    child = createSimpleTypeElement(document, "firstname", staff.getFirstname());
    staffElement.appendChild(child);
    child = createSimpleTypeElement(document, "lastname", staff.getLastname());
    staffElement.appendChild(child);
    child = createSimpleTypeElement(document, "nickname", staff.getNickname());
    staffElement.appendChild(child);
    child = createSimpleTypeElement(document, "salary", String.valueOf(staff.getSalary()));
    staffElement.appendChild(child);
    // creata address element.
    child = createAddressElement(document, staff.getAddress());
    staffElement.appendChild(child);
    return staffElement;
}
```

## El llenguatge XSL

El **llenguatge de plantilles XSL** (*extensible stylesheet language*) és una família de llenguatges que serveixen per definir transformacions i presentacions de documents *XML*. 

La família *XSL* està formada per tres llenguatges:
  * **XSL-FO** (*XSL formatting objects*): un llenguatge per definir el format que s’ha d’aplicar a un document.
  * **XSLT** (**XSL transformations*): un llenguatge per transformar documents XML.
  * **XPath**: un llenguatge per accedir a parts dels documents XML.

![](/damm06/assets/1.2/xml_xslt.png)

* **Element root:** 
* <xsl:stylesheet>: atributs genèrics del document.
* **Elements de la capçalera:** 
* <xsl:output>: permet seleccionar les opcions de sortida.
* <xsl:preserve-space>: conserva espais en blanc.
* <xsl:strip-space>: elimina espais en blanc no rellevants.
* <xsl:import>: permet usar un altre document com a plantilla.
* <xsl:decimal-format>: defineix un format numèric.
* **Elements bàsics:**
* <xsl:template>: defineix com es processa el contingut del document d'entrada. El seu atribut **//match//** serveix per seleccionar el conjunt de nodes sobre els que tindrà lloc la transformació.
* <xsl:include>: inserta un document d'estil. 
* **Instruccions de sortida:**
* <xsl:text>: genera un literal.
* <xsl:value-of>: genera com a resulat el valor d'una expresió.
* <xsl:element>: genera un nou element.
* <xsl:attribute>: genera un nou atribut.
* **Instruccions condicionals:**
* <xsl:for-each>: Itera sobre un conjunt de nodes.
* <xsl:if>: restringeix una transformació a l'avaluació d'una condició. No hi ha possibilitat d'indicar què fer en cas que la condició s'evalui a fals.
* <xsl:choose>: condicional múltiple. S'usa amb la instrucció <xsl:when [condició]> i <xsl:otherwise>.
* **Altres:**
* <xsl:comment>: crea un comentari.
* <xsl:sort>: processa els nodes en un determinat ordre.

Per a més informació: [](https://www.w3.org/TR/xslt20/)

Amb el següent codi realitzem la transformació d'un document xml origen aplicant una transformació basada en un document xsl. El resultat es desa en un document resultat.

```java
try {
    // Read file names from user.
    String xmlInputFilename = inputString("Enter source XML file name: ");
    String xslInputFilename = inputString("Enter source XSL file name: ");
    String xmlOutputFilename = inputString("Enter destination XML file name: ");
    Source input  = new StreamSource(xmlInputFilename);
    Source xsl    = new StreamSource(xslInputFilename);
    Result output = new StreamResult(xmlOutputFilename);
    // Perform transformation.
    TransformerFactory factory = TransformerFactory.newInstance();
    Transformer transformer = factory.newTransformer(xsl);
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.transform(input, output);
} catch (TransformerException ex) {
    Logger.getLogger(TransformUsingXSLT.class.getName()).log(Level.SEVERE, null, ex);
} catch (IOException ex) {
    Logger.getLogger(TransformUsingXSLT.class.getName()).log(Level.SEVERE, null, ex);
}
```

### Exemples

[Exemple de codi de transformació de fitxer xml amb plantilles xsl](/damm06/assets/1.2/transform_xslt.zip)

### Exercicis proposats

* Crear una llista d'objectes *Person* i desar-los en format xml d'acord amb el següent DTD:

```dtd
<?xml encoding="UTF-8"?>
<!ELEMENT persons (person*)>
<!ELEMENT person (firstname, surname, age, phone*, address*)>
<!ATTLIST person nif CDATA #REQUIRED>
<!ELEMENT firstname (#PCDATA)>
<!ELEMENT surname (#PCDATA)>
<!ELEMENT age (#PCDATA)>
<!ELEMENT phone (#PCDATA)>
<!ELEMENT address (city, road, number, floor, door)>
<!ELEMENT city (#PCDATA)>
<!ELEMENT road (#PCDATA)>
<!ATTLIST road type (street | avenue | square) #REQUIRED>
<!ELEMENT number (#PCDATA)>
<!ELEMENT floor (#PCDATA)>
<!ELEMENT door (#PCDATA)>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE persons SYSTEM "Person.dtd">
<persons>
    <person nif="12345678Z">
        <firstname>John</firstname>
        <surname>Smith</surname>
        <age>23</age>
        <phone>955555555</phone>
        <phone>699669966</phone>
        <address>
            <city>Barcelona</city>
            <road type="street">Mallorca</road>
            <number>44</number>
            <floor>3</floor>
            <door>1</door>
        </address>
    </person>
</persons>
```

* Llegir un fitxer xml amb dades de *Person* com les descrites abans i convertir-les a XHTML en format de taula.