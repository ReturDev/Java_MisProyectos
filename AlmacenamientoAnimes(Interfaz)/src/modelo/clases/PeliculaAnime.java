package modelo.clases;

import modelo.enums.Estados;

public class PeliculaAnime extends PiezaAudiovisual {

	public PeliculaAnime(int id, String titulo, String sinopsis, Estados estado) {
		super(id, titulo, sinopsis, estado);
	}

	public PeliculaAnime() {}

}
