package coda.global.hospitalmanagementwebapp.daoimplimentation;

import static global.coda.hospitalmanagementwebapp.constants.UserDAOConstants.*;
import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.MESSAGES_BUNDLE;
import static global.coda.hospitalmanagementwebapp.constants.UserDAOConstants.USER_READ_QUERY;
import static global.coda.hospitalmanagementwebapp.doa.JDBCController.createConnection;
import static global.coda.hospitalmanagementwebapp.constants.DAOConstants.MAIN_DB;
import static global.coda.hospitalmanagementwebapp.constants.PatientDAOConstants.PATIENT_DELETE_QUERY;

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
import global.coda.hospitalmanagementwebapp.beans.PersonDetails;
import global.coda.hospitalmanagementwebapp.doa.UserDAO;
import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;

public class UserDAOImplementation implements UserDAO {

    private Logger LOGGER = LogManager.getLogger(USERDAOIMPLEMENTATION_CLASSNAME);
    private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);
    private AddressDAOImplementation addressDAO = new AddressDAOImplementation();

    @Override
    public int createRecord(PersonDetails personObject, int addressId, int roleId)
            throws DatabaseConnectionException, SQLException {
        Connection mySqlConnection = createConnection(MAIN_DB);
        PreparedStatement insert = null;
        if (mySqlConnection == null) {
            LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
            throw new DatabaseConnectionException();
        }
        try {
            insert = mySqlConnection.prepareStatement(USER_INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, personObject.getName());
            insert.setString(2, Integer.toString(personObject.getAge()));
            insert.setString(3, personObject.getGender());
            insert.setString(4, Integer.toString(roleId));
            insert.setString(5, personObject.getUsername());
            insert.setString(6, personObject.getPassword());
            insert.setString(7, Integer.toString(addressId));

            int rowsAffectedUser = insert.executeUpdate();
            if (rowsAffectedUser == 0) {
                LOGGER.error(MessageFormat.format(errorCodes.HOS3015E.toString(), tableNames.t_patient.toString()));
                return -1;
            }
            int insertedUserId = -1;
            ResultSet userResultSet = insert.getGeneratedKeys();
            if (userResultSet.next()) {
                insertedUserId = userResultSet.getInt(1);
            }

            return insertedUserId;

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
            if (userResultSet.next() == false) {
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
    public boolean deleteRecord(int personId) throws DatabaseConnectionException, SQLException {
        Connection mySqlConnection = createConnection(MAIN_DB);
        PreparedStatement delete = null;
        if (mySqlConnection == null) {
            LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
            throw new DatabaseConnectionException();
        }
        try {
            delete = mySqlConnection.prepareStatement(USER_DELETE_QUERY + personId);
            int rowsAffectedPatients = delete.executeUpdate();
            if (rowsAffectedPatients == 0) {
                LOGGER.error(MessageFormat.format(errorCodes.HOS3015E.toString(), tableNames.t_patient.toString()));
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
    public boolean updateRecord(PersonDetails personDetails, int userId) throws DatabaseConnectionException,SQLException {
        Connection mySqlConnection = createConnection(MAIN_DB);
        PreparedStatement update = null;
        if (mySqlConnection == null) {
            LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
            throw new DatabaseConnectionException();
        }
        try {
            update = mySqlConnection.prepareStatement(USER_UPDATE_QUERY + userId);
            update.setString(1, personDetails.getName());
            update.setInt(2, personDetails.getAge());
            update.setString(3, personDetails.getGender());
            update.setString(4, personDetails.getUsername());
            update.setString(5, personDetails.getPassword());
            
            int rowsAffectedPatients = update.executeUpdate();
            if (rowsAffectedPatients == 0) {
                LOGGER.error(MessageFormat.format(errorCodes.HOS3015E.toString(), tableNames.t_patient.toString()));
                return false;
            }
            return true;
        } catch (SQLException sqlError) {
            LOGGER.error(sqlError);
            throw sqlError;
        }
    }

    @Override
    public int getAddressId(int userId) throws DatabaseConnectionException, SQLException {
        LOGGER.traceEntry(Integer.toString(userId));
        Connection mySqlConnection = createConnection(MAIN_DB);
        PreparedStatement read = null;
        if (mySqlConnection == null) {
            throw new DatabaseConnectionException(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
        }
        try {
            read = mySqlConnection.prepareStatement(USER_ADDRESSID_READ_QUERY + userId);
            ResultSet resultSet = read.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("fk_user_id");
            }
        } catch (SQLException sqlError) {
            LOGGER.error(sqlError.getMessage());
            throw sqlError;
        }
        return -1;
    }
}
