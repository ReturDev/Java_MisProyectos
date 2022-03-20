package modelo.envioDatos;

import modelo.clases.PiezaAudiovisual;
import modelo.enums.TiposPiezasAudiovisuales;

/**
 * Clase para pasar datos de una ventana a otra.
 * @author Sergio
 */
public final class EnvioDatos {
	
	private static final EnvioDatos instancia = new EnvioDatos();
	private PiezaAudiovisual datosTransferencia;
	private TiposPiezasAudiovisuales tipoTransferencia;
	
	private EnvioDatos() {}

	public static EnvioDatos getInstance() {
		return EnvioDatos.instancia;
	}

	public PiezaAudiovisual getDatosTransferencia() {
		return datosTransferencia;
	}

	public void setDatosTransferencia(PiezaAudiovisual datosTransferencia) {
		this.datosTransferencia = datosTransferencia;
	}

	public TiposPiezasAudiovisuales getTipoTransferencia() {
		return tipoTransferencia;
	}

	public void setTipoTransferencia(TiposPiezasAudiovisuales tipoTransferencia) {
		this.tipoTransferencia = tipoTransferencia;
	}
	
	
	
	
	
}
