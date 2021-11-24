package uniandes.dpoo.taller1.excepciones;

import uniandes.dpoo.taller1.modelo.Libro;

public class CustomMissingBook extends Exception{

	private static final long serialVersionUID = 5590984482887723428L;
	private Libro missingBook = null;
	
	public CustomMissingBook(Libro missingBook) {
		this.missingBook = missingBook;
	}

	public Libro getMissingBook() {
		return missingBook;
	}

	
}
