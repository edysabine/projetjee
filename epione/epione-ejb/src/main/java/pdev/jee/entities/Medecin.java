package pdev.jee.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@SuppressWarnings("serial")
@Entity
public class Medecin implements Serializable{
	private int id;
	private User user;
	private String specialite;
	private String ville;
	private String adresse;
	private String description;
	private Set<Planing> planing;
	private Set<Motif> motifs;
	private Set<Parcours> parcours;
	
	@OneToMany(mappedBy = "referent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
	public Set<Parcours> getParcours() {
		return parcours;
	}
	public void setParcours(Set<Parcours> parcours) {
		this.parcours = parcours;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Embedded
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	
	@OneToMany(mappedBy = "med",cascade = {CascadeType.PERSIST, CascadeType.REMOVE},fetch = FetchType.EAGER)
	public Set<Planing> getPlaning() {
		return planing;
	}
	public void setPlaning(Set<Planing> planing) {
		this.planing = planing;
	}
	
	@OneToMany(mappedBy = "doc", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
	public Set<Motif> getMotifs() {
		return motifs;
	}
	public void setMotifs(Set<Motif> motifs) {
		this.motifs = motifs;
	}
	
	public Medecin() {
		super();
	}
	
	public Medecin(User user, String specialite, String ville, String adresse, String description) {
		super();
		this.user = user;
		this.specialite = specialite;
		this.ville = ville;
		this.adresse = adresse;
		this.description = description;
	}
	
	
}
