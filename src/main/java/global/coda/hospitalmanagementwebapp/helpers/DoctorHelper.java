package global.coda.hospitalmanagementwebapp.helpers;

import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.MESSAGES_BUNDLE;
import static global.coda.hospitalmanagementwebapp.constants.PatientHelperConstants.PATIENT_HELPER_CLASSNAME;
import static global.coda.hospitalmanagementwebapp.constants.PatientHelperConstants.PATIENT_ROLE_ID;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import coda.global.hospitalmanagementwebapp.daoimplimentation.AddressDAOImplementation;
import coda.global.hospitalmanagementwebapp.daoimplimentation.DoctorDAOImplementation;
import coda.global.hospitalmanagementwebapp.daoimplimentation.PatientDAOImplementation;
import coda.global.hospitalmanagementwebapp.daoimplimentation.UserDAOImplementation;
import global.coda.hospitalmanagementwebapp.beans.DoctorDetails;
import global.coda.hospitalmanagementwebapp.beans.PatientDetails;
import global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.errorCodes;
import global.coda.hospitalmanagementwebapp.constants.DAOConstants.tableNames;
import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.exceptions.NoRecordsFoundException;

public class DoctorHelper {
	public static DoctorDAOImplementation doctorsDAO = new DoctorDAOImplementation();
	public static PatientDAOImplementation patientDAO = new PatientDAOImplementation();
	public static UserDAOImplementation userDAO = new UserDAOImplementation();
	public static AddressDAOImplementation addressDAO = new AddressDAOImplementation();
	private Logger LOGGER = LogManager.getLogger(PATIENT_HELPER_CLASSNAME);
	private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);

	public int createDoctor(DoctorDetails DoctorDetails) throws SQLException, DatabaseConnectionException {

		int addressId = addressDAO.createRecord(DoctorDetails.getAddress());
		if (addressId != -1) {
			int userId = userDAO.createRecord(DoctorDetails, addressId, PATIENT_ROLE_ID);
			LOGGER.info(userId);
			if (userId != -1) {
				int doctorId = doctorsDAO.createRecord(DoctorDetails, userId);
				LOGGER.info(doctorId);
				if (doctorId != -1) {
					return doctorId;
				} else {
					LOGGER.error(MessageFormat.format(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3016E.toString()),
							tableNames.t_patient.toString()));
					return -1;
				}
			} else {
				LOGGER.error(MessageFormat.format(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3016E.toString()),
						tableNames.t_user.toString()));
				return -1;
			}
		} else {
			LOGGER.error(MessageFormat.format(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3016E.toString()),
					tableNames.t_address.toString()));
			return -1;
		}

	}

	public DoctorDetails readDoctor(int doctorId)
			throws SQLException, DatabaseConnectionException, NoRecordsFoundException {

		DoctorDetails patientRecord = null;
		try {
			patientRecord = doctorsDAO.readRecord(doctorId);
			if (patientRecord == null) {
				LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3016E.toString()));
				throw new NoRecordsFoundException();
			}

		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw sqlError;
		} catch (DatabaseConnectionException dbError) {
			LOGGER.error(dbError);
			throw dbError;
		} catch (NoRecordsFoundException noRecordsFoundError) {
			LOGGER.error(noRecordsFoundError);
			throw noRecordsFoundError;
		} catch (Exception unknownError) {
			LOGGER.error(unknownError);
		}
		return patientRecord;
	}

	public boolean updateDoctor(DoctorDetails DoctorDetails, int doctorId)
			throws SQLException, DatabaseConnectionException {
		LOGGER.traceEntry(DoctorDetails.toString());
		try {
			if (doctorsDAO.updateRecord(DoctorDetails, doctorId)) {
				int foreignUserId = doctorsDAO.getUserId(doctorId);
				if (userDAO.updateRecord(DoctorDetails, foreignUserId)) {
					int foreignKeyAddressId = userDAO.getAddressId(foreignUserId);
					if (addressDAO.updateRecord(DoctorDetails.getAddress(), foreignKeyAddressId)) {
						return true;
					}
				}
			}
		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw sqlError;
		} catch (DatabaseConnectionException dbError) {
			LOGGER.error(dbError);
			throw dbError;
		} catch (Exception unknownError) {
			LOGGER.error(unknownError);
		}
		return false;
	}

	public boolean deleteDoctor(int doctorId) throws SQLException, DatabaseConnectionException {
		LOGGER.traceEntry(Integer.toString(doctorId));
		try {
			if (doctorsDAO.deleteRecord(doctorId)) {
				int foreignUserId = doctorsDAO.getUserId(doctorId);
				if (userDAO.deleteRecord(foreignUserId)) {
					int foreignKeyAddressId = userDAO.getAddressId(foreignUserId);
					if (addressDAO.deleteRecord(foreignKeyAddressId)) {
						return true;
					}
				}
			}
		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw sqlError;
		} catch (DatabaseConnectionException dbError) {
			LOGGER.error(dbError);
			throw dbError;
		} catch (Exception unknownError) {
			LOGGER.error(unknownError);
		}
		return false;
	}
	
	public ArrayList<PatientDetails> getPatientsDoctor(int doctorId) throws SQLException, DatabaseConnectionException, Exception{
		LOGGER.traceEntry(Integer.toString(doctorId));
		ArrayList<Integer> patientIds = new ArrayList<Integer>();
		ArrayList<PatientDetails> patientList = new ArrayList<PatientDetails>();
		try {
			patientIds = doctorsDAO.getPatientsDoctorId(doctorId);
			if(patientIds.size() == 0) {
				return null;
			}
			
			for(int index = 0; index < patientIds.size(); index++) {
				patientList.add(patientDAO.readRecord(patientIds.get(index)));
			}
			return patientList;
		}catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw sqlError;
		} catch (DatabaseConnectionException dbError) {
			LOGGER.error(dbError);
			throw dbError;
		} catch (Exception unknownError) {
			LOGGER.error(unknownError);
			throw unknownError;
		}
	}
	
	public ArrayList<PatientDetails> getAllPatientsAllDoctors() throws SQLException, DatabaseConnectionException, Exception{
		LOGGER.traceEntry();
		ArrayList<Integer> patientIds = new ArrayList<Integer>();
		ArrayList<PatientDetails> patientList = new ArrayList<PatientDetails>();
		try {
			patientIds = doctorsDAO.getAllPatientIds();
			if(patientIds.size() == 0) {
				return null;
			}
			
			for(int index = 0; index < patientIds.size(); index++) {
				patientList.add(patientDAO.readRecord(patientIds.get(index)));
			}
			return patientList;
		}catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw sqlError;
		} catch (DatabaseConnectionException dbError) {
			LOGGER.error(dbError);
			throw dbError;
		} catch (Exception unknownError) {
			LOGGER.error(unknownError);
			throw unknownError;
		}
	}

}
 