package uniandes.dpoo.taller1.excepciones;

public class CustomRepeatedException extends Exception{

	private static final long serialVersionUID = -659482843288707004L;
	private String repeatedName = null;
	
	public CustomRepeatedException(String repeatedName){
		this.repeatedName = repeatedName; 
	}

	public String getRepeatedName() {
		return repeatedName;
	}

	
	
}
