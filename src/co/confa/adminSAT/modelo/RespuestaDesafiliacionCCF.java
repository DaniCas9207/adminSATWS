package co.confa.adminSAT.modelo;

import com.google.gson.annotations.SerializedName;

public class RespuestaDesafiliacionCCF {
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
	
	@SerializedName("FechaEfectivaDesafiliacion")
	private String fechaEfectivaDesafiliacion = "";
	
	@SerializedName("MotivoRechazo")
	private String MotivoRechazo = "";
	
	@SerializedName("PazSalvo")
	private String pazSalvo = "";
	
	@SerializedName("FechaPazSalvo")
	private String fechaPazSalvo = "";
	
	public RespuestaDesafiliacionCCF() {
	}

	public RespuestaDesafiliacionCCF(String numeroRadicadoSolicitud, String numeroTransaccion,
			String tipoDocumentoEmpleador, String numeroDocumentoEmpleador, String serialSat, String fechaRespuesta,
			String resultadoTramite, String fechaEfectivaDesafiliacion, String motivoRechazo, String pazSalvo,
			String fechaPazSalvo) {
		super();
		this.numeroRadicadoSolicitud = numeroRadicadoSolicitud;
		this.numeroTransaccion = numeroTransaccion;
		this.tipoDocumentoEmpleador = tipoDocumentoEmpleador;
		this.numeroDocumentoEmpleador = numeroDocumentoEmpleador;
		this.serialSat = serialSat;
		this.fechaRespuesta = fechaRespuesta;
		this.resultadoTramite = resultadoTramite;
		this.fechaEfectivaDesafiliacion = fechaEfectivaDesafiliacion;
		MotivoRechazo = motivoRechazo;
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

	public String getFechaEfectivaDesafiliacion() {
		return fechaEfectivaDesafiliacion;
	}

	public void setFechaEfectivaDesafiliacion(String fechaEfectivaDesafiliacion) {
		this.fechaEfectivaDesafiliacion = fechaEfectivaDesafiliacion;
	}

	public String getMotivoRechazo() {
		return MotivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		MotivoRechazo = motivoRechazo;
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
