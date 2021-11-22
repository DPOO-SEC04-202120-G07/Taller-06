package uniandes.dpoo.taller1.excepciones;

public class CustomNullException extends NullPointerException{
	
	private static final long serialVersionUID = 8045508598966921212L;

	//Constantes -- Describen el objeto que no fue encontrado (Objeto nulo)
	public static final String NULL_CATEGORY = "null-category";
	
	
	//Variable que describe el tipo de error encontrado
	private String nullObject = null;
	private String categoriaName = null;
	
	//Método constructor
	public CustomNullException(String nullObject) {
		
		if(nullObject == NULL_CATEGORY) {
			this.setNullObject(NULL_CATEGORY);
		}
		
	}


	//Getters y setters de la variable que describe el tipo de objeto nulo encontrado
	public String getNullObject() {
		return nullObject;
	}


	public void setNullObject(String nullObject) {
		this.nullObject = nullObject;
	}


	public String getCategoriaName() {
		return categoriaName;
	}


	public void setCategoriaName(String categoriaName) {
		this.categoriaName = categoriaName;
	}
	
	
}
