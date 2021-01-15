package stars.controller;
import stars.view.AdminView;
import stars.view.StudentView;

import stars.model.AccessPeriod;
import stars.roster.NTU;
import stars.users.*;

import java.time.LocalDateTime;
import java.security.*; 
import java.io.IOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Represents a controller which will be used at the Login Page. It contains
 * functions which enable a user to sucessfully log in to MySTARS system or
 * change password.
 * 
 * @author
 *
 */
public class LoginController {
  /**
   * NTU object which contains methods to retrieve data from binary file.
   */
  private static NTU ntu;
  /**
   * List of students retrieved from database.
   */
  private ArrayList<Student> StudentList;
  /**
   * List of admin users retrieved from database.
   */
  private ArrayList<Admin> AdminList;
  /**
   * Salt used for password hashing.
   */
  private static final String SALT = "mySTARSSALT";
  /**
   * Variable used to store User's username.
   */
  private String username;
  /**
   * Variable used to store User's password.
   */
  private String password;
  /**
   * Variable used to store User's account type.
   */
  private String usertype;

  /**
   * Base Constructor called by LoginView class to instantiate a LoginController.
   */
  public LoginController() {
    // Loads the data stored in the binary file.
    File directory = new File("data");
    if (!directory.exists()) {
        directory.mkdir();
    }
    load();
  }
  
  /**
   * Another Constructor that overloads the base constructor called by LoginView class
   * to instantiate a LoginController.
   * 
   * @param uname
	 *            User's entered username.
   * @param pwd
	 *            User's entered password.
   * @param utype
	 *            User's entered account type.
   */
  public LoginController(String uname, String pwd, String utype ) {
    this.username = uname;
    this.password = generateHashedPassword(pwd); // Hashing plain password
    this.usertype = utype;
    // Loads data stored in the binary file.
    File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        load();
  }

  /**
	 * A method called by a user at the Login Page to log in to the system.
	 * 
	 */
  public void Login() {
    if (authenticateUser(username, password, usertype)) {
      if (usertype.equals("Student")) {
        Student student = getStudent(username, password);
        AccessPeriod accessPeriod = ntu.getAccessPeriod(student.getSchool());

        if (!accessPeriod.validateAccessPeriod()){
          System.out.println("Access denied! You are only allowed to access in your allocated time slot " +
                  accessPeriod.getAccessStart() + " - " + accessPeriod.getAccessEnd());
        } else{
          StudentView sv = new StudentView(ntu);
          sv.displayStudMenu(student);
        }
      }
      else if (usertype.equals("Admin")) {
        AdminView av = new AdminView();
        av.displayAdminMenu();
      }
    }
    else System.out.println("Invalid User/Password.");
  }

  /**
	 * A method called by the Login() and changePassword() methods to verify 
   * a user's credentials before letting them proceed.
	 * 
	 * @param userName
	 *            User's entered username.
   * @param pwd
   *            User's entered password.
   * @param utype
   *            User's entered account type.
   * @return true or false.
	 */
  public boolean authenticateUser(String userName, String pwd, String utype) {
    if (utype.equalsIgnoreCase("Student")) {
      for (Student student : getAllStudent()) {
        if (userName.equals(student.getUsername()) && pwd.equals(student.getPassword())) {
          System.out.println("VALID STUDENT FOUND");
          return true;
        }
      }
      return false;
    }

    else if (utype.equalsIgnoreCase("Admin")) {
      for (Admin admin : getAllAdmin()) {
        if (userName.equals(admin.getUsername()) && pwd.equals(admin.getPassword())) {
          System.out.println("VALID ADMIN FOUND");
          return true;
        }
      }
      return false;
    }

    System.out.println("Invalid User Type.");
    return false;
  }

  /**
	 * A method called by a user to change password.
	 * 
	 * @param userName
	 *            User's entered username.
   * @param pwd
   *            User's entered password.
   * @return true or false.
	 */
  public boolean changePassword(String userName, String pwd) {
    String newPwd = generateHashedPassword(pwd);
    for (User user : ntu.getUsers()) {
      //if username is found in database, change password and return true
      if (userName.equals(user.getUsername())) {
        user.setPassword(newPwd);
        save();
        return true;
      }
    }
    return false;
  }

  /**
	 * A method called by the authenticateUser() method to retrieve all students
   * from the database.
	 * 
   * @return list of student objects.
	 */
  public ArrayList<Student> getAllStudent() {
    StudentList = new ArrayList<Student>();
    for (User user : ntu.getUsers()){
      if (user instanceof Student){
          StudentList.add((Student) user);
      }
    }
    return StudentList;
  }

  /**
   * A method called by the Login() method to retrieve a specific student object 
   * from the database
   * 
   * @param uname
	 *            User's username.
   * @param pwd
	 *            User's password.
   * @return a student object.
   */
  public Student getStudent(String uname, String pwd) {
    for (Student student : getAllStudent()){
      if (uname.equals(student.getUsername()) && pwd.equals(student.getPassword())){
          return student;
      }
    }
    return null;
  }

  /**
   * A method called by the authenticateUser method to retrieve all admin users
   * from the database
   * 
   * @return list of admin user objects
   */
  public ArrayList<Admin> getAllAdmin() {
    AdminList = new ArrayList<Admin>();
    for (User user : ntu.getUsers()){
      if (user instanceof Admin){
          AdminList.add((Admin) user);
      }
    }
    return AdminList;
  }

  /**
   * A method called by generateHashedPassword() method to generate Hashed strings.
   * 
   * @param input
	 *            String input to be hashed.
   * @return a hashed string.
   */
  private static String generateHash(String input) {
    	StringBuilder hash = new StringBuilder();
  
    	try {
    		MessageDigest sha = MessageDigest.getInstance("SHA-256"); // use SHA-256 Algorithm
    		byte[] hashedBytes = sha.digest(input.getBytes());
    		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    		for (int idx = 0; idx < hashedBytes.length; idx++) {
    			byte b = hashedBytes[idx];
    			hash.append(digits[(b & 0xf0) >> 4]);
    			hash.append(digits[b & 0x0f]);
    		}
    	} catch (NoSuchAlgorithmException e) {
    	// failed to generate hash
      }
      return hash.toString();
    }
  
  /**
   * A method called by LoginController() constructor and changePassword() method to
   * hash users' input passwords when they log in or change password.
   * 
   * @param password
	 *            User's entered password.
   * @return a hashed password.
   */
  public static String generateHashedPassword(String password) {
    String saltedPassword = SALT + password;
    String hashedPassword = generateHash(saltedPassword);
    return hashedPassword;
  }

  /**
   * A method called by LoginController() to load the stored data in a binary file.
   */
  private static void load() {
    try (FileInputStream fis = new FileInputStream("data/NTU.ser");
    ObjectInputStream ois = new ObjectInputStream(fis);){
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
   * A method called by changePassword() method to save the updated password
   * into a binary file.
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
