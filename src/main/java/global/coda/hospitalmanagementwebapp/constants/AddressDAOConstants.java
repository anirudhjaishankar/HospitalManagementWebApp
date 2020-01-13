package global.coda.hospitalmanagementwebapp.constants;

public abstract class AddressDAOConstants {

    public static final String ADDRESSDOAIMPLEMENTATION_CLASSNAME = "AddressDAOImplementation";
    public static final String ADDRESS_READ_QUERY = "SELECT flatname, flatnumber, street, area, city, state, pincode FROM t_address WHERE pk_address_id = ";
    public static final String ADDRESS_UPDATE_QUERY = "UPDATE t_address SET flatname = ?, flatnumber = ?, street = ?, area = ?, city = ?, state = ?, pincode = ? where pk_address_id = ";
    public static final String ADDRESS_INSERT_QUERY = "INSERT INTO t_address(flatname, flatnumber, street, area, city, state, pincode) VALUES (?, ?, ?, ?, ?, ?, ?);";
    public static final String ADDRESS_DELETE_QUERY = "UPDATE t_address SET is_deleted = 1 WHERE pk_address_Id = ";

}
