import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Student {
    private String name;
    private static int nextId = 0;
    private int id;
    private HashMap<String, Double> grades;

    public Student (String n){
        name = n;
        id = nextId;
        nextId++;
        grades = new HashMap<>();
    }

    public int get_id(){
        return id;
    }
    
    public HashMap get_grades(){
        return grades;
    }
    public Double get_grades(String course){
        return grades.get(course);
    }
    public void add_grades(String name, Double updated_grade){
        grades.put(name, updated_grade);
    }
    public void details(){
        System.out.println("Name: " + name);
        System.out.println("Id: " + id);
        System.out.println("Grades: " + get_grades());
    }
    public double average_grades() {
        double sum = 0.0;
        int num = 0;
        for (Double g : grades.values()) {
            sum += g;
            num++;
        }
        if(num != 0){
            return sum/num;
        }
        return 0.0;
    }
    public double get_average(){
        return average_grades();
    }
}
