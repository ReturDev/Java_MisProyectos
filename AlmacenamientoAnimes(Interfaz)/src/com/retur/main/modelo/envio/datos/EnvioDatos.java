package com.retur.main.modelo.envio.datos;

import com.retur.main.modelo.elementos.PiezaAudiovisual;
import com.retur.main.modelo.enums.TiposPiezasAudiovisuales;

/**
 * Clase para pasar datos de una ventana a otra.
 * @author Sergio
 */
public final class EnvioDatos {
	
	private static EnvioDatos instancia;
	private PiezaAudiovisual datosTransferencia;
	private TiposPiezasAudiovisuales tipoTransferencia;
	
	private EnvioDatos() {}

	public static EnvioDatos getInstance() {
		
		if(instancia == null) {
			
			instancia = new EnvioDatos();
			
		}
		
		return instancia;
		
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
