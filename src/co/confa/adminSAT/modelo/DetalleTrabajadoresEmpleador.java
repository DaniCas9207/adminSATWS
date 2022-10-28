package co.confa.adminSAT.modelo;

import com.google.gson.annotations.SerializedName;

public class DetalleTrabajadoresEmpleador {
	@SerializedName("NumeroTransaccion")
	private String numeroTransaccion = null;

	@SerializedName("TipoDocumento")
	private String tipoDocumento = null;

	@SerializedName("NumeroDocumento")
	private String numeroDocumento = null;
	
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
	
	@SerializedName("CorreoElectronico")
	private String correoElectronico = null;

	@SerializedName("Salario")
	private String salario = null;

	@SerializedName("TipoSalario")
	private String tipoSalario = null;

	@SerializedName("HorasTrabajo")
	private String horasTrabajo = null;
	
	private String estadoConfa = null;

	public DetalleTrabajadoresEmpleador() {
		
	}

	public DetalleTrabajadoresEmpleador(String numeroTransaccion, String tipoDocumento, String numeroDocumento,
			String primerNombreTrabajador, String segundoNombreTrabajador, String primerApellidoTrabajador,
			String segundoApellidoTrabajador, String sexoTrabajador, String fechaNacimientoTrabajador,
			String correoElectronico, String salario, String tipoSalario, String horasTrabajo, String estadoConfa) {
		super();
		this.numeroTransaccion = numeroTransaccion;
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.primerNombreTrabajador = primerNombreTrabajador;
		this.segundoNombreTrabajador = segundoNombreTrabajador;
		this.primerApellidoTrabajador = primerApellidoTrabajador;
		this.segundoApellidoTrabajador = segundoApellidoTrabajador;
		this.sexoTrabajador = sexoTrabajador;
		this.fechaNacimientoTrabajador = fechaNacimientoTrabajador;
		this.correoElectronico = correoElectronico;
		this.salario = salario;
		this.tipoSalario = tipoSalario;
		this.horasTrabajo = horasTrabajo;
		this.estadoConfa = estadoConfa;
	}

	public String getNumeroTransaccion() {
		return numeroTransaccion;
	}

	public void setNumeroTransaccion(String numeroTransaccion) {
		this.numeroTransaccion = numeroTransaccion;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
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

	public String getEstadoConfa() {
		return estadoConfa;
	}

	public void setEstadoConfa(String estadoConfa) {
		this.estadoConfa = estadoConfa;
	}
	
	
}
