package modelo.clases;

public class Temporada {
	private int id;
	private int capitulosTotales;
	private int capitulosVistos;
	
	public Temporada(int id, int capitulosTotales, int capitulosVistos) {
		this.id = id;
		this.capitulosTotales = capitulosTotales;
		this.capitulosVistos = capitulosVistos;
	}

	public int getId() {
		return id;
	}

	public int getCapitulosTotales() {
		return capitulosTotales;
	}

	public int getCapitulosVistos() {
		return capitulosVistos;
	}

	public void setCapitulosTotales(int capitulosTotales) {
		this.capitulosTotales = capitulosTotales;
	}

	public void setCapitulosVistos(int capitulosVistos) {
		this.capitulosVistos = capitulosVistos;
	}
	
	
}
