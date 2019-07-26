package client.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pdev.jee.entities.Medecin;
import pdev.jee.entities.Patient;
import pdev.jee.interfaces.UserServiceRemote;

public class LoginController implements Initializable {
	
	String userjndi="epione-ear/epione-ejb/UserService!pdev.jee.interfaces.UserServiceRemote";
	static int session;
	@FXML
    private TextField identifiant;
    @FXML
    private PasswordField mdp;
    @FXML
    private Button register;
    @FXML
    private Button login;
    @FXML
    private Label error;
    @FXML
    private ComboBox<String> role;
    
	public void initialize(URL location, ResourceBundle resources) {
		role.getItems().addAll("Patient","Doctor");
		
	}
	
	@FXML
    private void register(ActionEvent event) {
		if(role.getValue()=="Patient") {	
		try {
			Parent root=FXMLLoader.load(getClass().getResource("./RegistrationP.fxml"));
			login.getScene().setRoot(root);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else if(role.getValue()=="Doctor") {
			try {
				Parent root=FXMLLoader.load(getClass().getResource("./RegistrationM.fxml"));
				login.getScene().setRoot(root);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			error.setText("Choose between Doctor and Patient");
		}
		
		
	}
	
	@FXML
    private void login(ActionEvent event) throws NamingException {
		Context ctx= new InitialContext();
		UserServiceRemote service= (UserServiceRemote) ctx.lookup(userjndi);
		if(role.getValue()=="Patient") {
			Patient p=service.retrieveP(identifiant.getText(), mdp.getText());
			if(p==null) {
				error.setText("Incorrect identifiers");
			}else {
				error.setText("");
				if(p.getUser().isEtat()) {
					session=p.getId();
					try {
						Parent root=FXMLLoader.load(getClass().getResource("./PatientSpace.fxml"));
						login.getScene().setRoot(root);	
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					Notifications.create().title("Your account is unvalidated, you will receive a mail when it will be validate").showInformation();
				}
			}
		}else if(role.getValue()=="Doctor") {
			Medecin d=service.retrieveM(identifiant.getText(), mdp.getText());
			if(d==null) {
				error.setText("Incorrect identifiers");
			}else {
				error.setText("");
				if(d.getUser().isEtat()) {
					session=d.getId();
					try {
						Parent root=FXMLLoader.load(getClass().getResource("./DoctorSpace.fxml"));
						login.getScene().setRoot(root);		
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					Notifications.create().title("Your account is unvalidated, \nyou will receive a mail when it will be validate").showInformation();
				}					
			}
		}
		else {
			error.setText("Choose between Doctor and Patient");
		}
	}
}
