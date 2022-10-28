package co.confa.adminSAT.modelo;

import com.google.gson.annotations.SerializedName;

public class FinRelacionLaboral {
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
    
    @SerializedName("TipoTerminacion")
    private String tipoTerminacion = null;
    
    @SerializedName("FechaTerminacion")
    private String fechaTerminacion = null;
    
    @SerializedName("TipoDocumentoTrabajador")
    private String tipoDocumentoTrabajador = null;

    @SerializedName("NumeroDocumentoTrabajador")
    private String numeroDocumentoTrabajador = null;
    
    @SerializedName("PrimerNombreTrabajador")
    private String primerNombreTrabajador = null;
    
    @SerializedName("PrimerApellidoTrabajador")
    private String primerApellidoTrabajador = null;
    
    @SerializedName("AutorizacionManejoDatos")
    private String autorizacionManejoDatos = null;

    @SerializedName("AutorizacionNotificaciones")
    private String autorizacionNotificaciones = null;
    
    private String estadoConfa =null;

    public FinRelacionLaboral() {
    }
    
	public FinRelacionLaboral(String numeroRadicadoSolicitud, String numeroTransaccion, String tipoDocumentoEmpleador,
			String numeroDocumentoEmpleador, String serialSAT, String tipoTerminacion, String fechaTerminacion,
			String tipoDocumentoTrabajador, String numeroDocumentoTrabajador, String primerNombreTrabajador,
			String primerApellidoTrabajador, String autorizacionManejoDatos, String autorizacionNotificaciones,String estadoConfa) {
		super();
		this.numeroRadicadoSolicitud = numeroRadicadoSolicitud;
		this.numeroTransaccion = numeroTransaccion;
		this.tipoDocumentoEmpleador = tipoDocumentoEmpleador;
		this.numeroDocumentoEmpleador = numeroDocumentoEmpleador;
		this.serialSAT = serialSAT;
		this.tipoTerminacion = tipoTerminacion;
		this.fechaTerminacion = fechaTerminacion;
		this.tipoDocumentoTrabajador = tipoDocumentoTrabajador;
		this.numeroDocumentoTrabajador = numeroDocumentoTrabajador;
		this.primerNombreTrabajador = primerNombreTrabajador;
		this.primerApellidoTrabajador = primerApellidoTrabajador;
		this.autorizacionManejoDatos = autorizacionManejoDatos;
		this.autorizacionNotificaciones = autorizacionNotificaciones;
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

	public String getTipoTerminacion() {
		return tipoTerminacion;
	}

	public void setTipoTerminacion(String tipoTerminacion) {
		this.tipoTerminacion = tipoTerminacion;
	}

	public String getFechaTerminacion() {
		return fechaTerminacion;
	}

	public void setFechaTerminacion(String fechaTerminacion) {
		this.fechaTerminacion = fechaTerminacion;
	}

	public String getTipoDocumentoTrabajador() {
		return tipoDocumentoTrabajador;
	}

	public void setTipoDocumentoTrabajador(String tipoDocumentoTrabajador) {
		this.tipoDocumentoTrabajador = tipoDocumentoTrabajador;
	}

	public String getNumeroDocumentoTrabajador() {
		return numeroDocumentoTrabajador;
	}

	public void setNumeroDocumentoTrabajador(String numeroDocumentoTrabajador) {
		this.numeroDocumentoTrabajador = numeroDocumentoTrabajador;
	}

	public String getPrimerNombreTrabajador() {
		return primerNombreTrabajador;
	}

	public void setPrimerNombreTrabajador(String primerNombreTrabajador) {
		this.primerNombreTrabajador = primerNombreTrabajador;
	}

	public String getPrimerApellidoTrabajador() {
		return primerApellidoTrabajador;
	}

	public void setPrimerApellidoTrabajador(String primerApellidoTrabajador) {
		this.primerApellidoTrabajador = primerApellidoTrabajador;
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

	public String getEstadoConfa() {
		return estadoConfa;
	}

	public void setEstadoConfa(String estadoConfa) {
		this.estadoConfa = estadoConfa;
	}
    
    
}
