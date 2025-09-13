import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class Main {
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
                        String course_name = part[0].trim();
                        double grade = Double.parseDouble(part[1].trim());
                        course_find(course_name).add_student(s);
                        s.add_grades(course_name,grade);
                    } else {
                    System.out.println("No Grades visibile");
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

            // Split into 3 parts: name, id, and the rest (grades)
            String[] parts = line.split(",", 3);

            if (parts.length >= 2) {
                String name = parts[0].trim();
                int id = Integer.parseInt(parts[1].trim());

                Student student = new Student(name, id);
                students.add(student);
                if (parts.length == 3) {
                    loadGrades(student, parts[2].trim());
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
            if(c.get_name().equals(name)){
                return c;
            }
        }
        return null;
    }
    public static Student student_find(String name){
        for(Student c: students){
            if(c.get_name().equals(name)){
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
        writer =  new BufferedWriter(new FileWriter("/Users/Pranavseelam/Documents/gradebook-java/grades.csv"));
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
            writer =  new BufferedWriter(new FileWriter("/Users/Pranavseelam/Documents/gradebook-java/courses.csv"));
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




    public static void main(String[] args) {
        loadCourses("/Users/Pranavseelam/Documents/gradebook-java/courses.csv");
        loadStudents("/Users/Pranavseelam/Documents/gradebook-java/grades.csv");

        System.out.println(get_course_averages(21345));
        System.out.println(student_find(234).get_student_average());




        //END CODE
        writeCourses();
        writeStudents();
    }
}