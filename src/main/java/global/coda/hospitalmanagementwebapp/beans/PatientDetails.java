package global.coda.hospitalmanagementwebapp.beans;


public class PatientDetails extends PersonDetails {

	private long patientPhone;
	private String patientBloodGroup;

	public long getPatientPhone() {
		return patientPhone;
	}

	public void setPatientPhone(long patientPhone) {
		this.patientPhone = patientPhone;
	}

	public String getPatientBloodGroup() {
		return patientBloodGroup;
	}

	public void setPatientBloodGroup(String patientBloodGroup) {
		this.patientBloodGroup = patientBloodGroup;
	}

	@Override
	public String toString() {
		return "PatientDetails [patientPhone=" + patientPhone + ", patientBloodGroup=" + patientBloodGroup + "]";
	}

}
