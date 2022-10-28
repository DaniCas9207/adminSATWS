package co.confa.adminSAT.configuracion;

import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

/**
 * 
 * @author tec_danielc
 *
 */
public class EnvioCorreoAfiliacion extends Thread {

	private static Logger log = Logger.getLogger(EnvioCorreoAfiliacion.class);
	private String mailServer = "";
	private String to = "";
	private String cc = "";
	private String subject = "";
	private String from = "";
	private String message = "";
	private String mailhost = "mail";

	/**
	 * Getter y Setter
	 * 
	 * @return
	 */

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMessaje() {
		return message;
	}

	public void setMessaje(String messaje) {
		this.message = messaje;
	}

	public String getMailhost() {
		return mailhost;
	}

	public void setMailhost(String mailhost) {
		this.mailhost = mailhost;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * Método encargado de generar el mensaje según el tipo de novedad y enviar el correo electrónico a las personas designadas
	 * 
	 * @param numeroTransaccion
	 * @param tipoNovedad
	 * @return
	 */
	public boolean enviarCorreoElectronico(String numeroTransaccion, int tipoNovedad, String fechaSolicitud) {

		boolean ret = false;
		try {

//			Vector<String> tos = new Vector<String>();

			// Quitar para que vuelva enivar a los usuarios
			// consulta.setCorreoElectronico("");
			//to.add("daniel.castrillon@confa.co");

			if (!to.equals("") || !cc.equals("")) {
//				this.to = tos;
				this.from = "\"SAT Web\"<gestionSAT@confamiliares.com.co>";
				this.subject = "Notificación Recepción SAT: Número Transacción: " + numeroTransaccion;
				StringBuilder msg = new StringBuilder();				
				
				msg.append("<link href=\"https://fonts.googleapis.com/css?family=Ubuntu:400,700%7CPoppins:500,400,700\" rel=\"stylesheet\" />");
				msg.append("<table  cellspacing=\"1\" cellpadding=\"0\" border=\"0\" align=\"center\" >");
				msg.append("<tr>");
				msg.append("<td>");
				msg.append("<div style=\" border: 1px solid #94969B; border-radius: 12px;width:100%;\">");
				msg.append("<table role=\"presentation\" cellspacing=\"1\" cellpadding=\"0\" border=\"0\" align=\"center\" width=\"900\" style=\"margin: 5px\" class=\"email-container\">");
				msg.append("<tr>");
				msg.append("<td bgcolor=\"#009ADA\" style=\"padding: 10px 10px 10px; text-align: center\">");
				msg.append("<h1 style=\"margin: 0; font-family: cursive; font-size: 20px; line-height: 27px; color: #ffffff; font-weight: normal;\">");
				msg.append("Has recibido una novedad: ");
				
				if (tipoNovedad == IConstantes.SERVICIO_AFILIACION_PRIMERA_VEZ) {
					msg.append("Solicitud de afiliación primera vez en un departamento. ");
				} else if (tipoNovedad == IConstantes.SERVICIO_AFILIACION_NO_PRIMERA_VEZ) {
					msg.append("Solicitud de afiliación NO primera vez en un departamento.");
				} else if (tipoNovedad == IConstantes.SERVICIO_DESAFILIACION) {
					msg.append("Solicitud de desafiliación a la CCF.");
				} else if (tipoNovedad == IConstantes.SERVICIO_RELACION_LABORAL) {
					msg.append("Inicio de relación laboral.");
				} else if (tipoNovedad == IConstantes.SERVICIO_TERMINACION_RELACION) {
					msg.append("Terminación de relación laboral.");
				} else if (tipoNovedad == IConstantes.SERVICIO_SUSPENSION) {
					msg.append("Suspensión temporal del contrato de trabajo.");
				} else if (tipoNovedad == IConstantes.SERVICIO_LICENCIA) {
					msg.append("Licencias remuneradas y no remuneradas.");
				} else if (tipoNovedad == IConstantes.SERVICIO_MODIFICAR_SALARIO) {
					msg.append("Modificación de salario.");
				} else if (tipoNovedad == IConstantes.SERVICIO_RETIRO_DEFINITIVO) {
					msg.append("Retiro definitivo del empleador al SSF");
				}else if (tipoNovedad == IConstantes.SERVICIO_CONSULTA_AFI_INDEPENDIENTES) {
					msg.append("Solicitud de afiliación independiente. ");
				}else if (tipoNovedad == IConstantes.SERVICIO_CONSULTA_AFI_PENSIONADOS) {
					msg.append("Solicitud de afiliación pensionado.");
				}

				msg.append("</h1>");
				msg.append("</td>");
				msg.append("</tr>");
				msg.append("</table>");
				
				msg.append("<table role=\"presentation\" cellspacing=\"3\" cellpadding=\"0\" border=\"0\" align=\"center\" width=\"900\" style=\"margin: 5px\" class=\"email-container\">");
				msg.append("<tr>");
				msg.append("<td bgcolor=\"#ffffff\" align=\"center\"></td>");
				msg.append("</tr>");
				msg.append("<tr>");
				msg.append("<td bgcolor=\"grey\" style=\"padding: 10px 10px 10px; text-align: justify; width: 30% !important\">");
				msg.append("<h1 style=\"margin: 0; font-family: cursive; font-size: 20px; line-height: 27px; color: #ffffff; font-weight: normal;\">");
				msg.append("<strong> Número de transacción:  </strong>");
				msg.append("</h1>");
				msg.append("</td>");
				msg.append("<td bgcolor=\"#f5f7f8\" style=\"padding: 10px 10px 10px; text-align: justify;width: 70%\">");
				msg.append("<h1 style=\"margin: 0; font-family: cursive; font-size: 20px; line-height: 27px; color: #94969B; font-weight: normal\">");
				msg.append(numeroTransaccion);
				msg.append("</h1>");
				msg.append("</td>");
				msg.append("</tr>");
				msg.append("<tr>");
				msg.append("<td bgcolor=\"grey\" style=\"padding: 10px 10px 10px; text-align: justify; width: 30% !important\">");
				msg.append("<h1 style=\"margin: 0; font-family: cursive; font-size: 20px; line-height: 27px; color: #ffffff; font-weight: normal;\">");
				msg.append("<strong> Fecha de la solicitud:  </strong>");
				msg.append("</h1>");
				msg.append("</td>");
				msg.append("<td bgcolor=\"#f5f7f8\" style=\"padding: 10px 10px 10px; text-align: justify;width: 70%\">");
				msg.append("<h1 style=\"margin: 0; font-family: cursive; font-size: 20px; line-height: 27px; color: #94969B; font-weight: normal\">");
				msg.append(fechaSolicitud);
				msg.append("</h1>");
				msg.append("</td>");
				msg.append("</tr>");
				msg.append("</table>");
				msg.append("</div>");
				msg.append("</td>");
				msg.append("</tr>");
				msg.append("</table>");
				
				this.message = msg.toString();
				String mailer = "msgsend";
				boolean debug = false;

				Properties props = System.getProperties();
				if (mailhost != null) {
					props.put("mail.smtp.host", mailhost);
				}
				Session session = Session.getDefaultInstance(props, null);
				if (debug) {
					session.setDebug(true);
				}
				// construir el mensaje
				Message msgs = new MimeMessage(session);
				if (from != null)
					msgs.setFrom(new InternetAddress(this.from));
				else
					msgs.setFrom();
				msgs.setSubject(subject);
				
				//obtener destinatarios del correo
				StringTokenizer correos = new StringTokenizer(to, ",");
		        while (correos.hasMoreElements())
		        {
		            String mail = (String) correos.nextElement();
		            InternetAddress mailAdress = new InternetAddress(mail);
		            msgs.addRecipient(Message.RecipientType.TO, mailAdress);
		        }
		        
		        //obtener destinatarios con copia del correo
		        correos = new StringTokenizer(cc, ",");
		        while (correos.hasMoreElements())
		        {
		            String mail = (String) correos.nextElement();
		            InternetAddress mailAdress = new InternetAddress(mail);
		            msgs.addRecipient(Message.RecipientType.CC, mailAdress);
		        }

				for (Address string : msgs.getAllRecipients()) {
					log.info(string.toString());
				}

				msgs.setText(message);
				msgs.setHeader("X-Mailer", mailer);
				msgs.setHeader("Content-type", "text/html");
				msgs.setHeader("charset", "iso-8859-1");
				msgs.setSentDate(new Date());
				Multipart multipart = new MimeMultipart(); // 1// Create the attachment part

				/**
				*
				*/
				BodyPart htmlBodyPart = new MimeBodyPart(); // 2
				htmlBodyPart.setContent(message, "text/html"); // 3
				multipart.addBodyPart(htmlBodyPart); // 4
				msgs.setContent(multipart); // 5

				// send the thing off
				Transport.send(msgs);

				ret = true;

			}

		} catch (Exception e) {
			System.out.println("" + e.getMessage());
			e.printStackTrace();
			ret = false;
		}
		return ret;
	}
}
