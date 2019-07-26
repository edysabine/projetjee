package pdev.jee.interfaces;

import java.util.List;

import javax.ejb.Remote;

import pdev.jee.entities.Medecin;
import pdev.jee.entities.Motif;
import pdev.jee.entities.Planing;
import pdev.jee.entities.Rendezvous;

@Remote
public interface RdvServiceRemote {
	void ajoutMotif(Motif m);
	void updateMotif(Motif m);
	void deleteMotif(int m);
	
	void ajoutDispo(List<Planing> planing,int id);
	List<Planing> retrieveDispo(int id);
	List<Motif> retrieveMotif(int id);
	List<Medecin> chercher(String ville, String nom, String spe);
	List<Medecin> cherchertout();
	
	Motif purpose(String a,int id);
	void ajoutRdv(Rendezvous rdev);
	List<Rendezvous> rdvm(int id);
	List<Rendezvous> rdvp(int id);
	List<Rendezvous> rdvmT(int id);
	
	void anulerrdv(int id);
	void effectuerrdv(int id);
}
