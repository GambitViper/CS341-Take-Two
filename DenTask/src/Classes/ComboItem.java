package Classes;

public class ComboItem {
	
	private String key;
    private String value;
    private Appointment appoint;

    public ComboItem(String key, String value){
        this.key = key;
        this.value = value;
    }
    
    public ComboItem(String key, Appointment app){
        this.key = key;
        this.appoint = app;
    }

    @Override
    public String toString(){
        return key;
    }

    public String getKey(){
        return key;
    }

    public String getValue(){
        return value;
    }
    
    public Appointment getApp() {
    	return appoint;
    }
    
}
