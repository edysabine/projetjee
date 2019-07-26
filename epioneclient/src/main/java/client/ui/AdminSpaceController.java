package client.ui;

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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import pdev.jee.entities.Medecin;
import pdev.jee.entities.Patient;
import pdev.jee.interfaces.UserServiceRemote;

public class AdminSpaceController implements Initializable{
	
	String userjndi="epione-ear/epione-ejb/UserService!pdev.jee.interfaces.UserServiceRemote";
	
	@FXML
	private ImageView logout;
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
    private TableColumn<Patient, String> psate;

    @FXML
    private Button pact;

    @FXML
    private TableColumn<Medecin, String> dname;

    @FXML
    private TableColumn<Medecin, String> dfn;

    @FXML
    private TableColumn<Medecin, String> dmail;

    @FXML
    private TableColumn<Medecin, String> dadd;

    @FXML
    private TableColumn<Medecin, Integer> dnum;

    @FXML
    private TableColumn<Medecin, String> dspe;

    @FXML
    private TableColumn<Medecin, String> dstate;

    @FXML
    private TableColumn<Medecin, String> ddesc;

    @FXML
    private Button dact;

    @FXML
    private TableColumn<Medecin, String> cname;

    @FXML
    private TableColumn<Medecin, String> clastn;

    @FXML
    private TableColumn<Medecin, String> cmail;

    @FXML
    private TableColumn<Medecin, String> cadd;

    @FXML
    private TableColumn<Medecin, Integer> cnum;

    @FXML
    private TableColumn<Medecin, String> cspe;

    @FXML
    private TableColumn<Medecin, String> cdesc;

    @FXML
    private Button create;
	
    @FXML
    private TableView<Medecin> dtable;
    
    @FXML
    private TableView<Patient> ptable;
    
    @FXML
    private TableView<Medecin> ctable;
    
    @FXML
    private ImageView mail;
    
    
    private void refreshP() {
    	try {
			Context ctx=new InitialContext();
			UserServiceRemote service= (UserServiceRemote)ctx.lookup(userjndi);
    	// Patient Table
    				ArrayList<Patient> p= (ArrayList<Patient>) service.findAllP();
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
    	            psate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient,String>, ObservableValue<String>>() {

    					@Override
    					public ObservableValue<String> call(CellDataFeatures<Patient, String> param) {
    						if(param.getValue().getUser().isEtat())
    							return new SimpleStringProperty("Activated");
    						else
    							return new SimpleStringProperty("Deactivated");
    					}
    				});
    				
    	            pbirth.setCellValueFactory(new PropertyValueFactory<>("birth"));
    	} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    private void refreshM() {
    	try {
			Context ctx=new InitialContext();
			UserServiceRemote service= (UserServiceRemote)ctx.lookup(userjndi);
			
			 // Doctor Table
         	ArrayList<Medecin> m= (ArrayList<Medecin>) service.findAllM();
            ObservableList<Medecin> obse=FXCollections.observableArrayList(m);
            dtable.setItems(obse);
            dname.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medecin,String>, ObservableValue<String>>() {
         				@Override
         				public ObservableValue<String> call(CellDataFeatures<Medecin, String> param) {
         					StringProperty prop=new SimpleStringProperty(param.getValue().getUser().getNom());
         					return prop;				
         				}
         			});
            dfn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medecin,String>, ObservableValue<String>>() {

         				@Override
         				public ObservableValue<String> call(CellDataFeatures<Medecin, String> param) {
         					return new SimpleStringProperty(param.getValue().getUser().getPrenom());
         				}
         			});
             dmail.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medecin,String>, ObservableValue<String>>() {

         				@Override
         				public ObservableValue<String> call(CellDataFeatures<Medecin, String> param) {
         					return new SimpleStringProperty(param.getValue().getUser().getMail());
         				}
         			});
                     
              dnum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medecin,Integer>, ObservableValue<Integer>>() {            	
         				@Override
         				public ObservableValue<Integer> call(CellDataFeatures<Medecin, Integer> param) {
         					IntegerProperty prop=new SimpleIntegerProperty(param.getValue().getUser().getTel());
         					return prop.asObject();
         				}
         			});
             dstate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medecin,String>, ObservableValue<String>>() {

         				@Override
         				public ObservableValue<String> call(CellDataFeatures<Medecin, String> param) {
         					if(param.getValue().getUser().isEtat())
         						return new SimpleStringProperty("Activated");
         					else
         						return new SimpleStringProperty("Deactivated");
         				}
         			});
         			
            dadd.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            ddesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            dspe.setCellValueFactory(new PropertyValueFactory<>("specialite"));
            
    	}catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void refreshMC() {
    	try {
			Context ctx=new InitialContext();
			UserServiceRemote service= (UserServiceRemote)ctx.lookup(userjndi);			
			// Doctor2 Table
         	ArrayList<Medecin> mr= (ArrayList<Medecin>) service.findAllMC();
            ObservableList<Medecin> obser=FXCollections.observableArrayList(mr);
            ctable.setItems(obser);
            cname.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medecin,String>, ObservableValue<String>>() {
         				@Override
         				public ObservableValue<String> call(CellDataFeatures<Medecin, String> param) {
         					StringProperty prop=new SimpleStringProperty(param.getValue().getUser().getNom());
         					return prop;				
         				}
         			});
            clastn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medecin,String>, ObservableValue<String>>() {

         				@Override
         				public ObservableValue<String> call(CellDataFeatures<Medecin, String> param) {
         					return new SimpleStringProperty(param.getValue().getUser().getPrenom());
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
            cadd.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            cdesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            cspe.setCellValueFactory(new PropertyValueFactory<>("specialite"));
    	}catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    public void initialize(URL location, ResourceBundle resources) {
       refreshP(); refreshM(); refreshMC();
	}

	
	// Event Listener on ImageView[#logout].onMouseClicked
	@FXML
	public void logout(MouseEvent event) {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("./Admin.fxml"));
			logout.getScene().setRoot(root);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String content="Your account have been desactivated by the Admin\n Best regards.";
	private String content2="Your account have been activated by the Admin\n Best regards";
	private String content3="Your account have been created by the Admin\n Your default password is 'default'"
			+ ",you have to change it in your space.\n "
			+ "\nYou also need to specify purposes of appointment.\nBest regards";
	
	 @FXML
	 void dactivate(ActionEvent event) {
		 dact.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				Medecin m=dtable.getSelectionModel().getSelectedItem();
				if(m!=null) {
					 try{
						Context ctx = new InitialContext();
						UserServiceRemote service= (UserServiceRemote)ctx.lookup(userjndi);
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Confirmation");
						alert.setHeaderText(null);
						
						if(m.getUser().isEtat()) {
							alert.setContentText("Do you really want to deactivate the count?\n"
									+"The user will recieve a notification by mail");							
							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == ButtonType.OK){
								service.deactivated(m.getId());
								ControleSaisie.sendMail(m.getUser().getMail(), "About your count", content);
								refreshM();
							}
	
						}else {
							alert.setContentText("Do you really want to activate the count?\n" +
						"The user will recieve a notification by mail");							
							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == ButtonType.OK){
								service.activated(m.getId());
								ControleSaisie.sendMail(m.getUser().getMail(), "About your count", content2);
								refreshM();
						}
					}
					}catch (NamingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
				}
				
			}
		});

	 }

	 @FXML
	 void dcreate(ActionEvent event) {
		 create.setOnAction(new EventHandler<ActionEvent>() {				
				public void handle(ActionEvent event) {
					Medecin m=ctable.getSelectionModel().getSelectedItem();
					if(m!=null) {
						 try{
							Context ctx = new InitialContext();
							UserServiceRemote service= (UserServiceRemote)ctx.lookup(userjndi);
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Confirmation");
							alert.setHeaderText(null);
							
							if(!m.getUser().isEtat()) {
								alert.setContentText("Do you really want to create the count?\n" +
							"The user will recieve a notification by mail");							
								Optional<ButtonType> result = alert.showAndWait();
								if (result.get() == ButtonType.OK){
									service.activated(m.getId());
									ControleSaisie.sendMail(m.getUser().getMail(), "About your count", content3);
									refreshMC(); refreshM();
							}
							
						}
						}catch (NamingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
						}
					}
					
				}
			});
	 }


	 @FXML
	 void pactivate(ActionEvent event) {
		 pact.setOnAction(new EventHandler<ActionEvent>() {			
				@Override
				public void handle(ActionEvent event) {
					Patient m=ptable.getSelectionModel().getSelectedItem();
					if(m!=null) {
						 try{
							Context ctx = new InitialContext();
							UserServiceRemote service= (UserServiceRemote)ctx.lookup(userjndi);
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Confirmation");
							alert.setHeaderText(null);
							
							if(m.getUser().isEtat()) {
								alert.setContentText("Do you really want to deactivate the count?\n"
										+ "The user will recieve a notification by mail");							
								Optional<ButtonType> result = alert.showAndWait();
								if (result.get() == ButtonType.OK){
									service.deactivatep(m.getId());
									ControleSaisie.sendMail(m.getUser().getMail(), "About your count", content);
									refreshP();
								}
		
							}else {
								alert.setContentText("Do you really want to activate the count?\n"+
							"The user will recieve a notification by mail");							
								Optional<ButtonType> result = alert.showAndWait();
								if (result.get() == ButtonType.OK){
									service.activatep(m.getId());
									ControleSaisie.sendMail(m.getUser().getMail(), "About your count", content2);
									refreshP();
								}
								
							}
						}catch (NamingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
						}
					}
					
				}
			});
	 }
	

	    @FXML
	    void mailing(MouseEvent event) {
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("You will send a mail");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){		
							
			}
	    }
}
