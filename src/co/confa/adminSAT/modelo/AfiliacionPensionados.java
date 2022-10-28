package co.confa.adminSAT.modelo;

import com.google.gson.annotations.SerializedName;

public class AfiliacionPensionados {

	@SerializedName("NumeroRadicadoSolicitud")
	private String numeroRadicadoSolicitud = null;

	@SerializedName("NumeroTransaccion")
	private String numeroTransaccion = null;

	@SerializedName("TipoAfiliado")
	private String tipoAfiliado = null;

	@SerializedName("TipoDocumentoSolicitante")
	private String tipoDocumentoSolicitante = null;

	@SerializedName("NumeroDocumentoSolicitante")
	private String numeroDocumentoSolicitante = null;

	@SerializedName("PrimerNombreSolicitante")
	private String primerNombreSolicitante = null;

	@SerializedName("SegundoNombreSolicitante")
	private String segundoNombreSolicitante = null;

	@SerializedName("PrimerApellidoSolicitante")
	private String primerApellidoSolicitante = null;

	@SerializedName("SegundoApellidoSolicitante")
	private String segundoApellidoSolicitante = null;

	@SerializedName("FechaSolicitud")
	private String fechaSolicitud = null;

	@SerializedName("FechaEfectivaAfiliacion")
	private String fechaEfectivaAfiliacion = null;

	@SerializedName("Departamento")
	private String departamento = null;

	@SerializedName("Municipio")
	private String municipio = null;

	@SerializedName("DireccionDomicilio")
	private String direccionDomicilio = null;

	@SerializedName("NumeroTelefono")
	private String numeroTelefono = null;

	@SerializedName("CorreoElectronico")
	private String correoElectronico = null;

	@SerializedName("ValorMensualIngresos")
	private String valorMensualIngresos = null;

	@SerializedName("DeclaracionFuenteIngresos")
	private String declaracionFuenteIngresos = null;

	@SerializedName("PerdidaAfiliacionCausaGrave")
	private String perdidaAfiliacionCausaGrave = null;

	@SerializedName("DeclaracionVeracidadInformacion")
	private String declaracionVeracidadInformacion = null;

	@SerializedName("AutorizacionManejoDatos")
	private String autorizacionManejoDatos = null;

	@SerializedName("AutorizacionNotificaciones")
	private String autorizacionNotificaciones = null;

	@SerializedName("AutorizacionManejoDatosCCF")
	private String autorizacionManejoDatosCCF = null;

	@SerializedName("AutorizacionNotificacionesCCF")
	private String autorizacionNotificacionesCCF = null;

	@SerializedName("Manifestacion")
	private String manifestacion = null;

	@SerializedName("CodigoCCF")
	private String codigoCCF = null;

	@SerializedName("PazSalvo")
	private String pazSalvo = null;

	@SerializedName("FechaPazSalvo")
	private String fechaPazSalvo = null;

	private String estadoConfa = null;

	public AfiliacionPensionados() {
		
	}

	public AfiliacionPensionados(String numeroRadicadoSolicitud, String numeroTransaccion, String tipoAfiliado,
			String tipoDocumentoSolicitante, String numeroDocumentoSolicitante, String primerNombreSolicitante,
			String segundoNombreSolicitante, String primerApellidoSolicitante, String segundoApellidoSolicitante,
			String fechaSolicitud, String fechaEfectivaAfiliacion, String departamento, String municipio,
			String direccionDomicilio, String numeroTelefono, String correoElectronico, String valorMensualIngresos,
			String declaracionFuenteIngresos, String perdidaAfiliacionCausaGrave,
			String declaracionVeracidadInformacion, String autorizacionManejoDatos, String autorizacionNotificaciones,
			String autorizacionManejoDatosCCF, String autorizacionNotificacionesCCF, String manifestacion,
			String codigoCCF, String pazSalvo, String fechaPazSalvo, String estadoConfa) {
		super();
		this.numeroRadicadoSolicitud = numeroRadicadoSolicitud;
		this.numeroTransaccion = numeroTransaccion;
		this.tipoAfiliado = tipoAfiliado;
		this.tipoDocumentoSolicitante = tipoDocumentoSolicitante;
		this.numeroDocumentoSolicitante = numeroDocumentoSolicitante;
		this.primerNombreSolicitante = primerNombreSolicitante;
		this.segundoNombreSolicitante = segundoNombreSolicitante;
		this.primerApellidoSolicitante = primerApellidoSolicitante;
		this.segundoApellidoSolicitante = segundoApellidoSolicitante;
		this.fechaSolicitud = fechaSolicitud;
		this.fechaEfectivaAfiliacion = fechaEfectivaAfiliacion;
		this.departamento = departamento;
		this.municipio = municipio;
		this.direccionDomicilio = direccionDomicilio;
		this.numeroTelefono = numeroTelefono;
		this.correoElectronico = correoElectronico;
		this.valorMensualIngresos = valorMensualIngresos;
		this.declaracionFuenteIngresos = declaracionFuenteIngresos;
		this.perdidaAfiliacionCausaGrave = perdidaAfiliacionCausaGrave;
		this.declaracionVeracidadInformacion = declaracionVeracidadInformacion;
		this.autorizacionManejoDatos = autorizacionManejoDatos;
		this.autorizacionNotificaciones = autorizacionNotificaciones;
		this.autorizacionManejoDatosCCF = autorizacionManejoDatosCCF;
		this.autorizacionNotificacionesCCF = autorizacionNotificacionesCCF;
		this.manifestacion = manifestacion;
		this.codigoCCF = codigoCCF;
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

	public String getTipoAfiliado() {
		return tipoAfiliado;
	}

	public void setTipoAfiliado(String tipoAfiliado) {
		this.tipoAfiliado = tipoAfiliado;
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

	public String getPrimerNombreSolicitante() {
		return primerNombreSolicitante;
	}

	public void setPrimerNombreSolicitante(String primerNombreSolicitante) {
		this.primerNombreSolicitante = primerNombreSolicitante;
	}

	public String getSegundoNombreSolicitante() {
		return segundoNombreSolicitante;
	}

	public void setSegundoNombreSolicitante(String segundoNombreSolicitante) {
		this.segundoNombreSolicitante = segundoNombreSolicitante;
	}

	public String getPrimerApellidoSolicitante() {
		return primerApellidoSolicitante;
	}

	public void setPrimerApellidoSolicitante(String primerApellidoSolicitante) {
		this.primerApellidoSolicitante = primerApellidoSolicitante;
	}

	public String getSegundoApellidoSolicitante() {
		return segundoApellidoSolicitante;
	}

	public void setSegundoApellidoSolicitante(String segundoApellidoSolicitante) {
		this.segundoApellidoSolicitante = segundoApellidoSolicitante;
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getFechaEfectivaAfiliacion() {
		return fechaEfectivaAfiliacion;
	}

	public void setFechaEfectivaAfiliacion(String fechaEfectivaAfiliacion) {
		this.fechaEfectivaAfiliacion = fechaEfectivaAfiliacion;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getDireccionDomicilio() {
		return direccionDomicilio;
	}

	public void setDireccionDomicilio(String direccionDomicilio) {
		this.direccionDomicilio = direccionDomicilio;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getValorMensualIngresos() {
		return valorMensualIngresos;
	}

	public void setValorMensualIngresos(String valorMensualIngresos) {
		this.valorMensualIngresos = valorMensualIngresos;
	}

	public String getDeclaracionFuenteIngresos() {
		return declaracionFuenteIngresos;
	}

	public void setDeclaracionFuenteIngresos(String declaracionFuenteIngresos) {
		this.declaracionFuenteIngresos = declaracionFuenteIngresos;
	}

	public String getPerdidaAfiliacionCausaGrave() {
		return perdidaAfiliacionCausaGrave;
	}

	public void setPerdidaAfiliacionCausaGrave(String perdidaAfiliacionCausaGrave) {
		this.perdidaAfiliacionCausaGrave = perdidaAfiliacionCausaGrave;
	}

	public String getDeclaracionVeracidadInformacion() {
		return declaracionVeracidadInformacion;
	}

	public void setDeclaracionVeracidadInformacion(String declaracionVeracidadInformacion) {
		this.declaracionVeracidadInformacion = declaracionVeracidadInformacion;
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

	public String getManifestacion() {
		return manifestacion;
	}

	public void setManifestacion(String manifestacion) {
		this.manifestacion = manifestacion;
	}

	public String getCodigoCCF() {
		return codigoCCF;
	}

	public void setCodigoCCF(String codigoCCF) {
		this.codigoCCF = codigoCCF;
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
