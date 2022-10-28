package co.confa.adminSAT.ws;

import java.io.Serializable;
import java.util.List;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import co.confa.adminSAT.modelo.NotificacionSAT;
import co.confa.adminSAT.modelo.Respuesta;
import co.confa.adminSAT.core.Validador;
import co.confa.adminSAT.implementacion.NotificacionesImpl;
import co.confa.adminSAT.configuracion.IConstantes;
import co.confa.adminSAT.configuracion.Mensaje;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase encargada de exponer los servicos para notificación del SAT a la CCF
 * 
 * @author tec_danielc
 *
 */
@Path("/notificacionSAT")
@Api(value = "Servicio de notificación y consulta de las transacciones realizadas en el SAT")
public class NotificacionSATWS implements Serializable {
	
	private static final long serialVersionUID = 7614564158678710546L;
	private Logger log = Logger.getLogger(NotificacionSATWS.class);
	private NotificacionesImpl gestionSAT;
	private Validador validador;
	
	public NotificacionSATWS() {
		gestionSAT = new NotificacionesImpl();
		validador = new Validador();
	}

	/**
	 * Metodo encargado de notificar las solicitudes realizadas desde el SAT
	 * 
	 * @param parametro
	 * @return
	 */
	@POST
	@Path("/recibir")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "servicio notificacion sat", notes = "Retorna si se realizó la notificación")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
	@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "04--Error no hay token de autorización"),
	@ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "05--Error en validacion del token/06--Error no el token de autorización no esta permitido") })
    @WebMethod
	public String verificacionSolicitudesSAT(String parametro) {
		boolean fallo = false;
		String numeroTransaccion = "";
		String codigoNovedad = "";
		String fechaCreacionNovedad = "";
		String fechaFinVigencia = "";
		String estadoFlujo = "";
		String url = "";
		Respuesta mensaje = new Respuesta();
		NotificacionSAT notificacionSAT = new NotificacionSAT();
		String informacionGestionSAT = "";
		try {
			JSONObject valor = new JSONObject(parametro);
			numeroTransaccion = valor.getString("numero_transaccion");
			codigoNovedad = valor.getString("codigo_novedad");
			fechaCreacionNovedad = valor.getString("fecha_creacion");
			fechaFinVigencia = valor.getString("fecha_vigencia");
			estadoFlujo = valor.getString("Estado_Fujo");
			url = valor.getString("url");

			notificacionSAT = new NotificacionSAT(numeroTransaccion, codigoNovedad, fechaCreacionNovedad,
					fechaFinVigencia, estadoFlujo, url);
		} catch (Exception e) {
			log.error("Error en el método NotificacionSATWS.verificacionSolicitudesSAT:" + e.getMessage());
			fallo = true;
		}
		String respuesta = "";
		if (!fallo) {
			try {
				List<String> errores = validador.validarNotificacionSAT(notificacionSAT);
				//Si hay errores de estructura NO se envia
				if(errores.isEmpty()) {
					//método realizado para verificar si ya se ha enviado ese número de transacción previamente y no ha sido gestionada
					String estadoConfa = gestionSAT.verificarNumeroTransaccion(notificacionSAT.getNumeroTransaccion());
					informacionGestionSAT = gestionSAT.guardarNotificacionesSAT(notificacionSAT, estadoConfa);
					if (informacionGestionSAT.isEmpty() || informacionGestionSAT.equals("")) {
						respuesta = "{\"estado\": \"" + IConstantes.SIN_TRAN + "\",\"mensaje\": \""
								+ Mensaje.getMensaje("respuesta.usuario.sin.datos") + "\"}";
					} else {
						mensaje.setEstado(IConstantes.RESPUESTA_OK);
						respuesta = "{\"codigoNotificacion\":\"" + notificacionSAT.getNumeroTransaccion() + "\",\"estado\":\"" + 
								IConstantes.RESPUESTA_OK + "\",\"mensaje\":\"" + informacionGestionSAT + "\"}";
					}
				} else {
					String mostrarError = "";
					for (int i=0; i < errores.size(); i++) {
						mostrarError += errores.get(i);
						if(i+1 < errores.size())
							mostrarError += ", ";
					}
					respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_DATOS_INVALIDOS + "\",\"mensaje\": \""
							+ mostrarError + "\"}";
				}
			} catch (Exception e) {
				e.printStackTrace();
				respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_SISTEMA + "\",\"mensaje\": \""
						+ Mensaje.getMensaje("respuesta.error.sistema.consulta") + "\"}";
			}
		} else {
			respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_FALTAN_DATOS + "\",\"mensaje\": \""
					+ Mensaje.getMensaje("respuesta.error.faltan.datos") + "\"}";
		}
		return respuesta;
	}

}
