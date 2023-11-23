# Patró Factory

En el patró Factory, creem l'objecte sense exposar la lògica de creació al client i es refereix al nou objecte usant una interfície comuna.

![factory_pattern_uml_diagram.jpg](/damm06/assets/0.1/factory_pattern_uml_diagram.jpg)

**Shape.java**
```java
public interface Shape {
   void draw();
}
```

**Square.java**
```java
public class Square implements Shape {
   @Override
   public void draw() {
      System.out.println("Square.draw() method.");
   }
}
```

**Circle.java**
```java
public class Circle implements Shape {
   @Override
   public void draw() {
      System.out.println("Circle.draw() method.");
   }
}
```

**Rectangle.java**
```java
public class Rectangleimplements Shape {
   @Override
   public void draw() {
      System.out.println("Rectangle.draw() method.");
   }
}
```

**ShapeFactory.java**
```java
public class ShapeFactory {
   //use getShape method to get object of type shape
   public Shape getShape(String shapeType){
      Shape instance = null;
      if(shapeType == null){
         instance = null;
      }  
      if(shapeType.equalsIgnoreCase("CIRCLE")){
         instance = new Circle();

      } else if(shapeType.equalsIgnoreCase("RECTANGLE")){
         instance = new Rectangle();
         
      } else if(shapeType.equalsIgnoreCase("SQUARE")){
         instance = new Square();
      }
      return instance;
   }
}
```

**FactoryPatternDemo.java**
```java
public class FactoryPatternDemo {
   public static void main(String[] args) {
      ShapeFactory shapeFactory = new ShapeFactory();
      //get an object of Circle and call its draw method.
      Shape shape1 = shapeFactory.getShape("CIRCLE");
      //call draw method of Circle
      shape1.draw();
      //get an object of Rectangle and call its draw method.
      Shape shape2 = shapeFactory.getShape("RECTANGLE");
      //call draw method of Rectangle
      shape2.draw();
      //get an object of Square and call its draw method.
      Shape shape3 = shapeFactory.getShape("SQUARE");
      //call draw method of circle
      shape3.draw();
   }
}
```
