package stars;

import stars.view.LoginView;

/**
 * Represents a client interface where the application begins.
 */
public class Client {
  /**
   * Main program method.
   * 
   * @param args
   */
  public static void main(String[] args) {
    LoginView loginNow = new LoginView();
    loginNow.displayWelcome();
    loginNow.displayLogin();
  }
}