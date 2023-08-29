# Document Object Model (DOM)

[Document object model](/damm06/assets/1.2/document_object_model.pdf)

Java proporciona la interface ***Document*** per representar tot el document XML. Per instanciar estructures *DOM* a partir d'un fitxer *XML*, cal usar les classes [**DocumentBuilderFactory**](https://docs.oracle.com/en/java/javase/19/docs/api/java.xml/javax/xml/parsers/DocumentBuilderFactory.html) i [**DocumentBuilder**](https://docs.oracle.com/en/java/javase/19/docs/api/java.xml/javax/xml/parsers/DocumentBuilder.html).

El següent fragment de codi mostra l'esquema bàsic per llegir un fitxer *xml* en un objecte *Document*.

```java
DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
dbFactory.setValidating(true);  //set if necessary
DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
Document doc = dBuilder.parse(new File("TestFile.xml"));  
```

Un objecte *DOM* està estrcuturat en forma d'arbre on cada part de l’*XML* es representa en forma de **node**. Un document *XML* té diferents tipus de nodes. El node principal que representa tot l’*XML* sencer s’anomena **document**, i les diverses etiquetes, inclosa l’etiqueta arrel, es coneixen com a nodes **element**.

El contingut textual d’una etiqueta s’instancia com a node de tipus **text** i els atributs com a nodes de tipus **Attribute**.

Cal tenir en compte que un node de text es representa com a node fill de la seva etiqueta contenidora.

Cada node específic disposa de mètodes per accedir a les seves dades concretes: **getNodeType()**, **getNodeName()**, **getNodeValue()**.

| NodeType                   | nodeName                 | nodeValue                          |
| ------ | ------ | ------ |
| ATTRIBUTE_NODE             | Name of attribute        | Value of attribute                 |
| CDATA_SECTION_NODE         | #cdata-section           | Content of the CDATA section       |
| COMMENT_NODE               | #comment                 | Content of the comment             |
| DOCUMENT_NODE              | #document                | null                               |
| DOCUMENT_FRAGMENT_NODE     | #documentFragment        | null                               |
| DOCUMENT_TYPE_NODE         | Document Type name       | null                               |
| ELEMENT_NODE               | Tag name                 | null                               |
| ENTITY_NODE                | Entity name              | null                               |
| ENTITY_REFERENCE_NODE      | Name of entity referenced| null                               |
| NOTATION_NODE              | Notation name            | null                               |
| PROCESSING_INSTRUCTION_NODE| Target                   | Entire content excluding the target|
| TEXT_NODE                  | #text                    | Content of the text node           |

Les classes més usades per a representar en Java els nodes del document són **Node** i **Element**.

Per obtenir un conjunt de nodes disposem de mètodes com **getChildNodes()* o **getElementsByTagName(etiqueta)**.

| Classe | Mètode  |  Retorna |
| ------ | ------ | ------ |
| Document      | getElementsByTagName(etiqueta)          | NodeList      |
| Node          | getChildNodes()        | NodeList                |

![Estructura DOM d'un document XML](/damm06/assets/1.2/domgraph.png)

## Exemples

[Mostrar elements de fitxer xml: Staff (versió recursiva)](/damm06/assets/1.2/staffxmldom.zip)

[Exemple de definició d'esquema XML (XSD): Agenda](/damm06/assets/1.2/agenda-xml_xsd.zip)

[Exemple lectura DOM immobiliària amb GUI: RealEstate](/damm06/assets/1.2/realestatedomgui.zip)

## Exercicis

[Aplicació lectura amb DOM de les dades d'una escola](/damm06/assets/1.2/schooldomap.zip). Cal completar les funcionalitats del prototip.

