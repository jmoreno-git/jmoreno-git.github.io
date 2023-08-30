# Accés a dades: Preparació de l'entorn de treball

**Programari requerit**

  - Gestor BBDD MariaDb, MySql, PostgreSql, ...
  - IDE: Netbeans, Geany, VSCodium
  - JDK

**Instal·lació de OpenJDK**

Instal·lar OpenJDK darrera versió

    sudo apt-get install default-jdk

Instal·lar OpenJDK 19 (canviar 19 per la versió requerida)

    sudo apt install openjdk-19-jdk

Convé tenir instal·lades la versió 8 i la darrera.

Per gestionar les diferents versions de Java

    sudo update-alternatives -opcions ordres

Per canviar la versió de java per defecte:

    sudo update-alternatives --config java
    sudo update-alternatives --config javac

Per afegir la variable d'entorn JAVA_PATH, editar el fitxer /etc/environment:

    sudo nano /etc/environment

afegint la línia:

    JAVA_HOME="YOUR_PATH" #escriure aquí la ruta al nostre SDK (veure update-alternatives)

Per carregar el fitxer d'entorn:

    source /etc/environment

Comprovació que s'ha carregat bé:

    echo $JAVA_HOME

**Instal·lació de MariaDb**

    sudo apt-get install mariadb-server mariadb-client

Crear un usuari per administració:

    sudo mariadb -u root
    create user admin@localhost identified by 'adminadmin';
    grant all privileges on *.* to admin@localhost with grant option;
    flush privileges;
    exit

**Instal·lació de VSCodium**

Descarregar i instal·lar el paquet des de <https://vscodium.com/>

**Instal·lació de NetBeans**

Descarregar i instal·lar des de <https://netbeans.apache.org/>

**Instal·lació de Geany**

    sudo apt install geany

