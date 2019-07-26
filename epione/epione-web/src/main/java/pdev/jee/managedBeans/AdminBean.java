package pdev.jee.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pdev.jee.dep.ControleSaisie;
import pdev.jee.entities.Medecin;
import pdev.jee.entities.Patient;
import pdev.jee.services.UserService;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class AdminBean implements Serializable{
	private int id;
	private String mail;
	private String pwd;
	
	private Medecin med;
	
	private List<Patient> patients=new ArrayList<>();
	private List<Medecin> medecins=new ArrayList<>();
	private List<Medecin> medecinsc=new ArrayList<>();
	
	@EJB
	UserService user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public List<Patient> getPatients() {
		return patients;
	}
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	public List<Medecin> getMedecins() {
		return medecins;
	}
	public void setMedecins(List<Medecin> medecins) {
		this.medecins = medecins;
	}
	public List<Medecin> getMedecinsc() {
		return medecinsc;
	}
	public void setMedecinsc(List<Medecin> medecinsc) {
		this.medecinsc = medecinsc;
	}
	
	@PostConstruct
	public void init() {
		if(LoginBean.admin!=null) {
			patients=user.findAllP();
			medecins=user.findAllM();
			medecinsc=user.findAllMC();
		}
	}
	
	public void create(Medecin m)
	{
		user.activated(m.getId());
		medecinsc=user.findAllMC();
		ControleSaisie.sendMail(m.getUser().getMail(), "Creation of your count", "Your account have been created by the Admin\n Your default password is 'default'"
			+ ",you have to change it in your space.\n "
			+ "\nYou also need to specify purposes of appointment.\nBest regards");
	}
	public Medecin getMed() {
		return med;
	}
	public void setMed(Medecin med) {
		this.med = med;
	}
	
	public void activate(Patient p) {
		
	}
	
	public void deactivate(Patient p) {
		
	}
	
}
