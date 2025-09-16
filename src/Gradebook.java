import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Gradebook {
    private static ArrayList<Courses> courses = new ArrayList<Courses>(); 
    private static ArrayList<Student> students = new ArrayList<Student>(); 
        public static void loadGrades(Student s, String filepath){
            filepath = filepath.replaceAll("[}]", "");
            filepath = filepath.replaceAll("[{]", "");
            //Calc 3=12 , Science 3
            String[] parts = filepath.split(",");
                for (String p: parts)  {
                    String[] part = p.split("=");
                    if (part.length == 2){
                        String course_name = part[0].toLowerCase().trim();
                        double grade = Double.parseDouble(part[1].trim());
                        course_find(course_name).add_student(s);
                        s.add_grades(course_name,grade);
                    } 
                }
        }

        public static void loadCourses(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = br.readLine()) != null) {
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

    public static void loadStudents(String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(",", 3);

            if (parts.length >= 2) {
                String name = parts[0].trim();
                int id = Integer.parseInt(parts[1].trim());

                Student student = new Student(name, id);
                students.add(student);
                if (parts.length == 3) {
                    loadGrades(student, parts[2].trim());
                } else {
                    System.out.println("Student has no grades");
                }
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
        public static Courses course_find(String name){
        for(Courses c: courses){
            if(c.get_name().toLowerCase().equals(name.toLowerCase())){
                return c;
            }
        }
        return null;
    }
    public static Student student_find(String name){
        for(Student c: students){
            if(c.get_name().toLowerCase().equals(name.toLowerCase())){
                return c;
            }
        }
        return null;
    }
    public static Student student_find(int id){
        for(Student c: students){
            if(c.get_id()==id){
                return c;
            }
        }
        return null;
    }
    public static void writeStudents(){
        BufferedWriter writer = null;
        try {
        writer =  new BufferedWriter(new FileWriter("data/grades.csv"));
            for(Student c: students){
                writer.write(c.details());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("An error occurred while working with the file.");
        }
    }
    public static void writeCourses(){
        BufferedWriter writer = null;
                try {
            writer =  new BufferedWriter(new FileWriter("data/courses.csv"));
            for(Courses c: courses){
                writer.write(c.get_course());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("An error occurred while working with the file.");
        }
    }
    public static double get_course_averages(int id){
        for(Courses s: courses){
            if(id == s.get_id()){
                return s.get_course_average();
            }
        }
        return 403.0;
    }

    public static boolean check_course(Student s, String n){
        for (String key : s.get_grades().keySet()) {
            if (key.contains(n)) {
                System.out.println("Match found: " + key);
                return true;
            }
        }
        return false;
    }

    public static void add_student(String name){
        Student s = new Student(name);
        students.add(s);
}
    public static void add_course(String name){
        Courses c = new Courses(name);
        courses.add(c);
    }
    public static double get_average(Student name){
        return name.get_average();
    }

}