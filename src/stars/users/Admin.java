package stars.users;

import stars.controller.AdminController;
import stars.controller.StudentController;
import stars.users.User;

/**
 * Represents an administrator which is a User of the STARS system.
 */
public class Admin extends User {

    /**
     * Staff number of this Admin.
     */
    private String staffNo;

    /**
     * Email address of this Admin.
     */
    private String email;

	/**
	 * Creates an Admin.
	 */
    public Admin(){
        super();
    }
    
    /**
     * Creates an Admin with details.
     * 
     * @param n
     * Name of this Admin.
     * @param u
     * Username of this Admin.
     * @param pass
     * Password of this Admin.
     * @param acc
     * Account of this Admin.
     * @param g
     * Gender of this Admin.
     * @param s
     * Staff number of this Admin.
     * @param email
     * Email address of this Admin.
     */
    public Admin(String n, String u, String pass, char acc, String g, String s, String email){
        super(n, u, pass, acc, g);
        this.staffNo = s;
        this.email = email;
    }

	/**
	 * Gets the staff number of this Admin.
	 * 
     * @return Staff number of this Admin.
	 */
    public String getStaffNo(){
        return staffNo;
    }

    /**
	 * Sets the staff number of this Admin.
     *
	 * @param s New staff number of this Admin.
	 */
    public void setStaffNo(String s){
        staffNo = s; 
    }

	/**
	 * Gets the staff email of this Admin.
	 * 
	 * @return Email address of this Admin.
	 */
    public String getEmail(){
        return email;
    }

    /**
     * Sets the email address of this Admin.
     * 
     * @param email
     * New email address of this Admin.
     */
    public void setEmail(String email){
        this.email = email;
    }
}
