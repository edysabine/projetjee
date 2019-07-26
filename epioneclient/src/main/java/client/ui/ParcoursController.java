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
import java.util.Optional;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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

import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import pdev.jee.entities.Parcours;
import pdev.jee.entities.Patient;
import pdev.jee.interfaces.ParcoursServiceRemote;
import pdev.jee.interfaces.UserServiceRemote;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class ParcoursController implements Initializable{
	String userjndi="epione-ear/epione-ejb/UserService!pdev.jee.interfaces.UserServiceRemote";
	String parcoursjndi="epione-ear/epione-ejb/ParcoursService!pdev.jee.interfaces.ParcoursServiceRemote";
	
	Patient lp;
	static int parc;
	@FXML
	private TextField paname;
	@FXML
	private TextArea msg;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ptable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
			paname.setText(newValue.getUser().getNom()+" "+ newValue.getUser().getPrenom());
			lp=newValue;
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
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation");
					alert.setHeaderText(null);
					alert.setContentText("Do you want to create way for this patient?\nYou will be redirected to add steps on this way.");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
						try {
							Parcours par=new Parcours();
							Context context= new InitialContext();
							UserServiceRemote user= (UserServiceRemote) context.lookup(userjndi);
							Context cont= new InitialContext();
							ParcoursServiceRemote ps=(ParcoursServiceRemote) cont.lookup(parcoursjndi);
							par.setDescription(msg.getText());
							par.setPat(lp);
							par.setReferent(user.sessionm(LoginController.session));
							parc=ps.ajoutPa(par);
							try {
								Parent root=FXMLLoader.load(getClass().getResource("./Step.fxml"));
								bac.getScene().setRoot(root);
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						} catch (NamingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
    	// Patient Table
    				ArrayList<Patient> p= (ArrayList<Patient>) service.findP(nom.getText());
    	            ObservableList<Patient> obs=FXCollections.observableArrayList(p);
    	            ptable.setItems(obs);
    	            pln.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient,String>, ObservableValue<String>>() {
    					@Override
    					public ObservableValue<String> call(CellDataFeatures<Patient, String> param) {
    						StringProperty prop=new SimpleStringProperty(param.getValue().getUser().getNom());
    						return prop;				
    					}
    				});
    	            pfn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient,String>, ObservableValue<String>>() {

    					@Override
    					public ObservableValue<String> call(CellDataFeatures<Patient, String> param) {
    						return new SimpleStringProperty(param.getValue().getUser().getPrenom());
    					}
    				});
    	            pmail.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient,String>, ObservableValue<String>>() {

    					@Override
    					public ObservableValue<String> call(CellDataFeatures<Patient, String> param) {
    						return new SimpleStringProperty(param.getValue().getUser().getMail());
    					}
    				});
    	            
    	            pnumber.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient,Integer>, ObservableValue<Integer>>() {            	
    					@Override
    					public ObservableValue<Integer> call(CellDataFeatures<Patient, Integer> param) {
    						IntegerProperty prop=new SimpleIntegerProperty(param.getValue().getUser().getTel());
    						return prop.asObject();
    					}
    				});
    				
    	            pbirth.setCellValueFactory(new PropertyValueFactory<>("birth"));
    	} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
