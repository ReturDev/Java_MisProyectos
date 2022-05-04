package com.retur.main.modelo.elementos;

import java.util.ArrayList;
import java.util.Objects;

import com.retur.main.modelo.enums.Estados;

public abstract class Serializable extends PiezaAudiovisual{
	

	private int temporadasTotales;
	private int temporadasVistas;
	private ArrayList<Temporada> temporadas;

	
	public Serializable(int id, String titulo, String sinopsis, Estados estado, int temporadasTotales,
			int temporadasVistas, ArrayList<Temporada> temporadas) {
		super(id, titulo, sinopsis, estado);
		this.temporadasTotales = temporadasTotales;
		this.temporadasVistas = temporadasVistas;
		this.temporadas = temporadas;
	}
	public Serializable() {}

	public int getTemporadasTotales() {
		return temporadasTotales;
	}
	public void setTemporadasTotales(int temporadasTotales) {
		this.temporadasTotales = temporadasTotales;
	}
	public int getTemporadasVistas() {
		return temporadasVistas;
	}
	public void setTemporadasVistas(int temporadasVistas) {
		this.temporadasVistas = temporadasVistas;
	}
	public ArrayList<Temporada> getTemporadas() {
		return temporadas;
	}
	public void setTemporadas(ArrayList<Temporada> temporadas) {
		this.temporadas = temporadas;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(temporadas, temporadasTotales, temporadasVistas);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Serializable other = (Serializable) obj;
		return Objects.equals(temporadas, other.temporadas) && temporadasTotales == other.temporadasTotales
				&& temporadasVistas == other.temporadasVistas;
	}
	
	
}
