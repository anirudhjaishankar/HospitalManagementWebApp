package global.coda.hospitalmanagementwebapp.doa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.beans.PersonDetails;

public interface UserDAO {
    int createRecord(PersonDetails personObject, int addressId,int roleId) throws DatabaseConnectionException, SQLException;

    PersonDetails readRecord(int personId) throws DatabaseConnectionException;

    List<PersonDetails> readAllRecords() throws DatabaseConnectionException;

    boolean deleteRecord(int personId) throws DatabaseConnectionException, SQLException;

    boolean checkDatabaseConnection(Connection SqlConnection) throws DatabaseConnectionException;

    boolean updateRecord(PersonDetails PersonDetails, int pk_user_id) throws DatabaseConnectionException, SQLException;

    int getAddressId(int userId) throws DatabaseConnectionException, SQLException;
}
