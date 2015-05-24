package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

import java.util.List;
import java.util.Map;

import org.eclipse.jetty.server.session.JDBCSessionManager.Session;

import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerAlergia;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerConocimientoInicial;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerIngredienteOPlato;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerIngredientesFavoritos;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerIngredientesOdiados;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerNivelCocina;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerValoracionUsuario;
import icaro.aplicaciones.informacion.dominioRecipe2Me.DialogoInicial;
import icaro.aplicaciones.informacion.dominioRecipe2Me.Message;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserSession;
import icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones.InformacionExtraida;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeDelUsuario;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.aplicaciones.recursos.sentenceGenerator.SentenceFactory;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

public class TareaEstudioPreliminar extends TareaSincrona {
	public ItfUsoRepositorioInterfaces repoIntfaces;
	private ItfUsoExtractorSemantico itfUsoExtractorSemantico;
	private ItfUsoComunicacionWeb itfUsComunicacionoWeb;

	public TareaEstudioPreliminar() {
		this.repoIntfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
		// Definimos el recurso extractor semantico
		try {
			itfUsoExtractorSemantico = (ItfUsoExtractorSemantico) this.repoIntfaces
					.obtenerInterfazUso("ExtractorSemantico1");
			itfUsComunicacionoWeb = (ItfUsoComunicacionWeb) this.repoIntfaces
					.obtenerInterfazUso("ComunicacionWeb1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void ejecutar(Object... params) {
		// TODO Auto-generated method stub
		try {
			EventoMensajeDelUsuario mensaje = (EventoMensajeDelUsuario) params[0];
			UserSession session = (UserSession) params[1];
			Focus focus = (Focus) params[2];
			EventoMensajeDelUsuario evento = (EventoMensajeDelUsuario) params[3];
			session.getMessages().add(new Message(mensaje.getMensaje()));
			session.setIntentos(session.getIntentos() + 1);
			String contenido = mensaje.getMensaje();
			InformacionExtraida informacionExtraida;
			informacionExtraida = itfUsoExtractorSemantico
					.extraerAnotaciones(contenido);
			Map<String, List<String>> anotaciones = informacionExtraida
					.getInformacionPorAnotacion();
			List<String> despedida = anotaciones.get("Despedida");
			List<String> saludo = anotaciones.get("Saludo");
			String msg = "";
			if (despedida != null) {
				msg = SentenceFactory
						.generateGoodbyKnownUser(session.getUser());
				itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,
						mensaje.getUser());
				itfUsComunicacionoWeb.terminarConversacion(mensaje.getUser());
				// this.generarInformeConCausaTerminacion(getIdentTarea(),null,"Despedida",null,null);
			} else if (saludo != null) {
				msg = "¡¡¡Hola, chaval, encantado de saludarte, pero !!!";
				itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,
						mensaje.getUser());
				this.generarInformeOK(getIdentTarea(), null, getIdentAgente(),
						"Estudio preliminar");
			} else {
				this.generarInformeOK(getIdentTarea(), null, getIdentAgente(),
						"Estudio preliminar");
			}
			if (session.getIntentos() == 2) {
				getEnvioHechos().eliminarHechoWithoutFireRules(evento);
				Objetivo objetivo = focus.getFoco();
				if (objetivo instanceof ObtenerConocimientoInicial) {
					ObtenerConocimientoInicial conocimiento = (ObtenerConocimientoInicial) objetivo;
					if (conocimiento.getIntentos() == 2) {
						itfUsComunicacionoWeb
								.enviarMensageAlUsuario(
										"Bueno parece que no consigo entenderte, pasate en otro momento, adiosss.",
										session.getUser());
					} else {
						itfUsComunicacionoWeb
								.enviarMensageAlUsuario(
										"Bueno no conseguimos entendernos en este paso, vamos a probar con otro.",
										session.getUser());
						session.setIntentos(0);
						conocimiento
								.setIntentos(conocimiento.getIntentos() + 1);
						conocimiento.getSubObjetivoAleatorio();
						getEnvioHechos().actualizarHechoWithoutFireRules(
								session);
						getEnvioHechos().actualizarHecho(conocimiento);
					}
				} else if (objetivo instanceof ObtenerIngredienteOPlato) {
					itfUsComunicacionoWeb
							.enviarMensageAlUsuario(
									"Bueno parece que no consigo entenderte, solo queria saber algun ingrediente que quisieras, pasate en otro momento, adiosss.",
									session.getUser());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
