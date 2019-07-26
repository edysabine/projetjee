package pdev.jee.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pdev.jee.entities.Etape;
import pdev.jee.entities.Parcours;
import pdev.jee.interfaces.ParcoursServiceRemote;

@Stateful
@LocalBean
public class ParcoursService implements ParcoursServiceRemote{

	@PersistenceContext(unitName = "epione-ejb")
	EntityManager em;
	
	@Override
	public int ajoutPa(Parcours P) {
		em.persist(P);
		return P.getId();
		
	}

	@Override
	public List<Parcours> retrievePa(int id) {
		try {
			TypedQuery<Parcours> q=em.createQuery("select p from Parcours p where p.referent.id=:id"
					+ " union "
					+ "select distinct e.parcours from Etape e where e.mot.doc.id=:id",Parcours.class);
			q.setParameter("id", id);
			return q.getResultList();
		}catch(NoResultException n) {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Etape> retrieveEt(int idp) {
		try {
			TypedQuery<Etape> q=em.createQuery("select e from Etape e where e.parcours.id=:id",Etape.class);
			q.setParameter("id", idp);
			return q.getResultList();
		}catch(NoResultException n) {
			return new ArrayList<>();
		}
	}

	@Override
	public Parcours recupereP(int id) {
		try {
			TypedQuery<Parcours> q=em.createQuery("select p from Parcours p where p.pat.id=:id",Parcours.class);
			q.setParameter("id", id);
			return q.getSingleResult();
		}catch(NoResultException n) {
			return null;
		}catch (NonUniqueResultException e) {
			return null;
		}
	}

	@Override
	public void modifEt(Etape p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ajoutE(Etape p) {
		em.persist(p);
		
	}

	@Override
	public Parcours parcoursSt(int id) {
		return em.find(Parcours.class, id);
	}

}
