package coda.global.hospitalmanagementwebapp.daoimplimentation;

import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.*;
import static global.coda.hospitalmanagementwebapp.constants.DAOConstants.*;
import static global.coda.hospitalmanagementwebapp.constants.PatientDAOConstants.*;
import static global.coda.hospitalmanagementwebapp.doa.JDBCController.createConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import global.coda.hospitalmanagementwebapp.beans.PersonDetails;
import coda.global.hospitalmanagementwebapp.daoimplimentation.UserDAOImplementation;
import global.coda.hospitalmanagementwebapp.beans.PatientDetails;
import global.coda.hospitalmanagementwebapp.doa.PatientDAO;
import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.exceptions.InconsistentDataException;
import global.coda.hospitalmanagementwebapp.exceptions.NoRecordsFoundException;

public class PatientDAOImplementation implements PatientDAO {

    private Logger LOGGER = LogManager.getLogger(PATIENTDAOIMPLEMENTATION_CLASSNAME);
    private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);
    private static UserDAOImplementation userDbDetails = new UserDAOImplementation();

    @Override
    public int createRecord(PatientDetails pateintObject) throws DatabaseConnectionException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public PatientDetails readRecord(int patientId) throws DatabaseConnectionException, SQLException, InconsistentDataException, NoRecordsFoundException {
        PatientDetails patient = new PatientDetails();
        Connection mySqlConnection = createConnection(MAIN_DB);
        PreparedStatement read = null;
        if (mySqlConnection == null) {
            LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
            return null;
        }
        try {
            read = mySqlConnection.prepareStatement(PATIENT_READ_QUERY + patientId);
            ResultSet sqlResult = read.executeQuery();
            if (sqlResult.next() == false) {
                LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3016E.toString()));
                throw new NoRecordsFoundException();
            }
            patient.setPatientPhone(Long.parseLong(sqlResult.getString("phone")));
            patient.setPatientBloodGroup(sqlResult.getString("blood_group"));

            PersonDetails userRecord = userDbDetails.readRecord(patientId);
            if (userRecord == null) {
                LOGGER.error(MessageFormat.format(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3019E.toString()),
                        tableNames.t_user.toString()));
                read.close();
                sqlResult.close();
                throw new InconsistentDataException();
            }

            patient.setName(userRecord.getName());
            patient.setUsername(userRecord.getUsername());
            patient.setPassword(userRecord.getPassword());
            patient.setAge(userRecord.getAge());
            patient.setGender(userRecord.getGender());
            patient.setAddress(userRecord.getAddress());
            LOGGER.info(userRecord);
            read.close();
            sqlResult.close();
            LOGGER.traceExit(patient.toString());
            return patient;

        } catch (SQLException sqlError) {
            LOGGER.error(sqlError.getMessage());
            throw sqlError;
        } catch (DatabaseConnectionException dbError) {
            LOGGER.error(dbError);
            throw dbError;
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
