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

import pdev.jee.entities.Medecin;
import pdev.jee.entities.Motif;
import pdev.jee.entities.Planing;
import pdev.jee.entities.Rendezvous;
import pdev.jee.interfaces.RdvServiceRemote;

@Stateful
@LocalBean
public class RdvService implements RdvServiceRemote{
	@PersistenceContext(unitName = "epione-ejb")
	EntityManager em;

	@Override
	public void ajoutMotif(Motif m) {
		em.persist(m);		
	}

	@Override
	public void updateMotif(Motif m) {
		
		  Motif motif= em.find(Motif.class, m.getId()); motif.setLib(m.getLib());
		  motif.setPrix(m.getPrix());
		 
		/*
		 * Query q=
		 * em.createQuery("update Motif m set m.prix=:a, m.lib=:b where m.id=:c");
		 * q.setParameter("a", m.getPrix()); q.setParameter("b", m.getLib());
		 * q.setParameter("c", m.getId());
		 * 
		 * int i=q.executeUpdate();
		 */
	}

	@Override
	public void ajoutDispo(List<Planing> planing,int id) {
		
		  Query q= em.createQuery("delete from Planing p where p.med.id=:c");
		  q.setParameter("c", id); q.executeUpdate();
		 
		for(Planing p: planing) {
			em.persist(p);
		}
		
	}

	@Override
	public List<Planing> retrieveDispo(int id) {
		try{
			TypedQuery<Planing> query= em.createQuery("select p from Planing p where p.med.id=:id", Planing.class);
			query.setParameter("id", id);
			return query.getResultList();
		}
		catch(NoResultException e){
			return new ArrayList<>();
		}
	}

	@Override
	public List<Motif> retrieveMotif(int id) {
		try{
			TypedQuery<Motif> query= em.createQuery("select m from Motif m where m.doc.id=:id", Motif.class);
			query.setParameter("id", id);
			return query.getResultList();
		}
		catch(NoResultException e){
			return new ArrayList<Motif>();
		}
	}

	@Override
	public void deleteMotif(int m) {
		// TODO Auto-generated method stub
		/*
		 * Motif mo=em.find(Motif.class, m); em.remove(mo);
		 */
		
		Query q= em.createQuery("delete from Motif m where m.id=:c");
				  q.setParameter("c", m);
				  q.executeUpdate();
	}

	@Override
	public List<Medecin> chercher(String ville, String nom, String spe) {
		try{
			TypedQuery<Medecin> query= em.createQuery("select m from Medecin m where m.ville=:v or m.user.nom=:n or m.specialite=:s", Medecin.class);
			query.setParameter("v", ville);
			query.setParameter("n", nom);
			query.setParameter("s", spe);
			return query.getResultList();
		}
		catch(NoResultException e){
			return new ArrayList<>();
		}
	}

	@Override
	public List<Medecin> cherchertout() {
		try{
			TypedQuery<Medecin> query= em.createQuery("select m from Medecin m where m.user.etat=:v", Medecin.class);
			query.setParameter("v", true);
			return query.getResultList();
		}
		catch(NoResultException e){
			return new ArrayList<>();
		}
	}

	@Override
	public Motif purpose(String a, int id) {
		try {
			TypedQuery<Motif> querry=em.createQuery("select m from Motif m where m.lib=:a and m.doc.id=:b", Motif.class);
			querry.setParameter("a", a);
			querry.setParameter("b", id);
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
	public void ajoutRdv(Rendezvous rdev) {
		em.persist(rdev); //ne marche pas pour l'ajout de Patient et Motif avec 'insertable updatable=false'
		//Query q=("insert into Motif)
		
	}

	@Override
	public List<Rendezvous> rdvp(int id) {
		try{
			TypedQuery<Rendezvous> query= em.createQuery("select r from Rendezvous r where r.patient.id=:v", Rendezvous.class);
			query.setParameter("v", id);
			return query.getResultList();
		}
		catch(NoResultException e){
			return null;
		}
	}

	@Override
	public List<Rendezvous> rdvm(int id) {
		try{
			TypedQuery<Rendezvous> query= em.createQuery("select r from Rendezvous r where r.motif.doc.id=:v", Rendezvous.class);
			query.setParameter("v", id);
			return query.getResultList();
		}
		catch(NoResultException e){
			return null;
		}
	}

	@Override
	public void anulerrdv(int id) {
		Rendezvous r=em.find(Rendezvous.class,id);
		r.setEtat("Canceled");
		
	}

	@Override
	public List<Rendezvous> rdvmT(int id) {
		try{
			TypedQuery<Rendezvous> query= em.createQuery("select r from Rendezvous r where r.motif.doc.id=:v and r.etat=:e", Rendezvous.class);
			query.setParameter("v", id);
			query.setParameter("e", "Canceled");
			return query.getResultList();
		}
		catch(NoResultException e){
			return new ArrayList<>();
		}
	}

	@Override
	public void effectuerrdv(int id) {
		// TODO Auto-generated method stub
		Rendezvous r=em.find(Rendezvous.class,id);
		r.setEtat("Done");
	}

}
