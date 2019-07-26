package client.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
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

public class AdminController implements Initializable{
	String userjndi="epione-ear/epione-ejb/UserService!pdev.jee.interfaces.UserServiceRemote";
	
	@FXML
	private TextField identifiant;
	@FXML
	private  PasswordField mdp;
	@FXML
	private Label error;
	@FXML
	private Button login;

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	// Event Listener on Button[#login].onAction
		@FXML
		public void login(ActionEvent event) throws NamingException {
			Context ctx= new InitialContext();
			UserServiceRemote service= (UserServiceRemote) ctx.lookup(userjndi);
			
		  if(service.retrieveA(identifiant.getText(), mdp.getText())==null) {
		  error.setText("Incorrect identifiers"); }
		  else { error.setText("");
		  try {
		  Parent root=FXMLLoader.load(getClass().getResource("./AdminSpace.fxml"));
		  login.getScene().setRoot(root);
		  
		  } catch (IOException e) { // TODO Auto-generated catch block
		  e.printStackTrace(); } }
		 
		}
}
