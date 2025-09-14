import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;




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
        writer =  new BufferedWriter(new FileWriter("grades.csv"));
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
            writer =  new BufferedWriter(new FileWriter("courses.csv"));
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


    public static void main(String[] args) {
    loadCourses("courses.csv");
    loadStudents("grades.csv");
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while(running != false){
            System.out.println("\n--- Gradebook Menu ---");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Assign Grade");
            System.out.println("4. View Student Average");
            System.out.println("5. View Course Average");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            boolean correct_number = false;
            int option = 0;
            while(!correct_number){
                if(scanner.hasNextInt()){
                        option = scanner.nextInt();
                        scanner.nextLine();   
                        correct_number = true;
                } else {
                    System.out.println("User did not enter a number");
                    scanner.nextLine();
                }
            }
            
            switch(option){
                case 1 : 
                    System.out.println("Enter Student name");
                    String name = scanner.nextLine();
                    if(student_find(name.toLowerCase())!= null){
                        System.out.println("Student Already Exists");
                        break;
                    }
                    if(name.isBlank()){
                        System.out.println("Name is Blank, Please try again");
                        break;
                    }
                    Student s = new Student(name);
                    students.add(s);
                    System.out.println("Student Added");
                    break;
                case 2 : 
                    System.out.println("Enter Course( Name ID )");
                    String course_name = scanner.next();
                    boolean correct_number_two = false;
                    int course_id = 0;
                    while(!correct_number_two){
                        if(scanner.hasNextInt()){
                                course_id = scanner.nextInt();
                                scanner.nextLine();   
                                correct_number_two = true;
                        } else {
                            System.out.println("User did not enter a number");
                            scanner.nextLine();
                        }
                    }
                    if(course_find(course_name)!= null){
                        System.out.println("Course Already Exists with Name ");
                        break;
                    }
                    if(course_find(course_id)!= null){
                        System.out.println("Course Already Exists with Id ");
                        break;
                    }
                    if(course_name.isBlank()){
                        System.out.println("Name is Blank, Please try again");
                        break;
                    }
                    Courses c = new Courses (course_name, course_id);
                    courses.add(c);
                    System.out.println("Course Added");
                    break;
                case 3 : 
                    System.out.println("Enter Student name, Course Name");
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    String name_one;
                    double grade_one = 0;
                    String course_one;
                    boolean correct_number_three = false;
                    if (parts.length < 2) {
                        System.out.println("Invalid input format. Example: Name, Course");
                        break;
                    } else {
                    name_one = parts[0].trim();
                    course_one = parts[1].trim();
                    if(student_find(name_one)==null){
                        System.out.println("Student Not Found");
                        break;
                    }
                    if(course_find(course_one)==null){
                        System.out.println("Course Not Found");
                        break;
                    }
                    }
                    System.out.print("Enter Grade " + "\n");
                    while(!correct_number_three){
                        if(scanner.hasNextDouble()){
                                grade_one = scanner.nextDouble();
                                scanner.nextLine();   
                                correct_number_three = true;
                        } else {
                            System.out.println("User did not enter a Grade(Ex (92.0))");
                            scanner.nextLine();
                        }
                    }
                    if(check_course(student_find(name_one), course_one)!=true){
                        course_find(course_one).add_student(student_find(name_one));
                    }
                    student_find(name_one).add_grades(course_one, grade_one);
                    System.out.println("Added Grades");
                    break;
                case 4:
                    System.out.println("Enter Student Name: ");
                    String name_two = scanner.nextLine();
                    if(student_find(name_two)==null){
                        System.out.println("Student Not Found");
                        break;
                    }
                    double student_average = student_find(name_two.toLowerCase()).get_student_average();
                    System.out.println("Student Average: " + student_average);
                    break;
                case 5:
                    System.out.println("Enter course Name: ");
                    String course_two = scanner.nextLine();
                    if(course_find(course_two)==null){
                        System.out.println("Course Not Found");
                        break; 
                    }
                    double course_average = course_find(course_two.toLowerCase()).get_course_average();
                    System.out.println("Course Average: " + course_average);
                    break;
                case 6:
                    System.out.println("Thank You for using GradeBook");
                    running = false;
                    break;
                default:
                    System.out.println("Integer is not part of options");
            }

        }
        scanner.close();

        //END CODE
        writeCourses();
        writeStudents();
    }
}