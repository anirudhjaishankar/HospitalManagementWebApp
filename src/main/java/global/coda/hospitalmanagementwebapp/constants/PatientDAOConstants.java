package global.coda.hospitalmanagementwebapp.constants;

public abstract class PatientDAOConstants {
    public static final String PATIENTDAOIMPLEMENTATION_CLASSNAME = "PatientDAOImplementation";
    public static final String PATIENT_READ_QUERY = "SELECT phone, blood_group, name, age, gender, username, password, flatname, flatnumber, street, area, city, state, pincode FROM t_patient p, t_address a, t_user u where p.fk_user_id = u.pk_user_id AND u.fk_address_id = a.pk_address_id AND p.is_deleted = false AND p.pk_patient_id = ";
    public static final String PATIENT_INSERT_QUERY = "INSERT INTO t_patient(phone, blood_group, fk_user_id) VALUES (?, ?, ?)";
}
