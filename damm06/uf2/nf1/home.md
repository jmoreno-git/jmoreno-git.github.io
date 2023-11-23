#  UF2-NF1. Accés a bases de dades

[Apunts de connexió a bases de dades](/damm06/assets/2.1/connexio_amb_bases_de_dades.pdf)

Les tasques de connexió a base de dades, realització de consultes i conversió entre format de dades de la base de dades i format d'objectes del model de dades de l'aplicació les realitzen les classes de la capa d'accés a dades [**Data Access Layer (DAL)**](https://en.wikipedia.org/wiki/Data_access_layer).

La capa d'accés a dades proveeix mètodes per a les diferents operacions de persistència amb objectes. [CRUD](https://es.wikipedia.org/wiki/CRUD)

## Model connectat

En aquest model de funcionament, cal mantenir la connexió a la base de dades durant tot el temps en què es realitza la consulta i mentre es recuperen les dades. 

Un cop obtinguda una Connection, aquesta proporciona un [**Statement**](https://docs.oracle.com/en/java/javase/20/docs/api/java.sql/java/sql/Statement.html), el qual permet executar la consulta.

El resultat d'una consulta de recuperació de dades és un objecte [**ResultSet**](https://docs.oracle.com/en/java/javase/20/docs/api/java.sql/java/sql/ResultSet.html)

El *ResultSet* només existeix mentre existeix l'objecte *Statement* que l'ha generat, i aquest només mentre la *Connection* existeix i no s'ha tancat.

* [Accés a dades amb patró DAO](dao.md)
* [Transaccions](transaccions.md)
* [Entitats amb relació 1xn](relacio1xn.md)
* [Entitats amb relació mxn](relaciomxn.md)

## Model desconnectat

Proveeix classes que permeten emmagatzemar les dades obtingudes a la consulta un cop tancada la connexió amb la base de dades. També inclouen informació per realitzar automàticament novament la connexió a la base de dades per realitzar actualitzacions de les dades.

* [RowSet](rowset.md)
