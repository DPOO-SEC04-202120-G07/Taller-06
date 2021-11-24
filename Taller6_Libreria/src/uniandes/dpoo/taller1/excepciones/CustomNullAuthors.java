package uniandes.dpoo.taller1.excepciones;

public class CustomNullAuthors extends Exception{

	private static final long serialVersionUID = -820190890264834260L;
	
	private String autorNulo = null;
	
	public CustomNullAuthors(String autorNulo) {
		this.autorNulo = autorNulo;
	}

	public String getAutorNulo() {
		return autorNulo;
	}

}
