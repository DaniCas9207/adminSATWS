package co.confa.adminSAT.configuracion;

import java.util.ResourceBundle;

public class IConstantes {
	
	//Datos WS recepción del SAT
	public static String KEY = "";
	public static String USUARIO_REST = "";
	public static String PASSWORD_REST = "";
	public static String SEPARADOR = "";
	public static String POOL_INGRESO = "";
	public static boolean INCLUIR_PROXY = false;
	public static final String APP_MOVIL = "movil";
	
	//Rutas WS consulta al SAT
    public static String WS_RUTA_TOKEN = "";
    public static String WS_RUTA_BASE = "";
    public static boolean CLIENT_ID_PRUEBAS = false;
//    public static String CLIENT_ID_CON = "";
//    public static String CLIENT_ID_RES = "";
    public static String USUARIO = "";
    public static String CLAVE = "";
    public static String GRAN_TYPE = "password";
	
	// CONSTANTE RESPUESTA METODOS CRUD
    public static final String RESPUESTA_CORRECTA = "OK";
	
	//RESPUESTA DE LOS SERVICIOS
	public static final String RESPUESTA_OK= "OK";
	public static final String RESPUESTA_ERROR_FALTAN_DATOS= "ERROR_FALTAN_DATOS";
	public static final String RESPUESTA_ERROR_DATOS_INVALIDOS= "ERROR_DATOS_INVALIDOS";
	public static final String RESPUESTA_ERROR_INSERCION_BD= "ERROR_INSERCION_BASE_DATOS";
	public static final String RESPUESTA_ERROR_SISTEMA= "ERROR_SISTEMA";
	
	//RESPUESTA SERVICIO METODO 1 notificacionSAT
	public static final String SIN_TRAN= "SIN_DATOS_TRANSACCION";
	
	//Estado de las notificaciones
	public static String ESTADO_SIN_PROCESAR = "S";
    public static String ESTADO_CONSULTADA = "C";
    public static String ESTADO_GESTIONADA = "G";
    public static String ESTADO_PROCESADA = "P";
    public static String ESTADO_REENVIADA = "E";
	
    //Empresas
	public final static int SERVICIO_AFILIACION_PRIMERA_VEZ = 1;
	public final static int SERVICIO_AFILIACION_NO_PRIMERA_VEZ = 2;
	public final static int SERVICIO_DESAFILIACION = 3;
	public final static int SERVICIO_RELACION_LABORAL =4;
    public final static int SERVICIO_TERMINACION_RELACION =5;
    public final static int SERVICIO_SUSPENSION = 6;
    public final static int SERVICIO_LICENCIA =7;
    public final static int SERVICIO_MODIFICAR_SALARIO = 8;
    public final static int SERVICIO_RETIRO_DEFINITIVO = 9;
    
    //Servicios de consulta y respuesta para empresas
    public final static int SERVICIO_CONSULTA_AFILIACION = 1;
    public final static int SERVICIO_RESPUESTA_AFILIACION = 2;
    public final static int SERVICIO_CONSULTA_AFILIACION_NO_PRIMERA_VEZ = 3;
    public final static int SERVICIO_RESPUESTA_AFILIACION_NO_PRIMERA_VEZ = 4;
    public final static int SERVICIO_CONSULTA_DESAFILIACION = 5;
    public final static int SERVICIO_RESPUESTA_DESAFILIACION = 6;
    public final static int SERVICIO_CONSULTA_INICIO_RELACION = 7;
    public final static int SERVICIO_RESPUESTA_INICIO_RELACION = 8;
    public final static int SERVICIO_CONSULTA_FIN_RELACION= 9;
    public final static int SERVICIO_RESPUESTA_FIN_RELACION = 10;
    
    //Independientes y Pensionados
  	public final static int SERVICIO_AFILIACION_INDEPENDIENTES = 1;
  	public final static int SERVICIO_AFILIACION_PENSIONADOS = 2;
  	public final static int SERVICIO_DESAFILIACION_INDEP_PENS = 3;
  	public final static int SERVICIO_PERDIDA_CAUSA_GRAVE_PENSIONADOS = 4;
  	public final static int SERVICIO_PERDIDA_CAUSA_GRAVE_INDEP_PENS = 5;
    
    //Servicios de consulta y respuesta para independientes y pensionados
	public final static int SERVICIO_CONSULTA_AFI_INDEPENDIENTES = 11;
	public final static int SERVICIO_CONSULTA_AFI_PENSIONADOS = 12;
	public final static int SERVICIO_RESPUESTA_AFI_INDEPENDIENTES_PENSIONADOS = 13;
	public final static int SERVICIO_CONSULTA_DESAFILIACION_INDEP_PENS = 14;
	public final static int SERVICIO_RESPUESTA_DESAFILIACION_INDEP_PENS = 15;
    
    //tipo token
    //public static String TOKEN_CONSULTA = "CONSULTA";
    //public static String TOKEN_RESPUESTA = "RESPUESTA";
    
    public final static String RESPUESTA_OK_MINISTERIO ="200";
    
    public static String destinatarios;
    public static String destinatariosCC;
    public static boolean PERMITERECIBIRCORREO = true;
	
	static{
		try {
			
			ResourceBundle bundle = null;
			bundle = ResourceBundle.getBundle("sistema");
			USUARIO_REST=bundle.getString("USUARIO_REST");
			PASSWORD_REST=bundle.getString("PASSWORD_REST");
			POOL_INGRESO=bundle.getString("POOL_INGRESO");
			SEPARADOR=bundle.getString("SEPARADOR");
			KEY=bundle.getString("KEY");
			
			//RUTA_VALIDACION_TOKENS=bundle2.getString("RUTA_VALIDACION_TOKENS");
			//INCLUIR_PROXY=Boolean.parseBoolean(bundle2.getString("INCLUIR_PROXY"));

		    WS_RUTA_BASE = bundle.getString("ruta_base");
		    WS_RUTA_TOKEN = bundle.getString("ruta_token");
		    CLIENT_ID_PRUEBAS = Boolean.parseBoolean(bundle.getString("client_id_pruebas"));
//			CLIENT_ID_CON = bundle.getString("client_id_afi_con");
//			CLIENT_ID_RES = bundle.getString("client_id_afi_res");
		    USUARIO= bundle.getString("usuario");
		    CLAVE= bundle.getString("clave");
		    destinatarios= bundle.getString("to");
		    destinatariosCC= bundle.getString("cc");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}