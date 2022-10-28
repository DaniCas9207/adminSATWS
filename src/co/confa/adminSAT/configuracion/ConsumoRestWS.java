package co.confa.adminSAT.configuracion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Clase diseñada para servir de puente para el consumo desde cualquier otra
 * clase de servicios web rest - post
 * 
 * @author tec_stivenv
 *
 */
public class ConsumoRestWS {

	/**
	 * Metodo encargado de consumir un servicio web rest - post con envio de tokens
	 * Este metodo siempre retorna un String[] con dos posiciones [0] es el estado
	 * del consumo: true - false [1] es el detalle de la respuesta(Objeto JSON):
	 * {"":""}
	 * 
	 * @param data
	 * @param ruta
	 * @param token
	 * @param proxy        debe o no adicionarle el proxy a la peticion
	 * @param certificados se deben omitir la validacion de certificados
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static String[] comsumoRestPost(String data, String ruta, String token, boolean proxy, boolean certificados, String metodo) {
		if (proxy) {
			activarProxy();
		}
		if (certificados) {
			/** Desabilitacion del SSL **/
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

				}

				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

				}

			} };
			SSLContext sc;
			try {
				sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			} catch (KeyManagementException e1) {
				e1.printStackTrace();
			}
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		}

		/** Finalizacion del codigo para la deshabilitacion del SSL */
		
		String[] response = new String[2];
		try {
			String urlParameters = data;
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			String request = ruta;
			URL url = new URL(request.trim());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod(metodo);
			if (!token.equals("")) {
				conn.setRequestProperty("Authorization", "Bearer " + token);
			}

			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("charset", "utf-8");
			conn.setUseCaches(false);
			try {
				OutputStream wr = conn.getOutputStream();
				wr.write(postData);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int c; (c = in.read()) >= 0;)
				sb.append((char) c);
			response[0] = "true";
			response[1] = sb.toString();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error consumiendo al Servicio");
			response[0] = "false";
			response[1] = "{\"mensaje\":\"04--Error validando el token--Sin permisos\"}";
		}
		return response;
	}

	/**
	 * Activar el proxy para la salida a internet de la peticiones
	 */
	public static void activarProxy() {
		System.setProperty("http.proxyHost", "proxyver");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("https.proxyHost", "proxyver");
		System.setProperty("https.proxyPort", "8080");
	}

	/**
	 * Metodo encarado de generar el json que contiene las credenciales necesarias
	 * para la creacion de los tokens, esto dependiendo de los datos que le
	 * ingresan, credeciales genericas o credenciales especificas
	 * 
	 * @param usuario
	 * @param password
	 * @return
	 */
	public static String generarUsuarioToken(String usuario, String password) {
		String valor = "";
		if (!usuario.equals("") && !password.equals("")) {
			valor = "{\"parametro1\":\"" + usuario + "\",\"parametro2\":\"" + password + "\"}";
		}
		return valor;
	}
}
