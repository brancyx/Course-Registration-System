package stars.model;

import stars.users.Student;
import stars.controller.emailController;

import java.io.Serializable;
import java.util.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an index a Course has. An Index has a queue of waitList, 
 * have many Lessons and can have many students registered in it.
 * 
 * @author
 *
 */
public class Index implements Serializable {
    /**
     *  Unique SerializationID for index
     */ 
    private static final long serialVersionUID = 6529685098267757697L;
    /**
     * The index number of this Index.
     */
    private String courseIndex;
    /**
     * The course code which this Index belongs to.
     */
    private String courseCode;
    /**
     * The course name of the index.
     */
    private String courseName;
    /**
     * The number of Academic Units this index has.
     */
    private int AU;
    /**
     * The total number of students this Index can have.
     */
    private int size;
    /**
     * The number of empty slot available for students in this Index.
     */
    private int vacancy;
    /**
     * The number of exisiting students in the index.
     */
    private int existingstudentsize;
    /**
     * All the students registered in this index.
     */
    private ArrayList<Student> existingStudents;
    /**
     * All the Lessons this Index have.
     */
    private ArrayList <Lesson> lessons;
    /**
     * Students in the index waitlist.
     */
    private Queue <Student> waitList;

    /**
     * Creates a new Index.
     */
    public Index(){existingStudents =  new ArrayList<>();
        lessons = new ArrayList<>();
        waitList =  new LinkedList<>();}

    /**
     * Creates a new Index
     * @param ci Course Index.
     * @param cc Course Code.
     * @param cn Course Name.
     * @param au Academic Units.
     * @param s Index size.
     * @param vac Index vacancy.
     * @param es Number of exisiting students registerd in the index.
     */
    public Index(String ci, String cc, String cn, int au, int s, int vac, int es){
        courseIndex = ci;
        size = s;
        courseCode = cc;
        courseName = cn;
        AU = au;
        vacancy = vac;
        existingstudentsize = es;
        existingStudents =  new ArrayList<>();
        lessons = new ArrayList<>();
        waitList =  new LinkedList<>();
    }

    /**
     * Gets the index number of this Index.
     * @return this Index's index number.
     */
    public String getCourseIndex(){
        return courseIndex;
    }

    /**
     * Changes the index number of this Index.
     * @param ci this Index's new index number.
     */
    public void setCourseIndex(String ci){
        courseIndex = ci;
    }

    /**
     * Gets the total number of students this Index can have.
     * @return this Index's size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Changes the number of student this Index can have.
     * @param s this Index's new size.
     */
    public void setSize(int s){
        size = s;
    }

    /**
     * Gets the course code which this Index belongs to.
     * @return this Index's course code.
     */
    public String getCourseCode(){
        return courseCode;
    }

    /**
     * Changes the course code of this Index.
     * @param cc this Index's new course code.
     */
    public void setCourseCode(String cc){
        courseCode = cc;
    }

    /**
     * Gets the course name of this Index.
     * @return this Index's course name
     */
    public String getCourseName(){
        return courseName;
    }

    /**
     * Changes the course name of this Index.
     * @param name this Index's new course name.
     */
    public void setCourseName(String name){
        courseName = name;
    }

    /**
     * Gets the academic units of this Index.
     * @return this Index's academic units.
     */
    public int getAU(){
        return AU;
    }

    /**
     * Changes the academic units of this Index.
     * @param au this Index's new academic units.
     */
    public void setAU(int au){
        AU = au;
    }

    /**
     * Gets the number of empty slot available for students in this Index.
     * @return this Index's Vacancy.
     */
    public int getVacancy(){
        return vacancy;
    }

    /**
     * Changes the number of empty slot of this Index.
     * @param v  this Index's new vacancy.
     */
    public void setVacancy(int v){
        vacancy = v;
    }
    
    /**
     * Changes the number of students who have added this Index.
     * @param es this Index's new number of students.
     */
    public void setExistingStudentNumber(int es){
        existingstudentsize = es;
    }


    /**
     * Gets the number of students who have registered in this Index.
     * @return this Index's number of students.
     */
    public int getExistingStudentNumber(){
        return existingstudentsize;
    }

    /**
     * Gets the list of students who have registerd in this index.
     * @return a list of student who have registerd in this index.
     */
    public List<Student> getExistingStudent(){
        return existingStudents;
    }

    /**
     * Add a student into this Index and minus one from vacancy
     * and add one to exisiting student size.
     * @param student New Student.
     */
    public void addExistingStudent(Student student){
        vacancy -= 1;
        existingstudentsize += 1;
        existingStudents.add(student);
    }
    
    /**
     * Drop a student from this Index and add one to vacancy
     * and minus one from exisiting student size.
     * @param student Student to be dropped.
     */
    public void dropExisitingStudent(Student student){
        vacancy += 1;
        existingstudentsize -= 1;
        existingStudents.remove(student);
    }

    /**
     * Gets the lessons this Index have.
     * @return this Index's Lessons.
     */
    public List <Lesson> getLessons(){
        return lessons;
    }

    /**
     * Get a queue of students in the waitlist.
     * @return a queue of students in the waitlist.
     */
    public Queue <Student> getWaitlist(){
        return waitList;
    }
    
    /**
     * Gets the number of students in the waitlist.
     * @return the number of students in the waitlist.
     */
    public int getWaitListSize(){
        return waitList.size();
    }
    
    /**
     * Removing the first student from the waitlist.
     */
    public void dropWaitList(){
        // First check if the number of vacancy is more than 0 and if there are students in the waitlist.
        if (vacancy>0 && this.getWaitListSize() > 0){
            Student student = waitList.remove();
            // Add student from waitlist into this Index.
            this.addExistingStudent(student);
            // Change registered status from "waitlist" to "registered".
            RegisteredCourses regC = new RegisteredCourses();
            for ( RegisteredCourses rc : student.getIndexRegistered()){
                if (rc.getIndex().equals(this)){
                    regC = rc;
                }
            }
            regC.setRegStatus("Registered");
            System.out.println("Dropping...");
            // Sent an email notification to student to notify him/her that he/she is being added into the Index.
            emailController.sendNotification(student.getEmail(), student.getName(), this);
        }
    }

    /**
     * Adding a student into the waitlist of this Index.
     * @param student New student.
     */
    public void addWaitlist(Student student){
        waitList.add(student);
        RegisteredCourses rc = new RegisteredCourses(student.getUsername(), "WAITLIST", "Core", this);
        student.setIndexRegistered(rc);
    }

    /**
     * Changes the Lessons of this Index.
     * @param idx Course Index.
     * @param ct Course Type.
     * @param d Day.
     * @param st Start Time.
     * @param et End Time.
     * @param ve Venue.
     * @param w Week.
     * @param g Group.
     */
   public void setLessons(String idx, String ct, String d, LocalTime st, LocalTime et, String ve, String w, String g){
        Lesson lesson = new Lesson(idx,ct,d,st,et,ve,w,g);
        lessons.add(lesson);
   }


}
