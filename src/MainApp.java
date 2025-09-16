import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        Gradebook.loadCourses("data/courses.csv");
        Gradebook.loadStudents("data/grades.csv");

        Button addStudent = new Button("Add Student");
        Button addGrades = new Button("Add Grades ");
        Button addCourse = new Button("Add Course ");
        Button studentAverage = new Button("Student Average ");
        Button courseAverage = new Button("Course Average ");
        //Here
        VBox root = new VBox(10); // spacing 10 pixels
        root.getChildren().addAll( 
        addStudent, addCourse, addGrades,
        studentAverage, courseAverage);

        // Scene
        Scene mainScene = new Scene(root, 500, 300);
        stage.setScene(mainScene);
        stage.setTitle("Gradebook");
        stage.show();
        //Here

        TextField nameField = new TextField();
        TextField courseField = new TextField();
        TextField gradeField = new TextField();
        Label message = new Label();

        nameField.setPromptText("Enter Student Name"); //this is for placeholder
        courseField.setPromptText("Enter Course Name"); //this is for placeholder
        gradeField.setPromptText("Enter Grade"); //this is for placeholder

        addStudent.setOnAction (e -> {

            VBox rootOne = new VBox(10);
            stage.setTitle("Add Student");
            //Correct
            Button confirm = new Button("confirm");
            confirm.setOnAction ( f -> {
                String name = nameField.getText();
                if (Gradebook.student_find(name.toLowerCase()) != null) {
                    message.setText("Student Already Exists");
                } else if (name.isBlank()) {
                    message.setText("Name is Blank, Please try again");
                } else {
                    Gradebook.add_student(name);
                    message.setText("Student Added: " + name);
                nameField.setText("");
            }
            });


            Button backButton = new Button("Back");
            backButton.setOnAction(g -> {
            message.setText("");
            stage.setScene(mainScene); // return to main
            stage.setTitle("Main Menu");
             });

        rootOne.getChildren().addAll(nameField, confirm, backButton, message);

        Scene scene = new Scene(rootOne, 300, 200);
        stage.setTitle("Add Student");
        stage.setScene(scene);
        stage.show();
        });

        addCourse.setOnAction(e -> {

            VBox rootOne = new VBox(10);
            stage.setTitle("Add Course");
            Button confirm = new Button("confirm");
            confirm.setOnAction(f -> {
                String course = courseField.getText();
                if (Gradebook.course_find(course.toLowerCase()) != null) {
                    message.setText("Course Already Exists");
                } else if (course.isBlank()) {
                    message.setText("Name is Blank Please try again");
                } else {
                    Gradebook.add_course(course);
                    message.setText("Course Added: " + course);
                }
                courseField.setText("");
            });
                Button backButton = new Button("Back");
                backButton.setOnAction(g -> {
                message.setText("");
                stage.setScene(mainScene); // return to main
                stage.setTitle("Main Menu");
            });
            rootOne.getChildren().addAll(courseField, confirm, backButton, message);

            Scene scene = new Scene(rootOne, 300, 200);
            stage.setTitle("Add Course");
            stage.setScene(scene);
            stage.show();
        });
        addGrades.setOnAction(e -> {
            VBox rootOne = new VBox(10);
            stage.setTitle("Add Grade");

            //Correct
            Button confirm = new Button("Confirm");
            confirm.setOnAction(f -> {
            String name = nameField.getText();
            String course = courseField.getText();
            String grade = gradeField.getText();
            if(course.isBlank() || name.isBlank() || grade.isBlank()){
                message.setText("please fill all fields");
                return;
            }
            double grade_updated = 0.0;
            try {
                grade_updated = Double.parseDouble(grade);
                
            } catch (NumberFormatException g) {
               message.setText("Invalid grade. Enter a number.");
               return;
            }
            Student student = Gradebook.student_find(name);
            Courses course_check = Gradebook.course_find(course);
             if(student==null){
                message.setText("Student Not Found");
                return;
             }
             if(course_check==null){
                message.setText("Course Not Found");
                return;
             }
            if(Gradebook.check_course(student, course)!=true){
                course_check.add_student(student);
            }
            student.add_grades(course, grade_updated);   
            message.setText("Grades Updated: " + name + ", " + grade_updated);  
            nameField.setText("");
            courseField.setText("");
            gradeField.setText("");
        });
            Button backButton = new Button("Back");
                backButton.setOnAction(g -> {
                message.setText("");
                stage.setScene(mainScene); // return to main
                stage.setTitle("Main Menu");
            });
            rootOne.getChildren().addAll(nameField, courseField, gradeField, confirm, backButton, message);

            Scene scene = new Scene(rootOne, 300, 200);
            stage.setTitle("Add Grade");
            stage.setScene(scene);
            stage.show();
        });
        
        studentAverage.setOnAction(e -> {
            VBox rootOne = new VBox(10);
            stage.setTitle("Add Grade");

            //Correct
            Button confirm = new Button("Confirm");
            confirm.setOnAction(f -> {
            String name = nameField.getText();
            if (Gradebook.student_find(name.toLowerCase()) == null) {
                message.setText("Student Doesnt Exists");
            } else if (name.isBlank()) {
                message.setText("Name is Blank Please try again");
            } else {
                double average = Gradebook.get_average(Gradebook.student_find(name.toLowerCase()));
                message.setText("Student Average: " + average);
            }
            nameField.setText("");
             });
            Button backButton = new Button("Back");
                backButton.setOnAction(g -> {
                message.setText("");
                stage.setScene(mainScene); // return to main
                stage.setTitle("Main Menu");
            });

            rootOne.getChildren().addAll(nameField, confirm, backButton, message);

            Scene scene = new Scene(rootOne, 300, 200);
            stage.setTitle("Student Average");
            stage.setScene(scene);
            stage.show();
        });
        
        courseAverage.setOnAction(e -> {
             VBox rootOne = new VBox(10);
            stage.setTitle("Add Grade");

            //Correct
            Button confirm = new Button("Confirm");
            confirm.setOnAction(f -> {
            String course = courseField.getText();
            if (Gradebook.course_find(course.toLowerCase()) == null) {
                message.setText("Course Doesnt Exists");
            } else if (course.isBlank()) {
                message.setText("Name is Blank Please try again");
            } else {
                double average = Gradebook.get_course_averages(Gradebook.course_find(course.toLowerCase()).get_id());
                message.setText("Course Average: " + average);
            }
            courseField.setText("");
            });
                        Button backButton = new Button("Back");
                backButton.setOnAction(g -> {
                message.setText("");
                stage.setScene(mainScene); // return to main
                stage.setTitle("Main Menu");
            });

            rootOne.getChildren().addAll(courseField, confirm, backButton, message);

            Scene scene = new Scene(rootOne, 300, 200);
            stage.setTitle("Course Average");
            stage.setScene(scene);
            stage.show();
        });     


        stage.setOnCloseRequest(e -> {
            Gradebook.writeCourses();
            Gradebook.writeStudents();
        });
    }

    public static void main(String[] args) {
        launch(args); 
    }
}
