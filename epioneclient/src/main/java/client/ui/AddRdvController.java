package client.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

import javafx.scene.control.ComboBox;


import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.AgendaSkinSwitcher;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import pdev.jee.entities.Medecin;
import pdev.jee.entities.Motif;
import pdev.jee.entities.Patient;
import pdev.jee.entities.Planing;
import pdev.jee.entities.Rendezvous;
import pdev.jee.interfaces.RdvServiceRemote;
import pdev.jee.interfaces.UserServiceRemote;


public class AddRdvController implements Initializable{
	String rdvjndi="epione-ear/epione-ejb/RdvService!pdev.jee.interfaces.RdvServiceRemote";
	String userjndi="epione-ear/epione-ejb/UserService!pdev.jee.interfaces.UserServiceRemote";
	Medecin med;
	Rendezvous rdv=new Rendezvous();
	@FXML
	private TextField add;
	@FXML
	private TextField  mdp;
	@FXML
	private TextField mail;
	@FXML
	private TextField date;
	@FXML
	private TextArea msg;
	@FXML
	private ComboBox<String> purp;
	@FXML
	private Button cancel;
	@FXML
	private Button confirm;
	@FXML
	private Label error;
	@FXML
	private Agenda agenda;
	@FXML
	private HBox hb;
	@FXML
    private TextField price;
	@FXML
    private Label label;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		AgendaSkinSwitcher lAgendaSkinSwitcher = new AgendaSkinSwitcher(agenda);
        hb.getChildren().add(lAgendaSkinSwitcher);
        
        agenda.setAllowDragging(false);agenda.setAllowResize(false);
		agenda.setActionCallback(new Callback<Agenda.Appointment, Void>() {
			@Override
			public Void call(Appointment param) {
				date.setText(param.getStartLocalDateTime().toString());
				//Notifications.create().text("RDV CREE OH").title("HIHI").showInformation();
				rdv.setDate(java.sql.Date.valueOf(param.getStartLocalDateTime().toLocalDate()));
				rdv.setDebut(Time.valueOf(param.getStartLocalDateTime().toLocalTime()));
				return null;
			}
		});
		
		try {
			Context ct= new InitialContext();
			RdvServiceRemote serv=(RdvServiceRemote) ct.lookup(rdvjndi);
			List<Motif> mot=serv.retrieveMotif(PatientSpaceController.medecinrdv);
			for(Motif m : mot) {
				purp.getItems().add(m.getLib()+"---"+ m.getPrix()+"Dt");
			}
			List<Appointment> liste= new ArrayList<>();
			List<Planing> plan=serv.retrieveDispo(PatientSpaceController.medecinrdv);		
			for(Planing p: plan) {
				String madate =p.getJour().toString();
				String heure =p.getDebut().toString(); String heure2 =p.getFin().toString();
				LocalDate localDate = LocalDate.parse(madate, DateTimeFormatter.ISO_LOCAL_DATE);
				LocalTime localtime = LocalTime.parse(heure, DateTimeFormatter.ISO_LOCAL_TIME);
				LocalTime localtime2 = LocalTime.parse(heure2, DateTimeFormatter.ISO_LOCAL_TIME);			
				liste.add(new Agenda.AppointmentImplLocal().withStartLocalDateTime(LocalDateTime.of(localDate, localtime))
				  .withEndLocalDateTime(LocalDateTime.of(localDate, localtime2))
				  .withDescription("It's time") .withAppointmentGroup(new
				  Agenda.AppointmentGroupImpl().withStyleClass("group6")));
			}
			agenda.appointments().addAll(liste);
			Context ctx= new InitialContext();
			UserServiceRemote user=(UserServiceRemote)ctx.lookup(userjndi);
			med=user.sessionm(PatientSpaceController.medecinrdv);
			Integer i=med.getUser().getTel();
			add.setText(med.getAdresse()); mdp.setText(i.toString()); mail.setText(med.getUser().getMail());
			String s=label.getText();
			label.setText(s+ " "+ med.getUser().getNom()+" "+med.getUser().getPrenom());
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	// Event Listener on Button[#cancel].onAction
	@FXML
	public void cancel(ActionEvent event) {
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
	// Event Listener on Button[#confirm].onAction
	@FXML
	public void confirm(ActionEvent event) {
		if(purp.getValue()==("") || purp.getValue()==null) {
			error.setText("Choosing a purpose is mandatory");
		}else if(date.getText().equals("")){
			error.setText("Choose a date");
		}
		else{
			error.setText("");
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("You will add confirm this appointment");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				String mots[]=(purp.getValue().split("---"));				
				try {
					Context ctx = new InitialContext();
					Context ct= new InitialContext();
					RdvServiceRemote serv=(RdvServiceRemote) ct.lookup(rdvjndi);
					UserServiceRemote userv=(UserServiceRemote)ctx.lookup(userjndi);
					Patient m= userv.sessionp(LoginController.session);
					Motif mot=serv.purpose(mots[0],PatientSpaceController.medecinrdv);
					if(mot!=null) {
						rdv.setEtat("Confirmed");
						rdv.setMessage(msg.getText());
						rdv.setPatient(m);
						rdv.setMotif(mot);
						serv.ajoutRdv(rdv);
						//Notifications.create().text("Appointment create").showInformation();
						Notifications.create().text("Appointment create with Doctor "+ mot.getDoc().getUser().getNom()+" et "+rdv.getDate()).showInformation();
						try {
							Parent root=FXMLLoader.load(getClass().getResource("./PatientSpace.fxml"));
							cancel.getScene().setRoot(root);
							
						} catch (IOException e) {
							 //TODO Auto-generated catch block
							 e.printStackTrace();
						}	
					}else {
						Notifications.create().text("Something wrong,Appointment not created").showWarning();
					}					
				} catch (NamingException e) {
						e.printStackTrace();
				}
			}
		}
	}
}
