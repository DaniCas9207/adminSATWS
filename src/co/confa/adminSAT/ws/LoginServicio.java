package co.confa.adminSAT.ws;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import co.confa.adminSAT.configuracion.IConstantes;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;

@WebService(name = "LoginServicio", targetNamespace = "")
@Path("/auth")
@Api(value = "Clase realizada para la creación del token a partir de parámetros")
public class LoginServicio {
	
	private static final String base64SecretBytes = Base64.getEncoder().encodeToString(IConstantes.KEY.getBytes());
	//private static String usuarioAut=Base64.getEncoder().encodeToString((IConstantes.USUARIO_REST+IConstantes.SEPARADOR+IConstantes.PASSWORD_REST).getBytes());
	private static String usuarioAut=IConstantes.USUARIO_REST+IConstantes.PASSWORD_REST;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String validar(String parametro) throws UnsupportedEncodingException{
		String parametro1="", parametro2="";
		String resultado="", parametro3="";
		try {
			JSONObject valor= new JSONObject(parametro);
			parametro1=valor.getString("usuario");
			parametro2=valor.getString("password");
		} catch (Exception e) {
			e.printStackTrace();
			JsonObject json = Json.createObjectBuilder()
					.add("mensaje", "Faltan parametros")
					.build();
			return json.toString();
		}
		try {
			JSONObject valor= new JSONObject(parametro);
			parametro3=valor.getString("parametro3");
		} catch (Exception e) {
			parametro3="";
		}
		
		try {
			if ((parametro1+parametro2).equals(usuarioAut)) {
				String t="";
				t= generateTokenParametros(parametro1+parametro2, parametro3);
				JsonObject json = Json.createObjectBuilder()
						.add("token", t)
						.build();
				return json.toString();
			}else{
				parametro=validarUsuarioBd(parametro1, parametro2);
				if(!parametro.equals("")){
					String t="";
					t= generateTokenParametros(parametro1, parametro3);
					JsonObject json = Json.createObjectBuilder()
							.add("token", t)
							.build();
					return json.toString();
				}else{
					JsonObject json = Json.createObjectBuilder()
							.add("mensaje", "Sin autorización")
							.build();
					return json.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonObject json = Json.createObjectBuilder()
					.add("mensaje", "Faltan parametros")
					.build();
			return json.toString();
		}

	}

	/**
	 * Metodo encargado de generar un nuevo token simple
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String generateToken() throws UnsupportedEncodingException {
		String id = UUID.randomUUID().toString().replace("-", "");
		Date now = new Date();
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH, 1);
		Date exp = cal.getTime();
		String token = Jwts.builder()
				.setId(id)
				.setSubject("AppConfa")
				.setIssuer("Confa")
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256, base64SecretBytes.getBytes("UTF-8"))
				.compact();
		return token;
	}

	/**
	 * Metodo encargado de generar un nuevo token relacionandole los parametros ingresados por parametro
	 * @return
	 * @throws Exception 
	 */
	private static String generateTokenParametros(String parametro, String origen) throws Exception {
		String id = UUID.randomUUID().toString().replace("-", "");
		Date now = new Date();
		Calendar cal = Calendar.getInstance(); 
		if(origen.equals("") || origen.equals(IConstantes.APP_MOVIL)) {
			cal.add(Calendar.MONTH, 1);
		}else {
			cal.add(Calendar.MINUTE, 20);
		}
		Date exp = cal.getTime();
		String [] parametros=parametro.split(";");
		String token = Jwts.builder()
				.setId(id)
				.setSubject("SatConfa")
				.setIssuer(parametros[0])
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256, base64SecretBytes.getBytes("UTF-8"))
				.compact();
		return token;
	}

	/**
	 * Metodo encargado de validar que los datos ingresados en la peticion 
	 * pertenezcan a un usuario registrado en la base de datos (Documento y Clave)
	 * @param parametro1
	 * @param parametro2
	 * @return
	 */

	public String validarUsuarioBd(String parametro1, String parametro2){
		String respuesta = "";
		try {
			//respuesta=gestionUsuarioEjecucion.validarAutenticacionDocumentoRest(parametro1, parametro2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respuesta;
	}


	public static void main(String[]args){
		LoginServicio lo= new LoginServicio();
		System.out.println(lo.usuarioAut);
		System.out.println(lo.base64SecretBytes);
		
	}

}