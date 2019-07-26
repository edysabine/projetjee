package pdev.jee.interfaces;

import java.util.List;

import javax.ejb.Remote;

import pdev.jee.entities.Etape;
import pdev.jee.entities.Parcours;

@Remote
public interface ParcoursServiceRemote {
	int ajoutPa(Parcours P);
	List<Parcours> retrievePa(int id);
	List<Etape> retrieveEt(int idp);
	
	Parcours recupereP(int id);
	void modifEt(Etape p);
	void ajoutE(Etape p);
	Parcours parcoursSt(int id);
	
	
}
