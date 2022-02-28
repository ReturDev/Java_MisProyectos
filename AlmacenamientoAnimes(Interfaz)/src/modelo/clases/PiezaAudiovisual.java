package modelo.clases;

import java.util.Objects;

import modelo.converters.EstadosConverter;
import modelo.enums.Estados;

public abstract class PiezaAudiovisual {

	private int id;
	private String titulo;
	private String sinopsis;
	private Estados estado;
	
	public PiezaAudiovisual(int id, String titulo,String sinopsis,Estados estado) {
		
		this.id = id;
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.estado = estado;

	}
	
	public PiezaAudiovisual() {}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public Estados getEstado() {
		return estado;
	}

	public void setEstado(Estados estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return this.titulo + "\t--> " + new EstadosConverter().toString(this.estado);
	}

	@Override
	public int hashCode() {
		return Objects.hash(estado, id, sinopsis, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PiezaAudiovisual other = (PiezaAudiovisual) obj;
		return estado == other.estado && id == other.id && Objects.equals(sinopsis, other.sinopsis)
				&& Objects.equals(titulo, other.titulo);
	}
	
	
	
	
	
}
