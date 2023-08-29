# Introducció a PHP

[php](https://en.wikipedia.org/wiki/php) (acrònim de PHP: Hypertext Preprocessor) és un llenguatge de codi obert molt popular especialment
adecuat per al desenvolupament web i que pot ser incrustat en HTML.

[Manual oficial de PHP](http://php.net/manual/es/index.php)

[Tutorial PHP (w3shools.com)](https://www.w3schools.com/php/)

[Presentacions PHP (PUE)](uf1_php_presentacions.md)

El codi php es pot incrustrar enmig del codi html tancant-lo entre les etiquetes 
``` php
<?php 
[aqui el programa PHP\]
 ?\>
 ```

El fitxer que el conté ha de tenir extensió php. Quan un fitxer només conté codi php, és una bona pràctica no posar l'etiqueta de tancament.

L'ordre de PHP per imprimir és **echo**, o també **print**. A continuació podeu veure un programa senzill en php:

``` php
<!DOCTYPE html>
<html lang="es">
<head>
<title>Hello World</title>
</head>
<body>
<?php
  echo "Hello World";
?>
</body>
</html>
```

La pàgina que es generará será:

``` html
<!DOCTYPE html>
<html lang="es">
<title>Hello World</title>
</head>
<body>
Hello World
</body>
</html>
```

Les sentències php han d'acabar amb el terminador **;**

Desem aquest fitxer a la carpeta arrel del servidor local web perquè sigui accessible amb un navegador a la url *localhost/helloworld.php*.

La informació de configuració del PHP es pot obtenir amb el següent codi:

``` php
<?php 
phpinfo(); 
?>
```

## Visualització d'errors en fase de desenvolupament

Cal editar el fitxer de configuració de PHP, php.ini (a /etc/php/{php\_version}/apache2/, activant les següents directives amb els valors que s'indiquen a continuació:

``` 
 display_errors = On
 error_reporting = E_ALL
```

i reiniciar el servidor apache perquè reconegui els canvis.

## Variables, tipus de dades i operadors

Comentaris d'una línia: //.

Comentaris multilínia: /\* ... \*/.

La definició de variables es fa antecedent el nom amb el caràcter **$**.
Les normes que regeixen per als noms són:

  - Ha de començar pel caràcter $
  - El segon caràcter ha de ser una lletra o un guió baix, però no un nombre o un signe de puntuació o accentuació.
  - A partir del 3r caràcter es poden usar números.
  - Els noms de les variables no tenen límit de caràcters.
  - Es diferencien les minúscules de les majúscules (*case sensitive*)

Els tipus de dades en php són:

  - int
  - float, double
  - string
  - bool
  - null
  - Arrays
  - Objectes: encapsulen dades (propietats) y mètodes (comportament)

En PHP no és necessari definir el tipus abans d'utilizar una variable. Les variables es creen en el moment d'usar-les. Les variables es
declaren quan se les assigna un valor, per exemple:

    $day = 24; //variable integer.
    $salary= 758.43; //variable double.
    $name= "Lewis"; //variable string.
    $found = true; //variable bool.

A partir de php7 ja es poden declarar els tipus dels paràmetres de les funcions.

Per obtenir una representació legible del tipus, es fa servir la funció *gettype()*.

Per comprovar un cert tipus, es fa servir les funcions *[is\_tipus](http://es1.php.net/manual/es/function.is-int.php)*

El canvi de tipus (*type cast*) en PHP funciona de la mateixa manera que en C: on el nom del tipus desitjat s'escriu entre parèntesi abans de la
variable que es vulgui forçar.

**Exemple**

Programar un script que mostri si el lloc web està en servei o fora de servei. El lloc nomès està actiu els 20 primers dies del mes.

La data del servidor es pot obtenir amb:

    $currentDay=date("d");  // just the day.
    $currentDate=date("Y:m:d")  // full date.

``` php
<!DOCTYPE html>
<html lang="es">
<head></head>
<body> 
  <?php 
    $currentDay = date("d"); 
    if ($currentDay <= 20) { echo "Active"; } 
    else { echo "Out of service"; } 
  ?>
</body>
</html>
```

La condició del *if* ha d'anar obligatòriament entre parèntesis. Els operadors relacionals són:

``` php
>     Major
>=    Major o igual
<     Menor
<=    Menor o igual
==    Igual
!=    Diferent
```

Les variables de tipus **string** emmagatzemen una sèrie de caràcters tancats entre cometes dobles o simples.

``` php
$word1="Hola";
$word2="Mundo";
echo $word1." ".$word2;
```

Per concatenar string utilitzem l'operador .

Quan un cadena tancada entre cometes dobles conté una variable al seu interior, la variable se substitueix pel seu contingut.

``` php
$day=10;
$data="Today is $day";
echo $data;
```

A la pantalla es mostra: Today is 10

En canvi, si la cadena està tancada amb cometes simples, les variables del seu interior no se substitueixen pel seu valor.

Podem incloure " o ' dintre d'un string:

    $a = "Welcome 'guys'";
    $b = 'Welcome "guys"';
    $c = "Welcome \"guys\"";

##### Caràcters especials

| Caràcter | Significat                                                   |
| -------- | ------------------------------------------------------------ |
| \\\\     | contrabarra                                                  |
| \\$      | dòlar                                                        |
| \\"      | cometa doble (en una cadena delimitada per cometes dobles)   |
| \\'      | cometa simple (en una cadena delimitada per cometes simples) |
| \\n      | Salt de línea                                                |
| \\t      | Tabulador horitzontal                                        |
| \\XXX    | Caràcter amb codiXXX en octal                                |
| \\xXX    | Caràcter amb codiXX en hexadecimal                           |
| \\r      | Retorn de carro                                              |
| \\v      | Tabulador vertical                                           |
| \\e      | Escape                                                       |
| \\f      | Salt de pàgina                                               |

### Constants

El nom d'una constant segueix les mateixes regles que qualsevol altra etiqueta de PHP. Un nom de constant vàlid comença per una lletra o subguió, seguit per qualsevol número o lletra, números o subguions.

Es pot definir una constant utilitzant la funció *define()*

``` php
<?php
define("PI", 3.14);
print "<p>The value of pi is " . PI . "</p>";
?>
```

Aquestes són les diferències entre constants i variables:

  - Les constants no porten el prefix dòlar ($)
  - Les constants solament poden ser definides usant la funció define(), i no per simple assignació.
  - Les constants poden ser definides i accedides des de qualsevol lloc sense importar les regles d'accés de variables.
  - Les constants no poden ser redefinides o eliminades una vegada s'han definit.

### Operadors

[Operadors](http://www.php.net/manual/es/language.operators.php)

### Arrays

Un array en PHP és realment un mapa ordenat. Un mapa és un tipus de dades que associa *claus* amb *valors*. Les claus poden ser numèriques (**arrays indexats**) o alfanumèriques (**arrays associatius**).

Un array pot ser creat mitjançant la funció **array()**.

A continuació teniu un exemple d'**array** **indexat** i d'un altre **associatiu**.

``` php
$indexed_array = array(value1, value2, value3,...);
$associative_array = array(
    key1 => value1,
    key2 => value2,
    key3 => value3,
    ...
);
```

A partir de PHP 5.4 també es pot utilitzar la *sintaxi d'array curta*:

``` php
$array = array(
    "Lisboa" => "Portugal",
    "Londres" => "Regne Unit"
);
// a partir de PHP 5.4
$array = [
    "Lisboa" => "Portugal",
    "Londres" => "Regne Unit"
];
```

L'accés als elements es fa amb la sintaxi:

    $element = myAssociativeArray[key];
    $element = myIndexedArray[index];

[Funcions per treballar amb arrays](http://www.php.net/manual/es/ref.array.php)

### Cadenes (Strings)

Un string és una sèrie de caràcters. Un string pot arribar a tenir una mida de 2GB.

Cal tenir cura del sistema de codificació de caràcters. Algunes funcions de gestió de strings en php permeten forçar el tractament amb multibyte unicode per evitar problemes.

Es pot definir una constant string de 4 formes diferents:

``` php
//cometes simples
$a = 'Hola';
//cometes dobles
$b = "Hola";
//heredoc: equival a string tancat amb cometes dobles.
$c = <<< EOT
<p>Hola</p>
<p>Adéu</p>
EOT;
//nowdoc: equival a string tancat amb cometes simples.
$c = <<< 'EOT'
<p>Hola</p>
<p>Adéu</p>
EOT;
```

[Strings (manual oficial php)](http://www.php.net/manual/es/language.types.string.php)

L'operador de concatenació de strings és (.)

``` php
$msg = 'Hola ' . 'John';  // la variable conté el valor 'Hola John'
```

[Funcions de manipulació de string](http://www.php.net/manual/es/ref.strings.php)

[Funcions de verificació del contingut d'un string](http://www.php.net/manual/es/ref.ctype.php)

[Funcions de caràcters multibyte](https://www.php.net/manual/es/book.mbstring.php)

Exemple:

``` php
<?php
// ------------------------------------------------------------
header('Content-Type: text/html; charset=UTF-8');
mb_internal_encoding('UTF-8');
mb_http_output('UTF-8');
mb_http_input('UTF-8');
mb_regex_encoding('UTF-8');
// ------------------------------------------------------------
?>
```

**Variable superglobal $\_SERVER**

[$\_SERVER](https://www.php.net/manual/es/reserved.variables.server.php) és un array que conté informació de *headers*, *paths* i ubicació de
*scripts*. Les seves entrades són creades pel servidor web.

``` php
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title></title>
</head>
<body>

<?php
echo "<pre>";
print_r ( $_SERVER );
var_dump ( $_SERVER );
echo "</pre>";
?>

</body>
</html>
```

### Conversión de tipos (typecast)

``` php
$fa = 3.2;  // coma flotant
$ia = (int) $fa;  //conversió a enter (3)
```

## Estructures de control del flux

[PHP manual: Estructures de control](https://www.php.net/manual/es/language.control-structures.php)

### Estructura condicional

Les estructures condicionals permeten definir diferents camíns d'execució del segons el compliment d'una condició. Utilizen les instruccions: **if**, **else** y **elseif**. L'estructura base d'aquest tipus de sentències és:

    if (condition) {
      sentence 1;
      sentence 2;
    } else {
      sentence A;
      sentence B;
    }

Les estructures condicionals es poden enniuar:

    if (condition 1) {
      sentence 1;
      sentence 2;
    } else {
      if (condition 2) {               
        sentence A;
        sentence B;
      } else {
        sentence X;
        sentence Z;
      }
    }

Una altra variant és la sentència **elseif**:

    if (condition 1) {
      sentence 1;
      sentence 2;
    }
    elseif (condition 2){
      sentence A;
      sentence B;
    } else {
      sentence X;
      sentence Z; 
    }

Operadors lògics:

    ===   verifica la igualtat de valors i tipus.
    ==    verifica la igualtat de valors.
    !=    diferent.
    >=    major o igual.
    >     major.
    <=    menor o igual
    <     menor

Convé utilitzar l'operador === per verificar la igualtat.

**Exemple 1 d'ús d'estructures condicionals**:

Generar un nombre aleatori i mostrar un missatge diferent segons sigui major que 5 o no.

``` php
<!DOCTYPE html>
<html lang="es">
<head>
<title>Compare to 5</title>
</head>
<body>
<?php
$value=rand(1,10);
echo "<p>Value = $value</p>";
if ($value<=5) {
  echo "<p>The value is lower than or equal to 5</p>";
}
else {
  echo "<p>The value is greater than 5</p>";
} 
?>
</body>
</html>
```

La condició del *if* ha d'anar sempre entre parèntesis.

Per generar el valor aleatori utilitzem la funció **rand()**, la qual rep com a arguments els valors mínim i màxim del rang en el qual volem generar els nombres aleatoris.

**Exemple 2 d'ús d'estructures condicionals**:

Generar un valor aleatori entre 1 i 100 i mostrar el nombre de dígits que té.

En aquest cas, el flux del codi ha de contemplar tres possibilitats.

``` php
<!DOCTYPE html>
<html lang="es">
<head>
<title>Number of digits</title>
</head>
<body>
<?php
$value=rand(1,100);
echo "<p>Value = $value</p>";
if ($value<=9) {
  echo "<p>The value has 1 digit</p>";
}
else {
  if ($value<100)   {
    echo "<p>The value has 2 digits</p>";
  }
  else   {
    echo "<p>The value has 3 digits</p>";
  }
}
?>
</body>
</html>
```

### Condicional múltiple

**switch**

``` php
switch ($i) {
    case 0:
        echo "i equals 0";
        break;
    case 1:
        echo "i equals 1";
        break;
    case 2:
        echo "i equals 2";
        break;
    default:
        echo "unknown value of i";
        break;
}
```

**match**

Variant de *switch* que pot retornar un valor (el qual es pot usar o no) i no requereix les claùsules break.

``` php
$result = match ($value) {
    1, 2 => foo(),
    3, 4 => bar(),
    default => baz(),
};
```

Un altre exemple de *match*:

``` php
$age = 23;
$result = match (true) {
    $age >= 65 => 'senior',
    $age >= 25 => 'adult',
    $age >= 18 => 'young adult',
    default => 'kid',
};
```

### Estructures repetitives (for - while - do/while)

#### Bucles comptats (estructura for)

    for([variable initialization];[condition];[variable update]) {
      [instructions];
    }

Exemple: mostrar els nombres 1 al 100.

``` php
<html>
<head>
<title>1 to 100</title>
</head>
<body>
<?php
for($f=1;$f<=100;$f++)
{
  echo " ".$f;
}
?>
</body>
</html>
```

#### Bucles condicionals provats a l'inici

    while (condition) {
      [instructions];
    }

Exemple: Generar un nombre aleatori entre 1 i 100 i mostrar els nombre des d'1 fins al nombre generat (d'un en un).

``` php
<!DOCTYPE html>
<html lang="es">
<head>
<title>while loop exemple</title>
</head>
<body>
<?php
$value=rand(1,100);
$current=1;
while($current<=$value)
{
  echo " ".$current;
  $current++;
}
?>
</body>
</html>
```

#### Bucles condicionals provats al final

``` php
do {
  [instructions];
} while (condition);
```

#### Bucles foreach

Permeten recórrer fàcilment arrays i col·leccions de dades.

``` php
foreach ($matrix as $value) {
    sentence_bloc
}
```

``` php
foreach ($matrix as $index => $value) {
   sentence_bloc
}
```

Els recorreguts dels arrays també es poden fer usant les funcions **list()** i **each()**.

  - list() converteix un array en una llista de variables.
  - each() retorna un array amb cada un dels valors del seu argument.

<!-- end list -->

``` php
while ( list($key, $value) = each($array) ) {
    sentence_bloc
}
```

#### continue i break

**continue**: finalitza la iteració actual i torna a l'inici del bloc iteratiu.

**break**: finalitza la iteració actual i salta al final del bloc iteratiu.

## Formatat de dades (printf)

La funció **printf** requereix com a primer paràmetre una cadena de control,la qual indica el format d'impressió de la resta de paràmetres.

``` php
<!DOCTYPE html>
<html lang="es">
<head>
<title>printf example</title>
</head>
<body>
<?php
  $number=255;
  printf("Integer in decimal format %d <br>",$number);
  printf("Integer in hexadecimal format and lowercase %x<br>", $number);
  printf("Integer in hexadecimal format and uppercase %X<br>", $number);
  printf("Integer in binary format %b<br>", $number);
  printf("Integer in octal format %o<br>", $number);
  $letter=65;
  printf("Integer as an ASCII character %c<br>", $letter);
  $real=10.776;
  printf("Double in general format %f <br>",$real);
  printf("Double with two decimal digits %0.2f <br>",$real);
?>
</body>
</html>
```

Si necessitem imprimir el caràcter %, hem de doblar-lo: %%.

Exemple d'ús per a formatar en hexadecimal un color:

``` php
<!DOCTYPE html>
<html lang="es">
<head>
<title>Color in hexadecimal</title>
</head>
<body bgcolor="<?php printf("#%X%X%X",150,150,0); ?>">
En aquesta pàgina definim el color de fons utilitzant la funció printf.
</body>
</html>
```

També podem definir l'amplada de la sortida i la manera d'omplir els espais sobrants:

``` php
<!DOCTYPE html>
<html lang="es">
<head>
<title>printf example: date formatting</title>
</head>
<body>
<?php
  $day=6;
  $month=5;
  $year=2006;
  printf("%02d/%02d/%d",$day,$month,$year);
?>
</body>
</html>
```

## Formatat de dades i sortida a un string (sprintf)

La funció **sprintf** formata les dades de la mateixa manera que printf, però envia la sortida a un string de retorn. Els formats de conversió
són iguals que els de printf:

  - %b integer as binary.
  - %d integer as signed decimal.
  - %u integer as unsigned decimal.
  - %o integer as octal.
  - %x integer as hexadecimal lowercase.
  - %X integer as hexadecimal uppercase.
  - %c integer as ASCII character.
  - %f double.
  - %s string.


``` php
<!DOCTYPE html>
<html lang="es">
<head>
<title>printf example</title>
</head>
<body>
<?php
function toHexColour($red,$green,$blue)
{
  $colour=sprintf("#%02X%02X%02X",$red,$green,$blue);
  return $colour;
}
?>
<table>
<tr>
<td bgcolor="<?php echo toHexColour(255,0,0)?>">Red box</td>
<td bgcolor="<?php echo toHexColour(0,255,0)?>">Green box</td>
<td bgcolor="<?php echo toHexColour(0,0,255)?>">Blue box</td>
</tr>
</table>
</body>
</html>
```

## Gestió de dates i hores amb la funció date

[Extensions relacionades amb dates i hores](https://www.php.net/manual/es/refs.calendar.php)

La funció [date](https://www.php.net/manual/es/function.date) retorna un string amb una data i hora, o parts d'ella en funció del string de format que li passem com a paràmetre. L'hora i la data s'obté del servidor.

``` php
<!DOCTYPE html>
<html lang="es">
<head>
<title>Date and time</title>
</head>
<body>
<?php
  echo "Today is:";
  $today=date("d/m/Y");
  echo $today;
  echo "<br>";
  echo "Now it is:";
  $now=date("H:i:s");
  echo $now;
  echo "<br>";
?>
</body>
</html>
```

Els caràcters de formats en la invocació a **date** són:

  - d dia del mes amb dos dígits "01" al "31"
  - m mes amb dos dígits "01" al "12"
  - Y any amb quatre dígits

I per a l'hora:

  - H hora amb dos dígits "00" a "23"
  - i minuts amb dos dígits "00" a "59"
  - s segons amb dos dígits "00" a "59"

Si volem els dies i mesos sense el zero del davant:

``` php
<!DOCTYPE html>
<html lang="es">
<head>
<title>Date and time</title>
</head>
<body>
<?php
  echo "Today is:";
  $today=date("j/n/y");
  echo $today;
  echo "<br>";
?>
</body>
</html>
```

Els caràcters que tenim ara són: :

  - j día del mes "1" al "31"
  - n mes "1" al "12"
  - y any amb dos dígits

Aquest exemple mostra alguns altres caràcters de format:

``` php
<!DOCTYPE html>
<html lang="es">
<head>
<title>Date and time</title>
</head>
<body>
<?php
  $leapYear=date("L");
  if ($leapYear==1)
    echo "It is a leap year";
  else
    echo "It is not a leap year";
  echo "<br>";
  echo "Day of the week:";
  $dayOfWeek=date("w");
  switch ($dayOfWeek) {
    case 0: echo "sunday";
            break;
    case 1: echo "monday";
            break;
    case 2: echo "tuesday";
            break;
    case 3: echo "wednesday";
            break;
    case 4: echo "thursday";
            break;
    case 5: echo "friday";
            break;
    case 6: echo "saturday";
            break;
  }
?>
</body>
</html>
```

Els caràcters de format són:

  - L "1" o "0", segons si l'any és de traspàs o no
  - w dia de la setmana, en número, de "0" (diumenge) a "6" (dissabte)

## Gestió de dates i hores amb la classe DateTime

[DateTime](https://www.php.net/manual/es/class.datetime.php) (php 5.2+) permet l'ús com a classe i en modalitat procedimental.

``` php
//construcció d'una data
$data1 = new DateTime("2022-02-18");
//llança excepció si el format no és correcte
try {
    $data2 = new DateTime("2022 02 18");
} catch(Exception $e) {
    echo 'Format invàlid de data: ' . $e->getMessage();
}
//construcció de data amb format
$data3 = DateTime::createFromFormat("Y m d", "2022 02 18");
//donar format a una data
$data3->format('d/m/Y');
```

Per sumar o restar intervals de temps a una data o bé per restar dues dates i obtenir l'interval de temps entre elles tenim la classe [DateInterval](https://www.php.net/manual/es/class.dateinterval.php).

``` php
$data->add(new DateInterval('P1D'));  //afegir un dia
```

## Fitxers

[Directory php function
reference](https://www.php.net/manual/en/ref.dir.php)

    chdir()     Changes the current directory
    chroot()    Changes the root directory
    closedir()  Closes a directory handle
    dir()           Returns an instance of the Directory class
    getcwd()    Returns the current working directory
    opendir()   Opens a directory handle
    readdir()   Returns an entry from a directory handle
    rewinddir()     Resets a directory handle
    scandir()   Returns an array of files and directories of a specified directory

## Separació de codi

És convenient separar el codi en fitxers independents.

El fitxer principal pot incloure el codi d'altres fitxers mitjançant les directives:

  - [include](https://www.php.net/manual/en/function.include.php)
  - [require](https://www.php.net/manual/en/function.require.php)
  - [include\_once](https://www.php.net/manual/en/function.include-once.php)
  - [require\_once](https://www.php.net/manual/en/function.require-once.php)

## Sintaxi alternativa per a condicionals i bucles

[Sintaxi alternativa per a estructures de control](https://www.php.net/manual/es/control-structures.alternative-syntax.php)

``` php
<?php if ($x == 5): ?>
<p>X val 5</p>
<?php endif; ?>
```

## Funcions anònimes

Les funcions anònimes són implementades per php com a subclasses de la classe **Closure**.

``` php
$fnSum = function($x, $y) {return $x+$y;};
echo $fnSum(2, 3);
```

## Funcions fletxa (arrow functions)

Defineixen funcions anònimes amb una sintaxi més compacta.

``` php
$fnSum = fn($x, $y) => $x + $y;
echo $fnSum(2, 3);
```
