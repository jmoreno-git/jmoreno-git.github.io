# Seriació d'objectes

La **seriació** (*serialization*) és el procés d'escriure un objecte a un stream de bytes. És útil quan es vol fer persistent l'estat d'un programa a un fitxer per exemple. El procés invers s'anomena **deseriació** (*deserialization*).

Només els objectes que implementen l'interfície [***Serializable***](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/Serializable.html) poden ser desats i restaurats usant la serialització. Si una classe és serialitzable, totes les seves subclasses també ho són.

Nota: les variables ***transient*** i ***static*** no es guarden durant la serialització.
Usarem les **interfícies** ***ObjectOutput*** i ***ObjectInput***, i les **classes** ***ObjectOutputStream*** i ***ObjectInputStream*** que les implementen.

Nota: les interfícies *ObjectOutput* i *ObjectInput* estenen respectivament *DataOutput* i *DataInput*, per la qual cosa hereten tots els seus mètodes.

![](/damm06/assets/1.1/1.1.io/object-serialization.png)

La interfície [***ObjectOutput***](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/ObjectOutput.html) estén les interfícies ***DataOutput*** i ***AutoCloseable*** i suporta serialització. Defineix els mètodes ***close()***, ***flush()***, ***write()*** i ***writeObject()***. Aquest últim s'invoca per serialitzar un objecte. Tots els mètodes llancen ***IOException*** si es produeix un error.

La interfície [***ObjectInput***](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/ObjectInput.html) estén les interfícies ***DataInput*** i ***AutoCloseable*** i suporta serialització. Defineix els mètodes ***close()***, ***available()***, ***read()*** i ***readObject()***. Aquest últim s'invoca per llegir un objecte serialitzat. Tots els mètodes llancen ***IOException*** si es produeix un error. El mètode ***readObject()*** pot llançar també l'excepció ***ClassNotFoundException***.

![](/damm06/assets/1.1/1.1.io/io-trav.gif)

La classe [***ObjectOutputStream***](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/ObjectOutputStream.html) estén ***OutputStream*** i implementa la interfície ***ObjectOutput***. El seu constructor és: ***ObjectOutputStream(OutputStream outStream) throws IOException***
La classe [***ObjectInputStream***](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/ObjectInputStream.html) estén ***InputStream*** i implementa la interfície ***ObjectInput***. El seu constructor és: ***ObjectInputStream(InputStream inStream) throws IOException***.

Consulteu la documentació en línia de Java per a la llista de mètodes que implementen les dues classes anteriors.

[Exemple de seriació d'objectes a fitxer en binari](/damm06/assets/1.1/1.1.io/contactobjectstream.zip)

**Proposta d'exercicis**

 * Programa per escriure i llegir llista d'objectes a fitxer com a objectes. Primera versió: escriure i llegir un objecte cada vegada. Segona versió: escriure i llegir tota la llista d'un a vegada (les llistes també són objectes i són seriables).
 * Programa per enviar i rebre objectes a través de sockets comunicant dos ordinadors (un fent d'emissor i un altre de receptor de les dades).