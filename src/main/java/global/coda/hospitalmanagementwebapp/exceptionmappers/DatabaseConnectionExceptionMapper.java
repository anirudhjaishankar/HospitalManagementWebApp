package global.coda.hospitalmanagementwebapp.exceptionmappers;

import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.MESSAGES_BUNDLE;
import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.errorCodes;

import java.util.ResourceBundle;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;

public class DatabaseConnectionExceptionMapper implements ExceptionMapper<DatabaseConnectionException>{


    private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);
    
    @Override
    @Produces(MediaType.TEXT_PLAIN)
    public Response toResponse(DatabaseConnectionException exception) {
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS2001E.toString())).build();
    }

}
