package co.confa.adminSAT.configuracion;

import java.util.Date;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import co.confa.adminSAT.modelo.Token;

@ManagedBean(eager = true)
@ApplicationScoped
public class TokenBean {

	private static final Logger log = Logger.getLogger(TokenBean.class);
	private Token token = new Token();

	/**
	 * Se debe colocar tipo cliente consulta o respuesta para asignar el respectivo client_id
	 * @param tipoCliente
	 * @return
	 */
	public String getToken(String clientId) {
		try {
			log.info("llamando tokens");
			System.setProperty("http.proxyHost", "proxyver");
			System.setProperty("http.proxyPort", "8080");
			System.setProperty("https.proxyHost", "proxyver");
			System.setProperty("https.proxyPort", "8080");
			if (!validarToken()) {
				boolean generado = false;
				do {
					try {
						OkHttpClient client = new OkHttpClient();
						RequestBody formBody = null;
						
						
						formBody = new FormEncodingBuilder()
								.add("username", IConstantes.USUARIO)
								.add("password", IConstantes.CLAVE)
								.add("grant_type", IConstantes.GRAN_TYPE)
								.add("client_id", clientId).build();
						

						Request request = new Request.Builder()
								.url(IConstantes.WS_RUTA_TOKEN + "token")
								.post(formBody)
								.addHeader("Content-Type", "application/x-www-form-urlencoded")
								.build();
						Response response = client.newCall(request).execute();
						if (response.isSuccessful()) {
							JSONObject tk = new JSONObject(response.body().string());
							token.setAccesToken(tk.getString("access_token"));
							token.setExpira(tk.getLong("expires_in"));
							token.setTiempoCreacion(new Date().getTime());
							generado = true;
//    				System.out.println(tk.toString());
						}
					} catch (Exception e) {
						log.error("ERROR: TokenBean.getToken()-> " + e.getMessage());
					}
				} while (!generado);
			}
			log.info("llamado Finalizando tokens");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return token.getAccesToken();
	}

	private boolean validarToken() {
		// TODO: validar por cada servicio
		try {
			if (token == null || token.getAccesToken() == null || token.getAccesToken().trim().isEmpty())
				return false;
			else {
				long now = new Date().getTime();
				long segDif = (now - token.getTiempoCreacion()) / 1000;

				if (segDif > token.getExpira()) {
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			log.error("ERROR: TokenBean.validarToken()-> ", e);
			return false;
		}
	}
}
