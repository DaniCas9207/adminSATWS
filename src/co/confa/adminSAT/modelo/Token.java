package co.confa.adminSAT.modelo;

public class Token {

	private String accesToken;
	private long expira;
	private long tiempoCreacion;// fecha en que se consulto en milisegundos

	public String getAccesToken() {
		return accesToken;
	}

	public void setAccesToken(String accesToken) {
		this.accesToken = accesToken;
	}

	public long getExpira() {
		return expira;
	}

	public void setExpira(long expira) {
		this.expira = expira;
	}

	public long getTiempoCreacion() {
		return tiempoCreacion;
	}

	public void setTiempoCreacion(long tiempoCreacion) {
		this.tiempoCreacion = tiempoCreacion;
	}
}
