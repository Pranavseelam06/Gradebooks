import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class Main {
    private static ArrayList<Courses> courses = new ArrayList<Courses>(); 

        public static void loadCourses(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                // Split by comma
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    int id = Integer.parseInt(parts[1].trim());
                    
                    Courses course = new Courses(name, id);
                    courses.add(course);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Courses course_find(int id){
        for(Courses c: courses){
            if(c.get_id()== id){
                return c;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        loadCourses("/Users/Pranavseelam/Documents/gradebook-java/courses.csv");
        BufferedWriter writer = null;

        Student pranav = new Student("Pranav");
        Student brandon = new Student("Brandon");
        Courses r = new Courses("Science",12345);
        courses.add(r);
        course_find(21345).add_student(pranav);
        try {
            writer =  new BufferedWriter(new FileWriter("/Users/Pranavseelam/Documents/gradebook-java/courses.csv"));
            for(Courses c: courses){
                writer.write(c.get_course());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("An error occurred while working with the file.");
        }
        try {
            writer =  new BufferedWriter(new FileWriter("/Users/Pranavseelam/Documents/gradebook-java/grades.csv", true));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            System.err.println("An error occurred while working with the file.");
        }
    }
}