package modulo.textoAlertas;

public final class MensajesAlertas {

	
	//Controlador Configuración.
	public static final String TITULO_EXAMINADOR = "Elige la ubicación donde almacenar los datos.";
	
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
	public static final String T_TITULO_VACIO = "Titulo vacío";
	public static final String M_TITULO_VACIO = "No has introducido un título.";
	public static final String T_ERROR_TT = "Error Temporadas Totales";
	public static final String M_ERROR_TT = "No puede haber menos de una temporada.";
	public static final String T_ERROR_TV = "Error Temporadas Vistas";
	public static final String T_COMP_CAPS = "Error Capitulos Vistos de las Temporadas Vistas";
	public static final String M_COMP_CAPS = "Hay menos temporadas con todos los Capitulos Vistos que Temporadas Vistas.";
	public static final String M_COMP_MAS_CAPS = "Hay m�s temporadas con todos los Capitulos Vistos que Temporadas Vistas.";
}

