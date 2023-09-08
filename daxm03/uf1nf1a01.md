# Conceptes bàsics de programació estructurada

## Programes i algorismes

Un ordinador és una màquina electrònica dotada d'una memòria de gran capacitat i de mètodes de tractament de la informació, capaç de resoldre problemes matemàtics i lògics mitjançant la utilització automàtica de programes informàtics.

Els components principals d'un ordinador són el processador central, la memòria i els perifèrics. Entre els perifèrics destaquen el teclat, la pantalla i els discs d'emmagatzemament (dics durs).

El **processador central** és el cervell de l'ordinador i realitza operacions aritmètico-lògiques sobre la informació que recupera de la memòria.

Els components físics de l'ordinador són el **maquinari**. Les dades i els programes constitueixen el **programari**.

El sistema operatiu de l'ordinador és la capa de programari que s'encarrega de la gestió i control del maquinari i proporciona eines per gestionar la informació i els programes.

La programació d'ordinadors s'utilitza per resoldre problemes. Generalment, els problemes a resoldre consisteixen en processar informació i obtenir un resultat o prendre accions o decisions.

El procediment per resoldre un problema concret s'anomena [**algorisme**](https://es.wikipedia.org/wiki/Algoritmo)

Un algorisme consisteix en un conjunt ordenat d'operacions que permeten resoldre un problema en un nombre finit de passos i en un temps finit. Pot acceptar un conjunt de **dades d'entrada** i un conjunt de **dades de sortida**.

La codificació d'un algorisme en un ordinador s'anomena **programa**.

**Activitat**
Prova de programar el moviment per arribar a la destinació [Blockly:laberint](https://blockly.games/maze?lang=es)

El processador de la CPU només entén el **codi màquina**. Per facilitar la tasca de programació, existeix el codi **assemblador**, el qual es caracteritza perquè cada *mnemònic* equival a una instrucció de màquina. Els **programes assembladors** tradueixen aquests mnemònics a instruccions de màquina en codi binari i generen l’estructura de programa a partir de les directives d’estructura definides pel programador. Els principals avantatges de la programació en assemblador són la rapidesa d’execució, l’estalvi de memòria, la potència a l’hora de controlar perifèrics, però té com a inconvenients que el codi no és transportable a sistemes amb altres processadors i és difícil d’escriure i depurar. Es tracta d’un **llenguatge de programació de baix nivell**. Per aquests motius, no és un llenguatge adient per dissenyar grans aplicacions. 

Els programadors dissenyen les aplicacions en **llenguatges d’alt nivell**, els quals estan dotats d’estructures complexes per definir el codi i també les dades. Aquests llenguatges són independents de l’arquitectura de l’ordinador i faciliten les tasques de desenvolupament, depuració i manteniment. Com a contrapartida, necessiten un programa que tradueixi les seves instruccions d’alt nivell a instruccions de màquina. Aquests traductors són els **compiladors** i els **intèrprets**.

Els **intèrprets** processen i tradueixen cada instrucció d’alt nivell just abans d’executar-la. El principal inconvenient és la poca velocitat d’execució, atès que cal traduir les instruccions d’alt nivell cada vegada que el programa s’executa.

Els **compiladors** tradueix per complet tot el programa a codi màquina. La compilació i l’execució són processos independents, de manera que l’execució es fa sobre el codi màquina sense cap traducció prèvia, aconseguint una millor rapidesa d’execució.

Les fases del procés de traducció amb un compilador són: **compilació** del **codi font** a **codi objecte** amb la corresponent detecció d’errors i **muntatge** (*link*) del codi objecte (que pot residir en diversos mòduls) amb les **biblioteques del llenguatge** aportades pel programa compilador.

Els algorismes han de ser mètodes generals per a resoldre tots els casos possibles del mateix problema i, per tant, han de ser independent de les dades d’entrada de qualsevol cas concret. Per a comprendre completament aquest concepte d’*algorisme* ens caldrà definir  ara els conceptes d’*entorn*, *acció*, *procés* i *processador*. 

L’**entorn** és el conjunt d’objectes necessaris per a portar a terme una tasca determinada. L’**estat de l’entorn** en un moment determinat és la descripció de l’estat dels objectes de l’entorn en aquell moment concret. Un algorisme actua de manera que fa canviar progressivament l’estat del seu entorn.

Una **acció** és un esdeveniment finit en el temps i que té un efecte definit i previst. Una acció pot actuar sobre un entorn i el pot modificar, és a dir, es parteix d’un estat inicial i s’arriba a un estat final diferent. Una **acció elemental** és  una acció que el destinatari d’un algorisme entén i sap processar. 

Un **procés** és l’execució d’una o diverses accions. L’algorisme expressa unes pautes que cal seguir per a portar a terme una tasca concreta. L’encarregat de dur a terme aquesta tasca és el processador.
Un **processador** és una entitat capaç  de comprendre i executar eficaçment un algorisme. El destinatari de l’algorisme  és, doncs, el processador. 

Un algorisme es defineix com una descripció no ambigua i precisa de  les accions que cal fer per a resoldre un problema ben definit en un  temps finit. 

El Diccionari de la Llengua de la Gran Enciclopèdia Catalana defineix:  *“Un algorisme és un procediment  de càlcul que consisteix a  acomplir un seguit ordenat i finit  d’instruccions que condueix, un  cop especificades les dades, a la  solució que el problema genèric  en qüestió té per a les dades  considerades”*.  

Exemple d’entorn i estat:
En el cas d’una recepta de cuina,  l’entorn vindria donat pels  estris de cuina (olles, paelles,  etc.) i els ingredients.  L’estat de les paelles, per  exemple, canviarà de netes a brutes. 

Un processador pot ser una persona, una rentadora, un ordinador, etc. Ara que ja sabem què és un algorisme ens cal decidir com el podem expressar. Haurem de trobar un llenguatge que ens permeti fer una descripció no ambigua i precisa de les accions que componen els nostres algorismes.

Anomenem **llenguatge natural** el llenguatge que normalment utilitzem per a  comunicar-nos. Ara bé, atesa la seva complexitat i ambigüitat, veurem que el  llenguatge natural no ens permet definir les accions amb la precisió i claredat  que volem. De fet, si nosaltres actuem com a processadors d’algú que ens indica  com s’ha de fer una tasca, sovint demanem puntualitzacions i aclariments  de què cal fer.  Necessitem, doncs, un llenguatge més reduït i precís. Els algorismes es poden representar mitjançant diagrames o usant un llenguatge algorísmic (pseudocodi) que permeti el disseny de l'algorisme amb la major independència del **llenguatge de programació** que s'utilitzi per codificar-lo amb posterioritat.  

Exemples de llenguatges de programació són Pascal, C, C++, Cobol,  Fortran, Java, Python, PHP, etc. 

Bàsicament, hi ha dos tipus de llenguatges de programació: els **llenguatges  imperatius** o **llenguatges procedimentals** i els **llenguatges declaratius**, que a la vegada es divideixen en **llenguatges funcionals** i **llenguatges lògics**. En aquesta assignatura estudiarem només la programació imperativa, que és  la més estesa i utilitzada.  Alguns llenguatges de programació faciliten el seguiment d’una metodologia  concreta de programació. Una de les més àmpliament acceptada actualment és  l’orientació a l’objecte.  **L’orientació a l’objecte** determina un estil de programació que es caracteritza per la manera de manipular la informació, centrant-se sobretot en les dades i la seva estructura més que en el codi i incorporant el codi que ha de manipular les dades dintre de la definició de les mateixes dades. 
En la programació imperativa, els programes són seqüències d’instruccions que s’han de dur a terme com una recepta o guió per a resoldre un problema determinat.  

Exemple d'algorisme:
Pensem el una rentadora de roba com un autòmat. Si demanem a la nostra rentadora que ens renti la roba blanca, la rentadora seguirà aquest procés:
  - Agafar aigua i sabó del calaix.
  - Escalfar l’aigua a 40 graus.
  - Donar voltes durant 20 minuts (rentar).
  - Expulsar l’aigua.
  - Agafar més aigua.
  - Donar voltes durant 10 minuts (primera esbandida).
  - Expulsar l’aigua.  
  - …  
  - Donar voltes durant 10 minuts (quarta esbandida). 
  - Expulsar l’aigua. 
  - Donar voltes molt ràpidament durant 1 minut (centrifugar)

Tot aquest conjunt d’accions que la rentadora ha dut a terme seria l’algorisme que aquest aparell segueix per rentar la roba blanca.  Si ara demanem que ens renti la roba delicada, les accions que executarà la rentadora seran segurament diferents (per exemple, la temperatura serà inferior, el temps de rentat serà més curt, hi haurà menys esbandides, etc.). Això equival a dir que l’algorisme per a rentar la roba delicada és diferent de l’algorisme  per a rentar la roba blanca. 

També que cal que es compleixin unes condicions inicials perquè el problema es resolgui correctament (que la roba blanca estigui dins la rentadora, que la rentadora estigui connectada al corrent elèctric i a l’entrada d’aigua, i també que hi hagi sabó al calaixet). Per tal de poder arribar a l’estat final desitjat,  que en el nostre cas seria tenir la roba neta al final, l’entorn hauria d’estar en aquest estat inicial. Si no partim de l’estat inicial correcte, segurament no aconseguirem  el nostre objectiu (penseu, per exemple, que si la rentadora no està connectada al corrent, el nostre algorisme no donarà el resultat esperat).  

## Cicle de vida d'un programa

El procés que se segueix des del plantejament d'un problema fins a tenir una solució instal·lada en la computadora, i en funcionament pels usuaris finals, es denomina **cicle de vida d'una aplicació informàtica**.

Les fases principals que es poden considerar són:

- Anàlisi de requeriments
- Disseny de l'algorisme
- Implentació del programa: codificació, compilació i muntatge
- Prova i depuració
- Explotació i manteniment

L'anàlisi ha de permetre determinar quina informació ha de processar el programa, què ha de fer amb la informació, quin mecanismes de comunicació té amb els usuaris, etc. Algunes de les tècniques que es poden utilitzar en l’anàlisi són els diagrames de flux de dades, els models de dades (entitat-relació, formes normals, tipus abstractes de dades, ...), els diccionaris de dades i la definició de la comunicació amb l’usuari. 

Durant la fase de disseny s'han de modelar les dades i definir la solució al problema (algorisme) utilitzant diagrames gràfics o [pseudocodi](https://ca.wikipedia.org/wiki/Pseudocodi).

Els diagrames permeten una visualització gràfica de l'estructura del codi (**diagrama de flux**) o de les dades (**diagrama UML**).

Les etapes bàsiques del disseny són la definició dels prerequisits i postrequisits sobre les dades d’entrada i sortida del programa, el disseny de dades, el disseny modular (partició del algorisme en mòduls i definició de la relació i la comunicació entre ells) i l’especificació de cada mòdul.

La fase de codificació consisteix en transcriure l'algorisme utilitzant llenguatges de programació, com per exemple java, C#, php, python, ... El resultat és el **codi font** del programa.

Els llenguatges més propers a les característiques i arquitectura de l'ordinador es diuen **llenguatges de baix nivell**, com ara el *llenguatge màquina* i el *llenguatge assemblador*.

Els llenguatges més propers al programador, amb característiques més complexes s'anomenen **llenguatges d'alt nivell**, com per exemple, Fortran, C, Cobol, Pascal, Java, etc.

Per tal que l'ordinador pugui executar el programa, cal traduir el codi font al *codi màquina*, específic del sistema on s'ha d'executar. Això es realitza a les fases de compilació i muntatge.

Segons el tipus de traducció que es realitza, els llenguatges es classifiquen en interpretats i compilats.

Als llenguatges **interpretats**, cada instrucció es tradueix just abans de la seva execució. 

Als llenguatges **compilats**, en primer lloc un programa anomenat compilador fa la traducció completa del codi font. El resultat és el **codi objecte**, el qual encara s'ha d'enllaçar amb les biblioteques del llenguatge (**link**), obtenint al final el **codi executable**. Els llenguatges compilats proporcionen, en general, més velocitat d'execució.

Un tipus especial de llenguatges compilats són els que tenen un codi objecte intermig entre el codi font i el codi màquina. Aquests llenguatges necessiten una capa virtual que interpreti el codi intermig per executar-lo. Tot i que la velocitat no serà la mateixa que en el cas de codi objecte natiu, tenen com a avantatge que són multiplataforma, és a dir, que es poden executar un cop compilats en qualsevol sistema, ja que és aquesta capa virtual l'unica que cal instal·lar de forma específica a cada sistema. És el cas del llenguatge **java**.

A la fase de prova i depuració es verifica que el programa funciona d'acord amb les especificacions requerides i es corregeixen els errors que s'hi trobin.

A les fases d'explotació i manteniment, el programa és utilitzat pel usuaris finals i els programadors realitzen canvis, correccions i modificacions d'acord amb els requeriments dels usuaris.

### Errors d'un programa

Els errors són funcionaments anòmals o manca de funcionament absoluta del programa en determinades circumstàncies. Cal fer un procés de prova intensiu amb dades d'entrada prou diverses per detectar possibles errors i poder-los corregir.

![](/images/mi_codigo_funciona_porque.jpg)

Els errors es clasifiquen en:

- de compilació
- d'execució
- de lògica
- d'especificació

Els errors de compilació corresponen a incompliment de les regles de sintaxi del llenguatge i són generats pel compilador o intèrpret.

Els errors d'execució es produeixen quan alguna operació no es pot realitzar sobre les dades subministrades. Són més difícils de detectar, ja que es produeixen en temps d'execució i només en circumstàncies molt concretes, depenent de les dades d'entrada.

Els errors de lògica produeixen resultats no correctes. Per detectar-los cal usar un joc de dades d'entrada prou extens en realitzar les proves.

Els errors d'especificació es produeixen per deficiències de comunicació entre client i desenvolupador. El resultat és que el programa no respon a les especificacions demanades pel client i acostuma a obligar a refer gran part del programa.

En l’etapa de proves es tracta de provar el programa resultant amb diferents dades d’entrada que reben el nom de **jocs de proves**. L’èxit d’aquestes  proves dependrà en gran mesura de la qualitat del disseny fet abans.

Els jocs de proves només serveixen per a comprovar que el programa no és correcte (si algun joc de proves no dóna els resultat  esperat), però no per a assegurar que sí que ho és (llevat que es pràcticament impossible comprovar totes les entrades possibles).

Els algorismes i els programes resultants han de complir les següents característiques:

  a) Correctesa: l’algorisme fa allò que realment es demana.  
  b) Intel·ligibilitat: l’algorisme ha de ser clar i fàcil d’entendre, atès que s’escriurà  un sol cop, però caldrà llegir-lo molts més per a poder-lo mantenir i/o  modificar.
  c) Eficiència: l’algorisme ha de portar a terme la tasca que se li ha encomanat  en un temps raonable.  
  d) Generalitat: amb pocs canvis, l’algorisme s’ha de poder adaptar a altres enunciats semblants.  Quan dissenyeu els vostres algorismes tingueu en compte sempre aquests quatre  criteris. La metodologia que proposarem en aquesta assignatura us ajudarà  a dissenyar algorismes que compleixin aquests criteris. Però penseu també que  no només és necessari seguir la metodologia, sinó que per a assolir una certa destresa en el disseny cal força pràctica, ja que tant els conceptes com la metodologia  necessiten un temps d’assimilació, i això només s’aconsegueix amb la pràctica contínua.

## Elements d'un programa

En el codi d'un programa informàtic podem trobar diversos elements. El programa es divideix en **instruccions o sentències**, cada una de les quals realitza una acció concreta i definida. Segons el tipus de sentència, poden contenir diferents elements:

### Variables i constants

Serveixen per emmagatzemar **dades** dintre del programa.

Han de tenir un tipus (defineix el tipus de dada contindrà), un valor (la dada concreta emmagatzemada) i un identificador (el nom amb què ens referirem a la variable o constant dintre del programa).

En el cas de les constants, no es permet canviar el valor durant l'execució del programa.

### Operadors

Són connectors de dades que simbolitzen accions sobre les dades.

En funció del tipus de dades que connecten i de l'operació que representen poden ser numèrics, alfanumèrics, lògics, etc.

### Expressions

Són combinacions de dades i operadors que proporcionen un resultat.

A les expressions, els operadors s'avaluen aplicant els criteris de prioritat establerts pel llenguatge. Si tenen igual grup de prioritat, s'avaluen d'esquerra a dreta.

## Representació de programes

Una manera convenient de reprentar gràficament un algorisme o procés és el [diagrama de flux o ordinograma](https://es.wikipedia.org/wiki/Diagrama_de_flujo).

Un altre sistema per representar un algorisme és el [pseudocodi](https://ca.wikipedia.org/wiki/Pseudocodi). Consisteix en un llenguatge intermedi entre el llenguatge natural i el llenguatge de programació i permet definir l'estructura del codi i el processat de les dades sense entrar en els detalls del llenguatge de programació a utilitzar.

## Característiques del llenguatge Java

El [llenguatge Java](https://es.wikipedia.org/wiki/Java_(lenguaje_de_programaci%C3%B3n)) va ser creat l'any 1995 per l'empresa Sun Microsystems, la qual va ser comprada l'any 2009 per Oracle.

És un llenguatge compilat, d'alt nivell i amb sintaxi estricta. El compilador genera un codi intermig (bytecodes) que s'executa posteriorment sobre una entorn d'execució (màquina virtual). Aixó permet que sigui multiplataforma, ja que el codi compilat és el mateix per a totes les plataformes. Només cal tenir instal·lat l'entorn virtual (JRE: Java Runtime Environment) corresponent a la plataforma concreta.

Java és un llenguatge orientat a objectes. Això implica que les funcions (anomenades mètodes) del llenguatge es troben encapsulades dintre d'objectes contenidors. El paradigma de l'orientació a objectes facilita molt el disseny i la construcció d'aplicacions grans.

Es pot escriure un programa en Java amb qualsevol editor de text, tot i que un IDE (Integrated Development Environment) és convenient per disposar d'eines de compleció i evitar errors d'escriptura. Els fitxers de codi font porten l'extensió .java. el compilador genera fitxers en format bytecode amb extensió .class.

Tot programa en Java es compon d'un conjunt de classes, cadascuna de les quals acostuma a estar en un fitxer amb el mateix nom que la classe. La classe que es carrega primer quan s'executa el programa ha de contenir un mètode main().

Heus aquí un exemple d'aplicació petita en Java:

``` java
/**
 * Hola món
 * @author ProvenSoft
 */
public class HolaMon {
    public static void main(String[] args) {
        //imprimir missatge
        System.out.println("Hola món!");
    } 
}
```
El fitxer s'ha de dir *HolaMon.java*.

Els comentaris poden ser multilínia (/*  */) o unilínia (//).

La classe *HolaMon* conté un únic mètode *main*. Les claus {} encerclen blocs de codi, en aquest cas, el corresponent al mètode *main*. Conté una única instrucció, la qual usa el mètode **println** per mostrar una cadena de text ("Hola món!"). El mètode println pertany a un objecte (*out*), el qual está contingut a la classe *System*.

Per als noms s'utilitza la notació **CamelCase**:

  - Noms de classes: inicial en majúscules i majúscula la inicial de cada nova paraula.
  - Noms de mètodes i variables: inicial en minúscules i majúscula la inicial de cada nova paraula.
  - Noms de constants: tots els caràcters en majúscula i paraules separades per guions baixos.

[Introducció a Java (ceedcv)](assets/1.1/ud4_Introduccion_a_Java.pdf)

![](/images/definicion_programador.jpg)

## Paradigmes de programació

### Programació modular

El programa es divideix en mòduls, cadascun dels quals executa una única activitat i es codifiquen independentment. Cada programa conté un mòdul principal que transfereix el control a altres mòduls (d'ara endavant subprogrames) que tornen el control al mòdul principal en acabar la seva tasca. Cada subprograma pot transferir el control a altres subprogrames, però cada un ha de tornar el control al mòdul que el va invocar. És aconsellable dimensionar els mòduls de manera que no ocupin més de 30 o 40 línies d'instruccions.

Podem definir un mòdul com una o diverses instruccions físicament contigües i lògicament encadenades, tals que es poden referenciar mitjançant un nom i ser anomenades des de diferents punts d'un programa, així com el conjunt de dades privades d'aquest mòdul requerides per les instruccions. Els mòduls han de tenir la màxima cohesió i el mínim acoblament. La sortida del mòdul ha de ser funció només de la vostra entrada.

### Programació estructurada

Per augmentar l'eficiència de la programació i del manteniment, cal dotar els programes d'una estructura. Això, a més, assegura que els programes siguin adaptables, manejables, fàcilment comprensibles i transportables.

Al maig de 1966, Böhm i Jacopini van demostrar el teorema de la programació estructurada: qualsevol programa propi pot ser escrit utilitzant només tres tipus d'estructures de control: seqüencials, condicionals i iteratives. Un programa es defineix com a propi si té només un punt d'entrada i un de sortida o fi, si hi ha camins des de l'entrada fins a la sortida que passen per totes les parts del programa i totes les instruccions són executables i no hi ha bucles sense fi.

Així, doncs, en el disseny dels nostres programes ens limitarem a aquestes estructures de control.

**Pàgina on practicar els conceptes bàsics de l'algorísmica**: [Code.org](http://www.code.org)

![](/images/cuando_reviso_codigo_6_meses-despues.png)

[Apunts de Java (Álex García)](assets/1.1/apuntes_java-alex_garcia.pdf) Introducció molt completa i amb exemples.
