import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Random;

public class Student {
    private String name;
    private int id;
    private HashMap<String, Double> grades;

    public Student (String n){
        name = n;
        Random random = new Random();
        int id = random.nextInt(1001);  
        grades = new HashMap<>();
    }
    public Student (String n , int nextIds){
        name = n;
        id = nextIds;
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
    public String details(){
       return name + ", " + id + ", " + get_grades();
    }
    public double get_student_average() {
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
        return get_student_average();
    }
    public String get_name(){
        return name;
    }
}
