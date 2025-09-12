import java.util.ArrayList;
import java.util.List;

public class Courses {
    private String course_name;
    private int course_id;
    private ArrayList<Student> students;

   public Courses(String name, int id){
    course_name = name;
    course_id = id;
    students = new ArrayList<Student>();
   }
   public String get_course(){
        return course_name + ", " + course_id;
   }
   public double get_course_average(){
    double sum = 0.0;
    int num = 0;
        for(Student s: students){
            sum += s.get_grades(this.course_name);
            num++;
        }
    if(num != 0){
        return sum/num;
    }
    return 0.0;
   }
   public void add_student(Student s){
    students.add(s);
   }
   public String get_name(){
    return course_name;
   }
   public int get_id(){
    return course_id;
   }
}
