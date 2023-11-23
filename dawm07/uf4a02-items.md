# Item Manager desenvolupat amb Laravel

Anem a desenvolupar pas a pas una aplicació per a la gestió de 'items' amb Laravel.

Creem el projecte:

    laravel new items
    cd items

Verifiquem que funciona: 

    php artisan serve

i obrim a un navegador la url

    http://localhost:8000

El codi de control és a 'app/Http/', directori que conté:

* Controllers: controladors de la lògica de l'aplicació
* Middleware: controladors de filtre, per exemple, per a autenticació

El directori 'app/Providers' conté els serveis de dades.

El directori 'resources/views' conté les vistes de l'aplicació. Es creen amb el llenguatge **Blade**. La ruta per defecte a les vistes per a tota l'aplicació és aquesta. Si creem un subdirectori per posar-hi una vista, per exemple, 'resources/views/customers/list.blade.php', ens referirem a ella al codi amb la notació 'customers.list'.

El directori 'routes' conté les definicions de l'encaminament entre les diferents vistes i controladors segons les peticions dels clients. És a dir, realitza el mapeig de url a controladors@mètodes o a vistes.

L'estructura de directories d'un projecte Laravel és la següent: https://laravel.com/docs/10.x/structure#the-models-directory

Per modificar l'encaminament o afegir noves rutes, editem el fitxer 'routes/web.php'.

El següent codi defineix una ruta per a la petició 'get' a la url arrel de l'aplicació '/'. La resposta ve donada per la funció anònima, la qual retorna la vista definida al fitxer 'resources/views/welcome.blade.php'. 

```php
Route::get('/', function () {
    return view('welcome');
});
```

o utilitzant una *Facade*: 

```php
Route::get('/', function () {
    return View::make('welcome');
});
```

La funció de resposta pot retornar directament un string, però és més net delegar les respostes a les vistes.

```php
Route::get('/', function () {
    return "Hello!";
});
```

La solució més elegant, no obstant, és encaminar les peticions a un mètode d'un controlador, el qual farà les peticions als proveïdors de servei de dades i passarà la informació de resposta a les vistes, d'acord amb el patró MVC.

```php
Route::get('/', 'ItemController@list');
```

O per a Laravel versió 8 o posterior:

```php
Route::get('/', [ItemController::class, 'list']);
```

Les peticions que es poden enrutar inclouen:

* get
* post
* put
* delete

## Vistes amb Blade

Blade permet estructurar les vistes per compartir plantilles entre elles.

Definim la plantilla general de les vistes de la nostra aplicació:

**layout.blade.php**
```html
<!DOCTYPE html>
<html lang="{{ config('app.locale') }}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>@yield('title')</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/styles.css"> 
</head>
<body>
    @section('topmenu')
    @include('menubar')
    @show
    
    <div class="container">
        @yield('content')
    </div>
    
    @section('footer')
    Application by ProvenSoft.
    @show
    
</body>
</html>
```

La directiva *@section* defineix una secció de la plantilla amb un nom. Amb *@show* fem que es mostri. Amb *@include* podem incloure una altra plantilla. *@yield* mostra el contingut de la secció, tal com és generat per la plantilla que estén la general.

La plantilla general contindrà una barra superior de menús, una secció de contingut i una secció de peu inferior. 

**menubar.blade.php**
```html
<nav>
    <ul>
    <li><a href="/home">Home</a></li>
    <li><a href="/items">Items</a></li>
    </ul>
</nav>
```

Afegim una vista que extengui la plantilla general, afegint, per exemple, un nou enllaç al menú superior i també el contingut de la secció 'content'.

**welcome.blade.php**
```html
@extends('layout')
    
@section('topmenu')
    @parent
    <a href="http://www.google.es">Google</a>
@endsection
    
@section('content')
<h2>Welcome to Item manager applicacion</h2>
<p>An applicacion made with Laravel framework</p>
@endsection
```

I apliquem una senzilla plantilla css per formatar mínimament les pàgines.

**styles.css**
```css
nav ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: #6297c0;
}
    
nav li {
    float: left;
}
    
nav li a {
    display: block;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}
    
nav li a:hover {
    background-color: #111;
}
    
table {
    border-collapse: collapse;
}
table th, tr, td {
    border: solid thin blue;
}
table th {
    background-color: lightgray;
}
    
form fieldset {
    display: block;
    margin: 2em;
}
    
form div {
    display: block;
}
form label {
    float: left;
    clear: left;
    min-width: 5em;
}
    
form input {
    float: left;
    clear: right;
    min-width: 5em;
}
    
table a {
    text-decoration: none;
}
    
table a:hover {
    color:brown;
}
    
.alert {
    background-color: #58d4ce;
    color: yellow;
    float: left;
}
```

## Sistema de navegació de l'aplicació

Definim una pàgina principal per a la gestió de items:

**item/index.blade.php**
```html
@extends('layout')
    
@section('content')
<h2>Item page</h2>
@endsection
```

Definim el sistema de rutes següent:

```php
Route::get('/', function () {
    return view('welcome');
});
 
Route::get('/home', function () {
    return view('welcome');
});
 
Route::get('/items', function () {
    return view('item.index');
});
```

Ara podem afegir una ruta per fer el llistat de items:
```php
Route::get('/items/list', function () {
    $items = ['Item1', 'Item2', 'Item3'];
    //$items = [];  //to test for empty list.
    return view('item.list', compact('items'));
});
```

El pas de dades a la vista es fa amb el mètode ***compact()*** i el nom de la variable.

Altres alternatives equivalents són: 

```php
return view('item.list')->with('items', $items);
return view('item.list')->withItems($items);
```

 I afegim la vista

**item/list.blade.php**
```php
@extends('layout')
    
@section('content')
    
@if(empty($items))
    <p>There are no items!</p>
@else
<ul>
    @foreach($items as $item)
        <li>{{ $item }}</li>
    @endforeach
    </ul>    
@endif
    
@endsection
```

## Controladors

Substituim les rutes a la gestió de items per aquestes:

```php
//per a laravel versió < 8
Route::get('/items', 'ItemController@index');
Route::get('/items/list', 'ItemController@list');
//per a laravel versió 8
Route::get('/items', [ItemController::class, 'index']);
Route::get('/items/list', [ItemController::class, 'list']);
```

Ara s'enruta a mètodes (*index*, *list*, …) del controlador *ItemController*.

A la consola, creem el controlador:

    php artisan make:controller ItemController

Ara copiem el codi de les anteriors funcions d'enrutament a les corresponents del controlador. 

**ItemController.php**
```php
<?php
    
namespace App\Http\Controllers;
    
use Illuminate\Http\Request;
    
class ItemController extends Controller
{
    public function index() {
        //return 'Item page';
        return view('item.index');
    }
    
    public function list() {
        $items = ['Item1', 'Item2', 'Item3'];
        //$items = [];
        return view('item.list', compact('items'));
    }
    
}
```

## Model de dades amb Eloquent

### Connexió amb sqlite

Cal revisar les configuracions d'accés a dades al fitxer '/config/database.php'. Per simplicitat, comencem treballant amb 'sqlite'. Canviem, doncs, la configuració :

    'default' => env('DB_CONNECTION', 'sqlite'),

Hem de crear manualment el fitxer de la base de dades sqlite:

    touch database/database.sqlite

Al fitxer .env del directori arrel del projecte cal canviar la connexió a sqlite i esborrar la resta de línies que segueixen i que comencen per DB_:

   DB_CONNECTION=sqlite

### Connexió amb mysql

Cal revisar les configuracions d'accés a dades al fitxer '/config/database.php'. Podemm canviar la configuració :

  'default' => env('DB_CONNECTION', 'mysql'),

Al fitxer .env del directori arrel del projecte cal canviar la connexió a mysql i esborrar la resta de línies que segueixen i que comencen per DB_:
```
DB_CONNECTION=mysql
DB_HOST=127.0.0.1
DB_PORT=3306
DB_DATABASE=itemdb
DB_USERNAME=itemusr
DB_PASSWORD=itempsw
```
Ara caldria escriure manualment el codi de les taules i el seu contingut.

Alternativament, Laravel proporciona eines anomenades '**migrations**' (veure el directori '/database/migrations').

Utilizem ***artisan*** per crear una migració de creació de la taula de items:

    php artisan make:migration create_items_table --create=items

I modifiquem l'estructura de la taula: 

**create_items-table.php**
```php
<?php
    
use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;
    
class CreateItemsTable extends Migration
{
    /**
        * Run the migrations.
        *
        * @return void
        */
    public function up()
    {
        Schema::create('items', function (Blueprint $table) {
            $table->engine = 'InnoDB';
            $table->increments('id');
            $table->string('title');
            $table->string('content');
            //$table->timestamps();
        });
    }
    
    /**
        * Reverse the migrations.
        *
        * @return void
        */
    public function down()
    {
        Schema::dropIfExists('items');
    }
}
```

Per executar la migració:

    php artisan migrate

Per fer proves, executar consultes, etc, disposem de l'eina **tinker**:
```
php artisan tinker

DB::table('items')->insert(['title'=>'Title1', 'content'=>'Content1']);
DB::table('items')->get();
DB::table('items')->where('title', 'Title1')->first();
```

Ara substituim la funció *list()* de *ItemController* perquè recuperi les dades de la base de dades:

```php
public function list() {
    //$items = ['Item1', 'Item2', 'Item3'];  //using array with mock data
    //$items = [];  //using empty array
    $items = \DB::table('items')->get(); //using database
    return view('item.list', compact('items'));
}
```

***Elloquent*** usa el Active Record Pattern per generar classes del model associades a les taules:

    php artisan make:model Item

S'ha creat el fitxer '/app/Models/Item.php':

**Item.php**
```php
<?php
    
namespace App\Models;
    
use Illuminate\Database\Eloquent\Model;
    
class Item extends Model
{
    use HasFactory;
    public $timestamps = false;  //turn off timestamps in migration.    
}
```

Ara podem modificar el controlador per utilizar aquest model: 


**ItemController.php**
```php
<?php
    
namespace App\Http\Controllers;
    
use Illuminate\Http\Request;
    
use App\Models\Item;
    
class ItemController extends Controller
{
    public function index() {
        //return 'Item page';
        return view('item.index');
    }
    
    public function list() {
        //$items = ['Item1', 'Item2', 'Item3'];  //using array with mock data
        //$items = [];  //using empty array
        //$items = \DB::table('items')->get(); //using database
        $items = Item::all();  //using model.
        return view('item.list', compact('items'));
    }
    
}
```

Anem ara a desenvolupar la cerca d'un Item, donat el seu id.

Afegim la ruta:

    Route::get('/items/{item}', [ItemController::class, 'find']);

Afegim al controlador el mètode *find()*:

```php
public function find($id) {
    $item = Item::find($id);
    return $item;
}
```
el qual retorna l'objecte Item en format JSON. 

 Si volem generar una vista de formulari, canviem el mètode *find()*:
```php
public function find($id) {
    $item = Item::find($id);
    //return $item;
    return view('item.form', compact('item'));
}
```

Ara creem la vista 'form.blade.php':

**form.blade.php**
```php
@extends('layout')
    
@section('content')
    
@if(empty($item))
    <p>No item found!</p>
@else
<form>
    <div>
        <label for="id">Id:</label>
        <input type="text" name="id" value="{{$item->id}}"/>
    </div>
    <div>
        <label for="title">Title:</label>
        <input type="text" name="title" value="{{$item->title}}"/>
    </div>
    <div>
        <label for="content">Content:</label>
        <input type="text" name="content" value="{{$item->content}}"/>
    </div>
</form>    
@endif
    
@endsection
```

 També podem deixar que Laravel i Eloquent s'ocupin d'omplir de contingut les dades automàticament:
```php
/**
* Això també funciona si el paràmetre a la ruta es diu item.
* Laravel-Eloquent s'ocupa de fer la cerca automàticament.'
*/
public function find(Item $item) {
    return view('item.form', compact('item'));
}
```

[Aplicació de gestió de 'Items' amb Laravel (sols model amb Item)](assets/4.2/items-laravel-nomes_item.zip)

[Items (projecte complet fins aquest punt) ](assets/4.2/items.zip)

## Model amb relacions 1-n amb Eloquent

Anem a crear una entitat (taula a la base de dades i classe al model de dades) **Note**, de manera que una *Note* pertany a un *Item*, i un *Item* té diversos *Note*.

En primer lloc, creem la migració per crear la taula de notes:

   php artisan make:migration create_notes_table --create=notes

I definim el mètode ***up()*** per crear la taula de notes:
```php
Schema::create('notes', function (Blueprint $table) {
    $table->engine = 'InnoDB';
    $table->increments('id');
    $table->text('content');
    $table->integer('item_id')->unsigned()->index();
    $table->foreign('item_id')->references('id')->on('items')->onUpdate('cascade')->onDelete('restrict');
    //$table->timestamps();
});
```
Ara executem l'eina ***migrate***:

    php artisan migrate

Generar un esquema sql de la base de dades. Després, en executar migrate primer carregarà a la base de dades l'esquema generat i després amb –prune s'aplicaran les migracions pendents.
```
php artisan schema:dump
# Dump the current database schema and prune all existing migrations...
php artisan schema:dump --prune
```

Per saber quines migracions s'han fet (consulta la taula de migracions de la base de dades):

    php artisan migrate:status

Per desfer l'ultima tanda de migracions:

    php artisan migrate:rollback

Per desfer totes les migracions efectuades:

    php artisan migrate:reset

Per desfer i tornar a fer totes les migracions:
```
php artisan migrate:refresh
# Refresh the database and run all database seeds...
php artisan migrate:refresh --seed
```
Creem la classe de model per a Note:

    php artisan make:model Note {-m}

El paràmetre opcional -m força fer la migració en el moment de la creació del model.

Anem a crear notes:
```
php artisan tinker

$item = App\Models\Item::first();
$note = new App\Models\Note;
$note->content = 'some content for the item';
$note->item_id = $item->id;
$note->save();
```

Establim les relacions entre les entitats Item i Note: 

Un Item té molts Note:

**Item.php**
```php
<?php
namespace App\Models;
use Illuminate\Database\Eloquent\Model;
class Item extends Model
{
    use HasFactory;
    public $timestamps = false;  //turn off timestamps in migration.
    
    //fields fillable with mass create method.
    protected $fillable = ['title', 'content'];
    
    //relationship OneToMany between Item and Note
    public function notes() {
        //return $this->hasMany('App\Note');
        return $this->hasMany(Note::class);        
    }
}
```

I ara un Note pertany a un Item:

**Note.php**
```php
<?php
    
namespace App\Models;
    
use Illuminate\Database\Eloquent\Model;
    
class Note extends Model
{
    use HasFactory;
    public $timestamps = false;  //turn off timestamps in migration.
    
    //fields fillable with mass create method.
    protected $fillable = ['content'];
    
    //relationship ManyToOne between Note and Item
    public function item() {
        return $this->belongsTo(Item::class);
    }
}
```

Ara podem usar aquestes notacions per obtenir els objectes relacionats:
```
php artisan tinker
 
$item->notes();
//parentesis no són necessaris:
$item->notes;
//o accedir com a array:
$item->notes[0];
//o com a col·lecció (es recupera tota la col·lecció i després se selecciona el primer)
$item->notes->first();
//o com a funció (es recupera només el primer)
$item->notes()->first();
 
$note = App\Models\Note::first();
```

Podem visualitzar quines consultes realment s'estan fent a la base de dades:
```
php artisan tinker
 
DB::listen(function($query) {var_dump($query->sql);});
 
$item = App\Models\Item::first();
//compara amb:
App\Models\Item::notes;
```

Els objectes Note de Item s'emmagatzemen en Item la primera vegada que es consulta aquest Item i no es torna a consultar si no es demana que es refresqui la informació.

    $item = $item->fresh();

Un cop establertes correctament les relacions entre les entitats, es pot escriure codi com el següent:
```
$note = new App\Models\Note;
$note->content = 'This is the note content';
$item->notes()->save($note);
```

Ara cal fer les modificacions pertinents a la vista de mostrar un Item, per tal que mostri també la llista dels seus objectes Note.

**form.blade.php**
```php
@extends('layout')
    
@section('content')
    
@if(empty($item))
    <p>No item found!</p>
@else
<form>
    <div>
        <label for="id">Id:</label>
        <input type="text" name="id" value="{{$item->id}}"/>
    </div>
    <div>
        <label for="title">Title:</label>
        <input type="text" name="title" value="{{$item->title}}"/>
    </div>
    <div>
        <label for="content">Content:</label>
        <input type="text" name="content" value="{{$item->content}}"/>
    </div>
    <ul>
        @foreach($item->notes as $note)
            <li>{{$note->content}}</li>
        @endforeach
    </ul>
</form>    
@endif
    
@endsection
```

També podem afegir a la vista de llistat de Items un enllaç a cada Item perquè mostri la vista de detall:

**list.blade.php**
```php
@extends('layout')
    
@section('content')
    
@if(empty($items))
    <p>There are no items!</p>
@else
<ul>
    @foreach($items as $item)
        <li><a href="/items/{{$item->id}}">{{ $item->title }}</a></li>
    @endforeach
</ul>    
@endif
    
@endsection
```

## Poblar la base de dades amb dades de prova (seed)

Per tal de generar dades de test per a provar l'aplicació o per a introduir a la base de dades abans de fer els tests, generarem un Factory per a cada entitat del model:
```
php artisan make:factory ItemFactory
php artisan make:factory NoteFactory
```

Editem les classes ItemFactory i NoteFactory: 

**ItemFactory.php**
```php
namespace Database\Factories;
    
use Illuminate\Database\Eloquent\Factories\Factory;
    
/**
    * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Item>
    */
class ItemFactory extends Factory
{
    /**
        * Define the model's default state.
        *
        * @return array<string, mixed>
        */
    public function definition()
    {
        return [
            'title' => $this->faker->name(),
            'content' => $this->faker->sentence($nbWords = 4, $variableNbWords = true)
        ];
    }
}
```
**NoteFactory.php**
```php
namespace Database\Factories;
    
use Illuminate\Database\Eloquent\Factories\Factory;
    
/**
    * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Note>
    */
class NoteFactory extends Factory
{
    /**
        * Define the model's default state.
        *
        * @return array<string, mixed>
        */
    public function definition()
    {
        return [
            'content' => $this->faker->sentence($nbWords = 6, $variableNbWords = true)
        ];
    }
}
```

Ara podem crear un proveïdor de dades de prova (seeder) per a Item i un altre per a Note:

    php artisan make:seeder ItemsTableSeeder
    php artisan make:seeder NotesTableSeeder

Després, cal invocar el seeder des de DatabaseSeedeer.php. 

**DatabaseSeeder.php**
```php
<?php
    
use Illuminate\Database\Seeder;
use Illuminate\Database\Eloquent\Model;
    
class DatabaseSeeder extends Seeder
{
    /**
        * Run the database seeds.
        *
        * @return void
        */
    public function run()
    {
        $this->call(ItemsTableSeeder::class);
    }
}
```

Hauríem de fer el mateix per a Note.

Com que només tenim dues taules, podem simplificar aquest procés i posar tot el codi dintre de DatabaseSeeder.php sense haver de crear un seeder per a Item i un altre per a Note.

**DatabaseSeeder.php**
```php
<?php
    
use Illuminate\Database\Seeder;
use Illuminate\Database\Eloquent\Model;
    
class DatabaseSeeder extends Seeder
{
    /**
        * Seed the application's database.
        *
        * @return void
        */
    public function run()
    {
    {
        // \App\Models\User::factory(10)->create();
        \App\Models\Item::factory()
            ->count(10)
            ->sequence(fn ($sequence) => ['title' => 'title '.$sequence->index])
            ->hasNotes(5)
            ->create();
}
```

Per poblar la base de dades:

    php artisan db:seed

## Formularis i enviament de dades

Primer de tot anem a suprimir els enllaços a sass o less (si els havíem posat) i afegim l'enllaç per a bootstrap a layout.blade.php. També eliminem els estils que haguem posat als fitxers d'estils de sass o less.

Modifiquem la vista que mostra un Item, per afegir un formulari que permeti afegir un Note.

**form.blade.php**
```php
@extends('layout')
    
@section('content')
    
@if(empty($item))
    <p>No item found!</p>
@else
<form method="post" action="/items/{{$item->id}}/notes">
    <fieldset>
    @csrf
    <div>
        <label for="id">Id:</label>
        <input type="text" name="id" value="{{$item->id}}"/>
    </div>
    <div>
        <label for="title">Title:</label>
        <input type="text" name="title" value="{{$item->title}}"/>
    </div>
    <div>
        <label for="content">Content:</label>
        <input type="text" name="content" value="{{$item->content}}"/>
    </div>
    </fieldset>
    <div>
    <b>Notes:</b>
    <ul>
        @foreach($item->notes as $note)
            <li><a href="/notes/{{$note->id}}/edit">{{$note->content}}</a></li>
        @endforeach
    </ul>
    </div>
    
<hr/>
<h3>Add a new note</h3>
<form method="post" action="/items/{{$item->id}}/notes">
    <div class="form-group">
        <textarea name="content" class="form-control">{{old('content')}}</textarea>
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-primary">Add note</button>
    </div>
</form>
    
@endif
    
@endsection
```
Afegim la ruta destinació del formulari

    Route::post('/items/{item}/notes', [NoteController::class, 'store']);

Creem un controlador per a notes.

    php artisan make:controller NoteController

**NoteController.php**
```php
<?php
namespace App\Http\Controllers;
    
use Illuminate\Http\Request;
    
use App\Item;
use App\Note;
    
class NoteController extends Controller
{
    public function store(Request $request, Item $item) {
        //return $request->all();  //only to test data has been received.
        $note = new Note;
        $note->content = $request->content;
        $item->notes()->save($note);
        //or alternatively:
        //$item->notes()->create(['content' => $request->content]);
        //or with a method addNote in Item:
        //$item->addNote($note);
        //return \Redirect::to('url');  //with a facade
        //return redirect('url');   //with function
        return back();  //return back.
    }
}
```
Podem millorar l'estructura del codi afegint un mètode ***addNote()*** a la classe Item:
```php
public function addNote(Note $note) {
    return $this->notes()->save($note);
}
```

## Formularis d'edició

Afegim una nova ruta per editar notes:

    Route::get('/notes/{note}/edit', [NoteController::class, 'edit']);

Afegim el mètode ***edit()*** a *NoteController*:
```php
public function edit(Note $note) {
    return view('note.edit', compact('note'));
}
```

Ara cal crear la vista form.blade.php al subdirectori note:

**form.blade.php**
```php
@extends('layout')
    
@section('content')
    
@if(empty($note))
    <p>No note found!</p>
@else
<hr/>
<h3>Edit a note</h3>
<form method="post" action="/notes/{{$note->id}}">
    @csrf
    <div class="form-group">
        <textarea name="content" class="form-control">{{$note->content}}</textarea>
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-primary">Update note</button>
    </div>
</form>
    
@endif
    
@endsection
```

Al formulari, el codi **@csrf** genera un camp ocult que s'envia amb el formulari i permet saber al controlador que el formulari ha estat enviat pel mateix servidor.

Afegim la nova ruta així com el mètode ***update()*** a *NoteController*.

    Route::post('/notes/{note}', [NoteController::class, 'update']);

```php
public function update(Request $request, Note $note) {
        $noteNew = Note::find($note->id);
        $noteNew->content = $request->content;
        $noteNew->save();
        return back(); //redirection
}
```

## Validació de dades

La classe **Controller**, la qual estenen tots els controladors, conté un mètode **validate()**

    public function validate(Request $request, array $rules, array $messages=[], ...);

El mètode accepta un array de regles de validació, un array de missatges d'error i altres paràmetres (veure documentació oficial).

Modifiquem el mètode store() de NoteController per incloure la validació. En aquest cas, imposem que 'content' és requerit:
```php
public function store(Request $request, Item $item) {
    //validation:
    $this->validate($request, [
        'content' => 'required|min:10'
    ]);
    $note = new Note;
    $note->content = $request->content;
    $item->addNote($note);
    return back();  //return back.
}
```

Si la validació falla, Laravel llança una excepció, però s'encarrega de capturar-la automàticament. El resultat és que redirecciona a la url de redirecció definida, juntament amb un array anomenat ***errors***, els quals podem mostrar a la vista. Amb la redirecció també envia les dades que s'havien rebut.

El mecanisme es basa en controladors que fan de filtre, anomenats ***Middleware***. Perquè funcionin aquests filtres amb les url que hem definit, cal incloure les seves rutes en el grup 'web' de Middleware. Canviem el fitxer de rutes 'web.php': 

**web.php**
```php
<?php
Route::group(['middleware' => ['web']], function () {
    Route::get('/', function () {
        return view('welcome');
    });
    Route::get('/home', function () {
        return view('welcome');
    });
    Route::get('/items', function () {
        return view('item.index');
    });
    Route::get('/items/list', [ItemController::class, 'list']);
    Route::get('/items/{item}', [ItemController::class, 'find']);
    Route::post('/items/{item}/notes', [NoteController::class, 'store']);
    Route::get('/notes/{note}/edit', [NoteController::class, 'edit']);
    Route::post('/notes/{note}', [NoteController::class, 'update']);
});
```

Tot formulari ha de contenir un camp ocult amb el ***csrf→token()***. Afegim, doncs, a tots els formularis:

    {{csrf->field()}}

o bé

    @csrf

Ara podem afegir un bloc per mostrar errors a la vista (fitxer form.blade.php) de formulari de Item. 

**form.blade.php**
```php
@extends('layout')
    
@section('content')
    
@if(empty($item))
    <p>No item found!</p>
@else
<form method="post" action="/items/{{$item->id}}/notes">
    <div>
        <label for="id">Id:</label>
        <input type="text" name="id" value="{{$item->id}}"/>
    </div>
    <div>
        <label for="title">Title:</label>
        <input type="text" name="title" value="{{$item->title}}"/>
    </div>
    <div>
        <label for="content">Content:</label>
        <input type="text" name="content" value="{{$item->content}}"/>
    </div>
    <ul>
        @foreach($item->notes as $note)
            <li>{{$note->content}}</li>
        @endforeach
    </ul>
    
<hr/>
<h3>Add a new note</h3>
    
    {{csrf->field()}}
    <div class="form-group">
        <textarea name="content" class="form-control">{{old('content')}}</textarea>
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-primary">Add note</button>
    </div>
</form>
    
@if (count($errors))
    <ul>
        @foreach ($errors->all() as $error)
            <li>{{$error}}</li>
        @endforeach
    </ul>
@endif
    
@endif
    
@endsection
```

El mètode ***old()*** recupera el valor del camp especificat que s'havia enviat.

## Autenticació d'usuaris

Cal definir controladors per a login i logout i canviar les rutes perquè les pàgines que requereixin inici de sessió passin pel **middleware**.

També necessitem un model per a usuaris. Per tant, cal una classe User i les migracions (per crear les taules a la base de dades) i seeds que convinguin per generar les dades de test.

Per últim, cal generar vistes per les noves funcionalitats. Almenys cal un formulari per introduir les credencials d'accés.

### La classe User

**User.php**
```php
class User extends Authenticatable
{
    use HasApiTokens, HasFactory, Notifiable;
    
    /**
     * The attributes that are mass assignable.
     *
     * @var array<int, string>
     */
    protected $fillable = [
        'name',
        'email',
        'password',
    ];
    
    /**
     * The attributes that should be hidden for serialization.
     *
     * @var array<int, string>
     */
    protected $hidden = [
        'password',
        'remember_token',
    ];
    
    /**
     * The attributes that should be cast.
     *
     * @var array<string, string>
     */
    protected $casts = [
        'email_verified_at' => 'datetime',
    ];
}
```

### La migració per a la taula d'usuaris

**create_users_table.php**
```php
use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
    
return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('users', function (Blueprint $table) {
            $table->id();
            $table->string('name');
            $table->string('email')->unique();
            $table->timestamp('email_verified_at')->nullable();
            $table->string('password');
            $table->rememberToken();
            $table->timestamps();
        });
    }
    
    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('users');
    }
};
```

### La Factory per a usuaris

**FactoryUser.php**
```php
namespace Database\Factories;
    
use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Str;
    
/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\User>
 */
class UserFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition()
    {
        return [
            'name' => $this->faker->name(),
            'email' => $this->faker->unique()->safeEmail(),
            'email_verified_at' => now(),
            'password' => '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', // password
            'remember_token' => Str::random(10),
        ];
    }
    
    /**
     * Indicate that the model's email address should be unverified.
     *
     * @return static
     */
    public function unverified()
    {
        return $this->state(function (array $attributes) {
            return [
                'email_verified_at' => null,
            ];
        });
    }
}
```

### La classe de seeder per a usuaris

**UserTableSeeder.php**
```php
class UserTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        //
    }
}
```

### El LoginController

**LoginController.php**
```php
namespace App\Http\Controllers;
    
use Illuminate\Http\Request;
use App\Http\Requests\LoginRequest;
use Illuminate\Support\Facades\Auth;
//use App\Services\Login\RememberMeExpiration;
    
class LoginController extends Controller
{
    //use RememberMeExpiration;
    
    /**
     * Display login page.
     * @return Renderable
     */
    public function show() {
        return view('auth.login');
    }
    
    public function login(Request $request) {
            $username = $request['username'];
            $password = $request['password'];
            $credentials = [
                'email' => $username,
                'password' => $password 
            ];
            //var_dump($credentials);
            $user = Auth::getProvider()->retrieveByCredentials($credentials);
            Auth::login($user);
            $request->session()->put('username', $user->name);
            return $this->authenticated($request, $user);
    }
    
    /**
     * Handle response after user authenticated
     * @param Request $request
     * @param Auth $user
     * @return \Illuminate\Http\Response
     */
    protected function authenticated(Request $request, $user) {
        return redirect()->intended();
    }
}
```

### El LogoutController

**LogoutController.php**
```php
namespace App\Http\Controllers;
    
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Session;
    
class LogoutController extends Controller
{
    /**
     * Log out account user.
     * @return \Illuminate\Routing\Redirector
     */
    public function perform() {
        Session::flush();
    
        Auth::logout();
    
        return redirect('login');
    }
}
```

### La vista d'autenticació (formulari de login) a 'resources/views/auth'

**login.blade.php**
```php
@extends('layout')
    
@section('content')
    
<form method="post" action="{{ route('login.perform') }}">
    @csrf
    <h1 class="h3 mb-3 fw-normal">Login</h1>
    
    <div class="form-group mb-3">
        <label for="username">Email or Username</label>
        <input type="text" class="form-control" name="username" value="{{ old('username') }}" placeholder="Username" required="required" autofocus>
        @if ($errors->has('username'))
            <span class="text-danger text-left">{{ $errors->first('username') }}</span>
        @endif
    </div>
    
    <div class="form-group mb-3">
        <label for="password">Password</label>
        <input type="password" class="form-control" name="password" value="{{ old('password') }}" placeholder="Password" required="required">
        @if ($errors->has('password'))
            <span class="text-danger text-left">{{ $errors->first('password') }}</span>
        @endif
    </div>
    
    <div class="form-group mb-3">
        <label for="remember">Remember me</label>
        <input type="checkbox" name="remember" value="1">
    </div>
    
    <button class="w-100 btn btn-lg btn-primary" type="submit">Login</button>
    
</form>
    
@endsection
```

### El fitxer de rutes

**web.php**
```php
use Illuminate\Support\Facades\Route;
    
use App\Http\Controllers\ItemController;
use App\Http\Controllers\NoteController;
use App\Http\Controllers\LoginController;
use App\Http\Controllers\LogoutController;
    
Route::group(['middleware' => ['web']], function () {
    Route::get('/', function () {
        return view('welcome');
    });
    Route::get('/home', function () {
        return view('welcome');
    });
    Route::get('/items', function () {
        return view('item.index');
    });
    Route::get('/items/list', [ItemController::class, 'list']);
    Route::get('/items/{item}', [ItemController::class, 'find']);
    Route::post('/items/{item}/notes', [NoteController::class, 'store']);
    Route::get('/notes/{note}/edit', [NoteController::class, 'edit']);
    Route::post('/notes/{note}', [NoteController::class, 'update']);
});
    
Route::middleware(['auth'])->group(function () {
    Route::get('/logout', [LogoutController::class, 'perform'])->name('logout');
});
    
Route::middleware(['guest'])->group(function () {
    Route::get('/login', [LoginController::class, 'show'])->name('login.show');
    Route::post('/login', [LoginController::class, 'login'])->name('login.perform');
});
```

[Codi complet aplicació de gestió de Item i Note (sense autenticació)](assets/4.2/items_notes-laravel.zip)

[Codi complet aplicació de gestió de Item i Note (amb autenticació)](assets/4.2/items_notes-laravel-autent.zip)
