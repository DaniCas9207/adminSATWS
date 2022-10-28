package co.confa.adminSAT.proceso;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import co.confa.adminSAT.core.RespuestaNovedades;

public class TareaRespuestaAfiliacionPrimeraVez implements Job{
	
	private static final Logger log = Logger.getLogger(TareaRespuestaAfiliacionPrimeraVez.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("TareaRespuestaAfiliacionPrimeraVez.execute(arg0)-> inicio");
		try {
			RespuestaNovedades read = new RespuestaNovedades();
		    //Consultar transacciones al SAT
		    int valor = read.responderTransaccionesAfiPrimeraVez();
		    log.info("TareaRespuestaAfiliacionPrimeraVez.execute(arg0)-> responder afiliaciones: "+valor);
		} catch (Exception e) {
		    log.error("ERROR: TareaRespuestaAfiliacionPrimeraVez.execute()-> ", e);
		    e.printStackTrace();
		}
		
		log.info("TareaRespuestaAfiliacionPrimeraVez.execute(arg0)-> fin");
		
	}

}
