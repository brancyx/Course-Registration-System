package stars.view;

import stars.ui.AdminUI;

import java.util.Scanner;

/**
 *  Represents a display boundary class for STARS application where admins will choose an option
 *  to edit student access period, add student, add/update course, check available slot for an index number,
 *  print student list by index number, print student list by course, print all students or print all admins.
 */
public class AdminView {
    /**
     * A User Interface class to further get command inputs from admin.
     */
    private AdminUI adminUI;

    /**
     *  Creates a new AdminView.
     */
    public AdminView() {
        adminUI = new AdminUI();
        System.out.println("Welcome Admin!");
    }

    /**
     * Displays admin main menu to allow admin to select options.
     */
    public void displayAdminMenu() {
        boolean bye = false;

        while (!bye) {
            System.out.println("What do you wish to do?");
            System.out.println("1: Edit student access period");
            System.out.println("2: Add student");
            System.out.println("3: Add/Update a course");
            System.out.println("4: Check available slot for an index number");
            System.out.println("5: Print student list by index number");
            System.out.println("6: Print student list by course");
            System.out.println("7: Print all students");
            System.out.println("8: Print all admins");
            System.out.println("9: Exit");

            Scanner scan = new Scanner(System.in);
            String choice = scan.nextLine();

            switch (choice) {
                case "1":
                    adminUI.updateAccessPeriod();
                    break;
                case "2":
                    adminUI.addStudent();
                    break;
                case "3":
                    nestedAddUpdateDisplay();
                    break;
                case "4":
                    adminUI.checkIndexVacancy();
                    break;
                case "5":
                    adminUI.printStudentByIndex();
                    break;
                case "6":
                    adminUI.printStudentByCourse();
                    break;
                case "7":
                    adminUI.printAllStudents();
                    break;
                case "8":
                    adminUI.printAllAdmins();
                    break;
                case "9":
                    bye = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    /**
     * Displays a nested menu for admin if they select to "Add/Update a course" from main menu.
     */
    public void nestedAddUpdateDisplay() {
        boolean bye = false;

        while (!bye) {
            System.out.println("What would like to do?");
            System.out.println("1: Print all courses");
            System.out.println("2: Add course");
            System.out.println("3: Update course");
            System.out.println("4: Delete course");
            System.out.println("5: Exit");

            Scanner scan = new Scanner(System.in);
            String choice = scan.nextLine();

            switch (choice) {
                case "1":
                    adminUI.displayCourses();
                    viewIndexDisplay();
                    break;
                case "2":
                    adminUI.addCourse();
                    nestedAddDisplay();
                    break;
                case "3":
                    nestedUpdateDisplay();
                    break;
                case "4":
                    adminUI.deleteCourse();
                    nestedAddUpdateDisplay();
                case "5":
                    bye = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    /**
     * Displays a nested menu if admin selects "Print all courses" from before.
     */
    public void viewIndexDisplay(){
        boolean bye = false;
        Scanner scan = new Scanner(System.in);
        while (!bye){
            System.out.println("What would you like to view?");
            System.out.println("1: Display indexes");
            System.out.println("2: Display lessons");
            System.out.println("3: Exit");
            String choice = scan.nextLine();

            switch (choice){
                case "1":
                    adminUI.getIndex();
                    break;
                case "2":
                    adminUI.getLesson();
                    break;
                case "3":
                    bye = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }

    }

    /**
     * Displays a nest menu if admin selects "Add course" from before.
     */
    public void nestedAddDisplay() {
        boolean bye = false;

        while (!bye) {
            System.out.println("Would you like to do the following?");
            System.out.println("1: Add index");
            System.out.println("2: Add Lesson");
            System.out.println("3: Exit");

            Scanner scan = new Scanner(System.in);
            String choice = scan.nextLine();

            switch (choice) {
                case "1":
                    adminUI.addIndex();
                    break;
                case "2":
                    adminUI.addLesson();
                    break;
                case "3":
                    bye = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    /**
     * Displays a nested menu if admin selects "Update course" from before.
     */
    public void nestedUpdateDisplay() {
        boolean bye = false;

        while (!bye) {
            System.out.println("Would you like to do update?");
            System.out.println("1: Update Course details");
            System.out.println("2: Update Index details");
            System.out.println("3: Update Lesson details");
            System.out.println("4: Exit");

            Scanner scan = new Scanner(System.in);
            String choice = scan.nextLine();

            switch (choice) {
                case "1":
                    nestedUpdateCourseDisplay();
                    break;
                case "2":
                    nestedUpdateIndexDisplay();
                    break;
                case "3":
                    nestedUpdateLessonDisplay();
                    break;
                case "4":
                    bye = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    /**
     * Displays a nested menu if admin selects "Update Lesson details" from before.
     */
    public void nestedUpdateCourseDisplay() {
        boolean bye = false;
        while (!bye) {
            System.out.println("Would you like to do update?");
            System.out.println("1. Course Code");
            System.out.println("2. Course Name");
            System.out.println("3. School");
            System.out.println("4. Course Type");
            System.out.println("5. Academic Unit");
            System.out.println("6: Exit");

            Scanner scan = new Scanner(System.in);
            String choice = scan.nextLine();

            switch (choice) {
                case "1":
                    adminUI.updateCourseCode();
                    break;
                case "2":
                    adminUI.updateCourseName();
                    break;
                case "3":
                    adminUI.updateCourseSchool();
                    break;
                case "4":
                    adminUI.updateCourseType();
                    break;
                case "5":
                    adminUI.updateAcademicUnit();
                    break;
                case "6":
                    bye = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    /**
     * Displays a nested menu if admin selects "Update index details" from before.
     */
    public void nestedUpdateIndexDisplay() {
        boolean bye = false;
        while (!bye) {
            System.out.println("Would you like to do update?");
            System.out.println("1. Index Code");
            System.out.println("2. Index Size");
            System.out.println("3. Index Vacancy");
            System.out.println("4. Add new index");
            System.out.println("5. Delete current index");
            System.out.println("6: Exit");

            Scanner scan = new Scanner(System.in);
            String choice = scan.nextLine();

            switch (choice) {
                case "1":
                    adminUI.updateIndexCode();
                    break;
                case "2":
                    adminUI.updateIndexSize();
                    break;
                case "3":
                    adminUI.updateVacancy();
                    break;
                case "4":
                    adminUI.addNewIndex();
                    break;
                case "5":
                    adminUI.deleteIndex(); 
                    nestedUpdateIndexDisplay();                  
                case "6":
                    bye = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    /**
     * Displays a nested menu if admin selects "Update lesson details" from before.
     */
    public void nestedUpdateLessonDisplay() {
        boolean bye = false;

        while (!bye) {
            System.out.println("Would you like to do update?");
            System.out.println("1: Day");
            System.out.println("2: Start & End time");
            System.out.println("3: Venue");
            System.out.println("4: Week");
            System.out.println("5: Group");
            System.out.println("6. Add new lesson");
            System.out.println("7: Exit");

            Scanner scan = new Scanner(System.in);
            String choice = scan.nextLine();

            switch (choice) {
                case "1":
                    adminUI.updateDay();
                    break;
                case "2":
                    adminUI.updateStartEndTime();
                    break;
                case "3":
                    adminUI.updateVenue();
                    break;
                case "4":
                    adminUI.updateWeek();
                    break;
                case "5":
                    adminUI.updateGroup();
                    break;
                case "6":
                    adminUI.addLesson();
                    break;
                case "7":
                    bye = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
}


