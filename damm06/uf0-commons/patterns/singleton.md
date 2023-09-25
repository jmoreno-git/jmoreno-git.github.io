# Singleton pattern

Aquest patró permet definir una classe de la qual només se'n podrà instanciar un únic objecte.

Proporciona un mètode estàtic per accedir a l'objecte, prèvia instanciació si encara no s'havia fet amb anterioritat.

El constructor es definex privat perquè no es pugui usar fora de la classe.

[Singleton pattern](https://en.wikipedia.org/wiki/Singleton_pattern)

**Singleton.java**
```java
public class Singleton {
   // we have a static variable to hold our one instance of the class Singleton, we usually call it "instance" or "uniqueInstance"
     private static Singleton instance;

   // other useful instance variables here

   //our constructor is declared private; only Singleton can instantiate this class !
     private Singleton(){}

     public static Singleton getInstance(){
      if (instance == null) {
           instance = new Singleton();
      }
      return instance;
   }
   // other useful methods here
   public void showMessage(){
      System.out.println("Singleton  Object Message!");
   }
}
```

**SingletonDemo.java**
```java
public class SingletonDemo {
   public static void main(String[] args) {
      //Get the instance
      Singleton object = Singleton.getInstance();
      //show the message
      object.showMessage();
   }
}
```
