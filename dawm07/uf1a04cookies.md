# Cookies

Les ***cookies*** són parelles nom:valor que s'emmagatzemen a l'ordinador client i que viatgen a les capçaleres (*headers*) de les peticions *http*. Aquesta informació s'utilitza per a finalitats molt
diverses, com ara autenticació, selecció de preferències, etc.).

Normalment, és el servidor qui crea les *cookies* i les inclou a la capçalera de la resposta a una petició *http*. El navegador del client desa les *cookies* (nom i valor) així com la identificació del servidor que les ha enviades.

PHP proporciona per a la creació de *cookies* la funció [setcookie()](http://www.php.net/manual/es/function.setcookie.php). Al igual que la resta d'elements de l'encapçalament, les *cookies* s'han de crear abans de generar cap sortida, és a dir, abans d'escriure el contingut de la pàgina de resposta.

Convé evitar als noms de les *cookies* els '.' i els ' ', els quals són substituïts per '\_'.

Per a accedir a les *cookies*, PHP proporciona accés amb la variable superglobal
[$\_COOKIE](https://www.php.net/manual/es/reserved.variables.cookies.php):

``` php
echo $_COOKIE['user'];
```

Convé filtrar els valors de les cookies abans d'utilitzarles, al igual que es fa amb les altres dades que s'envien des del client.

``` php
echo filter_input(INPUT_COOKIE,'user');
```

En alguns servidors, l'array associatiu de *cookies* també es pot obtenir de la variable $\_SERVER:

``` php
echo $_SERVER[‘HTTP_COOKIE’];
```

Exemple comptador de visites amb *cookies*: [counter.zip](/docencia/dawbi/m07/uf1/counter.zip)

**Exemple idiomes amb cookies**

``` php
<?php
 $user = "";
 $password = "";
 $language = "";
 $idiomaCookie= "";
 
 if (filter_has_var(INPUT_POST,'submit') ) {
    $user = filter_input(INPUT_POST,'user');
    $password = filter_input(INPUT_POST,'password');
    $language = filter_input(INPUT_POST,'language');
    
    if(isset($language)) {
      setcookie('idioma',$language );
      //expires 10 dias, tot el domini, httpOnly
      $expires =  time()+(60*60*24+10);
      setcookie("idioma2", $language, $expires , "/", "", false, true);
    }
 }
  
?>

<!DOCTYPE html>
<html lang="en">
    <head>
       <meta charset="UTF-8">
       <title>Cookie</title>
        <link rel="stylesheet" type="text/css" href="css/styles.css" />
    </head>
    <body>
        <form action="<?=htmlspecialchars($_SERVER['PHP_SELF'])?>" method="post">
            <fieldset>
                <legend>Login</legend>
                <label for="user">User:</label>
                <input type="user" id="user" name="user" value="<?=$user?>" />
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" value="<?=$password?>" />
                <label for="language">Language:</label>
                <input type="language" id="language" name="language" value="<?=filter_input(INPUT_COOKIE,'idioma')?>" />
                <input type="submit" name="submit" value="LOGIN" />
            </fieldset>
        </form> 
    </body>
</html>

```
