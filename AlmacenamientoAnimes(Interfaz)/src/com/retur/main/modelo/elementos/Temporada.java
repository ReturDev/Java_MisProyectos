package com.retur.main.modelo.elementos;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		
		return Objects.hash(capitulosTotales, capitulosVistos, id);
		
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			
			return true;
		
		if (obj == null)
			
			return false;
		
		if (getClass() != obj.getClass())
			
			return false;
		
		Temporada other = (Temporada) obj;
		
		return capitulosTotales == other.capitulosTotales && capitulosVistos == other.capitulosVistos && id == other.id;
	
	}
	
	
}
