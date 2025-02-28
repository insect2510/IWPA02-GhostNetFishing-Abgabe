import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class GhostNet implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int nr;

	private Double gpsLatitude;

	private Double gpsLongitude;
	
	private Double netLength;

	private Double netWidth;

	@ManyToOne
	@JoinColumn
	GhostNetState state;

	@ManyToOne
	@JoinColumn
	User reportingUser;

	public GhostNet() {

	}

	public GhostNet(Double gpsLatitude, Double gpsLongitude, Double netLength, Double netWidth, GhostNetState state,
			User reportingUser) {
		this.gpsLatitude = gpsLatitude;
		this.gpsLongitude = gpsLongitude;
		this.netLength = netLength;
		this.netWidth = netWidth;
		this.state = state;
		this.reportingUser = reportingUser;
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public Double getGpsLatitude() {
		return gpsLatitude;
	}

	public void setGpsLatitude(Double gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}

	public Double getGpsLongitude() {
		return gpsLongitude;
	}

	public void setGpsLongitude(Double gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}

	public Double getNetLength() {
		return netLength;
	}

	public void setNetLength(Double netLength) {
		this.netLength = netLength;
	}

	public Double getNetWidth() {
		return netWidth;
	}

	public void setNetWidth(Double netWidth) {
		this.netWidth = netWidth;
	}

	public GhostNetState getState() {
		return state;
	}

	public void setState(GhostNetState state) {
		this.state = state;
	}

	public User getreportingUser() {
		return reportingUser;
	}

	public void setReportingUser(User reportingUser) {
		this.reportingUser = reportingUser;
	}

}
