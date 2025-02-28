import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

@Named
@ApplicationScoped
public class Sea implements Serializable

{
	private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostNetUnit");
	private List<GhostNet> ghostNetList = new ArrayList<GhostNet>();
	private List<GhostNet> ghostNetListNew = new ArrayList<GhostNet>();
	private List<GhostNet> ghostNetListRescue = new ArrayList<GhostNet>();
	private List<GhostNet> ghostNetListNotLost = new ArrayList<GhostNet>();
	private List<User> userList = new ArrayList<User>();
	private GhostNetDAO ghostNetDAO;
	private UserDAO userDAO;
	private List<GhostNetState> states = new ArrayList<GhostNetState>();
	int userCount;

	private static Sea instance = new Sea();

	/**
	 * Creates a new instance of Sea
	 */

	public Sea() {

		/**
		 * Prüfen ob Netze in der Datenbank gespeichert sind wenn ja, dann laden
		 */

		ghostNetDAO = new GhostNetDAO();
		EntityManager em = emf.createEntityManager();
		long count = (long) em.createQuery("SELECT COUNT(g) FROM GhostNet g").getSingleResult();
		if (count > 0) {
			ghostNetDAO.findAll();
		}

		else {
			em.close();
		}

		/**
		 * Prüfen ob User in der Datenbank gespeichert sind wenn ja, dann laden
		 */

		userDAO = new UserDAO();
		em = emf.createEntityManager();
		count = (long) em.createQuery("SELECT COUNT(a) FROM User a").getSingleResult();
		if (count > 0) {
			getUserList();
		}

		/**
		 * Prüfen ob Statuse in der Datenbank gespeichert sind wenn ja, dann laden wenn
		 * nicht, dann Statuse in der DB speichern
		 */

		long countStates = (long) em.createQuery("SELECT COUNT(s) FROM GhostNetState s").getSingleResult();
		if (countStates == 0) {
			states.add(new GhostNetState(1, "Gemeldet"));
			states.add(new GhostNetState(2, "Bergung bevorstehend"));
			states.add(new GhostNetState(3, "Geborgen"));
			states.add(new GhostNetState(4, "Verschollen"));
			ghostNetDAO.mergeStates(states);
		} else {
			getStates();
		}
	}

	/**
	 * Liste aller Netze erzeugen
	 */

	public List<GhostNet> getGhostNetList() {
		return ghostNetDAO.findAll();

	}

	/**
	 * Liste aller noch zu bergender Netze erzeugen
	 */

	public List<GhostNet> getGhostNetListNotLost() {
		return ghostNetDAO.findAllNotLost();

	}

	/**
	 * Liste aller neu gemeldeten Netze erzeugen
	 */

	public List<GhostNet> getGhostNetListNew() {
		return ghostNetDAO.findAllNew();

	}

	/**
	 * Liste aller zur Bergung angekündigten Netze erzeugen
	 */

	public List<GhostNet> getGhostNetListRescue() {
		return ghostNetDAO.findAllRescue();

	}

	public Sea getInstance() {
		return instance;
	}

	/**
	 * Liste aller User erzeugen
	 */

	public List<User> getUserList() {
		return userDAO.findAllUsers();
	}

	/**
	 * Anzahl User zählen
	 */

	public int getUserCount() {
		userCount = getUserList().size();
		return userCount;
	}

	/**
	 * Liste aller Status erzeugen
	 */

	public List<GhostNetState> getStates() {
		return ghostNetDAO.findAllStates();
	}

	public int getGhostNetCount() {
		return getGhostNetList().size();
	}

}