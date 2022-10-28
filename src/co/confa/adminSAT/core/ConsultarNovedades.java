package co.confa.adminSAT.core;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;

import co.confa.adminSAT.configuracion.ConsumoRestWS;
import co.confa.adminSAT.configuracion.IConstantes;
import co.confa.adminSAT.configuracion.TokenBean;
import co.confa.adminSAT.implementacion.AfiliacionesImpl;
import co.confa.adminSAT.implementacion.EnvioCorreoImpl;
import co.confa.adminSAT.implementacion.NotificacionesImpl;
import co.confa.adminSAT.modelo.AfiliacionIndependientes;
import co.confa.adminSAT.modelo.AfiliacionNoPrimeraVez;
import co.confa.adminSAT.modelo.AfiliacionPensionados;
import co.confa.adminSAT.implementacion.NotificacionesIndepPensImpl;
import co.confa.adminSAT.modelo.AfiliacionPrimeraVez;
import co.confa.adminSAT.modelo.DesafiliacionEmpresas;
import co.confa.adminSAT.modelo.DesafiliacionIndependientesPensionados;
import co.confa.adminSAT.modelo.FinRelacionLaboral;
import co.confa.adminSAT.modelo.InicioRelacionLaboral;
import co.confa.adminSAT.modelo.NotificacionSAT;
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
public class ConsultarNovedades {
	
	private static final Logger log = Logger.getLogger(ConsultarNovedades.class);
	private Validador validador;
	private NotificacionesImpl gestionSAT;
	private NotificacionesIndepPensImpl gestionSATIndependPens;
	private AfiliacionesImpl afiliacionesSAT;
	private ArrayList<String> rutas = new ArrayList<String>();
	private ArrayList<String> client_id_token = new ArrayList<String>();
	private TokenBean tb;
	Gson gs = new Gson();
	private EnvioCorreoImpl envio;
	
	public ConsultarNovedades()
	{
		validador = new Validador();
		rutas = consultarRutas();
		client_id_token = consultarClientId();
		gestionSAT = new NotificacionesImpl();
		afiliacionesSAT = new AfiliacionesImpl();
		envio = new EnvioCorreoImpl();
		
		gestionSATIndependPens = new NotificacionesIndepPensImpl();
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
			log.error("ERROR: ConsultarNovedades.consultarRutas()-> ", e);
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
		}catch(Exception e) {
			log.error("ERROR: RespuestaNovedades.consultarRutas()-> ", e);
		}finally {
			bd.cerrar();
		}
		return respuesta;
	}
	
	public int consultarTransacciones(int estadoConsultaTransaccion, int servicioConsultar) {
		int respuesta = 0;
		BaseDatos bd = new BaseDatos();
		try {
			ArrayList<NotificacionSAT> notificaciones = 
					gestionSAT.listarTransaccionSinProcesar(IConstantes.ESTADO_SIN_PROCESAR,estadoConsultaTransaccion);
			if(notificaciones.size()>0) {
				tb= new TokenBean();
				if(tb!=null) {
					if(bd.conectar(IConstantes.POOL_INGRESO)) {
						String data = "";
						boolean salida = false;
						for (NotificacionSAT notificacion: notificaciones) {
							respuesta++;
							data = gs.toJson(notificacion).toString();

							//método utilizado para consumir cada transacción 
							salida = procesarNovedad(data,estadoConsultaTransaccion, servicioConsultar, notificacion, bd);
							if(salida) {
								log.info("ConsultarNovedades.consultarTransaccionesAfiPrimeraVez()-> Número transacción almacenada en BD ");	
							} else {
								log.error("ConsultarNovedades.consultarTransaccionesAfiPrimeraVez()-> Número transacción NO almacenado en BD ");
							}
						}
					}
				}
			} else {
				log.info("ConsultarNovedades.consultarTransaccionesAfiPrimeraVez()-> No existen transacciones pendientes por consultar");
			}
			
		}catch (Exception e) {
			log.error("ERROR: ConsultarNovedades.consultarTransaccionesAfiPrimeraVez()-> ", e);
			e.printStackTrace();
		}
		finally {
			bd.cerrar();
		}
		return respuesta;
	}
	
	public int consultarTransaccionesAfiPrimeraVez() {
//		enviarCorreoNotificacion("321456cc6547922",IConstantes.SERVICIO_AFILIACION_PRIMERA_VEZ, "2022-06-07");
//		return 1;
		int respuesta = consultarTransacciones(IConstantes.SERVICIO_AFILIACION_PRIMERA_VEZ, IConstantes.SERVICIO_CONSULTA_AFILIACION);
		return respuesta;
	}
	
	public int consultarTransaccionesAfiNoPrimeraVez() {
		int respuesta = consultarTransacciones(IConstantes.SERVICIO_AFILIACION_NO_PRIMERA_VEZ, IConstantes.SERVICIO_CONSULTA_AFILIACION_NO_PRIMERA_VEZ);
		return respuesta;
	}
	
	public int consultarTransaccionesDesafiliacion() {
		int respuesta = consultarTransacciones(IConstantes.SERVICIO_DESAFILIACION, IConstantes.SERVICIO_CONSULTA_DESAFILIACION);
		return respuesta;
	}
	
	public int consultarTransaccionesInicioRelacionLaboral() {
		int respuesta = consultarTransacciones(IConstantes.SERVICIO_RELACION_LABORAL, IConstantes.SERVICIO_CONSULTA_INICIO_RELACION);
		return respuesta;
	}
	
	public int consultarTransaccionesFinRelacionLaboral() {
		int respuesta = consultarTransacciones(IConstantes.SERVICIO_TERMINACION_RELACION, IConstantes.SERVICIO_CONSULTA_FIN_RELACION);
		return respuesta;
	}

	public boolean procesarAfiPrimeraVez(JSONObject valor) {
		boolean respuesta = false;
		AfiliacionPrimeraVez afiliado = new AfiliacionPrimeraVez();
		RespuestaAfiliacionPrimeraVez respuestaAfi = new RespuestaAfiliacionPrimeraVez();
		RespuestaNovedades respuestaNov = new RespuestaNovedades();
		String numeroTransaccion = (String)valor.get("NumeroTransaccion");
		try {
			//leer respuesta obtenida del número de transacción consultado y crear objeto de afiliacion
			afiliado.setNumeroRadicadoSolicitud((String)valor.get("RadicadoSolicitud"));
			afiliado.setNumeroTransaccion(numeroTransaccion);
			afiliado.setTipoPersona((String)valor.get("TipoPersona"));
			afiliado.setNaturalezaJuridicaEmpleador((int)valor.get("NaturalezaJuridicaEmpleador"));
			afiliado.setTipoDocumentoEmpleador((String)valor.get("TipoDocumentoEmpleador"));
			afiliado.setNumeroDocumentoEmpleador((String)valor.get("NumeroDocumentoEmpleador"));
			afiliado.setSerialSAT((String)valor.get("serialSat")); 
			afiliado.setPrimerNombreEmpleador((String)valor.get("PrimerNombreEmpleador"));
			afiliado.setSegundoNombreEmpleador((String)valor.get("SegundoNombreEmpleador"));
			afiliado.setPrimerApellidoEmpleador((String)valor.get("PrimerApellidoEmpleador"));
			afiliado.setSegundoApellidoEmpleador((String)valor.get("SegundoApellidoEmpleador"));
			afiliado.setFechaSolicitud((String)valor.get("FechaSolicitud"));
			afiliado.setPerdidaAfiliacionCausaGrave((String)valor.get("PerdidaAfiliacionCausaGrave"));
			afiliado.setFechaEfectivaAfiliacion((String)valor.get("FechaEfectivaAfiliacion"));
			afiliado.setRazonSocial((String)valor.get("NombreRazonSocial"));
			afiliado.setNumeroMatriculaMercantil((String)valor.get("NumeroMatriculaMercantil"));
			afiliado.setDepartamento((String)valor.get("Departamento"));
			afiliado.setMunicipio((String)valor.get("Municipio"));
			afiliado.setDireccionContacto((String)valor.get("DireccionContacto"));
			afiliado.setNumeroTelefono((String)valor.get("TelefonoContacto"));
			afiliado.setCorreoElectronico((String)valor.get("CorreoElectronicoContacto"));
			afiliado.setTipoDocumentoRepresentante((String)valor.get("TipoDocumentoRepresentanteLegal"));
			afiliado.setNumeroDocumentoRepresentante((String)valor.get("NumeroDocumentoRepresentanteLegal")); 
			afiliado.setPrimerNombreRepresentante((String)valor.get("PrimerNombreRepresentanteLegal"));
			afiliado.setSegundoNombreRepresentante((String)valor.get("SegundoNombreRepresentanteLegal")); 
			afiliado.setPrimerApellidoRepresentante((String)valor.get("PrimerApellidoRepresentanteLegal")); 
			afiliado.setSegundoApellidoRepresentante((String)valor.get("SegundoApellidoRepresentanteLegal"));
			afiliado.setAutorizacionManejoDatos((String)valor.get("AutorizacionManejoDatosPersonales")); 
			afiliado.setAutorizacionNotificaciones((String)valor.get("AutorizacionEnvioNotificaciones")); 
			afiliado.setManifestacion((String)valor.get("ManifestacionNoAfiliacionPrevia")); 
			afiliado.setEstadoConfa(IConstantes.ESTADO_SIN_PROCESAR);
			
			//revisar usando la malla de validación
			List<String> errores = validador.validarAfiliacion(afiliado);
			if(errores.isEmpty()) {
				//insercion en la bd
				respuesta = afiliacionesSAT.guardarAfiliacionPrimeraVez(afiliado);
				/*if(salida) {
					respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_CORRECTA + "\",\"mensaje\": \""
							+ IConstantes.RESPUESTA_CORRECTA + "\"}";
				} else {
					respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_INSERCION_BD + "\",\"mensaje\": \""
							+ IConstantes.RESPUESTA_ERROR_INSERCION_BD + "\"}";
				}*/
				
			} else {
				
				String glosaEstructura = "";
				int cantidadGlosas=0;
				ArrayList<String> descripcionErrores = new ArrayList<String>();
				for (int i=0; i < errores.size(); i++) {
					glosaEstructura += errores.get(i);
					//almacenar glosas de estructura al momento de consultar la afiliacion
					boolean almacenarError = afiliacionesSAT.guardarErroresAfiPrimeraVez(errores.get(i), numeroTransaccion);
					if(almacenarError) {
						String descErrores = afiliacionesSAT.listarDescripcionGlosas(errores.get(i));
						if(descripcionErrores.size()<2)
							descripcionErrores.add(descErrores);
						cantidadGlosas++;
					}
					if(i+1 < errores.size())
						glosaEstructura += ", ";
				}
				
				String salida = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_DATOS_INVALIDOS + "\",\"mensaje\": \""
						+ glosaEstructura + "\"}";
				
				log.error("ERROR: ConsultarNovedades.procesarAfiPrimeraVez(data)->" + salida);
			    log.error("ERROR: ConsultarNovedades.procesarAfiPrimeraVez(data)-> glosas de estructura:"+cantidadGlosas);
								
				
				//respuesta automática de la afiliación por primera vez
				respuestaAfi.setNumeroRadicadoSolicitud((String)valor.get("RadicadoSolicitud"));
				respuestaAfi.setNumeroTransaccion(numeroTransaccion);
				respuestaAfi.setTipoDocumentoEmpleador((String)valor.get("TipoDocumentoEmpleador"));
				respuestaAfi.setNumeroDocumentoEmpleador((String)valor.get("NumeroDocumentoEmpleador"));
				respuestaAfi.setSerialSat((String)valor.get("serialSat"));
				respuestaAfi.setResultadoTramite("2"); //resultado de rechazo
				respuestaAfi.setMotivoRechazo(descripcionErrores.get(0)+" "+descripcionErrores.get(1));
				respuestaAfi.setFechaEfectivaAfiliacion(""); //fecha efectiva de afiliación debe ir nula
				
				//método que envía respuesta automática de error de glosa
				String data = "";
				data = gs.toJson(respuestaAfi).toString();
				respuestaNov.responderTransaccionesGlosas(data, IConstantes.SERVICIO_RESPUESTA_AFILIACION,numeroTransaccion);
				
			}
			
		} catch (Exception e) {	
			log.error("ERROR: ConsultarNovedades.procesarAfiPrimeraVez(data)-> ", e);
		}
		return respuesta;
	}
	


	public boolean procesarAfiNoPrimeraVez(JSONObject valor) {
		boolean respuesta = false;
		AfiliacionNoPrimeraVez afiliado = new AfiliacionNoPrimeraVez();
		RespuestaAfiliacionNoPrimeraVez respuestaAfi = new RespuestaAfiliacionNoPrimeraVez();
		RespuestaNovedades respuestaNov = new RespuestaNovedades();
		String numeroTransaccion = (String)valor.get("NumeroTransaccion");
		try {
			// leer respuesta obtenida del número de transacción consultado y crear objeto
			// de afiliacion
			afiliado.setNumeroRadicadoSolicitud((String) valor.get("RadicadoSolicitud"));
			afiliado.setNumeroTransaccion(numeroTransaccion);
			afiliado.setTipoPersona((String) valor.get("TipoPersona"));
			afiliado.setNaturalezaJuridicaEmpleador((int) valor.get("NaturalezaJuridicaEmpleador"));
			afiliado.setTipoDocumentoEmpleador((String) valor.get("TipoDocumentoEmpleador"));
			afiliado.setNumeroDocumentoEmpleador((String) valor.get("NumeroDocumentoEmpleador"));
			afiliado.setSerialSAT((String) valor.get("serialSat"));
			afiliado.setPrimerNombreEmpleador((String) valor.get("PrimerNombreEmpleador"));
			afiliado.setSegundoNombreEmpleador((String) valor.get("SegundoNombreEmpleador"));
			afiliado.setPrimerApellidoEmpleador((String) valor.get("PrimerApellidoEmpleador"));
			afiliado.setSegundoApellidoEmpleador((String) valor.get("SegundoApellidoEmpleador"));
			afiliado.setFechaSolicitud((String) valor.get("FechaSolicitud"));
			afiliado.setPerdidaAfiliacionCausaGrave((String) valor.get("PerdidaAfiliacionCausaGrave"));
			afiliado.setFechaEfectivaAfiliacion((String) valor.get("FechaEfectivaAfiliacion"));
			afiliado.setRazonSocial((String) valor.get("NombreRazonSocial"));
			afiliado.setNumeroMatriculaMercantil((String) valor.get("NumeroMatriculaMercantil"));
			afiliado.setDepartamento((String) valor.get("Departamento"));
			afiliado.setMunicipio((String) valor.get("Municipio"));
			afiliado.setDireccionContacto((String) valor.get("DireccionContacto"));
			afiliado.setNumeroTelefono((String) valor.get("TelefonoContacto"));
			afiliado.setCorreoElectronico((String) valor.get("CorreoElectronicoContacto"));
			afiliado.setTipoDocumentoRepresentante((String) valor.get("TipoDocumentoRepresentanteLegal"));
			afiliado.setNumeroDocumentoRepresentante((String) valor.get("NumeroDocumentoRepresentanteLegal"));
			afiliado.setPrimerNombreRepresentante((String) valor.get("PrimerNombreRepresentanteLegal"));
			afiliado.setSegundoNombreRepresentante((String) valor.get("SegundoNombreRepresentanteLegal"));
			afiliado.setPrimerApellidoRepresentante((String) valor.get("PrimerApellidoRepresentanteLegal"));
			afiliado.setSegundoApellidoRepresentante((String) valor.get("SegundoApellidoRepresentanteLegal"));
			afiliado.setAutorizacionManejoDatos((String) valor.get("AutorizacionManejoDatosPersonales"));
			afiliado.setAutorizacionNotificaciones((String) valor.get("AutorizacionEnvioNotificaciones"));
			afiliado.setManifestacion((String) valor.get("ManifestacionNoAfiliacionPrevia"));
			afiliado.setCodigoCcfAnterior((String) valor.get("CodigoCcfAnterior"));
			afiliado.setPazSalvo((String) valor.get("PazSalvo"));
			afiliado.setFechaPazSalvo((String) valor.get("FechaPazSalvo"));
			afiliado.setEstadoConfa(IConstantes.ESTADO_SIN_PROCESAR);

			// revisar usando la malla de validación
			List<String> errores = validador.validarAfiliacionNoPrimeraVez(afiliado);
			if (errores.isEmpty()) {
				// insercion en la bd
				respuesta = afiliacionesSAT.guardarAfiliacionNoPrimeraVez(afiliado);
				/*
				 * if(salida) { respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_CORRECTA +
				 * "\",\"mensaje\": \"" + IConstantes.RESPUESTA_CORRECTA + "\"}"; } else {
				 * respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_INSERCION_BD +
				 * "\",\"mensaje\": \"" + IConstantes.RESPUESTA_ERROR_INSERCION_BD + "\"}"; }
				 */

			} else {
				
				String glosaEstructura = "";
				int cantidadGlosas=0;
				ArrayList<String> descripcionErrores = new ArrayList<String>();
				for (int i=0; i < errores.size(); i++) {
					glosaEstructura += errores.get(i);
					//almacenar glosas de estructura al momento de consultar la afiliacion
					boolean almacenarError = afiliacionesSAT.guardarErroresAfiPrimeraVez(errores.get(i), numeroTransaccion);
					if(almacenarError) {
						String descErrores = afiliacionesSAT.listarDescripcionGlosas(errores.get(i));
						if(descripcionErrores.size()<2)
							descripcionErrores.add(descErrores);
						cantidadGlosas++;
					}
					if(i+1 < errores.size())
						glosaEstructura += ", ";
				}
				
				String salida = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_DATOS_INVALIDOS + "\",\"mensaje\": \""
						+ glosaEstructura + "\"}";
				
				log.error("ERROR: ConsultarNovedades.procesarAfiNoPrimeraVez(data)->" + salida);
			    log.error("ERROR: ConsultarNovedades.procesarAfiNoPrimeraVez(data)-> glosas de estructura:"+cantidadGlosas);
								
				
				//respuesta automática de la afiliación no primera vez
				respuestaAfi.setNumeroRadicadoSolicitud((String)valor.get("RadicadoSolicitud"));
				respuestaAfi.setNumeroTransaccion(numeroTransaccion);
				respuestaAfi.setTipoDocumentoEmpleador((String)valor.get("TipoDocumentoEmpleador"));
				respuestaAfi.setNumeroDocumentoEmpleador((String)valor.get("NumeroDocumentoEmpleador"));
				respuestaAfi.setSerialSat((String)valor.get("serialSat"));
				respuestaAfi.setResultadoTramite("2"); //resultado de rechazo
				respuestaAfi.setMotivoRechazo(descripcionErrores.get(0)+" "+descripcionErrores.get(1));
				respuestaAfi.setFechaEfectivaAfiliacion(""); //fecha efectiva de afiliación debe ir nula
				
				//método que envía respuesta automática de error de glosa
				//método que envía respuesta automática de error de glosa
				String data = "";
				data = gs.toJson(respuestaAfi).toString();
				respuestaNov.responderTransaccionesGlosas(data, IConstantes.SERVICIO_RESPUESTA_AFILIACION_NO_PRIMERA_VEZ,numeroTransaccion);
				
			}

		} catch (Exception e) {
			log.error("ERROR: ConsultarNovedades.procesarAfiPrimeraVez(data, bd)-> ", e);
		}
		return respuesta;
	}

	public boolean procesarDesafiliacion(JSONObject valor) {
		boolean respuesta = false;
		DesafiliacionEmpresas desafiliado = new DesafiliacionEmpresas();
		RespuestaDesafiliacionCCF respuestaAfi = new RespuestaDesafiliacionCCF();
		RespuestaNovedades respuestaNov = new RespuestaNovedades();
		String numeroTransaccion = (String)valor.get("NumeroTransaccion");
		try {
			// leer respuesta obtenida del número de transacción consultado y crear objeto
			// de desafiliacion
			desafiliado.setNumeroRadicadoSolicitud((String) valor.get("RadicadoSolicitud"));
			desafiliado.setNumeroTransaccion(numeroTransaccion);
			desafiliado.setTipoDocumentoEmpleador((String) valor.get("TipoDocumentoEmpleador"));
			desafiliado.setNumeroDocumentoEmpleador((String) valor.get("NumeroDocumentoEmpleador"));
			desafiliado.setSerialSAT((String) valor.get("serialSat"));
			desafiliado.setFechaSolicitudDesafiliacion((String) valor.get("FechaSolicitudDesafiliacion"));
			desafiliado.setFechaEfectivaDesafiliacion((String) valor.get("FechaEfectivaDesafiliacion"));
			desafiliado.setDepartamento((String) valor.get("Departamento"));
			desafiliado.setAutorizacionManejoDatos((String) valor.get("AutorizacionManejoDatosPersonales"));
			desafiliado.setAutorizacionNotificaciones((String) valor.get("AutorizacionEnvioNotificaciones"));
			desafiliado.setPazSalvo((String) valor.get("PazSalvo"));
			desafiliado.setFechaPazSalvo((String) valor.get("FechaPazSalvo"));
			desafiliado.setEstadoConfa(IConstantes.ESTADO_SIN_PROCESAR);

			// revisar usando la malla de validación
			List<String> errores = validador.validarDesafiliacion(desafiliado);
			if (errores.isEmpty()) {
				// insercion en la bd
				respuesta = afiliacionesSAT.guardarDesafiliacion(desafiliado);
				/*
				 * if(salida) { respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_CORRECTA +
				 * "\",\"mensaje\": \"" + IConstantes.RESPUESTA_CORRECTA + "\"}"; } else {
				 * respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_INSERCION_BD +
				 * "\",\"mensaje\": \"" + IConstantes.RESPUESTA_ERROR_INSERCION_BD + "\"}"; }
				 */

			} else {
				
				String glosaEstructura = "";
				int cantidadGlosas=0;
				ArrayList<String> descripcionErrores = new ArrayList<String>();
				for (int i=0; i < errores.size(); i++) {
					glosaEstructura += errores.get(i);
					//almacenar glosas de estructura al momento de consultar la afiliacion
					boolean almacenarError = afiliacionesSAT.guardarErroresAfiPrimeraVez(errores.get(i), numeroTransaccion);
					if(almacenarError) {
						String descErrores = afiliacionesSAT.listarDescripcionGlosas(errores.get(i));
						if(descripcionErrores.size()<2)
							descripcionErrores.add(descErrores);
						cantidadGlosas++;
					}
					if(i+1 < errores.size())
						glosaEstructura += ", ";
				}
				
				String salida = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_DATOS_INVALIDOS + "\",\"mensaje\": \""
						+ glosaEstructura + "\"}";
				
				log.error("ERROR: ConsultarNovedades.procesarDesafiliacion(data)->" + salida);
			    log.error("ERROR: ConsultarNovedades.procesarDesafiliacion(data)-> glosas de estructura:"+cantidadGlosas);
								
				
				//respuesta automática de la afiliación no primera vez
				respuestaAfi.setNumeroRadicadoSolicitud((String)valor.get("RadicadoSolicitud"));
				respuestaAfi.setNumeroTransaccion(numeroTransaccion);
				respuestaAfi.setTipoDocumentoEmpleador((String)valor.get("TipoDocumentoEmpleador"));
				respuestaAfi.setNumeroDocumentoEmpleador((String)valor.get("NumeroDocumentoEmpleador"));
				respuestaAfi.setSerialSat((String)valor.get("serialSat"));
				respuestaAfi.setResultadoTramite("2"); //resultado de rechazo
				respuestaAfi.setMotivoRechazo(descripcionErrores.get(0)+" "+descripcionErrores.get(1));
				respuestaAfi.setFechaEfectivaDesafiliacion(""); //fecha efectiva de la dafiliación debe ir nula
				
				//método que envía respuesta automática de error de glosa
				String data = "";
				data = gs.toJson(respuestaAfi).toString();
				respuestaNov.responderTransaccionesGlosas(data, IConstantes.SERVICIO_RESPUESTA_DESAFILIACION,numeroTransaccion);
				
			}

		} catch (Exception e) {
			log.error("ERROR: ConsultarNovedades.procesarDesafiliacion(data, bd)-> ", e);
		}
		return respuesta;
	}

	public boolean procesarInicioRelacionLaboral(JSONObject valor) {
		boolean respuesta = false;
		InicioRelacionLaboral afiliado = new InicioRelacionLaboral();
		RespuestaInicioRelacion respuestaAfi = new RespuestaInicioRelacion();
		RespuestaNovedades respuestaNov = new RespuestaNovedades();
		String numeroTransaccion = (String)valor.get("NumeroTransaccion");
		try {
			// leer respuesta obtenida del número de transacción consultado y crear objeto
			// de desafiliacion
			afiliado.setNumeroRadicadoSolicitud((String) valor.get("RadicadoSolicitud"));
			afiliado.setNumeroTransaccion(numeroTransaccion);
			afiliado.setTipoDocumentoEmpleador((String) valor.get("TipoDocumentoEmpleador"));
			afiliado.setNumeroDocumentoEmpleador((String) valor.get("NumeroDocumentoEmpleador"));
			afiliado.setSerialSAT((String) valor.get("serialSat"));
			afiliado.setTipoInicio((String) valor.get("TipoInicio"));
			afiliado.setFechaInicio((String) valor.get("FechaInicio"));
			afiliado.setTipoDocumentoTrabajador((String) valor.get("TipoDocumentoTrabajador"));
			afiliado.setNumeroDocumentoTrabajador((String) valor.get("NumeroDocumentoTrabajador"));
			afiliado.setPrimerNombreTrabajador((String) valor.get("PrimerNombreTrabajador"));
			afiliado.setPrimerApellidoTrabajador((String) valor.get("PrimerApellidoTrabajador"));
			afiliado.setSegundoNombreTrabajador((String) valor.get("SegundoNombreTrabajador"));
			afiliado.setSegundoApellidoTrabajador((String) valor.get("SegundoApellidoTrabajador"));
			afiliado.setSexoTrabajador((String) valor.get("SexoTrabajador"));
			afiliado.setFechaNacimientoTrabajador((String) valor.get("FechaNacimientoTrabajador"));
			afiliado.setDepartamento((String) valor.get("Departamento"));
			afiliado.setMunicipio((String) valor.get("Municipio"));
			afiliado.setDireccionContacto((String) valor.get("DireccionContacto"));
			afiliado.setNumeroTelefono((String) valor.get("TelefonoContacto"));
			afiliado.setCorreoElectronico((String) valor.get("CorreoElectronicoContacto"));
			afiliado.setSalario((String) valor.get("Salario"));
			afiliado.setTipoSalario((String) valor.get("TipoSalario"));
			afiliado.setHorasTrabajo((String) valor.get("HorasTrabajo"));
			afiliado.setAutorizacionManejoDatos((String) valor.get("AutorizacionManejoDatosPersonales"));
			afiliado.setAutorizacionNotificaciones((String) valor.get("AutorizacionEnvioNotificaciones"));
			afiliado.setEstadoConfa(IConstantes.ESTADO_SIN_PROCESAR);

			// revisar usando la malla de validación
			List<String> errores = validador.validarInicioRelacionLabora(afiliado);
			if (errores.isEmpty()) {
				// insercion en la bd
				respuesta = afiliacionesSAT.guardarInicioRelacionLaboral(afiliado);
				/*
				 * if(salida) { respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_CORRECTA +
				 * "\",\"mensaje\": \"" + IConstantes.RESPUESTA_CORRECTA + "\"}"; } else {
				 * respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_INSERCION_BD +
				 * "\",\"mensaje\": \"" + IConstantes.RESPUESTA_ERROR_INSERCION_BD + "\"}"; }
				 */

			} else {
				
				String glosaEstructura = "";
				int cantidadGlosas=0;
				ArrayList<String> descripcionErrores = new ArrayList<String>();
				for (int i=0; i < errores.size(); i++) {
					glosaEstructura += errores.get(i);
					//almacenar glosas de estructura al momento de consultar la afiliacion
					boolean almacenarError = afiliacionesSAT.guardarErroresAfiPrimeraVez(errores.get(i), numeroTransaccion);
					if(almacenarError) {
						String descErrores = afiliacionesSAT.listarDescripcionGlosas(errores.get(i));
						if(descripcionErrores.size()<2)
							descripcionErrores.add(descErrores);
						cantidadGlosas++;
					}
					if(i+1 < errores.size())
						glosaEstructura += ", ";
				}
				
				String salida = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_DATOS_INVALIDOS + "\",\"mensaje\": \""
						+ glosaEstructura + "\"}";
				
				log.error("ERROR: ConsultarNovedades.procesarInicioRelacionLaboral(data)->" + salida);
			    log.error("ERROR: ConsultarNovedades.procesarInicioRelacionLaboral(data)-> glosas de estructura:"+cantidadGlosas);
								
				
				//respuesta automática de la afiliación no primera vez
				respuestaAfi.setNumeroRadicadoSolicitud((String)valor.get("RadicadoSolicitud"));
				respuestaAfi.setNumeroTransaccion(numeroTransaccion);
				respuestaAfi.setTipoDocumentoEmpleador((String)valor.get("TipoDocumentoEmpleador"));
				respuestaAfi.setNumeroDocumentoEmpleador((String)valor.get("NumeroDocumentoEmpleador"));
				respuestaAfi.setSerialSat((String)valor.get("serialSat"));
				respuestaAfi.setResultadoTramite("2"); //resultado de rechazo
				respuestaAfi.setMotivoRechazo(descripcionErrores.get(0)+" "+descripcionErrores.get(1));
				
				//método que envía respuesta automática de error de glosa
				String data = "";
				data = gs.toJson(respuestaAfi).toString();
				respuestaNov.responderTransaccionesGlosas(data, IConstantes.SERVICIO_RESPUESTA_INICIO_RELACION,numeroTransaccion);
				
			}

		} catch (Exception e) {
			log.error("ERROR: ConsultarNovedades.procesarInicioRelacionLaboral(data, bd)-> ", e);
		}
		return respuesta;
	}

	public boolean procesarFinRelacionLaboral(JSONObject valor) {
		boolean respuesta = false;
		FinRelacionLaboral afiliado = new FinRelacionLaboral();
		RespuestaFinRelacion respuestaAfi = new RespuestaFinRelacion();
		RespuestaNovedades respuestaNov = new RespuestaNovedades();
		String numeroTransaccion = (String)valor.get("NumeroTransaccion");
		try {
			// leer respuesta obtenida del número de transacción consultado y crear objeto
			// de desafiliacion
			afiliado.setNumeroRadicadoSolicitud((String) valor.get("RadicadoSolicitud"));
			afiliado.setNumeroTransaccion(numeroTransaccion);
			afiliado.setTipoDocumentoEmpleador((String) valor.get("TipoDocumentoEmpleador"));
			afiliado.setNumeroDocumentoEmpleador((String) valor.get("NumeroDocumentoEmpleador"));
			afiliado.setSerialSAT((String) valor.get("serialSat"));
			afiliado.setTipoTerminacion((String) valor.get("TipoTerminacion"));
			afiliado.setFechaTerminacion((String) valor.get("FechaTerminacion"));
			afiliado.setTipoDocumentoTrabajador((String) valor.get("TipoDocumentoTrabajador"));
			afiliado.setNumeroDocumentoTrabajador((String) valor.get("NumeroDocumentoTrabajador"));
			afiliado.setPrimerNombreTrabajador((String) valor.get("PrimerNombreTrabajador"));
			afiliado.setPrimerApellidoTrabajador((String) valor.get("PrimerApellidoTrabajador"));
			afiliado.setAutorizacionManejoDatos((String) valor.get("AutorizacionManejoDatosPersonales"));
			afiliado.setAutorizacionNotificaciones((String) valor.get("AutorizacionEnvioNotificaciones"));
			afiliado.setEstadoConfa(IConstantes.ESTADO_SIN_PROCESAR);

			// revisar usando la malla de validación
			List<String> errores = validador.validarFinRelacionLabora(afiliado);
			if (errores.isEmpty()) {
				// insercion en la bd
				respuesta = afiliacionesSAT.guardarFinRelacionLaboral(afiliado);
				/*
				 * if(salida) { respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_CORRECTA +
				 * "\",\"mensaje\": \"" + IConstantes.RESPUESTA_CORRECTA + "\"}"; } else {
				 * respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_INSERCION_BD +
				 * "\",\"mensaje\": \"" + IConstantes.RESPUESTA_ERROR_INSERCION_BD + "\"}"; }
				 */

			} else {
				
				String glosaEstructura = "";
				int cantidadGlosas=0;
				ArrayList<String> descripcionErrores = new ArrayList<String>();
				for (int i=0; i < errores.size(); i++) {
					glosaEstructura += errores.get(i);
					//almacenar glosas de estructura al momento de consultar la afiliacion
					boolean almacenarError = afiliacionesSAT.guardarErroresAfiPrimeraVez(errores.get(i), numeroTransaccion);
					if(almacenarError) {
						String descErrores = afiliacionesSAT.listarDescripcionGlosas(errores.get(i));
						if(descripcionErrores.size()<2)
							descripcionErrores.add(descErrores);
						cantidadGlosas++;
					}
					if(i+1 < errores.size())
						glosaEstructura += ", ";
				}
				
				String salida = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_DATOS_INVALIDOS + "\",\"mensaje\": \""
						+ glosaEstructura + "\"}";
				
				log.error("ERROR: ConsultarNovedades.procesarFinRelacionLaboral(data)->" + salida);
			    log.error("ERROR: ConsultarNovedades.procesarFinRelacionLaboral(data)-> glosas de estructura:"+cantidadGlosas);
								
				
				//respuesta automática de la afiliación no primera vez
				respuestaAfi.setNumeroRadicadoSolicitud((String)valor.get("RadicadoSolicitud"));
				respuestaAfi.setNumeroTransaccion(numeroTransaccion);
				respuestaAfi.setTipoDocumentoEmpleador((String)valor.get("TipoDocumentoEmpleador"));
				respuestaAfi.setNumeroDocumentoEmpleador((String)valor.get("NumeroDocumentoEmpleador"));
				respuestaAfi.setSerialSat((String)valor.get("serialSat"));
				respuestaAfi.setResultadoTramite("2"); //resultado de rechazo
				respuestaAfi.setMotivoRechazo(descripcionErrores.get(0)+" "+descripcionErrores.get(1));
				
				//método que envía respuesta automática de error de glosa
				String data = "";
				data = gs.toJson(respuestaAfi).toString();
				respuestaNov.responderTransaccionesGlosas(data, IConstantes.SERVICIO_RESPUESTA_FIN_RELACION,numeroTransaccion);
				
			}

		} catch (Exception e) {
			log.error("ERROR: ConsultarNovedades.procesarFinRelacionLaboral(data, bd)-> ", e);
		}
		return respuesta;
	}
	
	public boolean procesarAfiIndependiente(JSONObject valor) {
		boolean respuesta = false;
		AfiliacionIndependientes afiliado = new AfiliacionIndependientes();
		RespuestaAfiliacionIndependientesPensionados respuestaAfi = new RespuestaAfiliacionIndependientesPensionados();
		RespuestaNovedades respuestaNov = new RespuestaNovedades();
		String numeroTransaccion = (String)valor.get("NumeroTransaccion");
		try {
			//leer respuesta obtenida del número de transacción consultado y crear objeto de afiliacion
			afiliado.setNumeroRadicadoSolicitud((String)valor.get("NumeroRadicadoSolicitud"));
			afiliado.setNumeroTransaccion(numeroTransaccion);
			afiliado.setTipoAfiliado((String)valor.get("TipoAfiliado"));
			afiliado.setTipoDocumentoSolicitante((String)valor.get("TipoDocumentoSolicitante"));
			afiliado.setNumeroDocumentoSolicitante((String)valor.get("NumeroDocumentoSolicitante"));
			afiliado.setPrimerNombreSolicitante((String)valor.get("PrimerNombreSolicitante"));
			afiliado.setSegundoNombreSolicitante((String)valor.get("SegundoNombreSolicitante"));
			afiliado.setPrimerApellidoSolicitante((String)valor.get("PrimerApellidoSolicitante"));
			afiliado.setSegundoApellidoSolicitante((String)valor.get("SegundoApellidoSolicitante"));
			afiliado.setFechaSolicitud((String)valor.get("FechaSolicitud"));
			afiliado.setFechaEfectivaAfiliacion((String)valor.get("FechaEfectivaAfiliacion"));
			afiliado.setDepartamento((String)valor.get("Departamento"));
			afiliado.setMunicipio((String)valor.get("Municipio"));
			afiliado.setPais((String)valor.get("Pais"));
			afiliado.setCiudad((String)valor.get("Ciudad"));
			afiliado.setDireccionDomicilio((String)valor.get("DireccionDomicilio"));
			afiliado.setNumeroTelefono((String)valor.get("NumeroTelefono"));
			afiliado.setCorreoElectronico((String)valor.get("CorreoElectronico"));
			afiliado.setEstadoCivil((String)valor.get("EstadoCivil"));
			afiliado.setValorMensualIngresos((String)valor.get("ValorMensualIngresos"));
			afiliado.setPerdidaAfiliacionCausaGrave((String) valor.get("PerdidaAfiliacionCausaGrave"));
			afiliado.setDeclaracionFuenteIngresos((String) valor.get("DeclaracionFuenteIngresos"));
			afiliado.setDeclaracionVeracidadInformacion((String) valor.get("DeclaracionVeracidadInformacion"));
			afiliado.setCodigoCCF((String) valor.get("CodigoCCF"));
			afiliado.setPazSalvo((String) valor.get("PazSalvo"));
			afiliado.setFechaPazSalvo((String) valor.get("FechaPazSalvo"));
			afiliado.setAutorizacionManejoDatos((String)valor.get("AutorizacionManejoDatosPersonales")); 
			afiliado.setAutorizacionNotificaciones((String)valor.get("AutorizacionEnvioNotificaciones")); 
			afiliado.setAutorizacionManejoDatosCCF((String)valor.get("AutorizacionManejoDatosPersonalesCCF")); 
			afiliado.setAutorizacionNotificacionesCCF((String)valor.get("AutorizacionEnvioNotificacionesCCF")); 
			afiliado.setManifestacion((String)valor.get("Manifestacion")); 
			afiliado.setEstadoConfa(IConstantes.ESTADO_SIN_PROCESAR);
			
			//revisar usando la malla de validación
			List<String> errores = validador.validarAfiliacionIndependiente(afiliado);
			if(errores.isEmpty()) {
				//insercion en la bd
				respuesta = gestionSATIndependPens.guardarAfiliacionIndependiente(afiliado);
				/*if(salida) {
					respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_CORRECTA + "\",\"mensaje\": \""
							+ IConstantes.RESPUESTA_CORRECTA + "\"}";
				} else {
					respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_INSERCION_BD + "\",\"mensaje\": \""
							+ IConstantes.RESPUESTA_ERROR_INSERCION_BD + "\"}";
				}*/
				
			} else {
				
				String glosaEstructura = "";
				int cantidadGlosas=0;
				ArrayList<String> descripcionErrores = new ArrayList<String>();
				for (int i=0; i < errores.size(); i++) {
					glosaEstructura += errores.get(i);
					//almacenar glosas de estructura al momento de consultar la afiliacion
					boolean almacenarError = afiliacionesSAT.guardarErroresAfiPrimeraVez(errores.get(i), numeroTransaccion);
					if(almacenarError) {
						String descErrores = afiliacionesSAT.listarDescripcionGlosas(errores.get(i));
						if(descripcionErrores.size()<2)
							descripcionErrores.add(descErrores);
						cantidadGlosas++;
					}
					if(i+1 < errores.size())
						glosaEstructura += ", ";
				}
				
				String salida = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_DATOS_INVALIDOS + "\",\"mensaje\": \""
						+ glosaEstructura + "\"}";
				
				log.error("ERROR: ConsultarNovedades.procesarAfiIndependiente(data)->" + salida);
			    log.error("ERROR: ConsultarNovedades.procesarAfiIndependiente(data)-> glosas de estructura:"+cantidadGlosas);
								
				
				//respuesta automática de la afiliación de independientes
				respuestaAfi.setNumeroRadicadoSolicitud((String)valor.get("RadicadoSolicitud"));
				respuestaAfi.setNumeroTransaccion(numeroTransaccion);
				respuestaAfi.setTipoDocumentoSolicitante((String)valor.get("TipoDocumentoSolicitante"));
				respuestaAfi.setNumeroDocumentoSolicitante((String)valor.get("NumeroDocumentoSolicitante"));
				respuestaAfi.setResultadoTramite("2"); //resultado de rechazo
				respuestaAfi.setFechaEfectivaAfiliacion(""); //fecha efectiva de afiliación debe ir nula
				respuestaAfi.setMotivoRechazo(descripcionErrores.get(0)+" "+descripcionErrores.get(1));
				respuestaAfi.setFechaRechazo(LocalDate.now().toString());
				
				//método que envía respuesta automática de error de glosa
				String data = "";
				data = gs.toJson(respuestaAfi).toString();
				respuestaNov.responderTransaccionesGlosas(data, IConstantes.SERVICIO_RESPUESTA_AFI_INDEPENDIENTES_PENSIONADOS,numeroTransaccion);
				
			}
			
		} catch (Exception e) {	
			log.error("ERROR: ConsultarNovedades.procesarAfiIndependiente(data)-> ", e);
		}
		return respuesta;
	}
	
	public boolean procesarAfiPensionado(JSONObject valor) {
		boolean respuesta = false;
		AfiliacionPensionados afiliado = new AfiliacionPensionados();
		RespuestaAfiliacionIndependientesPensionados respuestaAfi = new RespuestaAfiliacionIndependientesPensionados();
		RespuestaNovedades respuestaNov = new RespuestaNovedades();
		String numeroTransaccion = (String)valor.get("NumeroTransaccion");
		try {
			//leer respuesta obtenida del número de transacción consultado y crear objeto de afiliacion
			afiliado.setNumeroRadicadoSolicitud((String)valor.get("NumeroRadicadoSolicitud"));
			afiliado.setNumeroTransaccion(numeroTransaccion);
			afiliado.setTipoAfiliado((String)valor.get("TipoAfiliado"));
			afiliado.setTipoDocumentoSolicitante((String)valor.get("TipoDocumentoSolicitante"));
			afiliado.setNumeroDocumentoSolicitante((String)valor.get("NumeroDocumentoSolicitante"));
			afiliado.setPrimerNombreSolicitante((String)valor.get("PrimerNombreSolicitante"));
			afiliado.setSegundoNombreSolicitante((String)valor.get("SegundoNombreSolicitante"));
			afiliado.setPrimerApellidoSolicitante((String)valor.get("PrimerApellidoSolicitante"));
			afiliado.setSegundoApellidoSolicitante((String)valor.get("SegundoApellidoSolicitante"));
			afiliado.setFechaSolicitud((String)valor.get("FechaSolicitud"));
			afiliado.setFechaEfectivaAfiliacion((String)valor.get("FechaEfectivaAfiliacion"));
			afiliado.setDepartamento((String)valor.get("Departamento"));
			afiliado.setMunicipio((String)valor.get("Municipio"));
			afiliado.setDireccionDomicilio((String)valor.get("DireccionDomicilio"));
			afiliado.setNumeroTelefono((String)valor.get("NumeroTelefono"));
			afiliado.setCorreoElectronico((String)valor.get("CorreoElectronicoContacto"));
			afiliado.setValorMensualIngresos((String)valor.get("ValorMensualIngresos"));
			afiliado.setDeclaracionFuenteIngresos((String) valor.get("DeclaracionFuenteIngresos"));
			afiliado.setPerdidaAfiliacionCausaGrave((String) valor.get("PerdidaAfiliacionCausaGrave"));
			afiliado.setDeclaracionVeracidadInformacion((String) valor.get("DeclaracionVeracidadInformacion"));
			afiliado.setAutorizacionManejoDatos((String)valor.get("AutorizacionManejoDatosPersonales")); 
			afiliado.setAutorizacionNotificaciones((String)valor.get("AutorizacionEnvioNotificaciones")); 
			afiliado.setAutorizacionManejoDatosCCF((String)valor.get("AutorizacionManejoDatosPersonalesCCF")); 
			afiliado.setAutorizacionNotificacionesCCF((String)valor.get("AutorizacionEnvioNotificacionesCCF")); 
			afiliado.setManifestacion((String)valor.get("Manifestacion")); 
			afiliado.setCodigoCCF((String) valor.get("CodigoCCF"));
			afiliado.setPazSalvo((String) valor.get("PazSalvo"));
			afiliado.setFechaPazSalvo((String) valor.get("FechaPazSalvo"));	
			afiliado.setEstadoConfa(IConstantes.ESTADO_SIN_PROCESAR);
			
			//revisar usando la malla de validación
			List<String> errores = validador.validarAfiliacionPensionado(afiliado);
			if(errores.isEmpty()) {
				//insercion en la bd
				respuesta = gestionSATIndependPens.guardarAfiliacionPensionado(afiliado);
				/*if(salida) {
					respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_CORRECTA + "\",\"mensaje\": \""
							+ IConstantes.RESPUESTA_CORRECTA + "\"}";
				} else {
					respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_INSERCION_BD + "\",\"mensaje\": \""
							+ IConstantes.RESPUESTA_ERROR_INSERCION_BD + "\"}";
				}*/
				
			} else {
				
				String glosaEstructura = "";
				int cantidadGlosas=0;
				ArrayList<String> descripcionErrores = new ArrayList<String>();
				for (int i=0; i < errores.size(); i++) {
					glosaEstructura += errores.get(i);
					//almacenar glosas de estructura al momento de consultar la afiliacion
					boolean almacenarError = afiliacionesSAT.guardarErroresAfiPrimeraVez(errores.get(i), numeroTransaccion);
					if(almacenarError) {
						String descErrores = afiliacionesSAT.listarDescripcionGlosas(errores.get(i));
						if(descripcionErrores.size()<2)
							descripcionErrores.add(descErrores);
						cantidadGlosas++;
					}
					if(i+1 < errores.size())
						glosaEstructura += ", ";
				}
				
				String salida = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_DATOS_INVALIDOS + "\",\"mensaje\": \""
						+ glosaEstructura + "\"}";
				
				log.error("ERROR: ConsultarNovedades.procesarAfiPensionado(data)->" + salida);
			    log.error("ERROR: ConsultarNovedades.procesarAfiPensionado(data)-> glosas de estructura:"+cantidadGlosas);
								
				
				//respuesta automática de la afiliación de pensionado
				respuestaAfi.setNumeroRadicadoSolicitud((String)valor.get("RadicadoSolicitud"));
				respuestaAfi.setNumeroTransaccion(numeroTransaccion);
				respuestaAfi.setTipoDocumentoSolicitante((String)valor.get("TipoDocumentoSolicitante"));
				respuestaAfi.setNumeroDocumentoSolicitante((String)valor.get("NumeroDocumentoSolicitante"));
				respuestaAfi.setResultadoTramite("2"); //resultado de rechazo
				respuestaAfi.setFechaEfectivaAfiliacion(""); //fecha efectiva de afiliación debe ir nula
				respuestaAfi.setMotivoRechazo(descripcionErrores.get(0)+" "+descripcionErrores.get(1));
				respuestaAfi.setFechaRechazo(LocalDate.now().toString());
				
				//método que envía respuesta automática de error de glosa
				String data = "";
				data = gs.toJson(respuestaAfi).toString();
				respuestaNov.responderTransaccionesGlosas(data, IConstantes.SERVICIO_RESPUESTA_AFI_INDEPENDIENTES_PENSIONADOS,numeroTransaccion);
				
			}
			
		} catch (Exception e) {	
			log.error("ERROR: ConsultarNovedades.procesarAfiPensionado(data)-> ", e);
		}
		return respuesta;
	}
	
	public boolean procesarDesafiliacionIndPens(JSONObject valor) {
		boolean respuesta = false;
		DesafiliacionIndependientesPensionados afiliado = new DesafiliacionIndependientesPensionados();
		RespuestaDesafiliacionIndependientesPensionados respuestaAfi = new RespuestaDesafiliacionIndependientesPensionados();
		RespuestaNovedades respuestaNov = new RespuestaNovedades();
		String numeroTransaccion = (String)valor.get("NumeroTransaccion");
		try {
			//leer respuesta obtenida del número de transacción consultado y crear objeto de afiliacion
			afiliado.setNumeroRadicadoSolicitud((String)valor.get("NumeroRadicadoSolicitud"));
			afiliado.setNumeroTransaccion(numeroTransaccion);
			afiliado.setTipoDocumentoSolicitante((String)valor.get("TipoDocumentoSolicitante"));
			afiliado.setNumeroDocumentoSolicitante((String)valor.get("NumeroDocumentoSolicitante"));
			afiliado.setTipoAfiliado((String)valor.get("TipoAfiliado"));
			afiliado.setFechaSolicitudDesafiliacion((String)valor.get("FechaSolicitudDesafiliacion"));
			afiliado.setFechaEfectivaDesafiliacion((String)valor.get("FechaEfectivaDesafiliacion"));
			afiliado.setAutorizacionManejoDatosMinisterio((String)valor.get("AutorizacionManejoDatosPersonalesMinisterio")); 
			afiliado.setAutorizacionNotificacionesMinisterio((String)valor.get("AutorizacionEnvioNotificacionesMinisterio")); 
			afiliado.setAutorizacionManejoDatosCCF((String)valor.get("AutorizacionManejoDatosPersonalesCCF")); 
			afiliado.setAutorizacionNotificacionesCCF((String)valor.get("AutorizacionEnvioNotificacionesCCF")); 
			afiliado.setPazSalvo((String) valor.get("PazSalvo"));
			afiliado.setFechaPazSalvo((String) valor.get("FechaPazSalvo"));	
			afiliado.setEstadoConfa(IConstantes.ESTADO_SIN_PROCESAR);
			
			//revisar usando la malla de validación
			List<String> errores = validador.validarDesafiliacionIndPen(afiliado);
			if(errores.isEmpty()) {
				//insercion en la bd
				respuesta = gestionSATIndependPens.guardarDesafiliacionIndePens(afiliado);
				/*if(salida) {
					respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_CORRECTA + "\",\"mensaje\": \""
							+ IConstantes.RESPUESTA_CORRECTA + "\"}";
				} else {
					respuesta = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_INSERCION_BD + "\",\"mensaje\": \""
							+ IConstantes.RESPUESTA_ERROR_INSERCION_BD + "\"}";
				}*/
				
			} else {
				
				String glosaEstructura = "";
				int cantidadGlosas=0;
				ArrayList<String> descripcionErrores = new ArrayList<String>();
				for (int i=0; i < errores.size(); i++) {
					glosaEstructura += errores.get(i);
					//almacenar glosas de estructura al momento de consultar la afiliacion
					boolean almacenarError = afiliacionesSAT.guardarErroresAfiPrimeraVez(errores.get(i), numeroTransaccion);
					if(almacenarError) {
						String descErrores = afiliacionesSAT.listarDescripcionGlosas(errores.get(i));
						if(descripcionErrores.size()<2)
							descripcionErrores.add(descErrores);
						cantidadGlosas++;
					}
					if(i+1 < errores.size())
						glosaEstructura += ", ";
				}
				
				String salida = "{\"estado\": \"" + IConstantes.RESPUESTA_ERROR_DATOS_INVALIDOS + "\",\"mensaje\": \""
						+ glosaEstructura + "\"}";
				
				log.error("ERROR: ConsultarNovedades.procesarDesafiliacionIndPens(data)->" + salida);
			    log.error("ERROR: ConsultarNovedades.procesarDesafiliacionIndPens(data)-> glosas de estructura:"+cantidadGlosas);
								
				
				//respuesta automática de la desafiliacion de independientes y pensionados
				respuestaAfi.setNumeroRadicadoSolicitud((String)valor.get("RadicadoSolicitud"));
				respuestaAfi.setNumeroTransaccion(numeroTransaccion);
				respuestaAfi.setTipoDocumentoSolicitante((String)valor.get("TipoDocumentoSolicitante"));
				respuestaAfi.setNumeroDocumentoSolicitante((String)valor.get("NumeroDocumentoSolicitante"));
				respuestaAfi.setTipoAfiliado((String)valor.get("TipoAfiliado"));
				respuestaAfi.setFechaRespuesta(LocalDate.now().toString());	
				respuestaAfi.setResultadoTramite("2"); //resultado de rechazo
				respuestaAfi.setFechaEfectivaDesfiliacion(""); //fecha efectiva de afiliación debe ir nula
				respuestaAfi.setMotivoRechazo(descripcionErrores.get(0)+" "+descripcionErrores.get(1));
				respuestaAfi.setPazSalvo("PazSalvo");
				respuestaAfi.setFechaPazSalvo("FechaPazSalvo");
				
				//método que envía respuesta automática de error de glosa
				String data = "";
				data = gs.toJson(respuestaAfi).toString();
				respuestaNov.responderTransaccionesGlosas(data, IConstantes.SERVICIO_RESPUESTA_DESAFILIACION_INDEP_PENS,numeroTransaccion);
				
			}
			
		} catch (Exception e) {	
			log.error("ERROR: ConsultarNovedades.procesarDesafiliacionIndPens(data)-> ", e);
		}
		return respuesta;
	}
	
	
	private boolean procesarNovedad(String data,int servicio, int servicioConsultar, NotificacionSAT notificacion, BaseDatos bd) {
		boolean resultado=false;
		try {
			tb= new TokenBean();			
			if(tb!=null) {
				String[] respuesta = ConsumoRestWS.comsumoRestPost(data, IConstantes.WS_RUTA_BASE+rutas.get(servicioConsultar-1), tb.getToken(client_id_token.get(servicioConsultar-1)), true, true,"POST");
				if (respuesta[0].equals("true")) {
					JSONObject valor= new JSONObject(respuesta[1]);
					String mensaje="";
					
					//cambiar estado confa a Consultada = C
					boolean cambiarEstado = gestionSAT.modificarEstadoNotificacion(notificacion.getNumeroTransaccion(),IConstantes.ESTADO_CONSULTADA);
					if(valor.has("mensaje")) {
						if(valor.getString("mensaje")!=null && !valor.getString("mensaje").isEmpty()){
							mensaje=valor.getString("mensaje").replaceAll("“", "\"");
							mensaje=mensaje.replaceAll("”", "\"");
							resultado= afiliacionesSAT.guardarRespuestaWS(valor.getString("resultado"), mensaje, valor.getString("codigo"), notificacion.getNumeroTransaccion());
							
						}
					} else if(valor.has("NumeroTransaccion")) {
						
						if(valor.getString("NumeroTransaccion")!=null && !valor.getString("NumeroTransaccion").isEmpty()){
							if(cambiarEstado) {
								switch (servicio) {
								case IConstantes.SERVICIO_AFILIACION_PRIMERA_VEZ:
									resultado = procesarAfiPrimeraVez(valor);
									if(resultado && IConstantes.PERMITERECIBIRCORREO)
										enviarCorreoNotificacion((String)valor.get("NumeroTransaccion"),IConstantes.SERVICIO_AFILIACION_PRIMERA_VEZ, (String)valor.get("FechaSolicitud"));
									break;
								case IConstantes.SERVICIO_AFILIACION_NO_PRIMERA_VEZ:
									resultado = procesarAfiNoPrimeraVez(valor);
									if(resultado && IConstantes.PERMITERECIBIRCORREO)
										enviarCorreoNotificacion((String)valor.get("NumeroTransaccion"),IConstantes.SERVICIO_AFILIACION_NO_PRIMERA_VEZ, (String)valor.get("FechaSolicitud"));
									break;
								case IConstantes.SERVICIO_DESAFILIACION:
									resultado = procesarDesafiliacion(valor);
									if(resultado && IConstantes.PERMITERECIBIRCORREO)
										enviarCorreoNotificacion((String)valor.get("NumeroTransaccion"),IConstantes.SERVICIO_DESAFILIACION, (String)valor.get("FechaSolicitud"));
									break;
								case IConstantes.SERVICIO_RELACION_LABORAL:
									resultado = procesarInicioRelacionLaboral(valor);
									if(resultado && IConstantes.PERMITERECIBIRCORREO)
										enviarCorreoNotificacion((String)valor.get("NumeroTransaccion"),IConstantes.SERVICIO_RELACION_LABORAL, (String)valor.get("FechaSolicitud"));
									break;
								case IConstantes.SERVICIO_TERMINACION_RELACION:
									resultado = procesarFinRelacionLaboral(valor);
									if(resultado && IConstantes.PERMITERECIBIRCORREO)
										enviarCorreoNotificacion((String)valor.get("NumeroTransaccion"),IConstantes.SERVICIO_TERMINACION_RELACION, (String)valor.get("FechaSolicitud"));
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
							}
						}
					}
					log.info("ConsultarNovedades.procesarNovedad(data, bd)-> enviado "+data);
					log.info("ConsultarNovedades.procesarNovedad(data, bd)-> recibido "+valor.toString());
				}else {
					JSONObject valor= new JSONObject(respuesta[1]);
					log.info("ConsultarNovedades.procesarNovedad(data, bd)-> enviado Error "+data);
					log.info("ConsultarNovedades.procesarNovedad(data, bd)-> recibido Error "+valor.toString());
				}
			}else {
				log.error("ConsultarNovedades.procesarNovedad()-> no hay token dsponible");
				resultado=false;
			}
		} catch (Exception e) {	
			log.error("ERROR: ConsultarNovedades.procesarNovedad()-> ", e);
		}
		return resultado;
	}

	public void enviarCorreoNotificacion(String numeroTransaccion, int tipoAfiliacion, String fechaSolicitud) {
		envio.obtenerEnviarCorreos(numeroTransaccion, tipoAfiliacion, fechaSolicitud);
	}
	
	public int consultarTransaccionesIndependientes() {
		int respuesta = consultarTransaccionesIndepPens(IConstantes.SERVICIO_AFILIACION_INDEPENDIENTES, IConstantes.SERVICIO_CONSULTA_AFI_INDEPENDIENTES);
		return respuesta;
	}
	
	public int consultarTransaccionesPensionados() {
		int respuesta = consultarTransaccionesIndepPens(IConstantes.SERVICIO_AFILIACION_PENSIONADOS, IConstantes.SERVICIO_CONSULTA_AFI_PENSIONADOS);
		return respuesta;
	}
	
	public int consultarTransaccionesDesafiliacionIndePens() {
		int respuesta = consultarTransaccionesIndepPens(IConstantes.SERVICIO_DESAFILIACION_INDEP_PENS, IConstantes.SERVICIO_CONSULTA_DESAFILIACION_INDEP_PENS);
		return respuesta;
	}
	
	public int consultarTransaccionesIndepPens(int estadoConsultaTransaccion, int servicioConsultar) {
		int respuesta = 0;
		BaseDatos bd = new BaseDatos();
		try {
			ArrayList<NotificacionSAT> notificaciones = 
					gestionSATIndependPens.listarTransaccionSinProcesar(IConstantes.ESTADO_SIN_PROCESAR,estadoConsultaTransaccion);
			if(notificaciones.size()>0) {
				tb= new TokenBean();
				if(tb!=null) {
					if(bd.conectar(IConstantes.POOL_INGRESO)) {
						String data = "";
						boolean salida = false;
						for (NotificacionSAT notificacion: notificaciones) {
							respuesta++;
							data = gs.toJson(notificacion).toString();

							//método utilizado para consumir cada transacción 
							salida = procesarNovedadIndepPens(data, servicioConsultar, notificacion, bd);
							if(salida) {
								log.info("ConsultarNovedades.consultarTransaccionesIndepPens()-> Número transacción almacenada en BD ");	
							} else {
								log.error("ConsultarNovedades.consultarTransaccionesIndepPens()-> Número transacción NO almacenado en BD ");
							}
						}
					}
				}
			} else {
				log.info("ConsultarNovedades.consultarTransaccionesIndepPens()-> No existen transacciones pendientes por consultar");
			}
			
		}catch (Exception e) {
			log.error("ERROR: ConsultarNovedades.consultarTransaccionesIndepPens()-> ", e);
			e.printStackTrace();
		}
		finally {
			bd.cerrar();
		}
		return respuesta;
	}
	
	private boolean procesarNovedadIndepPens(String data, int servicio, NotificacionSAT notificacion, BaseDatos bd) {
		boolean resultado=false;
		try {
			tb= new TokenBean();			
			if(tb!=null) {
				String[] respuesta = ConsumoRestWS.comsumoRestPost(data, IConstantes.WS_RUTA_BASE+rutas.get(servicio-1), tb.getToken(client_id_token.get(servicio-1)), true, true,"POST");
				if (respuesta[0].equals("true")) {
					JSONObject valor= new JSONObject(respuesta[1]);
					String mensaje="";
					
					//cambiar estado confa a Consultada = C
					boolean cambiarEstado = gestionSATIndependPens.modificarEstadoNotificacion(notificacion.getNumeroTransaccion(),IConstantes.ESTADO_CONSULTADA);
					if(valor.has("mensaje")) {
						if(valor.getString("mensaje")!=null && !valor.getString("mensaje").isEmpty()){
							mensaje=valor.getString("mensaje").replaceAll("“", "\"");
							mensaje=mensaje.replaceAll("”", "\"");
							resultado= afiliacionesSAT.guardarRespuestaWS(valor.getString("resultado"), mensaje, valor.getString("codigo"), notificacion.getNumeroTransaccion());
							
						}
					} else if(valor.has("NumeroTransaccion")) {
						
						if(valor.getString("NumeroTransaccion")!=null && !valor.getString("NumeroTransaccion").isEmpty()){
							if(cambiarEstado) {
								switch (servicio) {
								case IConstantes.SERVICIO_CONSULTA_AFI_INDEPENDIENTES:
									resultado = procesarAfiIndependiente(valor);
									if(resultado && IConstantes.PERMITERECIBIRCORREO)
										enviarCorreoNotificacion((String)valor.get("NumeroTransaccion"),IConstantes.SERVICIO_CONSULTA_AFI_INDEPENDIENTES, (String)valor.get("FechaSolicitud"));
									break;
								case IConstantes.SERVICIO_CONSULTA_AFI_PENSIONADOS:
									resultado = procesarAfiPensionado(valor);
									if(resultado && IConstantes.PERMITERECIBIRCORREO)
										enviarCorreoNotificacion((String)valor.get("NumeroTransaccion"),IConstantes.SERVICIO_CONSULTA_AFI_PENSIONADOS, (String)valor.get("FechaSolicitud"));
									break;
								case IConstantes.SERVICIO_CONSULTA_DESAFILIACION_INDEP_PENS:
									resultado = procesarDesafiliacionIndPens(valor);
									if(resultado && IConstantes.PERMITERECIBIRCORREO)
										enviarCorreoNotificacion((String)valor.get("NumeroTransaccion"),IConstantes.SERVICIO_CONSULTA_DESAFILIACION_INDEP_PENS, (String)valor.get("FechaSolicitud"));
									break;
								case IConstantes.SERVICIO_PERDIDA_CAUSA_GRAVE_PENSIONADOS:
									break;
								case IConstantes.SERVICIO_PERDIDA_CAUSA_GRAVE_INDEP_PENS:
									break;
								default:
									break;
								}
							}
						}
					}
					log.info("ConsultarNovedades.procesarNovedad(data, bd)-> enviado "+data);
					log.info("ConsultarNovedades.procesarNovedad(data, bd)-> recibido "+valor.toString());
				}else {
					JSONObject valor= new JSONObject(respuesta[1]);
					log.info("ConsultarNovedades.procesarNovedad(data, bd)-> enviado Error "+data);
					log.info("ConsultarNovedades.procesarNovedad(data, bd)-> recibido Error "+valor.toString());
				}
			}else {
				log.error("ConsultarNovedades.procesarNovedad()-> no hay token dsponible");
				resultado=false;
			}
		} catch (Exception e) {	
			log.error("ERROR: ConsultarNovedades.procesarNovedad()-> ", e);
		}
		return resultado;
	}

}
