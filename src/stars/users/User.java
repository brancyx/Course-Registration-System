package stars.users;

import java.io.Serializable;

/**
 * Represents a User of the STARS system. A User can be an Admin or a Student.
 */
public abstract class User implements Serializable {

	/**
	 * Unique SerializationID for this User.
	 */
	private static final long serialVersionUID = 6529685098267757222L;

	/**
	 * Name of this User.
	 */
	private String name;

	/**
	 * Username of this User.
	 */
	private String username;

	/**
	 * Password of this User.
	 */
	private String password;

	/**
	 * Acoount of this User.
	 */
	private char account;

	/**
	 * Gender of this User.
	 */
    private String gender;
	
	/**
	 * Creates a User.
	 */
	public User(){}

	/**
	 * Create a User with details.
	 * 
	 * @param name
	 * Name of this User.
	 * @param username
	 * Username of this User.
	 * @param password
	 * Password of this User.
	 * @param account
	 * Account of this User.
	 * @param gender
	 * Gender of this User.
	 */
    public User(String name, String username, String password, char account, String gender){
        this.name=name;
        this.username=username;
        this.password=password;
        this.account=account;
        this.gender=gender;
    }

	/**
	 * Sets the name of this User.
	 * 
	 * @param n
	 * New name of this User.
	 */
    public void setName(String n) {
		this.name = n;
	}

	/**
	 * Gets the name of this User.
	 * 
	 * @return Name of this User.
	 */
	public String getName() {
		return name;
    }

	/**
	 * Sets the Username of this User.
	 * 
	 * @param u
	 * New username of this User.
	 */
	public void setUsername(String u) {
		this.username = u;
    }

	/**
	 * Gets the username of this User.
	 * 
	 * @return Username of this User.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the password of this User.
	 * 
	 * @param pass
	 * New password of this User.
	 */
    public void setPassword(String pass) {
		this.password = pass;
    }

	/**
	 * Gets the password of this User.
	 * 
	 * @return Password of this User.
	 */
	public String getPassword() {
		return password;
    }

	/**
	 * Sets the account of this User.
	 * @param acc Account type of User
	 */
	public void setAccount(char acc) {
		this.account = acc;
    }

	/**
	 * Gets the account of this User.
	 * 
	 * @return Account of this User.
	 */
    public char getAccount() {
		return account;
	}
 
	/**
	 * Sets the gender of this User.
	 * 
	 * @param g
	 * New gender of this User.
	 */
	public void setGender(String g) {
		this.gender = g;
	}
	
	/**
	 * Gets the gender of this User.
	 * 
	 * @return Gender of this User.
	 */
	public String getGender() {
		return gender;
    } 
	
	/**
	 * Gets boolean expression for validation of this User's password. 
	 * 
	 * @param savePass
	 * Password of this User.
	 * 
	 * @return True if equal, else false.
	 */
	public boolean validate(String savePass) {
		if (this.password.equals(savePass))
			return true;
		else
			return false;
	}
}
