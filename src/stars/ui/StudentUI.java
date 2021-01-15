package stars.ui;

import stars.controller.StudentController;
import stars.controller.emailController;

import stars.model.Course;
import stars.model.Index;
import stars.users.Student;
import stars.users.User;
import stars.model.RegisteredCourses;
import stars.model.Lesson;
import stars.roster.NTU;
import stars.controller.LoginController;

import java.io.Console;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Represents the User Interface used by a Student.
 * Gets inputs from Student and methods from student controller.
 */
public class StudentUI {
    /**
     * Controller class that is used to execute Student commands.
     */
    private StudentController studentController;

    /**
     * Creates a new StudentUI by passing NTU object as reference
     * @param ntu NTU object
     */
    public StudentUI(NTU ntu){
        studentController = new StudentController(ntu);
    }

    /**
     * Returns a student object reference
     * @param uname Student's username.
     * @param pwd Student's password
     * @return Student object reference base on student's username and password.
     */
    public User getStudent(String uname, String pwd){
        return studentController.getStudent(uname, pwd);
    }

    /**
     * Add a course for a student.
     * @param student Current student.
     */
    public void addCourse(Student student){
        Scanner scan = new Scanner(System.in);
        //prints a list of all courses available
        studentController.printCourseCode();

        // user details
       
        //Checks that the selected course code exist in the database and its no yet registered in student's registered courses.
        int error = 1;
        while (error == 1){
            System.out.println("Please input the Course Code you want to add: ");
            String courseCode = scan.nextLine().trim();
            error = studentController.printCourseIndex(courseCode.toUpperCase(), student);
        }

        String courseIndex = new String();
        int error2  = 1;
        //Checks that the selected course index exist in the database.
        while (error2 == 1){
            System.out.println("Please select the index you want to view: ");
            courseIndex = scan.nextLine().trim();
            error2 = studentController.printAllIndexLessons(courseIndex);
        }
        
        System.out.println("Confirm to add? Yes/No");
        String confirm = scan.nextLine().trim();
        if (confirm.equalsIgnoreCase("Yes")){
            Index index = studentController.selectCourseIndex(courseIndex);
            studentController.addIndex(index, student);
           
        }
        else if (confirm.equalsIgnoreCase("No"))
            System.out.println("Course index not added."); 
        
        else{
            System.out.println("Please only key in either Yes or No.");
        }
    }

    /**
     * Drop a registered course for a student.
     * @param student Current student.
     */
    public void dropCourse(Student student){
        Scanner scan = new Scanner(System.in);
        System.out.println("+-----------------------------------------------------------------------------------------------------------------+");
        System.out.format("|%-12s|%-40s|%12s|%12s|%13s|%18s|\n", "Course Code", "Course Name", "Course Index", "Academic Unit", "Course Type", "Registered Status");
        System.out.println(
                "+-----------------------------------------------------------------------------------------------------------------+");
        
        // Prints all registered courses for the student
        studentController.printRegisteredCourses(student);
        System.out.println(
                "+-----------------------------------------------------------------------------------------------------------------+");

        Index index = new Index();
        index = null;
        // Ensure that the course index keyed in is one that the student has registerd in.
        while (index == null){
            System.out.println("Select the course index you would like to drop from your registered courses: ");
            String courseIndex = scan.nextLine().trim();
            index = studentController.selectCourseIndextoDrop(courseIndex, student);
        }
        
        System.out.println("Confirm to drop? Yes/No: ");
        String confirm = scan.nextLine();
        if (confirm.equalsIgnoreCase("Yes")){          
            studentController.dropIndex(index, student);
            System.out.println("Dropped Successfully!");
        }
    
        else if (confirm.equalsIgnoreCase("No"))
            System.out.println("Course index not dropped."); 
        
        else{
            System.out.println("Please only key in either Yes or No.");
        }
    }

    /**
     * Print all courses that the student is registered in.
     * @param student Current student.
     */
    public void printCoursesRegistered(Student student){
        System.out.println(
                "+-----------------------------------------------------------------------------------------------------------------+");
        System.out.format("|%-12s|%-40s|%12s|%12s|%13s|%18s|\n", "Course Code", "Course Name", "Course Index",
                "Academic Unit", "Course Type", "Registered Status");
        System.out.println(
                "+-----------------------------------------------------------------------------------------------------------------+");
        studentController.printRegisteredCourses(student);
        System.out.println(
                "+-----------------------------------------------------------------------------------------------------------------+");
    }

    /**
     * Prints our the selected index lessons as well as the vacancies and waitlist.
     */
    public void checkVacancies(){
        Scanner scan = new Scanner(System.in);
        studentController.printCourseCode();
       
        //Ensure that the course code keyed in is found in the database.
        int error = 1;
        while (error == 1){
            System.out.println("Please input the Course Code you want to check: ");
            String courseCode = scan.nextLine().trim();
            error = studentController.printAllCourseIndex(courseCode.toUpperCase());
        }

        // Ensure that the course index exist in the database.
        int error2 = 1;
        while (error2 == 1){
            System.out.println("Select the index from above that you would like to check vacancies: ");
            String courseIndex = scan.nextLine().trim();
            error2 = studentController.checkVacancy(courseIndex);
        }
    }

    /**
     * Change a course index to another one of the same course code.
     * @param student Current student.
     */
    public void changeIndex(Student student){
        Scanner scan = new Scanner(System.in);
        // Ensure that the course index keyed in exist in database and is found in the student registered course. 
        int error = 1;
        String currentIndex = new String();
        while (error == 1){
            System.out.println("Key in your current Index number: ");
            currentIndex = scan.nextLine().trim();
            error = studentController.printIndexlessons(currentIndex, student);
        }
        
        int error2 = 1;
        String newIndex = new String();
        // Ensures that the course index keyed in is found in the database.
        while (error2 == 1){
            System.out.println("Key in the Index number you would like to change to: ");
            newIndex = scan.nextLine().trim();
            // Ensures that the course index keyed in is not the same as the current one.
            if (newIndex.equals(currentIndex)){
                System.out.println("You keyed in the same index number. Please try again.");
                error2 = 1;
                continue;
            }
            error2 = studentController.printAllIndexLessons(newIndex);
        }
        
        System.out.println("Confirm to change? Yes/No: ");
        String confirm = scan.nextLine().trim();
        //Ensures that the new Index is of the same course code as the current index.
        if (confirm.equalsIgnoreCase("Yes")){          
            studentController.changeIndex(currentIndex, newIndex, student);
        }
    
        else if (confirm.equalsIgnoreCase("No"))
            System.out.println("Course index not Changed."); 
        
        else{
            System.out.println("Please only key in either Yes or No.");
        }
    }

        
    /**
     * Swap index with another student.
     * @param student1 Current student.
     */
    public void swopIndex(Student student1){
        Scanner scan = new Scanner(System.in);
        Console cnsl = System.console();
        // Ensure that the course index keyed in exist in current student's database and is found in the student registered course. 
        int error = 1;
        String courseIndex1 = new String();
        while (error == 1){
            System.out.println("Key in your current Index number: ");
            courseIndex1 = scan.nextLine().trim();
            error = studentController.printIndexlessons(courseIndex1, student1);
        }   

        //Ensures that the other student username and password matches.
        Student student2 = new Student();
        student2 = null;
        int counter = 0;
        while(student2 == null && counter < 3){
            System.out.println("Peer's Username: ");
            String username = scan.nextLine().toUpperCase().trim();
            char[] password = cnsl.readPassword("Peer's password: ");
            student2 = (Student) getStudent(username, LoginController.generateHashedPassword(String.valueOf(password)));  
            if (student2 == null){
                counter += 1;
                System.out.println("Wrong username or password. Please try again.");
            }
            // After the 3rd wrong attempt of entering the username and password of the student, he/she will be logged out of the system
            // and the opposite party will receive an email to warn him of it.
            if (counter == 3){
                System.out.println("You have keyed in the wrong username or password 3 times. You will be logged out of the system.");
                student2 = (Student) studentController.getReceiveMailStudent(username);
                if (student2 != null){
                    System.out.println("Email notification has been sent to peer to inform about your attempted log in swop!");
                    emailController.securityNotification(student2.getEmail(), student2.getName(),student1.getName());
                }
                System.exit(0);
            }
        }

        // Ensure that the course index keyed in exist in opposing student's database and is found in the student registered course. 
        int error2 = 1;
        String courseIndex2 = new String();
        while (error2 == 1){
            System.out.println("Key in your Peer's Index number: ");
            courseIndex2 = scan.nextLine().trim();
            if (courseIndex2.equals(courseIndex1)){
                System.out.println("You keyed in the same index number. Please try again.");
                error2 = 1;
                continue;
            }
            error2 = studentController.printIndexlessons(courseIndex2, student2);
        }   
        System.out.println("Confirm to change? Yes/No: ");
        String confirm = scan.nextLine().trim();
        if (confirm.equalsIgnoreCase("Yes")){          
            studentController.swapIndex(student1, student2, courseIndex1, courseIndex2);
        }
        else if (confirm.equalsIgnoreCase("No"))
            System.out.println("Course index not Changed."); 
        
        else{
            System.out.println("Please only key in either Yes or No.");
        }

    }

}
