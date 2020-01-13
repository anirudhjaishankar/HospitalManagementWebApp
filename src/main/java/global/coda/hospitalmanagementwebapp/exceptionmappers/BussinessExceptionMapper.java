package global.coda.hospitalmanagementwebapp.exceptionmappers;

import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.MESSAGES_BUNDLE;

import java.util.ResourceBundle;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.errorCodes;
import global.coda.hospitalmanagementwebapp.exceptions.BussinessException;

@Provider
public class BussinessExceptionMapper implements ExceptionMapper<BussinessException> {
	private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);

	@Override
	public Response toResponse(BussinessException bussinessError) {
		// TODO Auto-generated method stub
		return Response.status(400).entity(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS2002E.toString())).build();
	}

}
