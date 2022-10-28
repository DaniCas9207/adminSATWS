package co.confa.adminSAT.configuracion;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * <p align='justify'>
 * Clase para manejar el archivo de propiedades de los mensajes de la
 * aplicación.
 * 
 * @author <b>UsuarioConvenio:</b> tec_luisr <br>
 *         <b>Nombre:</b> Luis Eduardo Ramírez Cunda <br>
 *         <b>Fecha:</b> 09/02/2017
 *         </p>
 */
public class Mensaje {

	private static String archivoMensajes;
	private static ResourceBundle bundle;

	static {
		archivoMensajes = Configuracion.getRUTA_ARCHIVO_MENSAJES();
		bundle = ResourceBundle.getBundle(archivoMensajes, new Locale("es"));
	}

	/**
	 * 
	 * <p align='justify'>
	 * Método para obtener el valor del mensaje de acuerdo al nombre
	 * 
	 * @param nombre
	 * @return </p>
	 */
	public static String getMensaje(String nombre) {
		return bundle.getString(nombre);
	}
}
