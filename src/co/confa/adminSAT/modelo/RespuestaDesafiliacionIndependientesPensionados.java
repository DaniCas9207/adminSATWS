package co.confa.adminSAT.modelo;

import com.google.gson.annotations.SerializedName;

public class RespuestaDesafiliacionIndependientesPensionados {
	@SerializedName("NumeroRadicadoSolicitud")
    private String numeroRadicadoSolicitud = null;
	
	@SerializedName("NumeroTransaccion")
    private String numeroTransaccion = null;
	
	@SerializedName("TipoDocumentoSolicitante")
    private String tipoDocumentoSolicitante = null;
	
	@SerializedName("NumeroDocumentoSolicitante")
    private String numeroDocumentoSolicitante = null;
	
	@SerializedName("TipoAfiliado")
    private String tipoAfiliado = null;
	
	@SerializedName("FechaResouesta")
    private String fechaRespuesta = null;
	
	@SerializedName("ResultadoTramite")
	private String resultadoTramite = "";
	
	@SerializedName("FechaEfectivaDesfiliacion")
	private String fechaEfectivaDesfiliacion = "";
	
	@SerializedName("MotivoRechazo")
	private String motivoRechazo = "";
	
	@SerializedName("PazSalvo")
    private String pazSalvo = null;

    @SerializedName("FechaPazSalvo")
    private String fechaPazSalvo = null;
    

	public RespuestaDesafiliacionIndependientesPensionados() {
	}

	public RespuestaDesafiliacionIndependientesPensionados(String numeroRadicadoSolicitud, String numeroTransaccion,
			String tipoDocumentoSolicitante, String numeroDocumentoSolicitante, String tipoAfiliado,
			String fechaRespuesta, String resultadoTramite, String fechaEfectivaDesfiliacion, String motivoRechazo,
			String pazSalvo, String fechaPazSalvo) {
		super();
		this.numeroRadicadoSolicitud = numeroRadicadoSolicitud;
		this.numeroTransaccion = numeroTransaccion;
		this.tipoDocumentoSolicitante = tipoDocumentoSolicitante;
		this.numeroDocumentoSolicitante = numeroDocumentoSolicitante;
		this.tipoAfiliado = tipoAfiliado;
		this.fechaRespuesta = fechaRespuesta;
		this.resultadoTramite = resultadoTramite;
		this.fechaEfectivaDesfiliacion = fechaEfectivaDesfiliacion;
		this.motivoRechazo = motivoRechazo;
		this.pazSalvo = pazSalvo;
		this.fechaPazSalvo = fechaPazSalvo;
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

	public String getTipoAfiliado() {
		return tipoAfiliado;
	}

	public void setTipoAfiliado(String tipoAfiliado) {
		this.tipoAfiliado = tipoAfiliado;
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

	public String getFechaEfectivaDesfiliacion() {
		return fechaEfectivaDesfiliacion;
	}

	public void setFechaEfectivaDesfiliacion(String fechaEfectivaDesfiliacion) {
		this.fechaEfectivaDesfiliacion = fechaEfectivaDesfiliacion;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public String getPazSalvo() {
		return pazSalvo;
	}

	public void setPazSalvo(String pazSalvo) {
		this.pazSalvo = pazSalvo;
	}

	public String getFechaPazSalvo() {
		return fechaPazSalvo;
	}

	public void setFechaPazSalvo(String fechaPazSalvo) {
		this.fechaPazSalvo = fechaPazSalvo;
	}
    
    
}
