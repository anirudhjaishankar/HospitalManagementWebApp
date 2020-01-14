package global.coda.hospitalmanagementwebapp.beans;

public class DoctorDetails extends PersonDetails {

	private Long doctorPhone;
	private String doctorSpeciality;

	public String getDoctorSpeciality() {
		return doctorSpeciality;
	}

	public void setDoctorSpeciality(String doctorSpeciality) {
		this.doctorSpeciality = doctorSpeciality;
	}

	public Long getDoctorPhone() {
		return doctorPhone;
	}

	public void setDoctorPhone(Long doctorPhone) {
		this.doctorPhone = doctorPhone;
	}

	@Override
	public String toString() {
		return "DoctorDetails [id=" + id + "name=" + name + "age=" + age + "gender=" + gender + "doctorSpeciality="
				+ doctorSpeciality + "]";
	}

}