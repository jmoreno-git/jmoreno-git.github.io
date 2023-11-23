# PHP 8.x basics

En aquesta pàgina es presenten les principals característiques de PHP a partir de la versió 8.

## Paràmetres amb nom

Els paràmetres amb nom (*named parameters*) permeten passar valors a una funció sense tenir en compte l'ordre dels paràmetres.

``` php
function foo(string $x, string $y, string $z=null, string $t=null) {
 /* ...  */
}
foo(y:'adéu', x:'hola', t:'món');
```

## Tipus Unió

Un tipus unió (*union type*) és una col·lecció de diversos tipus per a un argument, atribut o tipus de retorn de funció. Qualsevol d'ells seria vàlid per a la variables o valor declarat.

``` php
public function foo(String|int $input): int|float;
```

## Operador Nullsafe

Permet tractar de manera compacta les eventualitats de valors nuls, evitant haver de fer comprovacions explícites abans d'usar una variable que pot valer *null*.

``` php
//before php 8
if ($user !== null) {
  $name = $user->getName();
  if ($name !== null) {
    echo $name;
  }  
}
//with php 8
$name = $user?->getName();
echo $name;
```

## Tipus Nullable

Els tipus de paràmetre, propietat i tipus de retorn poden ser marcats amb el símbol **?** per indicar que poden admetre el valor *null*.

Recordem que *null* és un tipus de dada. Per tant, una variable de tipus, per exemple *string*, no pot admetre el valor *null* si no es marca amb ? o bé s'utilitza unió de tipus *String|null*.

## Expressió match

L'expressió **match** (*match expresion*) és una millora de l'expressió switch. Match permet retornar un valor i utilitza comparacions estrictes (tenint en compte el tipus de la variable i el valor a comparar)

``` php
$result = match ($value) {
   0 => 'value is numeric',
   '1', '2', '3' => 'value is string',
   default => 'not match',
}
```

L'expressió *match* no requereix la sentència *break*.

Si no troba cap coincidència llança l'excepció **UnhandledMatchError**.

Només admet per a cada cas una expressió. De moment, no admet blocs de codi.

## Promoció de propietats al constructor

La promoció de propietats al constructor (*constructor property promotion*) permet simplificar la definició de classes utilitzant el constructor per definir propietats.

``` php
//before php 8
class User {
  private string $name;
  private int $age;
  
  public function __construct(string $name='', int $age=0) {
     $this->name = $name;
     $this->age = $age;
  }
}

//with php 8
class User {
  
  public function __construct(private string $name='', private int $age=0) {

  }
}
```

## Mixed Type

Ara **mixed** és un tipus que es pot aplicar a arguments, propietats i valors de retorn. Pot representar qualsevol d'aquests valors:

  - array
  - bool
  - callable
  - int
  - float
  - null
  - object
  - resource
  - string

## Obtenció de la classe d'un objecte

Es pot aplicar **::class** tant a classes com a objectes per tal d'obtenir el FQDN de la classe.

``` php
class User {
 /* .. */
}
var_dump(User::class);
$user = new User();
var_dump($user::class);
```

Aquesta notació és més simple que l'anterior ús de la funció *get\_class()*.

## Noves funcions de gestió de string

  - str\_contains
  - str\_starts\_with
  - str\_ends\_with

## Interface Stringable

La interface **Stringable** permet assignar tipus a arguments o valors de retorn que són de tipus *string* o bé que implementen el mètode *\_\_toString()*.

Qualsevol classe que implementi el mètode *\_\_toString()* implementarà automàticament aquesta interface.

``` php
class User {
  
  public function __construct(private string $name='', private int $age=0) {

  }
  
  public function __toString(): string {
     return sprintf("User:{[name=%s][age=%d]}", $this->name, $this->age);
  }
  
}

function display(Stringable $stringable) {
   echo $stringable;
}

display(new User('Peter', 30));
display('Hello');
```

## Examples

``` php
<?php 
//PHP 8.0 example.
namespace proven;
//class definition.
class User {
    public function __construct(
        private ?string $name, //nullable property.
        private int $age
    ) {

    }

    public function __toString(): string {
        return \sprintf("User:{[name=%s][age=%d]}", $this->name, $this->age);
    }
}
//construct a user with positional parameters.
echo "construct a user with positional parameters" . PHP_EOL;
$user1 = new User("Laura", 25);
\print_r ($user1);
//construct a user with named parameters.
echo "construct a user with named parameters" . PHP_EOL;
$user2 = new User(age:30, name:"Peter");
\print_r ($user2);
//construct a user with null parameters.
echo "construct a user with null parameters" . PHP_EOL;
$user3 = new User(null, 20);
\print_r ($user3);
//get class name.
echo "get class name" . PHP_EOL;
\print_r (User::class);
echo PHP_EOL;
\print_r ($user2::class);
echo PHP_EOL;
```

[Novetats en PHP 8.1](https://www.php.net/releases/8.1/es.php)