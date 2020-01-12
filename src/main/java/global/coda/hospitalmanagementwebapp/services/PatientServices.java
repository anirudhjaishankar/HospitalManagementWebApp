package global.coda.hospitalmanagementwebapp.services;

import static global.coda.hospitalmanagementwebapp.constants.PatientServicesConstants.PATIENT_SERVICES_CLASSNAME;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import global.coda.hospitalmanagementwebapp.beans.GenericResponse;
import global.coda.hospitalmanagementwebapp.beans.PatientDetails;
import global.coda.hospitalmanagementwebapp.delegate.PatientDelegate;
import global.coda.hospitalmanagementwebapp.exceptions.DatabaseConnectionException;
import global.coda.hospitalmanagementwebapp.exceptions.InconsistentDataException;
import global.coda.hospitalmanagementwebapp.exceptions.NoRecordsFoundException;;

@Path("/patients")
public class PatientServices {

    private Logger LOGGER = LogManager.getLogger(PATIENT_SERVICES_CLASSNAME);
    private PatientDelegate patientDelegate = new PatientDelegate(); 

    @GET
    @Path("read/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse<?> getPatient(@PathParam("id") int id)
            throws DatabaseConnectionException, InconsistentDataException, SQLException, NoRecordsFoundException {
        LOGGER.traceEntry(Integer.toString(id));
        
        
        PatientDetails patientRecord = patientDelegate.readPatient(id);

        GenericResponse<PatientDetails> responseObject = new GenericResponse<PatientDetails>();
        responseObject.setData(patientRecord);
        responseObject.setStatusCode(200);
        return responseObject;
    }

}
