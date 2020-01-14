package global.coda.hospitalmanagementwebapp.doa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.beans.DoctorDetails;

public interface DoctorDAO {
	int createRecord(DoctorDetails doctorObject, int userId) throws DatabaseConnectionException, SQLException;

	DoctorDetails readRecord(int doctorId) throws DatabaseConnectionException, SQLException;

	List<DoctorDetails> readAllRecords() throws DatabaseConnectionException;

	boolean deleteRecord(int doctorId) throws DatabaseConnectionException, SQLException;

	boolean checkDatabaseConnection(Connection SqlConnection) throws DatabaseConnectionException;

	boolean updateRecord(DoctorDetails newDoctorObject, int user_id) throws DatabaseConnectionException, SQLException;

	int getUserId(int doctorId) throws DatabaseConnectionException, SQLException;

}
