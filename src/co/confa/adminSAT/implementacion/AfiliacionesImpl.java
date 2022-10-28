package co.confa.adminSAT.implementacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import co.confa.adminSAT.configuracion.IConstantes;
import co.confa.adminSAT.modelo.AfiliacionNoPrimeraVez;
import co.confa.adminSAT.modelo.AfiliacionPrimeraVez;
import co.confa.adminSAT.modelo.DesafiliacionEmpresas;
import co.confa.adminSAT.modelo.FinRelacionLaboral;
import co.confa.adminSAT.modelo.InicioRelacionLaboral;
import co.confa.adminSAT.modelo.RespuestaAfiliacionNoPrimeraVez;
import co.confa.adminSAT.modelo.RespuestaAfiliacionPrimeraVez;
import co.confa.adminSAT.modelo.RespuestaDesafiliacionCCF;
import co.confa.adminSAT.modelo.RespuestaFinRelacion;
import co.confa.adminSAT.modelo.RespuestaInicioRelacion;
import co.confa.bd.BaseDatos;
import co.confa.util.Calendario;

/**
 * 
 * @author tec_danielc
 *
 */
public class AfiliacionesImpl {
	
	private Logger log = Logger.getLogger(AfiliacionesImpl.class);
	private BaseDatos bd = new BaseDatos();
	
	public AfiliacionesImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean guardarAfiliacionPrimeraVez(AfiliacionPrimeraVez afiliacion) {
		boolean salida = false;	
		PreparedStatement preparedStatement = null;

		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				bd.autoCommit(false);
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO recepcion.afiliacion_primera_vez( ");
				sql.append("numero_radicado_solicitud, numero_transaccion, tipo_persona, "); 
				sql.append("naturaleza_juridica_empleador, tipo_documento_empleador, numero_documento_empleador,"); 
				sql.append("serial_sat, primer_nombre_empleador, segundo_nombre_empleador,");
				sql.append("primer_apellido_empleador, segundo_apellido_empleador, fecha_solicitud,"); 
				sql.append("perdida_afiliacion_causa_grave, fecha_efectividad_afiliacion, ");
				sql.append("razon_social, numero_matricula_mercantil, departamento, municipio, ");
				sql.append("direccion_contacto, numero_telefono, correo_electronico, tipo_documento_representante, "); 
				sql.append("numero_documento_representante, primer_nombre_representante, ");
				sql.append("segundo_nombre_representante, primer_apellido_representante, ");
				sql.append("segundo_apellido_representante, autorizacion_manejo_datos, autorizacion_notificaciones,"); 
				sql.append("manifestacion, fecha_ingreso_afiliacion, hora_ingreso_afiliacion, estado_confa) ");
				sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");	
				sql.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_date, current_time, ?) ");
				preparedStatement = bd.crearSentencia(sql.toString());
				
				String fechaAfi = afiliacion.getFechaEfectivaAfiliacion();
									
				preparedStatement.setString(1,afiliacion.getNumeroRadicadoSolicitud());
				preparedStatement.setString(2,afiliacion.getNumeroTransaccion());
				preparedStatement.setString(3,afiliacion.getTipoPersona());
				preparedStatement.setInt(4,afiliacion.getNaturalezaJuridicaEmpleador());
				preparedStatement.setString(5,afiliacion.getTipoDocumentoEmpleador());
				preparedStatement.setString(6,afiliacion.getNumeroDocumentoEmpleador());
				preparedStatement.setString(7,afiliacion.getSerialSAT());
				preparedStatement.setString(8,afiliacion.getPrimerNombreEmpleador());
				preparedStatement.setString(9,afiliacion.getSegundoNombreEmpleador());
				preparedStatement.setString(10,afiliacion.getPrimerApellidoEmpleador());
				preparedStatement.setString(11,afiliacion.getSegundoApellidoEmpleador());
				preparedStatement.setDate(12,Calendario.fechaSql(afiliacion.getFechaSolicitud()));
				preparedStatement.setString(13,afiliacion.getPerdidaAfiliacionCausaGrave());
				preparedStatement.setDate(14,fechaAfi.isEmpty() ? null : Calendario.fechaSql(fechaAfi));
				preparedStatement.setString(15,afiliacion.getRazonSocial());
				preparedStatement.setString(16,afiliacion.getNumeroMatriculaMercantil());
				preparedStatement.setString(17,afiliacion.getDepartamento());
				preparedStatement.setString(18,afiliacion.getMunicipio());
				preparedStatement.setString(19,afiliacion.getDireccionContacto());
				preparedStatement.setString(20,afiliacion.getNumeroTelefono());
				preparedStatement.setString(21,afiliacion.getCorreoElectronico());
				preparedStatement.setString(22,afiliacion.getTipoDocumentoRepresentante());
				preparedStatement.setString(23,afiliacion.getNumeroDocumentoRepresentante());
				preparedStatement.setString(24,afiliacion.getPrimerNombreRepresentante());
				preparedStatement.setString(25,afiliacion.getSegundoNombreRepresentante());
				preparedStatement.setString(26,afiliacion.getPrimerApellidoRepresentante());
				preparedStatement.setString(27,afiliacion.getSegundoApellidoRepresentante());
				preparedStatement.setString(28,afiliacion.getAutorizacionManejoDatos());
				preparedStatement.setString(29,afiliacion.getAutorizacionNotificaciones());				
				preparedStatement.setString(30,afiliacion.getManifestacion());
				preparedStatement.setString(31,afiliacion.getEstadoConfa());
				
				int reg = preparedStatement.executeUpdate();
				if(reg>0){
					bd.commit();
					salida = true;
				} else {
					bd.rollback();
				}
			}else {
				log.error("Error en el método AfiliacionesImpl.guardarAfiliacionPrimeraVez: No hay conexión");
			}
		} catch (SQLException e) {
			bd.rollback();
			salida = false;
			log.error("Error en el método AfiliacionesImpl.guardarAfiliacionPrimeraVez:" + e.getMessage());
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}

	public boolean guardarAfiliacionNoPrimeraVez(AfiliacionNoPrimeraVez afiliacion) {
		boolean salida = false;
		PreparedStatement preparedStatement = null;

		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				bd.autoCommit(false);
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO recepcion.afiliacion_no_primera_vez( ");
				sql.append("numero_radicado_solicitud, numero_transaccion, tipo_persona, ");
				sql.append("naturaleza_juridica_empleador, tipo_documento_empleador, numero_documento_empleador,");
				sql.append("serial_sat, primer_nombre_empleador, segundo_nombre_empleador,");
				sql.append("primer_apellido_empleador, segundo_apellido_empleador, fecha_solicitud,");
				sql.append("perdida_afiliacion_causa_grave, fecha_efectividad_afiliacion, ");
				sql.append("razon_social, numero_matricula_mercantil, departamento, municipio, ");
				sql.append("direccion_contacto, numero_telefono, correo_electronico, tipo_documento_representante, ");
				sql.append("numero_documento_representante, primer_nombre_representante, ");
				sql.append("segundo_nombre_representante, primer_apellido_representante, ");
				sql.append("segundo_apellido_representante, autorizacion_manejo_datos, autorizacion_notificaciones,");
				sql.append("manifestacion, fecha_ingreso_afiliacion, hora_ingreso_afiliacion, estado_confa, codigo_ccf_anterior,paz_y_salvo,fecha_paz_y_salvo) ");
				sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
				sql.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_date, current_time, ?,?,?,?) ");
				preparedStatement = bd.crearSentencia(sql.toString());

				String fechaAfi = afiliacion.getFechaEfectivaAfiliacion();

				preparedStatement.setString(1, afiliacion.getNumeroRadicadoSolicitud());
				preparedStatement.setString(2, afiliacion.getNumeroTransaccion());
				preparedStatement.setString(3, afiliacion.getTipoPersona());
				preparedStatement.setInt(4, afiliacion.getNaturalezaJuridicaEmpleador());
				preparedStatement.setString(5, afiliacion.getTipoDocumentoEmpleador());
				preparedStatement.setString(6, afiliacion.getNumeroDocumentoEmpleador());
				preparedStatement.setString(7, afiliacion.getSerialSAT());
				preparedStatement.setString(8, afiliacion.getPrimerNombreEmpleador());
				preparedStatement.setString(9, afiliacion.getSegundoNombreEmpleador());
				preparedStatement.setString(10, afiliacion.getPrimerApellidoEmpleador());
				preparedStatement.setString(11, afiliacion.getSegundoApellidoEmpleador());
				preparedStatement.setDate(12, Calendario.fechaSql(afiliacion.getFechaSolicitud()));
				preparedStatement.setString(13, afiliacion.getPerdidaAfiliacionCausaGrave());
				preparedStatement.setDate(14, fechaAfi.isEmpty() ? null : Calendario.fechaSql(fechaAfi));
				preparedStatement.setString(15, afiliacion.getRazonSocial());
				preparedStatement.setString(16, afiliacion.getNumeroMatriculaMercantil());
				preparedStatement.setString(17, afiliacion.getDepartamento());
				preparedStatement.setString(18, afiliacion.getMunicipio());
				preparedStatement.setString(19, afiliacion.getDireccionContacto());
				preparedStatement.setString(20, afiliacion.getNumeroTelefono());
				preparedStatement.setString(21, afiliacion.getCorreoElectronico());
				preparedStatement.setString(22, afiliacion.getTipoDocumentoRepresentante());
				preparedStatement.setString(23, afiliacion.getNumeroDocumentoRepresentante());
				preparedStatement.setString(24, afiliacion.getPrimerNombreRepresentante());
				preparedStatement.setString(25, afiliacion.getSegundoNombreRepresentante());
				preparedStatement.setString(26, afiliacion.getPrimerApellidoRepresentante());
				preparedStatement.setString(27, afiliacion.getSegundoApellidoRepresentante());
				preparedStatement.setString(28, afiliacion.getAutorizacionManejoDatos());
				preparedStatement.setString(29, afiliacion.getAutorizacionNotificaciones());
				preparedStatement.setString(30, afiliacion.getManifestacion());
				preparedStatement.setString(31, afiliacion.getEstadoConfa());
				preparedStatement.setString(32, afiliacion.getCodigoCcfAnterior());
				preparedStatement.setString(33, afiliacion.getPazSalvo());
				preparedStatement.setString(34, afiliacion.getFechaPazSalvo());

				int reg = preparedStatement.executeUpdate();
				if (reg > 0) {
					bd.commit();
					salida = true;
				} else {
					bd.rollback();
				}
			} else {
				log.error("Error en el método AfiliacionesImpl.guardarAfiliacionNoPrimeraVez: No hay conexión");
			}
		} catch (SQLException e) {
			bd.rollback();
			salida = false;
			log.error("Error en el método AfiliacionesImpl.guardarAfiliacionNoPrimeraVez:" + e.getMessage());
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}
	
	public boolean guardarRespuestaWS(String resultado, String mensaje, String codigo, String numTransaccion)
	{
		boolean respuesta = false;
		PreparedStatement ps = null;
		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				bd.autoCommit(false);
	
				StringBuilder sb = new StringBuilder();
				
				sb.append("INSERT INTO recepcion.respuesta_ws(resultado,mensaje,codigo,numero_transaccion,fecha_ingreso,hora_ingreso) ");
				sb.append("VALUES(?, ?, ?, ?, current_date, current_time)");
				ps = bd.crearSentencia(sb.toString());
	
				ps.setString(1, resultado);
				ps.setString(2, mensaje);
				ps.setString(3, codigo);
				ps.setString(4, numTransaccion);
	
				int reg = ps.executeUpdate();
				if(reg>0){
					bd.commit();
					respuesta = true;
				} else {
					bd.rollback();
				}
			} else {
				log.error("Error en el método AfiliacionesImpl.guardarRespuestaWS: No hay conexión");
			}
		} catch (Exception e) {
			bd.rollback();
			respuesta = false;
			log.error("Error en el método  AfiliacionesImpl.guardarRespuestaWS: ", e);
		}
		finally {
			BaseDatos.cerrarStatement(ps);
			bd.cerrar();
		}
	
		return respuesta;
	}

	public ArrayList<RespuestaAfiliacionPrimeraVez> listarTransaccionGestionadas(String estado, int codigoNovedad) {	
		ArrayList<RespuestaAfiliacionPrimeraVez> salida = new ArrayList<RespuestaAfiliacionPrimeraVez>();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try{
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
			
				StringBuilder sb=new StringBuilder();
				sb.append("SELECT apv.numero_radicado_solicitud as numradicado, "); 
				sb.append("apv.numero_transaccion as numtransac,apv.tipo_documento_empleador as docemp, "); 
				sb.append("apv.numero_documento_empleador as numdocemp, apv.serial_sat as serialsat, "); 
				sb.append("gc.resultado_tramite as resultramite, gc.fecha_efectiva_afiliacion as fechaafil, "); 
				sb.append("gc.motivo_rechazo as motrech "); 
				sb.append("FROM recepcion.gestion_confa gc "); 
				sb.append("inner join recepcion.notificaciones_sat ns ON ns.id = gc.notificacion_id "); 
				sb.append("inner join recepcion.afiliacion_primera_vez apv ON gc.novedad_id = apv.id "); 
				sb.append("WHERE apv.estado_confa = ? AND ns.codigo_novedad = ? ");
				
				preparedStatement=bd.crearSentencia(sb.toString());
				
				preparedStatement.setString(1,estado);
				preparedStatement.setString(2,String.valueOf(codigoNovedad));
				
				resultSet=preparedStatement.executeQuery();
				while (resultSet.next()){
					RespuestaAfiliacionPrimeraVez respAfi = new RespuestaAfiliacionPrimeraVez();
					respAfi.setNumeroRadicadoSolicitud(resultSet.getString("numradicado"));
					respAfi.setNumeroTransaccion(resultSet.getString("numtransac"));
					respAfi.setTipoDocumentoEmpleador(resultSet.getString("docemp"));
					respAfi.setNumeroDocumentoEmpleador(resultSet.getString("numdocemp"));
					respAfi.setSerialSat(resultSet.getString("serialsat"));
					respAfi.setResultadoTramite(resultSet.getString("resultramite"));
					respAfi.setFechaEfectivaAfiliacion(resultSet.getDate("fechaafil")==null ? "" : resultSet.getDate("fechaafil").toString());
					respAfi.setMotivoRechazo(resultSet.getString("motrech"));
					
					salida.add(respAfi);
				}
			
			}else {
				log.error("Error en el método AfiliacionesImpl.listarTransaccionGestionadas: No hay conexión");
			}
		}catch(SQLException e){
			log.error("Error en el método AfiliacionesImpl.listarTransaccionGestionadas:"+e.getMessage());
		}finally{
			BaseDatos.cerrarResultSet(resultSet);
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}
	
	public boolean modificarEstadoAfiliacion(String notificacion, String estado) {
		boolean resultado = false;
		PreparedStatement preparedStatement=null;
		
		try{
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				StringBuilder sb=new StringBuilder();	
				sb.append("UPDATE recepcion.afiliacion_primera_vez SET estado_confa=? ");
				sb.append("WHERE numero_transaccion = ? ");
			
				preparedStatement=bd.crearSentencia(sb.toString());
				preparedStatement.setString(1,estado);
				preparedStatement.setString(2,notificacion);
				if (preparedStatement.executeUpdate()>0){
					resultado = true;
				}
			
			} else {
				log.error("Error en el método AfiliacionesImpl.modificarEstadoAfiliacion: No hay conexión");
			}
		}catch(SQLException e){
			log.error("Error en el método AfiliacionesImpl.modificarEstadoAfiliacion:"+e.getMessage());
		}finally{
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return resultado;
	}
	
	public boolean guardarErroresAfiPrimeraVez(String id_glosa, String numTransaccion){
		boolean respuesta = false;
		PreparedStatement ps = null;
		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				bd.autoCommit(false);
	
				StringBuilder sb = new StringBuilder();
				
				sb.append("INSERT INTO recepcion.glosas_respuesta_automatica(id_glosa,numero_transaccion,fecha_ingreso,hora_ingreso) ");
				sb.append("VALUES(?, ?, current_date, current_time)");
				ps = bd.crearSentencia(sb.toString());
	
				ps.setString(1, id_glosa);
				ps.setString(2, numTransaccion);
	
				int reg = ps.executeUpdate();
				if(reg>0){
					bd.commit();
					respuesta = true;
				} else {
					bd.rollback();
				}
			} else {
				log.error("Error en el método AfiliacionesImpl.guardarErroresAfiPrimeraVez: No hay conexión");
			}
		} catch (Exception e) {
			bd.rollback();
			respuesta = false;
			log.error("Error en el método  AfiliacionesImpl.guardarErroresAfiPrimeraVez: ", e);
		}
		finally {
			BaseDatos.cerrarStatement(ps);
			bd.cerrar();
		}
	
		return respuesta;
	}
	
	public boolean modificarEstadoGlosasAfiPrimeraVez(String estadoError, String numTransaccion, String idGlosa) {
		boolean resultado = false;
		PreparedStatement preparedStatement=null;
		
		try{
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				StringBuilder sb=new StringBuilder();	
				sb.append("UPDATE recepcion.glosas_respuesta_automatica SET estado_errores_reportados=? ");
				sb.append("WHERE numero_transaccion = ? AND id_glosa = ? ");
			
				preparedStatement=bd.crearSentencia(sb.toString());
				preparedStatement.setString(1,estadoError);
				preparedStatement.setString(2,numTransaccion);
				preparedStatement.setString(3,idGlosa);
				if (preparedStatement.executeUpdate()>0){
					resultado = true;
				}
			
			} else {
				log.error("Error en el método AfiliacionesImpl.modificarEstadoGlosasAfiPrimeraVez: No hay conexión");
			}
		}catch(SQLException e){
			log.error("Error en el método AfiliacionesImpl.modificarEstadoGlosasAfiPrimeraVez:"+e.getMessage());
		}finally{
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return resultado;
	}

	public String listarDescripcionGlosas(String estado) {
		String salida = "";
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try{
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
			
				StringBuilder sb=new StringBuilder();
				sb.append("SELECT id as id_glosa, descripcion "); 
				sb.append("FROM recepcion.tipo_glosa_recepcion "); 
				sb.append("WHERE id = ? ");
				
				preparedStatement=bd.crearSentencia(sb.toString());	
				preparedStatement.setString(1,estado);
				
				resultSet=preparedStatement.executeQuery();
				if (resultSet.next()){
					salida = resultSet.getString("descripcion");
				}
			
			}else {
				log.error("Error en el método AfiliacionesImpl.listarDescripcionGlosas: No hay conexión");
			}
		}catch(SQLException e){
			log.error("Error en el método AfiliacionesImpl.listarDescripcionGlosas:"+e.getMessage());
		}finally{
			BaseDatos.cerrarResultSet(resultSet);
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}

	public boolean guardarDesafiliacion(DesafiliacionEmpresas desafiliacion) {
		boolean salida = false;
		PreparedStatement preparedStatement = null;

		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				bd.autoCommit(false);
				StringBuilder sql = new StringBuilder();
				sql.append("recepcion.desafiliacion_empresa( ");
				sql.append("numero_radicado_solicitud, numero_transaccion, tipo_documento_empleador,  ");
				sql.append("numero_documento_empleador, serial_sat, fecha_solicitud_desafiliacion, ");
				sql.append("fecha_efectividad_desafiliacion, departamento, autorizacion_manejo_datos,");
				sql.append("autorizacion_notificaciones, paz_salvo, fecha_paz_salvo,");
				sql.append("fecha_ingreso_desafiliacion, hora_ingreso_desafiliacion, estado_confa ");
				sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
				sql.append("current_date, current_time, ?) ");
				preparedStatement = bd.crearSentencia(sql.toString());

				String fechaDesafi = desafiliacion.getFechaEfectivaDesafiliacion();
				String fechaPazSalvo = desafiliacion.getFechaPazSalvo();

				preparedStatement.setString(1, desafiliacion.getNumeroRadicadoSolicitud());
				preparedStatement.setString(2, desafiliacion.getNumeroTransaccion());
				preparedStatement.setString(3, desafiliacion.getTipoDocumentoEmpleador());
				preparedStatement.setString(4, desafiliacion.getNumeroDocumentoEmpleador());
				preparedStatement.setString(5, desafiliacion.getSerialSAT());
				preparedStatement.setDate(6, Calendario.fechaSql(desafiliacion.getFechaSolicitudDesafiliacion()));
				preparedStatement.setDate(7, fechaDesafi.isEmpty() ? null : Calendario.fechaSql(fechaDesafi));
				preparedStatement.setString(8, desafiliacion.getDepartamento());
				preparedStatement.setString(9, desafiliacion.getAutorizacionManejoDatos());
				preparedStatement.setString(10, desafiliacion.getAutorizacionNotificaciones());
				preparedStatement.setString(11, desafiliacion.getPazSalvo());
				preparedStatement.setDate(12, fechaPazSalvo.isEmpty() ? null : Calendario.fechaSql(fechaPazSalvo));
				preparedStatement.setString(13, desafiliacion.getEstadoConfa());

				int reg = preparedStatement.executeUpdate();
				if (reg > 0) {
					bd.commit();
					salida = true;
				} else {
					bd.rollback();
				}
			} else {
				log.error("Error en el método afiliacionesImpl.guardarDesafiliacion: No hay conexión");
			}
		} catch (SQLException e) {
			bd.rollback();
			salida = false;
			log.error("Error en el método afiliacionesImpl.guardarDesafiliacion:" + e.getMessage());
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}

	public boolean guardarInicioRelacionLaboral(InicioRelacionLaboral afiliacion) {
		boolean salida = false;
		PreparedStatement preparedStatement = null;

		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				bd.autoCommit(false);
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO recepcion.inicio_relacion_laboral( ");
				sql.append("numero_radicado_solicitud, numero_transaccion, tipo_documento_empleador,   ");
				sql.append("numero_documento_empleador, serial_sat, tipo_inicio, fecha_inicio,  ");
				sql.append("tipo_documento_trabajador, numero_documento_trabajador, primer_nombre_trabajador, ");
				sql.append("segundo_nombre_trabajador, primer_apellido_trabajador, segundo_apellido_trabajador, ");
				sql.append("sexo_trabajador, fecha_nacimiento_trabajador, departamento, municipio,  ");
				sql.append(" direccion_contacto, numero_telefono, correo_electronico, salario, ");
				sql.append(" tipo_salario, horas_trabajo, autorizacion_manejo_datos, autorizacion_notificaciones,  ");
				sql.append(" fecha_ingreso_inicio_relacion, hora_ingreso_inicio_relacion, estado_confa) ");
				sql.append("VALUES (?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?,?, ?, ?, ?,?, ?, ?, ?,?, ?, ?, ?, ");
				sql.append("current_date, current_time, ?) ");
				preparedStatement = bd.crearSentencia(sql.toString());

//				String fechaInicio = afiliacion.getFechaInicio();
				String fechaNacimiento = afiliacion.getFechaNacimientoTrabajador();

				preparedStatement.setString(1, afiliacion.getNumeroRadicadoSolicitud());
				preparedStatement.setString(2, afiliacion.getNumeroTransaccion());
				preparedStatement.setString(3, afiliacion.getTipoDocumentoEmpleador());
				preparedStatement.setString(4, afiliacion.getNumeroDocumentoEmpleador());
				preparedStatement.setString(5, afiliacion.getSerialSAT());
				preparedStatement.setString(6, afiliacion.getTipoInicio());
				preparedStatement.setDate(7, Calendario.fechaSql(afiliacion.getFechaInicio()));
				preparedStatement.setString(8, afiliacion.getTipoDocumentoTrabajador());
				preparedStatement.setString(9, afiliacion.getNumeroDocumentoTrabajador());
				preparedStatement.setString(10, afiliacion.getPrimerNombreTrabajador());
				preparedStatement.setString(11, afiliacion.getSegundoNombreTrabajador());
				preparedStatement.setString(12, afiliacion.getPrimerApellidoTrabajador());
				preparedStatement.setString(13, afiliacion.getSegundoApellidoTrabajador());
				preparedStatement.setString(14, afiliacion.getSexoTrabajador());
				preparedStatement.setDate(15, fechaNacimiento.isEmpty() ? null : Calendario.fechaSql(fechaNacimiento));
				preparedStatement.setString(16, afiliacion.getDepartamento());
				preparedStatement.setString(17, afiliacion.getMunicipio());
				preparedStatement.setString(18, afiliacion.getDireccionContacto());
				preparedStatement.setString(19, afiliacion.getNumeroTelefono());
				preparedStatement.setString(20, afiliacion.getCorreoElectronico());
				preparedStatement.setString(21, afiliacion.getSalario());
				preparedStatement.setString(22, afiliacion.getTipoSalario());
				preparedStatement.setString(23, afiliacion.getHorasTrabajo());
				preparedStatement.setString(24, afiliacion.getAutorizacionManejoDatos());
				preparedStatement.setString(25, afiliacion.getAutorizacionNotificaciones());
				preparedStatement.setString(26, afiliacion.getEstadoConfa());

				int reg = preparedStatement.executeUpdate();
				if (reg > 0) {
					bd.commit();
					salida = true;
				} else {
					bd.rollback();
				}
			} else {
				log.error("Error en el método afiliacionesImpl.guardarInicioRelacionLaboral: No hay conexión");
			}
		} catch (SQLException e) {
			bd.rollback();
			salida = false;
			log.error("Error en el método afiliacionesImpl.guardarInicioRelacionLaboral:" + e.getMessage());
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}

	public boolean guardarFinRelacionLaboral(FinRelacionLaboral afiliacion) {
		boolean salida = false;
		PreparedStatement preparedStatement = null;

		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				bd.autoCommit(false);
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO recepcion.fin_relacion_laboral( ");
				sql.append("numero_radicado_solicitud, numero_transaccion, tipo_documento_empleador,    ");
				sql.append("numero_documento_empleador, serial_sat, tipo_terminacion, fecha_terminacion,  ");
				sql.append("tipo_documento_trabajador, numero_documento_trabajador, primer_nombre_trabajador, ");
				sql.append("primer_apellido_trabajador, autorizacion_manejo_datos, autorizacion_notificaciones,  ");
				sql.append("fecha_ingreso_inicio_relacion, hora_ingreso_inicio_relacion, estado_confa)  ");
				sql.append("VALUES (?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ? ");
				sql.append("current_date, current_time, ?) ");
				preparedStatement = bd.crearSentencia(sql.toString());

//				String fechaTerminacion = afiliacion.getFechaTerminacion();

				preparedStatement.setString(1, afiliacion.getNumeroRadicadoSolicitud());
				preparedStatement.setString(2, afiliacion.getNumeroTransaccion());
				preparedStatement.setString(3, afiliacion.getTipoDocumentoEmpleador());
				preparedStatement.setString(4, afiliacion.getNumeroDocumentoEmpleador());
				preparedStatement.setString(5, afiliacion.getSerialSAT());
				preparedStatement.setString(6, afiliacion.getTipoTerminacion());
				preparedStatement.setDate(7, Calendario.fechaSql(afiliacion.getFechaTerminacion()));
				preparedStatement.setString(8, afiliacion.getTipoDocumentoTrabajador());
				preparedStatement.setString(9, afiliacion.getNumeroDocumentoTrabajador());
				preparedStatement.setString(10, afiliacion.getPrimerNombreTrabajador());
				preparedStatement.setString(11, afiliacion.getPrimerApellidoTrabajador());
				preparedStatement.setString(12, afiliacion.getAutorizacionManejoDatos());
				preparedStatement.setString(13, afiliacion.getAutorizacionNotificaciones());
				preparedStatement.setString(14, afiliacion.getEstadoConfa());

				int reg = preparedStatement.executeUpdate();
				if (reg > 0) {
					bd.commit();
					salida = true;
				} else {
					bd.rollback();
				}
			} else {
				log.error("Error en el método afiliacionesImpl.guardarInicioRelacionLaboral: No hay conexión");
			}
		} catch (SQLException e) {
			bd.rollback();
			salida = false;
			log.error("Error en el método afiliacionesImpl.guardarInicioRelacionLaboral:" + e.getMessage());
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}
	
	public ArrayList<RespuestaAfiliacionNoPrimeraVez> listarTransaccionGestionadasNoPrimeraVez(String estado, int codigoNovedad) {	
		ArrayList<RespuestaAfiliacionNoPrimeraVez> salida = new ArrayList<RespuestaAfiliacionNoPrimeraVez>();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try{
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
			
				StringBuilder sb=new StringBuilder();
				sb.append("SELECT anpv.numero_radicado_solicitud as numradicado, "); 
				sb.append("anpv.numero_transaccion as numtransac,anpv.tipo_documento_empleador as docemp, "); 
				sb.append("anpv.numero_documento_empleador as numdocemp, anpv.serial_sat as serialsat, "); 
				sb.append("gc.resultado_tramite as resultramite, gc.fecha_efectiva_afiliacion as fechaafil, "); 
				sb.append("gc.motivo_rechazo as motrech "); 
				sb.append("FROM recepcion.gestion_confa gc "); 
				sb.append("inner join recepcion.notificaciones_sat ns ON ns.id = gc.notificacion_id "); 
				sb.append("inner join recepcion.afiliacion_no_primera_vez anpv ON gc.novedad_id = anpv.id "); 
				sb.append("WHERE anpv.estado_confa = ? AND ns.codigo_novedad = ? ");
				
				preparedStatement=bd.crearSentencia(sb.toString());
				
				preparedStatement.setString(1,estado);
				preparedStatement.setString(2,String.valueOf(codigoNovedad));
				
				resultSet=preparedStatement.executeQuery();
				while (resultSet.next()){
					RespuestaAfiliacionNoPrimeraVez respAfi = new RespuestaAfiliacionNoPrimeraVez();
					respAfi.setNumeroRadicadoSolicitud(resultSet.getString("numradicado"));
					respAfi.setNumeroTransaccion(resultSet.getString("numtransac"));
					respAfi.setTipoDocumentoEmpleador(resultSet.getString("docemp"));
					respAfi.setNumeroDocumentoEmpleador(resultSet.getString("numdocemp"));
					respAfi.setSerialSat(resultSet.getString("serialsat"));
					respAfi.setResultadoTramite(resultSet.getString("resultramite"));
					respAfi.setFechaEfectivaAfiliacion(resultSet.getDate("fechaafil")==null ? "" : resultSet.getDate("fechaafil").toString());
					respAfi.setMotivoRechazo(resultSet.getString("motrech"));
					
					salida.add(respAfi);
				}
			
			}else {
				log.error("Error en el método AfiliacionesImpl.listarTransaccionGestionadas: No hay conexión");
			}
		}catch(SQLException e){
			log.error("Error en el método AfiliacionesImpl.listarTransaccionGestionadas:"+e.getMessage());
		}finally{
			BaseDatos.cerrarResultSet(resultSet);
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}
	
	public ArrayList<RespuestaDesafiliacionCCF> listarTransaccionGestionadasDesafiliaciones(String estado, int codigoNovedad) {
		ArrayList<RespuestaDesafiliacionCCF> salida = new ArrayList<RespuestaDesafiliacionCCF>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {

				StringBuilder sb = new StringBuilder();
				sb.append("SELECT de.numero_radicado_solicitud as numradicado, ");
				sb.append("de.numero_transaccion as numtransac,de.tipo_documento_empleador as docemp, ");
				sb.append("de.numero_documento_empleador as numdocemp, de.serial_sat as serialsat, ");
				sb.append("gc.resultado_tramite as resultramite, gc.fecha_efectiva_afiliacion as fechaafil, ");
				sb.append("gc.motivo_rechazo as motrech, gc.paz_salvo as pazsalvo, gc.fecha_paz_salvo as fechapaz ");
				sb.append("FROM recepcion.gestion_confa gc ");
				sb.append(" inner join recepcion.desafiliacion_empresa de ON de.id = gc.novedad_id ");
				sb.append(" inner join recepcion.notificaciones_sat ns ON ns.id = gc.notificacion_id");
				sb.append(" WHERE gc.estado_gestion = ? AND ns.codigo_novedad = ? AND de.estado_confa=?");

				preparedStatement = bd.crearSentencia(sb.toString());

				preparedStatement.setString(1, estado);
				preparedStatement.setString(2, String.valueOf(codigoNovedad));
				preparedStatement.setString(3, estado);
				
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					RespuestaDesafiliacionCCF respAfi = new RespuestaDesafiliacionCCF();
					respAfi.setNumeroRadicadoSolicitud(resultSet.getString("numradicado"));
					respAfi.setNumeroTransaccion(resultSet.getString("numtransac"));
					respAfi.setTipoDocumentoEmpleador(resultSet.getString("docemp"));
					respAfi.setNumeroDocumentoEmpleador(resultSet.getString("numdocemp"));
					respAfi.setSerialSat(resultSet.getString("serialsat"));
					respAfi.setResultadoTramite(resultSet.getString("resultramite"));
					respAfi.setFechaEfectivaDesafiliacion(resultSet.getString("fechaafil"));
					respAfi.setMotivoRechazo(resultSet.getString("motrech"));
					respAfi.setPazSalvo(resultSet.getString("pazsalvo"));
					respAfi.setFechaPazSalvo(resultSet.getString("fechapaz"));

					salida.add(respAfi);
				}

			} else {
				log.error("Error en el método NotificacionesImpl.listarTransaccionGestionadas: No hay conexión");
			}
		} catch (SQLException e) {
			log.error("Error en el método NotificacionesImpl.listarTransaccionGestionadas:" + e.getMessage());
		} finally {
			BaseDatos.cerrarResultSet(resultSet);
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}
	
	public ArrayList<RespuestaInicioRelacion> listarTransaccionGestionadasInicioRelacion(String estado, int codigoNovedad) {
		ArrayList<RespuestaInicioRelacion> salida = new ArrayList<RespuestaInicioRelacion>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {

				StringBuilder sb = new StringBuilder();
				sb.append("SELECT inirel.numero_radicado_solicitud as numradicado,");
				sb.append("inirel.numero_transaccion as numtransac,inirel.tipo_documento_empleador as docemp, ");
				sb.append("inirel.numero_documento_empleador as numdocemp, inirel.serial_sat as serialsat, ");
				sb.append("gc.resultado_tramite as resultramite, gc.fecha_efectiva_afiliacion as fechaafil, ");
				sb.append("gc.motivo_rechazo as motrech ");
				sb.append("FROM recepcion.gestion_confa gc  ");
				sb.append(" inner join recepcion.inicio_relacion_laboral inirel ON inirel.id = gc.novedad_id  ");
				sb.append(" inner join recepcion.notificaciones_sat ns ON ns.id = gc.notificacion_id");
				sb.append(" WHERE gc.estado_gestion = ? AND ns.codigo_novedad = ? AND inirel.estado_confa=?");

				preparedStatement = bd.crearSentencia(sb.toString());

				preparedStatement.setString(1, estado);
				preparedStatement.setString(2, String.valueOf(codigoNovedad));
				preparedStatement.setString(3, estado);
				
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					RespuestaInicioRelacion respAfi = new RespuestaInicioRelacion();
					respAfi.setNumeroRadicadoSolicitud(resultSet.getString("numradicado"));
					respAfi.setNumeroTransaccion(resultSet.getString("numtransac"));
					respAfi.setTipoDocumentoEmpleador(resultSet.getString("docemp"));
					respAfi.setNumeroDocumentoEmpleador(resultSet.getString("numdocemp"));
					respAfi.setSerialSat(resultSet.getString("serialsat"));
					respAfi.setResultadoTramite(resultSet.getString("resultramite"));
					respAfi.setMotivoRechazo(resultSet.getString("motrech"));

					salida.add(respAfi);
				}

			} else {
				log.error("Error en el método NotificacionesImpl.listarTransaccionGestionadasInicioRelacion: No hay conexión");
			}
		} catch (SQLException e) {
			log.error("Error en el método NotificacionesImpl.listarTransaccionGestionadasInicioRelacion:" + e.getMessage());
		} finally {
			BaseDatos.cerrarResultSet(resultSet);
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}
	
	public ArrayList<RespuestaFinRelacion> listarTransaccionGestionadasFinRelacion(String estado, int codigoNovedad) {
		ArrayList<RespuestaFinRelacion> salida = new ArrayList<RespuestaFinRelacion>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {

				StringBuilder sb = new StringBuilder();
				sb.append("SELECT finrel.numero_radicado_solicitud as numradicado,");
				sb.append("finrel.numero_transaccion as numtransac,finrel.tipo_documento_empleador as docemp, ");
				sb.append("finrel.numero_documento_empleador as numdocemp, finrel.serial_sat as serialsat, ");
				sb.append("gc.resultado_tramite as resultramite, gc.fecha_efectiva_afiliacion as fechaafil, ");
				sb.append("gc.motivo_rechazo as motrech ");
				sb.append("FROM recepcion.gestion_confa gc   ");
				sb.append(" inner join recepcion.fin_relacion_laboral finrel ON finrel.id = gc.novedad_id   ");
				sb.append(" inner join recepcion.notificaciones_sat ns ON ns.id = gc.notificacion_id");
				sb.append(" WHERE gc.estado_gestion = ? AND ns.codigo_novedad = ? AND finrel.estado_confa=?");

				preparedStatement = bd.crearSentencia(sb.toString());

				preparedStatement.setString(1, estado);
				preparedStatement.setString(2, String.valueOf(codigoNovedad));
				preparedStatement.setString(3, estado);
				
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					RespuestaFinRelacion respAfi = new RespuestaFinRelacion();
					respAfi.setNumeroRadicadoSolicitud(resultSet.getString("numradicado"));
					respAfi.setNumeroTransaccion(resultSet.getString("numtransac"));
					respAfi.setTipoDocumentoEmpleador(resultSet.getString("docemp"));
					respAfi.setNumeroDocumentoEmpleador(resultSet.getString("numdocemp"));
					respAfi.setSerialSat(resultSet.getString("serialsat"));
					respAfi.setResultadoTramite(resultSet.getString("resultramite"));
					respAfi.setMotivoRechazo(resultSet.getString("motrech"));

					salida.add(respAfi);
				}

			} else {
				log.error("Error en el método NotificacionesImpl.listarTransaccionGestionadasInicioRelacion: No hay conexión");
			}
		} catch (SQLException e) {
			log.error("Error en el método NotificacionesImpl.listarTransaccionGestionadasInicioRelacion:" + e.getMessage());
		} finally {
			BaseDatos.cerrarResultSet(resultSet);
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return salida;
	}
	
	public boolean modificarEstadoAfiliacionNoPrimeraVez(String notificacion, String estado) {
		boolean resultado = false;
		PreparedStatement preparedStatement = null;

		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				StringBuilder sb = new StringBuilder();
				sb.append("UPDATE recepcion.afiliacion_no_primera_vez SET estado_confa=? ");
				sb.append("WHERE numero_transaccion = ? ");

				preparedStatement = bd.crearSentencia(sb.toString());
				preparedStatement.setString(1, estado);
				preparedStatement.setString(2, notificacion);
				if (preparedStatement.executeUpdate() > 0) {
					resultado = true;
				}

			} else {
				log.error("Error en el método NotificacionesImpl.modificarEstadoAfiliacionNoPrimeraVez: No hay conexión");
			}
		} catch (SQLException e) {
			log.error("Error en el método NotificacionesImpl.modificarEstadoAfiliacionNoPrimeraVez:" + e.getMessage());
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return resultado;
	}
	
	public boolean modificarEstadoDesafiliacion(String notificacion, String estado) {
		boolean resultado = false;
		PreparedStatement preparedStatement = null;

		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				StringBuilder sb = new StringBuilder();
				sb.append("UPDATE recepcion.desafiliacion_empresa SET estado_confa=? ");
				sb.append("WHERE numero_transaccion = ? ");

				preparedStatement = bd.crearSentencia(sb.toString());
				preparedStatement.setString(1, estado);
				preparedStatement.setString(2, notificacion);
				if (preparedStatement.executeUpdate() > 0) {
					resultado = true;
				}

			} else {
				log.error("Error en el método NotificacionesImpl.modificarEstadoDesafiliacion: No hay conexión");
			}
		} catch (SQLException e) {
			log.error("Error en el método NotificacionesImpl.modificarEstadoDesafiliacion:" + e.getMessage());
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return resultado;
	}
	
	public boolean modificarEstadoInicioRelacion(String notificacion, String estado) {
		boolean resultado = false;
		PreparedStatement preparedStatement = null;

		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				StringBuilder sb = new StringBuilder();
				sb.append("UPDATE recepcion.inicio_relacion_laboral SET estado_confa=? ");
				sb.append("WHERE numero_transaccion = ? ");

				preparedStatement = bd.crearSentencia(sb.toString());
				preparedStatement.setString(1, estado);
				preparedStatement.setString(2, notificacion);
				if (preparedStatement.executeUpdate() > 0) {
					resultado = true;
				}

			} else {
				log.error("Error en el método NotificacionesImpl.modificarEstadoInicioRelacion: No hay conexión");
			}
		} catch (SQLException e) {
			log.error("Error en el método NotificacionesImpl.modificarEstadoInicioRelacion:" + e.getMessage());
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return resultado;
	}
	
	public boolean modificarEstadoFinRelacion(String notificacion, String estado) {
		boolean resultado = false;
		PreparedStatement preparedStatement = null;

		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				StringBuilder sb = new StringBuilder();
				sb.append("UPDATE recepcion.fin_relacion_laboral SET estado_confa=? ");
				sb.append("WHERE numero_transaccion = ? ");

				preparedStatement = bd.crearSentencia(sb.toString());
				preparedStatement.setString(1, estado);
				preparedStatement.setString(2, notificacion);
				if (preparedStatement.executeUpdate() > 0) {
					resultado = true;
				}

			} else {
				log.error("Error en el método NotificacionesImpl.modificarEstadoFinRelacion: No hay conexión");
			}
		} catch (SQLException e) {
			log.error("Error en el método NotificacionesImpl.modificarEstadoFinRelacion:" + e.getMessage());
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return resultado;
	}
	
	
	
	
}
