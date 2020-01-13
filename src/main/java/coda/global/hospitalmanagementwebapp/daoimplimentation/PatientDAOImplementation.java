package coda.global.hospitalmanagementwebapp.daoimplimentation;

import static global.coda.hospitalmanagementwebapp.constants.PatientDAOConstants.PATIENT_INSERT_QUERY;
import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.*;
import static global.coda.hospitalmanagementwebapp.constants.DAOConstants.*;
import static global.coda.hospitalmanagementwebapp.constants.PatientDAOConstants.*;
import static global.coda.hospitalmanagementwebapp.doa.JDBCController.createConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.errorCodes;
import global.coda.hospitalmanagementwebapp.constants.DAOConstants.tableNames;
import global.coda.hospitalmanagementwebapp.beans.Address;
import global.coda.hospitalmanagementwebapp.beans.PatientDetails;
import global.coda.hospitalmanagementwebapp.doa.PatientDAO;
import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;

public class PatientDAOImplementation implements PatientDAO {

	private Logger LOGGER = LogManager.getLogger(PATIENTDAOIMPLEMENTATION_CLASSNAME);
	private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);

	@Override
	public int createRecord(PatientDetails patientObject, int foreignKeyUserId)
			throws DatabaseConnectionException, SQLException {

		Connection mySqlConnection = createConnection(MAIN_DB);
		PreparedStatement insert = null;
		if (mySqlConnection == null) {
			LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
			throw new DatabaseConnectionException();
		}
		try {
			insert = mySqlConnection.prepareStatement(PATIENT_INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
			insert.setString(1, Long.toString(patientObject.getPatientPhone()));
			insert.setString(2, patientObject.getPatientBloodGroup());
			insert.setString(3, Integer.toString(foreignKeyUserId));
			int rowsAffectedPatient = insert.executeUpdate();
			if (rowsAffectedPatient == 0) {
				LOGGER.error(MessageFormat.format(errorCodes.HOS3015E.toString(), tableNames.t_patient.toString()));
				return -1;
			}
			ResultSet patientResultSet = insert.getGeneratedKeys();
			int insertedPatientId = -1;
			if(patientResultSet.next()) {
				 insertedPatientId = patientResultSet.getInt(1);
			}
			return insertedPatientId;

		} catch (SQLException sqlError) {
			LOGGER.error(sqlError.getMessage());
			throw sqlError;
		} finally {
			if (insert != null) {
				try {
					insert.close();
				} catch (SQLException sqlError) {
					LOGGER.error(sqlError.getMessage());
				}
			}
		}
	}

	@Override
	public PatientDetails readRecord(int patientId) throws DatabaseConnectionException, SQLException {
		PatientDetails patient = new PatientDetails();
		Connection mySqlConnection = createConnection(MAIN_DB);
		PreparedStatement read = null;
		if (mySqlConnection == null) {
			LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
			throw new DatabaseConnectionException();
		}
		try {
			read = mySqlConnection.prepareStatement(PATIENT_READ_QUERY + patientId);
			ResultSet sqlResult = read.executeQuery();
			if (sqlResult.next() == false) {
				return null;
			}
			patient.setPatientPhone(Long.parseLong(sqlResult.getString("phone")));
			patient.setPatientBloodGroup(sqlResult.getString("blood_group"));

			patient.setName(sqlResult.getString("name"));
			patient.setAge(Integer.parseInt(sqlResult.getString("age")));
			patient.setGender(sqlResult.getString("gender"));
			patient.setUsername(sqlResult.getString("username"));
			patient.setPassword(sqlResult.getString("password"));
			Address newAddress = new Address();
			newAddress.setFlatName(sqlResult.getString("flatname"));
			newAddress.setFlatNumber(sqlResult.getString("flatnumber"));
			newAddress.setAreaName(sqlResult.getString("area"));
			newAddress.setCityName(sqlResult.getString("city"));
			newAddress.setStateName(sqlResult.getString("state"));
			newAddress.setStreetName(sqlResult.getString("street"));
			patient.setAddress(newAddress);

			read.close();
			sqlResult.close();
			LOGGER.traceExit(patient.toString());
			return patient;

		} catch (SQLException sqlError) {
			LOGGER.error(sqlError.getMessage());
			throw sqlError;
		} finally {
			if (read != null) {
				try {
					read.close();
				} catch (SQLException sqlError) {
					LOGGER.error(sqlError.getMessage());
				}
			}
		}
	}

	@Override
	public List<PatientDetails> readAllRecords() throws DatabaseConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRecord(int patientId) throws DatabaseConnectionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkDatabaseConnection(Connection SqlConnection) throws DatabaseConnectionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRecord(PatientDetails newPatient, int patientId) throws DatabaseConnectionException {
		// TODO Auto-generated method stub
		return false;
	}

}
