package client.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

import javafx.scene.control.TableColumn;

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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;

import javafx.scene.control.PasswordField;
import javafx.scene.control.SortEvent;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.AgendaSkinSwitcher;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.LocalDateTimeRange;
import pdev.jee.entities.Etape;
import pdev.jee.entities.Medecin;
import pdev.jee.entities.Motif;
import pdev.jee.entities.Parcours;
import pdev.jee.entities.Patient;
import pdev.jee.entities.Planing;
import pdev.jee.entities.Rendezvous;
import pdev.jee.entities.User;
import pdev.jee.interfaces.ParcoursServiceRemote;
import pdev.jee.interfaces.RdvServiceRemote;
import pdev.jee.interfaces.UserServiceRemote;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class DoctorSpaceController implements Initializable{
	
	String rdvjndi="epione-ear/epione-ejb/RdvService!pdev.jee.interfaces.RdvServiceRemote";
	String userjndi="epione-ear/epione-ejb/UserService!pdev.jee.interfaces.UserServiceRemote";
	String parcoursjndi="epione-ear/epione-ejb/ParcoursService!pdev.jee.interfaces.ParcoursServiceRemote";
	Motif motif;
	
	@FXML
	private ImageView out;
	@FXML
	private TableColumn<Rendezvous,String> patient;
	@FXML
	private TableColumn<Rendezvous,String> date;
	@FXML
	private TableColumn<Rendezvous,String> but;
	@FXML
	private TableColumn<Rendezvous,String> cont;
	@FXML
	private TableColumn<Rendezvous,String> birth;
	@FXML
	private Agenda agenda;
	@FXML
	private Button cancel;
	@FXML
	private Button confirm;
	@FXML
	private HBox hb;
	@FXML
	private TextField surname;
	@FXML
	private TextField name;
	@FXML
	private TextField num;
	@FXML
	private TextField mail;
	@FXML
	private TextField specialty;
	@FXML
	private TextField city;
	@FXML
	private TextField address;
	@FXML
	private PasswordField pwd;
	@FXML
	private PasswordField pwd1;
	@FXML
	private Button editd;
	@FXML
	private Button confd;
	@FXML
	private Label error;
	@FXML
	private TableView<Motif> purpose;
	@FXML
	private TableColumn<Motif,String> ppurp;
	@FXML
	private TableColumn<Motif,Integer> pprix;
	@FXML
	private Button modp;
	@FXML
	private Button ajoutp;
	@FXML
	private Button delp;
	@FXML
	private GridPane gridp;
	@FXML
	private TextField butp;
	@FXML
	private TextField price;
	@FXML
	private Button confp;
	@FXML
    private TableView<Rendezvous> rdv;
	@FXML
    private Label label;
	 @FXML
	    private Label lpwd;
	 @FXML
	    private TextArea description;
	 @FXML
	 private TableColumn<Rendezvous, ?> pretat;
	 
	 @FXML
	    private ComboBox<String> filter;

	    @FXML
	    private Button ok;
	    @FXML
	    private Button markd;
	    
	    @FXML
	    private ImageView mail1;
	    
	    @FXML
	    private TableView<Parcours> patht;

	    @FXML
	    private TableColumn<Parcours, String> ppat;

	    @FXML
	    private TableColumn<Parcours, String> pabirth;

	    @FXML
	    private TableColumn<Parcours, String> paco;

	    @FXML
	    private TableColumn<Parcours, String> paref;

	    @FXML
	    private TableColumn<Parcours, String> parefco;

	    @FXML
	    private TableColumn<Parcours, String> pades;

	    @FXML
	    private Button stcr;

	    @FXML
	    private Button stshow;

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
	    private Button pmod;
	    
	 
	
	private void refreshmotif() {
		try {
			Context context = new InitialContext();
			RdvServiceRemote rdvser=(RdvServiceRemote)context.lookup(rdvjndi);
			ArrayList<Motif> p= (ArrayList<Motif>) rdvser.retrieveMotif(LoginController.session);
            ObservableList<Motif> obs=FXCollections.observableArrayList(p);
            purpose.setItems(obs);
            
            ppurp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Motif,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Motif, String> param) {
					StringProperty prop=new SimpleStringProperty(param.getValue().getLib());
					return prop;				
				}
			});
            pprix.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Motif,Integer>, ObservableValue<Integer>>() {            	
				@Override
				public ObservableValue<Integer> call(CellDataFeatures<Motif, Integer> param) {
					IntegerProperty prop=new SimpleIntegerProperty(param.getValue().getPrix());
					return prop.asObject();
				}
			});
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void refreshrdv() {
		   try {
					Context ct = new InitialContext();
					RdvServiceRemote rser=(RdvServiceRemote) ct.lookup(rdvjndi);
					ArrayList<Rendezvous> p= (ArrayList<Rendezvous>) rser.rdvm(LoginController.session);
		            ObservableList<Rendezvous> obs=FXCollections.observableArrayList(p);
				rdv.setItems(obs);
	            
	           patient.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
						String p=param.getValue().getPatient().getUser().getNom();
						StringProperty prop=new SimpleStringProperty(p+" "+param.getValue().getPatient().getUser().getPrenom());
						return prop;				
					}
				});
	            pretat.setCellValueFactory(new PropertyValueFactory<>("etat"));
	            
	            date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
						String date=param.getValue().getDate().toString();
						StringProperty prop=new SimpleStringProperty(date+" "+param.getValue().getDebut().toString());
						return prop;				
					}
				});
	            
	            birth.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
						return new SimpleStringProperty( param.getValue().getPatient().getBirth().toString());	
					}
				});
	            
	            but.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
						return new SimpleStringProperty( param.getValue().getMotif().getLib());	
					}
				});
	            cont.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
						return new SimpleStringProperty( param.getValue().getPatient().getUser().getMail()+" "+
					param.getValue().getPatient().getUser().getTel());	
					}
				});
	            
		   }catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	private void refreshPlan() throws NamingException {
		Context ct= new InitialContext();
		RdvServiceRemote rser=(RdvServiceRemote)ct.lookup(rdvjndi);
		List<Appointment> liste= new ArrayList<>();
		List<Planing> plan=rser.retrieveDispo(LoginController.session);		
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
	}
	
	private void refreshpath() {
		try {
			Context ct= new InitialContext();
			ParcoursServiceRemote pserv= (ParcoursServiceRemote) ct.lookup(parcoursjndi);
			
			ArrayList<Parcours> p= (ArrayList<Parcours>) pserv.retrievePa(LoginController.session);
            ObservableList<Parcours> obs=FXCollections.observableArrayList(p);
            patht.setItems(obs);
            
            pades.setCellValueFactory(new PropertyValueFactory<>("description"));
            
            ppat.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Parcours,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Parcours, String> param) {
					StringProperty prop=new SimpleStringProperty(param.getValue().getPat().getUser().getNom()+ " "+param.getValue().getPat().getUser().getPrenom());
					return prop;				
				}
			});
            
            ppat.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Parcours,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Parcours, String> param) {
					StringProperty prop=new SimpleStringProperty(param.getValue().getPat().getUser().getNom()+ " "+param.getValue().getPat().getUser().getPrenom());
					return prop;				
				}
			});
            
            pabirth.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Parcours,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Parcours, String> param) {
					return new SimpleStringProperty(param.getValue().getPat().getBirth().toString());
									
				}
			});
            
            paco.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Parcours,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Parcours, String> param) {
					StringProperty prop=new SimpleStringProperty(param.getValue().getPat().getUser().getMail()+ " "+param.getValue().getPat().getUser().getTel());
					return prop;				
				}
			});
            
            paref.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Parcours,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Parcours, String> param) {
					StringProperty prop=new SimpleStringProperty(param.getValue().getReferent().getUser().getNom()+ " "+param.getValue().getReferent().getUser().getPrenom());
					return prop;				
				}
			});
            
            parefco.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Parcours,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Parcours, String> param) {
					StringProperty prop=new SimpleStringProperty(param.getValue().getReferent().getUser().getMail()+ " "+param.getValue().getReferent().getUser().getTel());
					return prop;				
				}
			});
            
            
		} catch (NamingException e) {
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
		// TODO Auto-generated method stub
		
		filter.getItems().addAll("Done", "Canceled","Comfirmed","All");
		refreshmotif(); refreshrdv(); refreshpath();
		AgendaSkinSwitcher lAgendaSkinSwitcher = new AgendaSkinSwitcher(agenda);
        hb.getChildren().add(lAgendaSkinSwitcher);		  		  
		// accept new appointments
	        agenda.newAppointmentCallbackProperty().set(new Callback<Agenda.LocalDateTimeRange, Agenda.Appointment>()
	        {
	        	public Agenda.Appointment call(LocalDateTimeRange dateTimeRange)
	            {
	                return new Agenda.AppointmentImplLocal()
	                .withStartLocalDateTime( dateTimeRange.getStartLocalDateTime() )
	                .withEndLocalDateTime( dateTimeRange.getEndLocalDateTime() )
	                .withDescription("Plan") 
	                .withDescription("new")
	                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group14"));
	            }
	        });
	        
	    patht.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Parcours>() {
			@Override
			public void changed(ObservableValue<? extends Parcours> observable, Parcours oldValue, Parcours newValue) {
				refreshstep(newValue.getId());				
			}
		});
		 
		try {
			refreshPlan();
			Context ctx = new InitialContext();
			UserServiceRemote userv=(UserServiceRemote)ctx.lookup(userjndi);
			Medecin m= userv.sessionm(LoginController.session);
			String s=label.getText();
			label.setText(s+ " "+ m.getUser().getNom()+" "+m.getUser().getPrenom());
			Integer n=m.getUser().getTel();
			surname.setText(m.getUser().getPrenom()); name.setText(m.getUser().getNom()); num.setText(n.toString());
			mail.setText(m.getUser().getMail()); specialty.setText(m.getSpecialite()); city.setText(m.getVille());
			address.setText(m.getAdresse()); pwd.setText(m.getUser().getPwd()); description.setText(m.getDescription());
			
			pwd1.textProperty().addListener((observable, oldValue, newValue) -> {
	            if (pwd.getText().equals(pwd1.getText())){
	                error.setText("");
	            }else {
	                error.setText("Uncorresponding passwords");
	            }
	        });
			
			 			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
    @FXML
    void mailing(MouseEvent event) {

    }
    
    @FXML
    void pmodif(ActionEvent event) {

    }

    @FXML
    void showsteps(ActionEvent event) {
    	stshow.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Parcours p= patht.getSelectionModel().getSelectedItem();
		    	if(p!=null) {
		    		refreshstep(p.getId());
		    	}
			}
		});

    }

    @FXML
    void stcreate(ActionEvent event) {
    	stcr.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					Parent root=FXMLLoader.load(getClass().getResource("./Parcours.fxml"));
					stcr.getScene().setRoot(root);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
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
	// Event Listener on TableView.onSort
	@FXML
	public void triedate(SortEvent<TableView<Patient>> event) {
		// TODO Autogenerated
	}
	
	@FXML
    void filtrer(ActionEvent event) { //filtre les anulé
		ok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(filter.getValue()=="Canceled") {
					try {
						Context context= new InitialContext();
						RdvServiceRemote rdvser=(RdvServiceRemote)context.lookup(rdvjndi);					
						
						ArrayList<Rendezvous> p= (ArrayList<Rendezvous>) rdvser.rdvmT(LoginController.session);
			            ObservableList<Rendezvous> obs=FXCollections.observableArrayList(p);
					rdv.setItems(obs);
		            
		           patient.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
							String p=param.getValue().getPatient().getUser().getNom();
							StringProperty prop=new SimpleStringProperty(p+" "+param.getValue().getPatient().getUser().getPrenom());
							return prop;				
						}
					});
		            pretat.setCellValueFactory(new PropertyValueFactory<>("etat"));
		            
		            date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
							String date=param.getValue().getDate().toString();
							StringProperty prop=new SimpleStringProperty(date+" "+param.getValue().getDebut().toString());
							return prop;				
						}
					});
		            
		            birth.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
							return new SimpleStringProperty( param.getValue().getPatient().getBirth().toString());	
						}
					});
		            
		            but.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
							return new SimpleStringProperty( param.getValue().getMotif().getLib());	
						}
					});
		            cont.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous,String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<Rendezvous, String> param) {
							return new SimpleStringProperty( param.getValue().getPatient().getUser().getMail()+" "+
						param.getValue().getPatient().getUser().getTel());	
						}
					});
					} catch (NamingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					refreshrdv();
				}
				
			}
		});
    }
	
	@FXML
    void markdone(ActionEvent event) {
		markd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Rendezvous r=rdv.getSelectionModel().getSelectedItem();
				if(r!=null) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Do you want to mark this Appointment as done?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					try {
						Context context= new InitialContext();
						RdvServiceRemote rdvser=(RdvServiceRemote)context.lookup(rdvjndi);					
						rdvser.effectuerrdv(r.getId());
						refreshrdv();
					} catch (NamingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				}
				
				
			}
		});
    }

	// Event Listener on Button[#cancel].onAction
	@FXML
	public void cancel(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#confirm].onAction
	@FXML
	public void confirm(ActionEvent event) throws NamingException {
		// TODO Autogenerated
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText("Do you confirm adding those disponibility?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){

			Context ctx= new InitialContext();
			UserServiceRemote userv=(UserServiceRemote)ctx.lookup(userjndi);
			Medecin m= userv.sessionm(LoginController.session);
			List<Planing> plan= new ArrayList<>();
			
			for (Appointment i : agenda.appointments()) {
				Planing p= new Planing();	
				p.setJour(java.sql.Date.valueOf(i.getStartLocalDateTime().toLocalDate()));
				p.setDebut(Time.valueOf(i.getStartLocalDateTime().toLocalTime()));
				p.setFin(Time.valueOf(i.getEndLocalDateTime().toLocalTime()));
				p.setMed(m);
				plan.add(p);
			}
			Context ct= new InitialContext(); RdvServiceRemote rser=(RdvServiceRemote)ct.lookup(rdvjndi);
			rser.ajoutDispo(plan,LoginController.session);
			
		}
		
	}
	// Event Listener on Button[#editd].onAction
	@FXML
	public void editprofil(ActionEvent event) {
		editd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				surname.setEditable(true); name.setEditable(true); num.setEditable(true);
				mail.setEditable(true); specialty.setEditable(true); city.setEditable(true);
				address.setEditable(true); pwd.setEditable(true); description.setEditable(true);
				pwd1.setVisible(true); lpwd.setVisible(true); 
			}
		});
	}
	
	// Event Listener on Button[#confd].onAction
	@FXML
	public void confirmprofil(ActionEvent event) {
		confd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(name.getText().equals("") || num.getText().equals("") || mail.getText().equals("")  || specialty.getText().equals("") || city.getText().equals("") || address.getText().equals("")) {
					error.setText("All Fields are required");	
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
				}else if (!pwd.getText().equals(pwd1.getText())){
					error.setText("Confirm password");
				}else {
					try {
						Context ctx = new InitialContext();
						UserServiceRemote userv=(UserServiceRemote)ctx.lookup(userjndi);
						Medecin m= userv.sessionm(LoginController.session);
						
						User u=new User();
						u.setEtat(m.getUser().isEtat()); u.setImage(m.getUser().getImage()); 
						u.setMail(mail.getText()); u.setNom(name.getText());
						u.setPrenom(surname.getText()); u.setPwd(pwd.getText()); u.setTel(Integer.parseInt(num.getText()));
						
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Confirmation");
						alert.setHeaderText(null);
						alert.setContentText("Do you confirm the update?");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK){
							m.setUser(u); m.setDescription(description.getText()); m.setAdresse(address.getText());
							m.setVille(city.getText()); m.setSpecialite(specialty.getText());
							int a=userv.updateM(m);
							if(a==1) {
								Notifications.create().title("Successfully update your profil").showInformation();
								surname.setEditable(false); name.setEditable(false); num.setEditable(false);
								mail.setEditable(false); specialty.setEditable(false); city.setEditable(false);
								address.setEditable(false); pwd.setEditable(false); description.setEditable(false);
								pwd1.setVisible(false); lpwd.setVisible(false); 
							}else {
								Notifications.create().title("Failed to update").showError();
							}
						}
					} catch (NamingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		});
	}
	// Event Listener on Button[#modp].onAction
	@FXML
	public void modifyp(ActionEvent event) {
		modp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				motif=purpose.getSelectionModel().getSelectedItem();
				if(motif!=null) {
					gridp.setVisible(true);
					butp.setText(motif.getLib());
					price.setText(motif.getPrix().toString());
				}
				
			}
		});
	}
	
	// Event Listener on Button[#ajoutp].onAction
	@FXML
	public void addp(ActionEvent event) {
		ajoutp.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				gridp.setVisible(true);
				butp.setText("");price.setText("");
			}
		});
	}
	// Event Listener on Button[#delp].onAction
	@FXML
	public void deletep(ActionEvent event) {
		delp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Motif m= purpose.getSelectionModel().getSelectedItem();
				if(m!=null) {
				try {
					Context context= new InitialContext();
					RdvServiceRemote rdvser=(RdvServiceRemote)context.lookup(rdvjndi);
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation");
					alert.setHeaderText(null);
					alert.setContentText("Do you really want to delete this purpose of Appointment?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
						rdvser.deleteMotif(m.getId());
						refreshmotif();
					}
				}catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			}
		});
	}
	
	// Event Listener on Button[#confp].onAction
	@FXML
	public void confirmp(ActionEvent event) {
		confp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					Context ctx= new InitialContext(); 
					UserServiceRemote userv=(UserServiceRemote)ctx.lookup(userjndi);
					Context context= new InitialContext();
					RdvServiceRemote rdvser=(RdvServiceRemote)context.lookup(rdvjndi);
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation");
					alert.setHeaderText(null);
					
					if(motif==null) {
						motif= new Motif();
						motif.setLib(butp.getText());
						motif.setPrix(Integer.parseInt(price.getText()));
						motif.setDoc(userv.sessionm(LoginController.session));
						alert.setContentText("Do you really want to add this purpose of Appointment?");							
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK){
							rdvser.ajoutMotif(motif);
							motif=null;
							gridp.setVisible(false);
							refreshmotif();
							//refreshrdv();
						}
					}else {
						alert.setContentText("Do you really want to modify this purpose of Appointment?");							
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK){
							motif.setLib(butp.getText()); motif.setPrix(Integer.parseInt(price.getText()));
							rdvser.updateMotif(motif);
							motif=null;
							gridp.setVisible(false);
							refreshmotif();
						}
					}
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
