package global.coda.hospitalmanagementwebapp.config;

import org.glassfish.jersey.server.ResourceConfig;
import global.coda.hospitalmanagementwebapp.services.PatientServices;

public class JerseyConfig extends ResourceConfig{
	public JerseyConfig() {
		register(PatientServices.class);
	}
}
