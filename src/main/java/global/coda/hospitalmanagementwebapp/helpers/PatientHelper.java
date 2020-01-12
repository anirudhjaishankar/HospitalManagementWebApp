package global.coda.hospitalmanagementwebapp.helpers;

import static global.coda.hospitalmanagementwebapp.constants.PatientHelperConstants.PATIENT_HELPER_CLASSNAME;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import coda.global.hospitalmanagementwebapp.daoimplimentation.PatientDAOImplementation;
import global.coda.hospitalmanagementwebapp.beans.PatientDetails;
import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.exceptions.InconsistentDataException;
import global.coda.hospitalmanagementwebapp.exceptions.NoRecordsFoundException;

public class PatientHelper {

    public static PatientDAOImplementation patientsDAO = new PatientDAOImplementation();
    private Logger LOGGER = LogManager.getLogger(PATIENT_HELPER_CLASSNAME);

    public PatientDetails readPatient(int patientId)
            throws SQLException, DatabaseConnectionException, InconsistentDataException, NoRecordsFoundException {

        PatientDetails patientRecord = null;
        try {
            patientRecord = patientsDAO.readRecord(patientId);
        } catch (SQLException sqlError) {
            LOGGER.error(sqlError);
            throw sqlError;
        } catch (DatabaseConnectionException dbError) {
            LOGGER.error(dbError);
            throw dbError;
        } catch (InconsistentDataException dataError) {
            LOGGER.error(dataError);
            throw dataError;
        } catch(NoRecordsFoundException dataNotFoundError) {
            LOGGER.error(dataNotFoundError);
            throw dataNotFoundError;
        }

        return patientRecord;
    }
}
