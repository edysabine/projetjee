package pdev.jee.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pdev.jee.entities.Etape;
import pdev.jee.entities.Medecin;
import pdev.jee.entities.Motif;
import pdev.jee.entities.Parcours;
import pdev.jee.entities.Patient;
import pdev.jee.entities.Rendezvous;
import pdev.jee.entities.User;
import pdev.jee.services.ParcoursService;
import pdev.jee.services.RdvService;
import pdev.jee.services.UserService;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class PatientBean implements Serializable{
	private int id;
	private Date birth;
	private String nom;
	private String prenom;
	private String mail;
	private String pwd;
	private String image;
	private int tel;
	private String confp;
	private boolean etat;
	private List<Rendezvous> rdvs;
	private List<Parcours> parcours=new ArrayList<>();
	private List<Etape> steps=new ArrayList<>();
	private List<Patient> patients=new ArrayList<>();
	private List<Medecin> medecins=new ArrayList<>();
	private List<Motif> motifs;
	
	
	
	public String getConfp() {
		return confp;
	}
	public void setConfp(String confp) {
		this.confp = confp;
	}
	public List<Rendezvous> getRdvs() {
		return rdvs;
	}
	public void setRdvs(List<Rendezvous> rdvs) {
		this.rdvs = rdvs;
	}
	public List<Parcours> getParcours() {
		return parcours;
	}
	public void setParcours(List<Parcours> parcours) {
		this.parcours = parcours;
	}
	public List<Etape> getSteps() {
		return steps;
	}
	public void setSteps(List<Etape> steps) {
		this.steps = steps;
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
	public List<Motif> getMotifs() {
		return motifs;
	}
	public void setMotifs(List<Motif> motifs) {
		this.motifs = motifs;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getTel() {
		return tel;
	}
	public void setTel(int tel) {
		this.tel = tel;
	}
	public boolean isEtat() {
		return etat;
	}
	public void setEtat(boolean etat) {
		this.etat = etat;
	}
	
	@EJB
	UserService userv;
	@EJB
	RdvService rserv;
	@EJB
	ParcoursService pserv;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	@PostConstruct
	public void init() {
		motifs=new ArrayList<Motif>();
		if(LoginBean.patient!=null) {
			rdvs=rserv.rdvp(LoginBean.patient.getId());
			parcours.add(0,pserv.recupereP(LoginBean.patient.getId()));
			steps=new ArrayList<>();
			patients=new ArrayList<>();
			this.setId(LoginBean.patient.getId());
			this.setBirth(LoginBean.patient.getBirth());
			this.setMail(LoginBean.patient.getUser().getMail());
			this.setNom(LoginBean.patient.getUser().getNom());
			this.setPrenom(LoginBean.patient.getUser().getPrenom());
			this.setTel(LoginBean.patient.getUser().getTel());
			this.setPwd(LoginBean.patient.getUser().getPwd());
			this.setEtat(LoginBean.patient.getUser().isEtat());
		}

	}
	
	public void markD(Rendezvous d) {
		rserv.anulerrdv(d.getId());
		rdvs=rserv.rdvp(LoginBean.patient.getId());
	}
	
	public void stepsvue(Parcours p){
		this.setSteps(pserv.retrieveEt(p.getId()));
		//return "/pages/doctor/steps?faces-redirect=true";
	}
	
	public void updateP() {
		Patient p=new Patient();
		p.setId(id);p.setBirth(birth);
		User u=new User();
		u.setEtat(etat); u.setMail(mail); u.setNom(nom); u.setPrenom(prenom); u.setPwd(pwd);
		u.setTel(tel);
		p.setUser(u);
		userv.updateP(p);
	}
	
}
