package stars.controller;

import java.io.FileReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.internet.*;

import stars.model.Index;

import javax.mail.*;
/**
 * Controller that sends email notifications to Students.
 */
public class emailController {

  /**
   * Sends email notification to Student when pushed from waitlist into an Index upon having a vacancy.
   * 
   * @param email
   * Email address of Student.
   * @param name
   * Name of Student.
   * @param index
   * Index which the Student applied for.
   */
  public static void sendNotification(String email, String name, Index index) { 

    // system email
    String username = "cz2002team7@gmail.com";
    String password = "OODP12345";

    try { 
    Properties props = new Properties();    
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.starttls.enable","true");
    props.put("mail.smtp.auth", "true");   
    //get Session   
    Session session = Session.getDefaultInstance(props,    
      new javax.mail.Authenticator() {    
      protected PasswordAuthentication getPasswordAuthentication() {    
      return new PasswordAuthentication(username,password);  
        }    
      }
    );    

    //compose message    
    MimeMessage message = new MimeMessage(session);    
    message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));    
    message.setSubject("[" + index.getCourseCode() + "] Waitlist notification");           
    message.setText("Dear "+ name + ",\n" + "\nYou have been succesfully registered to " + index.getCourseCode() + " " + index.getCourseName() + " - Index:  "
    + index.getCourseIndex() + ". \nThis message is auto-generated. Please do not reply." );  
    message.setSentDate(new Date());  
    //send message  
    Transport.send(message);     
      } catch (NoSuchProviderException e) {
    e.printStackTrace();
    } catch (MessagingException e) {
      e.printStackTrace();
    } 
  }  

  /**
   * Sends a security notification to Student 1 upon detecting 3 failed attempts from another Student 2.
   * 
   * @param email
   * Email address of Student 1.
   * @param name1
   * Name of Student 1.
   * @param name2
   * Name of Student 2.
   */
  public static void securityNotification(String email, String name1, String name2) { 

    String username = "cz2002team7@gmail.com";
    String password = "OODP12345";
    try { 

    Properties props = new Properties();    
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.starttls.enable","true");
    props.put("mail.smtp.auth", "true");   
    //get Session   
    Session session = Session.getDefaultInstance(props,    
     new javax.mail.Authenticator() {    
     protected PasswordAuthentication getPasswordAuthentication() {    
     return new PasswordAuthentication(username,password);  
     }    
    });    
    //compose message    
    
     MimeMessage message = new MimeMessage(session);    
     message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));    
     message.setSubject("Login attempt detected");   //get coursecode
     
     message.setText("Dear "+ name1 + ",\n" + "\nWarning! The STARS system has detected several failed attempts to log in to your account by an external user " + name2 +
    ". Do verify with " + name2 + " for security purposes, else contact school help desk.\n"+"\nThis message is auto-generated. Please do not reply." );  
     message.setSentDate(new Date());  
     //send message  
     Transport.send(message);     
    } catch (NoSuchProviderException e) {
      e.printStackTrace();
    } catch (MessagingException e) {
      e.printStackTrace();
    }   
  }  
}  


