package pdev.jee.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
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
import pdev.jee.entities.Planing;
import pdev.jee.entities.Rendezvous;
import pdev.jee.entities.User;
import pdev.jee.services.ParcoursService;
import pdev.jee.services.RdvService;
import pdev.jee.services.UserService;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class MedecinBean implements Serializable{
	private int id;
	private String nom;
	private String prenom;
	private String mail;
	private String pwd;
	private String image;
	private int tel;
	private boolean etat;
	private List<Rendezvous> rdvs;
	private List<Planing> plans;
	private List<Parcours> parcours=new ArrayList<>();
	private List<Etape> steps=new ArrayList<>();
	private List<Patient> patients=new ArrayList<>();
	private List<Medecin> medecins=new ArrayList<>();
	private List<Motif> motifs;
	
	public List<Medecin> getMedecins() {
		return medecins;
	}
	public void setMedecins(List<Medecin> medecins) {
		this.medecins = medecins;
	}

	private int idp;
	private Parcours parc;
	
	public int getIdp() {
		return idp;
	}
	public void setIdp(int idp) {
		this.idp = idp;
	}
	public Parcours getParc() {
		return parc;
	}
	public void setParc(Parcours parc) {
		this.parc = parc;
	}

	
	
	public List<Patient> getPatients() {
		return patients;
	}
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	public List<Etape> getSteps() {
		return steps;
	}
	public void setSteps(List<Etape> steps) {
		this.steps = steps;
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
	public List<Planing> getPlans() {
		return plans;
	}
	public void setPlans(List<Planing> plans) {
		this.plans = plans;
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
	
	private String specialite;
	private String ville;
	private String adresse;
	private String description;
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
	public String getSpecialite() {
		return specialite;
	}
	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Motif> getMotifs() {
		return motifs;
	}

	public void setMotifs(List<Motif> motifs) {
		this.motifs = motifs;
	}
	
	public void listMotifs(int id) {
		
		motifs=rserv.retrieveMotif(id);
	}
	
	@PostConstruct
	public void init() {
		motifs=new ArrayList<Motif>();
		if(LoginBean.loggedIn && LoginBean.medecin!=null) {
			rdvs=rserv.rdvm(LoginBean.medecin.getId());
			parcours=pserv.retrievePa(LoginBean.medecin.getId());
			steps=new ArrayList<>();
			patients=new ArrayList<>();
			this.setId(1);
		}
		/*
		 * else if(LoginBean.loggedIn && LoginBean.patient!=null) {
		 * rdvs=rserv.rdvp(LoginBean.patient.getId()); }
		 */
	}
	
	public String addMed() {
		User u=new User();
		u.setEtat(false); u.setMail(mail); u.setNom(nom); u.setTel(tel); u.setPrenom(prenom);
		u.setPwd("default");
		userv.ajoutM(new Medecin(u,specialite, ville,adresse, description));
		return "/pages/doctor/welcome?faces-redirect=true";
	}
	
	public void markD(Rendezvous d) {
		rserv.effectuerrdv(d.getId());
	}
	
	
	public void stepsvue(Parcours p){
		this.setSteps(pserv.retrieveEt(p.getId()));
		//return "/pages/doctor/steps?faces-redirect=true";
	}
	
	public String stepsadd(Parcours p){
		idp=p.getId();
		parc=p;
		return "/pages/doctor/steps?faces-redirect=true";
	}
	
	public void recherche() {
		this.patients=userv.findP(prenom);
	}
	
	public void afficher(Patient p) {
		this.setNom(p.getUser().getNom()+" "+ p.getUser().getPrenom());
		this.setId(p.getId());
	}
	
	public void afficherM(Medecin p) {
		this.setNom(p.getUser().getNom()+" "+ p.getUser().getPrenom());
		this.setId(p.getId());
		listMotifs(id);
	}
	
	public void rechercheM() {
		this.medecins=userv.findM(prenom, prenom);
	}
	
	public String ajoutPa() {
		if(LoginBean.medecin!=null) {
		Parcours p=new Parcours();
		p.setDescription(image); p.setPat(userv.sessionp(id));
		p.setReferent(LoginBean.medecin);
		idp=pserv.ajoutPa(p);
		parc=pserv.parcoursSt(idp);
		this.setNom("");this.setPrenom(""); this.setId(0);
		return "/pages/doctor/steps?faces-redirect=true";
		}
		return "/pages/login?faces-redirect=true";
	}
	
	public String ajoutE() {
		if(LoginBean.medecin!=null) {
		Etape e=new Etape(); 
		e.setDescri(image); e.setEtat("To do"); e.setParcours(parc); e.setPriorite(adresse);
		Motif m=rserv.purpose(ville, id);
		e.setMot(m);
		pserv.ajoutE(e);
		this.setNom("");this.setPrenom("");
		this.setAdresse(""); this.setVille(""); this.setMail("");
		this.medecins=new ArrayList<>();
		return "/pages/doctor/steps?faces-redirect=true";
		}
		return "/pages/login?faces-redirect=true";
	}
	
	public void delete() {
		
	}
}
