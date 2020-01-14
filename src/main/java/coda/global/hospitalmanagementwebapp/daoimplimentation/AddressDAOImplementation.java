package coda.global.hospitalmanagementwebapp.daoimplimentation;

import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.MESSAGES_BUNDLE;
import static global.coda.hospitalmanagementwebapp.doa.JDBCController.createConnection;
import static global.coda.hospitalmanagementwebapp.constants.DAOConstants.MAIN_DB;

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
import global.coda.hospitalmanagementwebapp.doa.AddressDAO;
import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import static global.coda.hospitalmanagementwebapp.constants.AddressDAOConstants.*;

public class AddressDAOImplementation implements AddressDAO {

	private Logger LOGGER = LogManager.getLogger(ADDRESSDOAIMPLEMENTATION_CLASSNAME);
	private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);

	@Override
	public int createRecord(Address addressObject) throws DatabaseConnectionException, SQLException {
		Connection mySqlConnection = createConnection(MAIN_DB);
		PreparedStatement insert = null;
		if (mySqlConnection == null) {
			LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
			throw new DatabaseConnectionException();
		}
		try {
			insert = mySqlConnection.prepareStatement(ADDRESS_INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
			insert.setString(1, addressObject.getFlatName());
			insert.setString(2, addressObject.getFlatNumber());
			insert.setString(3, addressObject.getStreetName());
			insert.setString(4, addressObject.getAreaName());
			insert.setString(5, addressObject.getCityName());
			insert.setString(6, addressObject.getStateName());
			insert.setString(7, Long.toString(addressObject.getPincode()));

			int rowsAffectedUser = insert.executeUpdate();
			if (rowsAffectedUser == 0) {
				LOGGER.error(MessageFormat.format(errorCodes.HOS3015E.toString(), tableNames.t_patient.toString()));
				return -1;
			}
			ResultSet addressResultSet = insert.getGeneratedKeys();
			int insertedAddressId = -1;
			if (addressResultSet.next()) {
				insertedAddressId = addressResultSet.getInt(1);
			}
			return insertedAddressId;

		} catch (SQLException sqlError) {
			LOGGER.error(sqlError);
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
	public boolean deleteRecord(int AddressId) throws DatabaseConnectionException, SQLException {
		Connection mySqlConnection = createConnection(MAIN_DB);
		PreparedStatement delete = null;
		if (mySqlConnection == null) {
			LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
			throw new DatabaseConnectionException();
		}
		try {
			delete = mySqlConnection.prepareStatement(ADDRESS_DELETE_QUERY + AddressId);
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
	public boolean updateRecord(Address addressDetails, int addressId)
			throws DatabaseConnectionException, SQLException {
		Connection mySqlConnection = createConnection(MAIN_DB);
		PreparedStatement update = null;
		if (mySqlConnection == null) {
			LOGGER.error(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3014E.toString()));
			throw new DatabaseConnectionException();
		}
		try {
			update = mySqlConnection.prepareStatement(ADDRESS_UPDATE_QUERY + addressId);
			update.setString(1, addressDetails.getFlatName());
			update.setString(2, addressDetails.getFlatNumber());
			update.setString(3, addressDetails.getStreetName());
			update.setString(4, addressDetails.getAreaName());
			update.setString(5, addressDetails.getCityName());
			update.setString(6, addressDetails.getStateName());
			update.setLong(7, addressDetails.getPincode());

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

}
