import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
public class UserValidator {

	@Inject
	LoginController loginController;

	@Inject
	Sea sea;

	private boolean existingUserName = false;
	private boolean passwordOk = false;
	private boolean numberOk = false;

	User user;
	User newUser;

	/**
	 * User Name für neuen User validieren
	 */

	public void validateUserName(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String newUserName = (String) value;

		if (checkExistingUserName(newUserName))

			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Benutzername bereits vorhanden! Bitte wählen Sei einen neuen.", ""));

		if ((newUserName.length() < 3))

			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Benutzername zu kurz! Bitte mindestens 3 Zeichen eingeben.", ""));

	}

	/**
	 * Passwort für neuen User validieren
	 */

	public void validatePassword(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String newPassword = (String) value;

		if (newPassword.length() < 3)

			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Passwort zu kurz! Bitte mindestens 3 Zeichen eingeben.", ""));
	}

	/**
	 * Telefon für neuen User validieren
	 */

	public void validatePhone(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String newPhone = (String) value;

		if (newPhone.length() < 5)

			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Telefonnummer zu kurz! Bitte mindestens 5 Zeichen eingeben. Format: +123456 / 0123456", ""));

		if (newPhone.length() > 15)

			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Telefonnummer zu lang! Bitte maximal 15 Stellen eingaben. Format: +123456 / 0123456", ""));

		if ((!newPhone.startsWith("+") && !newPhone.startsWith("0")))
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Die eingegebene Telefonnummer ist ungültig. Bitte beachten Sie das Format: +123456 / 0123456",
					""));

		if (checkIfPhoneHasNumbers(newPhone) == false)

			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Die eingegebene Telefonnummer ist ungültig. Bitte beachten Sie das Format: +123456 / 0123456",
					""));
	}

	/**
	 * check ob Username bereits vorhanden
	 */

	public boolean checkExistingUserName(String newUserName) {

		int i = 0;
		existingUserName = false;

		while (i < sea.getUserCount() && existingUserName == false) {
			if (sea.getUserList().get(i).getUserName().equals(newUserName)) {
				existingUserName = true;
				i++;
			} else {
				existingUserName = false;
				i++;
			}
		}

		return existingUserName;
	}

	/**
	 * Check bei Telefon ob ersten Zeichen 0 oder + und alle weiteren Zeichen Zahlen
	 * sind
	 */

	public boolean checkIfPhoneHasNumbers(String newPhone) {

		int i = 0;
		String subNumber = newPhone.substring(1);

		numberOk = true;

		while (i < (subNumber.length()) && numberOk == true) {
			if ((String.valueOf(subNumber.charAt(i)).equals("0")) || (String.valueOf(subNumber.charAt(i)).equals("1"))
					|| (String.valueOf(subNumber.charAt(i)).equals("2"))
					|| (String.valueOf(subNumber.charAt(i)).equals("3"))
					|| (String.valueOf(subNumber.charAt(i)).equals("4"))
					|| (String.valueOf(subNumber.charAt(i)).equals("5"))
					|| (String.valueOf(subNumber.charAt(i)).equals("6"))
					|| (String.valueOf(subNumber.charAt(i)).equals("7"))
					|| (String.valueOf(subNumber.charAt(i)).equals("8"))
					|| (String.valueOf(subNumber.charAt(i)).equals("9"))) {
				numberOk = true;
				i++;
			} else {
				numberOk = false;
				i++;
			}
		}

		return numberOk;

	}

}
