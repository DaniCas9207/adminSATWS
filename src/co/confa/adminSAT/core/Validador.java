package co.confa.adminSAT.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import co.confa.adminSAT.modelo.AfiliacionIndependientes;
import co.confa.adminSAT.modelo.AfiliacionNoPrimeraVez;
import co.confa.adminSAT.modelo.AfiliacionPensionados;
import co.confa.adminSAT.modelo.AfiliacionPrimeraVez;
import co.confa.adminSAT.modelo.DesafiliacionEmpresas;
import co.confa.adminSAT.modelo.DesafiliacionIndependientesPensionados;
import co.confa.adminSAT.modelo.FinRelacionLaboral;
import co.confa.adminSAT.modelo.InicioRelacionLaboral;
import co.confa.adminSAT.modelo.NotificacionSAT;

/**
 * 
 * 
 * @author tec_danielc
 *
 */
public class Validador {

	private static final Logger log = Logger.getLogger(Validador.class);
	private static List<String> docEmpleador = Arrays.asList("NI", "CC", "CE", "CD", "PE", "PT");
	private static List<String> docTrabajador = Arrays.asList("RC", "TI", "CC", "CE", "CD", "PE", "PT");
	private static List<String> docRepLegal = Arrays.asList("CC", "CE", "CD", "PE", "PT");
	private static List<String> respAutoriza = Arrays.asList("SI", "NO");

	public Validador() {
	}

	public List<String> validarNotificacionSAT(NotificacionSAT notificacionSAT) {
		List<String> errores = new ArrayList<String>();
		try {
			if (!ValidadorCampos.validarTransaccion("" + notificacionSAT.getNumeroTransaccion()))
				errores.add("GEC01"); // preguntar que glosa se va a manejar
			if (!ValidadorCampos.validarCodigoNovedad("" + notificacionSAT.getCodigoNovedad()))
				errores.add("GEC02"); // preguntar que glosa se va a manejar
			if (!ValidadorCampos.validarFormatoFecha("" + notificacionSAT.getFechaCreacionNovedad()))
				errores.add("GEC03"); // preguntar que glosa se va a manejar
			if (notificacionSAT.getFechaFinVigencia() != null && !notificacionSAT.getFechaFinVigencia().isEmpty()) {
				if (!ValidadorCampos.validarFormatoFecha("" + notificacionSAT.getFechaFinVigencia()))
					errores.add("GEC04"); // preguntar que glosa se va a manejar
			}
			if (!ValidadorCampos.validarTransaccion("" + notificacionSAT.getEstadoFlujo()))
				errores.add("GEC05"); // preguntar que glosa se va a manejar
			if (notificacionSAT.getUrl() != null && !notificacionSAT.getUrl().isEmpty()) {
				if (!ValidadorCampos.validarUrl("" + notificacionSAT.getUrl()))
					errores.add("GEC06"); // preguntar que glosa se va a manejar
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return errores;
	}

	public List<String> validarAfiliacion(AfiliacionPrimeraVez afiliado) {
		List<String> errores = new ArrayList<String>();
		try {

			if (!ValidadorCampos.validarRadicadoCero("" + afiliado.getNumeroRadicadoSolicitud()))
				errores.add("GE04"); //radicado debe venir con valor igual a cero
			if (!ValidadorCampos.validarTransaccion("" + afiliado.getNumeroTransaccion()))
				errores.add("GE05");
			if (!ValidadorCampos.validarTipoPersona(afiliado.getTipoPersona()))
				errores.add("GE10");
			if (!ValidadorCampos.validarNaturalezaJuridica(afiliado.getTipoPersona(),"" + afiliado.getNaturalezaJuridicaEmpleador()))
				errores.add("GE11"); //no es obligatorio, debe venir con valores de 1 a 5 si el tipo de persona es J, si es N debe venir vacío
			if (!ValidadorCampos.validarTipoDoc(afiliado.getTipoDocumentoEmpleador(), docEmpleador))
				errores.add("GE01");
			if (!ValidadorCampos.validarDocumento(afiliado.getTipoDocumentoEmpleador(),
					afiliado.getNumeroDocumentoEmpleador()))
				errores.add("GE02");
			if (!ValidadorCampos.validarSerial(afiliado.getSerialSAT()))
				errores.add("GE03");
			if (!ValidadorCampos.validarNombreOApellido1(afiliado.getTipoPersona(),
					afiliado.getPrimerNombreEmpleador()))
				errores.add("GE12"); //no es obligatorio
			if (!ValidadorCampos.validarNombreOApellido1(afiliado.getTipoPersona(),
					afiliado.getPrimerApellidoEmpleador()))
				errores.add("GE12"); //no es obligatorio
			if (!ValidadorCampos.validarNombreOApellido2(afiliado.getTipoPersona(),
					afiliado.getSegundoNombreEmpleador()))
				errores.add("GE13"); //no es obligatorio
			if (!ValidadorCampos.validarNombreOApellido2(afiliado.getTipoPersona(),
					afiliado.getSegundoApellidoEmpleador()))
				errores.add("GE13"); //no es obligatorio
			if (!ValidadorCampos.validarFormatoFecha(afiliado.getFechaSolicitud()))
				errores.add("GE14");
			if (!ValidadorCampos.validarPerdidaAfiCausaGrave(afiliado.getPerdidaAfiliacionCausaGrave()))
				errores.add("GE15"); //no es obligatorio
			if (!ValidadorCampos.validarCampoVacio(afiliado.getFechaEfectivaAfiliacion()))
				errores.add("GE16"); //no es obligatorio
			if (!ValidadorCampos.validarRazonSocial(afiliado.getTipoPersona(), afiliado.getRazonSocial()))
				errores.add("GE17"); //no es obligatorio
			if (!ValidadorCampos.validarNumMatriculaMercantil(afiliado.getTipoPersona(), afiliado.getNumeroMatriculaMercantil()))
				errores.add("GE18"); //no es obligatorio, aplica si el tipo de persona es J
			if (!ValidadorCampos.validarCadenaLongitud(afiliado.getDepartamento(), 2))
				errores.add("GE19");
			if (!ValidadorCampos.validarCadenaLongitud(afiliado.getMunicipio(), 3))
				errores.add("GE20");
			if (!ValidadorCampos.validarCadenaLimite(afiliado.getDireccionContacto(), 200))
				errores.add("GE21");
			if (!ValidadorCampos.validarNumericoLongitud(afiliado.getNumeroTelefono(), 10))
				errores.add("GE22");
			if (!ValidadorCampos.validateEmail(afiliado.getCorreoElectronico()))
				errores.add("GE23");

			if (afiliado.getTipoDocumentoEmpleador().equals("NI")) { //los campos en el if o else no son obligatorios
				if (!ValidadorCampos.validarTipoDoc(afiliado.getTipoDocumentoRepresentante(), docRepLegal))
					errores.add("GE24");
				if (!ValidadorCampos.validarDocumento(afiliado.getTipoDocumentoRepresentante(),
						afiliado.getNumeroDocumentoRepresentante()))
					errores.add("GE02");

				if (!ValidadorCampos.validarNombreOApellido1("N", afiliado.getPrimerNombreRepresentante()))
					errores.add("GE26");
				if (!ValidadorCampos.validarNombreOApellido1("N", afiliado.getPrimerApellidoRepresentante()))
					errores.add("GE26");
				if (!ValidadorCampos.validarNombreOApellido2("N", afiliado.getSegundoNombreRepresentante()))
					errores.add("GE27");
				if (!ValidadorCampos.validarNombreOApellido2("N", afiliado.getSegundoApellidoRepresentante()))
					errores.add("GE27");
			} else {
				if (!ValidadorCampos.validarCampoVacio(afiliado.getTipoDocumentoRepresentante()))
					errores.add("GE24");
				if (!ValidadorCampos.validarCampoVacio(afiliado.getNumeroDocumentoRepresentante()))
					errores.add("GE02");
				if (!ValidadorCampos.validarCampoVacio(afiliado.getPrimerNombreRepresentante()))
					errores.add("GE26");
				if (!ValidadorCampos.validarCampoVacio(afiliado.getPrimerApellidoRepresentante()))
					errores.add("GE26");
				if (!ValidadorCampos.validarCampoVacio(afiliado.getSegundoNombreRepresentante()))
					errores.add("GE27");
				if (!ValidadorCampos.validarCampoVacio(afiliado.getSegundoApellidoRepresentante()))
					errores.add("GE27");
			}
			
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionManejoDatos(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionNotificaciones(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarManifestacion(afiliado.getManifestacion()))
				errores.add("GE29");
		} catch (Exception e) {
			log.error("ERROR: Validador.validar()-> ", e);
		}

		return errores;
	}

	public List<String> validarAfiliacionNoPrimeraVez(AfiliacionNoPrimeraVez afiliado) {
		List<String> errores = new ArrayList<String>();
		try {

			if (!ValidadorCampos.validarRadicadoCero("" + afiliado.getNumeroRadicadoSolicitud()))
				errores.add("");// preguntar que glosa manejar
			if (!ValidadorCampos.validarTransaccion("" + afiliado.getNumeroTransaccion()))
				errores.add("GE05");
			if (!ValidadorCampos.validarTipoPersona(afiliado.getTipoPersona()))
				errores.add("GE10");
			if (!ValidadorCampos.validarNaturalezaJuridica(afiliado.getTipoPersona(),
					"" + afiliado.getNaturalezaJuridicaEmpleador()))
				errores.add("GE11"); // no es obligatorio, en el listado de glosa dice que debe venir vacio (no
										// corresponde)
			if (!ValidadorCampos.validarTipoDoc(afiliado.getTipoDocumentoEmpleador(), docEmpleador))
				errores.add("GE01");
			if (!ValidadorCampos.validarDocumento(afiliado.getTipoDocumentoEmpleador(),
					afiliado.getNumeroDocumentoEmpleador()))
				errores.add("GE02");
			if (!ValidadorCampos.validarSerial(afiliado.getSerialSAT()))
				errores.add("GE03");
			if (!ValidadorCampos.validarNombreOApellido1(afiliado.getTipoPersona(),
					afiliado.getPrimerNombreEmpleador()))
				errores.add("GE12"); // no es obligatorio
			if (!ValidadorCampos.validarNombreOApellido1(afiliado.getTipoPersona(),
					afiliado.getPrimerApellidoEmpleador()))
				errores.add("GE12"); // no es obligatorio
			if (!ValidadorCampos.validarNombreOApellido2(afiliado.getTipoPersona(),
					afiliado.getSegundoNombreEmpleador()))
				errores.add("GE13"); // no es obligatorio
			if (!ValidadorCampos.validarNombreOApellido2(afiliado.getTipoPersona(),
					afiliado.getSegundoApellidoEmpleador()))
				errores.add("GE13"); // no es obligatorio
			if (!ValidadorCampos.validarFormatoFecha(afiliado.getFechaSolicitud()))
				errores.add("GE14");
			if (!ValidadorCampos.validarPerdidaAfiCausaGrave(afiliado.getPerdidaAfiliacionCausaGrave()))// preguntar si
																										// puede venir
																										// vacio
				errores.add("GE15"); // no es obligatorio
			if (!ValidadorCampos.validarCampoVacio(afiliado.getFechaEfectivaAfiliacion()))
				errores.add("GE16"); // no es obligatorio
			if (!ValidadorCampos.validarRazonSocial(afiliado.getTipoPersona(), afiliado.getRazonSocial()))
				errores.add("GE17"); // no es obligatorio
			if (!ValidadorCampos.validarNumMatriculaMercantil(afiliado.getTipoPersona(),
					afiliado.getNumeroMatriculaMercantil()))
				errores.add(""); // no es obligatorio, qué glosa asignar?
			if (!ValidadorCampos.validarCadenaLongitud(afiliado.getDepartamento(), 2))
				errores.add("GE19");
			if (!ValidadorCampos.validarCadenaLongitud(afiliado.getMunicipio(), 3))
				errores.add("GE20");
			if (!ValidadorCampos.validarCadenaLimite(afiliado.getDireccionContacto(), 200))
				errores.add("GE21");
			if (!ValidadorCampos.validarNumericoLongitud(afiliado.getNumeroTelefono(), 10))
				errores.add("GE22");
			if (!ValidadorCampos.validateEmail(afiliado.getCorreoElectronico()))
				errores.add("GE23");

			if (afiliado.getTipoDocumentoEmpleador().equals("NI")) { // los campos en el if o else no son obligatorios
				if (!ValidadorCampos.validarTipoDoc(afiliado.getTipoDocumentoRepresentante(), docRepLegal))
					errores.add("GE24");
				if (!ValidadorCampos.validarDocumento(afiliado.getTipoDocumentoRepresentante(),
						afiliado.getNumeroDocumentoRepresentante()))
					errores.add("GE02");

				if (!ValidadorCampos.validarNombreOApellido1("N", afiliado.getPrimerNombreRepresentante()))
					errores.add("GE26");
				if (!ValidadorCampos.validarNombreOApellido1("N", afiliado.getPrimerApellidoRepresentante()))
					errores.add("GE26");
				if (!ValidadorCampos.validarNombreOApellido2("N", afiliado.getSegundoNombreRepresentante()))
					errores.add("GE27");
				if (!ValidadorCampos.validarNombreOApellido2("N", afiliado.getSegundoApellidoRepresentante()))
					errores.add("GE27");
			} else {
				if (!ValidadorCampos.validarCampoVacio(afiliado.getTipoDocumentoRepresentante()))
					errores.add("GE24");
				if (!ValidadorCampos.validarCampoVacio(afiliado.getNumeroDocumentoRepresentante()))
					errores.add("GE02");
				if (!ValidadorCampos.validarCampoVacio(afiliado.getPrimerNombreRepresentante()))
					errores.add("GE26");
				if (!ValidadorCampos.validarCampoVacio(afiliado.getPrimerApellidoRepresentante()))
					errores.add("GE26");
				if (!ValidadorCampos.validarCampoVacio(afiliado.getSegundoNombreRepresentante()))
					errores.add("GE27");
				if (!ValidadorCampos.validarCampoVacio(afiliado.getSegundoApellidoRepresentante()))
					errores.add("GE27");
			}

			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionManejoDatos(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionNotificaciones(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarManifestacion(afiliado.getManifestacion()))
				errores.add("GE29");
			if (!ValidadorCampos.validarCadenaLongitud(afiliado.getCodigoCcfAnterior(), 5))
				errores.add("GE30");
			if (!ValidadorCampos.validarFormatoFecha(afiliado.getFechaPazSalvo()))
				errores.add("GE32");
		} catch (Exception e) {
			log.error("ERROR: Validador.validar()-> ", e);
		}

		return errores;
	}

	public List<String> validarDesafiliacion(DesafiliacionEmpresas desafiliado) {
		List<String> errores = new ArrayList<String>();
		try {

			if (!ValidadorCampos.validarRadicadoCero("" + desafiliado.getNumeroRadicadoSolicitud()))
				errores.add("");// preguntar que glosa manejar
			if (!ValidadorCampos.validarTransaccion("" + desafiliado.getNumeroTransaccion()))
				errores.add("GE05");
			if (!ValidadorCampos.validarTipoDoc(desafiliado.getTipoDocumentoEmpleador(), docEmpleador))
				errores.add("GE01");
			if (!ValidadorCampos.validarDocumento(desafiliado.getTipoDocumentoEmpleador(),
					desafiliado.getNumeroDocumentoEmpleador()))
				errores.add("GE02");
			if (!ValidadorCampos.validarSerial(desafiliado.getSerialSAT()))
				errores.add("GE03");
			if (!ValidadorCampos.validarFormatoFecha(desafiliado.getFechaSolicitudDesafiliacion()))
				errores.add("GE14");
			if (!ValidadorCampos.validarCampoVacio(desafiliado.getFechaEfectivaDesafiliacion()))
				errores.add("GE16"); // no es obligatorio
			if (!ValidadorCampos.validarCadenaLongitud(desafiliado.getDepartamento(), 2))
				errores.add("GE19");
			if (!ValidadorCampos.validarAutorizacion(desafiliado.getAutorizacionManejoDatos(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarAutorizacion(desafiliado.getAutorizacionNotificaciones(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarCampoVacio(desafiliado.getPazSalvo()))
				errores.add("GE31");
			if (!ValidadorCampos.validarCampoVacio(desafiliado.getFechaPazSalvo()))
				errores.add("GE32");
		} catch (Exception e) {
			log.error("ERROR: Validador.validar()-> ", e);
		}

		return errores;
	}

	public List<String> validarInicioRelacionLabora(InicioRelacionLaboral afiliado) {
		List<String> errores = new ArrayList<String>();
		try {

			if (!ValidadorCampos.validarRadicadoCero("" + afiliado.getNumeroRadicadoSolicitud()))
				errores.add("");// preguntar que glosa manejar
			if (!ValidadorCampos.validarTransaccion("" + afiliado.getNumeroTransaccion()))
				errores.add("GE05");
			if (!ValidadorCampos.validarTipoDoc(afiliado.getTipoDocumentoEmpleador(), docEmpleador))
				errores.add("GE01");
			if (!ValidadorCampos.validarDocumento(afiliado.getTipoDocumentoEmpleador(),
					afiliado.getNumeroDocumentoEmpleador()))
				errores.add("GE02");
			if (!ValidadorCampos.validarSerial(afiliado.getSerialSAT()))
				errores.add("GE03");
			if (!ValidadorCampos.validarTipoInicio(afiliado.getTipoInicio()))
				errores.add("GE35");
			if (!ValidadorCampos.validarFormatoFecha(afiliado.getFechaInicio()))
				errores.add("GE14");
			if (!ValidadorCampos.validarTipoDoc(afiliado.getTipoDocumentoTrabajador(), docTrabajador))
				errores.add("GE37");
			if (!ValidadorCampos.validarDocumento(afiliado.getTipoDocumentoTrabajador(),
					afiliado.getNumeroDocumentoTrabajador()))
				errores.add("GE02");
			if (!ValidadorCampos.validarTamañoNombre(afiliado.getPrimerNombreTrabajador()))
				errores.add("GE26");
			if (!ValidadorCampos.validarTamañoNombre(afiliado.getSegundoNombreTrabajador()))
				errores.add("GE27");
			if (!ValidadorCampos.validarTamañoNombre(afiliado.getPrimerApellidoTrabajador()))
				errores.add("GE26");
			if (!ValidadorCampos.validarTamañoNombre(afiliado.getSegundoApellidoTrabajador()))
				errores.add("GE27");
			if (!ValidadorCampos.validarSexo(afiliado.getSexoTrabajador()))
				errores.add("GE25");
			if (!ValidadorCampos.validarFormatoFecha(afiliado.getFechaNacimientoTrabajador()))
				errores.add("GE38");
			if (!ValidadorCampos.validarCadenaLongitud(afiliado.getDepartamento(), 2))
				errores.add("GE19");
			if (!ValidadorCampos.validarCadenaLongitud(afiliado.getMunicipio(), 3))
				errores.add("GE20");
			if (!ValidadorCampos.validarCadenaLimite(afiliado.getDireccionContacto(), 200))
				errores.add("GE21");
			if (!ValidadorCampos.validarNumericoLongitud(afiliado.getNumeroTelefono(), 10))
				errores.add("GE22");
			if (!ValidadorCampos.validateEmail(afiliado.getCorreoElectronico()))
				errores.add("GE23");
			if (!ValidadorCampos.validarNumericoLongitud(afiliado.getSalario(), 10))
				errores.add("GE39");
			if (!ValidadorCampos.validarTipoSalario(afiliado.getTipoSalario()))
				errores.add("GE40");
			if (!ValidadorCampos.validarNumericoLongitud(afiliado.getHorasTrabajo(), 3))
				errores.add("GE41");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionManejoDatos(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionNotificaciones(), respAutoriza))
				errores.add("GE28");
		} catch (Exception e) {
			log.error("ERROR: Validador.validar()-> ", e);
		}

		return errores;
	}
	
	public List<String> validarFinRelacionLabora(FinRelacionLaboral afiliado) {
		List<String> errores = new ArrayList<String>();
		try {

			if (!ValidadorCampos.validarRadicadoCero("" + afiliado.getNumeroRadicadoSolicitud()))
				errores.add("");// preguntar que glosa manejar
			if (!ValidadorCampos.validarTransaccion("" + afiliado.getNumeroTransaccion()))
				errores.add("GE05");
			if (!ValidadorCampos.validarTipoDoc(afiliado.getTipoDocumentoEmpleador(), docEmpleador))
				errores.add("GE01");
			if (!ValidadorCampos.validarDocumento(afiliado.getTipoDocumentoEmpleador(),
					afiliado.getNumeroDocumentoEmpleador()))
				errores.add("GE02");
			if (!ValidadorCampos.validarSerial(afiliado.getSerialSAT()))
				errores.add("GE03");
			if (!ValidadorCampos.validarTipoInicio(afiliado.getTipoTerminacion()))
				errores.add("GE35");
			if (!ValidadorCampos.validarFormatoFecha(afiliado.getFechaTerminacion()))
				errores.add("GE14");
			if (!ValidadorCampos.validarTipoDoc(afiliado.getTipoDocumentoTrabajador(), docTrabajador))
				errores.add("GE37");
			if (!ValidadorCampos.validarDocumento(afiliado.getTipoDocumentoTrabajador(),
					afiliado.getNumeroDocumentoTrabajador()))
				errores.add("GE02");
			if (!ValidadorCampos.validarTamañoNombre(afiliado.getPrimerNombreTrabajador()))
				errores.add("GE26");
			if (!ValidadorCampos.validarTamañoNombre(afiliado.getPrimerApellidoTrabajador()))
				errores.add("GE26");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionManejoDatos(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionNotificaciones(), respAutoriza))
				errores.add("GE28");
		} catch (Exception e) {
			log.error("ERROR: Validador.validar()-> ", e);
		}

		return errores;
	}
	
	public List<String> validarAfiliacionIndependiente(AfiliacionIndependientes afiliado) {
		List<String> errores = new ArrayList<String>();
		try {

			if (!ValidadorCampos.validarRadicadoCero("" + afiliado.getNumeroRadicadoSolicitud()))
				errores.add("GE04"); //radicado debe venir con valor igual a cero
			if (!ValidadorCampos.validarTransaccion("" + afiliado.getNumeroTransaccion()))
				errores.add("GE05");
			if (!ValidadorCampos.validarTipoSalario(afiliado.getTipoAfiliado()))
				errores.add("GE10");
			if (!ValidadorCampos.validarTipoDoc(afiliado.getTipoDocumentoSolicitante(), docTrabajador))
				errores.add("GE01");
			if (!ValidadorCampos.validarDocumento(afiliado.getTipoDocumentoSolicitante(),
					afiliado.getNumeroDocumentoSolicitante()))
				errores.add("GE02");
			if (!ValidadorCampos.validarTamañoNombre(afiliado.getPrimerNombreSolicitante()))
				errores.add("GE12"); // no es obligatorio
			if (!ValidadorCampos.validarTamañoNombre(afiliado.getPrimerApellidoSolicitante()))
				errores.add("GE12"); // no es obligatorio
			if (!ValidadorCampos.validarTamañoNombre(afiliado.getSegundoNombreSolicitante()))
				errores.add("GE13"); // no es obligatorio
			if (!ValidadorCampos.validarTamañoNombre(afiliado.getSegundoApellidoSolicitante()))
				errores.add("GE13"); // no es obligatorio
			if (!ValidadorCampos.validarFormatoFecha(afiliado.getFechaSolicitud()))
				errores.add("GE14");
			if (!ValidadorCampos.validarPerdidaAfiCausaGrave(afiliado.getPerdidaAfiliacionCausaGrave()))
				errores.add("GE15"); //no es obligatorio
			if (!ValidadorCampos.validarCampoVacio(afiliado.getFechaEfectivaAfiliacion()))
				errores.add("GE16"); // no es obligatorio
			if (afiliado.getTipoAfiliado().equals("1") || afiliado.getTipoAfiliado().equals("2")) {
				if (!ValidadorCampos.validarCadenaLongitud(afiliado.getDepartamento(), 2))
					errores.add("GE19");
				if (!ValidadorCampos.validarCadenaLongitud(afiliado.getMunicipio(), 3))
					errores.add("GE20");
			} else {
				if (!ValidadorCampos.validarCadenaLongitud(afiliado.getPais(), 4))
					errores.add(""); // no tiene glosa aun
				if (!ValidadorCampos.validarCadenaLongitud(afiliado.getCiudad(), 20))
					errores.add(""); // no tiene glosa aun
			}
			if (!ValidadorCampos.validarCadenaLimite(afiliado.getDireccionDomicilio(), 200))
				errores.add("GE21");
			if (!ValidadorCampos.validarEstadoCivil(afiliado.getEstadoCivil()))
				errores.add(""); //no tiene glosa aun
			if (!ValidadorCampos.validarNumericoLongitud(afiliado.getNumeroTelefono(), 10))
				errores.add("GE22");
			if (!ValidadorCampos.validateEmail(afiliado.getCorreoElectronico()))
				errores.add("GE23");
			if (!ValidadorCampos.validarCadenaLimite(afiliado.getValorMensualIngresos(),9))
				errores.add(""); //no tiene glosa
			if (!ValidadorCampos.validarEstadoReporte(afiliado.getDeclaracionFuenteIngresos()))
				errores.add(""); //no tiene glosa
			if (!ValidadorCampos.validarCadenaLimite(afiliado.getDeclaracionVeracidadInformacion(),2))
				errores.add(""); //no tiene glosa
			if (!ValidadorCampos.validarCampoVacio(afiliado.getCodigoCCF()))
				errores.add("GE30"); // no es obligatorio
			if (!ValidadorCampos.validarCampoVacio(afiliado.getPazSalvo()))
				errores.add("GE31"); // no es obligatorio
			if (!ValidadorCampos.validarCampoVacio(afiliado.getFechaPazSalvo()))
				errores.add("GE32"); // no es obligatorio
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionManejoDatos(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionNotificaciones(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionManejoDatosCCF(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionNotificacionesCCF(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarManifestacion(afiliado.getManifestacion()))
				errores.add("GE29");
		} catch (Exception e) {
			log.error("ERROR: Validador.validarAfiliacionIndependiente()-> ", e);
		}

		return errores;
	}
	
	
	public List<String> validarAfiliacionPensionado(AfiliacionPensionados afiliado) {
		List<String> errores = new ArrayList<String>();
		try {

			if (!ValidadorCampos.validarRadicadoCero("" + afiliado.getNumeroRadicadoSolicitud()))
				errores.add("GE04"); //radicado debe venir con valor igual a cero
			if (!ValidadorCampos.validarTransaccion("" + afiliado.getNumeroTransaccion()))
				errores.add("GE05");
			if (!ValidadorCampos.validarTipoAfiliadoPensionado(afiliado.getTipoAfiliado()))
				errores.add("GE10");
			if (!ValidadorCampos.validarTipoDoc(afiliado.getTipoDocumentoSolicitante(), docTrabajador))
				errores.add("GE01");
			if (!ValidadorCampos.validarDocumento(afiliado.getTipoDocumentoSolicitante(),
					afiliado.getNumeroDocumentoSolicitante()))
				errores.add("GE02");
			if (!ValidadorCampos.validarTamañoNombre(afiliado.getPrimerNombreSolicitante()))
				errores.add("GE12"); // no es obligatorio
			if (!ValidadorCampos.validarTamañoNombre(afiliado.getPrimerApellidoSolicitante()))
				errores.add("GE12"); // no es obligatorio
			if (!ValidadorCampos.validarTamañoNombre(afiliado.getSegundoNombreSolicitante()))
				errores.add("GE13"); // no es obligatorio
			if (!ValidadorCampos.validarTamañoNombre(afiliado.getSegundoApellidoSolicitante()))
				errores.add("GE13"); // no es obligatorio
			if (!ValidadorCampos.validarFormatoFecha(afiliado.getFechaSolicitud()))
				errores.add("GE14");
			if (!ValidadorCampos.validarPerdidaAfiCausaGrave(afiliado.getPerdidaAfiliacionCausaGrave()))
				errores.add("GE15"); // no es obligatorio
			if (!ValidadorCampos.validarCampoVacio(afiliado.getFechaEfectivaAfiliacion()))
				errores.add("GE16"); // no es obligatorio

			if (!ValidadorCampos.validarCadenaLongitud(afiliado.getDepartamento(), 2))
				errores.add("GE19");
			if (!ValidadorCampos.validarCadenaLongitud(afiliado.getMunicipio(), 3))
				errores.add("GE20");

			if (!ValidadorCampos.validarCadenaLimite(afiliado.getDireccionDomicilio(), 200))
				errores.add("GE21");
			if (!ValidadorCampos.validarNumericoLongitud(afiliado.getNumeroTelefono(), 10))
				errores.add("GE22");
			if (!ValidadorCampos.validateEmail(afiliado.getCorreoElectronico()))
				errores.add("GE23");
			if (!ValidadorCampos.validarCadenaLimite(afiliado.getValorMensualIngresos(), 9))
				errores.add(""); // no tiene glosa
			if (!ValidadorCampos.validarEstadoReporte(afiliado.getDeclaracionFuenteIngresos()))
				errores.add(""); //no tiene glosa
			if (!ValidadorCampos.validarCadenaLimite(afiliado.getDeclaracionVeracidadInformacion(),2))
				errores.add(""); //no tiene glosa
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionManejoDatos(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionNotificaciones(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionManejoDatosCCF(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionNotificacionesCCF(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarManifestacion(afiliado.getManifestacion()))
				errores.add("GE29");
			if (!ValidadorCampos.validarCampoVacio(afiliado.getCodigoCCF()))
				errores.add("GE30"); // no es obligatorio
			if (!ValidadorCampos.validarCampoVacio(afiliado.getPazSalvo()))
				errores.add("GE31"); // no es obligatorio
			if (!ValidadorCampos.validarCampoVacio(afiliado.getFechaPazSalvo()))
				errores.add("GE32"); // no es obligatorio
		} catch (Exception e) {
			log.error("ERROR: Validador.validarAfiliacionPensionado()-> ", e);
		}

		return errores;
	}
	
	public List<String> validarDesafiliacionIndPen(DesafiliacionIndependientesPensionados afiliado) {
		List<String> errores = new ArrayList<String>();
		try {

			if (!ValidadorCampos.validarRadicadoCero("" + afiliado.getNumeroRadicadoSolicitud()))
				errores.add("GE04"); //radicado debe venir con valor igual a cero
			if (!ValidadorCampos.validarTransaccion("" + afiliado.getNumeroTransaccion()))
				errores.add("GE05");
			if (!ValidadorCampos.validarTipoDoc(afiliado.getTipoDocumentoSolicitante(), docTrabajador))
				errores.add("GE01");
			if (!ValidadorCampos.validarDocumento(afiliado.getTipoDocumentoSolicitante(),
					afiliado.getNumeroDocumentoSolicitante()))
				errores.add("GE02");
			if (!ValidadorCampos.validarTipoAfiliadoIndepPensio(afiliado.getTipoAfiliado()))
				errores.add("");
			if (!ValidadorCampos.validarFormatoFecha(afiliado.getFechaSolicitudDesafiliacion()))
				errores.add("GE14");
			if (!ValidadorCampos.validarCampoVacio(afiliado.getFechaEfectivaDesafiliacion()))
				errores.add("GE16"); // no es obligatorio
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionManejoDatosMinisterio(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionNotificacionesMinisterio(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionManejoDatosCCF(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarAutorizacion(afiliado.getAutorizacionNotificacionesCCF(), respAutoriza))
				errores.add("GE28");
			if (!ValidadorCampos.validarCampoVacio(afiliado.getPazSalvo()))
				errores.add("GE31"); // no es obligatorio
			if (!ValidadorCampos.validarCampoVacio(afiliado.getFechaPazSalvo()))
				errores.add("GE32"); // no es obligatorio
		} catch (Exception e) {
			log.error("ERROR: Validador.validarDesafiliacionIndPen()-> ", e);
		}

		return errores;
	}

}
