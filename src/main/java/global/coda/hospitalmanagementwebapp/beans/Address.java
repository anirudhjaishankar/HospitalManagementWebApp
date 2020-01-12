package global.coda.hospitalmanagementwebapp.beans;

public class Address {

	private String flatNumber;
	private String flatName;
	private String streetName;
	private String areaName;
	private String cityName;
	private String stateName;
	private Long pincode;

	public String getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(String flatNumber) {
		this.flatNumber = flatNumber;
	}

	public String getFlatName() {
		return flatName;
	}

	public void setFlatName(String flatName) {
		this.flatName = flatName;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		return "PatientAddress [flatNumber=" + flatNumber + ", flatName=" + flatName + ", streetName=" + streetName
				+ ", areaName=" + areaName + ", cityName=" + cityName + ", stateName=" + stateName + ", pincode="
				+ pincode + "]";
	}

}
