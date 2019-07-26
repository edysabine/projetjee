package pdev.jee.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import pdev.jee.entities.Admin;
import pdev.jee.entities.Medecin;
import pdev.jee.entities.Patient;
import pdev.jee.interfaces.UserServiceRemote;

@Stateful
@LocalBean
public class UserService implements UserServiceRemote{
	
	@PersistenceContext(unitName = "epione-ejb")
	EntityManager em;

	@Override
	public void ajoutP(Patient p) {
		// TODO Auto-generated method stub
		em.persist(p);
	}

	@Override
	public void ajoutM(Medecin m) {
		// TODO Auto-generated method stub
		em.persist(m);
	}

	@Override
	public void updateP(Patient p) {
		em.merge(p);
		
	}

	@Override
	public int updateM(Medecin m) {
		Query q= em.createQuery("update Medecin m set m.user.nom=:a, m.user.prenom=:b ,m.user.mail=:c, m.user.tel=:d, m.user.pwd=:e"
				+ ",m.specialite=:f, m.ville=:g, m.adresse=:h , m.description=:i where m.id=:j");
		q.setParameter("a", m.getUser().getNom());
		q.setParameter("b", m.getUser().getPrenom());
		q.setParameter("c", m.getUser().getMail());
		q.setParameter("d", m.getUser().getTel());
		q.setParameter("e", m.getUser().getPwd());
		q.setParameter("f", m.getSpecialite());
		q.setParameter("g", m.getVille());
		q.setParameter("h", m.getAdresse());
		q.setParameter("i", m.getDescription());
		q.setParameter("j", m.getId());
		return q.executeUpdate();
	}

	@Override
	public void updateA(Admin a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void supprimerM(Medecin m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void supprimerP(Patient p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Admin retrieveA(String a, String b) {
		try{
			TypedQuery<Admin> query= em.createQuery("select ad from Admin ad where ad.mail=:a and ad.pwd=:b", Admin.class);
			query.setParameter("a", a);
			query.setParameter("b", b);
			return query.getSingleResult();
		}
		catch(NoResultException e){
			return null;
		}
		 catch (NonUniqueResultException nure) {
			 nure.printStackTrace();
			 return null;
		}
	}

	@Override
	public Patient retrieveP(String a, String b) {
		try {
		TypedQuery<Patient> querry=em.createQuery("select p from Patient p where p.user.mail=:a and p.user.pwd=:b", Patient.class);
		querry.setParameter("a", a);
		querry.setParameter("b", b);
		return querry.getSingleResult();
	}
	catch(NoResultException e){
		return null;
	}
	 catch (NonUniqueResultException nure) {
		 nure.printStackTrace();
		 return null;
	}
	}

	@Override
	public Medecin retrieveM(String a, String b) {
		try {
		TypedQuery<Medecin> querry=em.createQuery("select m from Medecin m where m.user.mail=:a and m.user.pwd=:b", Medecin.class);
		querry.setParameter("a", a);
		querry.setParameter("b", b);
		return querry.getSingleResult();
		}
		catch(NoResultException e){
			return null;
		}
		 catch (NonUniqueResultException nure) {
			 nure.printStackTrace();
			 return null;
		}
	}

	@Override
	public List<Patient> findAllP() {
		try {
			TypedQuery<Patient> querry=em.createQuery("select p from Patient p order by p.user.nom", Patient.class);
			return querry.getResultList();
		}
		catch(NoResultException e){
			return new ArrayList<Patient>();
		}
	}

	@Override
	public List<Medecin> findAllM() {
		try {
			TypedQuery<Medecin> querry=em.createQuery("select m from Medecin m where m.user.etat=:val or m.user.pwd!=:val2 order by m.user.nom", Medecin.class);
			querry.setParameter("val", true);
			querry.setParameter("val2", "default");
			return querry.getResultList();
		}
		catch(NoResultException e){
			return new ArrayList<Medecin>();
		}
	}

	@Override
	public List<Medecin> findAllMC() {
		try {
			TypedQuery<Medecin> querry=em.createQuery("select m from Medecin m  where m.user.etat=:val and  m.user.pwd=:val2 order by m.user.nom", Medecin.class);
			querry.setParameter("val", false);
			querry.setParameter("val2", "default");
			return querry.getResultList();
		}
		catch(NoResultException e){
			return new ArrayList<Medecin>();
		}
	}

	@Override
	public void activated(int id) {
		Medecin m=em.find(Medecin.class, id);
		m.getUser().setEtat(true);
		
	}

	@Override
	public void activatep(int id) {
		Patient m=em.find(Patient.class, id);
		m.getUser().setEtat(true);
		
	}

	@Override
	public void created(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivated(int id) {
		Medecin m=em.find(Medecin.class, id);
		m.getUser().setEtat(false);
		
	}

	@Override
	public void deactivatep(int id) {
		Patient m=em.find(Patient.class, id);
		m.getUser().setEtat(false);
	}

	@Override
	public Medecin sessionm(int id) {
		Medecin m=em.find(Medecin.class, id);
		return m;
	}

	@Override
	public Patient sessionp(int id) {
		Patient p=em.find(Patient.class, id);
		return p;
	}

	@Override
	public List<Patient> findP(String n) {
		try {
			TypedQuery<Patient> querry=em.createQuery("select p from Patient p where p.user.nom=:n", Patient.class);
			querry.setParameter("n", n);
			return querry.getResultList();
		}
		catch(NoResultException e){
			return new ArrayList<Patient>();
		}
	}

	@Override
	public List<Medecin> findM(String n, String s) {
		try {
		TypedQuery<Medecin> querry=em.createQuery("select m from Medecin m where m.user.nom=:n or m.specialite=:s", Medecin.class);
		querry.setParameter("n", n);
		querry.setParameter("s", s);
		return querry.getResultList();
	}
	catch(NoResultException e){
		return new ArrayList<>();
	}
	}

}
