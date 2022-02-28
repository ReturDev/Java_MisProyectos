package aplicacion.interaccionXML;

public class ComprobacionesInteracciones {

	private static final String E_PENDIENTE = "PENDIENTE";
	private static final String E_VISTO = "VISTO";
	private static final String E_SIGUIENDO = "SIGUIENDO";
	private static final String E_DROPEADO = "DROPEADO";
	private static final char E_PENDIENTE_CHAR = 'P';
	private static final char E_VISTO_CHAR = 'V';
	private static final char E_SIGUIENDO_CHAR = 'S';
	private static final char E_DROPEADO_CHAR = 'D';

	protected static boolean comprobarEstadoChar(char estado) {
		boolean estadoCorrecto = false;
		if (estado == E_PENDIENTE_CHAR || estado == E_VISTO_CHAR || estado == E_SIGUIENDO_CHAR
				|| estado == E_DROPEADO_CHAR) {
			estadoCorrecto = true;
		} else {
			System.out.println("Valor no válido.");
		}
		return estadoCorrecto;
	}
	
	protected static boolean comprobarEstado(String estado) {
		boolean estadoCorrecto = false;
		if (estado.equals(E_PENDIENTE) || estado.equals(E_VISTO) || estado.equals(E_SIGUIENDO) || estado.equals(E_DROPEADO)) {
			estadoCorrecto = true;
		} else {
			System.out.println("Valor no válido.");
		}
		
		return estadoCorrecto;
	}
	
	protected static boolean comprobarEstadoVisto(String estado) {
		boolean estadoVisto = false;
		if(estado.equals(E_VISTO)) {
			estadoVisto = true;
		}
		return estadoVisto;
	}
	
	protected static boolean comprobarEstadoPendiente(String estado) {
		boolean estadoVisto = false;
		if(estado.equals(E_PENDIENTE)) {
			estadoVisto = true;
		}
		return estadoVisto;
	}
	
	protected static boolean comprobarTemporadas(int tempTotales, int tempVistas) {
		boolean temporadasCorrectas = false;
		if(tempVistas <= tempTotales) {
			temporadasCorrectas = true;
		}else {
			System.out.println("No puedes asignar más temporadas vistas que temporadas totales.");
		}
		return temporadasCorrectas;
		
	}
	
	protected static boolean comprobarCapitulos(int capTotales, int capVistos) {
		boolean capitulosCorrectos = false;
		if(capVistos <= capTotales) {
			capitulosCorrectos = true;
		}else {
			System.out.println("No puedes asignar más capitulos vistos que capitulos totales.");
		}
		
		return capitulosCorrectos;
		
	}
	
	protected static int comprobarCapitulosTemporadasVistas(int tempVistas, int numTemporada) {
		int capitulosVistos = 0;
		
		return capitulosVistos; 
	}
	

	public static String getePendiente() {
		return E_PENDIENTE;
	}

	public static String geteVisto() {
		return E_VISTO;
	}

	public static String geteSiguiendo() {
		return E_SIGUIENDO;
	}

	public static String geteDropeado() {
		return E_DROPEADO;
	}
}
