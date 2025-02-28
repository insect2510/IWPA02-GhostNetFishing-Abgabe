import java.util.InputMismatchException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;

@ApplicationScoped
@Named
public class GhostNetValidator

{

	/**
	 * GPS Breitengrad validieren
	 */

	public void validateLatitude(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Double latitude = (Double) value;

		try {
			if ((latitude > 90.0))
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
						"GPS Koordinate zu groß. Wert zwischen -90.00 und 90.00 eingeben."));
			if ((latitude < -90.0))
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
						"GPS Koordinate zu klein. Wert zwischen -90.00 und 90.00 eingeben."));
		}

		catch (InputMismatchException e) {
			e.printStackTrace();
			throw new ValidatorException(new FacesMessage("Falsches Format! Bitte verwende [0-9] ##.#######"));
		}
	}

	/**
	 * GPS Längengrad validieren
	 */

	public void validateLongitude(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Double longitude = (Double) value;

		try {
			if ((longitude > 180.0))
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
						"GPS Koordinate zu groß. Wert zwischen -180.00 und 180.00 eingeben."));
			if ((longitude < -180.0))
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
						"GPS Koordinate zu klein. Wert zwischen -180.00 und 180.00 eingeben."));
		}

		catch (InputMismatchException e) {
			e.printStackTrace();
			throw new ValidatorException(new FacesMessage("Falsches Format! Bitte verwende [0-9] ##.#######"));
		}
	}

}
