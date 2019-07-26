package pdev.jee.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pdev.jee.entities.Motif;
import pdev.jee.services.RdvService;


@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class Data implements Serializable{
	private List<Motif> motifs;
	public Role[] getRoles() {
		return Role.values();
	}
	
	@EJB
	RdvService rdv;
	
	@PostConstruct
	public void init() {
		motifs=new ArrayList<Motif>();
	}

	public List<Motif> getMotifs() {
		return motifs;
	}

	public void setMotifs(List<Motif> motifs) {
		this.motifs = motifs;
	}
	
	public void listMotifs(int id) {
		
		motifs=rdv.retrieveMotif(id);
	}
}
