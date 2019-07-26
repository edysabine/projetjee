package pdev.jee.interfaces;

import java.util.List;

import javax.ejb.Remote;

import pdev.jee.entities.Admin;
import pdev.jee.entities.Medecin;
import pdev.jee.entities.Patient;

@Remote
public interface UserServiceRemote {
	
	void ajoutP(Patient p);
	void ajoutM(Medecin m);
	void updateP(Patient p);
	int updateM(Medecin m);
	void updateA(Admin a);
	void supprimerM(Medecin m);
	void supprimerP(Patient p);
	
	Admin retrieveA(String a, String b);
	Patient retrieveP(String a, String b);
	Medecin retrieveM(String a, String b);
	
	List<Patient> findAllP();
	List<Medecin> findAllM();
	List<Medecin> findAllMC();
	
	void activated(int id);
	void activatep(int id);
	void created(int id);
	void deactivated(int id);
	void deactivatep(int id);
	
	Medecin sessionm(int id);
	Patient sessionp(int id);
	List<Patient> findP(String n);
	List<Medecin> findM(String n, String s);
}
