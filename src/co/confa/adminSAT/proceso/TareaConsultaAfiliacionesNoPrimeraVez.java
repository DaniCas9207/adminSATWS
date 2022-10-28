package co.confa.adminSAT.proceso;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import co.confa.adminSAT.core.ConsultarNovedades;

public class TareaConsultaAfiliacionesNoPrimeraVez implements Job{
	
	private static final Logger log = Logger.getLogger(TareaConsultaAfiliacionesNoPrimeraVez.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("TareaConsultaAfiliacionesNOPrimeraVez.execute(arg0)-> inicio");
		try {
		    ConsultarNovedades read  = new ConsultarNovedades();
		    //Consultar transacciones al SAT
		    int valor = read.consultarTransaccionesAfiNoPrimeraVez();
		    log.info("TareaConsultaAfiliacionesNOPrimeraVez.execute(arg0)-> recibir transacciones "+valor);
		} catch (Exception e) {
		    log.error("ERROR: TareaConsultaAfiliacionesNOPrimeraVez.execute()-> ", e);
		    e.printStackTrace();
		}
		
		log.info("TareaConsultaAfiliacionesNOPrimeraVez.execute(arg0)-> fin");
		
	}

}
