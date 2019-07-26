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

@SuppressWarnings("serial")
@Entity
public class Motif implements Serializable {
	private int id;
	private Integer prix;
	private String lib;
	private Medecin doc;
	private  Set<Rendezvous> rdv;
	private Set<Etape> etape;
	
	@OneToMany(mappedBy = "motif",cascade = {CascadeType.PERSIST, CascadeType.REMOVE},fetch = FetchType.EAGER)
	public Set<Rendezvous> getRdv() {
		return rdv;
	}
	public void setRdv(Set<Rendezvous> rdv) {
		this.rdv = rdv;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getPrix() {
		return prix;
	}
	public void setPrix(Integer prix) {
		this.prix = prix;
	}
	@ManyToOne
	public Medecin getDoc() {
		return doc;
	}
	public void setDoc(Medecin doc) {
		this.doc = doc;
	}
	public String getLib() {
		return lib;
	}
	public void setLib(String lib) {
		this.lib = lib;
	}
	@OneToMany(mappedBy = "mot", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
	public Set<Etape> getEtape() {
		return etape;
	}
	public void setEtape(Set<Etape> etape) {
		this.etape = etape;
	}
	
	
}
