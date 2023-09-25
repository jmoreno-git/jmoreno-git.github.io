# Interfícies gràfiques d'usuari

Les vistes amb la biblioteca **Swing** de Java responen a esdeveniments d'usuari mitjançant controladors anomenats **listeners**. Hi ha diferents tipus de ***listeners*** segons els esdeveniments a observar en cada cas.

Podem fer que el controlador de l'aplicació s'encarregui de ser listener de tots els esdeveniments de la vista (o vistes). D'aquesta manera, les vistes són passives i tot el control el porta el controlador. Alternativament, podem fer que les vistes tinguin els seus propis ***listeners*** que gestionin els esdeveniments propis de la interacció de l'usuari, mentres que el controlador s'ocupa de la comunicació amb el model.

En definitiva, es tracta de decidir si programem un controlador centralitzat per a tota la comunicació amb el model i les vistes o bé si cada vista disposa del seu propi controlador.

**Versions del conversor de moneda amb un únic panell i diferents graus de segregació de codi**:

A continuació hi ha diferents versions d'un mateix programa que realitza conversió de moneda.

* Currency converter només amb el JFrame, el qual gestiona els càlculs invocant model estàtic. [currencyconverter.zip](/damm06/assets/0.1/gui/currencyconverter.zip)
* Currency converter amb un panell. El panell gestiona els càlculs invocant model estàtic. [currencyconverter2.zip](/damm06/assets/0.1/gui/currencyconverter2.zip)
* Currency converter amb un panell. El panell gestiona els càlculs. El model ja no és estàtic i es crea fora de l'entorn gràfic. [currencyconverter3.zip](/damm06/assets/0.1/gui/currencyconverter3.zip)
* Currency converter amb un panell i separació model-vista-controlador. El controlador gestiona tots els esdeveniments. [currencyconverter4.zip](/damm06/assets/0.1/gui/currencyconverter4.zip)
* Currency converter amb dos panells i separació model-vista-controlador. El controlador gestiona tots els esdeveniments. [currencyconverter_mvc.zip](/damm06/assets/0.1/gui/currencyconverter_mvc.zip)

[GUI i MVC: Comunicació entre components gràfics, controladors i model](traveltimecalculator.md)

[[docencia:dam:m06:ui:guimvc:start|Comunicació entre els components gràfics, els controladors i el model de dades]]

Exemple d'aplicació GUI amb menú formulari i taula de productes. [guiproduct.zip](/damm06/assets/0.1/gui/guiproduct.zip)

[Uso de Combobox](https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html)
