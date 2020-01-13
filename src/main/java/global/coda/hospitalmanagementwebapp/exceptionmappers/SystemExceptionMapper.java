package global.coda.hospitalmanagementwebapp.exceptionmappers;

import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.MESSAGES_BUNDLE;

import java.util.ResourceBundle;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.errorCodes;
import global.coda.hospitalmanagementwebapp.exceptions.SystemException;

@Provider
public class SystemExceptionMapper extends Exception implements ExceptionMapper<SystemException> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);

	@Override
	public Response toResponse(SystemException exception) {
		return Response.status(500).entity(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS2001E.toString())).build();
	}

}
