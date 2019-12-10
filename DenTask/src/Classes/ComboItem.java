package Classes;

public class ComboItem {
	
	private String key;
    private String value;
    private Appointment appoint;

    /**
     * Constructor for a ComboItem for ComboBoxes
     * 
     * @param key - What the ComboBox shows
     * @param value - What the Selected ComboBox Item returns
     */
    public ComboItem(String key, String value){
        this.key = key;
        this.value = value;
    }

    /**
     * Constructor for a ComboItem for ComboBoxes
     * 
     * @param key - What the ComboBox shows
     * @param app - Stores a local Appointment object
     */
    public ComboItem(String key, Appointment app){
        this.key = key;
        this.appoint = app;
    }

    /**
     * Returns the Key as a string with .toString();
     */
    @Override
    public String toString(){
        return key;
    }

    /**
     * Returns the Key as a string
     */
    public String getKey(){
        return key;
    }

    /**
     * Returns the value as a string
     */
    public String getValue(){
        return value;
    }

    /**
     * Returns the stored Appointment as a Appointment object
     */
    public Appointment getApp() {
    	return appoint;
    }
    
}
