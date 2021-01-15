package stars.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course in NTU. A Course can have many Indexes.
 * 
 * @author
 *
 */
public class Course implements Serializable {
    /**
     * Unique SerializationID for course.
     */
    private static final long serialVersionUID = 6529685098267757698L;
    /**
     * The school which this Course belongs to.
     */
    private String school;
    /**
     * The unique code of this Course.
     */
    private String courseCode;
    /**
     * The full course name of this Course.
     */
    private String courseName;
    /**
     * The course type of this Course. The course type can be "T1" tutorial/seminar only,
	 * "T2" lecture and tutorial/seminar, "T3" lecture, laboratory and tutorial/seminar.
     */
    private String courseType;
    /**
     * The academic unit of this Course.
     */
    private int au;
    /**
     * The Indexes under this Course.
     */
    private ArrayList<Index> index;

    /**
     * Creates a course.
     */
    public Course(){index = new ArrayList<Index>();}

    /**
     * Creates a course.
     * @param sch School Name.
     * @param cc Course code.
     * @param cn Course Name.
     * @param ct Course Type.
     * @param AU Academic Units.
     */
    public Course(String sch, String cc, String cn, String ct, int AU){
        school = sch;
        courseCode = cc;
        courseName = cn;
        courseType = ct;
        au = AU;
        index = new ArrayList<Index>();
        //https://www.geeksforgeeks.org/association-composition-aggregation-java/ check under libary how to extract the books in main method
    }

    /**
     * Gets School which this Course belong to.
     * @return this Course's School.
     */
    public String getSchool(){
        return school;
    }

    /**
     * Changes the School of this Course.
     * @param sch this Course's new School.
     */
    public void setSchool(String sch){
        school = sch;
    }

    /**
     * Gets the course code of this Course.
     * @return this Course's course code.
     */
    public String getCourseCode(){
        return courseCode;
    }

    /**
     * Changes the code of this Course.
     * @param cc this Course's new code.
     */
    public void setCourseCode(String cc){
        this.courseCode = cc;
    }

    /**
     * Gets the name of this Course.
     * @return this Course's full name.
     */
    public String getCourseName(){
        return courseName;
    }

    /**
     * Changes the full course name of this Course.
     * @param cn this Course's new course name.
     */
    public void setCourseName(String cn){
        this.courseName = cn;
    }

    /**
     * Gets the course type of this Course.
     * @return this Course's type.
     */
    public String getCourseType(){
        return courseType;
    }

    /**
     * Changes the course type of this Course.
     * @param ct this Course's new type.
     */
    public void setCourseType(String ct){
        this.courseType = ct;
    }

    /**
     * Gets the academic unit of this Course.
     * @return this Course academic unit.
     */
    public int getAU(){
        return au;
    }

    /**
     * Changes the academic unit of this Course.
     * @param AU this Course's new academic unit.
     */
    public void setAU(int AU){
        this.au = AU;
    }

    /**
     * Gets a list of Indexes that this Course have.
     * @return this Course's list of Indexes.
     */
    public List<Index> getIndexes(){
        return index;
    }
    
    /**
     * Add new courses to the course by creating the index object within the course object
     * @param ci New course index.
     * @param cc New course code.
     * @param cn New course name.
     * @param au New Academic Units.
     * @param s New index class size.
     * @param vac New index class vacancy.
     * @param es New index number of exisiting student size.
     */
    public void addIndexes(String ci, String cc, String cn, int au, int s, int vac, int es){
        Index ind = new Index(ci, cc, cn, au, s, vac, es);
        index.add(ind);
    }

    /**
     * Deleting an Index from the list of indexes.
     * @param ind Index to be deleted.
     */
    public void deleteIndexes(Index ind){
        index.remove(ind);
    }

}
