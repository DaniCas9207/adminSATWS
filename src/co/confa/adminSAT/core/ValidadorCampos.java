package co.confa.adminSAT.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Validador de los campos de estructura y contenido
 * 
 * @author tec_danielc
 *
 */
public class ValidadorCampos {
	private static final Logger log = Logger.getLogger(ValidadorCampos.class);
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static boolean validarTransaccion(String dato) {
		boolean salida = false;

		if (dato != null && !dato.trim().isEmpty()) {
			if (dato.length() <= 50)
				salida = true;
		}

		return salida;
	}

	public static boolean validarCodigoNovedad(String dato) {
		boolean salida = false;

		if (dato != null && !dato.trim().isEmpty()) {
			if (dato.length() <= 2) {
				if (dato.equals("1") || dato.equals("2") || dato.equals("3") || dato.equals("4") || dato.equals("5")
						|| dato.equals("6") || dato.equals("7") || dato.equals("8") || dato.equals("9"))
					salida = true;
			}
		}

		return salida;
	}

	public static boolean validarUrl(String dato) {
		boolean salida = false;

		if (dato != null && !dato.trim().isEmpty()) {
			if (dato.length() <= 255)
				salida = true;
		}

		return salida;
	}

	public static boolean validarRadicadoCero(String dato) {
		boolean salida = false;

		if (dato != null && !dato.trim().isEmpty()) {
			if (dato.equals("0"))
				salida = true;
		}

		return salida;
	}

	public static boolean validarTipoPersona(String dato) {
		boolean salida = false;

		if (dato != null && !dato.trim().isEmpty()) {
			if (dato.length() == 1 && (dato.equals("N") || dato.equals("J")))
				salida = true;
		}

		return salida;
	}

	public static boolean validarNaturalezaJuridica(String tipoPersona, String dato) {
		boolean salida = false;

		switch (tipoPersona) {
		case "J":
			if (dato != null && !dato.trim().isEmpty()) {
				if (dato.length() == 1 && (dato.equals("1") || dato.equals("2") || dato.equals("3") || dato.equals("4")
						|| dato.equals("5")))
					salida = true;
			}
			break;
		case "N":
			if (dato.trim().isEmpty())
				salida = true;
			break;
		default:
			break;
		}

		return salida;
	}

	public static boolean validarTipoDoc(String dato, List<String> listaValidacion) {
		boolean salida = false;

		if (dato != null && !dato.trim().isEmpty()) {
			if (dato.length() == 2 && listaValidacion.contains(dato))
				salida = true;
		}

		return salida;
	}

	private static boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public static boolean validarDocumento(String tipo, String dato) {
		boolean salida = false;

		if (dato != null && !dato.trim().isEmpty()) {
			if (dato.length() <= 16) {
				switch (tipo) {
				case "CC":
					if (dato.length() >= 3 && dato.length() <= 10)
						salida = isNumeric(dato);
					break;
				case "CE":
					if (dato.length() >= 3 && dato.length() <= 7)// preguntar longitud
						salida = true; // preguntar campo alfanumerico o numerico
					break;
				case "CD":
					if (dato.length() >= 3 && dato.length() <= 11)
						salida = true;
					break;
				case "PE":
					if (dato.length() == 15)
						salida = isNumeric(dato);
					break;
				case "NI":
					if (dato.length() <= 9)
						salida = isNumeric(dato);
					break;

				case "PT":
					if (dato.length() == 7)
						salida = isNumeric(dato);
					break;

				case "RC":
					if (dato.length() <= 11)
						salida = true;
					break;

				case "TI":
					if (dato.length() <= 11)
						salida = true;
					break;

				default:
					break;
				}
			}
		}

		return salida;
	}

	public static boolean validarSerial(String dato) {
		boolean salida = false;

		if (dato != null && !dato.trim().isEmpty()) {
			if (dato.length() <= 4)
				salida = true;
		}

		return salida;
	}

	public static boolean validarPerdidaAfiCausaGrave(String dato) {
		boolean salida = false;
		if (dato != null && !dato.trim().isEmpty()) {
			if (dato.length() <= 5)
				salida = true;
		} else {
			salida = true;
		}

		return salida;
	}

	public static boolean validarNombreOApellido1(String tipoPersona, String dato) {
		boolean salida = false;

		switch (tipoPersona) {
		case "N":
			if (dato != null && !dato.trim().isEmpty() && dato.length() <= 60)
				salida = true;
			break;
		case "J":
			if (dato.trim().isEmpty())
				salida = true;
			break;
		default:
			break;
		}

		return salida;
	}

	public static boolean validarRazonSocial(String tipoPersona, String dato) {
		boolean salida = false;

		switch (tipoPersona) {
		case "N":
			if (dato != null && dato.trim().isEmpty())
				salida = true;
			break;
		case "J":
			if (!dato.trim().isEmpty() && dato.length() <= 500)
				salida = true;
			break;
		default:
			break;
		}

		return salida;
	}
	
	public static boolean validarNumMatriculaMercantil(String tipoPersona, String dato) {
		boolean salida = false;

		switch (tipoPersona) {
		case "N":
			if (dato != null && dato.trim().isEmpty())
				salida = true;
			break;
		case "J":
			if (!dato.trim().isEmpty() && dato.length() <= 12)
				salida = true;
			break;
		default:
			break;
		}

		return salida;
	}

	public static boolean validarCausaRetDefinitivo(Long dato) {
		boolean salida = false;

		if (dato == 1 || dato == 2 || dato == 3 || dato == 4 || dato == 5)
			salida = true;

		return salida;
	}

	public static boolean validarEstadoReporte(String dato) {
		boolean salida = false;

		if (dato != null && !dato.trim().isEmpty())
			if (dato.trim().equals("1") || dato.trim().equals("2"))
				salida = true;

		return salida;
	}

	public static boolean validarNombreOApellido2(String tipoPersona, String dato) {
		boolean salida = false;

		switch (tipoPersona) {
		case "N":
			if (dato != null && (dato.length() >= 0 || dato.length() <= 60))
				salida = true;
			break;
		case "J":
			if (dato.trim().isEmpty())
				salida = true;
			break;
		default:
			break;
		}

		return salida;
	}

	public static boolean validarFormatoFecha(String dato) {
		boolean salida = false;
		if (dato.isEmpty()) {
			return salida;
		} else if (dato.length() == 10) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date d = df.parse(dato);
				if (d != null)
					salida = true;
			} catch (Exception e) {
				log.error("ERROR: Validador.validarFormatoFEcha()-> ", e);
				salida = false;
			}
		}

		return salida;
	}
	
	public static boolean validarCampoVacio(String dato) {
		boolean salida = false;
		if (dato.isEmpty()) {
			salida = true;
		} 
		return salida;
	}

	public static boolean validarCadenaLimite(String dato, int limite) {
		boolean salida = false;

		if (dato != null && !dato.trim().isEmpty()) {
			if (dato.length() <= limite)
				salida = true;
		}

		return salida;
	}

	public static boolean validarCadenaLongitud(String dato, int longitud) {
		boolean salida = false;

		if (dato != null && !dato.trim().isEmpty()) {
			if (dato.length() == longitud)
				salida = true;
		}

		return salida;
	}

	public static boolean validarNumericoLongitud(String dato, int longitud) {
		boolean salida = false;
		try {
			Long.parseLong(dato.trim());

			if (dato.length() <= longitud)
				salida = true;
		} catch (NumberFormatException nfe) {
			salida = false;
		}

		return salida;
	}

	public static boolean validateEmail(String email) {
		try {
			if (email.trim().isEmpty())
				return true;
			// Compiles the given regular expression into a pattern.
			Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			// Match the given input against this pattern
			Matcher matcher = pattern.matcher(email);
			return matcher.matches();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean validarAutorizacion(String dato, List<String> listaValidacion) {
		boolean salida = false;

		if (dato != null && !dato.trim().isEmpty()) {
			if (dato.length() == 2 && listaValidacion.contains(dato))
				salida = true;
		}

		return salida;
	}

	public static boolean validarManifestacion(String dato) {
		boolean salida = false;

		if (dato != null && !dato.trim().isEmpty()) {
			if (dato.length() == 2 && dato.equals("SI"))
				salida = true;
		}

		return salida;
	}

	public static boolean validarIndicadorNovedad(String dato) {
		boolean salida = false;

		if (dato != null && !dato.trim().isEmpty()) {
			if (dato.length() == 1 && (dato.equals("R") || dato.equals("I") || dato.equals("P") || dato.equals("C")
					|| dato.equals("X")))
				salida = true;
		}

		return salida;
	}
	
	public static boolean validarTipoInicio(String dato) {
		boolean salida = false;

		if (dato == "1")
			salida = true;

		return salida;
	}
	
	public static boolean validarSexo(String dato) {
		boolean salida = false;

		if (dato == "H" || dato== "M")
			salida = true;

		return salida;
	}
	
	public static boolean validarTamañoNombre(String dato) {
		boolean salida = false;

		if (dato != null && (dato.length() >= 0 || dato.length() <= 60)) {
			salida = true;
		}

		return salida;
	}
	public static boolean validarTipoSalario(String dato) {
		boolean salida = false;
		long datoLong = Long.parseLong(dato.trim());

		if (datoLong == 1 || datoLong == 2 || datoLong == 3)
			salida = true;

		return salida;
	}
	
	public static boolean validarEstadoCivil(String dato) {
		boolean salida = false;
		long datoLong = Long.parseLong(dato.trim());
		if (datoLong == 1 || datoLong == 2 || datoLong == 3 || datoLong == 4 || datoLong == 5 || datoLong==6)
			salida = true;

		return salida;
	}
	
	public static boolean validarTipoAfiliadoPensionado(String dato) {
		boolean salida = false;
		long datoLong = Long.parseLong(dato.trim());
		if (datoLong == 1 || datoLong == 2 || datoLong == 3 || datoLong == 4 || datoLong == 5 || datoLong==6 || datoLong==7)
			salida = true;

		return salida;
	}
	
	public static boolean validarTipoAfiliadoIndepPensio(String dato) {
		boolean salida = false;
		long datoLong = Long.parseLong(dato.trim());

		if (datoLong == 1 || datoLong == 2)
			salida = true;

		return salida;
	}
	
}
