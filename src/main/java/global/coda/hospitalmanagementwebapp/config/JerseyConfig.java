package global.coda.hospitalmanagementwebapp.config;

import org.glassfish.jersey.server.ResourceConfig;

import global.coda.hospitalmanagementwebapp.exceptionmappers.BussinessExceptionMapper;
import global.coda.hospitalmanagementwebapp.exceptionmappers.SystemExceptionMapper;
import global.coda.hospitalmanagementwebapp.services.PatientServices;

public class JerseyConfig extends ResourceConfig{
	public JerseyConfig() {
		register(PatientServices.class);
		register(BussinessExceptionMapper.class);
		register(SystemExceptionMapper.class);
	}
}
