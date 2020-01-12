package global.coda.hospitalmanagementwebapp.delegate;

import static global.coda.hospitalmanagementwebapp.constants.PatientDelegateConstants.PATIENT_DELEGATE_CLASSNAME;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import global.coda.hospitalmanagementwebapp.beans.PatientDetails;
import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.exceptions.InconsistentDataException;
import global.coda.hospitalmanagementwebapp.exceptions.NoRecordsFoundException;
import global.coda.hospitalmanagementwebapp.helpers.PatientHelper;

public class PatientDelegate {
    private static PatientHelper patientHelper = new PatientHelper();
    private Logger LOGGER = LogManager.getLogger(PATIENT_DELEGATE_CLASSNAME);

    public PatientDetails readPatient (int patientId) throws DatabaseConnectionException ,SQLException, InconsistentDataException, NoRecordsFoundException{
            
        PatientDetails patientRecord = null;
            
            try {
                patientRecord = patientHelper.readPatient(patientId);
            } catch(DatabaseConnectionException dbError) {
                LOGGER.error(dbError);
                throw dbError;
            } catch (InconsistentDataException dataError) {
                LOGGER.error(dataError);
                throw dataError;
            } catch(NoRecordsFoundException dataNotFoundError) {
                LOGGER.error(dataNotFoundError);
                throw dataNotFoundError;
            } catch (SQLException sqlError) {
                LOGGER.error(sqlError);
                throw sqlError;
            }
            return patientRecord;
    }
}
