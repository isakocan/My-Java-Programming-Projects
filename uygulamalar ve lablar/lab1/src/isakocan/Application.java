package isakocan;

public class Application {
	
	private Applicant applicant;
	private VisaType visaType;
	private String status;
	private int duration;
	
	
	public Application(Applicant applicant, VisaType visaType, int duration) {
		status = "beklemede";
		this.applicant = applicant;
		this.visaType = visaType;
		this.duration = duration;
	}
	
	
	public void approveApplication() {
		
		if(visaType.getType().equals("Turist") && duration > visaType.getMaxStayDuration()) {			
			status = "Başvuru reddedildi.\nReddetme nedeni: Turist vizesi için max kalış süresi aşıldı.";
		}
		
		else if(visaType.getType().equals("Çalışma") && !applicant.isHasWorkPermit()) {			
			status = "Başvuru reddedildi.\nReddetme nedeni: Çalışma izni yok.";
		}
		
		else {
			status = "Başvuru onaylandı.";
		}
		
	}
	
	
	public void applicationInfo() {
		System.out.println(applicant.toString());
		System.out.println(visaType.toString());
		System.out.println("Kalış süresi: " + duration + " gün\n" + "Durum: " + status);
		
	}


	public String getStatus() {
		return status;
	}
	
	
	
}
