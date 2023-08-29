# UF3-NF1 Bases de dades XML

## Apunts i materials

  * [DAM2-M06-UF3 Guia d'aprenentatge](/damm06/assets/3.1/dam2-m06-uf3_guiaaprenentatge.pdf)
  * [Apunts de XML i BD (Univ. de Oviedo)](/damm06/assets/3.1/xml_y_bd-univ_oviedo.pdf)
  * [IOC. Apunts de Llenguatges de marques. Àmbits d'aplicació](/damm06/assets/3.1/ioc-ambits_aplicacio_xml.pdf)
  * [Use XQuery from a Java environment](/damm06/assets/3.1/xquery_and_java.pdf)

**Sistema gestor de base de dades XML**

  * [eXist database](http://exist-db.org)

**Java API XMLDB**

  * [Writing Java applications with XMLDB API](/damm06/assets/3.1/exist-writing_java_applications_with_xmldb-api.pdf)
  * [XML:DB API](https://github.com/xmldb-org/xmldb-api)

**Java API XQJ**

  * [XQJ: XQuery API for Java](http://xqj.net)
  * [XQJ tutorial](/damm06/assets/3.1/xqj-tutorial.pdf)

[Biblioteques XQJ a importar per programar amb Java i eXistdB](https://github.com/xmldb-org/xmldb-api)


## Exemples

**Fitxers xml de treball**

[xml_data_files.zip](/damm06/assets/3.1/xml_data_files.zip)

### Consultes XQuery i XPath

**Consulta XPath**
```xml
data(doc('dam2/cdcatalog.xml')/catalog/cd/title)
```

**Consulta XQuery**
```xml

for $x in doc('dam2/cdcatalog.xml')/catalog/cd 
where $x/year > 1990 
order by $x/title 
return data($x/title)
```

### ExistDb update extension

[XQuery Update Extension](https://exist-db.org/exist/apps/doc/update_ext)

```xml
xquery version "3.1";
(: ========Insert======== :)
update insert 
    <cd>
    <title>New title</title>
        <artist>New artist</artist>
        <country>New country</country>
        <company>New company</company>
        <price>20.00</price>
        <year>2000</year>
    </cd>
into /catalog
```

```xml
xquery version "3.1";
(: ========Delete======== :)
for $e in doc("dam2/cdcatalog.xml")/catalog/cd
where $e/title = "New title"
return update delete $e
```

```xml
xquery version "3.1";
(: ========Modify======== :)
for $e in doc("dam2/cdcatalog.xml")/catalog/cd
where $e/price = 9.90
return update replace $e/price with <price>9.91</price>
```

### Exemple codi Java

[Exemple d'ús de l'API XQJ amb eXistDb](/damm06/assets/3.1/xqjexistexample.zip)


#### Interface XQResultItem

Superinterfícies: **XQItem**, **XQItemAccessor**

Prototip:

```java
public interface XQResultItem extends XQItem
```

Aquesta interfície representa un objecte ***Item*** immutable obtingut d'un ***XQResultSequence*** usant el mètode ***getItem()***.

La seqüència es pot recórrer només cap endavant i no admet la invocació múltiples vegades de mètodes *get* sobre el mateix item. Per solventar aquest problema, es pot utilitzar el mètode *getItem()* per obtenir l'item actual i aplicar mètodes *get* múltiples vegades sobre aquest item.

L'objecte ***XQResultItem*** depén de la connexió, de l'expressió i, per tant, la seqüència és vàlida només mentre duren aquests objectes. Si algun d'aquests es tanca, l'*XQResultItem* és tancat implícitament i ja no es pot utilitzar. De la mateixa manera, si reexecutem l'expressió, també es tanca la seqüència de resultat associada.

El driver *XQJ* no té cap requeriment de tancar recursos. És molt recomanable tancar-los explícitament quan no siguin ja necessaris. Convé tancar-los en un bloc finally per assegurar-ne el tancament tot i que es puguin llançar excepcions.

Exemple:

```java
XQPreparedExpression expr = conn.prepareExpression("for $i ..");
XQResultSequence result = expr.executeQuery();

// posititioned before the first item
while (result.next())
{
XQResultItem item = result.getItem();
// perform multiple gets on this item 
// get DOM
org.w3.dom.Node node = item.getNode(); 
// get SAX
item.writeItemToSAX(saxHandler);

item.close();  // good practice.  Item will get implicitly closed
                // when the expression,  connection or sequence is closed.
}

result.close(); // explicitly close the result sequence
```

El següent mètode il·lustra com enviar una seqüència a un *SAX* *ContentHandler* per processar el resultat *XML* d'una consulta i recuperar les dades. En aquest cas, s'ha fet una consulta que retorna elements Cd, els quals cal convertir en una llista d'objectes Cd.

```java
    public List<Cd>fromResultSequenceToCdList(XQResultSequence rs) throws XQException {
    CdCatalogContentHandler handler = new CdCatalogContentHandler();
    rs.writeSequenceToSAX(handler);
    List<Cd> data = handler.getCdList();
    return data;
} 
```

### Pràctica

[Pràctica de gestió d'una escola (cursos i alumnes) amb eXistDb](/damm06/assets/3.1/dam2-m06-pt31-school_xml.odt)
