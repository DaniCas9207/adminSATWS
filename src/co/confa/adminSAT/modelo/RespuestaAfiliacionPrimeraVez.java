package co.confa.adminSAT.modelo;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author tec_danielc
 *
 */
public class RespuestaAfiliacionPrimeraVez {
	
	/**
	 * Variables de clase
	 */
	@SerializedName("NumeroRadicadoSolicitud")
	private String numeroRadicadoSolicitud = "";
	@SerializedName("NumeroTransaccion")
	private String numeroTransaccion = "";
	@SerializedName("TipoDocumentoEmpleador")
	private String tipoDocumentoEmpleador = "";
	@SerializedName("NumeroDocumentoEmpleador")
	private String numeroDocumentoEmpleador = "";
	@SerializedName("SerialSat")
	private String serialSat = "0";
	@SerializedName("ResultadoTramite")
	private String resultadoTramite = "";
	@SerializedName("FechaEfectivaAfiliacion")
	private String FechaEfectivaAfiliacion = "";
	@SerializedName("MotivoRechazo")
	private String MotivoRechazo = "";
	
	/**
	 * Constructor de la clase
	 */
	public RespuestaAfiliacionPrimeraVez() {
		super();
	}

	public String getNumeroRadicadoSolicitud() {
		return numeroRadicadoSolicitud;
	}

	public void setNumeroRadicadoSolicitud(String numeroRadicadoSolicitud) {
		this.numeroRadicadoSolicitud = numeroRadicadoSolicitud;
	}

	public String getNumeroTransaccion() {
		return numeroTransaccion;
	}

	public void setNumeroTransaccion(String numeroTransaccion) {
		this.numeroTransaccion = numeroTransaccion;
	}

	public String getTipoDocumentoEmpleador() {
		return tipoDocumentoEmpleador;
	}

	public void setTipoDocumentoEmpleador(String tipoDocumentoEmpleador) {
		this.tipoDocumentoEmpleador = tipoDocumentoEmpleador;
	}

	public String getNumeroDocumentoEmpleador() {
		return numeroDocumentoEmpleador;
	}

	public void setNumeroDocumentoEmpleador(String numeroDocumentoEmpleador) {
		this.numeroDocumentoEmpleador = numeroDocumentoEmpleador;
	}

	public String getSerialSat() {
		return serialSat;
	}

	public void setSerialSat(String serialSat) {
		this.serialSat = serialSat;
	}

	public String getResultadoTramite() {
		return resultadoTramite;
	}

	public void setResultadoTramite(String resultadoTramite) {
		this.resultadoTramite = resultadoTramite;
	}

	public String getFechaEfectivaAfiliacion() {
		return FechaEfectivaAfiliacion;
	}

	public void setFechaEfectivaAfiliacion(String fechaEfectivaAfiliacion) {
		FechaEfectivaAfiliacion = fechaEfectivaAfiliacion;
	}

	public String getMotivoRechazo() {
		return MotivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		MotivoRechazo = motivoRechazo;
	}
	

}