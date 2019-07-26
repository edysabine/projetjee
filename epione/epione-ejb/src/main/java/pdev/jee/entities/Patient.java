package pdev.jee.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Patient implements Serializable{
	private int id;
	private User user;
	private Date birth;
	private  Set<Rendezvous> rdv;
	private Parcours parcours;
	
	@OneToMany(mappedBy = "patient",cascade = {CascadeType.PERSIST, CascadeType.REMOVE},fetch = FetchType.EAGER)
	public Set<Rendezvous> getRdv() {
		return rdv;
	}
	@OneToOne(mappedBy = "pat", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
	public Parcours getParcours() {
		return parcours;
	}
	public void setParcours(Parcours parcours) {
		this.parcours = parcours;
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
	
	@Embedded
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	
}
