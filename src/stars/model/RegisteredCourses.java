package stars.model;

import java.io.Serializable;

/**
 * Represents the courses that are registered for a student that includes
 * account ID, registered status of the course, registered course type
 * and the course Index.
 */
public class RegisteredCourses implements Serializable{
    /**
     * Unique Serialized ID for Registered Course.
     */
    private static final long serialVersionUID =  6529685098267757666L;
    /**
     * Account ID for student or student username.
     */
    private String accountID;
    /**
     * Registered status of course index - "Waitlist" or "Registered"
     */
    private String regStatus;
    /**
     * Registered course type.
     */
    private String regCourseType;
    /**
     * Course Index object that the student registered in.
     */
    private Index index;

    /**
     * Create a new RegisteredCourse
     */
    public RegisteredCourses(){}

    /**
     * Create a new RegisteredCourse
     * @param a Account ID.
     * @param rs Registered Status.
     * @param ct Course Type.
     * @param i Index that student is registered in.
     */
    public RegisteredCourses(String a, String rs, String ct, Index i){
        accountID = a;
        regStatus = rs;
        regCourseType = ct; 
        index = i;
    }

    /**
     * Get the accountID of the student.
     * @return this Student's account ID.
     */
    public String getAccountID(){
        return accountID;
    }

    /**
     * Get the registered status of the course.
     * @return this Student's registered status.
     */
    public String getRegStatus(){
        return regStatus;
    }

    /**
     * Get the course type of the course.
     * @return this Student's registered status.
     */
    public String getCourseType(){
        return regCourseType;
    }

    /**
     * Get the academic units of this Index.
     * @return this Index's academic units.
     */
    public int getAU(){
        return index.getAU();
    }

    /**
     * Get the index object.
     * @return this Student's index.
     */
    public Index getIndex(){
        return index;
    }

    /**
     * Change the account ID for the student.
     * @param a New Account ID.
     */
    public void setAccountID(String a){
        accountID = a;
    }


    /**
     * Change the registered status of an index for the student.
     * @param rs New registered status.
     */
    public void setRegStatus(String rs){
       regStatus = rs;
    }

    /**
     * Change the course type of the course for the student.
     * @param ct New course type for student
     */
    public void setCourseType(String ct){
        regCourseType = ct;
    }

    /**
     * Change the course index for the student by passing an Index reference
     * @param ind New Index object
     */
    public void setIndex(Index ind){
        index = ind;
    }

}
