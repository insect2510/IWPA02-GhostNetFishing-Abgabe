import java.io.Serializable;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;


@Named
@RequestScoped
public class MarkerSelectionView implements Serializable {
	
    @Inject
    Sea sea;
    
    private MapModel<Long> simpleModel;
 
    private Marker<Long> marker;

    @PostConstruct
    public void init() {
        simpleModel = new DefaultMapModel<>();
        
     long l = 1;
        
     for (GhostNet i : sea.getGhostNetListNotLost())
     	{	
             simpleModel.addOverlay(new Marker<>(new LatLng(i.getGpsLatitude(),i.getGpsLongitude()), 
             		"Netz: " + i.getNr() + " Status: " + i.getState(),l));
             l++;
     	}
       
    	}
    
    public MapModel<Long> getSimpleModel() {
        return simpleModel;
    }
    
    public void onMarkerSelect(OverlaySelectEvent<Long> event) {
        marker = (Marker) event.getOverlay();

        		
      FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Marker " + marker.getData() + " Selected", marker.getTitle()));
    }

    
    public Marker<Long> getMarker() {
        return marker;
    }
     
}
