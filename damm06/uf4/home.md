# UF4 Components d'accés a dades

Un component de programari és un element d'un sistema escrit d'acord amb unes especificacions, el qual ofereix un servei predefinit,és capaç de comunicar-se amb altres components i té la característica de la reusabilitat.

Els components han de poder ser serialitzables per poder-los compartir entre components i per poder-los enviar a diferents xarxes.

La capacitat de ser reutilitzat (***reusability***), és una característica important dels components de programari d'alta qualitat. Un component ha de ser dissenyat i implementat de tal manera que pugui ser reutilitzat en molts programes diferents.

Per tal de garantir la reusabilitat, el component ha d'estar:

* Completament documentat.
* Provat intensivament:
* Ha de ser robust, comprovant la validesa de les entrades.
* Ha de ser capaç de passar missatges d'error apropiats.
* Dissenyat pensant que serà usat de maneres imprevistes.

[Component based software engineering](https://en.wikipedia.org/wiki/Component-based_software_engineering)

En conseqüència, un component és una classe pública serialitzable, amb unes propietats públiques que s'exposen als altres components i amb una sèrie d'esdeveniments que poden ser capturats i associats a una sèrie d'accions.

Els components tenen atributs (privats) i propietats (fetes públiques a través de mètodes *get* i *set*). Normalment, les propietats estan associades a atributs, però no sempre.

Els components poden tenir també propietats indexades:

```java
public <TipusProp>[] get<NomProp>()
public void set<NomProp> (<TipusProp>[] p)
public <TipusProp> get<NomProp>(int index)
public void set<NomProp> (int index, <TipusProp> p)
```

Els components poden generar esdeveniments, que poden ser capturats i tractats externament per altres components o objectes, anomenats observadors (***listeners***). Per a això, el component emmagatzema els observadors en llistes. Els observadors sol·liciten al component suscriure's als seus esdeveniments a través de mètodes ***add<NomEsdeveniment>()***.

Els components gràfics de Java extenen ***java.awt.Component***.

El disseny dels components fa que es puguin utilitzar a través d'interfícies específiques sense necessitat de conèixer la seva estructura ni funcionament interns. També ha de garantir la reusabilitat.

[Els components en Java: Javabeans](javabeans.md)