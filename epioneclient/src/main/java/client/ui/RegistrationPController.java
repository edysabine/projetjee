package client.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import pdev.jee.entities.Patient;
import pdev.jee.entities.User;
import pdev.jee.interfaces.UserServiceRemote;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

import javafx.scene.control.DatePicker;

public class RegistrationPController implements Initializable{
	
	String userjndi="epione-ear/epione-ejb/UserService!pdev.jee.interfaces.UserServiceRemote";
	
	@FXML
	private TextField surname;
	@FXML
	private TextField name;
	@FXML
	private TextField num;
	@FXML
	private PasswordField mdp;
	@FXML
	private PasswordField mdp2;
	@FXML
	private DatePicker date;
	@FXML
	private TextField mail;
	@FXML
	private Label error;
	@FXML
	private Button register;
	@FXML
	private Button login;
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		mdp2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (mdp2.getText().equals(mdp.getText())){
                error.setText("");
            }else {
                error.setText("Uncorresponding passwords");
            }
        });
		mail.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!ControleSaisie.validateEmail(mail.getText())) {
				error.setText("Invalid mail address");
            }else {
                error.setText("");
            }
        });
	}

	// Event Listener on Button[#register].onAction
	@FXML
	public void register(ActionEvent event) throws NamingException {
		// TODO Autogenerated
		if(name.getText().equals("") || num.getText().equals("") || mail.getText().equals("") || mdp.getText().equals("") || mdp2.getText().equals("")) {
			error.setText("Fields with * are mendatory");	
		}else if(!ControleSaisie.name(name.getText())) {
			error.setText("Invalid Name");
		}
		else if(!ControleSaisie.name(surname.getText())) {
			error.setText("Invalid First Name");
		}
		else if(!ControleSaisie.validateNumTel(num.getText())) {
			error.setText("Invalid phone number");
		}else if(num.getText().length()!=8) {
			error.setText("Invalid length of phone number");
		}
		else if(!ControleSaisie.validateEmail(mail.getText())) {
			error.setText("Invalid mail address");
		} else if (!mdp2.getText().equals(mdp.getText())){
			error.setText("Uncorresponding passwords");
		}else {
			Patient p=new Patient();
			User u=new User();
			u.setEtat(false); u.setImage(""); u.setMail(mail.getText()); u.setNom(name.getText());
			u.setPrenom(surname.getText()); u.setPwd(mdp.getText()); u.setTel(Integer.parseInt(num.getText()));
			p.setBirth(java.sql.Date.valueOf(date.getValue()));
			p.setUser(u);
			
			Context ctx=new InitialContext();
			UserServiceRemote service=(UserServiceRemote) ctx.lookup(userjndi);
			service.ajoutP(p);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("Account created");
			alert.setContentText("An mail will be send to the admin for the activation of your count\nYou'll receive a notification when it'll be validate");
			alert.showAndWait();
			ControleSaisie.sendMail("applications.sabine@gmail.com", "New Count", "User "+p.getUser().getNom()+" is waiting for the validation of is count \nBest Regards");
			
			try {
				Parent root=FXMLLoader.load(getClass().getResource("./Login.fxml"));
				login.getScene().setRoot(root);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	// Event Listener on Button[#login].onAction
	@FXML
	public void login(ActionEvent event) {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("./Login.fxml"));
			login.getScene().setRoot(root);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
