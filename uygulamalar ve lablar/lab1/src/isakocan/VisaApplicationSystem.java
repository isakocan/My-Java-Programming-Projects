package isakocan;

import java.util.ArrayList;
import java.util.List;


public class VisaApplicationSystem {
	
	private List<Application> applications;
	
	
	public VisaApplicationSystem() {
		this.applications = new ArrayList<Application>();
	}


	public void addApplication(Applicant applicant, VisaType visatype, int duration) {
		Application application = new Application(applicant, visatype, duration);
		application.approveApplication();
		applications.add(application);
		application.applicationInfo();
	}
	
}
