package co.confa.adminSAT.modelo;

import com.google.gson.annotations.SerializedName;

public class InicioRelacionLaboral {
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

	@SerializedName("TipoInicio")
	private String tipoInicio = null;

	@SerializedName("FechaInicio")
	private String fechaInicio = null;

	@SerializedName("TipoDocumentoTrabajador")
	private String tipoDocumentoTrabajador = null;

	@SerializedName("NumeroDocumentoTrabajador")
	private String numeroDocumentoTrabajador = null;

	@SerializedName("PrimerNombreTrabajador")
	private String primerNombreTrabajador = null;

	@SerializedName("SegundoNombreTrabajador")
	private String segundoNombreTrabajador = null;

	@SerializedName("PrimerApellidoTrabajador")
	private String primerApellidoTrabajador = null;

	@SerializedName("SegundoApellidoTrabajador")
	private String segundoApellidoTrabajador = null;

	@SerializedName("SexoTrabajador")
	private String sexoTrabajador = null;

	@SerializedName("FechaNacimientoTrabajador")
	private String fechaNacimientoTrabajador = null;

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

	@SerializedName("Salario")
	private String salario = null;

	@SerializedName("TipoSalario")
	private String tipoSalario = null;

	@SerializedName("HorasTrabajo")
	private String horasTrabajo = null;

	@SerializedName("AutorizacionManejoDatos")
	private String autorizacionManejoDatos = null;

	@SerializedName("AutorizacionNotificaciones")
	private String autorizacionNotificaciones = null;

	private String estadoConfa = null;

	public InicioRelacionLaboral() {
	}

	public InicioRelacionLaboral(String numeroRadicadoSolicitud, String numeroTransaccion,
			String tipoDocumentoEmpleador, String numeroDocumentoEmpleador, String serialSAT, String tipoInicio,
			String fechaInicio, String tipoDocumentoTrabajador, String numeroDocumentoTrabajador,
			String primerNombreTrabajador, String segundoNombreTrabajador, String primerApellidoTrabajador,
			String segundoApellidoTrabajador, String sexoTrabajador, String fechaNacimientoTrabajador,
			String departamento, String municipio, String direccionContacto, String numeroTelefono,
			String correoElectronico, String salario, String tipoSalario, String horasTrabajo,
			String autorizacionManejoDatos, String autorizacionNotificaciones, String estadoConfa) {
		super();
		this.numeroRadicadoSolicitud = numeroRadicadoSolicitud;
		this.numeroTransaccion = numeroTransaccion;
		this.tipoDocumentoEmpleador = tipoDocumentoEmpleador;
		this.numeroDocumentoEmpleador = numeroDocumentoEmpleador;
		this.serialSAT = serialSAT;
		this.tipoInicio = tipoInicio;
		this.fechaInicio = fechaInicio;
		this.tipoDocumentoTrabajador = tipoDocumentoTrabajador;
		this.numeroDocumentoTrabajador = numeroDocumentoTrabajador;
		this.primerNombreTrabajador = primerNombreTrabajador;
		this.segundoNombreTrabajador = segundoNombreTrabajador;
		this.primerApellidoTrabajador = primerApellidoTrabajador;
		this.segundoApellidoTrabajador = segundoApellidoTrabajador;
		this.sexoTrabajador = sexoTrabajador;
		this.fechaNacimientoTrabajador = fechaNacimientoTrabajador;
		this.departamento = departamento;
		this.municipio = municipio;
		this.direccionContacto = direccionContacto;
		this.numeroTelefono = numeroTelefono;
		this.correoElectronico = correoElectronico;
		this.salario = salario;
		this.tipoSalario = tipoSalario;
		this.horasTrabajo = horasTrabajo;
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

	public String getTipoInicio() {
		return tipoInicio;
	}

	public void setTipoInicio(String tipoInicio) {
		this.tipoInicio = tipoInicio;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
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

	public String getSegundoNombreTrabajador() {
		return segundoNombreTrabajador;
	}

	public void setSegundoNombreTrabajador(String segundoNombreTrabajador) {
		this.segundoNombreTrabajador = segundoNombreTrabajador;
	}

	public String getPrimerApellidoTrabajador() {
		return primerApellidoTrabajador;
	}

	public void setPrimerApellidoTrabajador(String primerApellidoTrabajador) {
		this.primerApellidoTrabajador = primerApellidoTrabajador;
	}

	public String getSegundoApellidoTrabajador() {
		return segundoApellidoTrabajador;
	}

	public void setSegundoApellidoTrabajador(String segundoApellidoTrabajador) {
		this.segundoApellidoTrabajador = segundoApellidoTrabajador;
	}

	public String getSexoTrabajador() {
		return sexoTrabajador;
	}

	public void setSexoTrabajador(String sexoTrabajador) {
		this.sexoTrabajador = sexoTrabajador;
	}

	public String getFechaNacimientoTrabajador() {
		return fechaNacimientoTrabajador;
	}

	public void setFechaNacimientoTrabajador(String fechaNacimientoTrabajador) {
		this.fechaNacimientoTrabajador = fechaNacimientoTrabajador;
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

	public String getSalario() {
		return salario;
	}

	public void setSalario(String salario) {
		this.salario = salario;
	}

	public String getTipoSalario() {
		return tipoSalario;
	}

	public void setTipoSalario(String tipoSalario) {
		this.tipoSalario = tipoSalario;
	}

	public String getHorasTrabajo() {
		return horasTrabajo;
	}

	public void setHorasTrabajo(String horasTrabajo) {
		this.horasTrabajo = horasTrabajo;
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
