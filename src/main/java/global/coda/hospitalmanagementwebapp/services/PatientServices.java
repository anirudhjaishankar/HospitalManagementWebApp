package global.coda.hospitalmanagementwebapp.services;

import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.MESSAGES_BUNDLE;
import static global.coda.hospitalmanagementwebapp.constants.PatientServicesConstants.PATIENT_SERVICES_CLASSNAME;

import java.util.ResourceBundle;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import global.coda.hospitalmanagementwebapp.beans.GenericResponse;
import global.coda.hospitalmanagementwebapp.beans.PatientDetails;
import global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.errorCodes;
import global.coda.hospitalmanagementwebapp.delegate.PatientDelegate;
import global.coda.hospitalmanagementwebapp.exceptions.BussinessException;
import global.coda.hospitalmanagementwebapp.exceptions.SystemException;;

@Path("/patients")
public class PatientServices {

    private Logger LOGGER = LogManager.getLogger(PATIENT_SERVICES_CLASSNAME);
    private PatientDelegate patientDelegate = new PatientDelegate();

    private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);

    @GET
    @Path("read/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse<PatientDetails> getPatient(@PathParam("id") int id)
            throws BussinessException, SystemException {
        LOGGER.traceEntry(Integer.toString(id));

        PatientDetails patientRecord = patientDelegate.readPatient(id);

        GenericResponse<PatientDetails> responseObject = new GenericResponse<PatientDetails>();
        responseObject.setData(patientRecord);
        responseObject.setStatusCode(200);
        return responseObject;
    }

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse<String> createPatient(PatientDetails patient) throws BussinessException, SystemException {
        LOGGER.traceEntry(patient.toString());

        int patientId = patientDelegate.createPatient(patient);
        GenericResponse<String> responseObject = new GenericResponse<String>();
        if (patientId != -1) {
            responseObject.setData(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS2000I.toString()));
            responseObject.setStatusCode(200);
        }

        return responseObject;

    }

    @PUT
    @Path("update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse<String> updatePatient(PatientDetails newPatient, @PathParam("id") int patientId)
            throws BussinessException, SystemException {
        LOGGER.traceEntry(newPatient.toString());

        boolean isUpdated = patientDelegate.updatePatient(newPatient, patientId);
        GenericResponse<String> genericResponse = new GenericResponse<String>();
        if (isUpdated) {
            genericResponse.setData(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS2001I.toString()));
            genericResponse.setStatusCode(200);
        }
        return genericResponse;
    }

    @DELETE
    @Path("delete/{id}")
    public GenericResponse<String> deletePatient(@PathParam("id") int patientId)
            throws BussinessException, SystemException {
        LOGGER.traceEntry(Integer.toString(patientId));

        boolean isDeleted = patientDelegate.deleteRecord(patientId);
        GenericResponse<String> genericResponse = new GenericResponse<String>();
        if(isDeleted) {
            genericResponse.setData(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS2002I.toString()));
            genericResponse.setStatusCode(200);
        }
        return genericResponse;
    }

}
