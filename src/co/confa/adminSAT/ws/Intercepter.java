package co.confa.adminSAT.ws;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import co.confa.adminSAT.configuracion.IConstantes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Provider
@PreMatching
public class Intercepter implements ContainerRequestFilter{

	private static String usuarioAut=IConstantes.USUARIO_REST + IConstantes.PASSWORD_REST;
	//private static final String base64SecretBytes = Base64.getEncoder().encodeToString(IConstantes.KEY.getBytes());
	private Logger log = Logger.getLogger(Intercepter.class);
	private static String usuarioGenerico = Base64.getEncoder().encodeToString((IConstantes.USUARIO_REST +":"+IConstantes.PASSWORD_REST).getBytes());

	/**
	 * Este filtro intercepta las peticiones realizadas a los diferentes metodos de este web service
	 * y extrae el token inyectado en el header y revisa que este tenga permisos para acceder
	 * al recurso solicitado, teniendo en cuenta que la validacion se realiza con un 
	 * nuevo llamado realizado al metodo validartoken del proyecto ingresoCOnfa
	 * el cual tambien es rest
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String url = requestContext.getUriInfo().getAbsolutePath().toString();
		System.out.println("Validacion token inicio");

		if(url.split("/rest")[1].equals("/auth")){
			return;
		}else {
			String token = requestContext.getHeaderString("Authorization");
			boolean validacionExtra= false;
			if(token==null){
				JsonObject json = Json.createObjectBuilder()
						.add("mensaje", "Credenciales necesarias")
						.add("estado", "AUTENTICACION_FALLIDA")
						.build();
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
						.entity(json).type(MediaType.APPLICATION_JSON).build());
				return;
			}

			String valor="";
			if (token.split(" ").length>1) {
				valor=token.split(" ")[1];
			}else{
				valor=token;
			}
			
			if(token.split(" ")[0].contains("Basic") || token.split(" ")[0].contains("basic")) {
				if(usuarioGenerico.equals(valor)) {
					return;
				}else {
					JsonObject json = Json.createObjectBuilder()
							.add("mensaje", "Credenciales incorrectas")
							.add("estado", "AUTENTICACION_FALLIDA")
							.build();
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
							.entity(json).type(MediaType.APPLICATION_JSON)
							.build());
					return;
				}
			}else {
				String llave = Base64.getEncoder().encodeToString(IConstantes.KEY.getBytes());
				if(((url.split("/rest")[1]).contains("/notificacionSAT"))) {
					validacionExtra = true;
				}
				else {
					validacionExtra = false;
				}
				try {						
					Claims claims = Jwts.parser()
							.setSigningKey(llave.getBytes("UTF-8"))
							.parseClaimsJws(valor).getBody();
					if (claims.getExpiration().before(new Date())) {				
						JsonObject json = Json.createObjectBuilder()
								.add("mensaje", "Credenciales vencidas")
								.add("estado", "AUTENTICACION_FALLIDA")
								.build();
						requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
								.entity(json).type(MediaType.APPLICATION_JSON)
								.build());
						return;
					}

					if (validacionExtra && !claims.getIssuer().equals(usuarioAut)) {
						JsonObject json = Json.createObjectBuilder()
								.add("mensaje", "Token inválido")
								.add("estado", "AUTENTICACION_FALLIDA")
								.build();
						requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
								.entity(json).type(MediaType.APPLICATION_JSON)
								.build());
						return;
					}
				} catch (ExpiredJwtException e) {
					JsonObject json = Json.createObjectBuilder()
							.add("mensaje", "El Token Expiró")
							.add("estado", "AUTENTICACION_FALLIDA")
							.build();
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
							.entity(json).type(MediaType.APPLICATION_JSON)
							.build());
					return;
				}catch (JwtException e) {
					JsonObject json = Json.createObjectBuilder()
							.add("mensaje", "Token Inválido1")
							.add("estado", "AUTENTICACION_FALLIDA")
							.build();
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
							.entity(json).type(MediaType.APPLICATION_JSON)
							.build());
					return;
				}catch (Exception e) {
					e.printStackTrace();
					JsonObject json = Json.createObjectBuilder()
							.add("mensaje", "Token Inválido2")
							.add("estado", "AUTENTICACION_FALLIDA")
							.build();
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
							.entity(json).type(MediaType.APPLICATION_JSON)
							.build());
					return;
				}
			}
		}
	}


	//	/**
	//	 * Este filtro intercepta las peticiones realizadas a los diferentes metodos de este web service
	//	 * y extrae el token inyectado en el header y revisa que este tenga permisos para acceder
	//	 * al recurso solicitado, teniendo en cuenta que la validacion se realiza con un 
	//	 * nuevo llamado realizado al metodo validartoken del proyecto ingresoCOnfa
	//	 * el cual tambien es rest
	//	 */
	//	@Override
	//	public void filter(ContainerRequestContext requestContext) throws IOException {
	//		String url = requestContext.getUriInfo().getAbsolutePath().toString();
	//		System.out.println("Validacion token inicio");
	//		
	//		String token = requestContext.getHeaderString("Authorization");
	//		String token2 = requestContext.getHeaderString("X-Access-Token");
	//		boolean validacionExtra= false;
	//
	//		if(url.split("/rest")[1].equals("/auth")){
	//			return;
	//		}
	//		
	//		if(token==null){
	//			if (token2==null) {
	//				JsonObject json = Json.createObjectBuilder()
	//						.add("mensaje", "Credenciales necesarias")
	//						.add("estado", "AUTENTICACION_FALLIDA")
	//						.build();
	//				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
	//						.entity(json).type(MediaType.APPLICATION_JSON).build());
	//				return;
	//			}
	//		}
	//		
	//		String valor="";
	//		if (token!=null) {
	//			if (token.split(" ").length>1) {
	//				valor=token.split(" ")[1];
	//			}else{
	//				valor=token;
	//			}
	//		}else{
	//			if (token2.split(" ").length>1) {
	//				valor=token2.split(" ")[1];
	//			}else{
	//				valor=token2;
	//			}
	//		}
	//
	//		String llave ="";
	//		if(((url.split("/rest")[1]).contains("/notificacionSAT"))) {
	//			llave = Base64.getEncoder().encodeToString(IConstantes.KEY.getBytes());
	//			validacionExtra = true;
	//		}
	//		else {
	//			llave = Base64.getEncoder().encodeToString(IConstantes.KEY.getBytes());
	//			validacionExtra = false;
	//		}
	//		
	//		try {						
	//			Claims claims = Jwts.parser()
	//					.setSigningKey(llave.getBytes("UTF-8"))
	//					.parseClaimsJws(valor).getBody();
	//			if (claims.getExpiration().before(new Date())) {				
	//				JsonObject json = Json.createObjectBuilder()
	//						.add("mensaje", "Credenciales vencidas")
	//						.add("estado", "AUTENTICACION_FALLIDA")
	//						.build();
	//				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
	//						.entity(json).type(MediaType.APPLICATION_JSON)
	//						.build());
	//				return;
	//			}
	//
	//			if (validacionExtra && !claims.getIssuer().equals(usuarioAut)) {
	//				JsonObject json = Json.createObjectBuilder()
	//						.add("mensaje", "Token inválido")
	//						.add("estado", "AUTENTICACION_FALLIDA")
	//						.build();
	//				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
	//						.entity(json).type(MediaType.APPLICATION_JSON)
	//						.build());
	//				return;
	//			}
	//		} catch (ExpiredJwtException e) {
	//			JsonObject json = Json.createObjectBuilder()
	//					.add("mensaje", "El Token Expiró")
	//					.add("estado", "AUTENTICACION_FALLIDA")
	//					.build();
	//			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
	//					.entity(json).type(MediaType.APPLICATION_JSON)
	//					.build());
	//			return;
	//		}catch (JwtException e) {
	//			JsonObject json = Json.createObjectBuilder()
	//					.add("mensaje", "Token Inválido1")
	//					.add("estado", "AUTENTICACION_FALLIDA")
	//					.build();
	//			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
	//					.entity(json).type(MediaType.APPLICATION_JSON)
	//					.build());
	//			return;
	//		}catch (Exception e) {
	//			e.printStackTrace();
	//			JsonObject json = Json.createObjectBuilder()
	//					.add("mensaje", "Token Inválido2")
	//					.add("estado", "AUTENTICACION_FALLIDA")
	//					.build();
	//			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
	//					.entity(json).type(MediaType.APPLICATION_JSON)
	//					.build());
	//			return;
	//		}
	//	}


}
