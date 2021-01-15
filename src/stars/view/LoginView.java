package stars.view;

import stars.controller.LoginController;

import java.util.Arrays;
import java.util.Scanner;
import java.io.Console;

/**
 * Represents a main boundary class for STARS application where users will enter their
 * credentials to log in to the system.
 * 
 * @author
 *
 */
public class LoginView {

  /**
   * A method to display welcome message.
   */
  public void displayWelcome() {
    System.out.println("#################################################################");
    System.out.println("*\t\t _____ _____ ___  ______  _____ \t\t*");
    System.out.println("*\t\t/  ___|_   _/ _ \\ | ___ \\/  ___|\t\t*");
    System.out.println("*\t\t\\ `--.  | |/ /_\\ \\| |_/ /\\ `--. \t\t*");
    System.out.println("*\t\t `--. \\ | ||  _  ||    /  `--. \\\t\t*");
    System.out.println("*\t\t/\\__/ / | || | | || |\\ \\ /\\__/ /\t\t*");
    System.out.println("*\t\t\\____/  \\_/\\_| |_/\\_| \\_|\\____/ \t\t*");
    System.out.println("*\tWelcome to NTU Student Automated Registration System\t*");
    System.out.println("#################################################################");
  }

  /**
   * A method to display login menu to allow users to select options and input information.
   */
  public void displayLogin() {
    String enteredType = "System Admin";
    boolean boo = false;
    while (!boo) {
      Scanner sc = new Scanner(System.in);
      Console cnsl = System.console();
      System.out.println("\n------------------");
      System.out.println("LOGIN PAGE");
      System.out.println("------------------\n");
      System.out.println("Account Type");
      System.out.println("1: Student");
      System.out.println("2: Admin");
      System.out.println("3: Change password");
      System.out.println("4: Quit");
      System.out.println("Enter Option: ");
      String select = sc.nextLine();

      switch (select) {
        case "1":
          enteredType = "Student";
          break;
        case "2":
          enteredType = "Admin";
          break;
        case "3":
          displayChangePassword();
          continue;
        case "4":
          System.out.println("Thank you for using! Goodbye!");
          boo = true;
          System.exit(0);
          break;
        default:
          System.out.println("Error! Invalid choice! Please try again!");
          continue;
      }

      System.out.println("Enter " + enteredType + " Username: ");
      String enteredUsername = sc.next().toUpperCase().trim();
      char[] enteredPassword = cnsl.readPassword("Enter password: ");

      LoginController loginMgr = new LoginController(enteredUsername, String.valueOf(enteredPassword), enteredType);
      loginMgr.Login();
    }
  }

  /**
   * A method called by displayLogin() method to allow users to input information before they
   * can change their passwords.
   */
  private void displayChangePassword(){
    Scanner sc = new Scanner(System.in);
    Console cnsl = System.console();
    String enteredType = null;

    boolean error = true;
    while (error){
      System.out.println("Enter your account type (Student/Admin): ");
      enteredType = sc.nextLine().toUpperCase().trim();
      if (!(enteredType.equalsIgnoreCase("Student") || enteredType.equalsIgnoreCase("Admin"))){
        System.out.println("Error! Please select correct account type!");
      } else {
        error = false;
      }
    }

    System.out.println("Enter your username: ");
    String enteredUsername = sc.next().toUpperCase().trim();
    char[] enteredPassword = cnsl.readPassword("Enter old password: ");

    LoginController lc = new LoginController();
    boolean resetCompleted = false;
    char[] newPassword;
    char[] cfmPassword;
    if (lc.authenticateUser(enteredUsername, lc.generateHashedPassword(String.valueOf(enteredPassword)), enteredType)) {
      while (resetCompleted == false) {
        newPassword = cnsl.readPassword("Enter New Password: ");
        cfmPassword = cnsl.readPassword("Confirm New Password: ");
        if (!Arrays.equals(newPassword, cfmPassword)) {
          System.out.println("Passwords do not match! Please re-enter password.");
          continue;
        }
        resetCompleted = lc.changePassword(enteredUsername, String.valueOf(newPassword));
        if (resetCompleted) {
          System.out.println("Password Successfully Changed.");
        } else {
          System.out.println("Unsuccessful! User not found! Returning to main menu....");
          break;
        }
      }
    } else {
      System.out.println("Wrong username and password. You are not authorised to change your password");
    }
  }
}

