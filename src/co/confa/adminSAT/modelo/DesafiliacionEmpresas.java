package co.confa.adminSAT.modelo;

import com.google.gson.annotations.SerializedName;

public class DesafiliacionEmpresas {
	@SerializedName("NumeroRadicadoSolicitud")
    private String numeroRadicadoSolicitud = null;
	
	@SerializedName("NumeroTransaccion")
    private String numeroTransaccion = null;
	
	@SerializedName("TipoDocumentoEmpleador")
    private String tipoDocumentoEmpleador = null;

    @SerializedName("NumeroDocumentoEmpleador")
    private String numeroDocumentoEmpleador = null;
    
    @SerializedName("SerialSAT")
    private String serialSAT = null;
    
    @SerializedName("FechaSolicitudDesafiliacion")
    private String fechaSolicitudDesafiliacion = null;
    
    @SerializedName("FechaEfectivaDesafiliacion")
    private String fechaEfectivaDesafiliacion = null;
    
    @SerializedName("Departamento")
    private String departamento = null;
    
    @SerializedName("AutorizacionManejoDatos")
    private String autorizacionManejoDatos = null;

    @SerializedName("AutorizacionNotificaciones")
    private String autorizacionNotificaciones = null;
    
    @SerializedName("PazSalvo")
    private String pazSalvo = null;

    @SerializedName("FechaPazSalvo")
    private String fechaPazSalvo = null;
    
    private String estadoConfa = null;

	public DesafiliacionEmpresas() {
		
	}

	public DesafiliacionEmpresas(String numeroRadicadoSolicitud, String numeroTransaccion, String tipoDocumentoEmpleador,
			String numeroDocumentoEmpleador, String serialSAT, String fechaSolicitudDesafiliacion,
			String fechaEfectivaDesafiliacion, String departamento, String autorizacionManejoDatos,
			String autorizacionNotificaciones, String pazSalvo, String fechaPazSalvo,String estadoConfa) {
		super();
		this.numeroRadicadoSolicitud = numeroRadicadoSolicitud;
		this.numeroTransaccion = numeroTransaccion;
		this.tipoDocumentoEmpleador = tipoDocumentoEmpleador;
		this.numeroDocumentoEmpleador = numeroDocumentoEmpleador;
		this.serialSAT = serialSAT;
		this.fechaSolicitudDesafiliacion = fechaSolicitudDesafiliacion;
		this.fechaEfectivaDesafiliacion = fechaEfectivaDesafiliacion;
		this.departamento = departamento;
		this.autorizacionManejoDatos = autorizacionManejoDatos;
		this.autorizacionNotificaciones = autorizacionNotificaciones;
		this.pazSalvo = pazSalvo;
		this.fechaPazSalvo = fechaPazSalvo;
		this.estadoConfa = estadoConfa;
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

	public String getSerialSAT() {
		return serialSAT;
	}

	public void setSerialSAT(String serialSAT) {
		this.serialSAT = serialSAT;
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

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getAutorizacionManejoDatos() {
		return autorizacionManejoDatos;
	}

	public void setAutorizacionManejoDatos(String autorizacionManejoDatos) {
		this.autorizacionManejoDatos = autorizacionManejoDatos;
	}

	public String getAutorizacionNotificaciones() {
		return autorizacionNotificaciones;
	}

	public void setAutorizacionNotificaciones(String autorizacionNotificaciones) {
		this.autorizacionNotificaciones = autorizacionNotificaciones;
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
