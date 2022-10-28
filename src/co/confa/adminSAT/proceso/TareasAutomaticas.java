package co.confa.adminSAT.proceso;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class TareasAutomaticas extends HttpServlet{

	/**
	 * Generacion de tareas automaticas
	 * @author tec_stivenv
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TareasAutomaticas.class);
	ResourceBundle quartz = ResourceBundle.getBundle("quartz");	
	

	public void init(ServletConfig config) throws ServletException 
	{	
		logger.info("Initializing Scheduler for Jobs");
		super.init(config);		
	
		try {			
			
			String ejecutarTareas = quartz.getString("org.quartz.scheduler.executeQuartz");
			if ( ejecutarTareas == null || !ejecutarTareas.trim().equals("S") ) {
				logger.debug("......... TareasServlet.init(): NO SE HA HABILITADO EL PLANIFICADOR DE TAREAS. NO SE EJECUTARÁ NINGUNA TAREA PROGRAMADA. POR FAVOR, COMPRUEBE EL FICHERO quartz.properties");
			} else {
				// Planificador				
				SchedulerFactory schedFact = new StdSchedulerFactory();
				Scheduler sched = schedFact.getScheduler();				
				// Antes de ejecutar cada tarea, comprobamos que esté habilitada mediante un flag.		
				String ejecutarTareaConsAfiPrimera = quartz.getString("TareaConsultaAfiliacionPrimeraVez.ejecucion"); 
				String ejecutarTareaRespuestaAfiliacion = quartz.getString("TareaRespuestaAfiliacionPrimeraVez.ejecucion"); 
				
				String ejecutarTareaNotificacionNoPrimeraVez = quartz.getString("TareaAfiliacionesNoPrimeraVezPrueba.ejecucion"); 
				String ejecutarTareaRespuestaNoAfiliacion = quartz.getString("TareaRespuestaAfiliacionNoPrimeraVez.ejecucion"); 
				
				String ejecutarTareaDesafiliacionEmpresas = quartz.getString("TareaDesafiliacionEmpresas.ejecucion"); 
				String ejecutarTareaRespuestaDesafiliacionEmpresas = quartz.getString("TareaRespuestaDesafiliacionEmpresas.ejecucion"); 
				
				String ejecutarTareaInicioRelacion = quartz.getString("TareaInicioRelacion.ejecucion");
				String ejecutarTareaRespuestaInicioRelacion = quartz.getString("TareaRespuestaInicioRelacion.ejecucion");
				
				String ejecutarTareaFinRelacion = quartz.getString("TareaFinRelacion.ejecucion");
				String ejecutarTareaRespuestaFinRelacion = quartz.getString("TareaRespuestaFinRelacion.ejecucion");
				
				String ejecutarTareaConsultaAfiliacionesIndependientes = quartz.getString("TareaConsultaAfiliacionesIndependientes.ejecucion");
				String ejecutarTareaConsultaAfiliacionesPensionados = quartz.getString("TareaConsultaAfiliacionesPensionados.ejecucion");
				String ejecutarTareaRespuestaAfiliacionIndepPens = quartz.getString("TareaRespuestaAfiliacionIndepPens.ejecucion");
				
				String ejecutarTareaConsultaDesafiliacionIndePens = quartz.getString("TareaConsultaDesafiliacionIndePens.ejecucion");
				String ejecutarTareaRespuestaDesafiliacionIndePens = quartz.getString("TareaRespuestaDesafiliacionIndePens.ejecucion");
				
				String tiempo="";
	            
				/**
				 * Tarea 1:TareaConsultaAfiliacionesPrimeraVez  
				 * 
				 */ 
				if ( ejecutarTareaConsAfiPrimera == null || !ejecutarTareaConsAfiPrimera.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaConsultaAfiliacionPrimeraVez, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaConsultaAfiliacionPrimeraVez",   null, TareaConsultaAfiliacionesPrimeraVez.class);									
					
					tiempo = quartz.getString("TareaConsultaAfiliacionPrimeraVez.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaConsultaAfiliacionPrimeraVez", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaConsultaAfiliacionPrimeraVez ");
				}	
				
				/**
				 * Tarea 2:TareaRespuestaAfiliacionPrimeraVez  
				 * 
				 */ 
				if ( ejecutarTareaRespuestaAfiliacion == null || !ejecutarTareaRespuestaAfiliacion.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaRespuestaAfiliacionPrimeraVez, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaRespuestaAfiliacionPrimeraVez",   null, TareaRespuestaAfiliacionPrimeraVez.class);									
					
					tiempo = quartz.getString("TareaRespuestaAfiliacionPrimeraVez.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaRespuestaAfiliacionPrimeraVez", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaRespuestaAfiliacionPrimeraVez ");
				}	
				
				/**
				 * Tarea 3:TareaAfiliacionesNoPrimeraVez  
				 * 
				 */ 
				if ( ejecutarTareaNotificacionNoPrimeraVez == null || !ejecutarTareaNotificacionNoPrimeraVez.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaAfiliacionesNoPrimeraVezPrueba, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaAfiliacionesNoPrimeraVezPrueba",   null, TareaAfiliacionesNoPrimeraVezLD.class);									
					
					tiempo = quartz.getString("TareaAfiliacionesNoPrimeraVezPrueba.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaAfiliacionesNoPrimeraVezPrueba", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaAfiliacionesNoPrimeraVezPrueba ");
				}	
				
				/**
				 * Tarea 4:TareaRespuestaAfiliacionNoPrimeraVez  
				 * 
				 */ 
				if ( ejecutarTareaRespuestaNoAfiliacion == null || !ejecutarTareaRespuestaNoAfiliacion.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaRespuestaAfiliacionNoPrimeraVez, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaRespuestaAfiliacionNoPrimeraVez",   null, TareaRespuestaAfiliacionNoPrimeraVez.class);									
					
					tiempo = quartz.getString("TareaRespuestaAfiliacionNoPrimeraVez.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaRespuestaAfiliacionNoPrimeraVez", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaRespuestaAfiliacionNoPrimeraVez ");
				}	
				
				/**
				 * Tarea 5:TareaDesafiliacionEmpresas  
				 * 
				 */ 
				if ( ejecutarTareaDesafiliacionEmpresas == null || !ejecutarTareaDesafiliacionEmpresas.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaDesafiliacionEmpresas, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaDesafiliacionEmpresas",   null, TareaDesafiliacionEmpresas.class);									
					
					tiempo = quartz.getString("TareaDesafiliacionEmpresas.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaDesafiliacionEmpresas", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaDesafiliacionEmpresas ");
				}	
				
				/**
				 * Tarea 6:TareaRespuestaDesafiliacionEmpresas  
				 * 
				 */ 
				if ( ejecutarTareaRespuestaDesafiliacionEmpresas == null || !ejecutarTareaRespuestaDesafiliacionEmpresas.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaDesafiliacionEmpresas, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaRespuestaDesafiliacionEmpresas",   null, TareaRespuestaDesafiliacionEmpresas.class);									
					
					tiempo = quartz.getString("TareaRespuestaDesafiliacionEmpresas.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaRespuestaDesafiliacionEmpresas", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaRespuestaDesafiliacionEmpresas ");
				}	
				
				/**
				 * Tarea 7:TareaInicioRelacion  
				 * 
				 */ 
				if ( ejecutarTareaInicioRelacion == null || !ejecutarTareaInicioRelacion.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaInicioRelacion, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaInicioRelacion",   null, TareaInicioRelacion.class);									
					
					tiempo = quartz.getString("TareaInicioRelacion.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaInicioRelacion", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaInicioRelacion ");
				}	
				
				/**
				 * Tarea 8:TareaRespuestaInicioRelacion  
				 * 
				 */ 
				if ( ejecutarTareaRespuestaInicioRelacion == null || !ejecutarTareaRespuestaInicioRelacion.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaRespuestaInicioRelacion, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaRespuestaInicioRelacion",   null, TareaRespuestaInicioRelacion.class);									
					
					tiempo = quartz.getString("TareaRespuestaInicioRelacion.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaRespuestaInicioRelacion", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaRespuestaInicioRelacion ");
				}	
				
				/**
				 * Tarea 9:TareaFinRelacion  
				 * 
				 */ 
				if ( ejecutarTareaFinRelacion == null || !ejecutarTareaFinRelacion.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaFinRelacion, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaFinRelacion",   null, TareaFinRelacion.class);									
					
					tiempo = quartz.getString("TareaFinRelacion.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaFinRelacion", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaFinRelacion ");
				}	
				
				/**
				 * Tarea 10:TareaFinRelacion  
				 * 
				 */ 
				if ( ejecutarTareaRespuestaFinRelacion == null || !ejecutarTareaRespuestaFinRelacion.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaRespuestaFinRelacion, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaRespuestaFinRelacion",   null, TareaRespuestaFinRelacion.class);									
					
					tiempo = quartz.getString("TareaRespuestaFinRelacion.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaRespuestaFinRelacion", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaRespuestaFinRelacion ");
				}	
				
				/**
				 * Tarea 11:TareaConsultaAfiliacionesIndependientes  
				 * 
				 */ 
				if ( ejecutarTareaConsultaAfiliacionesIndependientes == null || !ejecutarTareaConsultaAfiliacionesIndependientes.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaConsultaAfiliacionesIndependientes, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaConsultaAfiliacionesIndependientes",   null, TareaConsultaAfiliacionesIndependientes.class);									
					
					tiempo = quartz.getString("TareaConsultaAfiliacionesIndependientes.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaConsultaAfiliacionesIndependientes", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaConsultaAfiliacionesIndependientes ");
				}	
				
				/**
				 * Tarea 12:TareaConsultaAfiliacionesPensionados  
				 * 
				 */ 
				if ( ejecutarTareaConsultaAfiliacionesPensionados == null || !ejecutarTareaConsultaAfiliacionesPensionados.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaConsultaAfiliacionesPensionados, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaConsultaAfiliacionesPensionados",   null, TareaConsultaAfiliacionesPensionados.class);									
					
					tiempo = quartz.getString("TareaConsultaAfiliacionesPensionados.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaConsultaAfiliacionesPensionados", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaConsultaAfiliacionesPensionados ");
				}	
				
				/**
				 * Tarea 13:TareaRespuestaAfiliacionesIndepPens  
				 * 
				 */ 
				if ( ejecutarTareaRespuestaAfiliacionIndepPens == null || !ejecutarTareaRespuestaAfiliacionIndepPens.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaRespuestaAfiliacionesIndepPens, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaRespuestaAfiliacionIndepPens",   null, TareaRespuestaAfiliacionIndepPens.class);									
					
					tiempo = quartz.getString("TareaRespuestaAfiliacionIndepPens.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaRespuestaAfiliacionIndepPens", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaRespuestaAfiliacionIndepPens ");
				}	
				
				/**
				 * Tarea 14:TareaConsultaDesafiliacionIndePens  
				 * 
				 */ 
				if ( ejecutarTareaConsultaDesafiliacionIndePens == null || !ejecutarTareaConsultaDesafiliacionIndePens.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaConsultaDesafiliacionIndePens, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaConsultaDesafiliacionIndePens",   null, TareaConsultaDesafiliacionIndePens.class);									
					
					tiempo = quartz.getString("TareaConsultaDesafiliacionIndePens.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaConsultaDesafiliacionIndePens", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaConsultaDesafiliacionIndePens ");
				}	
				
				/**
				 * Tarea 15:TareaRespuestaDesafiliacionIndePens  
				 * 
				 */ 
				if ( ejecutarTareaRespuestaDesafiliacionIndePens == null || !ejecutarTareaRespuestaDesafiliacionIndePens.trim().equals("S") ){
					logger.debug("............ TareasAutomaticas.init(): NO SE LANZARA LA TareaRespuestaDesafiliacionIndePens, COMPRUEBE EL FICHERO quartz.properties");
				} else {
					JobDetail   jobDetail01 = new JobDetail("TareaRespuestaDesafiliacionIndePens",   null, TareaRespuestaDesafiliacionIndePens.class);									
					
					tiempo = quartz.getString("TareaRespuestaDesafiliacionIndePens.periodo");
					CronTrigger trigger01   = new CronTrigger("TareaRespuestaDesafiliacionIndePens", null, tiempo);
					
					sched.scheduleJob(jobDetail01, trigger01); // Se añade al planificador
					logger.info("............. Tarea Adicionada: TareaRespuestaDesafiliacionIndePens ");
				}	
				
				sched.start();
			}
		} catch (Exception e){
			logger.error(e.getMessage());
		}		
	}
	
	public void service(ServletRequest request, ServletResponse response)throws ServletException, IOException {
		logger.info("No se necesita utilizar este metodo");
	}
	
	public String getServletInfo() {
		return null;
	}
	
}
