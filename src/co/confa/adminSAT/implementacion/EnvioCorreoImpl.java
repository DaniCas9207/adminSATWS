package co.confa.adminSAT.implementacion;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import co.confa.adminSAT.configuracion.EnvioCorreoAfiliacion;
import co.confa.adminSAT.configuracion.IConstantes;
import co.confa.bd.BaseDatos;

public class EnvioCorreoImpl {

    private static final Logger log = Logger.getLogger(EnvioCorreoImpl.class);
	
	public boolean obtenerEnviarCorreos(String numeroTransaccion, int tipoAfiliacion, String fechaSolicitud) {

		EnvioCorreoAfiliacion historico = new EnvioCorreoAfiliacion();
		String correosPrincipal = "";
		String correosCopia = "";
		BaseDatos bd= new BaseDatos();
		ResultSet rs=null;
		boolean errorEnvioNotificacion = false;
		try {
			if(bd.conectar(IConstantes.POOL_INGRESO)) {

				StringBuilder sb = new StringBuilder();
				sb.append("select correo_principal, correo_copia ");
				sb.append("from recepcion.listado_correo_recepcion ");
				rs=bd.ejecutarConsulta(sb.toString());
				int count=1;
				while(rs.next()) {
					if(count>1) {
						if(rs.getString("correo_principal")!=null && !rs.getString("correo_principal").isEmpty())
							correosPrincipal += ","+rs.getString("correo_principal");
						if(rs.getString("correo_copia")!=null && !rs.getString("correo_copia").isEmpty())
							correosCopia += ","+rs.getString("correo_copia");
					}else {
						if(rs.getString("correo_principal")!=null && !rs.getString("correo_principal").isEmpty())
							correosPrincipal = rs.getString("correo_principal");
						if(rs.getString("correo_copia")!=null && !rs.getString("correo_copia").isEmpty())
							correosCopia = rs.getString("correo_copia");
					}
					count++;
				}

				if(correosPrincipal!="")
					historico.setTo(correosPrincipal);
				if(correosCopia!="")
					historico.setCc(correosCopia);
				errorEnvioNotificacion = historico.enviarCorreoElectronico(numeroTransaccion, tipoAfiliacion, fechaSolicitud);
			}else {
				log.error("problemas en los resultados de la tabla historico_ejecucion" );
			}

		} catch (Exception e) {
			log.error("ERROR: EnvioCorreoImpl.execute()-> ", e);
			e.printStackTrace();
		}finally {
			BaseDatos.cerrarResultSet(rs);
			bd.cerrar();
		}
		return errorEnvioNotificacion;
	}

}
