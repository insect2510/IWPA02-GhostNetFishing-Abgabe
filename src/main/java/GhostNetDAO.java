import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

@Named
@ApplicationScoped
public class GhostNetDAO implements Serializable

{
	private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostNetUnit");

	@Inject
	Sea sea;

	GhostNet ghostnet;

	@Inject
	LoginController loginController;

	GhostNetController ghostNetController;

	/**
	 * Alle Netze aus der DB laden
	 */

	public List<GhostNet> findAll() {
		EntityManager em = emf.createEntityManager();
		List<GhostNet> ghostNetList = em.createQuery("SELECT g FROM GhostNet g").getResultList();
		em.close();
		return ghostNetList;

	}

	/**
	 * Alle neuen und zur Bergung angekündigten Netze aus der DB laden
	 */

	public List<GhostNet> findAllNotLost() {
		EntityManager em = emf.createEntityManager();
		List<GhostNet> ghostNetListNotLost = em
				.createQuery("SELECT g FROM GhostNet g WHERE state.stateId = 1 OR state.stateId = 2").getResultList();
		em.close();
		return ghostNetListNotLost;
	}

	/**
	 * Alle neue gemeldeten Netze aus der DB laden
	 */

	public List<GhostNet> findAllNew() {
		EntityManager em = emf.createEntityManager();
		List<GhostNet> ghostNetListNew = em.createQuery("SELECT g FROM GhostNet g WHERE state.stateId = 1")
				.getResultList();
		em.close();
		return ghostNetListNew;

	}

	/**
	 * Alle Netze mit angekündigter Bergung aus der DB laden
	 */

	public List<GhostNet> findAllRescue() {
		EntityManager em = emf.createEntityManager();
		List<GhostNet> ghostNetListRescue = em.createQuery("SELECT g FROM GhostNet g WHERE state.stateId = 2")
				.getResultList();

		em.close();
		return ghostNetListRescue;

	}

	/**
	 * GhostNet in der DB mergen
	 */

	public void mergeGhostNet(GhostNet ghostNet) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.merge(ghostNet);
		transaction.commit();
		em.close();
	}

	/**
	 * Alle Status aus der DB laden
	 */

	public List<GhostNetState> findAllStates() {
		EntityManager em = emf.createEntityManager();
		List<GhostNetState> states = em.createQuery("SELECT s FROM GhostNetState s").getResultList();
		em.close();
		return states;

	}

	/**
	 * Status in die DB schreiben
	 */

	public void mergeStates(List<GhostNetState> states) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		for (GhostNetState s : states) {
			em.merge(s);
		}
		transaction.commit();
		em.close();
	}

}
