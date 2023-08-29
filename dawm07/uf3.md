# DAWBI. M07-UF3 Tècniques d'accés a dades

Durada DAW: 42 hores

Durada DAWBI: 25 hores

## Materials diversos

* [PHP database extensions](https://www.php.net/manual/en/refs.database.php)
* PDO tutorial (phpdelusions.net) [ODT](assets/3.1/pdo_tutorial-php_delusions.odt) [PDF](assets/3.1/pdo_tutorial-php_delusions.pdf)
* [PDO manual](http://php.net/manual/es/book.pdo.php)

* [Connexió](http://php.net/manual/es/pdo.connections.php)
* [Connexió persistent](http://php.net/manual/es/features.persistent-connections.php)
* [Sentències preparades](http://php.net/manual/es/pdo.prepared-statements.php)
* [Transaccions](http://php.net/manual/es/pdo.transactions.php)
* [mclibre.org](http://www.mclibre.org/consultar/php/): veure secció de bases de dades amb php
* [Extensions de bases de dades per a php](http://php.net/manual/es/refs.database.vendors.php)
* [Conceptes sobre seguretat amb bases de dades i php](https://secure.php.net/manual/es/security.database.php)

## Instal·lació d'extensions de base de dades

Extensions de BD a PHP per MySQL (php.net - extensions BD):

 * MySQLi - extensió propietària de MySQL (És una versió millorada de l'anterior MySQL). Necessiteu el MySQLi module per PHP
* PDO - interfície abstracta de PHP per accés a BD. Genèric pels diferents SGBD que tinguin controlador PDO. Necessiteu el controlador de MySQL per PDO

Per comprovar si teniu les extensions de php: mysqli i pdo_mysql
```
php -m | grep mysql  
```
A Ubuntu el paquet php-mysql instal·la les extensiones de PHP ext/mysql, ext/mysqli, i pdo_mysql.

Per veure la versió de php instal·lada:

    php -v

Per instal·lar el paquet php-mysql per una versió específica de php, per exemple per la versió 7.2:

    sudo apt install php7.2-mysql

Si no indiqueu versió, s'instal·la per l'última versió de php configurada als repositoris d'ubuntu:

    sudo apt install php-mysql    

## Conceptes bàsics de bases de dades

**Creació de la base de dades, l'usuari per a accedir amb l'aplicació i atorgament dels permisos.**

```sql
-- Crear usuari per a accés local.
CREATE USER 'provenusr'@'localhost' IDENTIFIED BY 'provenpass';
-- Crear base de dades.
CREATE DATABASE proven
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
USE proven;
-- Assignar permisos a l'usuari local sobre la base de dades.
GRANT SELECT, INSERT, UPDATE, DELETE ON proven.* TO 'provenusr'@'localhost';
```

**Creació de la taula**

```sql
-- Crear la taula
CREATE TABLE items (
   id INTEGER PRIMARY KEY AUTO_INCREMENT,
   item VARCHAR(255)
) ENGINE=InnoDb;
```

**Afegir dades a la taula**

```sql
INSERT INTO items (item) VALUES 
  ('item 1'),
  ('item 2'),
  ('item 3'),
  ('item 4'),
  ('item 5');
```

## Connexió a la base de dades

 Es fa instanciant un objecte de la classe [PDO](https://www.php.net/manual/es/class.pdo.php).

Més informació en el Constructor a [PDO Constructor](https://www.php.net/manual/es/pdo.construct.php).

Provoca una [PDOException](https://www.php.net/manual/en/class.pdoexception.php) si falla la connexió amb la base de dades. 

```php
//parameters to connect to database.
$dsn = 'mysql:host=localhost;dbname=mydb;charset=utf8';  //data source name.
$usr = 'usr';   //user.
$psw = 'psw';   //password.
try {
     //connect to database
     $connection= new PDO($dsn, $usr, $psw);
} catch(PDOException $ex) {
    //connect error
    echo "Connection failed: " . $ex->getMessage();
}
```

La biblioteca PDO pot treballar amb diversos modes de tractament dels errors:

* PDO::ERRMODE_SILENT
* PDO::ERRMODE_WARNING
* PDO::ERRMODE_EXCEPTION

En els dos primers casos, es pot obtenir informació de l'error amb els mètodes [errorCode()](https://www.php.net/manual/es/pdo.errorcode.php) i [errorInfo()](https://www.php.net/manual/es/pdo.errorinfo.php). En el tercer cas, la informació s'obté de l'excepció. 

Construcció amb especificació del mètode de tractament d'error: 

```php
try {
     //connect to database
     $connection = new PDO($dsn, $usr, $psw);   
     $connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION)
} catch(PDOException $ex) {
    //connect error
    echo "Connection failed: " . $ex->getMessage();
}
```

Si la connexió falla, el constructor sempre llança l'excepció PDOException, amb independència del mode d'error establert. 

La classe PDO ens proporciona diversos mètodes per realitzar consultes. 

```php
class PDO {
  public __construct ( string $dsn , string $username = ? , string $password = ? , array $options = ? )
  public beginTransaction ( ) : bool
  public commit ( ) : bool
  public errorCode ( ) : mixed
  public errorInfo ( ) : array
  public exec ( string $statement ) : int
  public getAttribute ( int $attribute ) : mixed
  public static getAvailableDrivers ( ) : array
  public inTransaction ( ) : bool
  public lastInsertId ( string $name = null ) : string
  public prepare ( string $statement , array $driver_options = array() ) : PDOStatement
  public query ( string $statement ) : PDOStatement
  public quote ( string $string , int $parameter_type = PDO::PARAM_STR ) : string
  public rollBack ( ) : bool
  public setAttribute ( int $attribute , mixed $value ) : bool
}
```

Si es volen processar **transaccions**, es pot modificar el valor per defecte de la propietat '*autocommit*'. 

```php
  $db = new PDO('mysql:dbname=employee');
  $db->setAttribute(PDO::ATTR_AUTOCOMMIT,0);
 
  try {                             
      $db->beginTransaction();
      /* les meves consultes van aquí */
      $db->commit();
  } catch (Exception $e) {
      $db->rollBack();
  }
```

## Execució de sentència SQL amb el mètode exec

El mètode [exec](https://www.php.net/manual/es/pdo.exec.php) de la classe PDO executa una consulta de modificació de dades i retorna en un *int* el nombre de files afectades.

```php
//executing a modifying query
$affectedRows = $connection->exec('INSERT INTO users VALUES (1, "somevalue"');
```

## Execució SELECT amb mètode query

El mètode [query](https://www.php.net/manual/es/pdo.query.php) de la classe PDO executa una consulta de dades i retorna el resultat en un objecte **PDOStatement**. En cas d'error, retorna **false**.

[PDOStatement](https://www.php.net/manual/en/class.pdostatement.php) implementa la interfaz [Traversable](https://www.php.net/manual/en/class.traversable.php), la qual permet fer use bucles  *foreach*.

```php
//fetching queries
$result = $connection->query('SELECT * FROM users');
foreach($result as $row) {
  echo $row['id'] . ' ' . $row['name'];
}
```

## Recorrent les dades de PDOStatement amb fetch

El mètode [fetch](https://www.php.net/manual/es/pdo.query.php) obté la següent fila d'un conjunt de resultats.

```php
public PDOStatement::fetch ( 
   int $fetch_style = ? , 
   int $cursor_orientation = PDO::FETCH_ORI_NEXT , 
   int $cursor_offset = 0 ) : mixed
```

El paràmetre ***fetch_style*** determina com es retorna el resultat:

* PDO::FETCH_ASSOC: array indexat amb els noms de les columnes.
* PDO::FETCH_NUM: array indexat amb els números de les columnes.
* PDO::FETCH_CLASS: objecte del tipus especificat.

Per a tots els valors del paràmetre *fetch_style*, retorna *false* en cas d'error.

**Execució SELECT amb mètode query i accés a les dades del PDOStatement obtingut en forma d'array associatiu.**

```php
//fetching with associative array
$statement = $connection->query('SELECT * FROM users');
while($row = $statement->fetch(PDO::FETCH_ASSOC)) {
  echo $row['id'] . ' ' . $row['name'];
}
```

**Execució SELECT amb mètode query i accés a les dades del PDOStatement obtingut en forma d'objecte.**

```php
//fetching objects of class User (defined elsewhere)
$statement = $connection->query('SELECT * FROM users');
while($row = $statement->fetch(PDO::FETCH_CLASS, 'User')) {
  echo $row->getId() . ' ' . $row->getName();
}
```

## Consultes preparades

El mètode [prepare](https://www.php.net/manual/es/pdo.prepare.php) prepara una sentència (molt útil per a sentències amb paràmetres) per a la seva execució i retorna la sentència (*PDOStatement*). En cas d'error, retorna *false* o emet una excepció *PDOException* (depenent de la gestió dels errors).

Per executar la consulta diposa del mètode [execute()](https://www.php.net/manual/es/pdostatement.execute.php).

   public PDOStatement::execute ( array $input_parameters = ? ) : bool

Si la sentència té marcadors de paràmetres (consulta parametritzada), cal vincular els paràmetres amb els seus valors. Això es pot fer de dues maneres:

* Invocant [bindParam()](https://www.php.net/manual/es/pdostatement.bindparam.php) o [bindValue()](https://www.php.net/manual/es/pdostatement.bindvalue.php).
* Passant-li un array amb els valors dels paràmetres.

```php
//querying with prepared statements
$statement = $connection->prepare('select * from users where id = :id');
$id = 5;
$statement->execute([':id' => $id]);
$results = $statement->fetchAll(PDO::FETCH_OBJ);
//prepared statements and parameter binding
$statement = $connection->prepare('SELECT * FROM users WHERE name = ?');
$name = 'peter';
$results = $statement->execute([$name]);
//queries with just one result (fetchColumn(int) retorna la columna indicada)
$numberOfUsers = $connection->query('SELECT COUNT(*) FROM users')->fetchColumn();
//specifying data types in binding
$users = [];
$statement = $connection->prepare('SELECT * FROM users WHERE id = ? LIMIT 1');
for ($i = 1; $i <= 5; $i++) {
    $id = rand(1, $numberOfUsers);
    $statement->bindValue(1, $id, PDO::PARAM_INT);
    $statement->execute();
    $users[] = $statement->fetch(PDO::FETCH_OBJ);
}
```

Exemple de consultes senzilles: 

```php
<?php
//Test of database access.
//parameters to connect to database.
$dsn = 'mysql:host=localhost;dbname=proven;charset=utf8';  //data source name.
$usr = 'provenusr';   //user.
$psw = 'provenpass';   //password.
try {
     //connect to database.
     $connection= new PDO($dsn, $usr, $psw);
     //select with query().
     $statement = $connection->query('SELECT * FROM items');
     while($row = $statement->fetch(PDO::FETCH_NUM)) {
        echo "[" . $row[0] . ' ' . $row[1]. "]";
     }
     //select where with prepared statement.
     $statement = $connection->prepare("SELECT * FROM items WHERE id=:id");
     echo "searching item with id =3";
     //$statement->execute([":id" => 3]);
     $statement->bindValue(":id", 3, PDO::PARAM_INT);
     $statement->execute();
     $row = $statement->fetch(PDO::FETCH_NUM);
     echo "[" . $row[0] . ' ' . $row[1]. "]";
     //insert with prepared statement.
    //  $statement = $connection->prepare("INSERT INTO items (item) VALUES (?)");
    //  $success = $statement->execute(['item 8']);
    //  if ($success) echo "Success";
    //  else echo "Fail";
 
     //update with prepared statement.
     $statement = $connection->prepare("UPDATE items SET item=:item WHERE id=:id");
     $success = $statement->execute([":id"=>3 ,":item"=>"changed 3"]);
    //  $statement = $connection->prepare("UPDATE items SET item=? WHERE id=?");
    //  $statement->execute(["changed 7", 7]);
     if ($success) echo "Success";
     else echo "Fail";    
     echo $statement->rowCount();
 
 
    //  while($row = $statement->fetch(PDO::FETCH_ASSOC)) {
    //     echo "[" . $row['id'] . ' ' . $row['item'] . "]";
    //  }
    //  foreach($statement as $row) {
    //    echo "[" . $row['id'] . ' ' . $row['item'] . "]";
    //  }
    //  $affectedRows = $connection->exec("INSERT INTO items (item) VALUES ('item 6')");
    //  echo $affectedRows . "rows inserted";
} catch(PDOException $ex) {
    //connect error
    echo "Connection failed: " . $ex->getMessage();
}
```

## Exemples

[Exemple de persistència d'usuaris](uf3a01.md).