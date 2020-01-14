package global.coda.hospitalmanagementwebapp.constants;

public class DoctorDAOConstants {
	public static final String DOCTORDAOIMPLEMENTATION_CLASSNAME = "DoctorDAOImplementation";
	public static final String DOCTOR_READ_QUERY = "SELECT phone, qualification, name, age, gender, username, password, flatname, flatnumber, street, area, city, state, pincode FROM t_doctor d, t_address a, t_user u where d.fk_user_id = u.pk_user_id AND u.fk_address_id = a.pk_address_id AND d.is_deleted = false AND d.pk_doctor_id = ";
	public static final String DOCTOR_INSERT_QUERY = "INSERT INTO t_doctor(phone, qualification, fk_user_id) VALUES (?, ?, ?)";
	public static final String DOCTOR_USERID_READ_QUERY = "SELECT fk_user_id FROM t_doctor WHERE pk_doctor_id = ";
	public static final String DOCTOR_UPDATE_QUERY = "UPDATE t_doctor SET phone = ?, qualification = ? WHERE pk_DOCTOR_id = ";
	public static final String DOCTOR_DELETE_QUERY = "UPDATE t_doctor SET is_deleted = 1 WHERE pk_doctor_id = ";
	public static final String DOCTOR_ID_READALL_QUERY = "SELECT pk_doctor_id from t_doctor";
	public static final String DOCTOR_PATIENT_ID = "SELECT fk_patient_id from t_patient_doctor_mapping WHERE fk_doctor_id = "; 
	public static final String DOCTOR_PATIENT_ALL_READ = "SELECT fk_patient_id FROM t_patient_doctor_mapping";
}
