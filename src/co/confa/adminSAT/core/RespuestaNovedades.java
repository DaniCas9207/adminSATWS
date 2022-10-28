package co.confa.adminSAT.core;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;

import co.confa.adminSAT.configuracion.ConsumoRestWS;
import co.confa.adminSAT.configuracion.IConstantes;
import co.confa.adminSAT.configuracion.TokenBean;
import co.confa.adminSAT.implementacion.AfiliacionesImpl;
import co.confa.adminSAT.implementacion.NotificacionesImpl;
import co.confa.adminSAT.implementacion.NotificacionesIndepPensImpl;
import co.confa.adminSAT.modelo.RespuestaAfiliacionIndependientesPensionados;
import co.confa.adminSAT.modelo.RespuestaAfiliacionNoPrimeraVez;
import co.confa.adminSAT.modelo.RespuestaAfiliacionPrimeraVez;
import co.confa.adminSAT.modelo.RespuestaDesafiliacionCCF;
import co.confa.adminSAT.modelo.RespuestaDesafiliacionIndependientesPensionados;
import co.confa.adminSAT.modelo.RespuestaFinRelacion;
import co.confa.adminSAT.modelo.RespuestaInicioRelacion;
import co.confa.bd.BaseDatos;

/**
 * 
 * @author tec_danielc
 *
 */
public class RespuestaNovedades {
	
	private static final Logger log = Logger.getLogger(RespuestaNovedades.class);
	private NotificacionesImpl gestionSAT;
	private AfiliacionesImpl afiliacionesSAT;
	private NotificacionesIndepPensImpl gestionSATIndependPens;
	private ArrayList<String> rutas = new ArrayList<String>();
	private ArrayList<String> client_id_token = new ArrayList<String>();
	private TokenBean tb;
	Gson gs = new Gson();
	
	public RespuestaNovedades()
	{
		rutas = consultarRutas();
		client_id_token = consultarClientId();
		gestionSAT = new NotificacionesImpl();
		gestionSATIndependPens = new NotificacionesIndepPensImpl();
		afiliacionesSAT = new AfiliacionesImpl();
	}
	
	/**
	 * Consulta del listado de servicios actualmente parametrizados 
	 * en la BD
	 * @return
	 */
	public ArrayList<String> consultarRutas() {
		ArrayList<String> respuesta= new ArrayList<String>();
		BaseDatos bd= new BaseDatos();
		ResultSet rs=null;
		try {
			if(bd.conectar(IConstantes.POOL_INGRESO)) {
				rs=bd.ejecutarConsulta("select * from recepcion.servicio_web order by id asc");
				while(rs.next()) {
					respuesta.add(rs.getString("url"));
				}
			}
		}catch(Exception e) {
			log.error("RespuestaNovedades.consultarRutas()-> ", e);
		}finally {
			bd.cerrar();
		}
		return respuesta;
	}

	/**
	 * Consulta del listado de servicios actualmente parametrizados en la BD
	 * 
	 * @return
	 */
	public ArrayList<String> consultarClientId() {
		ArrayList<String> respuesta = new ArrayList<String>();
		BaseDatos bd = new BaseDatos();
		ResultSet rs = null;
		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				rs = bd.ejecutarConsulta("select * from recepcion.servicio_web order by id asc");
				while (rs.next()) {
					if(IConstantes.CLIENT_ID_PRUEBAS) {
						respuesta.add(rs.getString("client_id_pruebas"));
					} else {
						respuesta.add(rs.getString("client_id_produccion"));
					}
				}
			}
		} catch (Exception e) {
			log.error("RespuestaNovedades.consultarClientId()-> ", e);
		} finally {
			bd.cerrar();
		}
		return respuesta;
	}
		
	
	public int responderTransaccionesAfiPrimeraVez() {
		int respuesta=0;
		BaseDatos bd = new BaseDatos();
		try {
			
			ArrayList<RespuestaAfiliacionPrimeraVez> respAfilPrimeraVez = 
					afiliacionesSAT.listarTransaccionGestionadas(IConstantes.ESTADO_GESTIONADA, IConstantes.SERVICIO_AFILIACION_PRIMERA_VEZ);
			
			if(respAfilPrimeraVez.size()>0) {
				tb= new TokenBean();
				if(tb!=null) {
					if(bd.conectar(IConstantes.POOL_INGRESO)) {
						String data = "";
						boolean salida = false;
						for (RespuestaAfiliacionPrimeraVez respAfi: respAfilPrimeraVez) {
							respuesta++;
							data = gs.toJson(respAfi).toString();

							//método utilizado para responder cada transacción 
							salida = responderSolicitudes(data, IConstantes.SERVICIO_RESPUESTA_AFILIACION, respAfi.getNumeroTransaccion(), bd,IConstantes.SERVICIO_AFILIACION_PRIMERA_VEZ);
							if(salida) {
								log.info("RespuestaNovedades.responderTransaccionesAfiPrimeraVez()-> Respuesta afiliación enviada: notificación -> " + respAfi.getNumeroTransaccion());	
							} else {
								log.error("RespuestaNovedades.responderTransaccionesAfiPrimeraVez()-> Respuesta afiliación no enviada: notificación -> " + respAfi.getNumeroTransaccion());
							}
						}
					}
				}
			} else {
				log.info("RespuestaNovedades.responderTransaccionesAfiPrimeraVez()-> No existen transacciones pendientes por consultar");
			}
			
		}catch (Exception e) {
			log.error("RespuestaNovedades.responderTransaccionesAfiPrimeraVez()-> ", e);
			e.printStackTrace();
		}
		finally {
			bd.cerrar();
		}
		return respuesta;
	}
	
	public boolean responderTransaccionesGlosas(String respAfi, int servicio, String numeroTransaccion) {
		boolean resultado=false;
		BaseDatos bd = new BaseDatos();
		if(respAfi != null) {
			tb= new TokenBean();
			if(tb!=null) {
				if(bd.conectar(IConstantes.POOL_INGRESO)) {
					String data = respAfi;

					String[] respuesta = ConsumoRestWS.comsumoRestPost(data, IConstantes.WS_RUTA_BASE+rutas.get(servicio-1), tb.getToken(client_id_token.get(servicio - 1)), true, true,"POST");
					if (respuesta[0].equals("true")) {
						JSONObject valor= new JSONObject(respuesta[1]);
						String mensaje="";

						if(valor.has("codigo") && valor.has("mensaje")) {
							//se valida el mensaje para almacenarlo en BD
							if(valor.getString("mensaje")!=null && !valor.getString("mensaje").isEmpty()){
								mensaje=valor.getString("mensaje").replaceAll("“", "\"");
								mensaje=mensaje.replaceAll("”", "\"");
								resultado= afiliacionesSAT.guardarRespuestaWS(valor.getString("resultado"), mensaje, valor.getString("codigo"), numeroTransaccion);
								
							}
							
						} 
						log.info("RespuestaNovedades.responderTransaccionesGlosas()-> enviado "+data);
						log.info("RespuestaNovedades.responderTransaccionesGlosas()-> recibido "+valor.toString());
					}else {
						JSONObject valor= new JSONObject(respuesta[1]);
						log.info("RespuestaNovedades.responderTransaccionesGlosas()-> enviado Error "+data);
						log.info("RespuestaNovedades.responderTransaccionesGlosas()-> recibido Error "+valor.toString());
					}

				}
			} else {
				log.error("RespuestaNovedades.responderTransaccionesGlosas()-> no hay token dsponible");
				resultado=false;
			}
		}
		
		return resultado;
	}

	public int responderTransaccionesDesafiliacionEmpresa() {
		int respuesta = 0;
		BaseDatos bd = new BaseDatos();
		try {

			ArrayList<RespuestaDesafiliacionCCF> respAfilPrimeraVez = afiliacionesSAT.listarTransaccionGestionadasDesafiliaciones
					(IConstantes.ESTADO_GESTIONADA,	IConstantes.SERVICIO_DESAFILIACION);

			if (respAfilPrimeraVez.size() > 0) {
				tb = new TokenBean();
				if (tb != null) {
					if (bd.conectar(IConstantes.POOL_INGRESO)) {
						String data = "";
						boolean salida = false;
						for (RespuestaDesafiliacionCCF respAfi : respAfilPrimeraVez) {
							respuesta++;
							data = gs.toJson(respAfi).toString();

							// método utilizado para responder cada transacción
							salida = responderSolicitudes(data, IConstantes.SERVICIO_RESPUESTA_DESAFILIACION, respAfi.getNumeroTransaccion(), bd,IConstantes.SERVICIO_DESAFILIACION);
							if (salida) {
								log.info(
										"INFO: RespuestaNovedades.responderTransaccionesDesafiliacionEmpresa()-> Respuesta desafiliación enviada: notificación -> "
												+ respAfi.getNumeroTransaccion());
							} else {
								log.error(
										"ERROR: RespuestaNovedades.responderTransaccionesDesafiliacionEmpresa()-> Respuesta desafiliación no enviada: notificación -> "
												+ respAfi.getNumeroTransaccion());
							}
						}
					}
				}
			} else {
				log.info(
						"RespuestaNovedades.responderTransaccionesDesafiliacionEmpresa()-> No existen transacciones pendientes por consultar");
			}

		} catch (Exception e) {
			log.error("ERROR: RespuestaNovedades.responderTransaccionesDesafiliacionEmpresa()-> ", e);
			e.printStackTrace();
		} finally {
			bd.cerrar();
		}
		return respuesta;
	}
	
	
	public int responderTransaccionesInicioRelacion() {
		int respuesta = 0;
		BaseDatos bd = new BaseDatos();
		try {

			ArrayList<RespuestaInicioRelacion> respAfilPrimeraVez = afiliacionesSAT.listarTransaccionGestionadasInicioRelacion
					(IConstantes.ESTADO_GESTIONADA,	IConstantes.SERVICIO_RELACION_LABORAL);

			if (respAfilPrimeraVez.size() > 0) {
				tb = new TokenBean();
				if (tb != null) {
					if (bd.conectar(IConstantes.POOL_INGRESO)) {
						String data = "";
						boolean salida = false;
						for (RespuestaInicioRelacion respAfi : respAfilPrimeraVez) {
							respuesta++;
							data = gs.toJson(respAfi).toString();

							// método utilizado para responder cada transacción
							salida = responderSolicitudes(data, IConstantes.SERVICIO_RESPUESTA_INICIO_RELACION, respAfi.getNumeroTransaccion(), bd,IConstantes.SERVICIO_RELACION_LABORAL);
							if (salida) {
								log.info(
										"INFO: RespuestaNovedades.responderTransaccionesInicioRelacion()-> Respuesta inicio relacion enviada: notificación -> "
												+ respAfi.getNumeroTransaccion());
							} else {
								log.error(
										"ERROR: RespuestaNovedades.responderTransaccionesInicioRelacion()-> Respuesta inicio relacion no enviada: notificación -> "
												+ respAfi.getNumeroTransaccion());
							}
						}
					}
				}
			} else {
				log.info(
						"RespuestaNovedades.responderTransaccionesInicioRelacion()-> No existen transacciones pendientes por consultar");
			}

		} catch (Exception e) {
			log.error("ERROR: RespuestaNovedades.responderTransaccionesInicioRelacion()-> ", e);
			e.printStackTrace();
		} finally {
			bd.cerrar();
		}
		return respuesta;
	}
		
	public int responderTransaccionesFinRelacion() {
		int respuesta = 0;
		BaseDatos bd = new BaseDatos();
		try {

			ArrayList<RespuestaFinRelacion> respAfilPrimeraVez = afiliacionesSAT.listarTransaccionGestionadasFinRelacion
					(IConstantes.ESTADO_GESTIONADA,	IConstantes.SERVICIO_TERMINACION_RELACION);

			if (respAfilPrimeraVez.size() > 0) {
				tb = new TokenBean();
				if (tb != null) {
					if (bd.conectar(IConstantes.POOL_INGRESO)) {
						String data = "";
						boolean salida = false;
						for (RespuestaFinRelacion respAfi : respAfilPrimeraVez) {
							respuesta++;
							data = gs.toJson(respAfi).toString();

							// método utilizado para responder cada transacción
							salida = responderSolicitudes(data, IConstantes.SERVICIO_RESPUESTA_FIN_RELACION, respAfi.getNumeroTransaccion(), bd,IConstantes.SERVICIO_TERMINACION_RELACION);
							if (salida) {
								log.info(
										"INFO: RespuestaNovedades.responderTransaccionesFinRelacion()-> Respuesta fin relacion enviada: notificación -> "
												+ respAfi.getNumeroTransaccion());
							} else {
								log.error(
										"ERROR: RespuestaNovedades.responderTransaccionesFinRelacion()-> Respuesta fin relacion no enviada: notificación -> "
												+ respAfi.getNumeroTransaccion());
							}
						}
					}
				}
			} else {
				log.info(
						"RespuestaNovedades.responderTransaccionesFinRelacion()-> No existen transacciones pendientes por consultar");
			}

		} catch (Exception e) {
			log.error("ERROR: RespuestaNovedades.responderTransaccionesFinRelacion()-> ", e);
			e.printStackTrace();
		} finally {
			bd.cerrar();
		}
		return respuesta;
	}
	
	public int responderTransaccionesAfiNoPrimeraVez() {
		int respuesta = 0;
		BaseDatos bd = new BaseDatos();
		try {

			ArrayList<RespuestaAfiliacionNoPrimeraVez> respAfilPrimeraVez = afiliacionesSAT.listarTransaccionGestionadasNoPrimeraVez(
					IConstantes.ESTADO_GESTIONADA, IConstantes.SERVICIO_AFILIACION_NO_PRIMERA_VEZ);

			if (respAfilPrimeraVez.size() > 0) {
				tb = new TokenBean();
				if (tb != null) {
					if (bd.conectar(IConstantes.POOL_INGRESO)) {
						String data = "";
						boolean salida = false;
						for (RespuestaAfiliacionNoPrimeraVez respAfi : respAfilPrimeraVez) {
							respuesta++;
							data = gs.toJson(respAfi).toString();

							// método utilizado para responder cada transacción
							salida = responderSolicitudes(data, IConstantes.SERVICIO_RESPUESTA_AFILIACION_NO_PRIMERA_VEZ, respAfi.getNumeroTransaccion(), bd,IConstantes.SERVICIO_AFILIACION_NO_PRIMERA_VEZ);
							if (salida) {
								log.info("RespuestaNovedades.responderTransaccionesAfiPrimeraVez()-> Respuesta afiliación enviada: notificación -> "
												+ respAfi.getNumeroTransaccion());
							} else {
								log.error("RespuestaNovedades.responderTransaccionesAfiPrimeraVez()-> Respuesta afiliación no enviada: notificación -> "
												+ respAfi.getNumeroTransaccion());
							}
						}
					}
				}
			} else {
				log.info(
						"RespuestaNovedades.responderTransaccionesAfiPrimeraVez()-> No existen transacciones pendientes por consultar");
			}

		} catch (Exception e) {
			log.error("RespuestaNovedades.responderTransaccionesAfiPrimeraVez()-> ", e);
			e.printStackTrace();
		} finally {
			bd.cerrar();
		}
		return respuesta;
	}
	
	public int responderTransaccionesAfiIndepPens() {
		int respuesta=0;
		BaseDatos bd = new BaseDatos();
		try {
			ArrayList<RespuestaAfiliacionIndependientesPensionados> respAfilPrimeraVez = gestionSATIndependPens.listarTransaccionGestionadasAfiliacionIndepPens(IConstantes.ESTADO_GESTIONADA, IConstantes.SERVICIO_AFILIACION_INDEPENDIENTES,IConstantes.SERVICIO_AFILIACION_PENSIONADOS);		
			if(respAfilPrimeraVez.size()>0) {
				tb= new TokenBean();
				if(tb!=null) {
					if(bd.conectar(IConstantes.POOL_INGRESO)) {
						String data = "";
						boolean salida = false;
						for (RespuestaAfiliacionIndependientesPensionados respAfi: respAfilPrimeraVez) {
							respuesta++;
							data = gs.toJson(respAfi).toString();

							//método utilizado para responder cada transacción 
							salida = responderSolicitudesIndePens(data, IConstantes.SERVICIO_RESPUESTA_AFI_INDEPENDIENTES_PENSIONADOS, respAfi.getNumeroTransaccion(), bd);
							if(salida) {
								log.info("RespuestaNovedades.responderTransaccionesAfiPrimeraVez()-> Respuesta afiliación enviada: notificación -> " + respAfi.getNumeroTransaccion());	
							} else {
								log.error("RespuestaNovedades.responderTransaccionesAfiPrimeraVez()-> Respuesta afiliación no enviada: notificación -> " + respAfi.getNumeroTransaccion());
							}
						}
					}
				}
			} else {
				log.info("RespuestaNovedades.responderTransaccionesAfiIndepPens()-> No existen transacciones pendientes por consultar");
			}
			
		}catch (Exception e) {
			log.error("RespuestaNovedades.responderTransaccionesAfiIndepPens()-> ", e);
			e.printStackTrace();
		}
		finally {
			bd.cerrar();
		}
		return respuesta;
	}
	
	public int responderTransaccionesDesafiliacionIndepPens() {
		int respuesta=0;
		BaseDatos bd = new BaseDatos();
		try {
			
			ArrayList<RespuestaDesafiliacionIndependientesPensionados> respAfilPrimeraVez = 
					gestionSATIndependPens.listarTransaccionGestionadasDesafiliacionIndePens(IConstantes.ESTADO_GESTIONADA, IConstantes.SERVICIO_DESAFILIACION_INDEP_PENS);
			
			if(respAfilPrimeraVez.size()>0) {
				tb= new TokenBean();
				if(tb!=null) {
					if(bd.conectar(IConstantes.POOL_INGRESO)) {
						String data = "";
						boolean salida = false;
						for (RespuestaDesafiliacionIndependientesPensionados respAfi: respAfilPrimeraVez) {
							respuesta++;
							data = gs.toJson(respAfi).toString();

							//método utilizado para responder cada transacción 
							salida = responderSolicitudesIndePens(data, IConstantes.SERVICIO_RESPUESTA_DESAFILIACION_INDEP_PENS, respAfi.getNumeroTransaccion(), bd);
							if(salida) {
								log.info("RespuestaNovedades.responderTransaccionesDesafiliacionIndepPens()-> Respuesta afiliación enviada: notificación -> " + respAfi.getNumeroTransaccion());	
							} else {
								log.error("RespuestaNovedades.responderTransaccionesDesafiliacionIndepPens()-> Respuesta afiliación no enviada: notificación -> " + respAfi.getNumeroTransaccion());
							}
						}
					}
				}
			} else {
				log.info("RespuestaNovedades.responderTransaccionesDesafiliacionIndepPens()-> No existen transacciones pendientes por consultar");
			}
			
		}catch (Exception e) {
			log.error("RespuestaNovedades.responderTransaccionesDesafiliacionIndepPens()-> ", e);
			e.printStackTrace();
		}
		finally {
			bd.cerrar();
		}
		return respuesta;
	}

	private boolean responderSolicitudes(String data, int servicio, String notificacion, BaseDatos bd,int servicioPertenece) {
		boolean resultado = false;
		try {
			tb = new TokenBean();
			if (tb != null) {
				String[] respuesta = ConsumoRestWS.comsumoRestPost(data,
						IConstantes.WS_RUTA_BASE + rutas.get(servicio - 1),
						tb.getToken(client_id_token.get(servicio - 1)), true, true, "POST");
				
				if (respuesta[0].equals("true")) {
					JSONObject valor = new JSONObject(respuesta[1]);
					String mensaje = "";

					if (valor.has("codigo") && valor.has("mensaje")) {
						// se valida el mensaje para almacenarlo en BD
						if (valor.getString("mensaje") != null && !valor.getString("mensaje").isEmpty()) {
							mensaje = valor.getString("mensaje").replaceAll("“", "\"");
							mensaje = mensaje.replaceAll("”", "\"");
							resultado = afiliacionesSAT.guardarRespuestaWS(valor.getString("resultado"), mensaje,
									valor.getString("codigo"), notificacion);

						}
						// validar que la respuesta del código sea 200 para cambiar estado de la
						// afiliación a Procesada
						if (valor.getString("codigo") != null && !valor.getString("codigo").isEmpty()) {
							if (valor.getString("codigo").equals(IConstantes.RESPUESTA_OK_MINISTERIO)
									|| valor.getString("codigo").equals("GN37")) {
								// cambiar estado confa a Finalizada = P
								boolean cambiarEstado = gestionSAT.modificarNotificacionRespuesta(notificacion, IConstantes.ESTADO_PROCESADA);
								if (cambiarEstado) {
									switch (servicioPertenece) {
									case IConstantes.SERVICIO_AFILIACION_PRIMERA_VEZ:
										cambiarEstado = afiliacionesSAT.modificarEstadoAfiliacion(notificacion, IConstantes.ESTADO_PROCESADA);
										break;
									case IConstantes.SERVICIO_AFILIACION_NO_PRIMERA_VEZ:
										cambiarEstado = afiliacionesSAT.modificarEstadoAfiliacionNoPrimeraVez(notificacion, IConstantes.ESTADO_PROCESADA);
										break;
									case IConstantes.SERVICIO_DESAFILIACION:
										cambiarEstado = afiliacionesSAT.modificarEstadoDesafiliacion(notificacion, IConstantes.ESTADO_PROCESADA);
										break;
									case IConstantes.SERVICIO_RELACION_LABORAL:
										cambiarEstado = afiliacionesSAT.modificarEstadoInicioRelacion(notificacion, IConstantes.ESTADO_PROCESADA);
										break;
									case IConstantes.SERVICIO_TERMINACION_RELACION:
										cambiarEstado = afiliacionesSAT.modificarEstadoFinRelacion(notificacion, IConstantes.ESTADO_PROCESADA);
										break;
									case IConstantes.SERVICIO_SUSPENSION:
										break;
									case IConstantes.SERVICIO_LICENCIA:
										break;
									case IConstantes.SERVICIO_MODIFICAR_SALARIO:
										break;
									case IConstantes.SERVICIO_RETIRO_DEFINITIVO:
										break;
									default:
										break;
									}
									if (cambiarEstado) {
										log.info("RespuestaNovedades.responderSolicitudes()-> Notificacion procesada: " + notificacion);
									}
								}
							}
						}
					}
					log.info("RespuestaNovedades.responderSolicitudes()-> enviado " + data);
					log.info("RespuestaNovedades.responderSolicitudes()-> recibido " + valor.toString());
				} else {
					JSONObject valor = new JSONObject(respuesta[1]);
					log.info("RespuestaNovedades.responderSolicitudes()-> enviado Error "	+ data);
					log.info("RespuestaNovedades.responderSolicitudes()-> recibido Error " + valor.toString());
				}
			} else {
				log.error("RespuestaNovedades.responderSolicitudes()-> no hay token dsponible");
				resultado = false;
			}
		} catch (Exception e) {
			log.error("RespuestaNovedades.responderSolicitudes()-> ", e);
		}
		return resultado;
	}
	
	private boolean responderSolicitudesIndePens(String data, int servicio, String notificacion, BaseDatos bd) {
		boolean resultado = false;
		try {
			tb = new TokenBean();
			if (tb != null) {
				String[] respuesta = ConsumoRestWS.comsumoRestPost(data,
						IConstantes.WS_RUTA_BASE + rutas.get(servicio - 1),
						tb.getToken(client_id_token.get(servicio - 1)), true, true, "POST");
				
				if (respuesta[0].equals("true")) {
					JSONObject valor = new JSONObject(respuesta[1]);
					String mensaje = "";

					if (valor.has("codigo") && valor.has("mensaje")) {
						// se valida el mensaje para almacenarlo en BD
						if (valor.getString("mensaje") != null && !valor.getString("mensaje").isEmpty()) {
							mensaje = valor.getString("mensaje").replaceAll("“", "\"");
							mensaje = mensaje.replaceAll("”", "\"");
							resultado = afiliacionesSAT.guardarRespuestaWS(valor.getString("resultado"), mensaje,
									valor.getString("codigo"), notificacion);

						}
						// validar que la respuesta del código sea 200 para cambiar estado de la
						// afiliación a Procesada
						if (valor.getString("codigo") != null && !valor.getString("codigo").isEmpty()) {
							if (valor.getString("codigo").equals(IConstantes.RESPUESTA_OK_MINISTERIO)
									|| valor.getString("codigo").equals("GN37")) {
								// cambiar estado confa a Finalizada = P
								boolean cambiarEstado = gestionSATIndependPens.modificarNotificacionRespuesta(notificacion, IConstantes.ESTADO_PROCESADA);
								if (cambiarEstado) {
									switch (servicio) {
									case IConstantes.SERVICIO_RESPUESTA_AFI_INDEPENDIENTES_PENSIONADOS:
										cambiarEstado = gestionSATIndependPens.modificarEstadoAfiliacionIndePens(notificacion, IConstantes.ESTADO_PROCESADA);
										break;
									case IConstantes.SERVICIO_RESPUESTA_DESAFILIACION_INDEP_PENS:
										cambiarEstado = gestionSATIndependPens.modificarEstadoDesafiliacionIndePens(notificacion, IConstantes.ESTADO_PROCESADA);
										break;
									default:
										break;
									}
									if (cambiarEstado) {
										log.info("RespuestaNovedades.responderSolicitudesIndePens()-> Notificacion procesada: " + notificacion);
									}
								}
							}
						}
					}
					log.info("RespuestaNovedades.responderSolicitudesIndePens()-> enviado " + data);
					log.info("RespuestaNovedades.responderSolicitudesIndePens()-> recibido " + valor.toString());
				} else {
					JSONObject valor = new JSONObject(respuesta[1]);
					log.info("RespuestaNovedades.responderSolicitudesIndePens()-> enviado Error "	+ data);
					log.info("RespuestaNovedades.responderSolicitudesIndePens()-> recibido Error " + valor.toString());
				}
			} else {
				log.error("RespuestaNovedades.responderSolicitudesIndePens()-> no hay token dsponible");
				resultado = false;
			}
		} catch (Exception e) {
			log.error("RespuestaNovedades.responderSolicitudesIndePens()-> ", e);
		}
		return resultado;
	}

}
