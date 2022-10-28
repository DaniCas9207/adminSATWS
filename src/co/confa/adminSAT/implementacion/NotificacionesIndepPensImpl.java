package co.confa.adminSAT.implementacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import co.confa.adminSAT.configuracion.IConstantes;
import co.confa.adminSAT.modelo.AfiliacionIndependientes;
import co.confa.adminSAT.modelo.AfiliacionPensionados;
import co.confa.adminSAT.modelo.DesafiliacionIndependientesPensionados;
import co.confa.adminSAT.modelo.NotificacionSAT;
import co.confa.adminSAT.modelo.RespuestaAfiliacionIndependientesPensionados;
import co.confa.adminSAT.modelo.RespuestaDesafiliacionIndependientesPensionados;
import co.confa.bd.BaseDatos;
import co.confa.util.Calendario;

public class NotificacionesIndepPensImpl {

	private Logger log = Logger.getLogger(NotificacionesIndepPensImpl.class);
	private BaseDatos bd = new BaseDatos();

	
	public ArrayList<NotificacionSAT> listarTransaccionSinProcesar(String estadoConfa, int codigoNovedad) {	
		ArrayList<NotificacionSAT> salida = new ArrayList<NotificacionSAT>();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try{
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
			
				StringBuilder sb=new StringBuilder();
				sb.append("SELECT numero_transaccion, codigo_novedad FROM recepcion.notificaciones_indep_pens_sat ");
				sb.append("WHERE estado_confa = ? and codigo_novedad = ? ");
				sb.append("order by id");
				
				preparedStatement=bd.crearSentencia(sb.toString());
				preparedStatement.setString(1,estadoConfa);
				preparedStatement.setString(2,String.valueOf(codigoNovedad));
				resultSet=preparedStatement.executeQuery();
				while (resultSet.next()){
					NotificacionSAT noti = new NotificacionSAT();
					noti.setNumeroTransaccion(resultSet.getString("numero_transaccion"));
					salida.add(noti);
				}
			
			}else {
				log.error("Error en el método NotificacionesIndepPensImpl.listarTransaccionSinProcesar: No hay conexión");
			}
		}catch(SQLException e){
			log.error("Error en el método NotificacionesIndepPensImpl.listarTransaccionSinProcesar:"+e.getMessage());
		}finally{
			BaseDatos.cerrarResultSet(resultSet);
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}
	
	public boolean modificarEstadoNotificacion(String notificacion, String estado) {
		boolean resultado = false;
		PreparedStatement preparedStatement=null;
		
		try{
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				StringBuilder sb=new StringBuilder();	
				sb.append("UPDATE recepcion.notificaciones_indep_pens_sat SET estado_confa=? ");
				sb.append("WHERE numero_transaccion = ? ");
			
				preparedStatement=bd.crearSentencia(sb.toString());
				preparedStatement.setString(1,estado);
				preparedStatement.setString(2,notificacion);
				if (preparedStatement.executeUpdate()>0){
					resultado = true;
				}
			
			} else {
				log.error("Error en el método NotificacionesIndepPensImpl.modificarEstadoNotificacion: No hay conexión");
			}
		}catch(SQLException e){
			log.error("Error en el método NotificacionesIndepPensImpl.modificarEstadoNotificacion:"+e.getMessage());
		}finally{
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return resultado;
	}
	
	public boolean modificarNotificacionRespuesta(String notificacion, String estado) {
		boolean resultado = false;
		PreparedStatement preparedStatement=null;
		
		try{
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				StringBuilder sb=new StringBuilder();	
				sb.append("UPDATE recepcion.gestion_confa SET estado_gestion=?, ");
				sb.append("WHERE numero_transaccion = ? ");
			
				preparedStatement=bd.crearSentencia(sb.toString());
				preparedStatement.setString(1,estado);
				preparedStatement.setString(2,notificacion);
				if (preparedStatement.executeUpdate()>0){
					resultado = true;
				}
			
			} else {
				log.error("Error en el método NotificacionesImpl.modificarNotificacionRespuesta: No hay conexión");
			}
		}catch(SQLException e){
			log.error("Error en el método NotificacionesImpl.modificarNotificacionRespuesta:"+e.getMessage());
		}finally{
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return resultado;
	}
	
	public boolean guardarAfiliacionIndependiente(AfiliacionIndependientes afiliacion) {
		boolean salida = false;	
		PreparedStatement preparedStatement = null;

		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				bd.autoCommit(false);
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO recepcion.afiliacion_independiente( ");
				sql.append("numero_radicado_solicitud, numero_transaccion, tipo_afiliado,  "); 
				sql.append("tipo_documento_solicitante, numero_documento_solicitante, primer_nombre_solicitante, "); 
				sql.append("segundo_nombre_solicitante, primer_apellido_solicitante, segundo_apellido_solicitante, ");
				sql.append("fecha_solicitud, fecha_efectividad_afiliacion, departamento, "); 
				sql.append("municipio, pais_residencia, ciudad_residencia, direccion_contacto,  ");
				sql.append("estado_civil, numero_telefono, correo_electronico, valor_mensual_ingresos,  ");
				sql.append("perdida_afiliacion_causa_grave, declaracion_fuente_ingresos, "); 
				sql.append(" declaracion_veracidad_informacion, codigo_ccf_anterior, paz_salvo, ");
				sql.append("fecha_paz_salvo, autorizacion_manejo_datos_min, autorizacion_notificaciones_min,  ");
				sql.append("autorizacion_manejo_datos_ccf, autorizacion_notificaciones_ccf,"); 
				sql.append("manifestacion, fecha_ingreso_afiliacion, hora_ingreso_afiliacion, estado_confa) ");
				sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ");	
				sql.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?, current_date, current_time, ?) ");
				preparedStatement = bd.crearSentencia(sql.toString());
				
				String fechaAfi = afiliacion.getFechaEfectivaAfiliacion();
				String fechaPaz = afiliacion.getFechaPazSalvo();
									
				preparedStatement.setString(1,afiliacion.getNumeroRadicadoSolicitud());
				preparedStatement.setString(2,afiliacion.getNumeroTransaccion());
				preparedStatement.setInt(3,Integer.parseInt(afiliacion.getTipoAfiliado()));
				preparedStatement.setString(4,afiliacion.getTipoDocumentoSolicitante());
				preparedStatement.setString(5,afiliacion.getNumeroDocumentoSolicitante());
				preparedStatement.setString(6,afiliacion.getPrimerNombreSolicitante());
				preparedStatement.setString(7,afiliacion.getSegundoNombreSolicitante());
				preparedStatement.setString(8,afiliacion.getPrimerApellidoSolicitante());
				preparedStatement.setString(9,afiliacion.getSegundoApellidoSolicitante());
				preparedStatement.setDate(10,Calendario.fechaSql(afiliacion.getFechaSolicitud()));
				preparedStatement.setDate(11,fechaAfi.isEmpty() ? null : Calendario.fechaSql(fechaAfi));
				preparedStatement.setString(12,afiliacion.getDepartamento());
				preparedStatement.setString(13,afiliacion.getMunicipio());
				preparedStatement.setString(14,afiliacion.getPais());
				preparedStatement.setString(15,afiliacion.getCiudad());
				preparedStatement.setString(16,afiliacion.getDireccionDomicilio());
				preparedStatement.setString(17,afiliacion.getEstadoCivil());
				preparedStatement.setString(18,afiliacion.getNumeroTelefono());
				preparedStatement.setString(19,afiliacion.getCorreoElectronico());
				preparedStatement.setString(20,afiliacion.getValorMensualIngresos());
				preparedStatement.setString(21,afiliacion.getPerdidaAfiliacionCausaGrave());
				preparedStatement.setString(22,afiliacion.getDeclaracionFuenteIngresos());
				preparedStatement.setString(23,afiliacion.getDeclaracionVeracidadInformacion());
				preparedStatement.setString(24,afiliacion.getCodigoCCF());
				preparedStatement.setString(25,afiliacion.getPazSalvo());
				preparedStatement.setDate(26,fechaAfi.isEmpty() ? null : Calendario.fechaSql(fechaPaz));
				preparedStatement.setString(27,afiliacion.getAutorizacionManejoDatos());
				preparedStatement.setString(28,afiliacion.getAutorizacionNotificaciones());		
				preparedStatement.setString(29,afiliacion.getAutorizacionManejoDatosCCF());
				preparedStatement.setString(30,afiliacion.getAutorizacionNotificacionesCCF());	
				preparedStatement.setString(31,afiliacion.getManifestacion());
				preparedStatement.setString(32,afiliacion.getEstadoConfa());
				
				int reg = preparedStatement.executeUpdate();
				if(reg>0){
					bd.commit();
					salida = true;
				} else {
					bd.rollback();
				}
			}else {
				log.error("Error en el método NotificacionesIndepPensImpl.guardarAfiliacionIndependiente: No hay conexión");
			}
		} catch (SQLException e) {
			bd.rollback();
			salida = false;
			log.error("Error en el método NotificacionesIndepPensImpl.guardarAfiliacionIndependiente:" + e.getMessage());
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}
	
	public boolean guardarAfiliacionPensionado(AfiliacionPensionados afiliacion) {
		boolean salida = false;	
		PreparedStatement preparedStatement = null;

		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				bd.autoCommit(false);
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO recepcion.afiliacion_pensionado( ");
				sql.append("numero_radicado_solicitud, numero_transaccion, tipo_afiliado_vinculado,  "); 
				sql.append("tipo_documento_solicitante, numero_documento_solicitante, primer_nombre_solicitante,  "); 
				sql.append("segundo_nombre_solicitante, primer_apellido_solicitante, segundo_apellido_solicitante,  ");
				sql.append("fecha_solicitud, fecha_efectividad_afiliacion, departamento,  "); 
				sql.append("municipio, direccion_contacto, numero_telefono, correo_electronico,  ");
				sql.append("valor_mesada_pensional, declaracion_fuente_ingresos, perdida_afiliacion_causa_grave,   ");
				sql.append("declaracion_veracidad_informacion, autorizacion_manejo_datos_min,  "); 
				sql.append("autorizacion_notificaciones_min, autorizacion_manejo_datos_ccf, ");
				sql.append("autorizacion_notificaciones_ccf, manifestacion, codigo_ccf_anterior,  ");
				sql.append("paz_salvo, fecha_paz_salvo, fecha_ingreso_afiliacion, hora_ingreso_afiliacion, "); 
				sql.append("estado_confa) ");
				sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ");	
				sql.append("?,?,?,?,?,?,?,?,?,?,?, current_date, current_time, ?) ");
				preparedStatement = bd.crearSentencia(sql.toString());
				
				String fechaAfi = afiliacion.getFechaEfectivaAfiliacion();
				String fechaPaz = afiliacion.getFechaPazSalvo();
									
				preparedStatement.setString(1,afiliacion.getNumeroRadicadoSolicitud());
				preparedStatement.setString(2,afiliacion.getNumeroTransaccion());
				preparedStatement.setInt(3,Integer.parseInt(afiliacion.getTipoAfiliado()));
				preparedStatement.setString(4,afiliacion.getTipoDocumentoSolicitante());
				preparedStatement.setString(5,afiliacion.getNumeroDocumentoSolicitante());
				preparedStatement.setString(6,afiliacion.getPrimerNombreSolicitante());
				preparedStatement.setString(7,afiliacion.getSegundoNombreSolicitante());
				preparedStatement.setString(8,afiliacion.getPrimerApellidoSolicitante());
				preparedStatement.setString(9,afiliacion.getSegundoApellidoSolicitante());
				preparedStatement.setDate(10,Calendario.fechaSql(afiliacion.getFechaSolicitud()));
				preparedStatement.setDate(11,fechaAfi.isEmpty() ? null : Calendario.fechaSql(fechaAfi));
				preparedStatement.setString(12,afiliacion.getDepartamento());
				preparedStatement.setString(13,afiliacion.getMunicipio());
				preparedStatement.setString(14,afiliacion.getDireccionDomicilio());
				preparedStatement.setString(15,afiliacion.getNumeroTelefono());
				preparedStatement.setString(16,afiliacion.getCorreoElectronico());
				preparedStatement.setString(17,afiliacion.getValorMensualIngresos());
				preparedStatement.setString(18,afiliacion.getDeclaracionFuenteIngresos());
				preparedStatement.setString(19,afiliacion.getPerdidaAfiliacionCausaGrave());		
				preparedStatement.setString(20,afiliacion.getDeclaracionVeracidadInformacion());				
				preparedStatement.setString(21,afiliacion.getAutorizacionManejoDatos());
				preparedStatement.setString(22,afiliacion.getAutorizacionNotificaciones());		
				preparedStatement.setString(23,afiliacion.getAutorizacionManejoDatosCCF());
				preparedStatement.setString(24,afiliacion.getAutorizacionNotificacionesCCF());	
				preparedStatement.setString(25,afiliacion.getManifestacion());
				preparedStatement.setString(26,afiliacion.getCodigoCCF());
				preparedStatement.setString(27,afiliacion.getPazSalvo());
				preparedStatement.setDate(28,fechaAfi.isEmpty() ? null : Calendario.fechaSql(fechaPaz));
				preparedStatement.setString(29,afiliacion.getEstadoConfa());
				
				int reg = preparedStatement.executeUpdate();
				if(reg>0){
					bd.commit();
					salida = true;
				} else {
					bd.rollback();
				}
			}else {
				log.error("Error en el método NotificacionesIndepPensImpl.guardarAfiliacionPensionado: No hay conexión");
			}
		} catch (SQLException e) {
			bd.rollback();
			salida = false;
			log.error("Error en el método NotificacionesIndepPensImpl.guardarAfiliacionPensionado:" + e.getMessage());
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}
	
	public boolean guardarDesafiliacionIndePens(DesafiliacionIndependientesPensionados afiliacion) {
		boolean salida = false;	
		PreparedStatement preparedStatement = null;

		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				bd.autoCommit(false);
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO recepcion.desafiliacion_indep_pens( ");
				sql.append("numero_radicado_solicitud, numero_transaccion, tipo_documento_solicitante,   "); 
				sql.append("numero_documento_solicitante, tipo_afiliado, fecha_solicitud_desafiliacion,   "); 
				sql.append("fecha_efectividad_desafiliacion, autorizacion_manejo_datos_min,  ");
				sql.append("autorizacion_notificaciones_min, autorizacion_manejo_datos_ccf,   "); 
				sql.append("autorizacion_notificaciones_ccf, paz_salvo, fecha_paz_salvo,   ");
				sql.append("fecha_ingreso_afiliacion, hora_ingreso_afiliacion, estado_confa)");
				sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?, ");	
				sql.append("current_date, current_time, ?) ");
				preparedStatement = bd.crearSentencia(sql.toString());
				
				String fechaAfi = afiliacion.getFechaEfectivaDesafiliacion();
				String fechaPaz = afiliacion.getFechaPazSalvo();
									
				preparedStatement.setString(1,afiliacion.getNumeroRadicadoSolicitud());
				preparedStatement.setString(2,afiliacion.getNumeroTransaccion());
				preparedStatement.setString(3,afiliacion.getTipoDocumentoSolicitante());
				preparedStatement.setString(4,afiliacion.getNumeroDocumentoSolicitante());
				preparedStatement.setInt(5,Integer.parseInt(afiliacion.getTipoAfiliado()));
				preparedStatement.setDate(6,Calendario.fechaSql(afiliacion.getFechaSolicitudDesafiliacion()));
				preparedStatement.setDate(7,fechaAfi.isEmpty() ? null : Calendario.fechaSql(fechaAfi));				
				preparedStatement.setString(8,afiliacion.getAutorizacionManejoDatosMinisterio());
				preparedStatement.setString(9,afiliacion.getAutorizacionNotificacionesMinisterio());		
				preparedStatement.setString(10,afiliacion.getAutorizacionManejoDatosCCF());
				preparedStatement.setString(11,afiliacion.getAutorizacionNotificacionesCCF());
				preparedStatement.setString(12,afiliacion.getPazSalvo());
				preparedStatement.setDate(13,fechaAfi.isEmpty() ? null : Calendario.fechaSql(fechaPaz));
				preparedStatement.setString(14,afiliacion.getEstadoConfa());
				
				int reg = preparedStatement.executeUpdate();
				if(reg>0){
					bd.commit();
					salida = true;
				} else {
					bd.rollback();
				}
			}else {
				log.error("Error en el método NotificacionesIndepPensImpl.guardarDesafiliacionIndePens: No hay conexión");
			}
		} catch (SQLException e) {
			bd.rollback();
			salida = false;
			log.error("Error en el método NotificacionesIndepPensImpl.guardarDesafiliacionIndePens:" + e.getMessage());
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}
	
	public ArrayList<RespuestaAfiliacionIndependientesPensionados> listarTransaccionGestionadasAfiliacionIndepPens(String estado, int codigoNovedad1,int codigoNovedad2) {	
		ArrayList<RespuestaAfiliacionIndependientesPensionados> salida = new ArrayList<RespuestaAfiliacionIndependientesPensionados>();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		PreparedStatement preparedStatement2=null;
		ResultSet resultSet2=null;
		try{
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
			
				StringBuilder sb=new StringBuilder();
				sb.append("SELECT afind.numero_radicado_solicitud as numradicado, "); 
				sb.append("afind.numero_transaccion as numtransac,afind.tipo_documento_solicitante as docsoli, "); 
				sb.append("afind.numero_documento_solicitante as numdosoli, "); 
				sb.append("gc.resultado_tramite as resultramite, gc.fecha_efectiva_afiliacion as fechaafil, "); 
				sb.append("gc.motivo_rechazo as motrech, gc.fecha_gestion as fesges "); 
				sb.append("FROM recepcion.gestion_confa gc  "); 
				sb.append("inner join recepcion.notificaciones_indep_pens_sat ns ON ns.id = gc.notificacion_id "); 
				sb.append("inner join recepcion.afiliacion_independiente afind ON gc.novedad_id = afind.id "); 
				sb.append("WHERE afind.estado_confa = ? AND ns.codigo_novedad = ? ");
				
				preparedStatement=bd.crearSentencia(sb.toString());
				
				preparedStatement.setString(1,estado);
				preparedStatement.setString(2,String.valueOf(codigoNovedad1));
				
				resultSet=preparedStatement.executeQuery();
				while (resultSet.next()){
					RespuestaAfiliacionIndependientesPensionados respAfi = new RespuestaAfiliacionIndependientesPensionados();
					respAfi.setNumeroRadicadoSolicitud(resultSet.getString("numradicado"));
					respAfi.setNumeroTransaccion(resultSet.getString("numtransac"));
					respAfi.setTipoDocumentoSolicitante(resultSet.getString("docsoli"));
					respAfi.setNumeroDocumentoSolicitante(resultSet.getString("numdosoli"));
					respAfi.setResultadoTramite(resultSet.getString("resultramite"));
					respAfi.setFechaEfectivaAfiliacion(resultSet.getDate("fechaafil")==null ? "" : resultSet.getDate("fechaafil").toString());
					respAfi.setMotivoRechazo(resultSet.getString("motrech"));
					respAfi.setFechaRechazo(resultSet.getDate("fesges")==null ? "" : resultSet.getDate("fesges").toString());
					
					salida.add(respAfi);
				}
				
				StringBuilder sb2=new StringBuilder();
				sb2.append("SELECT afipen.numero_radicado_solicitud as numradicado, "); 
				sb2.append("afipen.numero_transaccion as numtransac,afipen.tipo_documento_solicitante as docsoli, "); 
				sb2.append("afipen.numero_documento_solicitante as numdosoli, "); 
				sb2.append("gc.resultado_tramite as resultramite, gc.fecha_efectiva_afiliacion as fechaafil, "); 
				sb2.append("gc.motivo_rechazo as motrech, gc.fecha_gestion as fesges "); 
				sb2.append("FROM recepcion.gestion_confa gc  "); 
				sb2.append("inner join recepcion.notificaciones_indep_pens_sat ns ON ns.id = gc.notificacion_id "); 
				sb2.append("inner join recepcion.afiliacion_pensionado afipen ON gc.novedad_id = afipen.id "); 
				sb2.append("WHERE afipen.estado_confa = ? AND ns.codigo_novedad = ? ");
				
				preparedStatement2=bd.crearSentencia(sb2.toString());
				
				preparedStatement2.setString(1,estado);
				preparedStatement2.setString(2,String.valueOf(codigoNovedad2));
				
				resultSet2=preparedStatement2.executeQuery();
				while (resultSet2.next()){
					RespuestaAfiliacionIndependientesPensionados respAfi = new RespuestaAfiliacionIndependientesPensionados();
					respAfi.setNumeroRadicadoSolicitud(resultSet2.getString("numradicado"));
					respAfi.setNumeroTransaccion(resultSet2.getString("numtransac"));
					respAfi.setTipoDocumentoSolicitante(resultSet2.getString("docsoli"));
					respAfi.setNumeroDocumentoSolicitante(resultSet2.getString("numdosoli"));
					respAfi.setResultadoTramite(resultSet2.getString("resultramite"));
					respAfi.setFechaEfectivaAfiliacion(resultSet2.getDate("fechaafil")==null ? "" : resultSet2.getDate("fechaafil").toString());
					respAfi.setMotivoRechazo(resultSet2.getString("motrech"));
					respAfi.setFechaRechazo(resultSet2.getDate("fesges")==null ? "" : resultSet2.getDate("fesges").toString());
					
					salida.add(respAfi);
				}
			
			}else {
				log.error("Error en el método NotificacionesIndepPensImpl.listarTransaccionGestionadasAfiliacionIndepPens: No hay conexión");
			}
		}catch(SQLException e){
			log.error("Error en el método NotificacionesIndepPensImpl.listarTransaccionGestionadasAfiliacionIndepPens:"+e.getMessage());
		}finally{
			BaseDatos.cerrarResultSet(resultSet);
			BaseDatos.cerrarStatement(preparedStatement);
			BaseDatos.cerrarResultSet(resultSet2);
			BaseDatos.cerrarStatement(preparedStatement2);
			bd.cerrar();
		}
		return salida;
	}
	
	public ArrayList<RespuestaDesafiliacionIndependientesPensionados> listarTransaccionGestionadasDesafiliacionIndePens(String estado, int codigoNovedad) {	
		ArrayList<RespuestaDesafiliacionIndependientesPensionados> salida = new ArrayList<RespuestaDesafiliacionIndependientesPensionados>();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try{
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
			
				StringBuilder sb=new StringBuilder();
				sb.append("SELECT dindpes.numero_radicado_solicitud as numradicado,  "); 
				sb.append("dindpes.numero_transaccion as numtransac,dindpes.tipo_documento_solicitante as docsol, "); 
				sb.append("dindpes.numero_documento_solicitante as numdocsol, dindpes.tipo_afiliado as tipoafiliado, "); 
				sb.append("gc.resultado_tramite as resultramite, gc.fecha_efectiva_afiliacion as fechaafil, "); 
				sb.append("gc.motivo_rechazo as motrech,gc.paz_salvo as pazsalvo,gc.fecha_paz_salvo as fechapaz "); 
				sb.append("FROM recepcion.gestion_confa gc "); 
				sb.append("inner join recepcion.notificaciones_indep_pens_sat ns ON ns.id = gc.notificacion_id "); 
				sb.append("inner join recepcion.desafiliacion_indep_pens dindpes ON gc.novedad_id = dindpes.id "); 
				sb.append("WHERE dindpes.estado_confa = ? AND ns.codigo_novedad = ? ");
				
				preparedStatement=bd.crearSentencia(sb.toString());
				
				preparedStatement.setString(1,estado);
				preparedStatement.setString(2,String.valueOf(codigoNovedad));
				
				resultSet=preparedStatement.executeQuery();
				while (resultSet.next()){
					RespuestaDesafiliacionIndependientesPensionados respAfi = new RespuestaDesafiliacionIndependientesPensionados();
					respAfi.setNumeroRadicadoSolicitud(resultSet.getString("numradicado"));
					respAfi.setNumeroTransaccion(resultSet.getString("numtransac"));
					respAfi.setTipoDocumentoSolicitante(resultSet.getString("docsol"));
					respAfi.setNumeroDocumentoSolicitante(resultSet.getString("numdocsol"));
					respAfi.setTipoAfiliado(resultSet.getString("tipoafiliado"));
					respAfi.setResultadoTramite(resultSet.getString("resultramite"));
					respAfi.setFechaEfectivaDesfiliacion(resultSet.getDate("fechaafil")==null ? "" : resultSet.getDate("fechaafil").toString());
					respAfi.setMotivoRechazo(resultSet.getString("motrech"));
					respAfi.setPazSalvo(resultSet.getString("pazsalvo"));
					respAfi.setMotivoRechazo(resultSet.getDate("fechapaz")==null ? "" : resultSet.getDate("fechapaz").toString());
					respAfi.setFechaRespuesta(LocalDate.now().toString());
					
					salida.add(respAfi);
				}
			
			}else {
				log.error("Error en el método NotificacionesIndepPensImpl.listarTransaccionGestionadasDesafiliacionIndePens: No hay conexión");
			}
		}catch(SQLException e){
			log.error("Error en el método NotificacionesIndepPensImpl.listarTransaccionGestionadasDesafiliacionIndePens:"+e.getMessage());
		}finally{
			BaseDatos.cerrarResultSet(resultSet);
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}
	
	public boolean modificarEstadoAfiliacionIndePens(String notificacion, String estado) {
		boolean resultado = false;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				StringBuilder sb = new StringBuilder();
				sb.append("UPDATE recepcion.afiliacion_independiente SET estado_confa=? ");
				sb.append("WHERE numero_transaccion = ? ");

				preparedStatement = bd.crearSentencia(sb.toString());
				preparedStatement.setString(1, estado);
				preparedStatement.setString(2, notificacion);
				if (preparedStatement.executeUpdate() > 0) {
					resultado = true;
				} else {
					StringBuilder sb2 = new StringBuilder();
					sb2.append("UPDATE recepcion.afiliacion_pensionado SET estado_confa=? ");
					sb2.append("WHERE numero_transaccion = ? ");

					preparedStatement2 = bd.crearSentencia(sb.toString());
					preparedStatement2.setString(1, estado);
					preparedStatement2.setString(2, notificacion);
					if (preparedStatement2.executeUpdate() > 0) {
						resultado = true;
					}
				}

			} else {
				log.error("Error en el método NotificacionesIndepPensImpl.modificarEstadoAfiliacionIndePens: No hay conexión");
			}
		} catch (SQLException e) {
			log.error("Error en el método NotificacionesIndepPensImpl.modificarEstadoAfiliacionIndePens:" + e.getMessage());
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			BaseDatos.cerrarStatement(preparedStatement2);
			bd.cerrar();
		}
		return resultado;
	}
	
	public boolean modificarEstadoDesafiliacionIndePens(String notificacion, String estado) {
		boolean resultado = false;
		PreparedStatement preparedStatement = null;
		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				StringBuilder sb = new StringBuilder();
				sb.append("UPDATE recepcion.desafiliacion_indep_pens SET estado_confa=? ");
				sb.append("WHERE numero_transaccion = ? ");

				preparedStatement = bd.crearSentencia(sb.toString());
				preparedStatement.setString(1, estado);
				preparedStatement.setString(2, notificacion);
				if (preparedStatement.executeUpdate() > 0) {
					resultado = true;
				}
			} else {
				log.error("Error en el método NotificacionesIndepPensImpl.modificarEstadoDesafiliacionIndePens: No hay conexión");
			}
		} catch (SQLException e) {
			log.error("Error en el método NotificacionesIndepPensImpl.modificarEstadoDesafiliacionIndePens:" + e.getMessage());
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return resultado;
	}
	
	

}
