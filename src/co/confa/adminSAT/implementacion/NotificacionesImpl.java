package co.confa.adminSAT.implementacion;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import co.confa.adminSAT.configuracion.IConstantes;
import co.confa.adminSAT.modelo.NotificacionSAT;
import co.confa.bd.BaseDatos;

/**
 * 
 * @author tec_danielc
 *
 */
public class NotificacionesImpl implements Serializable{
	
	/**
	 * N�mero serial de la clase
	 */
	private static final long serialVersionUID = -3846127701351098728L;
	private Logger log = Logger.getLogger(NotificacionesImpl.class);
	private BaseDatos bd = new BaseDatos();
	
	public NotificacionesImpl() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Metodo encargado de guardar las notificaciones enviadas por parte del SAT
	 * 
	 * @param notificacion
	 * @param estadoConfa
	 * @return
	 */
	public String guardarNotificacionesSAT(NotificacionSAT notificacion, String estadoConfa) {
		String respuesta = "";			
		PreparedStatement preparedStatement = null;

		try {
			if (bd.conectar(IConstantes.POOL_INGRESO)) {	
				bd.autoCommit(false);
				StringBuilder sql = new StringBuilder();

				sql.append("INSERT INTO recepcion.notificaciones_sat( ");
				sql.append("numero_transaccion, codigo_novedad, fecha_creacion_novedad, fecha_fin_vigencia, estado_flujo, url, fecha_ingreso_notificacion, hora_ingreso_notificacion, estado_confa) ");
				sql.append("VALUES (?, ?, ?, ?, ?, ?, current_date, current_time, ?) ");			
				preparedStatement = bd.crearSentencia(sql.toString());						
					
				preparedStatement.setString(1, notificacion.getNumeroTransaccion());
				preparedStatement.setString(2, notificacion.getCodigoNovedad());
				preparedStatement.setString(3, notificacion.getFechaCreacionNovedad());
				preparedStatement.setString(4, notificacion.getFechaFinVigencia());
				preparedStatement.setString(5, notificacion.getEstadoFlujo());
				preparedStatement.setString(6, notificacion.getUrl());
				preparedStatement.setString(7, estadoConfa);
				
				int reg = preparedStatement.executeUpdate();
				if(reg>0){
					bd.commit();
					respuesta = IConstantes.RESPUESTA_CORRECTA;
				} else {
					bd.rollback();
				}

			}else {
				log.error("Error en el m�todo NotificacionesImpl.guardarNotificacionesSAT: No hay conexi�n");
			}
		} catch (SQLException e) {
			bd.rollback();
			log.error("Error en el m�todo NotificacionesImpl.guardarNotificacionesSAT:" + e.getMessage());
			respuesta = e.getMessage().toString();
		} finally {
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return respuesta;
	}

	/**
	 * Metodo encargado de verificar si ya existe una transacci�n
	 * 
	 * @param numeroTransaccion
	 * @return E=Reenviada, S=Sin procesar
	 */
	public String verificarNumeroTransaccion(String numeroTransaccion) {
		String respuesta= IConstantes.ESTADO_SIN_PROCESAR;	
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;

		try{
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
			
			StringBuilder sb=new StringBuilder();
				sb.append("SELECT numero_transaccion FROM recepcion.notificaciones_sat ");
				sb.append("WHERE numero_transaccion = ?"); 	
				
				preparedStatement=bd.crearSentencia(sb.toString());
				preparedStatement.setString(1,numeroTransaccion);
				resultSet=preparedStatement.executeQuery();
				if (resultSet.next()){
					respuesta = IConstantes.ESTADO_REENVIADA;
				}
			
			}else {
				log.error("Error en el m�todo NotificacionesImpl.verificarNumeroTransaccion: No hay conexi�n");
			}
		}catch(SQLException e){
			log.error("Error en el m�todo NotificacionesImpl.verificarNumeroTransaccion:"+e.getMessage());
		}finally{
			BaseDatos.cerrarResultSet(resultSet);
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return respuesta;
	}
	
	
	public ArrayList<NotificacionSAT> listarTransaccionSinProcesar(String estadoConfa, int codigoNovedad) {	
		ArrayList<NotificacionSAT> salida = new ArrayList<NotificacionSAT>();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try{
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
			
				StringBuilder sb=new StringBuilder();
				sb.append("SELECT numero_transaccion, codigo_novedad FROM recepcion.notificaciones_sat ");
				sb.append("WHERE estado_confa = ? and codigo_novedad = ? ");
				sb.append("order by id");
				
				preparedStatement=bd.crearSentencia(sb.toString());
//				preparedStatement.setString(1,IConstantes.ESTADO_SIN_PROCESAR);
//				preparedStatement.setString(2,String.valueOf(IConstantes.SERVICIO_AFILIACION_PRIMERA_VEZ));
				preparedStatement.setString(1,estadoConfa);
				preparedStatement.setString(2,String.valueOf(codigoNovedad));
				resultSet=preparedStatement.executeQuery();
				while (resultSet.next()){
					NotificacionSAT noti = new NotificacionSAT();
					noti.setNumeroTransaccion(resultSet.getString("numero_transaccion"));
					salida.add(noti);
				}
			
			}else {
				log.error("Error en el m�todo NotificacionesImpl.listarTransaccionSinProcesar: No hay conexi�n");
			}
		}catch(SQLException e){
			log.error("Error en el m�todo NotificacionesImpl.listarTransaccionSinProcesar:"+e.getMessage());
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
				sb.append("UPDATE recepcion.notificaciones_sat SET estado_confa=? ");
				sb.append("WHERE numero_transaccion = ? ");
			
				preparedStatement=bd.crearSentencia(sb.toString());
				preparedStatement.setString(1,estado);
				preparedStatement.setString(2,notificacion);
				if (preparedStatement.executeUpdate()>0){
					resultado = true;
				}
			
			} else {
				log.error("Error en el m�todo NotificacionesImpl.modificarEstadoNotificacion: No hay conexi�n");
			}
		}catch(SQLException e){
			log.error("Error en el m�todo NotificacionesImpl.modificarEstadoNotificacion:"+e.getMessage());
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
				log.error("Error en el m�todo NotificacionesImpl.modificarNotificacionRespuesta: No hay conexi�n");
			}
		}catch(SQLException e){
			log.error("Error en el m�todo NotificacionesImpl.modificarNotificacionRespuesta:"+e.getMessage());
		}finally{
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return resultado;
	}
	
	public boolean modificarEstadoGestion(String notificacion, String estado) {
		boolean resultado = false;
		PreparedStatement preparedStatement=null;
		
		try{
			if (bd.conectar(IConstantes.POOL_INGRESO)) {
				StringBuilder sb=new StringBuilder();	
				sb.append("UPDATE recepcion.notificaciones_sat SET estado_confa=?, ");
				sb.append("fecha_respuesta_notificacion = current_date, "); 
				sb.append("hora_respuesta_notificacion = current_time, ");
				sb.append("tipo_gestion = 'A' "); //gesti�n autom�tica
				sb.append("WHERE numero_transaccion = ? ");
			
				preparedStatement=bd.crearSentencia(sb.toString());
				preparedStatement.setString(1,estado);
				preparedStatement.setString(2,notificacion);
				if (preparedStatement.executeUpdate()>0){
					resultado = true;
				}
			
			} else {
				log.error("Error en el m�todo NotificacionesImpl.modificarEstadoGestion: No hay conexi�n");
			}
		}catch(SQLException e){
			log.error("Error en el m�todo NotificacionesImpl.modificarEstadoGestion:"+e.getMessage());
		}finally{
			BaseDatos.cerrarStatement(preparedStatement);
			bd.cerrar();
		}
		return resultado;
	}

}