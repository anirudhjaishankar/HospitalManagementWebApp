package global.coda.hospitalmanagementwebapp.constants;

public abstract class UserDAOConstants {
    public static final String USERDAOIMPLEMENTATION_CLASSNAME = "UserDAOImplementation";
    public static final String USER_READ_QUERY = "SELECT name, age, gender, username, password, fk_address_id FROM t_user WHERE pk_user_id = ";
    public static final String USER_INSERT_QUERY = "INSERT INTO t_user(name, age, gender, fk_role_id, username, password, fk_address_id) VALUES(?, ?, ?, ?, ?, ?, ?)"; 
}
