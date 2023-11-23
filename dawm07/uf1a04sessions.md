# Sessions

Les [sessions](https://www.php.net/manual/en/features.sessions.php) s'utilitzen per preservar dades entre successives peticions del mateix client.

Cada sessió té associat un identificador únic i un espai de memòria per a emmagatzemar variables de sessió. Aquestes variables són accessibles en PHP amb l'array associatiu superglobal [$\_SESSION](https://www.php.net/manual/en/reserved.variables.session).

Segons si el navegador del client accepta o no cookies, el servidor enviarà l'identificador de sessió amb la pàgina de resposta en forma de cookie o amb la url.

## Iniciar una sessió

``` php
<?php
// start a session.
session_start();
// access, if necessari, to session id.
echo session_id();
```

``` php
<?php
// set session id, if needed.
session_id(YOUR_SESSION_ID);
// start a session.
session_start();
?>

```

## Crear/Eliminar variables de sessió

Per crear una variable de sessió, només cal assignar-li el valor amb el nom corresponent a l'array associatiu $\_SESSION. Per eliminar-la, cal usar la funció [unset()](https://www.php.net/manual/en/function.unset.php).

``` php
<?php
// start a session.
session_start();
// initialize a session variable.
$_SESSION['username'] = 'peter';
// unset a session variable
unset($_SESSION['username']);
```

## Destruir una sessió

Per destruir una sessió s'utilitza la funció
[session\_destroy()](http://www.php.net/manual/es/function.session-destroy.php). A més, cal eliminar l'array S\_SESSION i, cas que l'id de la sessió es desi en forma de cookie al client, també cal fer que s'esborri aquesta cookie.

``` php
<?php
// start a session
session_start();
// destroy everything in this session
unset($_SESSION);
if (ini_get("session.use_cookies")) {
    $params = session_get_cookie_params();
    setcookie(session_name(), '', time() - 42000, $params["path"], $params["domain"], $params["secure"],$params["httponly"]);
}
session_destroy();
?>
```
