package co.confa.adminSAT.modelo;

import com.google.gson.annotations.SerializedName;

public class AfiliacionNoPrimeraVez {

	@SerializedName("NumeroRadicadoSolicitud")
	private String numeroRadicadoSolicitud = null;

	@SerializedName("NumeroTransaccion")
	private String numeroTransaccion = null;

	@SerializedName("TipoPersona")
	private String tipoPersona = null;

	@SerializedName("NaturalezaJuridicaEmpleador")
	private int naturalezaJuridicaEmpleador = 0;

	@SerializedName("TipoDocumentoEmpleador")
	private String tipoDocumentoEmpleador = null;

	@SerializedName("NumeroDocumentoEmpleador")
	private String numeroDocumentoEmpleador = null;

	@SerializedName("SerialSAT")
	private String serialSAT = null;

	@SerializedName("PrimerNombreEmpleador")
	private String primerNombreEmpleador = null;

	@SerializedName("SegundoNombreEmpleador")
	private String segundoNombreEmpleador = null;

	@SerializedName("PrimerApellidoEmpleador")
	private String primerApellidoEmpleador = null;

	@SerializedName("SegundoApellidoEmpleador")
	private String segundoApellidoEmpleador = null;

	@SerializedName("FechaSolicitud")
	private String fechaSolicitud = null;

	@SerializedName("PerdidaAfiliacionCausaGrave")
	private String perdidaAfiliacionCausaGrave = null;

	@SerializedName("FechaEfectivaAfiliacion")
	private String fechaEfectivaAfiliacion = null;

	@SerializedName("RazonSocial")
	private String razonSocial = null;

	@SerializedName("NumeroMatriculaMercantil")
	private String numeroMatriculaMercantil = null;

	@SerializedName("Departamento")
	private String departamento = null;

	@SerializedName("Municipio")
	private String municipio = null;

	@SerializedName("DireccionContacto")
	private String direccionContacto = null;

	@SerializedName("NumeroTelefono")
	private String numeroTelefono = null;

	@SerializedName("CorreoElectronico")
	private String correoElectronico = null;

	@SerializedName("TipoDocumentoRepresentante")
	private String tipoDocumentoRepresentante = null;

	@SerializedName("NumeroDocumentoRepresentante")
	private String numeroDocumentoRepresentante = null;

	@SerializedName("PrimerNombreRepresentante")
	private String primerNombreRepresentante = null;

	@SerializedName("SegundoNombreRepresentante")
	private String segundoNombreRepresentante = null;

	@SerializedName("PrimerApellidoRepresentante")
	private String primerApellidoRepresentante = null;

	@SerializedName("SegundoApellidoRepresentante")
	private String segundoApellidoRepresentante = null;

	@SerializedName("AutorizacionManejoDatos")
	private String autorizacionManejoDatos = null;

	@SerializedName("AutorizacionNotificaciones")
	private String autorizacionNotificaciones = null;

	@SerializedName("Manifestacion")
	private String manifestacion = null;

	@SerializedName("CodigoCcfAnterior")
	private String codigoCcfAnterior = "";

	@SerializedName("PazSalvo")
	private String pazSalvo = "";

	@SerializedName("FechaPazSalvo")
	private String fechaPazSalvo = "";

	private String estadoConfa = null;

	public AfiliacionNoPrimeraVez() {
	}
	
	public AfiliacionNoPrimeraVez(String numeroRadicadoSolicitud, String numeroTransaccion, String tipoPersona,
			int naturalezaJuridicaEmpleador, String tipoDocumentoEmpleador, String numeroDocumentoEmpleador,
			String serialSAT, String primerNombreEmpleador, String segundoNombreEmpleador,
			String primerApellidoEmpleador, String segundoApellidoEmpleador, String fechaSolicitud,
			String perdidaAfiliacionCausaGrave, String fechaEfectivaAfiliacion, String razonSocial,
			String numeroMatriculaMercantil, String departamento, String municipio, String direccionContacto,
			String numeroTelefono, String correoElectronico, String tipoDocumentoRepresentante,
			String numeroDocumentoRepresentante, String primerNombreRepresentante, String segundoNombreRepresentante,
			String primerApellidoRepresentante, String segundoApellidoRepresentante, String autorizacionManejoDatos,
			String autorizacionNotificaciones, String manifestacion, String codigoCcfAnterior, String pazSalvo,
			String fechaPazSalvo, String estadoConfa) {
		super();
		this.numeroRadicadoSolicitud = numeroRadicadoSolicitud;
		this.numeroTransaccion = numeroTransaccion;
		this.tipoPersona = tipoPersona;
		this.naturalezaJuridicaEmpleador = naturalezaJuridicaEmpleador;
		this.tipoDocumentoEmpleador = tipoDocumentoEmpleador;
		this.numeroDocumentoEmpleador = numeroDocumentoEmpleador;
		this.serialSAT = serialSAT;
		this.primerNombreEmpleador = primerNombreEmpleador;
		this.segundoNombreEmpleador = segundoNombreEmpleador;
		this.primerApellidoEmpleador = primerApellidoEmpleador;
		this.segundoApellidoEmpleador = segundoApellidoEmpleador;
		this.fechaSolicitud = fechaSolicitud;
		this.perdidaAfiliacionCausaGrave = perdidaAfiliacionCausaGrave;
		this.fechaEfectivaAfiliacion = fechaEfectivaAfiliacion;
		this.razonSocial = razonSocial;
		this.numeroMatriculaMercantil = numeroMatriculaMercantil;
		this.departamento = departamento;
		this.municipio = municipio;
		this.direccionContacto = direccionContacto;
		this.numeroTelefono = numeroTelefono;
		this.correoElectronico = correoElectronico;
		this.tipoDocumentoRepresentante = tipoDocumentoRepresentante;
		this.numeroDocumentoRepresentante = numeroDocumentoRepresentante;
		this.primerNombreRepresentante = primerNombreRepresentante;
		this.segundoNombreRepresentante = segundoNombreRepresentante;
		this.primerApellidoRepresentante = primerApellidoRepresentante;
		this.segundoApellidoRepresentante = segundoApellidoRepresentante;
		this.autorizacionManejoDatos = autorizacionManejoDatos;
		this.autorizacionNotificaciones = autorizacionNotificaciones;
		this.manifestacion = manifestacion;
		this.codigoCcfAnterior = codigoCcfAnterior;
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

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public int getNaturalezaJuridicaEmpleador() {
		return naturalezaJuridicaEmpleador;
	}

	public void setNaturalezaJuridicaEmpleador(int naturalezaJuridicaEmpleador) {
		this.naturalezaJuridicaEmpleador = naturalezaJuridicaEmpleador;
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

	public String getPrimerNombreEmpleador() {
		return primerNombreEmpleador;
	}

	public void setPrimerNombreEmpleador(String primerNombreEmpleador) {
		this.primerNombreEmpleador = primerNombreEmpleador;
	}

	public String getSegundoNombreEmpleador() {
		return segundoNombreEmpleador;
	}

	public void setSegundoNombreEmpleador(String segundoNombreEmpleador) {
		this.segundoNombreEmpleador = segundoNombreEmpleador;
	}

	public String getPrimerApellidoEmpleador() {
		return primerApellidoEmpleador;
	}

	public void setPrimerApellidoEmpleador(String primerApellidoEmpleador) {
		this.primerApellidoEmpleador = primerApellidoEmpleador;
	}

	public String getSegundoApellidoEmpleador() {
		return segundoApellidoEmpleador;
	}

	public void setSegundoApellidoEmpleador(String segundoApellidoEmpleador) {
		this.segundoApellidoEmpleador = segundoApellidoEmpleador;
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getPerdidaAfiliacionCausaGrave() {
		return perdidaAfiliacionCausaGrave;
	}

	public void setPerdidaAfiliacionCausaGrave(String perdidaAfiliacionCausaGrave) {
		this.perdidaAfiliacionCausaGrave = perdidaAfiliacionCausaGrave;
	}

	public String getFechaEfectivaAfiliacion() {
		return fechaEfectivaAfiliacion;
	}

	public void setFechaEfectivaAfiliacion(String fechaEfectivaAfiliacion) {
		this.fechaEfectivaAfiliacion = fechaEfectivaAfiliacion;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNumeroMatriculaMercantil() {
		return numeroMatriculaMercantil;
	}

	public void setNumeroMatriculaMercantil(String numeroMatriculaMercantil) {
		this.numeroMatriculaMercantil = numeroMatriculaMercantil;
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

	public String getDireccionContacto() {
		return direccionContacto;
	}

	public void setDireccionContacto(String direccionContacto) {
		this.direccionContacto = direccionContacto;
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

	public String getTipoDocumentoRepresentante() {
		return tipoDocumentoRepresentante;
	}

	public void setTipoDocumentoRepresentante(String tipoDocumentoRepresentante) {
		this.tipoDocumentoRepresentante = tipoDocumentoRepresentante;
	}

	public String getNumeroDocumentoRepresentante() {
		return numeroDocumentoRepresentante;
	}

	public void setNumeroDocumentoRepresentante(String numeroDocumentoRepresentante) {
		this.numeroDocumentoRepresentante = numeroDocumentoRepresentante;
	}

	public String getPrimerNombreRepresentante() {
		return primerNombreRepresentante;
	}

	public void setPrimerNombreRepresentante(String primerNombreRepresentante) {
		this.primerNombreRepresentante = primerNombreRepresentante;
	}

	public String getSegundoNombreRepresentante() {
		return segundoNombreRepresentante;
	}

	public void setSegundoNombreRepresentante(String segundoNombreRepresentante) {
		this.segundoNombreRepresentante = segundoNombreRepresentante;
	}

	public String getPrimerApellidoRepresentante() {
		return primerApellidoRepresentante;
	}

	public void setPrimerApellidoRepresentante(String primerApellidoRepresentante) {
		this.primerApellidoRepresentante = primerApellidoRepresentante;
	}

	public String getSegundoApellidoRepresentante() {
		return segundoApellidoRepresentante;
	}

	public void setSegundoApellidoRepresentante(String segundoApellidoRepresentante) {
		this.segundoApellidoRepresentante = segundoApellidoRepresentante;
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

	public String getManifestacion() {
		return manifestacion;
	}

	public void setManifestacion(String manifestacion) {
		this.manifestacion = manifestacion;
	}

	public String getEstadoConfa() {
		return estadoConfa;
	}

	public void setEstadoConfa(String estadoConfa) {
		this.estadoConfa = estadoConfa;
	}

	public String getCodigoCcfAnterior() {
		return codigoCcfAnterior;
	}

	public void setCodigoCcfAnterior(String codigoCcfAnterior) {
		this.codigoCcfAnterior = codigoCcfAnterior;
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
