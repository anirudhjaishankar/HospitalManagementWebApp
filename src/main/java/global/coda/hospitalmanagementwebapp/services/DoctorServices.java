package global.coda.hospitalmanagementwebapp.services;

import static global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.MESSAGES_BUNDLE;
import static global.coda.hospitalmanagementwebapp.constants.PatientServicesConstants.PATIENT_SERVICES_CLASSNAME;

import java.util.ArrayList;
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

import global.coda.hospitalmanagementwebapp.beans.DoctorDetails;
import global.coda.hospitalmanagementwebapp.beans.GenericResponse;
import global.coda.hospitalmanagementwebapp.beans.PatientDetails;
import global.coda.hospitalmanagementwebapp.constants.ApplicationConstants.errorCodes;
import global.coda.hospitalmanagementwebapp.delegate.DoctorDelegate;
import global.coda.hospitalmanagementwebapp.exceptions.BussinessException;
import global.coda.hospitalmanagementwebapp.exceptions.SystemException;

@Path("/doctors")
public class DoctorServices {

	private Logger LOGGER = LogManager.getLogger(PATIENT_SERVICES_CLASSNAME);
	private DoctorDelegate doctorDelegate = new DoctorDelegate();

	private static ResourceBundle LOCAL_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE);

	@GET
	@Path("read/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse<DoctorDetails> getDoctor(@PathParam("id") int id)
			throws BussinessException, SystemException {
		LOGGER.traceEntry(Integer.toString(id));

		DoctorDetails patientRecord = doctorDelegate.readDoctor(id);

		GenericResponse<DoctorDetails> responseObject = new GenericResponse<DoctorDetails>();
		responseObject.setData(patientRecord);
		responseObject.setStatusCode(200);
		return responseObject;
	}

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse<String> createDoctor(DoctorDetails doctor) throws BussinessException, SystemException {
		LOGGER.traceEntry(doctor.toString());

		int doctorId = doctorDelegate.createDoctor(doctor);
		GenericResponse<String> responseObject = new GenericResponse<String>();
		if (doctorId != -1) {
			responseObject.setData(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3000I.toString()));
			responseObject.setStatusCode(200);
		}

		return responseObject;

	}

	@PUT
	@Path("update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse<String> updateDoctor(DoctorDetails newDoctor, @PathParam("id") int doctorId)
			throws BussinessException, SystemException {
		LOGGER.traceEntry(newDoctor.toString());

		boolean isUpdated = doctorDelegate.updateDoctor(newDoctor, doctorId);
		GenericResponse<String> genericResponse = new GenericResponse<String>();
		if (isUpdated) {
			genericResponse.setData(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3001I.toString()));
			genericResponse.setStatusCode(200);
		}
		return genericResponse;
	}

	@DELETE
	@Path("delete/{id}")
	public GenericResponse<String> deleteDoctor(@PathParam("id") int doctorId)
			throws BussinessException, SystemException {
		LOGGER.traceEntry(Integer.toString(doctorId));

		boolean isDeleted = doctorDelegate.deleteDoctor(doctorId);
		GenericResponse<String> genericResponse = new GenericResponse<String>();
		if (isDeleted) {
			genericResponse.setData(LOCAL_MESSAGE_BUNDLE.getString(errorCodes.HOS3002I.toString()));
			genericResponse.setStatusCode(200);
		}
		return genericResponse;
	}

	@GET
	@Path("{id}/patients")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse<ArrayList<PatientDetails>> getAllPatientsForDoctor(@PathParam("id") int doctorId)
			throws BussinessException, SystemException {
		LOGGER.traceEntry(Integer.toString(doctorId));

		ArrayList<PatientDetails> patientList = doctorDelegate.getPatientsDoctor(doctorId);
		GenericResponse<ArrayList<PatientDetails>> genericResponse = new GenericResponse<ArrayList<PatientDetails>>();
		genericResponse.setData(patientList);
		genericResponse.setStatusCode(200);
		return genericResponse;
	}

	@GET
	@Path("getAllPatients")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse<ArrayList<PatientDetails>> getAllPatientsForAllDoctor()
			throws BussinessException, SystemException {
		LOGGER.traceEntry();

		ArrayList<PatientDetails> patientList = doctorDelegate.getAllPatientAllDoctors();
		GenericResponse<ArrayList<PatientDetails>> genericResponse = new GenericResponse<ArrayList<PatientDetails>>();
		genericResponse.setData(patientList);
		genericResponse.setStatusCode(200);
		return genericResponse;
	}

}
