import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class GhostNetState implements Serializable {
	
	@Id
	private int stateId;

	private String stateName;

	public GhostNetState() {
	}

	public GhostNetState(int stateId, String stateName) {
		this.stateId = stateId;
		this.stateName = stateName;
	}

	public int getStateId() {
		return stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
