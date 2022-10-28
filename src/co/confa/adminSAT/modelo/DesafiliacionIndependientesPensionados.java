package co.confa.adminSAT.modelo;

import com.google.gson.annotations.SerializedName;

public class DesafiliacionIndependientesPensionados {
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
	
	@SerializedName("FechaSolicitudDesafiliacion")
    private String fechaSolicitudDesafiliacion = null;
    
    @SerializedName("FechaEfectivaDesafiliacion")
    private String fechaEfectivaDesafiliacion = null;
    
    @SerializedName("AutorizacionManejoDatosMinisterio")
    private String autorizacionManejoDatosMinisterio = null;

    @SerializedName("AutorizacionNotificacionesMinisiterio")
    private String autorizacionNotificacionesMinisterio = null;
    
    @SerializedName("AutorizacionManejoDatosCCf")
    private String autorizacionManejoDatosCCF = null;

    @SerializedName("AutorizacionNotificacionesCCF")
    private String autorizacionNotificacionesCCF = null;
    
    @SerializedName("PazSalvo")
    private String pazSalvo = null;

    @SerializedName("FechaPazSalvo")
    private String fechaPazSalvo = null;

    public DesafiliacionIndependientesPensionados() {
	}
    
	public DesafiliacionIndependientesPensionados(String numeroRadicadoSolicitud, String numeroTransaccion,
			String tipoDocumentoSolicitante, String numeroDocumentoSolicitante, String tipoAfiliado,
			String fechaSolicitudDesafiliacion, String fechaEfectivaDesafiliacion,
			String autorizacionManejoDatosMinisterio, String autorizacionNotificacionesMinisterio,
			String autorizacionManejoDatosCCF, String autorizacionNotificacionesCCF, String pazSalvo,
			String fechaPazSalvo, String estadoConfa) {
		super();
		this.numeroRadicadoSolicitud = numeroRadicadoSolicitud;
		this.numeroTransaccion = numeroTransaccion;
		this.tipoDocumentoSolicitante = tipoDocumentoSolicitante;
		this.numeroDocumentoSolicitante = numeroDocumentoSolicitante;
		this.tipoAfiliado = tipoAfiliado;
		this.fechaSolicitudDesafiliacion = fechaSolicitudDesafiliacion;
		this.fechaEfectivaDesafiliacion = fechaEfectivaDesafiliacion;
		this.autorizacionManejoDatosMinisterio = autorizacionManejoDatosMinisterio;
		this.autorizacionNotificacionesMinisterio = autorizacionNotificacionesMinisterio;
		this.autorizacionManejoDatosCCF = autorizacionManejoDatosCCF;
		this.autorizacionNotificacionesCCF = autorizacionNotificacionesCCF;
		this.pazSalvo = pazSalvo;
		this.fechaPazSalvo = fechaPazSalvo;
		this.estadoConfa = estadoConfa;
	}



	private String estadoConfa = null;

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

	public String getFechaSolicitudDesafiliacion() {
		return fechaSolicitudDesafiliacion;
	}

	public void setFechaSolicitudDesafiliacion(String fechaSolicitudDesafiliacion) {
		this.fechaSolicitudDesafiliacion = fechaSolicitudDesafiliacion;
	}

	public String getFechaEfectivaDesafiliacion() {
		return fechaEfectivaDesafiliacion;
	}

	public void setFechaEfectivaDesafiliacion(String fechaEfectivaDesafiliacion) {
		this.fechaEfectivaDesafiliacion = fechaEfectivaDesafiliacion;
	}

	public String getAutorizacionManejoDatosMinisterio() {
		return autorizacionManejoDatosMinisterio;
	}

	public void setAutorizacionManejoDatosMinisterio(String autorizacionManejoDatosMinisterio) {
		this.autorizacionManejoDatosMinisterio = autorizacionManejoDatosMinisterio;
	}

	public String getAutorizacionNotificacionesMinisterio() {
		return autorizacionNotificacionesMinisterio;
	}

	public void setAutorizacionNotificacionesMinisterio(String autorizacionNotificacionesMinisterio) {
		this.autorizacionNotificacionesMinisterio = autorizacionNotificacionesMinisterio;
	}

	public String getAutorizacionManejoDatosCCF() {
		return autorizacionManejoDatosCCF;
	}

	public void setAutorizacionManejoDatosCCF(String autorizacionManejoDatosCCF) {
		this.autorizacionManejoDatosCCF = autorizacionManejoDatosCCF;
	}

	public String getAutorizacionNotificacionesCCF() {
		return autorizacionNotificacionesCCF;
	}

	public void setAutorizacionNotificacionesCCF(String autorizacionNotificacionesCCF) {
		this.autorizacionNotificacionesCCF = autorizacionNotificacionesCCF;
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

	public String getEstadoConfa() {
		return estadoConfa;
	}

	public void setEstadoConfa(String estadoConfa) {
		this.estadoConfa = estadoConfa;
	}
	
    
    
}
