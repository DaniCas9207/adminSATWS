package co.confa.adminSAT.proceso;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import co.confa.adminSAT.core.RespuestaNovedades;

public class TareaRespuestaDesafiliacionIndePens implements Job {
	private static final Logger log = Logger.getLogger(TareaRespuestaDesafiliacionIndePens.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("TareaRespuestaDesafiliacionIndePens.execute(arg0)-> inicio");
		try {
			RespuestaNovedades read = new RespuestaNovedades();
		    //Consultar transacciones al SAT
		    int valor = read.responderTransaccionesDesafiliacionIndepPens();
		    log.info("TareaRespuestaDesafiliacionIndePens.execute(arg0)-> responder afiliaciones: "+valor);
		} catch (Exception e) {
		    log.error("ERROR: TareaRespuestaDesafiliacionIndePens.execute()-> ", e);
		    e.printStackTrace();
		}
		
		log.info("TareaRespuestaDesafiliacionIndePens.execute(arg0)-> fin");
		
	}
}
