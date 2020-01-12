package global.coda.hospitalmanagementwebapp.doa;

import static  global.coda.hospitalmanagementwebapp.constants.JDBCControllerConstants.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JDBCController {
    private static Connection mySqlConnector = null;
    private static Logger LOGGER = LogManager.getLogger(JDBCCONTROLLER_CLASSNAME);

    public static Connection createConnection(String dbName) {
        LOGGER.traceEntry();
        if (mySqlConnector != null) {
            return mySqlConnector;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            mySqlConnector = DriverManager.getConnection(DB_URL + dbName, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException mySqlException) {
            LOGGER.error(mySqlException.getMessage());
            return null;
        } catch (ClassNotFoundException classError) {
            LOGGER.error(classError.getMessage());
            return null;
        }
        LOGGER.traceExit(mySqlConnector.toString());
        return mySqlConnector;
    }

    public static boolean closeConnection() {
        LOGGER.traceEntry();
        if (mySqlConnector != null) {
            try {
                mySqlConnector.close();
                mySqlConnector = null;
            } catch (SQLException mySqlException) {
                LOGGER.error(mySqlException);
                return false;
            }
            LOGGER.traceExit(true);
            return true;
        }
        LOGGER.traceExit(false);
        return false;
    }

}
