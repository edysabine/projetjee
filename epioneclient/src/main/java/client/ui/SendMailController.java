package client.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import pdev.jee.entities.Patient;
import pdev.jee.interfaces.UserServiceRemote;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SendMailController implements Initializable{
	@FXML
	private TextField identifiant;
	@FXML
	private Button cancel;
	@FXML
	private Button send;
	@FXML
    private TextField content;

    @FXML
    private TextField mail;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		String userjndi="epione-ear/epione-ejb/UserService!pdev.jee.interfaces.UserServiceRemote";
		try {
			Context ctx = new InitialContext();
			UserServiceRemote userv=(UserServiceRemote)ctx.lookup(userjndi);
			Patient p= userv.sessionp(LoginController.session);
			mail.setText(p.getUser().getMail());
			 			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
    
	// Event Listener on Button[#cancel].onAction
	@FXML
	public void cancel(ActionEvent event){
		cancel.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				try {
					Parent root=FXMLLoader.load(getClass().getResource("./PatientSpace.fxml"));
					cancel.getScene().setRoot(root);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	// Event Listener on Button[#send].onAction
	@FXML
	public void send(ActionEvent event) {
		send.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Do you confirm sending this mail to your referent?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					ControleSaisie.sendMail(PatientSpaceController.medref, identifiant.getText(), "Mail from "+mail.getText()+".\n"+content.getText());
				}
				
			}
		});
	}
}
