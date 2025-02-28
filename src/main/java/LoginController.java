import java.io.Serializable;
import java.util.List;

import org.primefaces.event.FlowEvent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Persistence;

@Named
@ApplicationScoped
public class LoginController implements Serializable {

	@Inject
	Sea sea;

	@Inject
	User user;

	@Inject
	UserDAO userDAO;

	private boolean loginState = false;
	private boolean skip;
	private User currentUser;
	private User newUser = null;
	@OneToMany
	private List<User> userList;
	private static LoginController instance = new LoginController();

	String userName;
	String password;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getNewUser() {
		if (null == newUser) {
			this.newUser = new User();
		}
		return this.newUser;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public boolean isLoginState() {
		return loginState;
	}

	public void setLoginState(boolean loginState) {
		this.loginState = loginState;
	}

	/**
	 * Eingabe Username Login validieren
	 */

	public void postValidateUserName(ComponentSystemEvent ev) throws AbortProcessingException {
		UIInput temp = (UIInput) ev.getComponent();
		this.userName = (String) temp.getValue();

	}

	/**
	 * Eingabe User Login validieren
	 */

	public User validateLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		for (User u : getUserList()) {
			User temp = new User(this.userName, (String) value, (String) value, (String) value, (String) value);
			if (u.equals(temp))

			{
				this.currentUser = u;
				return currentUser;
			}
		}
		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Falsch!", ""));
	}

	/**
	 * nach Login zur Startseite navigieren
	 */

	public String login() {

		setLoginState(true);
		return "index.xhtml";
	}

	/**
	 * nach Logout zur Startseite navigieren
	 */

	public String logout() {

		setLoginState(false);
		this.currentUser = null;
		return "logout.xhtml";
	}

	public String addUser() {
		return "add-user.xhtml";
	}

	/**
	 * User speichern
	 */

	public String saveUser() {
		userDAO.mergeUser(newUser);
		showPositiveRegistration();
		sea.getUserList();
		return "login.xhtml";
	}

	/**
	 * Rückmeldung bei erfolgreicher Registrierung
	 */

	public void showPositiveRegistration() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Ihre Registrierung war erfolgreich. Sie können sich jetzt anmelden.", ""));
	}

	/**
	 * User List erzeugen
	 */

	public List<User> getUserList() {
		return sea.getUserList();
	}

	public LoginController getInstance() {
		return instance;
	}

	/**
	 * Umschalten Tab im Wizzard
	 */

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	/**
	 * Wizard Prozess
	 */

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

}
