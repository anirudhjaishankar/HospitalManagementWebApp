package global.coda.hospitalmanagementwebapp.constants;

public abstract class AddressDAOConstants {
    
   public static final String ADDRESSDOAIMPLEMENTATION_CLASSNAME = "AddressDAOImplementation";
   public static final String ADDRESS_READ_QUERY = "SELECT flatname, flatnumber, street, area, city, state, pincode FROM t_address WHERE pk_address_id = ";

}
