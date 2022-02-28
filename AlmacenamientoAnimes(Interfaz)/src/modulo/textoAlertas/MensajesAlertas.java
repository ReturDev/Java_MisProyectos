package modulo.textoAlertas;

public final class MensajesAlertas {

	
	//Controladores Registro y Modificaci�n.
	public static final String T_QUITAR_F_C = "Error campo vac�o";
	public static final String M_QUITAR_F_C_TT = "No puedes dejar vac�o el campo Temporadas Totales.";
	public static final String M_QUITAR_F_C_TV = "No puedes dejar vac�o el campo Temporadas Vistas.";
	public static final String M_QUITAR_F_C_TITULO = "No puedes dejar el campo Titulo vac�o";
	
	//Controlador Modificaci�n.
	public static final String T_CONFIRM_MODIF = "Confirmar Modificaci�n";
	public static final String M_CONFIRM_MODIF = "�Seguro que quieres guardar estas modificaciones?";
	public static final String T_MODIFICADO = "Dato Modificado";
	public static final String M_MODIFICADO = "Los datos se han modificado con �xito.";
	
	//Funciones apoyo controladores.
	public static final String T_INTRO_TT = "Temporadas necesarias.";
	public static final String M_INTRO_TT = "Tienes que introducir al menos 1 temporada.";
	public static final String T_INTRO_TV = "Cantidad no v�lida";
	public static final String M_INTRO_TV = "No puede haber m�s temporadas vistas que totales.";
	public static final String T_VALOR_INV_CAPS = "Valor no v�lido"; 
	public static final String M_VALOR_INV_CAPS = "Solo puedes introducir n�meros.";
	public static final String T_INTRO_CAPV = "Error Capitulos";
	public static final String M_INTRO_CAPV = "No puedes introducir m�s capitulos vistos que los totales.";
	//Funciones apoyo controladores (comprobaciones).
	public static final String T_TITULO_VACIO = "Titulo vac�o";
	public static final String M_TITULO_VACIO = "No has introducido un t�tulo.";
	public static final String T_ERROR_TT = "Error Temporadas Totales";
	public static final String M_ERROR_TT = "No puede haber menos de una temporada.";
	public static final String T__ERROR_TV = "Error Temporadas Vistas";
	public static final String T_COMP_CAPS = "Error Capitulos Vistos de las Temporadas Vistas";
	public static final String M_COMP_CAPS = "Hay menos temporadas con todos los Capitulos Vistos que Temporadas Vistas.";
	public static final String M_COMP_MAS_CAPS = "Hay m�s temporadas con todos los Capitulos Vistos que Temporadas Vistas.";
}

