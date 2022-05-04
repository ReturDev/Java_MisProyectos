package com.retur.main.modelo.elementos;

import com.retur.main.modelo.enums.Estados;

public class PeliculaAnime extends PiezaAudiovisual {

	public PeliculaAnime(int id, String titulo, String sinopsis, Estados estado) {
		super(id, titulo, sinopsis, estado);
	}

	public PeliculaAnime() {}

}
