import java.io.Serializable;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;

@Named
@ViewScoped
public class GhostNetController implements Serializable

{
	@Inject
	Sea sea;

	@Inject
	GhostNetDAO ghostNetDao;

	@Inject
	LoginController loginController;

	private GhostNet newGhostNet = null;

	/**
	 * newGhostNet erzeugen
	 */

	public GhostNet getNewGhostNet() {
		if (null == newGhostNet)

		{
			this.newGhostNet = new GhostNet();
		}

		if (loginController.isLoginState()) {
			this.newGhostNet.setReportingUser(loginController.getCurrentUser());
		}
		this.newGhostNet.setState(sea.getStates().get(0));

		return this.newGhostNet;
	}

	/**
	 * GhostNet speichern
	 */

	public String saveNewGhostNet()

	{
		ghostNetDao.mergeGhostNet(newGhostNet);
		sea.getGhostNetList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Die Meldung des Geisternetzes wurde eingetragen.", " "));

		return "list-nets.xhtml";
	}

	/**
	 * Bergung ankündigen speichern
	 */

	public void reportRescue(GhostNet ghostNet) {
		ghostNet.setState(sea.getStates().get(1));
		ghostNet.setReportingUser(loginController.getCurrentUser());
		ghostNetDao.mergeGhostNet(ghostNet);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Die Änderung wurde gespeichert ", "Die Bergung ist auf deinen Namen eingetragen."));

	}

	/**
	 * Geborgenes Netz speichern
	 */

	public void reportRescueDone(GhostNet ghostNet) {
		ghostNet.setState(sea.getStates().get(2));
		ghostNet.setReportingUser(loginController.getCurrentUser());
		ghostNetDao.mergeGhostNet(ghostNet);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Die Änderung wurde gespeichert ", "Das Netz wurde als geborgen erfasst."));
	}

	/**
	 * Bergung abmelden
	 */

	public void removeRescue(GhostNet ghostNet) {
		ghostNet.setState(sea.getStates().get(0));
		ghostNet.setReportingUser(null);
		ghostNetDao.mergeGhostNet(ghostNet);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Die Änderung wurde gespeichert ", "Die Bergung wurde abgemeldet."));
	}

	/**
	 * Verschollen melden
	 */

	public void reportLost(GhostNet ghostNet) {
		ghostNet.setReportingUser(loginController.getCurrentUser());
		ghostNet.setState(sea.getStates().get(3));
		ghostNetDao.mergeGhostNet(ghostNet);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Die Änderung wurde gespeichert ", "Das Netz wurde als verschollen gemeldet."));
	}

}