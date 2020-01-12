package global.coda.hospitalmanagementwebapp.doa;

import java.sql.Connection;
import java.util.List;

import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.beans.PersonDetails;

public interface UserDAO {
    int createRecord(PersonDetails personObject) throws DatabaseConnectionException;

    PersonDetails readRecord(int personId) throws DatabaseConnectionException;

    List<PersonDetails> readAllRecords() throws DatabaseConnectionException;

    boolean deleteRecord(int personId) throws DatabaseConnectionException;

    boolean checkDatabaseConnection(Connection SqlConnection) throws DatabaseConnectionException;

    boolean updateRecord(PersonDetails PersonDetails, int pk_user_id) throws DatabaseConnectionException;
}
