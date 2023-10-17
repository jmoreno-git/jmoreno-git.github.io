# Classes enniuades, internes i anònimes

**Classe java OuterClass.java**

```java
/**
 * Nested classes exemple.
 * @author Jose
 */
package cat.proven.nestedclasses;

/**
* OuterClass.
* @author Jose
 */
public class OuterClass {
    private String x;
    private static String y;
    private NestedClass nested;

    public OuterClass(String x) {
        this.x = x;
        OuterClass.y = "Outer."+x;
        this.nested = new NestedClass("Nested."+x);
    }

    /**
  * example of variable access.
  * @return information string
     */
    public String show() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nFrom Outer:\n");
        sb.append("Outer {");
        sb.append(String.format("[Outer.x=%s]", this.x));
        sb.append(String.format("[Outer.y=%s]", OuterClass.y));
        sb.append(String.format("[Nested.nx=%s]", nested.nx));  
        //sb.append(String.format("[StaticInner.six=%s]", StaticInnerClass.six)); //static not allowed here
        sb.append(String.format("[StaticInner.siy=%s]", StaticInnerClass.siy));
        sb.append("}");
        sb.append(nested.show());
        return sb.toString();
    }

    /**
  * NestedClass
     */
    private class NestedClass {
        private String nx;
        // private static String ny = "Nested.ny";  illegal static in an inner class.

        public NestedClass(String nx) {
            this.nx = nx;
        }

        /**
    * example of variable access.
    * @return information string
         */
        public String show() {
            StringBuilder sb = new StringBuilder();
            sb.append("\nFrom Nested:\n");
            sb.append("Outer {");
            sb.append(String.format("[Outer.x=%s]", x));
            sb.append(String.format("[Outer.y=%s]", OuterClass.y));
            sb.append(String.format("[Nested.nx=%s]", this.nx));  
            sb.append(String.format("[StaticInner.siy=%s]", StaticInnerClass.siy));
            sb.append("}");
            return sb.toString();
        }

    }

    /**
  * Public nested class. It can be instantiated out of outer class.
     */
    public class PublicNestedClass {
        private String nx;
        // private static String ny = "Nested.ny";  illegal static in an inner class.

        public PublicNestedClass(String nx) {
            this.nx = nx;
        }

        /**
    * example of variable access.
    * @return information string
         */
        public String show() {
            StringBuilder sb = new StringBuilder();
            sb.append("\nFrom PublicNested:\n");
            sb.append("Outer {");
            sb.append(String.format("[Outer.x=%s]", x));
            sb.append(String.format("[Outer.y=%s]", OuterClass.y));
            sb.append(String.format("[PublicNested.nx=%s]", this.nx));  
            sb.append(String.format("[StaticInner.siy=%s]", StaticInnerClass.siy));
            sb.append("}");
            return sb.toString();
        }  

    }

    /**
  * Static inner class
     */
    public static class StaticInnerClass {
        private static String siy = "StaticInner.siy";

        public StaticInnerClass() {
        }

        /**
    * example of variable access.
    * @return information string
         */
        public static String show() {
            StringBuilder sb = new StringBuilder();
            sb.append("\nFrom StaticInner:\n");
            sb.append("Outer {");
            //sb.append(String.format("[Outer.x=%s]", x)); //illegal access a non static variable.
            sb.append(String.format("[Outer.y=%s]", OuterClass.y));  
            sb.append(String.format("[StaticInner.siy=%s]", StaticInnerClass.siy));
            sb.append("}");
            return sb.toString();
        }  

    }

    /**
  * interface to be implemented by anonimous inner classes.
     */
    public interface Greeter {
        String greet(String name);
    }

    /**
  * exemple of anonimous local inner classes implementing an interface.
  * @param name
  * @return
     */
    public String doGreet(String name) {
        /**
    * Anonimous class implementing Greeter interface
         */
        Greeter greeterCA = new Greeter() {
            String greet = "hola";
            @Override
            public String greet(String name) {
                return String.format("%s %s", greet, name);
            }
        };
        /**
    * Anonimous class implementing Greeter interface
         */
        Greeter greeterEN = new Greeter() {
            String greet = "hello";
            @Override
            public String greet(String name) {
                return String.format("%s %s", greet, name);
            }
        };
        return String.format("greeterCA:%s; greeterEN:%s", greeterCA.greet(name), greeterEN.greet(name));
    }

}
```

**Classe Main.java**

```java
package cat.proven.nestedclasses;

/**

* Nested classes exemple. Main class.
* @author Jose
 */
public class Main {

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    private void run() {
        OuterClass outerObj = new OuterClass("x");
        System.out.println(outerObj.show());
        System.out.println(OuterClass.StaticInnerClass.show());
        OuterClass.PublicNestedClass publicNested = outerObj.new PublicNestedClass("Public.x");
        System.out.println(publicNested.show());
        System.out.println("doGreet:"+outerObj.doGreet("Paul"));
    }

}
```
