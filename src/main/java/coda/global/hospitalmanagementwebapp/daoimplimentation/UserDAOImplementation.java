package coda.global.hospitalmanagementwebapp.daoimplimentation;

import static global.coda.hospitalmanagementwebapp.constants.DAOConstants.*;
import static global.coda.hospitalmanagementwebapp.constants.PatientDAOConstants.PATIENTDAOIMPLEMENTATION_CLASSNAME;
import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.MESSAGES_BUNDLE;
import static global.coda.hospitalmanagementwebapp.constants.UserDAOConstants.USER_READ_QUERY;
import static global.coda.hospitalmanagementwebapp.doa.JDBCController.createConnection;
import static global.coda.hospitalmanagementwebapp.constants.DAOConstants.MAIN_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.errorCodes;
import global.coda.hospitalmanagementwebapp.beans.Address;
import global.coda.hospitalmanagementwebapp.beans.PersonDetails;
import global.coda.hospitalmanagementwebapp.doa.UserDAO;
import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;

public class UserDAOImplementation implements UserDAO{
    
    private Logger LOGGER = LogManager.getLogger(PATIENTDAOIMPLEMENTATION_CLASSNAME);
    private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);
    private AddressDAOImplementation addressDAO = new AddressDAOImplementation();

    @Override
    public int createRecord(PersonDetails personObject) throws DatabaseConnectionException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public PersonDetails readRecord(int userId) throws DatabaseConnectionException {
        Connection mySqlConnection = createConnection(MAIN_DB);
        PreparedStatement read = null;
        PersonDetails userRecord = new PersonDetails();
        if (mySqlConnection == null) {
            throw new DatabaseConnectionException(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
        }
        try {
            LOGGER.debug(userId);
            read = mySqlConnection.prepareStatement(USER_READ_QUERY + userId);
            ResultSet userResultSet = read.executeQuery();
            if(userResultSet.next() == false) {
                LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3016E.toString()));
                return null;
            }
            userRecord.setName(userResultSet.getString("name"));
            userRecord.setAge(userResultSet.getInt("age"));
            userRecord.setGender(userResultSet.getString("gender"));
            userRecord.setUsername(userResultSet.getString("username"));
            userRecord.setPassword(userResultSet.getString("password"));
            int foreignKeyAddressId = userResultSet.getInt("fk_address_id");

            Address userAddress = addressDAO.readRecord(foreignKeyAddressId);

            if (userAddress == null) {
                LOGGER.error(MessageFormat.format(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3019E.toString()),
                        tableNames.t_address.toString()));
                read.close();
                userResultSet.close();
                return null;
            }

            userRecord.setAddress(userAddress);
            read.close();
            userResultSet.close();
            LOGGER.traceExit(userRecord.toString());
            return userRecord;

        } catch (DatabaseConnectionException dbError) {
            throw dbError;
        } catch (SQLException sqlError) {
            LOGGER.error(sqlError.getMessage());
            return null;
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
    public List<PersonDetails> readAllRecords() throws DatabaseConnectionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteRecord(int personId) throws DatabaseConnectionException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkDatabaseConnection(Connection SqlConnection) throws DatabaseConnectionException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updateRecord(PersonDetails PersonDetails, int pk_user_id) throws DatabaseConnectionException {
        // TODO Auto-generated method stub
        return false;
    }

}
