package stars.users;

import java.io.Serializable;
import java.util.*;
import stars.users.User;
import stars.model.Course;
import stars.model.Index;
import stars.model.RegisteredCourses;

/**
 * Represents a Student which is User of this STARS system.
 */
public class Student extends User implements Serializable {

  /**
   * Unique SerializationID for this Student.
   */
  private static final long serialVersionUID = 6529685098267757111L;

  /**
   * Nationality of this Student.
   */
  private String nationality;

  /**
   * Year of study of this Student.
   */
  private int studyYear;

  /**
   * School of this Student.
   */
  private String school;

  /**
   *  Programme of this Student.
   */
  private String program;

  /**
   * Matriculation number of this Student.
   */
  private String matricNum;

  /**
   * Email address of this Student.
   */
  private String email;

  /**
   * Mobile number of this Student.
   */
  private Long mobileNum;

  /**
   * Current total AUs of courses registered for this Student.
   */
  private int currentTotalAU;

  /**
   * Maximum AUs of courses registered for this Student.
   */
  private final int MAX_AU = 22;

  /**
   * List of indexes registered for this Student.
   */
  private List <RegisteredCourses> indexRegistered;

  /**
   * Creates a Student.
   */
  public Student(){super(); currentTotalAU = 0; indexRegistered = new ArrayList<RegisteredCourses>();}

  /**
   * Creates a Student with details.
   * 
   * @param n
   * Name of tis Student.
   * @param u
   * Username of this Student.
   * @param pass
   * Password of this Student.
   * @param acc
   * Account of this Student.
   * @param g
   * Gender of this Student.
   * @param nation
   * Nationality of this Student.
   * @param yr
   * Year of study of this Student.
   * @param sch
   * School of this Student.
   * @param prog
   *  of this Student.
   * @param matric
   * Matriculation number of this Student.
   * @param emai
   * Email address of this Student.
   * @param mobile
   * Mobile number of this Student.
   */
  public Student(String n, String u, String pass, char acc, String g, String nation, int yr, String sch,  String prog,
                  String matric, String emai, Long mobile) {
    super(n, u, pass, acc, g);
    nationality = nation;
    studyYear = yr;
    program = prog;
    school = sch;
    matricNum = matric;
    email = emai;
    mobileNum = mobile;
    currentTotalAU = 0;
    indexRegistered = new ArrayList<RegisteredCourses>();
  }

  /**
   * Sets this nationality of this Student.
   * 
   * @param nat
   * New Nationality of this Student.
   */
  public void setNationality(String nat) {
    this.nationality = nat;
  }

  /**
   * Gets this nationality of this Student.
   * 
   * @return Nationality of this Student.
   */
  public String getNationality() {
    return nationality;
  }

  /**
   * Sets this year of study of this Student.
   * 
   * @param sy
   * New year of study of this Student.
   */
  public void setStudyYear(int sy) {
    this.studyYear = sy;
  }

  /**
   * Gets year of study of this Student.
   * 
   * @return Year of study of this Student.
   */
  public int getStudyYear() {
    return studyYear;
  }

  /**
   * Sets school of this Student.
   * 
   * @param sch
   * New school of this Student.
   */
  public void setSchool(String sch) {
      this.school = sch;
  }

  /**
   * Gets school of this Student.
   * 
   * @return School of this Student.
   */
  public String getSchool(){
      return school;
  }
  
  /**
   * Sets Program of this Student.
   * 
   * @param prog
   * New Program of this Student.
   */
  public void setProgram(String prog) {
    this.program = prog;
  }
  
  /**
   * Gets Program of this Student.
   * 
   * @return Program of this Student.
   */
  public String getProgram() {
    return program;
  }

  /**
   * Sets this matriculation number of this Student.
   * 
   * @param mat
   * New matriculation number of this Student.
   */
  public void setMatricNum(String mat) {
    this.matricNum = mat;
  }

  /**
   * Gets matriculation number of this Student.
   * 
   * @return Matriculation number of this Student.
   */
  public String getMatricNum() {
    return matricNum;
  }

  /**
   * Sets email address of this Student.
   * 
   * @param e
   * New email address of this Student.
   */
  public void setEmail(String e) {
    this.email = e;
  }

  /**
   * Gets email address of this Student.
   * 
   * @return Email address of this Student.
   */
  public String getEmail() {
    return email;
  }
  
  /**
   * Sets mobile number of this Student.
   *
   * @param m
   * New mobile number of this Student.
   */
  public void setMobileNum(Long m) {
    this.mobileNum = m;
  }

  /**
   * Gets mobile number of this Student.
   * 
   * @return Mobile number of this Student.
   */
  public Long getMobileNum() {
    return mobileNum;
  }

  /**
   * Gets current AUs of courses registered for this Student.
   * 
   * @return Current AUs of courses registered for this Student.
   */
  public int getCurrentAU(){
    return currentTotalAU;
  }

  /**
   * Gets maximum AUs of courses registered for this Student.
   * 
   * @return Maximum AUs of courses registered for this Student.
   */
  public int getMaxAU(){
    return MAX_AU;
  }
  
  /**
   * Sets the current AUs of courses registered for this Student.
   * 
   * @param au
   * New current AUs of courses registered for this Student.
   */
  public void setCurrentAU(int au){
    currentTotalAU += au;
  }

  /**
   * Changes the list of indexes by adding a new index for this Student.
   * 
   * @param index
   * New index added into current list of indexes for this Student.
   */
  public void setIndexRegistered(RegisteredCourses index){
    indexRegistered.add(index);
  }

  /**
   * Changes the list of indexes by dropping a current index for this Student.
   * 
   * @param index
   * Index dropped from current list of indexes for this Student.
   */
  public void dropIndexRegistered(RegisteredCourses index){
    indexRegistered.remove(index);
  }
  
  /**
   * Gets list of indexes registered for this Student.
   * 
   * @return List of indexes registered for this Student.
   */
  public List<RegisteredCourses> getIndexRegistered(){
    return indexRegistered; 
  }

  /**
   * Changes string to uppercase.
   */
  public String toString(){
      return matricNum.toUpperCase() + ": " + getName() +  " " + program + " Year:" + studyYear;
  }
}


