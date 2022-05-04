package com.retur.main.modelo.alertas.texto;

public final class MensajesAlertas {

	public static final String T_ERROR_GENERICO = "Ha ocurrido un error.";
	
	//Controlador Configuración.
	public static final String TITULO_EXAMINADOR = "Elige la ubicación donde almacenar los datos.";
	
	//Controlador Principal.
	public static final String T_ERROR_DIRECTORIO = "Error de ubicación";
	
	//Controlador Registro.
	public static final String T_PREGUNTA_REGISTRO = "Registro Datos";
	public static final String M_PREGUNTA_REGISTRO = "¿Seguro que quieres ingresar los datos introducidos?";
	public static final String T_REGISTRO_EXITOSO = "Registro Exitoso";
	public static final String M_REGISTRO_EXITOSO = "Se han registrado los datos con éxito.";
	
	//Controladores Registro y Modificación.
	public static final String T_QUITAR_F_C = "Error campo vacío";
	public static final String M_QUITAR_F_C_TT = "No puedes dejar vacío el campo Temporadas Totales.";
	public static final String M_QUITAR_F_C_TV = "No puedes dejar vacío el campo Temporadas Vistas.";
	public static final String M_QUITAR_F_C_TITULO = "No puedes dejar el campo Titulo vacío";
	public static final String T_ERROR_GUARDAR_DATOS = "Error Guardar Datos";
	public static final String M_ERROR_GUARDAR_DATOS = "Ha ocurrido un error al guardar los datos.";
	
	//Controlador Modificación.
	public static final String T_CONFIRM_MODIF = "Confirmar Modificación";
	public static final String M_CONFIRM_MODIF = "¿Seguro que quieres guardar estas modificaciones?";
	public static final String T_MODIFICADO = "Dato Modificado";
	public static final String M_MODIFICADO = "Los datos se han modificado con éxito.";
	
	//Funciones apoyo controladores.
	public static final String T_INTRO_TT = "Temporadas necesarias.";
	public static final String M_INTRO_TT = "Tienes que introducir al menos 1 temporada.";
	public static final String T_INTRO_TV = "Cantidad no válida";
	public static final String M_INTRO_TV = "No puede haber más temporadas vistas que totales.";
	public static final String T_VALOR_INV_CAPS = "Valor no válido"; 
	public static final String M_VALOR_INV_CAPS = "Solo puedes introducir números.";
	public static final String T_INTRO_CAPV = "Error Capitulos";
	public static final String M_INTRO_CAPV = "No puedes introducir más capitulos vistos que los totales.";
	
	//Funciones apoyo controladores (comprobaciones).

	public static final String T_ERROR_CAMPO = "Campo Invalido";
	public static final String M_TITULO_VACIO = "No has introducido un título.";
	public static final String M_ERROR_TT = "No puede haber menos de una temporada.";
	public static final String M_MENOS_CAPS = "Hay menos temporadas con todos los Capitulos Vistos que Temporadas Vistas.";
	public static final String M_MAS_CAPS = "Hay más temporadas con todos los Capitulos Vistos que Temporadas Vistas.";
	public static final String M_MIN_CAPST = "El valor de los capitulos totales tiene que ser mínimo de 1";
	public static final String M_MAX_CAPST = "El valor de los capitulos vistos no puede ser mayor que los capitulos totales.";


}

