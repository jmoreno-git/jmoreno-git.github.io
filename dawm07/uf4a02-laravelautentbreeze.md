# Autenticació en Laravel amb Breeze

Utilitzem com a plantilla [Starter kit: Breeze](https://laravel.com/docs/9.x/starter-kits#laravel-breeze)

Creem un projecte nou

```
laravel new authtest
cd authteset
```

Creem una base de dades authtest i configurem l'accés a la base de dades al fitxer *env* del projecte.

Instal·lem Laravel/Breeze

```
composer require laravel/breeze --dev
```

Executem l'instal·lador del suport de Breeze per crear les vistes, rutes i controladors d'autenticació.

```
php artisan breeze:install
```

Construir l'aplicació (*build*)

```
npm install
npm run dev
```

Migrar les dades per crear les taules requerides per a autenticació a la base de dades

```
php artisan migrate
```

Totes les rutes de *breeze* estan definides a **routes/auth.php**.

Ja es pot executar el projecte. Permet registrar nous usuaris, fer login i logout.
