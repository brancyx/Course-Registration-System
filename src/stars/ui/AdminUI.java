package stars.ui;

import stars.controller.AdminController;
import stars.model.*;
import stars.roster.ProgrammeCode;
import stars.roster.SchoolCode;
import stars.users.Student;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents the User Interface used by an Admin.
 * Gets inputs from Admin.
 */

public class AdminUI {
    /**
     * Controller class that is used to execute Admin commands.
     */
    private AdminController adminController;

    /**
     * Creates a new AdminUI.
     */
    public AdminUI(){
        adminController = new AdminController();
    }

    /**
     * Update student's respective school e.g SCSE/NBS/CEE/SSM STARS access period.
     */
    // update Access Period
    public void updateAccessPeriod(){
        Scanner scan = new Scanner(System.in);

        boolean schFound = false;
        String sch = null;
        // Identify which School Access period to change
        while(!schFound) {
            System.out.println("Please input School to update:");
            sch = scan.nextLine().toUpperCase().trim();
            for (SchoolCode schoolCode : SchoolCode.values()) {
                if (schoolCode.name().equalsIgnoreCase(sch)) {
                    schFound = true;
                    break;
                }
            }
            if (!schFound) {
                System.out.println("School does not exist! Please retry again!");
            }
        }

        // Show current Access Period
        System.out.println("Current " + sch + " Access Period");
        adminController.printAccessPeriod(sch);

        // Get new Access Period time
        LocalDateTime as = null;
        LocalDateTime ae = null;
        boolean incomplete = true;
        while (incomplete) {
            try {
                // Get new Access Start Period
                System.out.println("Please input new Start Access Period in Date Time format e.g 2020-11-31T09:00");
                String accessStart = scan.nextLine().toUpperCase().trim();
                as = LocalDateTime.parse(accessStart);

                // Get new Access End Period
                System.out.println("Please input new End Access Period in Date Time format e.g 2020-11-31T09:00");
                String accessEnd = scan.nextLine().toUpperCase().trim();
                ae = LocalDateTime.parse(accessEnd);

                // Check Time Period logic error
                if (ae.isBefore(as) || ae.equals(as)){
                    System.out.println("Error! AccessEnd cannot be earlier or same as AccessStart! Please try again!");
                    continue;
                }
                incomplete = false;
            } catch (DateTimeParseException e) {
                System.out.println("Error in Date Time format/logic! Please try again!");
            }
        }
        adminController.updateAccessPeriod(sch,as,ae);
    }

    /**
     * Adds new Student.
     */
    // Add new Student
    public void addStudent(){
        Scanner scan = new Scanner(System.in);
        boolean incomplete = true;

        // Get input details
        while (incomplete) {
            // User details
            // Get Student Name
            System.out.println("Please input the Student Name:");
            String name = scan.nextLine().toUpperCase().trim();

            // Get Username
            System.out.println("Please input the Username:");
            String userName = scan.nextLine().toUpperCase().trim();

            // Get password
            System.out.println("Please input registered password (Case sensitive):");
            String password = scan.nextLine();

            // Student account type
            char account = 'S';

            // Get gender
            System.out.println("Please input gender (Male/Female)?:");
            String gender = scan.nextLine().toUpperCase().trim();
            if (!(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"))){
                System.out.println("Error in gender! Please type in Male/Female only! Please try adding student again!");
                continue;
            }

            // Student details
            // Get Matric number
            System.out.println("Please input the matric number:");
            String matric = scan.nextLine().toUpperCase().trim();
            if (matric.length() != 9 ){
                System.out.println("Error! Matric number must be 9 characters! Please try adding student again!");
                continue;
            }else{
                // Check if Student matric already exist
                boolean studentExist = false;
                for (Student student : adminController.getAllStudents()){
                    if (student.getMatricNum().equalsIgnoreCase(matric)){
                        System.out.println("Error unable to add Student! Student already exist! Please try again!");
                        studentExist = true;
                        break;
                    }
                }
                if (studentExist){
                    break;
                }
            }

            // Get Course Year
            int year;
            try {
                System.out.println("Please input the course Year:");
                year = scan.nextInt();
                if (year < 1 || year > 4) {
                    System.out.println("Error! Academic Year must be within 1-4!Please try adding student again!");
                    String dummy = scan.nextLine();
                    continue;
                }
            }catch (InputMismatchException e){
                System.out.println("Error! Year must be a integer! Please try adding student again!");
                String dummy = scan.nextLine();
                continue;
            }

            // Get Student home School
            String dummy = scan.nextLine();
            boolean schFound = false;
            String sch = null;
            while (!schFound) {
                System.out.println("Please input the School e.g SCSE/NBS/CEE etc:");
                sch = scan.nextLine().toUpperCase().trim();
                for (SchoolCode schoolCode : SchoolCode.values()) {
                    if (schoolCode.name().equalsIgnoreCase(sch)) {
                        schFound = true;
                        break;
                    }
                }
                if (!schFound) {
                    System.out.println("School does not exist! Please retry again!");
                }
            }

            // Get Student course programme
            boolean programmeFound = false;
            String programme = null;
            while (!programmeFound) {
                System.out.println("Please input the full programme name (e.g Computer Science):");
                programme = scan.nextLine().toUpperCase().trim();
                for (ProgrammeCode programmeCode : ProgrammeCode.values()) {
                    if (programmeCode.displayName().equalsIgnoreCase(programme)) {
                        programmeFound = true;
                        break;
                    }
                }
                if (!programmeFound) {
                    System.out.println("Programme does not exist! Please retry again!");
                }
            }

            // Get Student NTU official email
            System.out.println("Please input the email address:");
            String email = scan.nextLine();
            if (!isValidEmailAddress(email)){
                System.out.println("Error! Please enter valid email address! Please try adding student again!");
                continue;
            } else if(!(email.contains("@e.ntu.edu.sg") || email.contains("@ntu.edu.sg"))){
                System.out.println("Error! Address must be ntu address @e.ntu.edu.sg or @ntu.edu.sg!");
                continue;
            }

            // Get Student Mobile No
            Long mobileNo;
            try {
                System.out.println("Please input the mobile no:");
                mobileNo = scan.nextLong();
                //dummy = scan.nextLine();
                if (String.valueOf(mobileNo).length() != 8) {
                    System.out.println("Error! Mobile number must be 8 digits! Please try adding student again!");
                    dummy = scan.nextLine();
                    continue;
                }
            } catch (InputMismatchException e){
                System.out.println("Error! Mobile must be a 9 digit number! Please try adding student again!");
                dummy = scan.nextLine();
                continue;
            }

            // Get Student Nationality
            System.out.println("Please input the nationality:");
            dummy = scan.nextLine();
            String nationality = scan.nextLine().toUpperCase().trim();

            incomplete = false;
            adminController.addStudent(name, userName, password, account, gender, nationality, year, sch, programme,
                    matric, email, mobileNo);
            printAllStudents();
        }
    }

    /**
     * Checks if email input for adding of new Student is a valid email.
     * @param email Student's official NTU email
     * @return true/false
     */
    // Check if Student email is valid
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    /**
     * Prints all Students registered in NTU
     */
    // Print all Students in NTU
    public void printAllStudents(){
        System.out.println(
                "+----------------------------------------------------------------------------+");
        System.out.format("|%-14s|%-30s|%-30s|\n", "Matric Number", "Name", "Programme");
        System.out.println(
                "+----------------------------------------------------------------------------+");

        adminController.printAllStudent();

        System.out.println(
                "+----------------------------------------------------------------------------+");
    }

    /**
     * Prints all Admins registered in NTU
     */
    // Print all Admins in NTU
    public void printAllAdmins(){
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        System.out.format("|%-30s|%-20s|%-30s|\n", "Name", "Staff number", "Email");
        System.out.println(
                "+----------------------------------------------------------------------------------+");

        adminController.printAllAdmin();

        System.out.println(
                "+----------------------------------------------------------------------------------+");
    }

    /**
     * Prints all Courses in NTU
     */
    // Print all Courses in NTU
    public void displayCourses(){
        System.out.println(
                "+-----------------------------------------------------------------------------------------------+");
        System.out.format("|%-12s|%-40s|%15s|%11s|%13s|\n", "Course Code", "Course Name", "School Initial",
                "Course Type", "Academic Unit");
        System.out.println(
                "+-----------------------------------------------------------------------------------------------+");

        adminController.printAllCourse();

        System.out.println(
                "+-----------------------------------------------------------------------------------------------+");

        System.out.println();
        System.out.println("Course Type");
        System.out.println("T1 = Tutorial/Seminar");
        System.out.println("T2 = Lecture + Tutorial/Seminar");
        System.out.println("T3 = Lecture + Tutorial/Seminar + Laboratory");
        System.out.println();

    }

    /**
     * Adds new Course.
     */
    // Add new Course
    public void addCourse(){
        Scanner scan = new Scanner(System.in);

        // Add course details
        // Get Course home School
        boolean schFound = false;
        String school = null;
        while (!schFound) {
            System.out.println("Please input Course School e.g SCSE/NBS/CEE etc");
            school = scan.nextLine().toUpperCase().trim();
            for (SchoolCode schoolCode : SchoolCode.values()) {
                if (schoolCode.name().equalsIgnoreCase(school)) {
                    schFound = true;
                    break;
                }
            }
            if (!schFound) {
                System.out.println("School does not exist! Please retry again!");
            }
        }

        // Get Course Name
        boolean incomplete = true;
        while (incomplete){
            System.out.println("Please add Course Name");
            String courseName = scan.nextLine().toUpperCase().trim();
            boolean courseExist = false;
            //Check if Course Name already exist or if duplicate Course Name
            for (Course course : adminController.getCourseArray()){
                if (course.getCourseName().equalsIgnoreCase(courseName)){
                    System.out.println("Course already exists! Please Try again!");
                    courseExist = true;
                }
            }
            if (courseExist){
                continue;
            }

            // Get Course Code
            System.out.println("Please add Course Code");
            String courseCode = scan.nextLine().toUpperCase().trim();
            boolean codeExist = false;
            //Check if Course Code already exist or if duplicate Course Code
            for (Course course : adminController.getCourseArray()){
                if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                    System.out.println("Course code already exists! Please Try again!");
                    codeExist = true;
                }
            }
            if(codeExist){
                continue;
            }else if (courseCode.length() != 6){
                System.out.println("Error! Course Code must be 6 characters e.g CZ2002! Please re-try adding course again!");
                continue;
            }

            // Get Course Type
            System.out.println("Course Type");
            System.out.println("T1 = Tutorial/Seminar");
            System.out.println("T2 = Lecture + Tutorial/Seminar");
            System.out.println("T3 = Lecture + Tutorial/Seminar + Laboratory");
            System.out.println("Please input Course Type (T1/T2/T3)");
            String courseType = scan.nextLine().toUpperCase().trim();
            if (!(courseType.equalsIgnoreCase("T1") || courseType.equalsIgnoreCase("T2")
                    || courseType.equalsIgnoreCase("T3"))){
                System.out.println("Error! Course Type must be (T1/T2/T3)! Please re-try adding course again!");
                continue;
            }

            // Get Academic Unit
            int au;
            try {
                System.out.println("Please input Academic Unit");
                au = scan.nextInt();
                if (au < 1 || au > 4) {
                    System.out.println("Error! Academic Unit must be between 1-4! Please re-try adding course again!");
                    String dummy = scan.nextLine();
                    continue;
                }
            } catch (InputMismatchException e){
                System.out.println("Error! Academic Unit must be a integer! Please try adding course again!");
                String dummy = scan.nextLine();
                continue;
            }

            adminController.addCourse(school, courseCode, courseName, courseType, au);
            incomplete = false;
            displayCourses();
        }
    }

    /**
     * Prints all Indexes registered to a Course.
     */
    // Print all Index of a Course
    public void getIndex(){
        Scanner scan = new Scanner(System.in);
        boolean courseFound = false;
        System.out.println("Enter course code: ");
        String cc = scan.nextLine().toUpperCase().trim();

        // Print Index of the Course found
        for (Course course : adminController.getCourseArray()){
            if (course.getCourseCode().equalsIgnoreCase(cc)){
                courseFound=true;
                System.out.println("+------------+");
                System.out.format("|%-12s|\n", "Index");
                System.out.println("+------------+");
                adminController.printAllIndex(cc);
                System.out.println("+------------+");
            }
        }
        if (!courseFound){
            System.out.println("Course does not exist!");
        }
    }

    /**
     * Adds Index for Course during adding of new Course to NTU.
     */
    // Add Index for a Course
    public void addIndex(){
        Scanner scan = new Scanner(System.in);
        // Get Course code of Index
        System.out.println("Please input Course Code to add Index");
        String courseCode = scan.nextLine().toUpperCase().trim();
        boolean courseFound = false;

        for (Course course : adminController.getCourseArray()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;
                boolean incomplete = true;
                // Get new Index details
                while (incomplete) {
                    String courseIndex;
                    int indexSize;
                    int indexVacancy;
                    try {
                        // Get course index
                        System.out.println("Please add Course Index");
                        int idx = scan.nextInt();
                        if (String.valueOf(idx).length() != 5) {
                            System.out.println("Error! Index must be 5 digits! Please try adding index again!");
                            continue;
                        }
                        courseIndex = Integer.toString(idx);

                        // Get index size
                        System.out.println("Please input Index class size");
                        indexSize = scan.nextInt();
                        if (indexSize < 1 || indexSize > 50) {
                            System.out.println("Error! Class size cannot be less than 1 or more than 50!" + " Please try adding index again!");
                            continue;
                        }

                        // Get index vacancy
                        System.out.println("Please input Index class vacancy");
                        indexVacancy = scan.nextInt();
                        if (indexVacancy > indexSize) {
                            System.out.println("Error! Vacancy cannot be more than class size! Please try adding index again!");
                            continue;
                        } else if (indexVacancy < 0) {
                            System.out.println("Error! Vacancy cannot be less than 0 or more than 50! " + "Please try adding index again!");
                            continue;
                        }
                    } catch (InputMismatchException e){
                        System.out.println("Error! Input must be an integer number! Please try adding index again!");
                        String dummy = scan.nextLine();
                        continue;
                    }

                    adminController.addIndex(course, courseIndex, course.getCourseCode(), course.getCourseName(),
                            indexSize, course.getAU(), indexVacancy);
                    incomplete = false;
                }
            }
        }
        if (!courseFound){
            System.out.println("Course does not exist!");
        }
    }

    /**
     * Adds new Index for a existing Course in NTU.
     */
    // Add new Index
    public void addNewIndex(){
        Scanner scan = new Scanner(System.in);
        displayCourses();
        System.out.println("Please input Course Code to add new Index");
        String courseCode = scan.nextLine().toUpperCase().trim();
        boolean courseFound = false;

        for (Course course : adminController.getCourseArray()) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                courseFound = true;
                String courseIndex;

                boolean incomplete = true;
                // Add new index details
                while (incomplete) {
                    boolean indexExist = false;
                    try {
                        // Get course Index
                        System.out.println("Please add new Course Index");
                        int idx = scan.nextInt();
                        if (String.valueOf(idx).length() != 5) {
                            System.out.println("Error! Index must be 5 digits! Please try adding index again!");
                            continue;
                        }
                        courseIndex = Integer.toString(idx);

                        // Check if index already exist
                        for (Index index : course.getIndexes()) {
                            if (index.getCourseIndex().equalsIgnoreCase(courseIndex)) {
                                indexExist = true;
                                System.out.println("Index already exist! Please try adding index again!");
                            }
                        }
                        if (!indexExist) {
                            // Get Index size
                            System.out.println("Please input Index class size");
                            int indexSize = scan.nextInt();
                            if (indexSize < 1 || indexSize > 50) {
                                System.out.println("Error! Class size cannot be less than 1 or more than 50!" + " Please try adding index again!");
                                continue;
                            }

                            // Get Index Vacancy
                            System.out.println("Please input Index class vacancy");
                            int indexVacancy = scan.nextInt();
                            if (indexVacancy > indexSize) {
                                System.out.println("Error! Vacancy cannot be more than class size! Please try adding index again!");
                                continue;
                            } else if (indexVacancy < 0) {
                                System.out.println("Error! Vacancy cannot be less than 0 or more than 50! Please try adding index again!");
                                continue;
                            }
                            adminController.addIndex(course, courseIndex, course.getCourseCode(),
                                    course.getCourseName(), indexSize, course.getAU(), indexVacancy);
                            incomplete = false;
                        } else {
                            continue;
                        }
                    } catch (InputMismatchException e){
                        System.out.println("Error! Input must be integer number! Please try adding index again!");
                        String dummy = scan.nextLine();
                    }
                }
            }
        }
        if (!courseFound){
            System.out.println("Course does not exist!");
        }
    }

    /**
     * Prints all lessons in a Index.
     */
    // Get All Lessons for a Index
    public void getLesson(){
        Scanner scan = new Scanner(System.in);
        // Get Course code of Lesson
        System.out.println("Enter course code: ");
        String cc = scan.nextLine().toUpperCase().trim();

        System.out.println("+------------+");
        System.out.format("|%-12s|\n", "Index");
        System.out.println("+------------+");
        adminController.printAllIndex(cc);
        System.out.println("+------------+");

        // Get Index of Lesson
        System.out.println("Enter index: ");
        String idx = scan.nextLine().toUpperCase().trim();

        boolean courseFound = false;
        boolean indexFound = false;
        // Find Course and Index
        for (Course course : adminController.getCourseArray()){
            if (course.getCourseCode().equalsIgnoreCase(cc)){
                courseFound=true;
                for (Index index :course.getIndexes()){
                    // Print all Lesson of the Index
                    if (index.getCourseIndex().equalsIgnoreCase(idx)){
                        indexFound=true;
                        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
                        System.out.format("|%-12s|%-20s|%12s|%12s|%12s|%12s|%12s|\n", "Class Type", "Day", "Start Time","End Time","Venue","Week","Group");
                        System.out.println("+-------------------------------------------------------------------------------------------------------------+");

                        adminController.printAllLessons(cc,idx);

                        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
                    }
                }
                if (!indexFound){
                    System.out.println("Index does not exist!");
                }
            }
        }
        if (!courseFound){
            System.out.println("Course does not exist!");
        }
    }

    /**
     * Adds new lesson to a Index e.g LEC/TUT/LAB.
     */
    // Add new Lesson
    public void addLesson(){
        Scanner scan = new Scanner(System.in);
        displayCourses();
        // Get Course Code of Lesson
        System.out.println("Please input lesson Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();
        boolean courseFound = false;

        // Find Lesson's Course Code
        for (Course course : adminController.getCourseArray()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;

                System.out.println("+-------------+");
                System.out.format("|%-12s|\n", "Existing index");
                System.out.println("+-------------+");

                adminController.printAllIndex(courseCode);

                System.out.println("+-------------+");

                // Get Index of Lesson
                System.out.println("Please input lessons Course index");
                String courseIndex = scan.nextLine().toUpperCase().trim();
                boolean indexFound = false;

                System.out.println("+-------------------------------------------------------------------------------------------------------------+");
                System.out.format("|%-12s|%-20s|%12s|%12s|%12s|%12s|%12s|\n", "Class Type", "Day", "Start Time","End Time","Venue","Week","Group");
                System.out.println("+-------------------------------------------------------------------------------------------------------------+");

                adminController.printAllLessons(courseCode,courseIndex);

                System.out.println("+-------------------------------------------------------------------------------------------------------------+");

                // Find Lesson's Index
                for (Index index : course.getIndexes()){
                    if (index.getCourseIndex().equalsIgnoreCase(courseIndex)){
                        indexFound = true;
                        boolean incomplete = true;

                        // Add Lesson details
                        while(incomplete) {
                            // Get class type
                            System.out.println("Please add Class Type (Lec/Tut/Lab)");
                            String classType = scan.nextLine().toUpperCase().trim();
                            if (!(classType.equalsIgnoreCase("Lec") ||
                                    classType.equalsIgnoreCase("Tut") ||
                                    classType.equalsIgnoreCase("Lab"))){
                                System.out.println("Error! Please input correct Class type format (Lec/Tut/Lab)" +
                                        "Please re-try adding lesson again!");
                                continue;
                            }

                            // Get class day
                            System.out.println("Please input day of class (format e.g Mon/Tues/Weds/Thurs/Fri/Sat)");
                            String[] days = {"MON","TUES","WEDS","THURS","FRI","SAT"};
                            String day = scan.nextLine().toUpperCase().trim();
                            if (!Arrays.asList(days).contains(day)){
                                System.out.println("Error! Wrong day format! MON-SAT only! Please re-try adding Lesson!");
                                continue;
                            }

                            // Get class Start and End time
                            LocalTime st;
                            LocalTime et;
                            try {
                                System.out.println("Please input start time in 24H format (HH:MM)");
                                String startTime = scan.nextLine().toUpperCase().trim();
                                st = LocalTime.parse(startTime);

                                System.out.println("Please input end time in 24H format (HH:MM)");
                                String endTime = scan.nextLine().toUpperCase().trim();
                                et = LocalTime.parse(endTime);
                            } catch (DateTimeParseException e) {
                                System.out.println("Error in Date Time format/logic (HH:MM)! Please try again!");
                                continue;
                            }
                            // Check Start and End time logic
                            if (et.isBefore(st) || et.equals(st)){
                                System.out.println("Error! EndTime cannot be earlier or same as StartTime! Please try again!");
                                continue;
                            }

                            // Get class venue
                            System.out.println("Please input class venue");
                            String venue = scan.nextLine().toUpperCase().trim();

                            // Get number of weeks of class
                            System.out.println("Please input number of weeks of class (odd/even/all)");
                            String week = scan.nextLine().toUpperCase().trim();
                            if (!(week.equalsIgnoreCase("Odd") || week.equalsIgnoreCase("Even")
                                    || week.equalsIgnoreCase("All"))){
                                System.out.println("Error! Please enter correct format (odd/even/all)! Please re-try adding lesson!");
                                continue;
                            }

                            // Get class tutorial group e.g SSP3
                            System.out.println("Please input group of class");
                            String group = scan.nextLine().toUpperCase().trim();

                            adminController.addLesson(index, index.getCourseIndex(), classType, day, st, et, venue, week, group);
                            incomplete = false;
                        }
                    }
                }
                if (!indexFound){
                    System.out.println("Index does not exist");
                }
            }
        }
        if (!courseFound){
            System.out.println("Course does not exist!");
        }
    }

    /**
     * Updates Course Code of existing Course e.g CZ2002.
     */
    // Update Course Code
    public void updateCourseCode(){
        Scanner scan = new Scanner(System.in);
        displayCourses();

        // Get old Course Code
        System.out.println("Please input old Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

        // Get new Course Code
        boolean incomplete = true;
        String newCode = null;
        while(incomplete) {
            System.out.println("Please input new Course Code");
            newCode = scan.nextLine().toUpperCase().trim();
            if (newCode.length() != 6) {
                System.out.println("Error! Course Code must be 6 characters e.g CZ2002! Please re-try adding course again!");
                continue;
            }
            incomplete = false;
        }

        adminController.updateCourseCode(courseCode,newCode);
    }

    /**
     * Updates Course name of existing Course.
     */
    // Update Course Name
    public void updateCourseName(){
        Scanner scan = new Scanner(System.in);
        displayCourses();

        // Get Course code
        System.out.println("Please input Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

        // Get new Course Name
        System.out.println("Please input New Course Name");
        String newName = scan.nextLine().toUpperCase().trim();

        adminController.updateCourseName(courseCode,newName);
    }

    /**
     * Updates home School of existing Course e.g SCSE/NBS/CEE.
     */
    // Update Course School
    public void updateCourseSchool(){
        Scanner scan = new Scanner(System.in);
        displayCourses();

        // Get Course Code
        System.out.println("Please input Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

        // Get new Course School Name
        boolean schFound = false;
        String newSch = null;
        while (!schFound) {
            System.out.println("Please input New Course School Code");
            newSch = scan.nextLine().toUpperCase().trim();
            for (SchoolCode schoolCode : SchoolCode.values()) {
                if (schoolCode.name().equalsIgnoreCase(newSch)) {
                    schFound = true;
                    break;
                }
            }
            if (!schFound) {
                System.out.println("School does not exist! Please retry again!");
            }
        }

        adminController.updateCourseSchool(courseCode,newSch);
    }

    /**
     * Updates course type of existing Course e.g
     * (T1 = Tutorial/Seminar) ,
     * (T2 = Lecture + Tutorial/Seminar) ,
     * (T3 = Lecture + Tutorial/Seminar + Laboratory)
     */
    // Update course Type
    public void updateCourseType(){
        Scanner scan = new Scanner(System.in);
        displayCourses();

        // Get Course Code
        System.out.println("Please input Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

        // Get new course Type
        boolean incomplete = true;
        String courseType = null;
        while(incomplete) {
            System.out.println("Please input new course Type (T1/T2/T3)");
            courseType = scan.nextLine().toUpperCase().trim();
            if (!(courseType.equalsIgnoreCase("T1") || courseType.equalsIgnoreCase("T2")
                    || courseType.equalsIgnoreCase("T3"))){
                System.out.println("Error! Course Type must be (T1/T2/T3)! Please re-try adding course again!");
                continue;
            }
            incomplete = false;
        }

        adminController.updateCourseType(courseCode,courseType);
    }

    /**
     * Updates Academic Unit of existing Course
     */
    // Update Academic unit
    public void updateAcademicUnit(){
        Scanner scan = new Scanner(System.in);
        displayCourses();

        // Get Course Code
        System.out.println("Please input Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

        // Get Academic Unit
        boolean incomplete = true;
        while (incomplete) {
            try {
                System.out.println("Please input New Academic Unit");
                int au = scan.nextInt();
                if (au < 1 || au > 4) {
                    System.out.println("Error! Academic Unit must be between 1-4! Please re-try adding course again!");
                    continue;
                }
                incomplete = false;
                adminController.updateAcademicUnit(courseCode, au);
            } catch (InputMismatchException e){
                System.out.println("Error! AU must be a integer number! Please try again!");
                String dummy = scan.nextLine();
            }
        }
    }

    /**
     * Updates Index Code of existing Index e.g 10112
     */
    // Update Index Code
    public void updateIndexCode(){
        Scanner scan = new Scanner(System.in);
        displayCourses();
        // Get Course Code
        System.out.println("Please input Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

        // Check if Course Exist
        int courseCount = 0;
        for (Course course : adminController.getCourseArray()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseCount += 1;
            }
        }
        if (courseCount == 0){
            System.out.println("Error! Course don't exist! Please try again!");
            return;
        }

        System.out.println("+-------------+");
        System.out.format("|%-12s|\n", "Existing index");
        System.out.println("+-------------+");
        adminController.printAllIndex(courseCode);
        System.out.println("+-------------+");

        System.out.println("Please select index");
        String oldIdx = scan.nextLine().toUpperCase().trim();

        // Get new Index Code
        boolean incomplete = true;
        while (incomplete) {
            try {
                System.out.println("Please input new Index");
                int idx = scan.nextInt();
                if (String.valueOf(idx).length() != 5) {
                    System.out.println("Error! Index must be 5 digits! Please try adding index again!");
                    continue;
                }
                incomplete = false;
                String newIdx = Integer.toString(idx);
                adminController.updateIndexCode(courseCode, oldIdx, newIdx);
            } catch (InputMismatchException e){
                System.out.println("Error! Index must be an integer numbers! Please try again");
                String dummy = scan.nextLine();      
            }
        }
    }

    /**
     * Updates class size of existing Index.
     */
    // Update Index class size
    public void updateIndexSize(){
        Scanner scan = new Scanner(System.in);
        displayCourses();
        // Get Course Code
        System.out.println("Please input Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

         // Check if Course Exist
        int courseCount = 0;
        for (Course course : adminController.getCourseArray()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseCount += 1;
            }
        }
        if (courseCount == 0){
            System.out.println("Error! Course don't exist! Please try again!");
            return;
        }

        System.out.println("+-------------+");
        System.out.format("|%-12s|\n", "Existing index");
        System.out.println("+-------------+");
        adminController.printAllIndex(courseCode);
        System.out.println("+-------------+");

        System.out.println("Please select index");
        String idx = scan.nextLine().toUpperCase().trim();

        // Get new Index class size
        boolean incomplete = true;
        while (incomplete) {
            try {
                System.out.println("Please input new class size");
                int newSize = scan.nextInt();
                if (newSize < 1 || newSize > 50) {
                    System.out.println("Error! Class size cannot be less than 1 or more than 50!" + " Please try adding index again!");
                    continue;
                }
                incomplete = false;
                adminController.updateIndexSize(courseCode, idx, newSize);
            } catch (InputMismatchException e){
                System.out.println("Error! Input must be an integer! Please try again!");
                String dummy = scan.nextLine();
            }
        }
    }

    /**
     * Updates Vacancy size of existing Index.
     */
    // Update Index Vacancy
    public void updateVacancy(){
        Scanner scan = new Scanner(System.in);
        displayCourses();
        // Get Course Code
        System.out.println("Please input Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

        // Check if Course Exist
        int courseCount = 0;
        for (Course course : adminController.getCourseArray()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseCount += 1;
            }
        }
        if (courseCount == 0){
            System.out.println("Error! Course don't exist! Please try again!");
            return;
        }

        System.out.println("+-------------+");
        System.out.format("|%-12s|\n", "Existing index");
        System.out.println("+-------------+");
        adminController.printAllIndex(courseCode);
        System.out.println("+-------------+");

        // Get Index code
        System.out.println("Please select index");
        String idx = scan.nextLine().toUpperCase().trim();

        // Get new Index Vacancy
        boolean incomplete = true;
        while(incomplete) {
            try {
                System.out.println("Please input new vacancy");
                int newVacancy = scan.nextInt();
                adminController.updateVacancy(courseCode, idx, newVacancy);
                incomplete = false;
            } catch (InputMismatchException e) {
                System.out.println("Error! Vacancy must be an integer! Please re-try again!");
                String dummy = scan.nextLine();
            }

        }
    }

    /**
     * Checks vacancy size of existing Index class.
     */
    // Check Index Vacancy
    public void checkIndexVacancy(){
        Scanner scan = new Scanner(System.in);
        displayCourses();
        // Get Course Code
        System.out.println("Please input Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

        // Check if Course exist
        int courseCount = 0;
        for (Course course : adminController.getCourseArray()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseCount += 1;
            }
        }
        if (courseCount == 0){
            System.out.println("Error! Course don't exist! Please try again!");
            return;
        }

        System.out.println("+-------------+");
        System.out.format("|%-12s|\n", "Existing index");
        System.out.println("+-------------+");
        adminController.printAllIndex(courseCode);
        System.out.println("+-------------+");

        // Get Index Code
        System.out.println("Please select index");
        String idx = scan.nextLine().toUpperCase().trim();

        adminController.checkVacancy(courseCode,idx);
    }

    /**
     * Prints all students in an existing class Index.
     */
    // Print Student by class Index
    public void printStudentByIndex(){
        Scanner scan = new Scanner(System.in);
        displayCourses();
        // Get Course Code
        System.out.println("Please input Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

        // Check if Course exist
        int courseCount = 0;
        for (Course course : adminController.getCourseArray()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseCount += 1;
            }
        }
        if (courseCount == 0){
            System.out.println("Error! Course don't exist! Please try again!");
            return;
        }

        System.out.println("+-------------+");
        System.out.format("|%-12s|\n", "Existing index");
        System.out.println("+-------------+");
        adminController.printAllIndex(courseCode);
        System.out.println("+-------------+");

        // Get Index code
        System.out.println("Please select index");
        String idx = scan.nextLine().toUpperCase().trim();

        // print all students by index
        adminController.printStudentByIndex(courseCode,idx);
    }

    /**
     * Prints all Students registered in an existing Course.
     */
    public void printStudentByCourse(){
        Scanner scan = new Scanner(System.in);
        displayCourses();

        int error = 1;
        while (error == 1){
            //get course code
            System.out.println("Please input Course Code");
            String courseCode = scan.nextLine().toUpperCase().trim();

        
            // print all students by course
            error = adminController.printStudentByCourse(courseCode);
    

        }
    }

    /**
     * Updates the day of the existing Lesson class e.g MON/TUES/WEDS/THURS/FRI/SAT
     */
    // Update Day of Lesson
    public void updateDay() {
        Scanner scan = new Scanner(System.in);
        displayCourses();
        // Get Course Code
        System.out.println("Please input Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

        // Check if Course exist
        int courseCount = 0;
        for (Course course : adminController.getCourseArray()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseCount += 1;
            }
        }
        if (courseCount == 0){
            System.out.println("Error! Course don't exist! Please try again!");
            return;
        }

        System.out.println("+------------+");
        System.out.format("|%-12s|\n", "Index");
        System.out.println("+------------+");
        adminController.printAllIndex(courseCode);
        System.out.println("+------------+");

        // Get Index code
        System.out.println("Please select index");
        String idx = scan.nextLine().toUpperCase().trim();

        System.out.println("Existing lessons:");
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        System.out.format("|%-12s|%-20s|%12s|%12s|%12s|%12s|%12s|\n", "Class Type", "Day", "Start Time", "End Time", "Venue", "Week", "Group");
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        adminController.printAllLessons(courseCode, idx);
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");

        // Get which class type of Lesson to change e.g LEC/TUT/LAB
        System.out.println("Please select class type");
        String ct = scan.nextLine().toUpperCase().trim();

        // Get new Day
        boolean incomplete = true;
        while (incomplete) {
            System.out.println("Please input new day of class (format e.g Mon/Tues/Weds/Thurs/Fri/Sat)");
            String[] days = {"MON", "TUES", "WEDS", "THURS", "FRI", "SAT"};
            String newDay = scan.nextLine().toUpperCase().trim();
            if (!Arrays.asList(days).contains(newDay)) {
                System.out.println("Error! Wrong day format! Please retry!");
                continue;
            }
            incomplete = false;
            adminController.updateDay(courseCode, idx, ct, newDay);
        }
    }


    /**
     * Updates Start and End time of lesson class
     */
    // Update Start & End time of Lesson
    public void updateStartEndTime(){
        Scanner scan = new Scanner(System.in);
        displayCourses();
        // Get Course code
        System.out.println("Please input Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

        // Check if Course exist
        int courseCount = 0;
        for (Course course : adminController.getCourseArray()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseCount += 1;
            }
        }
        if (courseCount == 0){
            System.out.println("Error! Course don't exist! Please try again!");
            return;
        }

        System.out.println("+------------+");
        System.out.format("|%-12s|\n", "Index");
        System.out.println("+------------+");
        adminController.printAllIndex(courseCode);
        System.out.println("+------------+");

        // Get Course Index
        System.out.println("Please select index");
        String idx = scan.nextLine().toUpperCase().trim();

        System.out.println("Existing lessons:");
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        System.out.format("|%-12s|%-20s|%12s|%12s|%12s|%12s|%12s|\n", "Class Type", "Day", "Start Time","End Time","Venue","Week","Group");
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        adminController.printAllLessons(courseCode,idx);
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");

        // Get class type of Index to change e.g LEC/TUT/LAB
        System.out.println("Please select class type");
        String ct = scan.nextLine().toUpperCase().trim();

        // Get class new Start and End time details
        LocalTime st = null;
        LocalTime et = null;
        boolean incomplete = true;
        while (incomplete) {
            try {
                System.out.println("Please input new Start Time in 24H format (HH:MM)");
                String startTime = scan.nextLine().toUpperCase().trim();
                st = LocalTime.parse(startTime);

                System.out.println("Please input new End Time in 24H format (HH:MM)");
                String endTime = scan.nextLine().toUpperCase().trim();
                et = LocalTime.parse(endTime);

                // Check start & end time logic
                if (et.isBefore(st) || et.equals(st)){
                    System.out.println("Error! EndTime cannot be earlier or same as StartTime! Please try again!");
                    continue;
                }

                incomplete = false;
            } catch (DateTimeParseException e) {
                System.out.println("Error in Date Time format/logic (HH:MM)! Please try again!");
            }
        }

        adminController.updateStartEndTime(courseCode,idx,ct,st,et);
    }

    /**
     * Updates veneue of Lesson.
     */
    // Update Lesson Venue
    public void updateVenue(){
        Scanner scan = new Scanner(System.in);
        displayCourses();
        // Get Course code
        System.out.println("Please input Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

        // Check if Course exist
        int courseCount = 0;
        for (Course course : adminController.getCourseArray()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseCount += 1;
            }
        }
        if (courseCount == 0){
            System.out.println("Error! Course don't exist! Please try again!");
            return;
        }

        System.out.println("+------------+");
        System.out.format("|%-12s|\n", "Index");
        System.out.println("+------------+");
        adminController.printAllIndex(courseCode);
        System.out.println("+------------+");

        // Get Course Index
        System.out.println("Please select index");
        String idx = scan.nextLine().toUpperCase().trim();

        System.out.println("Existing lessons:");
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        System.out.format("|%-12s|%-20s|%12s|%12s|%12s|%12s|%12s|\n", "Class Type", "Day", "Start Time","End Time","Venue","Week","Group");
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        adminController.printAllLessons(courseCode,idx);
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");

        // Get class type of Lesson to change e.g LEC/TUT/LAB
        System.out.println("Please select class type");
        String ct = scan.nextLine().toUpperCase().trim();

        // Get new Venue
        System.out.println("Please input new Venue");
        String v = scan.nextLine().toUpperCase().trim();

        adminController.updateVenue(courseCode,idx,ct,v);
    }

    /**
     * Updates the number of weeks of Lesson class e.g Odd week class/ Even week class/ All semester
     */
    // Update Lesson weeks
    public void updateWeek(){
        Scanner scan = new Scanner(System.in);
        displayCourses();
        // Get Course code
        System.out.println("Please input Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

        // Check if Course exist
        int courseCount = 0;
        for (Course course : adminController.getCourseArray()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseCount += 1;
            }
        }
        if (courseCount == 0){
            System.out.println("Error! Course don't exist! Please try again!");
            return;
        }

        System.out.println("+------------+");
        System.out.format("|%-12s|\n", "Index");
        System.out.println("+------------+");
        adminController.printAllIndex(courseCode);
        System.out.println("+------------+");

        // Get Course Index
        System.out.println("Please select index");
        String idx = scan.nextLine().toUpperCase().trim();

        System.out.println("Existing lessons:");
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        System.out.format("|%-12s|%-20s|%12s|%12s|%12s|%12s|%12s|\n", "Class Type", "Day", "Start Time","End Time","Venue","Week","Group");
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        adminController.printAllLessons(courseCode,idx);
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");

        // Get class type of Lesson to change e.g LEC/TUT/LAB
        System.out.println("Please select class type");
        String ct = scan.nextLine().toUpperCase().trim();

        // Get new class Weeks
        boolean incomplete = true;
        while(incomplete) {
            System.out.println("Please input new number of weeks of class (odd/even/all)");
            String w = scan.nextLine().toUpperCase().trim();
            if (!(w.equalsIgnoreCase("Odd") || w.equalsIgnoreCase("Even") || w.equalsIgnoreCase("All"))) {
                System.out.println("Error! Please enter correct format (odd/even/all)! Please retry!");
                continue;
            }
            incomplete = false;
            adminController.updateWeek(courseCode, idx, ct, w);
        }
    }

    /**
     * Updates class group of Lesson e.g SSP3 etc.
     */
    // Update Lesson Group
    public void updateGroup(){
        Scanner scan = new Scanner(System.in);
        displayCourses();
        // Get Course code
        System.out.println("Please input Course Code");
        String courseCode = scan.nextLine().toUpperCase().trim();

        // Check if Course exist
        int courseCount = 0;
        for (Course course : adminController.getCourseArray()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseCount += 1;
            }
        }
        if (courseCount == 0){
            System.out.println("Error! Course don't exist! Please try again!");
            return;
        }

        System.out.println("+------------+");
        System.out.format("|%-12s|\n", "Index");
        System.out.println("+------------+");
        adminController.printAllIndex(courseCode);
        System.out.println("+------------+");

        // Get Course Index
        System.out.println("Please select index");
        String idx = scan.nextLine().toUpperCase().trim();

        System.out.println("Existing lessons:");
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        System.out.format("|%-12s|%-20s|%12s|%12s|%12s|%12s|%12s|\n", "Class Type", "Day", "Start Time","End Time","Venue","Week","Group");
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        adminController.printAllLessons(courseCode,idx);
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");

        // Get class type of Lesson to change e.g LEC/TUT/LAB
        System.out.println("Please select class type");
        String ct = scan.nextLine().toUpperCase().trim();

        // Get new class group e.g SSP3
        System.out.println("Please input new Group");
        String g = scan.nextLine().toUpperCase().trim();

        adminController.updateGroup(courseCode,idx,ct,g);
    }

    /**
     * Deletes existing Course.
     */
    // Delete existing Course
    public void deleteCourse(){
        Scanner scan = new Scanner(System.in);
        displayCourses();
        Course course = new Course();
        course = null;
        // Check if Course exist
        while (course == null){
            System.out.println("Which course would you like to remove? Please enter course code.");
            String courseCode = scan.nextLine().toUpperCase().trim();
            course = adminController.selectCourseCodeAdmin(courseCode);
        }

        System.out.println("Confirm to drop? Yes/No: ");
        String confirm = scan.nextLine().toUpperCase().trim();

        if (confirm.equals("YES")){
            adminController.deleteCourse(course);
        }
    }

    /**
     * Deletes existing Index of a Course.
     */
    // Delete existing Course Index
    public void deleteIndex(){
        Scanner scan = new Scanner(System.in);
        displayCourses();
        getIndex();
        Course course = new Course();
        course = null;
        // Check if Course exist
        while (course == null){
            System.out.println("Which index would you like to remove? Please re-enter course code for confirmation to delete: ");
            String cc = scan.nextLine().toUpperCase().trim();
            course = adminController.selectCourseCodeAdmin(cc);
        }

        // Check if Index exist
        Index index = new Index();
        index = null;
        while (index == null){
            System.out.println("Enter course index: ");
            String ind = scan.nextLine().toUpperCase().trim();
            index = adminController.selectCourseIndexAdmin(ind);
        }
        
        System.out.println("Confirm to drop? Yes/No: ");
        String confirm = scan.nextLine().toUpperCase().trim();

        if (confirm.equals("YES")){
            adminController.deleteIndex(course,index);
            System.out.println("Deleted successfully!");
        }
    }

}
