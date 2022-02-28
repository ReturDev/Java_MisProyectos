package modulo.listas;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.clases.PiezaAudiovisual;
import modelo.clases.Temporada;
import modelo.enums.Estados;
import modelo.enums.TiposPiezasAudiovisuales;
import modelo.funciones.Consultas;

public class ListasObservables {

	public static ObservableList<Estados> listaEstados(){
		ObservableList<Estados> lista = FXCollections.observableArrayList();
		Estados[] estados = Estados.values();
		lista.addAll(Arrays.asList(estados));
		
		return lista;
	}
	
	public static ObservableList<TiposPiezasAudiovisuales> listaTipos(){
		ObservableList<TiposPiezasAudiovisuales> lista = FXCollections.observableArrayList();
		TiposPiezasAudiovisuales[] tipos = TiposPiezasAudiovisuales.values();
		lista.addAll(Arrays.asList(tipos));
		
		return lista;
		
	}
	
	public static ObservableList<PiezaAudiovisual> listaPiezasTipos(){
		ObservableList<PiezaAudiovisual> lista = FXCollections.observableArrayList();
		lista.addAll(Consultas.getListaElementosBase());
		lista.sort((o1, o2) -> o1.getId() - o2.getId());
		
		return lista;
	}
	
	public static ObservableList<PiezaAudiovisual> listaBusquedaTitulo(){
		ObservableList<PiezaAudiovisual> lista = FXCollections.observableArrayList();
		
		lista.addAll(Consultas.busquedaPorNombre());
		lista.sort((o1, o2) -> o1.getId() - o2.getId());
		
		return lista;
	}
	
	public static ObservableList<PiezaAudiovisual> listaBusquedaEstado(){
		ObservableList<PiezaAudiovisual> lista = FXCollections.observableArrayList();
		
		lista.addAll(Consultas.busquedaPorEstado());
		lista.sort((o1, o2) -> o1.getId() - o2.getId());
		
		return lista;
	}
	
	public static ObservableList<Temporada> listaTemporadas(ArrayList<Temporada> temporadas){
		ObservableList<Temporada> lista = FXCollections.observableArrayList();
		lista.addAll(temporadas);
		return lista;
	}
}
