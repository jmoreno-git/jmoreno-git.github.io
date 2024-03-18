# Exemple d'ordenació de llistes

En aquest exemple s'il·lustra l'ordenació de llistes implementant els interfaces **Comparable** i **Comparator**.

Es defineix una classe **Group** amb un mètode de comparació natural segons el nom.

```java
public class Group implements Comparable {
    private String name;
    private List<Student> students;

    public Group(String name) {
        this.name = name;
        this.students = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Group other = (Group) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Group{");
        sb.append("name=").append(name);
        sb.append(", students=").append(students);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        Group other = (Group) o;
        return name.compareTo(other.name);
    }

    public void addStudent(Student student) {
        students.add(student);
    }
    
}
```

El mètode *compareTo()* defineix el mecanisme de comparació natural d'objectes *Group*.

La classe **Student** es defineix de la següent manera:

```java
public class Student implements Comparable {
    
    private String name;
    private String surname;
    private List<Integer> grades;

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public void setGrades(List<Integer> grades) {
        this.grades = grades;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.surname);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.surname, other.surname);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student{");
        sb.append("name=").append(name);
        sb.append(", surname=").append(surname);
        sb.append(", grades=").append(grades);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        int result = 0;
        Student other = (Student) o;
        result = this.surname.compareTo(other.surname);
        if (result == 0) {  //surname equals
            result = this.name.compareTo(other.name);
        }
        return result;
    }
    
    public double getMeanGrade() {
        double mean = 0.0;
        if (!grades.isEmpty()) {
            for (Integer grade : grades) {
               mean += grade;
            }
        mean /= grades.size();           
        }
        return mean;
    }
    
    public int getMaxGrade() {
        int max = 0;
//        if (!grades.isEmpty()) {
//            max = grades.get(0);
//            for (int i = 1; i < grades.size(); i++) {
//                max = Math.max(max, grades.get(i));
//            }
//        }
        max = Collections.max(grades); //get max value using Collections class.
        return max;
    }    
}
```

Els *Student* es comparen de forma natural amb la combinació del cognom i el nom (*surname*, *name*).

Hi ha mètodes per obtenir la nota mitjana i la nota més alta.

Definir ara també els comparadors per a criteris de comparació de Student no naturals.

```java
public class StudentComparatorByMaxGrade implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        return o1.getMaxGrade() - o2.getMaxGrade();
    }
    
}
```

```java
public class StudentComparatorByMeanGrade implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        return (int) Math.signum(o1.getMeanGrade() - o2.getMeanGrade());
    }
    
}
```

I ja podem crear una classe principal per provar l'ordenació amb els diferents criteris.

```java
public class SchoolMain {
    
    private List<Group> groups;

    public SchoolMain() {
        this.groups = new ArrayList<>();
        generateGroups();
    }

    public static void main(String[] args) {
        SchoolMain sm = new SchoolMain();
        sm.init();
    }

    private void init() {
        groups.sort(null);
        System.out.println("Groups: "+groups);
        List<Student> dam1List = getStudentsByGroupName("DAM1");
        System.out.println("Students in DAM1 sorted by natural criteria");
        dam1List.sort(null);
        System.out.println(dam1List);
        //
        List<Student> dam1ListSorted1 = new ArrayList<>(getStudentsByGroupName("DAM1"));
        System.out.println("Students in DAM1 sorted by mean grade");
        dam1ListSorted1.sort(new StudentComparatorByMeanGrade());
        System.out.println(dam1ListSorted1);
        for (Student st : dam1ListSorted1) {
            System.out.printf("%s %s: %.2f%n", 
                    st.getName(), st.getSurname(), st.getMeanGrade());
        }
        //
        List<Student> dam1ListSorted2 = new ArrayList<>(getStudentsByGroupName("DAM1"));
        System.out.println("Students in DAM1 sorted by max grade");
        dam1ListSorted2.sort(new StudentComparatorByMaxGrade());
        System.out.println(dam1ListSorted2);
        for (Student st : dam1ListSorted2) {
            System.out.printf("%s %s: %d%n", 
                    st.getName(), st.getSurname(), st.getMaxGrade());
        }
    }

    private void generateGroups() {
        Group g;
        Student s;
        //
        g = new Group("DAW1");
            //TODO add students
        groups.add(g);
        //
        g = new Group("DAM1");
            //
            s = new Student("Joseph", "Morgan");
                s.getGrades().add(5);
                s.getGrades().add(6);
                s.getGrades().add(3);
                s.getGrades().add(8);
                s.getGrades().add(1);
            g.addStudent(s);
            //
            s = new Student("Albert", "Morgan");
                s.getGrades().add(9);
                s.getGrades().add(7);
                s.getGrades().add(8);
                s.getGrades().add(10);
                s.getGrades().add(7);
            g.addStudent(s);
            //
            s = new Student("Zoe", "Lopez");
                s.getGrades().add(2);
                s.getGrades().add(5);
                s.getGrades().add(6);
                s.getGrades().add(9);
                s.getGrades().add(3);
            g.addStudent(s);
        //
        groups.add(g);

    }
    
    public List<Student> getStudentsByGroupName(String grName) {
        List<Student> list = null;
        for (Group g : groups) {
            if (g.getName().equals(grName)) {
                list = g.getStudents();
                break;
            }
        }
        return list;
    }
}

```

