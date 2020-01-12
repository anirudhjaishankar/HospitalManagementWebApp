package global.coda.hospitalmanagementwebapp.doa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.exceptions.InconsistentDataException;
import global.coda.hospitalmanagementwebapp.exceptions.NoRecordsFoundException;
import global.coda.hospitalmanagementwebapp.beans.PatientDetails;

public interface PatientDAO {
    int createRecord(PatientDetails pateintObject) throws DatabaseConnectionException;

    PatientDetails readRecord(int pateintId) throws DatabaseConnectionException, SQLException, InconsistentDataException, NoRecordsFoundException;

    List<PatientDetails> readAllRecords() throws DatabaseConnectionException;

    boolean deleteRecord(int patientId) throws DatabaseConnectionException;

    boolean checkDatabaseConnection(Connection SqlConnection) throws DatabaseConnectionException;

    boolean updateRecord(PatientDetails newPatient, int patientId) throws DatabaseConnectionException;
}
