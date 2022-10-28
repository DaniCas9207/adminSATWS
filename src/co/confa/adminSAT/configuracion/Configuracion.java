package co.confa.adminSAT.configuracion;

import java.util.ResourceBundle;

/**
 * 
 * <p align='justify'>
 * Clase para configuar los archivos de etiquetas y mensajes
 * 
 * @author <b>UsuarioConvenio:</b> tec_luisr <br>
 *         <b>Nombre:</b> Luis Eduardo Ramírez Cunda <br>
 *         <b>Fecha:</b> 09/02/2017
 *         </p>
 */
public class Configuracion {

	private static final String archivo = "propiedadesSistema";
	private static ResourceBundle bundle;
	private static String RUTA_ARCHIVO_MENSAJES = "";


	static {
		bundle = ResourceBundle.getBundle(archivo);
		RUTA_ARCHIVO_MENSAJES = bundle.getString("archivoMensajes");
	}

	public static String getRUTA_ARCHIVO_MENSAJES() {
		return RUTA_ARCHIVO_MENSAJES;
	}


}
