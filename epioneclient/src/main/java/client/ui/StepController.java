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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

import javafx.scene.control.ComboBox;

import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import pdev.jee.entities.Etape;
import pdev.jee.entities.Medecin;
import pdev.jee.entities.Motif;
import pdev.jee.entities.Patient;
import pdev.jee.interfaces.ParcoursServiceRemote;
import pdev.jee.interfaces.RdvServiceRemote;
import pdev.jee.interfaces.UserServiceRemote;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class StepController implements Initializable{
	String rdvjndi="epione-ear/epione-ejb/RdvService!pdev.jee.interfaces.RdvServiceRemote";
	String userjndi="epione-ear/epione-ejb/UserService!pdev.jee.interfaces.UserServiceRemote";
	String parcoursjndi="epione-ear/epione-ejb/ParcoursService!pdev.jee.interfaces.ParcoursServiceRemote";
	
	Medecin med;
	@FXML
	private TextField priority;
	@FXML
	private TextField paname;
	@FXML
	private TextArea msg;
	@FXML
	private ComboBox<String> purp;
	@FXML
	private TextField doname;
	@FXML
	private Button bac;
	@FXML
	private Button ajout;
	@FXML
	private Label error;
	@FXML
	private Label label;
	@FXML
	private TextField nom;
	@FXML
	private TextField nom1;
	@FXML
	private ImageView search;
	@FXML
	private TableView<Patient> ptable;
	@FXML
	private TableColumn<Patient, String> pln;
	@FXML
	private TableColumn<Patient, String> pfn;
	@FXML
	private TableColumn<Patient, String> pmail;
	@FXML
	private TableColumn<Patient, Integer> pnumber;
	@FXML
	private TableColumn<Patient, ?> pbirth;
	@FXML
	private TableView<Medecin> ctable;
	@FXML
	private TableColumn<Medecin, String> cname;
	@FXML
	private TableColumn<Medecin, String> cmail;
	@FXML
	private TableColumn<Medecin, Integer> cnum;
	@FXML
	private TableColumn<Medecin, String> cspe;
	@FXML
	private TableColumn<Medecin, String> cdesc;
	@FXML
	private Label error1;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Context ctx=new InitialContext();
			UserServiceRemote service= (UserServiceRemote)ctx.lookup(userjndi);
			
			Context con=new InitialContext();
			ParcoursServiceRemote ps=(ParcoursServiceRemote) con.lookup(parcoursjndi);
			
			 // Doctor Table
         	ArrayList<Medecin> m= (ArrayList<Medecin>) service.findAllM();
            ObservableList<Medecin> obse=FXCollections.observableArrayList(m);
            ctable.setItems(obse);
            cname.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medecin,String>, ObservableValue<String>>() {
         				@Override
         				public ObservableValue<String> call(CellDataFeatures<Medecin, String> param) {
         					StringProperty prop=new SimpleStringProperty(param.getValue().getUser().getNom()+" "+ param.getValue().getUser().getPrenom());
         					return prop;				
         				}
         			});
             cmail.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medecin,String>, ObservableValue<String>>() {

         				@Override
         				public ObservableValue<String> call(CellDataFeatures<Medecin, String> param) {
         					return new SimpleStringProperty(param.getValue().getUser().getMail());
         				}
         			});
                     
              cnum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medecin,Integer>, ObservableValue<Integer>>() {            	
         				@Override
         				public ObservableValue<Integer> call(CellDataFeatures<Medecin, Integer> param) {
         					IntegerProperty prop=new SimpleIntegerProperty(param.getValue().getUser().getTel());
         					return prop.asObject();
         				}
         			});         			
            cdesc.setCellValueFactory(new PropertyValueFactory<>("ville"));
            cspe.setCellValueFactory(new PropertyValueFactory<>("specialite"));
            
            //Nom P
            Patient patient=ps.parcoursSt(ParcoursController.parc).getPat();
            paname.setText(patient.getUser().getNom()+" "+ patient.getUser().getPrenom());
            
    	}catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		///////////////////////////
		ctable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
			doname.setText(newValue.getUser().getNom()+" "+ newValue.getUser().getPrenom());
			try {
				med=newValue;
				Context ct= new InitialContext();
				RdvServiceRemote serv=(RdvServiceRemote) ct.lookup(rdvjndi);
				List<Motif> moti=serv.retrieveMotif(oldValue.getId());
				for(int i=0;i<moti.size();i++) {
					purp.getItems().remove(moti.get(i).getLib());
				}
				List<Motif> mot=serv.retrieveMotif(newValue.getId());
				for(int i=0;i<mot.size();i++) {
					purp.getItems().add(mot.get(i).getLib());
				}
			}catch (NamingException e) {
				e.printStackTrace();
			}
		});
		
	}

	
	// Event Listener on Button[#bac].onAction
	@FXML
	public void back(ActionEvent event) {
		bac.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Parent root=FXMLLoader.load(getClass().getResource("./DoctorSpace.fxml"));
					bac.getScene().setRoot(root);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	// Event Listener on Button[#ajout].onAction
	@FXML
	public void add(ActionEvent event) {
		ajout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(priority.getText().equals("")) {
					error.setText("Enter the priority level");
				}else if(purp.getValue()==null) {
					error.setText("Choose the purpose");
				}else {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation");
					alert.setHeaderText(null);
					alert.setContentText("This step will be add do you confirm?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
						try {
							Context con=new InitialContext();
							ParcoursServiceRemote ps=(ParcoursServiceRemote) con.lookup(parcoursjndi);
							
							Context context= new InitialContext();
							RdvServiceRemote rdvser=(RdvServiceRemote)context.lookup(rdvjndi);
							
							Etape etap= new Etape();
							etap.setDescri(msg.getText());
							etap.setEtat("To do");
							etap.setParcours(ps.parcoursSt(ParcoursController.parc));
							etap.setPriorite(priority.getText());
							etap.setMot(rdvser.purpose(purp.getValue(), med.getId()));
							
							ps.ajoutE(etap);
							msg.setText("");
							Notifications.create().text("Step correctly added").showInformation();
							
						} catch (NamingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
		});
	}
	// Event Listener on ImageView[#search].onMouseClicked
	@FXML
	public void search(MouseEvent event) {
		try {
			Context ctx=new InitialContext();
			UserServiceRemote service= (UserServiceRemote)ctx.lookup(userjndi);			
			 // Doctor Table
         	ArrayList<Medecin> m= (ArrayList<Medecin>) service.findM(nom.getText(), nom1.getText());
            ObservableList<Medecin> obse=FXCollections.observableArrayList(m);
            ctable.setItems(obse);
            cname.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medecin,String>, ObservableValue<String>>() {
         				@Override
         				public ObservableValue<String> call(CellDataFeatures<Medecin, String> param) {
         					StringProperty prop=new SimpleStringProperty(param.getValue().getUser().getNom()+" "+ param.getValue().getUser().getPrenom());
         					return prop;				
         				}
         			});
             cmail.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medecin,String>, ObservableValue<String>>() {

         				@Override
         				public ObservableValue<String> call(CellDataFeatures<Medecin, String> param) {
         					return new SimpleStringProperty(param.getValue().getUser().getMail());
         				}
         			});
                     
              cnum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medecin,Integer>, ObservableValue<Integer>>() {            	
         				@Override
         				public ObservableValue<Integer> call(CellDataFeatures<Medecin, Integer> param) {
         					IntegerProperty prop=new SimpleIntegerProperty(param.getValue().getUser().getTel());
         					return prop.asObject();
         				}
         			});         			
            cdesc.setCellValueFactory(new PropertyValueFactory<>("ville"));
            cspe.setCellValueFactory(new PropertyValueFactory<>("specialite"));
            
            
    	}catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
