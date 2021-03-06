package global.coda.hospitalmanagementwebapp.doa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.beans.PatientDetails;

public interface PatientDAO {
    int createRecord(PatientDetails pateintObject, int foreignKeyUserId)
            throws DatabaseConnectionException, SQLException;

    PatientDetails readRecord(int pateintId) throws DatabaseConnectionException, SQLException;

    List<PatientDetails> readAllPatients() throws DatabaseConnectionException;

    boolean deleteRecord(int patientId) throws DatabaseConnectionException, SQLException;

    boolean checkDatabaseConnection(Connection SqlConnection) throws DatabaseConnectionException;

    boolean updateRecord(PatientDetails newPatient, int patientId) throws DatabaseConnectionException, SQLException;

    public int getUserId(int PatientId) throws DatabaseConnectionException, SQLException;
}
