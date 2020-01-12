package coda.global.hospitalmanagementwebapp.daoimplimentation;


import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.MESSAGES_BUNDLE;
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
import global.coda.hospitalmanagementwebapp.constants.DAOConstants.tableNames;
import global.coda.hospitalmanagementwebapp.beans.Address;
import global.coda.hospitalmanagementwebapp.doa.AddressDAO;
import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import static global.coda.hospitalmanagementwebapp.constants.AddressDAOConstants.*;

public class AddressDAOImplementation implements AddressDAO {

    private Logger LOGGER = LogManager.getLogger(ADDRESSDOAIMPLEMENTATION_CLASSNAME);
    private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);

    @Override
    public int createRecord(Address AddressObject) throws DatabaseConnectionException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Address readRecord(int addressId) throws DatabaseConnectionException {
        LOGGER.traceEntry(Integer.toString(addressId));
        Connection mySqlConnection = createConnection(MAIN_DB);
        PreparedStatement read = null;
        Address userAddress = new Address();

        if (mySqlConnection == null) {
            throw new DatabaseConnectionException();
        }
        try {
            read = mySqlConnection.prepareStatement(ADDRESS_READ_QUERY + addressId);
            ResultSet addressResultSet = read.executeQuery();
            if (addressResultSet.next() == false) {
                LOGGER.error(MessageFormat.format(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3019E.toString()),
                        tableNames.t_address.toString()));
                read.close();
                return null;
            }
            userAddress.setFlatName(addressResultSet.getString("flatname"));
            userAddress.setFlatNumber(addressResultSet.getString("flatnumber"));
            userAddress.setStreetName(addressResultSet.getString("street"));
            userAddress.setAreaName(addressResultSet.getString("area"));
            userAddress.setCityName(addressResultSet.getString("city"));
            userAddress.setStateName(addressResultSet.getString("state"));
            userAddress.setPincode(addressResultSet.getLong("pincode"));
            read.close();
            addressResultSet.close();
            LOGGER.traceExit(userAddress.toString());
            return userAddress;
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
    public List<Address> readAllRecords() throws DatabaseConnectionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteRecord(int AddressId) throws DatabaseConnectionException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkDatabaseConnection(Connection SqlConnection) throws DatabaseConnectionException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updateRecord(Address addressDetails, int pk_address_id) throws DatabaseConnectionException {
        // TODO Auto-generated method stub
        return false;
    }

}
