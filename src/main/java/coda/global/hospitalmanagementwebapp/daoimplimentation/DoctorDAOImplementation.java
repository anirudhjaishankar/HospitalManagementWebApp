package coda.global.hospitalmanagementwebapp.daoimplimentation;

import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.MESSAGES_BUNDLE;
import static global.coda.hospitalmanagementwebapp.constants.DAOConstants.MAIN_DB;
import static global.coda.hospitalmanagementwebapp.doa.JDBCController.createConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import global.coda.hospitalmanagementwebapp.beans.Address;
import global.coda.hospitalmanagementwebapp.beans.DoctorDetails;
import global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.errorCodes;
import global.coda.hospitalmanagementwebapp.constants.DAOConstants.tableNames;
import global.coda.hospitalmanagementwebapp.doa.DoctorDAO;
import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import static global.coda.hospitalmanagementwebapp.constants.DoctorDAOConstants.*;

public class DoctorDAOImplementation implements DoctorDAO {

	private Logger LOGGER = LogManager.getLogger(DOCTORDAOIMPLEMENTATION_CLASSNAME);
	private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);

	@Override
	public int createRecord(DoctorDetails doctorObject, int userId) throws DatabaseConnectionException, SQLException {
		Connection mySqlConnection = createConnection(MAIN_DB);
		PreparedStatement insert = null;
		if (mySqlConnection == null) {
			LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
			throw new DatabaseConnectionException();
		}
		try {
			insert = mySqlConnection.prepareStatement(DOCTOR_INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
			insert.setString(1, Long.toString(doctorObject.getDoctorPhone()));
			insert.setString(2, doctorObject.getDoctorSpeciality());
			insert.setString(3, Integer.toString(userId));
			int rowsAffectedDoctor = insert.executeUpdate();
			if (rowsAffectedDoctor == 0) {
				LOGGER.error(MessageFormat.format(errorCodes.HOS3015E.toString(), tableNames.t_doctor.toString()));
				return -1;
			}
			ResultSet doctorResultSet = insert.getGeneratedKeys();
			int inserteddoctorId = -1;
			if (doctorResultSet.next()) {
				inserteddoctorId = doctorResultSet.getInt(1);
			}
			return inserteddoctorId;

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
	public DoctorDetails readRecord(int doctorId) throws DatabaseConnectionException, SQLException {
		LOGGER.traceEntry(Integer.toString(doctorId));
		DoctorDetails doctor = new DoctorDetails();
		Connection mySqlConnection = createConnection(MAIN_DB);
		PreparedStatement read = null;
		if (mySqlConnection == null) {
			LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
			throw new DatabaseConnectionException();
		}
		try {
			read = mySqlConnection.prepareStatement(DOCTOR_READ_QUERY + doctorId);
			ResultSet sqlResult = read.executeQuery();
			if (sqlResult.next() == false) {
				return null;
			}
			doctor.setDoctorPhone(Long.parseLong(sqlResult.getString("phone")));
			doctor.setDoctorSpeciality(sqlResult.getString("qualification"));

			doctor.setName(sqlResult.getString("name"));
			doctor.setAge(Integer.parseInt(sqlResult.getString("age")));
			doctor.setGender(sqlResult.getString("gender"));
			doctor.setUsername(sqlResult.getString("username"));
			doctor.setPassword(sqlResult.getString("password"));
			Address newAddress = new Address();
			newAddress.setFlatName(sqlResult.getString("flatname"));
			newAddress.setFlatNumber(sqlResult.getString("flatnumber"));
			newAddress.setAreaName(sqlResult.getString("area"));
			newAddress.setCityName(sqlResult.getString("city"));
			newAddress.setStateName(sqlResult.getString("state"));
			newAddress.setStreetName(sqlResult.getString("street"));
            newAddress.setPincode(sqlResult.getLong("pincode"));
			doctor.setAddress(newAddress);

			read.close();
			sqlResult.close();
			LOGGER.traceExit(doctor.toString());
			return doctor;

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
	public List<DoctorDetails> readAllRecords() throws DatabaseConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRecord(int doctorId) throws DatabaseConnectionException, SQLException {
		Connection mySqlConnection = createConnection(MAIN_DB);
		PreparedStatement delete = null;
		if (mySqlConnection == null) {
			LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
			throw new DatabaseConnectionException();
		}
		try {
			delete = mySqlConnection.prepareStatement(DOCTOR_DELETE_QUERY + doctorId);
			int rowsAffectedDoctors = delete.executeUpdate();
			if (rowsAffectedDoctors == 0) {
				LOGGER.error(MessageFormat.format(errorCodes.HOS3015E.toString(), tableNames.t_doctor.toString()));
				return false;
			}
			return true;
		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw sqlError;
		}
	}

	@Override
	public boolean checkDatabaseConnection(Connection SqlConnection) throws DatabaseConnectionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRecord(DoctorDetails newDoctor, int doctorId)
			throws DatabaseConnectionException, SQLException {
		Connection mySqlConnection = createConnection(MAIN_DB);
		PreparedStatement update = null;
		if (mySqlConnection == null) {
			LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
			throw new DatabaseConnectionException();
		}
		try {
			update = mySqlConnection.prepareStatement(DOCTOR_UPDATE_QUERY + doctorId);
			update.setString(1, Long.toString(newDoctor.getDoctorPhone()));
			update.setString(2, newDoctor.getDoctorSpeciality());

			int rowsAffectedDoctors = update.executeUpdate();
			if (rowsAffectedDoctors == 0) {
				LOGGER.error(MessageFormat.format(errorCodes.HOS3015E.toString(), tableNames.t_doctor.toString()));
				return false;
			}
			return true;
		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw sqlError;
		}
	}

	@Override
	public int getUserId(int doctorId) throws DatabaseConnectionException, SQLException {
		Connection mySqlConnection = createConnection(MAIN_DB);
		PreparedStatement read = null;
		if (mySqlConnection == null) {
			LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
			throw new DatabaseConnectionException();
		}
		try {
			read = mySqlConnection.prepareStatement(DOCTOR_USERID_READ_QUERY + doctorId);
			ResultSet userIdResult = read.executeQuery();
			if (userIdResult.next()) {
				return userIdResult.getInt("fk_user_id");
			} else {
				return -1;
			}
		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw sqlError;
		}

	}

	public ArrayList<Integer> getAllDoctorId() throws DatabaseConnectionException, SQLException {
		LOGGER.traceEntry();
		Connection mySqlConnection = createConnection(MAIN_DB);
		PreparedStatement read = null;
		if (mySqlConnection == null) {
			LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
			throw new DatabaseConnectionException();
		}
		try {
			ArrayList<Integer> doctorIds = new ArrayList<Integer>();
			read = mySqlConnection.prepareStatement(DOCTOR_ID_READALL_QUERY);
			ResultSet userIdResult = read.executeQuery();
			while (userIdResult.next()) {
				doctorIds.add(userIdResult.getInt("pk_doctor_id"));
			}
			return doctorIds;
		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw sqlError;
		}

	}
	
	public ArrayList<Integer> getPatientsDoctorId(int doctorId)throws DatabaseConnectionException, SQLException{
		LOGGER.traceEntry();
		Connection mySqlConnection = createConnection(MAIN_DB);
		PreparedStatement read = null;
		if (mySqlConnection == null) {
			LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
			throw new DatabaseConnectionException();
		}
		try {
			ArrayList<Integer> patientIds = new ArrayList<Integer>();
			read = mySqlConnection.prepareStatement(DOCTOR_PATIENT_ID + doctorId);
			ResultSet userIdResult = read.executeQuery();
			while (userIdResult.next()) {
				patientIds.add(userIdResult.getInt("fk_patient_id"));
			}
			return patientIds;
		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw sqlError;
		}
	}
	
	public ArrayList<Integer> getAllPatientIds()throws DatabaseConnectionException, SQLException{
		LOGGER.traceEntry();
		Connection mySqlConnection = createConnection(MAIN_DB);
		PreparedStatement read = null;
		if (mySqlConnection == null) {
			LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
			throw new DatabaseConnectionException();
		}
		try {
			ArrayList<Integer> patientIds = new ArrayList<Integer>();
			read = mySqlConnection.prepareStatement(DOCTOR_PATIENT_ALL_READ);
			ResultSet userIdResult = read.executeQuery();
			while (userIdResult.next()) {
				patientIds.add(userIdResult.getInt("fk_patient_id"));
			}
			return patientIds;
		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw sqlError;
		}
	}

}
