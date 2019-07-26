package pdev.jee.dep;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ControleSaisie {

	public static boolean name(String nom){
        Pattern pattern=Pattern.compile("[a-zA-Z]+");
        Matcher matcher=pattern.matcher(nom);
        if (matcher.find() && matcher.group().equals(nom)){
            return true;
        }else {
            return false;
        }
    }
	
	public static boolean validateNumTel(String mobile){
        Pattern pattern=Pattern.compile("[0-9]+");
        Matcher matcher=pattern.matcher(mobile);
        if (matcher.find() && matcher.group().equals(mobile)){
            return true;
        }else {
            return false;
        }
    }
	
	public static boolean validateEmail(String mail){
        Pattern pattern=Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9.]*@[a-zA-Z0-9.]+([.][a-zA-Z]+)+");
        Matcher matcher=pattern.matcher(mail);
        if (matcher.find() && matcher.group().equals(mail)){
            return true;
        }else {
            return false;
        }
    }
	
	public static void sendMail(String dest, String subject,String content) {
		//Mail
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		//le protocol smtp du serveur gmail
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
	    //debuger 
    	props.put("mail.debug", "true");
    	//autoriser lutilisation des ports s�curis�s
    	props.put("mail.smtp.starttls.enable", "true");		    	
    	props.put("mail.smtp.auth", "true");
    	
 
	    Session session = Session.getInstance(props);
	    try {
	    	InternetAddress from= new InternetAddress("applications.sabine@gmail.com", "Epione");
	        MimeMessage msg = new MimeMessage(session);
	        msg.setFrom(from);
	        msg.setRecipients(Message.RecipientType.TO,
	                          dest);
	        msg.setSubject(subject);
	        msg.setSentDate(new Date());
	        msg.setDescription("Epione Appointment Platform");
	        msg.setHeader("X-Mailer", "Java");
	        
	        msg.setText("Hello,\n"+ content+ "\n");
	        Transport.send(msg, "applications.sabine@gmail.com", "alloallosabine");
	        
	    } catch (MessagingException mex) {
	        System.out.println("send failed, exception: " + mex);
	    } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
