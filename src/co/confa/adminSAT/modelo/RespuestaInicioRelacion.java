package co.confa.adminSAT.modelo;

import com.google.gson.annotations.SerializedName;

public class RespuestaInicioRelacion {
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
	
	@SerializedName("FechaRespuesta")
	private String fechaRespuesta = "";
	
	@SerializedName("ResultadoTramite")
	private String resultadoTramite = "";
	
	@SerializedName("MotivoRechazo")
	private String MotivoRechazo = "";


	public RespuestaInicioRelacion() {
	}

	public RespuestaInicioRelacion(String numeroRadicadoSolicitud, String numeroTransaccion,
			String tipoDocumentoEmpleador, String numeroDocumentoEmpleador, String serialSat, String fechaRespuesta,
			String resultadoTramite, String motivoRechazo) {
		super();
		this.numeroRadicadoSolicitud = numeroRadicadoSolicitud;
		this.numeroTransaccion = numeroTransaccion;
		this.tipoDocumentoEmpleador = tipoDocumentoEmpleador;
		this.numeroDocumentoEmpleador = numeroDocumentoEmpleador;
		this.serialSat = serialSat;
		this.fechaRespuesta = fechaRespuesta;
		this.resultadoTramite = resultadoTramite;
		MotivoRechazo = motivoRechazo;
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

	public String getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(String fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	public String getResultadoTramite() {
		return resultadoTramite;
	}

	public void setResultadoTramite(String resultadoTramite) {
		this.resultadoTramite = resultadoTramite;
	}

	public String getMotivoRechazo() {
		return MotivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		MotivoRechazo = motivoRechazo;
	}
	
	
}
