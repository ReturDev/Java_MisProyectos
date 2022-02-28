package modelo.clases;

import modelo.enums.Estados;

public class Pelicula extends PiezaAudiovisual {

	public Pelicula(int id, String titulo, String sinopsis, Estados estado) {
		super(id, titulo, sinopsis, estado);
	}

	public Pelicula() {}

}
