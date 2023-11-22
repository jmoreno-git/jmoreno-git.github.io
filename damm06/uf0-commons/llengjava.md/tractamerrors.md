# Tractament d'errors

Ús de **logger** per enviar errors a un fitxer (veure [logger](logger.md)).

Utilització de **enum** per a codificar errors:

```java
public enum DbResultCode {
    /**
     * result codes: code and value.
     */
    OK  (0),
    DB_CONN_FAIL (-1),
    BAD_QUERY (-2),
    INTEG_VIOLATION (-3),
    NOT_FOUND (-4);

    private final int code;
 
    DbResultCode(int code) {
        this.code = code;
    }
 
    public int code() { return code; }
}
```

Ús d'excepcions específiques per comunicar errors amb un codi:

```java
public class ResultCodeException extends Exception {
    private int resultCode;

    public ResultCodeException(String message, int resultCode) {
        super(message);
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

}

```

Si es prefereix no haver de registrar l'excepció (declaració a tots els mètodes pel que passen sense ser capturada) es pot extendre de ***RuntimeException*** en comptes de ***Exception***.

Exemple de mètode per generar missatges a partir de codis de resultat d'una acció:

```java
public String getMessage(int code) {
    String msg = null;
    switch (code) {
        case 0:
            msg = "action completed successfully";
            break;
        case -1:
            msg = "fail to connect to database";
            break;
        case -2:
            msg = "the query to the database has some problem";
            break;
        case -3:
            msg = "Some restriction in database has prohibited query";
            break;
        case -4:
            msg = "That object has not been found in database";
            break;
        default:
            msg = "unexpected error";
            break;
    }
    return msg;
}
```

Exemple de com capturar l'excepció específica i informar a l'usuari.

```java
public void testRetrieveAllCategories() {
    try {
        //test retrieve all categories
        System.out.println("Retrieve all categories");
        List<Category> allCategories = categoryDao.selectAll();
        printList(allCategories);
    } catch (ResultCodeException ex) {
        //Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println(getMessage(ex.getResultCode()));
    }
}
```
