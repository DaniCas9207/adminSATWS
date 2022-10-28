package co.confa.adminSAT.modelo;

/**
 * 
 * @author tec_danielc
 *
 */
public class Respuesta {
	
	/**
	 * Variables de clase
	 */
	private static final long serialVersionUID = 940005818826563057L;
	private String estado;
	private String mensaje;
	
	/**
	 * Constructor de la clase
	 */
	public Respuesta() {
		estado = "";
		mensaje = "";
	}
	
	/**
	 * 
	 * @param resultado
	 * @param mensaje
	 * @param codigo
	 */
	public Respuesta(String estado, String mensaje) {
		super();
		this.estado = estado;
		this.mensaje = mensaje;
	}


	/**
	 * GETTERS Y SETTERS DE LA CLASE
	 */
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}