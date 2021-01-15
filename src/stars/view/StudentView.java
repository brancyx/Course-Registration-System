package stars.view;

import java.util.Scanner;
import stars.ui.StudentUI;
import stars.users.Student;
import stars.roster.*;

/**
 * Represents a display boundary class for STARS application where students will choose an option
 * to add course, drop course, print courses registered, check vacancies, change index number
 * and to swap index number with another student. 
 */
public class StudentView {
  /**
   * A User Interface class to further interact and get command inputs from student.
   */
  private StudentUI studentUI;

  /**
   * Creates a new Student View by passing NTU object as reference
   * @param ntu NTU object
   */
  public StudentView(NTU ntu) {
      studentUI = new StudentUI(ntu);
      System.out.println("Welcome Student!");
  }

  /**
   * Student's choice of functions 1-7.
   */
  private String choice;
  Scanner sc = new Scanner(System.in);

  /**
   * A method to display student menu to allow student to select options.
   * @param student Student object.
   */
  public void displayStudMenu(Student student) {
    boolean bye = false;
    while (!bye) {
      System.out.println("1: Add Course");
      System.out.println("2: Drop Course");
      System.out.println("3: Check/Print Courses Registered");
      System.out.println("4: Check Vacancies Available");
      System.out.println("5: Change Index Number of Course");
      System.out.println("6: Swap Index with Another Student");
      System.out.println("7: Logout");
      System.out.println("Input Option: ");
      choice = sc.nextLine();

      switch (choice) {
        case "1":
          studentUI.addCourse(student);
          break;
        case "2":
          studentUI.dropCourse(student);
          break;
        case "3":
          studentUI.printCoursesRegistered(student);
          break;
        case "4":
          studentUI.checkVacancies();
          break;
        case "5":
          studentUI.changeIndex(student);
          break;
        case "6":
          studentUI.swopIndex(student);
          break;
        case "7":
          bye = true;
          break;
        default:
          System.out.println("Invalid Option, Try Again!");
          break;
      }

    }
  }
}