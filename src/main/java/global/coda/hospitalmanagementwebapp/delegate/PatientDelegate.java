package global.coda.hospitalmanagementwebapp.delegate;

import static global.coda.hospitalmanagementwebapp.constants.PatientDelegateConstants.PATIENT_DELEGATE_CLASSNAME;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import global.coda.hospitalmanagementwebapp.beans.PatientDetails;
import global.coda.hospitalmanagementwebapp.exceptions.BussinessException;
import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.exceptions.NoRecordsFoundException;
import global.coda.hospitalmanagementwebapp.exceptions.SystemException;
import global.coda.hospitalmanagementwebapp.helpers.PatientHelper;

public class PatientDelegate {
    private static PatientHelper patientHelper = new PatientHelper();
    private Logger LOGGER = LogManager.getLogger(PATIENT_DELEGATE_CLASSNAME);

    public int createPatient(PatientDetails patientDetails) throws BussinessException, SystemException {
        LOGGER.traceEntry(patientDetails.toString());
        try {
            int patientId = patientHelper.createPatient(patientDetails);
            if (patientId == -1) {
                throw new BussinessException();
            }
            return patientId;
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

    public PatientDetails readPatient(int patientId) throws BussinessException, SystemException {
        LOGGER.traceEntry();
        PatientDetails patientRecord = null;

        try {
            patientRecord = patientHelper.readPatient(patientId);
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

    public boolean updatePatient(PatientDetails newPatient, int patientId) throws BussinessException, SystemException {
        LOGGER.traceEntry(newPatient.toString());
        boolean isUpdated = false;

        try {
            isUpdated = patientHelper.updatePatient(newPatient, patientId);
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
    
    public boolean deleteRecord(int patientId)  throws BussinessException, SystemException{
        LOGGER.traceEntry(Integer.toString(patientId));
        boolean isDeleted = false;
        
        try {
            isDeleted = patientHelper.deletePatient(patientId);
            if(isDeleted == false) {
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
}
