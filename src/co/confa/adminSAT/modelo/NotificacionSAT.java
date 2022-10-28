package co.confa.adminSAT.modelo;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author tec_danielc
 *
 */
public class NotificacionSAT{
	
	@SerializedName("NumeroTransaccion")
	public String numeroTransaccion = null;
	
	@SerializedName("codigo_novedad")
	public String codigoNovedad = null;
	
	@SerializedName("fecha_creacion")
	public String fechaCreacionNovedad = null;
	
	@SerializedName("fecha_vigencia")
	public String fechaFinVigencia = null;
	
	@SerializedName("Estado_Fujo")
	public String estadoFlujo = null;
	
	@SerializedName("url")
	public String url = null;
	
	private String resultado = null;
    private String mensaje = null;
    private String codigo = null;
	
	
	public NotificacionSAT() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NotificacionSAT(String numeroTransaccion) {
		this.numeroTransaccion = numeroTransaccion;
	}

	public NotificacionSAT(String numeroTransaccion, String codigoNovedad, String fechaCreacionNovedad,
			String fechaFinVigencia, String estadoFlujo, String url) {
		super();
		this.numeroTransaccion = numeroTransaccion;
		this.codigoNovedad = codigoNovedad;
		this.fechaCreacionNovedad = fechaCreacionNovedad;
		this.fechaFinVigencia = fechaFinVigencia;
		this.estadoFlujo = estadoFlujo;
		this.url = url;
	}
	
	/**
	 * GETTERS Y SETTERS
	 */
	
	public String getNumeroTransaccion() {
		return numeroTransaccion;
	}

	public void setNumeroTransaccion(String numeroTransaccion) {
		this.numeroTransaccion = numeroTransaccion;
	}

	public String getCodigoNovedad() {
		return codigoNovedad;
	}

	public void setCodigoNovedad(String codigoNovedad) {
		this.codigoNovedad = codigoNovedad;
	}

	public String getFechaCreacionNovedad() {
		return fechaCreacionNovedad;
	}

	public void setFechaCreacionNovedad(String fechaCreacionNovedad) {
		this.fechaCreacionNovedad = fechaCreacionNovedad;
	}

	public String getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(String fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public String getEstadoFlujo() {
		return estadoFlujo;
	}

	public void setEstadoFlujo(String estadoFlujo) {
		this.estadoFlujo = estadoFlujo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}