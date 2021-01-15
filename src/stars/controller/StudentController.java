package stars.controller;

import stars.model.Course;
import stars.model.Index;
import stars.users.Student;
import stars.users.User;

import stars.model.RegisteredCourses;
import stars.model.Lesson;
import stars.roster.NTU;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Represents a controller that interacts with the student and other class entities.
 */
public class StudentController {
    /**
     * Object that contains all other class entities.
     */
    private static NTU ntu;
    /**
     * Creates a StudentController object by passing a NTU object as reference. 
     * @param ntuRef
     */
    public StudentController(NTU ntuRef) {
        this.ntu = ntuRef;

    }

    /**
     * Prints out all courses.
     */
    public void printCourseCode(){
        System.out.println(
            "+-----------------------------------------------------------------------------------------------+");
        System.out.format("|%-12s|%-40s|%15s|%11s|%13s|\n", "Course Code", "Course Name", "School Initial",
            "Lesson Type", "Academic Unit");
        System.out.println(
            "+-----------------------------------------------------------------------------------------------+");
        for (Course course : ntu.getCourse()){
            System.out.format("|%-12s|%-40s|%15s|%11s|%13s|\n", course.getCourseCode(), course.getCourseName(),
            course.getSchool(), course.getCourseType(), course.getAU());
            }
        System.out.println(
                "+-----------------------------------------------------------------------------------------------+");
       
        System.out.println();
        System.out.println("Lesson Type");
        System.out.println("T1 = Tutorial/Seminar");
        System.out.println("T2 = Lecture + Tutorial/Seminar");
        System.out.println("T3 = Lecture + Tutorial/Seminar + Laboratory");
        System.out.println();
    }

    /**
     * Prints out all indexes related to the selected course.
     * @param courseCode
     *        selected course code number.
     * @return 1 if there is an error where course does not exist.
     */
    public int printAllCourseIndex(String courseCode){
        boolean courseFound = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equals(courseCode)){
                courseFound = true;
                System.out.println("+------------+");
                System.out.format("|%-12s|\n", "Course Index");
                System.out.println("+------------+");
                for (Index index : course.getIndexes()){
                    System.out.format("|%-12s|\n", index.getCourseIndex());
                }
                System.out.println("+------------+");
                return 0;
            }
        }
        if (!courseFound){
            System.out.println("Course does not exist!");
        }
        return 1;
    }

    /**
     * Prints out all indexes related to the selected course.
     * @param courseCode
     *        selected course code number.
     * @param student
     *         current student.
     * @return 1 if course is not found in database or if the student has already registered in this course.
     */
    public int printCourseIndex(String courseCode, Student student){
        boolean courseFound = false;
        boolean courseDuplicate = false;
        for (Course course : ntu.getCourse()){
            if (course.getCourseCode().equals(courseCode)){
                courseFound = true;
                for (RegisteredCourses rc : student.getIndexRegistered()){
                    if (rc.getIndex().getCourseCode().equalsIgnoreCase(courseCode)){
                        System.out.println("You have already registered in this course. You can only swap or change index.");
                        courseDuplicate = true;
                    }
                }
                if (!courseDuplicate){
                    System.out.println("+------------+");
                    System.out.format("|%-12s|\n", "Course Index");
                    System.out.println("+------------+");
                    for (Index index : course.getIndexes()){
                        System.out.format("|%-12s|\n", index.getCourseIndex());
                    }
                    System.out.println("+------------+");
                    return 0;
                }
            }
        }
        if (!courseFound){
            System.out.println("Course does not exist!");
        } 
        return 1;
    }

    /**
     * Prints out all lessons related to the selected index.
     * @param courseIndex
     *        selected course index number.
     * @return 1 if course index does not exist in the database.
     */
    public int printAllIndexLessons(String courseIndex){
        boolean indexFound = false;
        for (Course course : ntu.getCourse()){
            for (Index index : course.getIndexes()){
                if (index.getCourseIndex().equals(courseIndex)){
                    indexFound = true;
                    System.out.println("Course Name: " + index.getCourseName());
                    System.out.println("Course Index: " + index.getCourseIndex());
                    System.out.println("+---------------------------------------------------------------------------------------------------+");
                    System.out.format("|%-12s|%-20s|%12s|%12s|%12s|%12s|%12s|\n", "Class Type", "Day", "Start Time","End Time","Venue","Week","Group");
                    System.out.println("+---------------------------------------------------------------------------------------------------+");
                    for (Lesson les : index.getLessons()){
                        System.out.format("|%-12s|%-20s|%12s|%12s|%12s|%12s|%12s|\n", les.getClassType(), les.getDay(),
                        les.getStartTime(), les.getEndTime(), les.getVenue(),les.getWeek(), les.getGroup());
                   }
                    System.out.println("+---------------------------------------------------------------------------------------------------+");
                    return 0;
                }
            }
        }
        if (!indexFound){
            System.out.println("Index does not exist");
        }
        return 1;
    }

    /**
     * Prints out all lessons related to the selected index.
     * @param courseIndex
     *        selected course index number.
     * @param student
     *         current student.
     * @return 1 if course index does not exist in the database or if course index is not found in the student's registered courses.
     */
    public int printIndexlessons(String courseIndex, Student student){
        boolean indexFound = false;
        boolean indexNotRegistered = false;
        for (Course course : ntu.getCourse()){
            for (Index index : course.getIndexes()){
                if (index.getCourseIndex().equals(courseIndex)){
                    indexFound = true;
                    for (RegisteredCourses rc : student.getIndexRegistered()){
                        if (!rc.getIndex().getCourseIndex().equalsIgnoreCase(courseIndex)){
                            indexNotRegistered = true;
                            continue;
                        }
                        else{
                            indexNotRegistered = false;
                            System.out.println("Course Name: " + index.getCourseName());
                            System.out.println("Course Name: " + index.getCourseIndex());
                            System.out.println("+---------------------------------------------------------------------------------------------------+");
                            System.out.format("|%-12s|%-20s|%12s|%12s|%12s|%12s|%12s|\n", "Class Type", "Day", "Start Time","End Time","Venue","Week","Group");
                            System.out.println("+---------------------------------------------------------------------------------------------------+");
                            for (Lesson les : index.getLessons()){
                                System.out.format("|%-12s|%-20s|%12s|%12s|%12s|%12s|%12s|\n", les.getClassType(), les.getDay(),
                                les.getStartTime(), les.getEndTime(), les.getVenue(),les.getWeek(), les.getGroup());
                            }
                            System.out.println("+---------------------------------------------------------------------------------------------------+");
                            return 0;
                        }
                    }
                }
            }
        }
        if (indexNotRegistered){
            System.out.println("Course index not registered in respective student database.");
            return 1;
        }
        if (!indexFound){
            System.out.println("Index does not exist");
        }
        return 1;
    }

    /**
     * Return the index reference from selected course index to drop.
     * @param courseIndex selected course index number.
     * @param student Current student.
     * @return index reference.
     */
    public Index selectCourseIndextoDrop(String courseIndex, Student student){
        boolean indexFound = false;
        boolean indexNotRegistered = false;
        for (Course course : ntu.getCourse()){
            for (Index index : course.getIndexes()){
                if (index.getCourseIndex().equals(courseIndex)){
                    indexFound = true;
                    for (RegisteredCourses rc : student.getIndexRegistered()){
                        if (!rc.getIndex().getCourseIndex().equalsIgnoreCase(courseIndex)){
                            indexNotRegistered = true;
                            continue;
                        }
                        else{
                            return index;
                        }
                    }
                }
            }
        }
        if (indexNotRegistered){
            System.out.println("Course index not registered in respective student database.");
            return null;
        }
        if (!indexFound){
            System.out.println("Index does not exist.");
        }
        return null;
    }

    /**
     * Return the index reference from selected course index.
     * @param courseIndex
     *         selected course index number.
     * @return index reference.
     */
    public Index selectCourseIndex(String courseIndex){
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
            System.out.println("You are not registered in this index. Please select an index you are registered in.");
            
        }
        return null;
    }

    /**
     * Print selected index lessons and its respective vancancies and waitlist size. 
     * @param courseIndex
     *        selected course index number.
     * @return 1 if the index does not exist in the database.
     */
    public int checkVacancy(String courseIndex){
        int error = 1;
        boolean indexFound = false;
        for (Course course : ntu.getCourse()){
            for (Index index : course.getIndexes()){
                if (index.getCourseIndex().equals(courseIndex)){
                    indexFound = true;
                    error = printAllIndexLessons(courseIndex);
                    if (error == 1){
                        return error;
                    }
                    System.out.println("Vacancies available: " + index.getVacancy() + "/" + index.getSize());
                    System.out.println("Length of waitlist: " + index.getWaitListSize());
                    
                }
            }
        }
        if (!indexFound){
            System.out.println("Index does not exist.");
        }
        return error;
    }

    /**
     * Returns user reference base on username and password.
     * @param uname
     *        username
     * @param pwd
     *        password
     * @return user reference.
     */
    public User getStudent(String uname, String pwd) {
        for (User student : ntu.getUsers()){
          if (student.getUsername().equals(uname) && student.getPassword().equals(pwd)){
              return student;
          }
        }
        return null;
      }
    
    /**
     * Returns user reference base on username.
     * @param uname
     *        username.
     * @return user reference.
     */
    public User getReceiveMailStudent(String uname) {
        for (User student : ntu.getUsers()){
            if (student.getUsername().equals(uname)){
                return student;
            }
        }
        return null;
    }

    /**
     * Print all registered courses for student.
     * @param student
     *        current student.
     */
    public void printRegisteredCourses(Student student){
        for (RegisteredCourses rc : student.getIndexRegistered()){
            System.out.format("|%-12s|%-40s|%12s|%12s|%13s|%19s|\n", rc.getIndex().getCourseCode(), rc.getIndex().getCourseName(),
            rc.getIndex().getCourseIndex(), rc.getAU(), rc.getCourseType(), rc.getRegStatus());
        }
    }
    
    /**
     * Add course index for student.
     * @param index
     *        desired course index to be registered in.
     * @param student
     *        current student.
     */
    public void addIndex(Index index, Student student){
        if (index.getAU() < student.getMaxAU()-student.getCurrentAU()){
            for (RegisteredCourses rc : student.getIndexRegistered()){
                for (Lesson les : rc.getIndex().getLessons()){
                    if (!les.IsTimeClashWLessson(index.getLessons())){
                        continue;
                    }
                    else{
                        System.out.println("Time clash with your current course " + les.getCourseIndex() + ". Unable to add index!"); //later then add more details
                        return;
                    }
                }
            }
            if (index.getVacancy()>0){
                index.addExistingStudent(student);
                RegisteredCourses rc = new RegisteredCourses(student.getUsername(), "REGISTERED", "Core", index);
                student.setIndexRegistered(rc);
                System.out.println("Added Successfully!");
                //save();

            }
            else{
                System.out.println("There is no more vacancies, you will be added into the waitlist.");
                index.addWaitlist(student);
                //save();
            }           
        }
        else{
            System.out.println("Sorry you are not able to add any more courses as you have reached your Max AU.");
        }

        save();
    }

    /**
     * Dropping an index for a student from his/her current registered courses.
     * @param index
     *        Desired course index to be dropped.
     * @param student
     *        Current student.
     */
    public void dropIndex(Index index, Student student){
        index.dropExisitingStudent(student);
        index.dropWaitList();
        RegisteredCourses coursedrop = new RegisteredCourses();
        for (RegisteredCourses rc : student.getIndexRegistered()){
            if (rc.getIndex().equals(index)){
                coursedrop = rc;
                
            }
        }
        student.dropIndexRegistered(coursedrop); 
        save();
    }

    /**
     * Change course index to another one of the same course code for a student.
     * @param currentIndex
     *        Current course index number.
     * @param newIndex
     *        New course index number.
     * @param student
     *        Current student.
     */
    public void changeIndex(String currentIndex, String newIndex, Student student){ //index here represents the index i want to swap with
        Index curIndex = new Index();
        Index replaceIndex = new Index();
        curIndex = selectCourseIndex(currentIndex);
        replaceIndex = selectCourseIndex(newIndex);
        if (curIndex.getCourseCode().equals(replaceIndex.getCourseCode())){
            for (RegisteredCourses rc : student.getIndexRegistered()){
                if (curIndex.equals(rc.getIndex())){
                    continue;}
                else{
                    for (Lesson les : rc.getIndex().getLessons()){
                        if (!les.IsTimeClashWLessson(replaceIndex.getLessons())){
                            continue;
                        }
                        else{
                            System.out.println("Time clash with your current course " + les.getCourseIndex() + ". Unable to change index."); //later then add more details
                            return;
                        }
                    }
                }
            }
        
          if (replaceIndex.getVacancy()>0){
              dropIndex(curIndex, student);
              replaceIndex.addExistingStudent(student);
              RegisteredCourses rc = new RegisteredCourses(student.getUsername(), "REGISTERED", "Core", replaceIndex);
              student.setIndexRegistered(rc);  
              System.out.println("Changed Successfully!");
          }
          else{
              System.out.println("Sorry there are no more vacancies, you are recommended to drop your current course index before enrolling into your preferred course index waitlist.");
          }

        }
        else {
            System.out.println("The index you are trying to change into is not the same course as your current one.");
        }
        save();
    }

    /**
     * Swap Index with another student.
     * @param student1
     *        Current student.
     * @param student2
     *        Student whom you are swapping with.
     * @param courseIndex1
     *        Current course index number.
     * @param courseIndex2
     *        Desired course index number to swap.
     */
    public void swapIndex(Student student1, Student student2, String courseIndex1, String courseIndex2){
        Index index1 = new Index();
        Index index2 = new Index();
        index1 = selectCourseIndex(courseIndex1);
        index2 = selectCourseIndex(courseIndex2);
        if (index1.getCourseCode().equals(index2.getCourseCode())){
            for (RegisteredCourses rc : student1.getIndexRegistered()){
                if (index1.equals(rc.getIndex())){
                    continue;}
                else{
                    for (Lesson les : rc.getIndex().getLessons()){
                        if (!les.IsTimeClashWLessson(index2.getLessons())){
                            continue;
                        }
                        else{
                            System.out.println("Time clash with your current course " + les.getCourseIndex() + " and " + index2.getCourseIndex() + ". Unable to swap index with peer"); //later then add more details
                            return;
                        }
                    }
                }
            }

            for (RegisteredCourses rc : student2.getIndexRegistered()){
                if (index2.equals(rc.getIndex())){
                    continue;}
                else{
                    for (Lesson les : rc.getIndex().getLessons()){
                        if (!les.IsTimeClashWLessson(index1.getLessons())){
                            continue;
                        }
                        else{
                            System.out.println("Time clash with your current course " + les.getCourseIndex() + " and " + index1.getCourseIndex() + ". Unable to swap index with peer"); //later then add more details
                            return;
                        }
                    }
                }
            }
            
            //swapping students in index
            index1.dropExisitingStudent(student1);
            index2.dropExisitingStudent(student2);
            index1.addExistingStudent(student2);
            index2.addExistingStudent(student1);
            //cannot dropIndex() for students cos will initiate drop waitlist which i do not want here.
            //swapping for students in their registeredCourses 
            RegisteredCourses course1 = new RegisteredCourses();
            RegisteredCourses course2 = new RegisteredCourses();
            for (RegisteredCourses rc : student1.getIndexRegistered()){
                if (rc.getIndex().equals(index1)){
                    course1 = rc;
                }
            } 
            student1.dropIndexRegistered(course1);
            for (RegisteredCourses rc : student2.getIndexRegistered()){
                if (rc.getIndex().equals(index2)){
                    course2 = rc;
                }
            }
            student2.dropIndexRegistered(course2);
            RegisteredCourses rc1 = new RegisteredCourses(student1.getUsername(), "REGISTERED", "Core", index2);
            student1.setIndexRegistered(rc1);

            RegisteredCourses rc2 = new RegisteredCourses(student2.getUsername(), "REGISTERED", "Core", index1);
            student2.setIndexRegistered(rc2);
            System.out.println("Swapped Successfully!");
    }
    else{
        System.out.println("The index you are trying to swap is not the same course as your current one.");
    }
        save();

    }

    /**
     * Save serialized objects into file.
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


