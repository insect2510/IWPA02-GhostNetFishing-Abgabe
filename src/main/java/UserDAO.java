import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

@Named
@ApplicationScoped
public class UserDAO implements Serializable {	
	
	private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostNetUnit");
	
	@Inject
	LoginController loginController;

	@Inject
	Sea sea;
    
	/**
	 * Alle User aus der DB laden
	 */

	
	public List<User> findAllUsers()
    {
    	EntityManager em = emf.createEntityManager();
    	List<User> userList = em.createQuery("SELECT a FROM User a").getResultList();
    	em.close();	
        return userList;
    }

	/**
	 * neuen User in der DB speichern
	 */

    public void mergeUser(User User)
    {
    	  	EntityManager em = emf.createEntityManager();
    	   	EntityTransaction transaction = em.getTransaction();
        	transaction.begin();
        	em.merge(User);
        	transaction.commit();
        	em.close();		      
    }
    
}
