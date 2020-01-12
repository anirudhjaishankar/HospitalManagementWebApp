package global.coda.hospitalmanagementwebapp.constants;

public abstract class PatientDAOConstants {
    public static final String PATIENTDAOIMPLEMENTATION_CLASSNAME = "PatientDAOImplementation";
    public static final String PATIENT_READ_QUERY = "SELECT phone, blood_group, fk_user_id FROM t_patient WHERE pk_patient_id =";
}
