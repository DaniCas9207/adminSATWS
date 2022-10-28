package co.confa.adminSAT.proceso;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import co.confa.adminSAT.core.RespuestaNovedades;

public class TareaRespuestaAfiliacionNoPrimeraVez implements Job {

	private static final Logger log = Logger.getLogger(TareaRespuestaAfiliacionNoPrimeraVez.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("TareaRespuestaAfiliacionNoPrimeraVez.execute(arg0)-> inicio");
		try {
			RespuestaNovedades read = new RespuestaNovedades();
			// Consultar transacciones al SAT
			int valor = read.responderTransaccionesAfiNoPrimeraVez();
			log.info("TareaRespuestaAfiliacionNoPrimeraVez.execute(arg0)-> responder afiliaciones: " + valor);
		} catch (Exception e) {
			log.error("ERROR: TareaRespuestaAfiliacionNoPrimeraVez.execute()-> ", e);
			e.printStackTrace();
		}

		log.info("TareaRespuestaAfiliacionNoPrimeraVez.execute(arg0)-> fin");

	}

}
