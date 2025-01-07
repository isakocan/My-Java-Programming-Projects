package isakocan;

public class VisaType {
	private String type;
	private int MaxStayDuration;
	private boolean requiresWorkPermit;
	

	public VisaType(String type, int maxStayDuration, boolean requiresWorkPermit) {
		this.type = type;
		MaxStayDuration = maxStayDuration;
		this.requiresWorkPermit = requiresWorkPermit;
	}


	public String getType() {
		return type;
	}


	public int getMaxStayDuration() {
		return MaxStayDuration;
	}


	public boolean isRequiresWorkPermit() {
		return requiresWorkPermit;
	}


	@Override
	public String toString() {
		return "Vize Türü: " + type;
	}
	
	
	
	
	
}
