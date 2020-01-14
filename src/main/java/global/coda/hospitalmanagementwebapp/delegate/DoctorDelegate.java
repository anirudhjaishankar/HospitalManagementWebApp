package global.coda.hospitalmanagementwebapp.delegate;

import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.MESSAGES_BUNDLE;
import static global.coda.hospitalmanagementwebapp.constants.PatientDelegateConstants.PATIENT_DELEGATE_CLASSNAME;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import global.coda.hospitalmanagementwebapp.beans.DoctorDetails;
import global.coda.hospitalmanagementwebapp.beans.PatientDetails;
import global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.errorCodes;
import global.coda.hospitalmanagementwebapp.exceptions.BussinessException;
import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.exceptions.NoRecordsFoundException;
import global.coda.hospitalmanagementwebapp.exceptions.SystemException;
import global.coda.hospitalmanagementwebapp.helpers.DoctorHelper;

public class DoctorDelegate {
	private static DoctorHelper doctorHelper = new DoctorHelper();
	private Logger LOGGER = LogManager.getLogger(PATIENT_DELEGATE_CLASSNAME);

	private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);

	public int createDoctor(DoctorDetails doctorDetails) throws BussinessException, SystemException {
		LOGGER.traceEntry(doctorDetails.toString());
		try {
			int doctorId = doctorHelper.createDoctor(doctorDetails);
			if (doctorId == -1) {
				throw new BussinessException();
			}
			return doctorId;
		} catch (DatabaseConnectionException dbError) {
			LOGGER.error(dbError);
			throw new SystemException();
		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw new SystemException();
		} catch (BussinessException bussinessException) {
			LOGGER.error(bussinessException);
			throw new BussinessException();
		} catch (Exception exception) {
			LOGGER.error(exception);
		}
		return -1;
	}

	public DoctorDetails readDoctor(int doctorId) throws BussinessException, SystemException {
		LOGGER.traceEntry();
		DoctorDetails patientRecord = null;

		try {
			patientRecord = doctorHelper.readDoctor(doctorId);
		} catch (DatabaseConnectionException dbError) {
			LOGGER.error(dbError);
			throw new SystemException();
		} catch (NoRecordsFoundException dataNotFoundError) {
			LOGGER.error(dataNotFoundError);
			throw new BussinessException();
		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw new SystemException();
		}
		return patientRecord;
	}

	public boolean updateDoctor(DoctorDetails newDoctor, int doctorId) throws BussinessException, SystemException {
		LOGGER.traceEntry(newDoctor.toString());
		boolean isUpdated = false;

		try {
			isUpdated = doctorHelper.updateDoctor(newDoctor, doctorId);
			if (isUpdated == false) {
				throw new SystemException();
			}
		} catch (DatabaseConnectionException dbError) {
			LOGGER.error(dbError);
			throw new SystemException();
		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw new SystemException();
		}
		return isUpdated;
	}

	public boolean deleteDoctor(int doctorId) throws BussinessException, SystemException {
		LOGGER.traceEntry(Integer.toString(doctorId));
		boolean isDeleted = false;

		try {
			isDeleted = doctorHelper.deleteDoctor(doctorId);
			if (isDeleted == false) {
				throw new SystemException();
			}
		} catch (DatabaseConnectionException dbError) {
			LOGGER.error(dbError);
			throw new SystemException();
		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw new SystemException();
		}
		return isDeleted;
	}

	public ArrayList<PatientDetails> getPatientsDoctor(int doctorId) throws BussinessException, SystemException {
		LOGGER.traceEntry(Integer.toString(doctorId));

		try {
			ArrayList<PatientDetails> patientList = doctorHelper.getPatientsDoctor(doctorId);
			if (patientList == null) {
				LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3016E.toString()));
				throw new BussinessException();
			}
			return patientList;
		} catch (DatabaseConnectionException dbError) {
			LOGGER.error(dbError);
			throw new SystemException();
		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw new SystemException();
		} catch (Exception error) {
			LOGGER.error(error);
			throw new BussinessException();
		}
	}

	public ArrayList<PatientDetails> getAllPatientAllDoctors() throws BussinessException, SystemException {
		LOGGER.traceEntry();

		try {
			ArrayList<PatientDetails> patientList = doctorHelper.getAllPatientsAllDoctors();
			if (patientList == null) {
				LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3016E.toString()));
				throw new BussinessException();
			}
			return patientList;
		} catch (DatabaseConnectionException dbError) {
			LOGGER.error(dbError);
			throw new SystemException();
		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
			throw new SystemException();
		} catch (Exception error) {
			LOGGER.error(error);
			throw new BussinessException();
		}
	}
}
