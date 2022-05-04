package com.retur.main.modelo.elementos;

import java.util.ArrayList;

import com.retur.main.modelo.enums.Estados;

public class Serie extends Serializable {

	public Serie(int id, String titulo, String sinopsis, Estados estado, int temporadasTotales, int temporadasVistas,
			ArrayList<Temporada> temporadas) {
		
		super(id, titulo, sinopsis, estado, temporadasTotales, temporadasVistas, temporadas);
	
	}
	
	public Serie() {}



	
	
	

}
