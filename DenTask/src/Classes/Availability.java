package Classes;

public class Availability {

	String dayOfWeek;
	String startTime;
	String endTime;
	
	public Availability() {}
	
	public Availability(String dayOfWeek)	{
		this.dayOfWeek = dayOfWeek;
		startTime = "0800";
		endTime = "1700";
	}
	
	public Availability(String dayOfWeek, String startTime, String endTime)	{
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}
	
	public Availability setStartTime(String startTime) {
		this.startTime = startTime;
		return this;
	}

	public Availability setEndTime(String endTime) {
		this.endTime = endTime;
		return this;
	}
	
	public String toString()	{
		return dayOfWeek + " (" + startTime + "-" + endTime + ")";
	}
}
