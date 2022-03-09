package controlador;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import modelo.clases.*;
import modelo.converters.*;
import modelo.enums.*;
import modelo.envioDatos.EnvioDatos;
import modelo.funciones.*;
import modelo.funcionesXML.OpcionesDirectorioXML;
import modulo.alertas.Alertas;
import modulo.listas.ListasObservables;
import modulo.textoAlertas.MensajesAlertas;

public class ControladorPrincipal implements Initializable {

	// Creaci�n de elementos Consulas.
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
	
	// Creaci�n de elementos Registro.
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
	
	// Creaci�n de elementos donde mostrar la seleccion.
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
		
		OpcionesDirectorioXML.creacionArchivos();

		//A�adimos los elemento que mostrar en los ComboBox y le asignamos sus converters.
		comboEstadosConsulta.setItems(ListasObservables.listaEstados());
		comboEstadosConsulta.setConverter(new EstadosConverter());

		comboTiposConsulta.setItems(ListasObservables.listaTipos());
		comboTiposConsulta.setConverter(new TiposConverter());
		
		comboEstadosRegistro.setItems(ListasObservables.listaEstados());
		comboEstadosRegistro.setConverter(new EstadosConverter());

		comboTiposRegistro.setItems(ListasObservables.listaTipos());
		comboTiposRegistro.setConverter(new TiposConverter());
		
		//A�adimos un evento cuando el campo pierda el foco.
		tempTRegistro.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldProperty, Boolean newProperty) {
				//Comprobamos si antes tenia el foco y ahora lo ha perdido.
				if(oldProperty && !newProperty) {
					//Si el campo una vez pierda el foco est� vac�o, se le dara un valor por defecto de 0.
					if(tempTRegistro.getText().isEmpty()) {
						tempTRegistro.setText("1");
					}
				}
				
			}
		});
		
		//A�adimos un evento cuando el campo pierda el foco.
		tempVRegistro.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldProperty, Boolean newProperty) {
				//Comprobamos si antes tenia el foco y ahora lo ha perdido.
				if(oldProperty&& !newProperty) {
					//Si el campo una vez pierda el foco est� vac�o, se le dara un valor por defecto de 0.
					if(tempVRegistro.getText().isEmpty()) {
						tempVRegistro.setText("0");
					}
				}
				
			}
		});
		
		//Asignamos instrucciones para la pesta�a registro.
		
		colTempRegistro.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("id"));
		colCapTRegistro.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("capitulosTotales"));
		colCapVRegistro.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("capitulosVistos"));
		
		colCapTRegistro.setCellFactory(TextFieldTableCell.forTableColumn(new CapitulosConverter()));
		colCapVRegistro.setCellFactory(TextFieldTableCell.forTableColumn(new CapitulosConverter()));

		ComprobacionesCampos.temporadasTextField(tempTRegistro);
		ComprobacionesCampos.temporadasTextField(tempVRegistro);
		
		//Quitamos el texto que aparece en el medio de la tabla cuando esta vac�a.
		tablaTempRegistro.setPlaceholder(new Label(null));
		tablaTemporadas.setPlaceholder(new Label(null));
		
	}

	
	//Controladores varias pesta�as.
	
	/**
	 * Controlador del ComboBox encargado de elegir el tipo de Pieza Auidiovisual.
	 * @param e
	 */
	@FXML
	private void eleccionTipo(ActionEvent e) {
		
		Object obj = e.getSource();
		
		if(obj == comboTiposConsulta) {
			
			botonModificador.setDisable(true);
			
			if(comboTiposConsulta.getSelectionModel().getSelectedItem() != null) {
				
				Consultas.elegirTipo(comboTiposConsulta, tituloConsulta, comboEstadosConsulta, listaElementosObtenidos);
				
			}
		}else {
			
			if( comboTiposRegistro.getSelectionModel().getSelectedItem() != null) {
				
				//Se llama al m�todo para que prepare los siguientes campos del formulario.
				Registros.CambioTipo(comboTiposRegistro,comboEstadosRegistro, tempTRegistro, tempVRegistro, tablaTempRegistro, tituloRegistro,
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

		Object obj = e.getSource();
		
		if(obj == comboEstadosConsulta) {
			
			botonModificador.setDisable(true);
			
			Consultas.eleccionEstado(comboEstadosConsulta, listaElementosObtenidos, tituloConsulta);
			
		}else {
			//Se comprueba que el valor del estado no sea nulo, para que al resetear
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
	 * Controladores pesta�a consultas.		*
	 * 										*
	 * -------------------------------------*
	 */
	
	/**
	 * Controlador del TextField del Titulo encargado de filtrar la lista de elementos
	 * a partir del titulo.
	 * @param e
	 */
	@FXML
	private void comprobacionTitulo() {

		botonModificador.setDisable(true);
		
		Consultas.comprobacionTitulo(tituloConsulta, listaElementosObtenidos);

	}

	/**
	 * Controlador del evento al clicar sobre un elemento de la lista.
	 * @param e
	 */
	@FXML
	private void obtenerElementoLista() {
		
		Consultas.obtenerElementoLista(listaElementosObtenidos, botonModificador, tituloSeleccion,
				idSeleccion, estadoSeleccion, sinopsisSeleccion, tempTSeleccion, tempVSeleccion,
				tablaTemporadas, columnaTemporadas, columnaCapT,columnaCapV);

	}
	

	/**
	 * M�todo que se encarga de controlar el evento al pulsar el bot�n par modificar
	 * el campo seleccionado en la ListView.
	 * 
	 * @param e
	 */
	@FXML
	private void modificarElementos(ActionEvent e) {
		
		Node nodo = (Node) e.getTarget();
		
		// Obtenemos el stage con el que estamos trabajando.
		Stage stage = (Stage) nodo.getScene().getWindow();
		// Ocultamos el stage para que el usuario no pueda realizar acciones en el.
		stage.hide();
		// Obtenemos la instancia de la clase que no servir� para transferir datos.
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
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/vista/VentanaModificacion.fxml"));
			// Creamos la escena pasandole el AnchorPane como base.
			Scene scene = new Scene(root);
			// Modificamos el t�tulo.
			modificadorStage.setTitle("Modificar");
			// Le asignamos un m�nimo al escalado de la altura y longitud.
			modificadorStage.setMinWidth(900);
			modificadorStage.setMinHeight(480);
			// Asignamos la escena al stage.
			modificadorStage.setScene(scene);
			// Mostramos el stage.
			modificadorStage.show();
			/*
			 * A�adimos un evento de ventana al cerrar la nueva ventana(desde la X de la ventana) para que se vuelva a
			 * mostrar la ventana principal.
			 */
			modificadorStage.setOnCloseRequest(event -> stage.show());
			/*
			 * A�adimos un evento para que se muestr la ventana principal al cerrar(Llamando al .close()) o al esconder la ventana.
			 */
			modificadorStage.setOnHidden(event -> {
				stage.show();
				int elemento = listaElementosObtenidos.getSelectionModel().getSelectedIndex();
				listaElementosObtenidos.getItems().set(elemento, EnvioDatos.getInstance().getDatosTransferencia());
				listaElementosObtenidos.getSelectionModel().select(elemento);
				obtenerElementoLista();
				modificadorStage.close();
			});

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	
	/**
	 * M�todo encargado de controlar el evento al pusar el bot�n de resetear, que vaciar� todos los campos
	 * y deshabilitar� los que deban estarlo para que quede como al inicio.
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
	 * Controlador para quitar el foco en la pesta�a Consultas.
	 */
	@FXML
	private void quitarFocoConsulta() {
		
		FuncionesApoyoControladores.quitarFoco(raizConsulta);
	
	}
	
	
	/*
	 * -------------------------------------*
	 * 										*
	 * Controladores pesta�a registros.		*
	 * 										*
	 * -------------------------------------*
	 */
	
	//TODO COMENTAR metodos registro.
	
	@FXML
	private void introduccionTempTRegistro() {
		
		/*
		 * Si el valor introducido en el TextField es v�lido se le quita el foco al
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
		 * Se obtiene el objeto que ha activado el evento, se comparan y si el campo est� vac�o mostrar� una
		 * ventana emergente avisando que el campo est� vac�o y no perder� el foco. Solo perder� el foco si el
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
	 * Controlador encargado de la introducci�n de valores en el campo Temporadas Vistas.
	 */
	@FXML
	private void introduccionTempVRegistro() {
		
		if(!tempVRegistro.getText().isEmpty()) {
		
			FuncionesApoyoControladores.introduccionTempV(tempVRegistro, tempTRegistro,tablaTempRegistro,comboEstadosRegistro);
			
		}
	}
	
	
	
	/**
	 * Controlador encargado de la introducci�n de valores en el campo Temporadas Vistas.
	 */
	@FXML
	private void introduccionCapT(CellEditEvent<Temporada, Integer> e) {
		
		boolean valorValido = FuncionesApoyoControladores.modificarCapT(e, tablaTempRegistro, tempVRegistro,
				comboEstadosRegistro.getSelectionModel().getSelectedItem());

		if (valorValido) {
			
			quitarFocoRegistro();

		}
		
	}
	
	@FXML
	private void introduccionCapV(CellEditEvent<Temporada, Integer> e) {
		
		boolean valorValido = FuncionesApoyoControladores.modificarCapV(e, tablaTempRegistro, tempVRegistro,
				comboEstadosRegistro);
		
		if(valorValido) {
			
			FuncionesApoyoControladores.quitarFoco(raizRegistro);
			
		}
		
	}
	
	
	
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
	
	
	@FXML
	private void registrarDatos() {
		
		if(Alertas.alertaEleccion("Registro Datos", "�Seguro que quieres ingresar los datos introducidos?")) {
			
			boolean datosIntroducidos = FuncionesApoyoControladores.registrar(comboTiposRegistro, tituloRegistro, comboEstadosRegistro, sinopsisRegistro,
					tempTRegistro, tempVRegistro, tablaTempRegistro);
			
			
			if(datosIntroducidos) {
				
				Alertas.alertaInformativa("Registro Exitoso", "Se han registrado los datos con �xito.");
				
				resetearRegistro();
				
			}
		}
		
	}
	
	@FXML
	private void quitarFocoRegistro() {
		FuncionesApoyoControladores.quitarFoco(raizRegistro);
	}
	
	

}
