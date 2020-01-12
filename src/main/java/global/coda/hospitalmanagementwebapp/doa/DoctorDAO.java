package global.coda.hospitalmanagementwebapp.doa;

import java.sql.Connection;
import java.util.List;

import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.beans.DoctorDetails;

public interface DoctorDAO {
    int createRecord(DoctorDetails doctorObject) throws DatabaseConnectionException;

    DoctorDetails readRecord(int doctorId) throws DatabaseConnectionException;

    List<DoctorDetails> readAllRecords() throws DatabaseConnectionException;

    boolean deleteRecord(int doctorId) throws DatabaseConnectionException;

    boolean checkDatabaseConnection(Connection SqlConnection) throws DatabaseConnectionException;

    boolean updateRecord(DoctorDetails newDoctorObject, int user_id) throws DatabaseConnectionException;
}
