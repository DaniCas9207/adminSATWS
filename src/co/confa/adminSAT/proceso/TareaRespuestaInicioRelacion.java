package co.confa.adminSAT.proceso;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import co.confa.adminSAT.core.RespuestaNovedades;

public class TareaRespuestaInicioRelacion implements Job {
	private static final Logger log = Logger.getLogger(TareaRespuestaInicioRelacion.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("TareaRespuestaInicioRelacion.execute(arg0)-> inicio");
		try {
			RespuestaNovedades read = new RespuestaNovedades();
		    //Consultar transacciones al SAT
		    int valor = read.responderTransaccionesInicioRelacion();
		    log.info("TareaRespuestaInicioRelacion.execute(arg0)-> responder inicio relacion : "+valor);
		} catch (Exception e) {
		    log.error("ERROR: TareaRespuestaInicioRelacion.execute()-> ", e);
		    e.printStackTrace();
		}
		
		log.info("TareaRespuestaInicioRelacion.execute(arg0)-> fin");
		
	}
}
