package global.coda.hospitalmanagementwebapp.helpers;

import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.MESSAGES_BUNDLE;
import static global.coda.hospitalmanagementwebapp.constants.PatientHelperConstants.*;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import coda.global.hospitalmanagementwebapp.daoimplimentation.AddressDAOImplementation;
import coda.global.hospitalmanagementwebapp.daoimplimentation.PatientDAOImplementation;
import coda.global.hospitalmanagementwebapp.daoimplimentation.UserDAOImplementation;
import global.coda.hospitalmanagementwebapp.beans.PatientDetails;
import global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.errorCodes;
import global.coda.hospitalmanagementwebapp.constants.DAOConstants.tableNames;
import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.exceptions.NoRecordsFoundException;

public class PatientHelper {

    public static PatientDAOImplementation patientsDAO = new PatientDAOImplementation();
    public static UserDAOImplementation userDAO = new UserDAOImplementation();
    public static AddressDAOImplementation addressDAO = new AddressDAOImplementation();
    private Logger LOGGER = LogManager.getLogger(PATIENT_HELPER_CLASSNAME);
    private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);

    public int createPatient(PatientDetails patientDetails) throws SQLException, DatabaseConnectionException {

        int addressId = addressDAO.createRecord(patientDetails.getAddress());
        if (addressId != -1) {
            int userId = userDAO.createRecord(patientDetails, addressId, PATIENT_ROLE_ID);
            LOGGER.info(userId);
            if (userId != -1) {
                int patientId = patientsDAO.createRecord(patientDetails, userId);
                LOGGER.info(patientId);
                if (patientId != -1) {
                    return patientId;
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

    public PatientDetails readPatient(int patientId)
            throws SQLException, DatabaseConnectionException, NoRecordsFoundException {

        PatientDetails patientRecord = null;
        try {
            patientRecord = patientsDAO.readRecord(patientId);
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

    public boolean updatePatient(PatientDetails patientDetails, int patientId)
            throws SQLException, DatabaseConnectionException {
        LOGGER.traceEntry(patientDetails.toString());
        try {
            if (patientsDAO.updateRecord(patientDetails, patientId)) {
                int foreignUserId = patientsDAO.getUserId(patientId);
                if (userDAO.updateRecord(patientDetails, foreignUserId)) {
                    int foreignKeyAddressId = userDAO.getAddressId(foreignUserId);
                    if (addressDAO.updateRecord(patientDetails.getAddress(), foreignKeyAddressId)) {
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

    public boolean deletePatient(int patientId) throws SQLException, DatabaseConnectionException {
        LOGGER.traceEntry(Integer.toString(patientId));
        try {
            if(patientsDAO.deleteRecord(patientId)) {
                int foreignUserId = patientsDAO.getUserId(patientId);
                if(userDAO.deleteRecord(foreignUserId)) {
                    int foreignKeyAddressId = userDAO.getAddressId(foreignUserId);
                    if(addressDAO.deleteRecord(foreignKeyAddressId)) {
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
}
