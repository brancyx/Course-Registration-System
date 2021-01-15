package stars.controller;

import stars.model.AccessPeriod;
import stars.model.Course;
import stars.model.Index;
import stars.model.Lesson;
import stars.model.RegisteredCourses;
import stars.users.Student;
import stars.users.Admin;
import stars.roster.NTU;
import stars.users.User;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Represents a controller that allows Admin to interact with other class entities.
 */
public class AdminController {
    /**
     * Object that contains all other class entities.
     */
    private static NTU ntu;

    /**
     * Creates a new AdminController.
     * Loads serialized file.
     */
    public AdminController() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        load();
    }

    /**
     * Prints access period for a School.
     * @param sch School of Student e.g NBS/SCSE/CEE etc.
     */
    // display access period of school
    public void printAccessPeriod(String sch){
        AccessPeriod ap = ntu.getAccessPeriod(sch);
        System.out.println(ap.getAccessStart() + " - " + ap.getAccessEnd());
    }

    /**
     * Updates access period for a School.
     * @param sch School of Student e.g NBS/SCSE/CEE etc.
     * @param as Access period Start datetime
     * @param ae Access period End datetime
     */
    // update access period of students based on school
    public void updateAccessPeriod(String sch, LocalDateTime as, LocalDateTime ae){
        ntu.updateAccessPeriod(sch,as,ae);
        System.out.println("Update successful!");
        save();
    }

    /**
     * Adds new Student.
     * @param n Name of Student.
     * @param u Username of Student.
     * @param pass Password of Student.
     * @param acc Account Type of Student.
     * @param g Gender of Student.
     * @param nation Nationality of Student.
     * @param yr Course Year of Student.
     * @param sch Programme School of Student e.g SCSE/NBS/CEE etc.
     * @param prog Course Programme of Student
     * @param matric Matriculation Number of Student.
     * @param email NTU email address of Student.
     * @param mobile Mobile number of Student.
     */
    // add new student with details
    public void addStudent(String n, String u, String pass, char acc, String g, String nation, int yr, String sch,
                           String prog, String matric, String email, Long mobile) {
        String newPwdHashed = LoginController.generateHashedPassword(pass);                       
        Student student = new Student(n, u, newPwdHashed, acc, g, nation, yr, sch, prog, matric, email , mobile);
        ntu.addUsers(student);
        System.out.println("Student added successfully!");

        save();
    }

    /**
     * Adds new Course.
     * @param school School of course programme e.g SCSE/NBS/CEE etc.
     * @param courseCode Course code e.g CZ2002
     * @param courseName Course name e.g Object Oriented Programming
     * @param courseType Course type of Course e.g T1/T2/T3
     * @param au Academic Unit of Course
     */
    // add new course with details
    public void addCourse(String school, String courseCode,String courseName,String courseType, int au) {
        ntu.addCourse(school, courseCode, courseName, courseType, au);
        System.out.println("Course added successfully!");
        save();
    }

    /**
     * Adds a Index for Course.
     * @param course Course object of Index
     * @param courseIndex  Course Index e.g 10112
     * @param courseCode Course code of Index e.g CZ2002
     * @param courseName Course name of Index e.g Object Oriented Programming
     * @param indexSize Index class size
     * @param AUs Academic Unit of Index
     * @param indexVacancy Index class vacancy
     */
    // add new index with details
    public void addIndex(Course course,String courseIndex,String courseCode,String courseName,int indexSize, int AUs, int indexVacancy){
        course.addIndexes(courseIndex,courseCode,courseName, AUs, indexSize, indexVacancy, 0);
        System.out.println("Index added successfully");
        save();
    }

    /**
     * Adds a Lesson for Index.
     * @param index Index object of Lesson.
     * @param courseIndex Course Index e.g 10112
     * @param classType Class Type e.g LEC/TUT/LAB
     * @param day Day of class e.g MON/TUS/WEDS/THURS/FRI/SAT
     * @param st Start time of lesson
     * @param et End time of lesson
     * @param venue Venue of lesson
     * @param week Number of weeks of lesson e.g Odd weeks/Even weeks/All semester
     * @param group Class Group of lesson e.g SSP3
     */
    // add new lesson with details
    public void addLesson(Index index, String courseIndex, String classType, String day, LocalTime st, LocalTime et,
                          String venue, String week, String group ){
        index.setLessons(courseIndex,classType,day,st,et,venue,week,group);
        System.out.println("Lesson addedd successfully!");
        save();
    }

    /**
     * Updates Course code of existing Course.
     * @param courseCode Old Course code
     * @param newCode New Course code
     */
    // update course code of selected course
    public void updateCourseCode(String courseCode, String newCode){
        boolean courseFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;
                course.setCourseCode(newCode);
                for (Index index : course.getIndexes()){
                    index.setCourseCode(newCode);
                }
                System.out.println("Update successful!");
            }
            save();
        }
        if (!courseFound){
            System.out.println("Update fail. Course does not exist!");
        }
    }

    /**
     * Updates Course name of existing Course.
     * @param oldName Old Course name
     * @param newName New Course name
     */

    // update name of selected course
    public void updateCourseName(String oldName, String newName){
        boolean courseFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(oldName)){
                courseFound = true;
                course.setCourseName(newName);
                for (Index index : course.getIndexes()){
                    index.setCourseName(newName);
                }
                System.out.println("Update successful!");
            }
            save();
        }
        if (!courseFound){
            System.out.println("Update fail. Course does not exist!");
        }
    }

    /**
     * Updates home School of Course e.g NBS/SCSE/CEE etc.
     * @param oldSchool Old home School of Course
     * @param newSchool New home School of Course
     */
    // update school of seelcted course 
    public void updateCourseSchool(String oldSchool, String newSchool){
        boolean courseFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(oldSchool)){
                courseFound = true;
                course.setSchool(newSchool);
                System.out.println("Update successful!");
            }
            save();
        }
        if (!courseFound){
            System.out.println("Update fail. Course does not exist!");
        }
    }

    /**
     * Updates course type of Course e.g T1/T2/T3
     * @param oldType Old course type
     * @param newType New course type
     */
    // update course type of course (T1/T2/T3)
    public void updateCourseType(String oldType, String newType){
        boolean courseFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(oldType)){
                courseFound = true;
                course.setCourseType(newType);
                System.out.println("Update successful!");
            }
            save();
        }
        if (!courseFound){
            System.out.println("Update fail. Course does not exist!");
        }
    }

    /**
     * Update Academic Unit of Course
     * @param courseCode Course code of Course
     * @param au New Academic Unit of Course
     */
    // update academic units of selected course
    public void updateAcademicUnit(String courseCode, int au){
        boolean courseFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;
                course.setAU(au);
                for (Index index : course.getIndexes()){
                    index.setAU(au);
                }
                System.out.println("Update successful!");
            }
            save();
        }
        if (!courseFound){
            System.out.println("Update fail. Course does not exist!");
        }
    }

    /**
     * Updates Index Code of existing Index
     * @param courseCode Course code of Index's Course e.g CZ2002
     * @param idx Old Index code
     * @param newIdx New Index code
     */
    // update selected index code to new index code
    public void updateIndexCode(String courseCode, String idx, String newIdx){
        boolean courseFound = false;
        boolean indexFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;
                for (Index index : course.getIndexes()){
                    if (index.getCourseIndex().equalsIgnoreCase(idx)){
                        indexFound = true;
                        index.setCourseIndex(newIdx);
                        for (Lesson lesson : index.getLessons()){
                            lesson.setCourseIndex(newIdx);
                        }
                        System.out.println("Update successful!");
                    }
                }
                if (!indexFound){
                    System.out.println("Update fail. Index does not exist!");
                }
            }
        }
        if (!courseFound){
            System.out.println("Update fail. Course does not exist!");
        }
        save();
    }

    /**
     * Updates Index class size.
     * @param courseCode Course code of Index's Course e.g CZ2002
     * @param idx Index code
     * @param size New class size
     */
    // update class size of selected index
    public void updateIndexSize(String courseCode, String idx, int size){
        boolean courseFound = false;
        boolean indexFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;
                for (Index index : course.getIndexes()){
                    if (index.getCourseIndex().equalsIgnoreCase(idx)){
                        indexFound = true;
                        index.setSize(size);
                        System.out.println("Update successful!");
                    }
                }
                if (!indexFound){
                    System.out.println("Update fail. Index does not exist!");
                }
            }
        }
        if (!courseFound){
            System.out.println("Update fail. Course does not exist!");
        }
        save();
    }

    /**
     * Updates Vacancy of an Index.
     * @param courseCode Course code of Index's Course e.g CZ2002
     * @param idx Index code
     * @param newVacancy New vacancy size
     */
    // update vacancy of selected index
    public void updateVacancy(String courseCode, String idx, int newVacancy){
        boolean courseFound = false;
        boolean indexFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;
                for (Index index : course.getIndexes()){
                    if (index.getCourseIndex().equalsIgnoreCase(idx)){
                        indexFound = true;
                        if (newVacancy>index.getSize()){
                            System.out.println("Error! Vacancy cannot be more than class size! Please try adding index again!");
                            break;
                        } else if (newVacancy<0){
                            System.out.println("Error! Vacancy cannot be less than 0 or more than 50! " +
                                    "Please try adding index again!");
                            break;
                        } else {
                            index.setVacancy(newVacancy);
                            System.out.println("Update successful!");
                        }
                    }
                }
                if (!indexFound){
                    System.out.println("Update fail. Index does not exist!");
                }
            }
        }
        if (!courseFound){
            System.out.println("Update fail. Course does not exist!");
        }
        save();
    }

    /**
     * Checks Index class Vacancy size.
     * @param courseCode Course code of Index's Course e.g CZ2002
     * @param idx Index code
     */
    // display vacancy in selected index
    public void checkVacancy(String courseCode, String idx){
        boolean courseFound = false;
        boolean indexFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;
                for (Index index : course.getIndexes()){
                    if (index.getCourseIndex().equalsIgnoreCase(idx)){
                        indexFound = true;
                        System.out.println("Current Vacancy: " + index.getVacancy() + "/" + index.getSize());
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
     * Prints all Student registered in NTU.
     */
    // display all student details
    public void printAllStudent(){
        for (User user : ntu.getUsers()){
            if (user instanceof Student){
                System.out.format("|%-14s|%-30s|%-30s|\n",((Student) user).getMatricNum(), user.getName(),
                        ((Student) user).getProgram());
            }
        }
    }

    /**
     * Prints all Admin registered in NTU.
     */
    // display all admin details
    public void printAllAdmin(){
        for (User user : ntu.getUsers()){
            if (user instanceof Admin){
                System.out.format("|%-30s|%-20s|%-30s|\n", user.getName(), ((Admin) user).getStaffNo(), ((Admin) user).getEmail());
            }
        }
    }

    /**
     * Gets array list of all Students in NTU
     * @return Array list of Students
     */
    // retrieve array list of all students
    public ArrayList<Student> getAllStudents(){
        ArrayList<Student> students = new ArrayList<>();
        for (User user : ntu.getUsers()){
            if (user instanceof Student){
                students.add((Student) user);
            }
        }
        return students;
    }

    /**
     * Prints all Students registered in a Course Index
     * @param courseCode Course code of Index
     * @param idx Index code
     */
    // display students in a course index
    public void printStudentByIndex(String courseCode, String idx){
        boolean courseFound = false;
        boolean indexFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;
                for (Index index : course.getIndexes()){
                    if (index.getCourseIndex().equalsIgnoreCase(idx)){
                        indexFound = true;

                        System.out.println(
                                "+------------------------------------------------------------------------------------------+");
                        System.out.format("|%-14s|%-30s|%-7s|%-30s|%5s|\n", "Matric Number", "Name", "Gender", "Programme", "Year");
                        System.out.println(
                                "+------------------------------------------------------------------------------------------+");

                        if (index.getExistingStudent().isEmpty()){
                            System.out.println("No students in index!");
                        } else {
                            for (Student student : index.getExistingStudent()) {
                                System.out.format("|%-14s|%-30s|%-7s|%-30s|%5s|\n", student.getMatricNum(),
                                        student.getName(), student.getGender(), student.getProgram(), student.getStudyYear());
                            }
                        }
                        System.out.println(
                                "+------------------------------------------------------------------------------------------+");
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
     * Prints all Students registerd in a Course
     * @param courseCode
     * @return 1(Course not found) or 0(Course found)
     */
    public int printStudentByCourse(String courseCode){
        boolean courseFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;
                int size = course.getIndexes().size();
                System.out.println("+-----------------------------------------------------------+");
                System.out.format("|%-30s|%-7s|%-20s|\n", "Name", "Gender", "Nationality");
                System.out.println("+-----------------------------------------------------------+");
                for (Index index : course.getIndexes()) {
                    if (index.getExistingStudent().isEmpty()) {
                        size -= 1;
                    } else {
                        for (Student student : index.getExistingStudent()) {
                            System.out.format("|%-30s|%-7s|%-20s|\n", student.getName(), student.getGender(),
                                    student.getNationality());
                        }
                    }
                }
                if (size == 0){
                    System.out.println("No students in course!");
                }
                System.out.println("+-----------------------------------------------------------+");
                return 0;
            }
        }
        if (!courseFound){
            System.out.println("Course does not exist!");
        }
        return 1;
    }

    /**
     * Gets array list of Course
     * @return Array list of Course
     */
    // retieve array of course
    public ArrayList<Course> getCourseArray(){
        return ntu.getCourse();
    }

    /**
     * Prints all Courses in NTU
     */
    // display all courses available
    public void printAllCourse(){
        for (Course course : ntu.getCourse()){
            System.out.format("|%-12s|%-40s|%15s|%11s|%13s|\n", course.getCourseCode(), course.getCourseName(),
                    course.getSchool(), course.getCourseType(), course.getAU());
        }
    }

    /**
     * Prints all Indexes of a Course
     * @param courseCode
     */
    // display all indexes in a course
    public void printAllIndex(String courseCode){
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                for (Index index : course.getIndexes()){
                    System.out.format("|%-12s|\n", index.getCourseIndex());
                }
            }
        }
    }

    /**
     * Prints all Lessons in an Index
     * @param courseCode Course code of Index e.g CZ2002
     * @param idx Index code
     */
    // display all lessons in an index
    public void printAllLessons(String courseCode, String idx){
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                for (Index index : course.getIndexes()){
                    if(index.getCourseIndex().equalsIgnoreCase(idx)){
                        for (Lesson lesson : index.getLessons()){
                            System.out.format("|%-12s|%-20s|%12s|%12s|%12s|%12s|%12s|\n", lesson.getClassType(), lesson.getDay(),
                                    lesson.getStartTime(), lesson.getEndTime(), lesson.getVenue(),lesson.getWeek(), lesson.getGroup());
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates Day of the lesson e.g MON/TUES/WEDS/THURS/FRI/SAT
     * @param courseCode Course code of Index e.g CZ2002
     * @param idx Index code
     * @param classType Class Type e.g LEC/TUT/LAB
     * @param day new Day
     */
    //update lesson day
    public void updateDay(String courseCode, String idx, String classType, String day){
        boolean courseFound = false;
        boolean indexFound = false;
        boolean lessonFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;
                for (Index index : course.getIndexes()){
                    if (index.getCourseIndex().equalsIgnoreCase(idx)){
                        indexFound = true;
                        for (Lesson lesson : index.getLessons()){
                            if (lesson.getClassType().equalsIgnoreCase(classType)){
                                lessonFound = true;
                                lesson.setDay(day);
                                System.out.println("Update successful!");
                            }
                        }
                        if (!lessonFound){
                            System.out.println("Update fail. Lesson does not exist!");
                        }
                    }
                }
                if (!indexFound){
                    System.out.println("Update fail. Index does not exist!");
                }
            }
        }
        if (!courseFound){
            System.out.println("Update fail. Course does not exist!");
        }
        save();
    }

    /**
     * Updates Start and End time of Lesson
     * @param courseCode Course code of Index e.g CZ2002
     * @param idx Index code
     * @param classType Class Type e.g LEC/TUT/LAB
     * @param startTime New start time
     * @param endTime New end time
     */
    //update lesson start and end time
    public void updateStartEndTime(String courseCode, String idx, String classType, LocalTime startTime, LocalTime endTime){
        boolean courseFound = false;
        boolean indexFound = false;
        boolean lessonFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;
                for (Index index : course.getIndexes()){
                    if (index.getCourseIndex().equalsIgnoreCase(idx)){
                        indexFound = true;
                        for (Lesson lesson : index.getLessons()){
                            if (lesson.getClassType().equalsIgnoreCase(classType)){
                                lessonFound = true;
                                lesson.setStartTime(startTime);
                                lesson.setEndTime(endTime);
                                System.out.println("Update successful!");
                            }
                        }
                        if (!lessonFound){
                            System.out.println("Update fail. Lesson does not exist!");
                        }
                    }
                }
                if (!indexFound){
                    System.out.println("Update fail. Index does not exist!");
                }
            }
        }
        if (!courseFound){
            System.out.println("Update fail. Course does not exist!");
        }
        save();
    }


    /**
     * Updates Venue of Lesson
     * @param courseCode Course code of Index e.g CZ2002
     * @param idx Index code
     * @param classType Class Type e.g LEC/TUT/LAB
     * @param venue New Venue
     */
    //update lesson venue
    public void updateVenue(String courseCode, String idx, String classType, String venue){
        boolean courseFound = false;
        boolean indexFound = false;
        boolean lessonFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;
                for (Index index : course.getIndexes()){
                    if (index.getCourseIndex().equalsIgnoreCase(idx)){
                        indexFound = true;
                        for (Lesson lesson : index.getLessons()){
                            if (lesson.getClassType().equalsIgnoreCase(classType)){
                                lessonFound = true;
                                lesson.setVenue(venue);
                                System.out.println("Update successful!");
                            }
                        }
                        if (!lessonFound){
                            System.out.println("Update fail. Lesson does not exist!");
                        }
                    }
                }
                if (!indexFound){
                    System.out.println("Update fail. Index does not exist!");
                }
            }
        }
        if (!courseFound){
            System.out.println("Update fail. Course does not exist!");
        }
        save();
    }

    /**
     * Updates weeks of Lesson e.g Odd week/Even week/All semester
     * @param courseCode Course code of Index e.g CZ2002
     * @param idx Index code
     * @param classType Class Type e.g LEC/TUT/LAB
     * @param week New week
     */
    //update lesson week
    public void updateWeek(String courseCode, String idx, String classType, String week){
        boolean courseFound = false;
        boolean indexFound = false;
        boolean lessonFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;
                for (Index index : course.getIndexes()){
                    if (index.getCourseIndex().equalsIgnoreCase(idx)){
                        indexFound = true;
                        for (Lesson lesson : index.getLessons()){
                            if (lesson.getClassType().equalsIgnoreCase(classType)){
                                lessonFound = true;
                                lesson.setWeek(week);
                                System.out.println("Update successful!");
                            }
                        }
                        if (!lessonFound){
                            System.out.println("Update fail. Lesson does not exist!");
                        }
                    }
                }
                if (!indexFound){
                    System.out.println("Update fail. Index does not exist!");
                }
            }
        }
        if (!courseFound){
            System.out.println("Update fail. Course does not exist!");
        }
        save();
    }

    /**
     * Updates class group of Lesson e.g SSP3 etc.
     * @param courseCode Course code of Index e.g CZ2002
     * @param idx Index code
     * @param classType Class Type e.g LEC/TUT/LAB
     * @param group New group
     */
    //update lesson group 
    public void updateGroup(String courseCode, String idx, String classType, String group){
        boolean courseFound = false;
        boolean indexFound = false;
        boolean lessonFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equalsIgnoreCase(courseCode)){
                courseFound = true;
                for (Index index : course.getIndexes()){
                    if (index.getCourseIndex().equalsIgnoreCase(idx)){
                        indexFound = true;
                        for (Lesson lesson : index.getLessons()){
                            if (lesson.getClassType().equalsIgnoreCase(classType)){
                                lessonFound = true;
                                lesson.setGroup(group);
                                System.out.println("Update successful!");
                            }
                        }
                        if (!lessonFound){
                            System.out.println("Update fail. Lesson does not exist!");
                        }
                    }
                }
                if (!indexFound){
                    System.out.println("Update fail. Index does not exist!");
                }
            }
        }
        if (!courseFound){
            System.out.println("Update fail. Course does not exist!");
        }
        save();
    }

    /**
     * Get Course object from Course code.
     * @param courseCode Course code
     * @return Course object
     */
    // retrieve course code
    public Course selectCourseCodeAdmin(String courseCode){
        boolean courseFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equals(courseCode)){
                courseFound = true;
                return course;
            }
        }
        if (!courseFound){
            System.out.println("Course does not exist! Please select an existing course.");    
            }
        return null;
    }

    /**
     * Get Index object from Course Index.
     * @param courseIndex
     * @return Index object
     */
    // retieve course index
    public Index selectCourseIndexAdmin(String courseIndex){
        boolean indexFound = false;
        for (Course course : ntu.getCourse()){
            for (Index index : course.getIndexes()){
                if (index.getCourseIndex().equals(courseIndex)){
                    indexFound = true;
                    return index;
                }
            }
        }
        if (!indexFound){
            System.out.println("Index does not exist! Please select an existing Index.");           
        }
        return null;
    }

    /**
     * Delete a existing Course
     * @param course
     */
    //delete course in ntu database as well as from courses registered under student
    public void deleteCourse(Course course){
        List <Student> stud = new ArrayList<>();
        List <RegisteredCourses> coursedrop = new ArrayList<>();
        for (Index index : course.getIndexes()) {
            for (Student student : index.getExistingStudent()){
                for (RegisteredCourses rc : student.getIndexRegistered()){
                    if (rc.getIndex().equals(index)){
                        stud.add(student);
                        coursedrop.add(rc);
                    }
                }
            }
        }            
        for (Student s : stud){
            for (RegisteredCourses c : coursedrop){
                s.dropIndexRegistered(c); 
            }
        }      
        ntu.deleteCourse(course); 
        System.out.println("Course and respective Indexes deleted Successfully!"); 
        save();
    }

    /**
     * Delete existing Index
     * @param course Course object
     * @param idx Index object
     */
    //delete index details from selected course and student
    public void deleteIndex(Course course,Index idx){
        List <Student> stud = new ArrayList<>();
        List <RegisteredCourses> coursedrop = new ArrayList<>();
            for (Index index : course.getIndexes()){
                for (Student student : index.getExistingStudent()){
                    for (RegisteredCourses rc : student.getIndexRegistered()){
                        if (rc.getIndex().equals(idx)){
                            stud.add(student);
                            coursedrop.add(rc);
                            }
                        }
                    }
                }          
        course.deleteIndexes(idx);
        for (Student s : stud){
            for (RegisteredCourses c : coursedrop){
                s.dropIndexRegistered(c); 
            } 
        }        
        save();
    }

    /**
     * Load serialized objects from file.
     */
    private static void load() {
        try (FileInputStream fis = new FileInputStream("data/NTU.ser");
        ObjectInputStream ois = new ObjectInputStream(fis)){
            ntu = (NTU) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            ntu = new NTU();
            System.out.println("File not found");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidClassException e) {
            ntu = new NTU();
            System.out.println("Invalid class exception");
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
    }

    /**
     * Save serialized objects into file
     */
    private static void save() {
        try( FileOutputStream fos = new FileOutputStream("data/NTU.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);){
            oos.writeObject(ntu);
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
