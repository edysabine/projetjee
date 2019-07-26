package pdev.jee.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class Parcours implements Serializable{
	private int id;
	private String description;
	private Medecin referent;
	private Patient pat;
	private Set<Etape> etapes;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@ManyToOne
	public Medecin getReferent() {
		return referent;
	}
	public void setReferent(Medecin referent) {
		this.referent = referent;
	}
	
	@OneToMany(mappedBy = "parcours", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
	public Set<Etape> getEtapes() {
		return etapes;
	}
	public void setEtapes(Set<Etape> etapes) {
		this.etapes = etapes;
	}
	@OneToOne
	public Patient getPat() {
		return pat;
	}
	public void setPat(Patient pat) {
		this.pat = pat;
	}
	
	
	

}
