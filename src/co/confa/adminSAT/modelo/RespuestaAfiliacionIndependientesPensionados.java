package co.confa.adminSAT.modelo;

import com.google.gson.annotations.SerializedName;

public class RespuestaAfiliacionIndependientesPensionados {
	
	@SerializedName("NumeroRadicadoSolicitud")
	private String numeroRadicadoSolicitud = "";
	
	@SerializedName("NumeroTransaccion")
	private String numeroTransaccion = "";
	
	@SerializedName("TipoDocumentoSolicitante")
	private String tipoDocumentoSolicitante = "";
	
	@SerializedName("NumeroDocumentoSolicitante")
	private String numeroDocumentoSolicitante = "";
	
	@SerializedName("ResultadoTramite")
	private String resultadoTramite = "";
	
	@SerializedName("FechaEfectivaAfiliacion")
	private String fechaEfectivaAfiliacion = "";
	
	@SerializedName("MotivoRechazo")
	private String motivoRechazo = "";
	
	@SerializedName("FechaRechazo")
	private String fechaRechazo = "";

	public RespuestaAfiliacionIndependientesPensionados() {
		
	}

	public RespuestaAfiliacionIndependientesPensionados(String numeroRadicadoSolicitud, String numeroTransaccion,
			String tipoDocumentoSolicitante, String numeroDocumentoSolicitante, String resultadoTramite,
			String fechaEfectivaAfiliacion, String motivoRechazo, String fechaRechazo) {
		super();
		this.numeroRadicadoSolicitud = numeroRadicadoSolicitud;
		this.numeroTransaccion = numeroTransaccion;
		this.tipoDocumentoSolicitante = tipoDocumentoSolicitante;
		this.numeroDocumentoSolicitante = numeroDocumentoSolicitante;
		this.resultadoTramite = resultadoTramite;
		this.fechaEfectivaAfiliacion = fechaEfectivaAfiliacion;
		this.motivoRechazo = motivoRechazo;
		this.fechaRechazo = fechaRechazo;
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

	public String getTipoDocumentoSolicitante() {
		return tipoDocumentoSolicitante;
	}

	public void setTipoDocumentoSolicitante(String tipoDocumentoSolicitante) {
		this.tipoDocumentoSolicitante = tipoDocumentoSolicitante;
	}

	public String getNumeroDocumentoSolicitante() {
		return numeroDocumentoSolicitante;
	}

	public void setNumeroDocumentoSolicitante(String numeroDocumentoSolicitante) {
		this.numeroDocumentoSolicitante = numeroDocumentoSolicitante;
	}

	public String getResultadoTramite() {
		return resultadoTramite;
	}

	public void setResultadoTramite(String resultadoTramite) {
		this.resultadoTramite = resultadoTramite;
	}

	public String getFechaEfectivaAfiliacion() {
		return fechaEfectivaAfiliacion;
	}

	public void setFechaEfectivaAfiliacion(String fechaEfectivaAfiliacion) {
		this.fechaEfectivaAfiliacion = fechaEfectivaAfiliacion;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public String getFechaRechazo() {
		return fechaRechazo;
	}

	public void setFechaRechazo(String fechaRechazo) {
		this.fechaRechazo = fechaRechazo;
	}
	

}
