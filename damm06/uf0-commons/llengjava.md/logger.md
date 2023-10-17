# Logger

A continuació s'exemplifica com definir un Logger que redireccioni la sortida a fitxers (amb un màxim de 3) i límit de tamany especificat, així com la manera d'evitar heretar els handler pares.

```java
private Logger configLogger() {
    //file path to log.
    logfile = "resources/log.txt";
    //create and configure (if necessary) the message logger.
    Logger logger = Logger.getLogger(this.getClass().getName());
    //set to false to avoid inheritance of parent handlers.
    logger.setUseParentHandlers(false);
    try {
        FileHandler handler;
        int limit = 1000000;
        int maxFiles = 3;
        handler = new FileHandler(logfile, limit, maxFiles, true);
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
    } catch (FileNotFoundException ex) {
        logger.setUseParentHandlers(true);
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    } catch (IOException | SecurityException ex) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }
    return logger;
}
```
