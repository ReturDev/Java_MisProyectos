package modelo.clases;

import java.util.ArrayList;

import modelo.enums.Estados;

public class Anime extends Serializable {



	public Anime(int id, String titulo, String sinopsis, Estados estado, int temporadasTotales, int temporadasVistas,
			ArrayList<Temporada> temporadas) {
		super(id, titulo, sinopsis, estado, temporadasTotales, temporadasVistas, temporadas);

	}
	
	public Anime() {}

}
