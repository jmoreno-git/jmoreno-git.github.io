# UF1-NF2. Fitxers XML

Els analitzadors XML tenen com a objectiu classificar la informació continguda en un fitxer XML per tal de poder tractar-la d'acord amb la seva posició dins la jerarquia d'elements. N'hi ha de dos tipus: seqüencials i jeràrquics.

## Analitzadors seqüencials 

Extreuen el contingut del fitxer XML a mesura que descobreixen noves etiquetes d'obertura i tancament. També s'anomenen analitzadors **sintàctics**.

Son analitzadors ràpids però presenten el problema que cada cop que cal accedir a una part del document cal rellegir-lo de dalt a baix.

**SAX** (*Simple API for XML*) és un analitzador sintàctic.

![](/damm06/assets/1.2/saxparser.png)


## Analitzadors jeràrquics

Emmagatzemen el contingut del document en memòria seguint l'estructura jeràrquica del document. Aquest fet facilita les consultes que cal repetir diverses vegades.
El format en què s'emmagatzema el document XML es coneix com a **DOM** (*Document Object Model*) i ha estat definit per l'organisme internacional *W3C* (*World Wide Web Consortium*).

![](/damm06/assets/1.2/domparser.gif)

* [Simple API for XML (SAX)](sax/home.md)
* [Document Object Model (DOM)](dom/home.md)
* [Transformacions](transform/home.md)

