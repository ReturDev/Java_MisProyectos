package com.retur.main.controlador;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

import javax.xml.transform.TransformerException;

import com.retur.main.modelo.alertas.Alertas;
import com.retur.main.modelo.alertas.texto.MensajesAlertas;
import com.retur.main.modelo.convertidores.*;
import com.retur.main.modelo.elementos.*;
import com.retur.main.modelo.enums.*;
import com.retur.main.modelo.envio.datos.EnvioDatos;
import com.retur.main.modelo.excepciones.CampoInvalidoException;
import com.retur.main.modelo.excepciones.ObraYaRegistradaException;
import com.retur.main.modelo.funciones.*;
import com.retur.main.modelo.funciones.xml.ObtencionDatosXML;
import com.retur.main.modelo.funciones.xml.OpcionesDirectorioXML;
import com.retur.main.modelo.funciones.xml.RegistroDatosXML;
import com.retur.main.modelo.listas.ListasObservables;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ControladorPrincipal implements Initializable {

	//Menú
	@FXML
	private MenuItem menuOpcionesArchivo;
	
	//Pestañas
	@FXML
	private Tab tabConsultas;
	@FXML
	private Tab tabRegistrar;
	
	
	// Creación de elementos Consulas.
	@FXML
	private GridPane raizConsulta;
	@FXML
	private ComboBox<TiposPiezasAudiovisuales> comboTiposConsulta;
	@FXML
	private ComboBox<Estados> comboEstadosConsulta;
	@FXML
	private TextField tituloConsulta;
	@FXML
	private Button resetConsulta;
	
	// Creación de elementos Registro.
	@FXML
	private AnchorPane raizRegistro;
	@FXML
	private ComboBox<TiposPiezasAudiovisuales> comboTiposRegistro;
	@FXML
	private TextField tituloRegistro;
	@FXML
	private ComboBox<Estados> comboEstadosRegistro;
	@FXML
	private TextField tempTRegistro;
	@FXML
	private TextField tempVRegistro;
	@FXML
	private TableView<Temporada> tablaTempRegistro;
	@FXML
	private TableColumn<Temporada, Integer> colTempRegistro;
	@FXML
	private TableColumn<Temporada, Integer> colCapTRegistro;
	@FXML
	private TableColumn<Temporada, Integer> colCapVRegistro;
	@FXML
	private TextArea sinopsisRegistro;
	@FXML
	private Button resetRegistro;
	@FXML
	private Button botonRegistrar;
	
	// Creación de elementos donde mostrar la seleccion.
	@FXML
	private TextField tituloSeleccion;
	@FXML
	private TextField idSeleccion;
	@FXML
	private TextField estadoSeleccion;
	@FXML
	private TextField tempTSeleccion;
	@FXML
	private TextField tempVSeleccion;
	@FXML
	private ListView<PiezaAudiovisual> listaElementosObtenidos;
	@FXML
	private TableView<Temporada> tablaTemporadas;
	@FXML
	private TableColumn<Temporada, Integer> columnaTemporadas;
	@FXML
	private TableColumn<Temporada, Integer> columnaCapT;
	@FXML
	private TableColumn<Temporada, Integer> columnaCapV;
	@FXML
	private TextArea sinopsisSeleccion;
	@FXML
	private Button botonModificador;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			
			/*
			 * Se llama al método encargado de ver si los archivos están creados y si las ubicaciones almacenadas
			 * en los archivos es correcta. Si la ubiación almacenada en el archivo configuración no existe, lanzará
			 * una excepción que al capturarse mostrará al usuario un mensaje con el error.
			 */
			OpcionesDirectorioXML.creacionArchivos();
			ObtencionDatosXML.obtenerRaiz();
			tabConsultas.setDisable(false);
			tabRegistrar.setDisable(false);
			
		} catch (FileNotFoundException e) {
			
			Alertas.alertaError(MensajesAlertas.T_ERROR_DIRECTORIO, e.getMessage());
			
		}

		//Añadimos los elementos que mostrar en los ComboBox y le asignamos sus convertidores.
		comboEstadosConsulta.setItems(ListasObservables.listaEstados());
		comboEstadosConsulta.setConverter(new EstadosConverter());

		comboTiposConsulta.setItems(ListasObservables.listaTipos());
		comboTiposConsulta.setConverter(new TiposConverter());
		
		comboEstadosRegistro.setItems(ListasObservables.listaEstados());
		comboEstadosRegistro.setConverter(new EstadosConverter());

		comboTiposRegistro.setItems(ListasObservables.listaTipos());
		comboTiposRegistro.setConverter(new TiposConverter());
		
		//Añadimos un evento para verificar cuando el campo pierda el foco.
		ComprobacionesCampos.listenerCambioFocoTempTotales(tempTRegistro);
		
		//Añadimos un evento cuando el campo pierda el foco.
		ComprobacionesCampos.listenerCambioFocoTempVistas(tempVRegistro);
		
		//Asignamos instrucciones para la pestaña registro.
		
		colTempRegistro.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("id"));
		colCapTRegistro.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("capitulosTotales"));
		colCapVRegistro.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("capitulosVistos"));
		
		colCapTRegistro.setCellFactory(TextFieldTableCell.forTableColumn(new CapitulosConverter()));
		colCapVRegistro.setCellFactory(TextFieldTableCell.forTableColumn(new CapitulosConverter()));

		ComprobacionesCampos.listenerTextoTemporadasTextField(tempTRegistro);
		ComprobacionesCampos.listenerTextoTemporadasTextField(tempVRegistro);
		
		//Quitamos el texto que aparece en el medio de la tabla cuando esta vacía.
		tablaTempRegistro.setPlaceholder(new Label(null));
		tablaTemporadas.setPlaceholder(new Label(null));
		
	}
	
	/**
	 * Abre la Ventana de Configuración.
	 */
	@FXML
	private void abrirConfiguracion() {
		
		// Obtenemos el stage con el que estamos trabajando.
		Stage stage = (Stage) raizConsulta.getScene().getWindow();
		
		// Ocultamos el stage para que el usuario no pueda realizar acciones en él.
		stage.hide();

		try {
			
			// Creamos el stage de la nueva ventana.
			Stage configStage = new Stage();
			
			// Obtenemos el AnchorPane de la nueva ventana a traves del archivo FXML.
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("../vista/VentanaConfiguracion.fxml"));
			// Creamos la escena pasandole el AnchorPane como base.
			Scene scene = new Scene(root);
			// Modificamos el título.
			configStage.setTitle("Configuración");
			// Bloqueamos la modificiación del tamaño de la ventana.
			configStage.setResizable(false);
			// Asignamos la escena al stage.
			configStage.setScene(scene);
			// Mostramos el stage.
			configStage.show();
			/*
			 * Añadimos un evento de ventana al cerrar la nueva ventana(desde la X de la ventana) para que se vuelva a
			 * mostrar la ventana principal.
			 */
			configStage.setOnCloseRequest(event -> stage.show());
			/*
			 * Añadimos un evento para que se muestre la ventana principal al cerrar(Llamando al .close()) o al esconder la ventana.
			 */
			configStage.setOnHidden(event -> {
				stage.show();
				configStage.close();
				//Comprueba si el directorio que hay almacenado es válido. Si lo es se desbloquearán las pestañas de Consulta y Registro.
				if(Files.isDirectory(OpcionesDirectorioXML.getRutaArchivo().getParent())) {
					ObtencionDatosXML.obtenerRaiz();
					tabConsultas.setDisable(false);
					tabRegistrar.setDisable(false);
				}
			});

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	
	//Controladores varías pestañas.
	
	/**
	 * Controlador del ComboBox encargado de elegir el tipo de Pieza Audiovisual.
	 * @param e
	 */
	@FXML
	private void eleccionTipo(ActionEvent e) {
		
		//Almacena el elemento que ha activado el evento.
		Object obj = e.getSource();
		
		//Comprueba que comboBox activó el evento.
		if(obj == comboTiposConsulta) {
			
			//Deshabilita el botón para modificar el elemento.
			botonModificador.setDisable(true);
			
			/*
			 * Comprueba que haya algun elemento seleccionado en el ComboBox (necesario para evitar el error
			 * al resetear los campos mediante el botón de reseteo.)
			 */
			if(comboTiposConsulta.getSelectionModel().getSelectedItem() != null) {
				
				//Llama el método para que realice los cambios necesarios para obtener los elementos del tipo que se quiere.
				Consultas.elegirTipo(comboTiposConsulta, comboEstadosConsulta, listaElementosObtenidos);
				
				// Activamos los campos para que sea posible rellenarlos.
				tituloConsulta.setDisable(false);
				comboEstadosConsulta.setDisable(false);
				// Borramos los datos de los campos si ya estaban activos.
				tituloConsulta.setText("");
				// Colocamos el combobox de estados por defecto.
				comboEstadosConsulta.valueProperty().set(null);
				
			}
			
		}else {
			
			/*
			 * Comprueba que haya algun elemento seleccionado en el ComboBox (necesario para evitar el error
			 * al resetear los campos mediante el botón de reseteo.)
			 */
			if(comboTiposRegistro.getSelectionModel().getSelectedItem() != null) {
				
				//Se llama al método para que prepare los siguientes campos del formulario.
				Registros.cambioTipo(comboTiposRegistro,comboEstadosRegistro, tempTRegistro, tempVRegistro, tablaTempRegistro, tituloRegistro,
						sinopsisRegistro,botonRegistrar);
				
			}
			
		}
		
	}

	/**
	 * Controlador del ComboBox encargado de elegir el Estado de la obra.
	 * @param e
	 */
	@FXML
	private void eleccionEstado(ActionEvent e) {

		//Almacena el elemento que ha activado el evento.
		Object obj = e.getSource();
		
		//Comprueba que elemento activó el evento.
		if(obj == comboEstadosConsulta) {
			
			//Deshabilita el botón para modificar el elemento.
			botonModificador.setDisable(true);
			
			//Se llama al método para que prepare los siguientes campos del formulario.
			Consultas.eleccionEstado(comboEstadosConsulta, listaElementosObtenidos, tituloConsulta);
			
		}else {
			
			//Comprueba que haya algun elemento seleccionado en el ComboBox( necesario para el botón resetear).
			if(comboEstadosRegistro.getSelectionModel().getSelectedItem() != null) {
				
				//Comprobamos si el tipo seleccionado es una instancia de Serializable.
				boolean serializable = ComprobacionesCampos.tipoSerializable(comboTiposRegistro.getSelectionModel().getSelectedItem());
				
				//Desbloqueamos los campos adecuados dependiendo si la pieza es serializable.
				Registros.prepararCamposTSerializable(serializable,tempTRegistro, tempVRegistro, tablaTempRegistro,comboEstadosRegistro.getSelectionModel().getSelectedItem(),
						colCapVRegistro,botonRegistrar);
				
			}
			
		}
		

	}

	
	
	/*
	 * -------------------------------------*
	 * 										*
	 * Controladores pestaña consultas.		*
	 * 										*
	 * -------------------------------------*
	 */
	
	/**
	 * Controlador del TextField del Título encargado de filtrar la lista de elementos
	 * a partir del título.
	 */
	@FXML
	private void comprobacionTitulo() {

		//Habilita el botón para modificar el elemento.
		botonModificador.setDisable(true);
		
		//Comprueba el título introducido con la lista de elementos.
		Consultas.comprobacionTitulo(tituloConsulta, listaElementosObtenidos);

	}

	/**
	 * Controlador del evento al clicar sobre un elemento de la lista.
	 */
	@FXML
	private void obtenerElementoLista() {
		
		// Obtenemos el elemento seleccionado en la lista.
		PiezaAudiovisual pieza = listaElementosObtenidos.getSelectionModel().getSelectedItem();
		// Comprobamos que el objeto no sea null, es decir, que haya alg�n elemento
		// seleccionado.
		if (pieza != null) {
			
			Consultas.mostrarElementoSeleccionado(pieza, tituloSeleccion, idSeleccion, estadoSeleccion,
					sinopsisSeleccion, tempTSeleccion, tempVSeleccion, tablaTemporadas,
					columnaTemporadas, columnaCapT, columnaCapV);
			botonModificador.setDisable(false);
			
		}

	}
	

	/**
	 * Método que se encarga de controlar el evento al pulsar el botón para modificar
	 * el elemento seleccionado en la Listview.
	 * @param e
	 */
	@FXML
	private void modificarElementos(ActionEvent e) {
		
		Node nodo = (Node) e.getTarget();
		
		// Obtenemos el stage con el que estamos trabajando.
		Stage stage = (Stage) nodo.getScene().getWindow();
		// Ocultamos el stage para que el usuario no pueda realizar acciones en el.
		stage.hide();
		// Obtenemos la instancia de la clase que nos servirá para transferir datos.
		EnvioDatos datos = EnvioDatos.getInstance();
		// Obtenemos la pieza seleccionada en la ListView.
		PiezaAudiovisual pieza = listaElementosObtenidos.getSelectionModel().getSelectedItem();
		// Le asignamos los datos a la variable de la instancia de la clase.
		datos.setDatosTransferencia(pieza);
		datos.setTipoTransferencia(comboTiposConsulta.getSelectionModel().getSelectedItem());

		try {
			// Creamos el stage de la nueva ventana.
			Stage modificadorStage = new Stage();
			// Obtenemos el AnchorPane de la nueva ventana a traves del archivo FXML.
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("../vista/VentanaModificacion.fxml"));
			// Creamos la escena pasandole el AnchorPane como raíz.
			Scene scene = new Scene(root);
			// Modificamos el título.
			modificadorStage.setTitle("Modificar");
			// Le asignamos un mínimo al escalado de la altura y longitud.
			modificadorStage.setMinWidth(900);
			modificadorStage.setMinHeight(480);
			// Asignamos la escena al stage.
			modificadorStage.setScene(scene);
			// Mostramos el stage.
			modificadorStage.show();
			/*
			 * Añadimos un evento de ventana al cerrar la nueva ventana(desde la X de la ventana) para que se vuelva a
			 * mostrar la ventana principal.
			 */
			modificadorStage.setOnCloseRequest(event -> stage.show());
			/*
			 * Añadimos un evento para que se muestre la ventana principal al cerrar(Llamando al .close()) o al esconder la ventana.
			 */
			modificadorStage.setOnHidden(event -> {
				stage.show();
				//Se actualiza la ventana consulta al salir de la ventana de modificación.
				int posElemento = listaElementosObtenidos.getSelectionModel().getSelectedIndex();
				listaElementosObtenidos.getItems().set(posElemento, EnvioDatos.getInstance().getDatosTransferencia());
				listaElementosObtenidos.getSelectionModel().select(posElemento);
				//Se obtiene el elemento de la lista actualizado para que se muestre en la ventana de datos.
				obtenerElementoLista();
				modificadorStage.close();
				
			});

		} catch (Exception ex) {
			
			ex.printStackTrace();
			
		}

	}
	
	
	/**
	 * Método encargado de controlar el evento al pulsar el botón de resetear, que vaciará todos los campos
	 * y deshabilitará los que deban estarlo para que quede como al inicio.
	 * @param e
	 */
	@FXML
	private void resetearConsultas() {
		
		Consultas.setTipo(null);
		Consultas.setEstado(null);
		Consultas.setTitulo(null);
		
		comboTiposConsulta.valueProperty().set(null);
		tituloConsulta.setDisable(true);
		tituloConsulta.setText("");
		comboEstadosConsulta.setDisable(true);
		comboEstadosConsulta.valueProperty().set(null);
		listaElementosObtenidos.setItems(null);
		tituloSeleccion.setText("");
		idSeleccion.setText("");
		estadoSeleccion.setText("");
		tempTSeleccion.setText("");
		tempVSeleccion.setText("");
		tablaTemporadas.setItems(FXCollections.observableArrayList());
		sinopsisSeleccion.setText("");
		
	}
	

	/**
	 * Controlador para quitar el foco en la pestaña Consultas.
	 */
	@FXML
	private void quitarFocoConsulta() {
		
		FuncionesApoyoControladores.quitarFoco(raizConsulta);
	
	}
	
	
	/*
	 * -------------------------------------*
	 * 										*
	 * Controladores pestaña registros.		*
	 * 										*
	 * -------------------------------------*
	 */
	

	
	@FXML
	private void introduccionTempTRegistro() {
		
		/*
		 * Si el valor introducido en el TextField es válido se le quita el foco al
		 * campo.
		 */
		if(!tempTRegistro.getText().isEmpty()) {
		
			FuncionesApoyoControladores.introduccionTempT(tempTRegistro, tempVRegistro, tablaTempRegistro,
					comboEstadosRegistro.getSelectionModel().getSelectedItem());
		
		}
		
	}
	
	/**
	 * Quita el foco de los campos al pulsar enter.
	 * @param e
	 */
	@FXML
	private void quitarFocoCamposR(ActionEvent e) {
		/*
		 * Se obtiene el objeto que ha activado el evento, se comparan y si el campo está vacío mostrará una
		 * ventana emergente avisando que el campo está vacío y no perderá el foco. Solo perderá el foco si el
		 * campo que activa el evento tiene contenido.
		 */
		
		Object obj = e.getSource();
		
		
		if(obj == tempTRegistro) {
			
			if(!tempTRegistro.getText().isEmpty()) {
				
				quitarFocoRegistro();
				
			}else {
				
				Alertas.alertaError(MensajesAlertas.T_QUITAR_F_C, MensajesAlertas.M_QUITAR_F_C_TT);
				
			}
			
		}else if (obj == tempVRegistro) {
			
			if(!tempVRegistro.getText().isEmpty()) {
				
				quitarFocoRegistro();
				
			}else {
				
				Alertas.alertaError(MensajesAlertas.T_QUITAR_F_C, MensajesAlertas.M_QUITAR_F_C_TV);
				
			}
		}else if (obj == tituloRegistro) {
			
			if(!tituloRegistro.getText().isBlank()) {
				
				quitarFocoRegistro();
				
			}else {
				
				Alertas.alertaError(MensajesAlertas.T_QUITAR_F_C, MensajesAlertas.M_QUITAR_F_C_TITULO);
				
			}
			
		}
		
	}
	
	
	/**
	 * Controlador encargado de la introduccin de valores en el campo Temporadas Vistas.
	 */
	@FXML
	private void introduccionTempVRegistro() {
		
		//Comprueba que el campo no esté vacío.
		if(!tempVRegistro.getText().isEmpty()) {
			//Se hacen las comprobaciones pertinentes a través del método.
			FuncionesApoyoControladores.introduccionTempV(tempVRegistro, tempTRegistro,tablaTempRegistro,comboEstadosRegistro);
			
		}
	}
	
	
	
	/**
	 * Controlador encargado de la introducción de valores en las celda de Capitulos Totales.
	 */
	@FXML
	private void introduccionCapT(CellEditEvent<Temporada, Integer> e) {
		
		//Almacena si el valor introducido en los Capitulos Totales es válido.
		boolean valorValido = FuncionesApoyoControladores.modificarCapT(e, tablaTempRegistro, tempVRegistro,
				comboEstadosRegistro.getSelectionModel().getSelectedItem());

		//Si el valor es válido quita el foco.
		if (valorValido) {
			
			quitarFocoRegistro();

		}
		
	}
	
	
	/**
	 * Controlador encargado de la introducción de valores en las celda de Capitulos Vistos.
	 */
	@FXML
	private void introduccionCapV(CellEditEvent<Temporada, Integer> e) {
		
		//Almacena si el valor introducido en los Capitulos Totales es válido.
		boolean valorValido = FuncionesApoyoControladores.modificarCapV(e, tablaTempRegistro, tempVRegistro,
				comboEstadosRegistro);
		
		//Si el valor es válido quita el foco.
		if(valorValido) {
			
			FuncionesApoyoControladores.quitarFoco(raizRegistro);
			
		}
		
	}
	
	
	/**
	 * Controlador del botón encargado de resetear los valores de los campos.
	 */
	@FXML
	private void resetearRegistro() {
		

		comboTiposRegistro.valueProperty().set(null);
		
		tituloRegistro.setDisable(true);
		tituloRegistro.setText(null);
		
		comboEstadosRegistro.setDisable(true);
		comboEstadosRegistro.valueProperty().set(null);
		
		tempTRegistro.setDisable(true);
		tempTRegistro.setText("");
		tempVRegistro.setDisable(true);
		tempVRegistro.setText("");
		
		tablaTempRegistro.setItems(FXCollections.observableArrayList());
		
		sinopsisRegistro.setText("");
		botonRegistrar.setDisable(true);
	}
	
	
	/**
	 * Controlador del botón encargado de registrar el elemento con los datos introducidos.
	 */
	@FXML
	private void registrarDatos() {
		
		//Muestra una ventana emergente preguntando al usuario si está seguro de guardar los datos del elemento introducido.
		if(Alertas.alertaEleccion(MensajesAlertas.T_PREGUNTA_REGISTRO, MensajesAlertas.M_PREGUNTA_REGISTRO)) {
			
			try {
				//Registra los datos introducidos.
				PiezaAudiovisual pieza = FuncionesApoyoControladores.crearPiezaRegistro(comboTiposRegistro, tituloRegistro, comboEstadosRegistro, sinopsisRegistro,
						tempTRegistro, tempVRegistro, tablaTempRegistro);
				
				FuncionesApoyoControladores.verificacionCampos(pieza, comboTiposRegistro.getSelectionModel().getSelectedItem());
				RegistroDatosXML.introducirDatosPieza(pieza, comboTiposRegistro.getSelectionModel().getSelectedItem());
				
				//Se mostrará una ventana emergente avisando al usuario que los datos se han registrado con éxito.
				Alertas.alertaInformativa(MensajesAlertas.T_REGISTRO_EXITOSO, MensajesAlertas.M_REGISTRO_EXITOSO);
					
				//Borra todos los datos del registro.
				resetearRegistro();
				
			} catch (TransformerException e) {
				
				//En caso de que ocurriera un error al registrar el elemento, se mostrará una ventana emergente al usuario.
				Alertas.alertaError(MensajesAlertas.T_ERROR_GUARDAR_DATOS, MensajesAlertas.M_ERROR_GUARDAR_DATOS + e.getMessage());
				
			} catch (CampoInvalidoException e) {
				
				Alertas.alertaError(MensajesAlertas.T_ERROR_CAMPO, e.getMessage());
				
			} catch (ObraYaRegistradaException e) {
				
				Alertas.alertaError(MensajesAlertas.T_TITULO_REPETIDO, e.getMessage());
				
			}
			
		}
		
	}
	
	/**
	 * Controlador para quitar el foco de los elementos en la pestaña registros.
	 */
	@FXML
	private void quitarFocoRegistro() {
		
		FuncionesApoyoControladores.quitarFoco(raizRegistro);
		
	}

}
