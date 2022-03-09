package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.clases.*;
import modelo.converters.*;
import modelo.enums.Estados;
import modelo.envioDatos.EnvioDatos;
import modelo.funciones.*;
import modelo.funcionesXML.RegistroDatosXML;
import modulo.alertas.Alertas;
import modulo.listas.ListasObservables;
import modulo.textoAlertas.MensajesAlertas;

public class ControladorModificacion implements Initializable {

	// Creación de elementos coincidiendo el nombre con la id en el FXML
	@FXML
	private AnchorPane raiz;
	@FXML
	private TextField titulo;
	@FXML
	private TextField id;
	@FXML
	private TextField tempTotales;
	@FXML
	private TextField tempVistas;
	@FXML
	private TextArea sinopsis;
	@FXML
	private ComboBox<Estados> estado;
	@FXML
	private TableView<Temporada> tablaTemporadas;
	@FXML
	private TableColumn<Temporada, Integer> columnaTemporada;
	@FXML
	private TableColumn<Temporada, Integer> columnaCapT;
	@FXML
	private TableColumn<Temporada, Integer> columnaCapV;
	@FXML
	private Button modificar;

	/*
	 * TODO Comentar, arreglar lo del foco e intentar separar las acciones ya que
	 * muchas de ellas ser�n iguales para el registro de elementos.
	 */


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Se obtiene los datos de la ventana anterior.
		PiezaAudiovisual pieza = EnvioDatos.getInstance().getDatosTransferencia();

		estado.setItems(ListasObservables.listaEstados());
		estado.setConverter(new EstadosConverter());
		
		//Se comprueba si el elemento que se ha pasado no sea serializable para eliminar el estado Siguiendo.
		if(!ComprobacionesCampos.tipoSerializable(EnvioDatos.getInstance().getTipoTransferencia())) {
			estado.getItems().remove(Estados.SIGUIENDO);
		}

		titulo.setText(pieza.getTitulo());
		id.setText(pieza.getId() + "");
		estado.getSelectionModel().select(pieza.getEstado());
		sinopsis.setText(pieza.getSinopsis());

		if (pieza instanceof Serializable) {

			Serializable serializable = (Serializable) pieza;
			
			
			tempVistas.setText(serializable.getTemporadasVistas() + "");
			
			//Añadimos un evento cuando el campo pierda el foco.
			tempVistas.focusedProperty().addListener(new ChangeListener<Boolean>() {
				
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldProperty, Boolean newProperty) {
					//Comprobamos si antes tenia el foco y ahora lo ha perdido.
					if(oldProperty && !newProperty) {
						//Si el campo una vez pierda el foco est� vac�o, se le dara un valor por defecto de 0.
						if(tempVistas.getText().isEmpty()) {
							tempVistas.setText("0");
						}
					}
					
				}
			});
			tempTotales.setText(serializable.getTemporadasTotales() + "");
			//A�adimos un evento cuando el campo pierda el foco.
			tempTotales.focusedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldProperty, Boolean newProperty) {
					//Comprobamos si antes tenia el foco y ahora lo ha perdido.
					if(oldProperty&& !newProperty) {
						//Si el campo una vez pierda el foco est� vac�o, se le dara un valor por defecto de 0.
						if(tempTotales.getText().isEmpty()) {
							tempTotales.setText("1");
						}
					}
					
				}
			});
			
			tablaTemporadas.setItems(ListasObservables.listaTemporadas(serializable.getTemporadas()));
			
			columnaTemporada.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("id"));
			columnaCapT.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("capitulosTotales"));
			columnaCapV.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("capitulosVistos"));
			
			columnaCapT.setCellFactory(TextFieldTableCell.forTableColumn(new CapitulosConverter()));
			columnaCapV.setCellFactory(TextFieldTableCell.forTableColumn(new CapitulosConverter()));
			
			ComprobacionesCampos.temporadasTextField(tempTotales);
			ComprobacionesCampos.temporadasTextField(tempVistas);

			
		} else {

			//Se bloquean los campos que pertenecen a series serializables.
			tempTotales.setDisable(true);
			tempVistas.setDisable(true);
			tablaTemporadas.setDisable(true);
		}

	}

	/**
	 * Controlador del evento del bot�n modificar.
	 * @param e
	 */
	@FXML
	private void modificarElemento(ActionEvent e) {
		
		Node nodo = (Node) e.getTarget();
		
		if(Alertas.alertaEleccion(MensajesAlertas.T_CONFIRM_MODIF, MensajesAlertas.M_CONFIRM_MODIF)) {
			PiezaAudiovisual pieza = obtenerElementos();
			boolean valoresValidos = FuncionesApoyoControladores.verificacionCampos(pieza);
			
			if(valoresValidos) {
				
				
				boolean introducido = RegistroDatosXML.introducirDatosPieza(pieza, EnvioDatos.getInstance().getTipoTransferencia());
					
				if(introducido) {
						Modificaciones.actualizarConsultasModificado(pieza);
						Alertas.alertaInformativa(MensajesAlertas.T_MODIFICADO,MensajesAlertas.M_MODIFICADO);
				}
				
				
				//Se cierra la ventana
				Stage stage = (Stage) nodo.getScene().getWindow();
				
				stage.close();
			}
		}
	
		
	}

	
	/**
	 * Controlador del ComboBox de Estados.
	 * @param e
	 */
	@FXML
	private void seleccionEstado(ActionEvent e) {
		//Se comprueba si el elemento a modificar es serializable.
		Modificaciones.comprobarEstado(estado.getSelectionModel().getSelectedItem(), tempVistas, tempTotales, tablaTemporadas, columnaCapV);
	}

	/**
	 * Controlador de evento del TextField de las Temporadas Totales.
	 * @param e
	 */
	@FXML
	private void introduccionTempT() {
		
		if(!tempTotales.getText().isEmpty()) {
			
			FuncionesApoyoControladores.introduccionTempT(tempTotales, tempVistas, tablaTemporadas,
					estado.getSelectionModel().getSelectedItem());
			
		}

	}
	
	/**
	 * M�todo para quitar foco Campos.
	 * @param e
	 */
	@FXML
	private void quitarFocoCampos(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == tempTotales) {
			if(!tempTotales.getText().isEmpty()) {
				
				quitarFocoModificacion();
				
			}else {
				
				Alertas.alertaError(MensajesAlertas.T_QUITAR_F_C, MensajesAlertas.M_QUITAR_F_C_TT);
				
			}
		}else if (obj == tempVistas) {
			
			if(!tempVistas.getText().isEmpty()) {
				
				quitarFocoModificacion();
				
			}else {
				
				Alertas.alertaError(MensajesAlertas.T_QUITAR_F_C, MensajesAlertas.M_QUITAR_F_C_TV);
				
			}
		}else if (obj == titulo) {
			
			
			if(!titulo.getText().isEmpty()) {
				
				quitarFocoModificacion();
				
			}
			
		}
	}

	/**
	 * Controlador de evento del TextField de las Temporadas Vistas.
	 * @param e
	 */
	@FXML
	private void introduccionTempV() {
		
		if(!tempVistas.getText().isEmpty()) {
			
			FuncionesApoyoControladores.introduccionTempV(tempVistas, tempTotales, tablaTemporadas,estado);
			
			
		}

	}
	


	/**
	 * Controlador de la modificaci�n de las celdas de la tabla de Capitulos Totales.
	 * @param e
	 */
	@FXML
	private void modificarCapT(CellEditEvent<Temporada, Integer> e) {
		
		boolean valorValido = FuncionesApoyoControladores.modificarCapT(e, tablaTemporadas, tempVistas, estado.getSelectionModel().getSelectedItem());

		if (valorValido) {

			quitarFocoModificacion();

		}
		
		
	}

	/**
	 * Controlador de la modificaci�n de las celdas de la tabla de Capitulos Vistas.
	 * @param e
	 */
	@FXML
	private void modificarCapV(CellEditEvent<Temporada, Integer> e) {
	
	boolean valorValido = FuncionesApoyoControladores.modificarCapV(e, tablaTemporadas,tempVistas,estado);

		if (valorValido) {
	
			//Se actualizan los capitulos vistos con la misma cantidad que los capitulos totales.
			FuncionesApoyoControladores.rellenarCapV(tablaTemporadas, tempVistas, estado.getSelectionModel().getSelectedItem());
			
			quitarFocoModificacion();
	
		}
		
	}

	@FXML
	private void quitarFocoModificacion() {
		
		FuncionesApoyoControladores.quitarFoco(raiz);
		
	}

	

	private PiezaAudiovisual obtenerElementos() {

		PiezaAudiovisual pieza = EnvioDatos.getInstance().getDatosTransferencia();
		PiezaAudiovisual nuevaPieza = null;

		int id = Integer.parseInt(this.id.getText());
		String titulo = this.titulo.getText();
		Estados estado = this.estado.getSelectionModel().getSelectedItem();
		String sinopsis = this.sinopsis.getText();

		if (pieza instanceof Serializable) {
			int tempTotales = Integer.parseInt(this.tempTotales.getText());
			int tempVistas = Integer.parseInt(this.tempVistas.getText());
			ArrayList<Temporada> temporadas = new ArrayList<Temporada>();
			temporadas.addAll(tablaTemporadas.getItems());

			if (pieza instanceof Anime) {
				nuevaPieza = new Anime(id, titulo, sinopsis, estado, tempTotales, tempVistas, temporadas);
			} else {
				nuevaPieza = new Serie(id, titulo, sinopsis, estado, tempTotales, tempVistas, temporadas);
			}
		} else {
			if (pieza instanceof PeliculaAnime) {
				nuevaPieza = new PeliculaAnime(id, titulo, sinopsis, estado);
			} else {
				nuevaPieza = new Pelicula(id, titulo, sinopsis, estado);
			}
		}
		
		return nuevaPieza;
		
	}

	
	
}
