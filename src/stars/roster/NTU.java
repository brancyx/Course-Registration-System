package stars.roster;

import stars.model.AccessPeriod;
import stars.model.Course;
import stars.users.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents NTU object that composes of all the Users, all the Course and the access periods of every school.
 */
public class NTU implements Serializable {

    /**
     * Unique SerializationID for NTU.
     */
    private static final long serialVersionUID = 6529685098267757699L;

    /**
     * Array list of Users in NTU.
     */
    private ArrayList<User> users = new ArrayList<>();

    /**
     * Array list of Courses in NTU.
     */
    private ArrayList<Course> course = new ArrayList<>();

    /**
     * Hash map of Access Periods for every school in NTU.
     */
    private HashMap<String, AccessPeriod> accessPeriod = new HashMap <>();

    /**
     * Creates NTU.
     */
    public NTU(){
        for (SchoolCode sc : SchoolCode.values()){
            // set a default access period for all school
            AccessPeriod ap = new AccessPeriod(sc.name(), LocalDateTime.parse("2020-11-01T00:00"),
                    LocalDateTime.parse("2020-12-31T23:59"));
            accessPeriod.put(sc.name(), ap);
        }
    }

    /**
     * Adds User into NTU.
     * 
     * @param u
     * User of the system. User can be an Admin or a Student.
     */
    public void addUsers(User u){
        users.add(u);
    }

    /**
     * Adds Course into NTU.
     *
     * @param school School of course e.g NBS/SCSE/CEE
     * @param courseCode Course code e.g CZ2002
     * @param courseName Course name e.g Object Oriented Programming
     * @param courseType Type of Course e.g T1/T2/T3
     * @param au Academic Unit
     */
    public void addCourse(String school, String courseCode,String courseName,String courseType, int au){
        Course newCourse = new Course(school, courseCode,courseName,courseType,au);
        course.add(newCourse);
    }

    /**
     * Deletes Course from NTU.
     * 
     * @param c
     * Course available for enrollment by Students in NTU.
     */ 
    public void deleteCourse(Course c){
        course.remove(c);
    }

    /**
     * Gets array list of Courses in NTU.
     * 
     * @return Array list of Courses.
     */
    public ArrayList<Course> getCourse(){
        return course;
    }

    /**
     * Gets array list of Users in NTU.
     * 
     * @return Users in NTU, either Admin or Student.
     */
    public ArrayList<User> getUsers(){
        return users;
    }

    /**
     * Gets access period of respective school in NTU.
     * 
     * @param sch
     * Schools in NTU.
     * 
     * @return Access period of the School.
     */
    public AccessPeriod getAccessPeriod(String sch){
        AccessPeriod ap = accessPeriod.get(sch);
        return ap;
    }

    /**
     * Updates the access period of the school.
     * 
     * @param sch
     * School in NTU.
     * @param as
     * Start of access period.
     * @param ae
     * End of access period.
     */
    public void updateAccessPeriod(String sch, LocalDateTime as, LocalDateTime ae){
        AccessPeriod ap = accessPeriod.get(sch);
        ap.setAccessStart(as);
        ap.setAccessEnd(ae);
    }

    /**
     * Sets the access period of the school in NTU.
     * 
     * @param sch
     * School in NTU.
     * @param ap
     * Access period of school.
     */
    public void setAccessPeriod(String sch, AccessPeriod ap) {
        accessPeriod.put(sch,ap);
    }
}
