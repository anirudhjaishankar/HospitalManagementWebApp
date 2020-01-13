package global.coda.hospitalmanagementwebapp.doa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.beans.Address;

public interface AddressDAO {
    int createRecord(Address AddressObject) throws DatabaseConnectionException, SQLException;

    Address readRecord(int AddressId) throws DatabaseConnectionException;

    List<Address> readAllRecords() throws DatabaseConnectionException;

    boolean deleteRecord(int AddressId) throws DatabaseConnectionException, SQLException;

    boolean checkDatabaseConnection(Connection SqlConnection) throws DatabaseConnectionException;

    boolean updateRecord(Address addressDetails, int pk_address_id) throws DatabaseConnectionException, SQLException;
}
