package client.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import pdev.jee.entities.Etape;
import pdev.jee.entities.Medecin;
import pdev.jee.entities.Parcours;
import pdev.jee.entities.Patient;
import pdev.jee.entities.Rendezvous;
import pdev.jee.interfaces.ParcoursServiceRemote;
import pdev.jee.interfaces.RdvServiceRemote;
import pdev.jee.interfaces.UserServiceRemote;
import javafx.scene.control.DatePicker;

public class PatientSpaceController implements Initializable{
	String userjndi="epione-ear/epione-ejb/UserService!pdev.jee.interfaces.UserServiceRemote";
	String rdvjndi="epione-ear/epione-ejb/RdvService!pdev.jee.interfaces.RdvServiceRemote";
	String parcoursjndi="epione-ear/epione-ejb/ParcoursService!pdev.jee.interfaces.ParcoursServiceRemote";
	
	static int medecinrdv;
	static String medref;
	@FXML
	private ImageView out;
	@FXML
	private Button modify;
	@FXML
	private Button rdv;
	@FXML
	private TextField vil;
	@FXML
	private TextField nom;
	@FXML
	private TextField spe;
	@FXML
	private ImageView search;
	@FXML
	private TextField surname;
	@FXML
	private TextField name;
	@FXML
	private TextField num;
	@FXML
	private TextField mail;
	@FXML
	private PasswordField pwd;
	@FXML
	private Label lpwd;
	@FXML
	private PasswordField pwd1;
	@FXML
	private DatePicker birth;
	@FXML
	private Button editd;
	@FXML
	private Button confd1;
	@FXML
	private Label error;
	 @FXML
	    private TableView<Medecin> table;

	    @FXML
	    private TableColumn<Medecin, String> tnom;

	    @FXML
	    private TableColumn<Medecin, ?> tspe;

	    @FXML
	    private TableColumn<Medecin, ?> tcity;

	    @FXML
	    private TableColumn<Medecin, ?> tdes;

	    @FXML
	    private TableColumn<Medecin, String> tad;
	    
	    @FXML
	    private Label label;
	    
	    @FXML
	    private TableView<Rendezvous> trdv;

	    @FXML
	    private TableColumn<Rendezvous, String> trdoc;

	    @FXML
	    private TableColumn<Rendezvous, String> trdate;

	    @FXML
	    private TableColumn<Rendezvous, String> trad;

	    @FXML
	    private TableColumn<Rendezvous, String> trpur;

	    @FXML
	    private TableColumn<Rendezvous, String> trcon;

	    @FXML
	    private TableColumn<Rendezvous, String> trsta;
	
	    @FXML
	    private Button anuler;
	    
	    @FXML
	    private ComboBox<String> filter;

	    @FXML
	    private Button ok;
	    
	    @FXML
	    private Label paref;

	    @FXML
	    private Label padesc;

	    @FXML
	    private TableView<Etape> stept;

	    @FXML
	    private TableColumn<Etape, String> stprio;

	    @FXML
	    private TableColumn<Etape, String> ststa;

	    @FXML
	    private TableColumn<Etape, String> stpur;

	    @FXML
	    private TableColumn<Etape, String> stdescr;
	    
	    @FXML
	    private TableColumn<Etape, String> stepm;

	    @FXML
	    private TableColumn<Etape, String> stepmad;
	    
	    @FXML
	    private ImageView mail1;
	private void refreshrdv() {
	   try {
				Context ct = new InitialContext();
				RdvServiceRemote rser=(RdvServiceRemote) ct.lookup(rdvjndi);
				ArrayList<Rendezvous> p= (ArrayList<Rendezvous>) rser.rdvp(LoginController.session);
	            ObservableList<Rendezvous> obs=FXCollections.observableArrayList(p);
			trdv.setItems(obs);
            
           trdoc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
					String p=param.getValue().getMotif().getDoc().getUser().getNom();
					StringProperty prop=new SimpleStringProperty(p+" "+param.getValue().getMotif().getDoc().getUser().getPrenom());
					return prop;				
				}
			});
            trsta.setCellValueFactory(new PropertyValueFactory<>("etat"));
            
            trdate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
					String date=param.getValue().getDate().toString();
					StringProperty prop=new SimpleStringProperty(date+" "+param.getValue().getDebut().toString());
					return prop;				
				}
			});
            
            trad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
					return new SimpleStringProperty( param.getValue().getMotif().getDoc().getAdresse());	
				}
			});
            
            trpur.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
					return new SimpleStringProperty( param.getValue().getMotif().getLib()+" "+param.getValue().getMotif().getPrix());	
				}
			});
            trcon.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
					return new SimpleStringProperty( param.getValue().getMotif().getDoc().getUser().getMail()+" "+
				param.getValue().getMotif().getDoc().getUser().getTel());	
				}
			});
				
	   }catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void refreshstep(int id) {
		try {
			Context ct=new InitialContext();
			ParcoursServiceRemote pserv= (ParcoursServiceRemote) ct.lookup(parcoursjndi);
			
			ArrayList<Etape> p= (ArrayList<Etape>) pserv.retrieveEt(id);
            ObservableList<Etape> obs=FXCollections.observableArrayList(p);
            stept.setItems(obs);
            stprio.setCellValueFactory(new PropertyValueFactory<>("priorite"));
            ststa.setCellValueFactory(new PropertyValueFactory<>("etat"));
            stdescr.setCellValueFactory(new PropertyValueFactory<>("descri"));
            
            stpur.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Etape,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Etape, String> param) {
					StringProperty prop=new SimpleStringProperty(param.getValue().getMot().getLib());
					return prop;				
				}
			});
            
            stepm.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Etape,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Etape, String> param) {
					StringProperty prop=new SimpleStringProperty(param.getValue().getMot().getDoc().getUser().getNom()+" "+
							param.getValue().getMot().getDoc().getUser().getPrenom());
					return prop;				
				}
			});
            stepmad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Etape,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Etape, String> param) {
					StringProperty prop=new SimpleStringProperty(param.getValue().getMot().getDoc().getUser().getMail()+" "+
							param.getValue().getMot().getDoc().getUser().getTel());
					return prop;				
				}
			});
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		filter.getItems().addAll("Done", "Canceled","Comfirmed","All");
		refreshrdv();
		try {
		Context ctx = new InitialContext();
		UserServiceRemote userv=(UserServiceRemote)ctx.lookup(userjndi);
		Patient m= userv.sessionp(LoginController.session);
		String s=label.getText();
		label.setText(s+ " "+ m.getUser().getNom()+" "+m.getUser().getPrenom());
		
		name.setText(m.getUser().getNom());
		surname.setText(m.getUser().getPrenom());
		num.setText(""+m.getUser().getTel());
		mail.setText(m.getUser().getMail());
		birth.setValue(LocalDate.parse(m.getBirth().toString(), DateTimeFormatter.ISO_LOCAL_DATE));
		pwd.setText(m.getUser().getPwd());
		
		Context ct= new InitialContext();
		ParcoursServiceRemote pserv= (ParcoursServiceRemote) ct.lookup(parcoursjndi);
		Parcours p=pserv.recupereP(LoginController.session);
		if(p!=null) {
		paref.setText(p.getReferent().getUser().getNom()+" "+ p.getReferent().getUser().getPrenom());
		padesc.setText(p.getDescription());
		medref=p.getReferent().getUser().getMail();
		refreshstep(p.getId());
		}
		
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
    @FXML
    void mailing(MouseEvent event) {
    	try {
			Parent root=FXMLLoader.load(getClass().getResource("./SendMail.fxml"));
			mail1.getScene().setRoot(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	// Event Listener on ImageView[#out].onMouseClicked
	@FXML
	public void logout(MouseEvent event) {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("./Login.fxml"));
			out.getScene().setRoot(root);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Event Listener on Button[#modify].onAction
	@FXML
	public void modify(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#rdv].onAction
	@FXML
	public void addrdv(ActionEvent event) {
		Medecin m= table.getSelectionModel().getSelectedItem();
		if(m!=null) {
			medecinrdv=m.getId();
			try {
				Parent root=FXMLLoader.load(getClass().getResource("./AddRdv.fxml"));
				out.getScene().setRoot(root);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	// Event Listener on ImageView[#search].onMouseClicked
	@FXML
	public void search(MouseEvent event) {
		String nomd, sped, villed;
		nomd=nom.getText(); sped=spe.getText();
		villed=vil.getText();
		ArrayList<Medecin> p; ObservableList<Medecin> obs;
		try {
			Context ct = new InitialContext();
			RdvServiceRemote rser=(RdvServiceRemote) ct.lookup(rdvjndi);
			if(nomd.equals("") && sped.equals("") && villed.equals("")) {
				p= (ArrayList<Medecin>) rser.cherchertout();
				obs=FXCollections.observableArrayList(p);			
			}else {
				 p= (ArrayList<Medecin>) rser.chercher(villed, nomd, sped);
	            obs=FXCollections.observableArrayList(p);
			}
			table.setItems(obs);
            
           tnom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medecin,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Medecin, String> param) {
					String p=param.getValue().getUser().getNom();
					StringProperty prop=new SimpleStringProperty(p+" "+param.getValue().getUser().getPrenom());
					return prop;				
				}
			});
            tspe.setCellValueFactory(new PropertyValueFactory<>("specialite"));
            tad.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            tcity.setCellValueFactory(new PropertyValueFactory<>("ville"));
            tdes.setCellValueFactory(new PropertyValueFactory<>("description"));
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
	}
	
	@FXML
    void filtrer(ActionEvent event) {

    }
	
	@FXML
    void anulerrdv(ActionEvent event) {
		anuler.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Rendezvous r=trdv.getSelectionModel().getSelectedItem();
				if(r!=null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation");
					alert.setHeaderText(null);
					alert.setContentText("Do you really want to cancel this Appointment?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
						try {
							Context context= new InitialContext();
							RdvServiceRemote rdvser=(RdvServiceRemote)context.lookup(rdvjndi);					
							rdvser.anulerrdv(r.getId());
							refreshrdv();
							ControleSaisie.sendMail(r.getMotif().getDoc().getUser().getMail(), "Appointment Canceled", "Hello,\n"
									+r.getPatient().getUser().getNom()+" "+r.getPatient().getUser().getPrenom()
											+ " canceled your appointment of "+r.getDate()+ " at "+r.getDebut()+".\nBest Regards");						
						} catch (NamingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
    }
	// Event Listener on Button[#editd].onAction
	@FXML
	public void editprofil(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#confd1].onAction
	@FXML
	public void confirmprofil(ActionEvent event) {
		// TODO Autogenerated
	}
	
}
