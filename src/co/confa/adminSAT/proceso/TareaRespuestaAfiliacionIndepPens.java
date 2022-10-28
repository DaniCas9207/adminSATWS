package co.confa.adminSAT.proceso;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import co.confa.adminSAT.core.RespuestaNovedades;

public class TareaRespuestaAfiliacionIndepPens implements Job {
	private static final Logger log = Logger.getLogger(TareaRespuestaAfiliacionIndepPens.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("TareaRespuestaAfiliacionIndepPens.execute(arg0)-> inicio");
		try {
			RespuestaNovedades read = new RespuestaNovedades();
		    //Consultar transacciones al SAT
		    int valor = read.responderTransaccionesAfiIndepPens();
		    log.info("TareaRespuestaAfiliacionIndepPens.execute(arg0)-> responder afiliaciones: "+valor);
		} catch (Exception e) {
		    log.error("ERROR: TareaRespuestaAfiliacionIndepPens.execute()-> ", e);
		    e.printStackTrace();
		}
		
		log.info("TareaRespuestaAfiliacionIndepPens.execute(arg0)-> fin");
		
	}
}
