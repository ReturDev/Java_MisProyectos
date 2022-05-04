package com.retur.main.modelo.elementos;

import com.retur.main.modelo.enums.Estados;

public class Pelicula extends PiezaAudiovisual {

	public Pelicula(int id, String titulo, String sinopsis, Estados estado) {
		
		super(id, titulo, sinopsis, estado);
		
	}

	public Pelicula() {}

}
