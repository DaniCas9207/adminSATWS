package co.confa.adminSAT.proceso;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import co.confa.adminSAT.core.RespuestaNovedades;

public class TareaRespuestaDesafiliacionEmpresas implements Job {
	
	private static final Logger log = Logger.getLogger(TareaRespuestaDesafiliacionEmpresas.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("TareaRespuestaDesafiliacionEmpresas.execute(arg0)-> inicio");
		try {
			RespuestaNovedades read = new RespuestaNovedades();
		    //Consultar transacciones al SAT
		    int valor = read.responderTransaccionesDesafiliacionEmpresa();
		    log.info("TareaRespuestaDesafiliacionEmpresas.execute(arg0)-> responder desafiliaciones: "+valor);
		} catch (Exception e) {
		    log.error("ERROR: TareaRespuestaDesafiliacionEmpresas.execute()-> ", e);
		    e.printStackTrace();
		}
		
		log.info("TareaRespuestaDesafiliacionEmpresas.execute(arg0)-> fin");
		
	}
}
