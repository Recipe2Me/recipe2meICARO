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
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones.InformacionExtraida;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeDelUsuario;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.aplicaciones.recursos.sentenceGenerator.ItfUsoSentenceGenerator;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

public class TareaEstudioPreliminar extends TareaSincrona {
	public ItfUsoRepositorioInterfaces repoIntfaces;
	private ItfUsoExtractorSemantico itfUsoExtractorSemantico;
	private ItfUsoComunicacionWeb itfUsComunicacionoWeb;
	private ItfUsoSentenceGenerator itfSentenceGenerator;
	

	public TareaEstudioPreliminar() {
		this.repoIntfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
		// Definimos el recurso extractor semantico
		try {
			itfUsoExtractorSemantico = (ItfUsoExtractorSemantico) this.repoIntfaces
					.obtenerInterfazUso("ExtractorSemantico1");
			itfUsComunicacionoWeb = (ItfUsoComunicacionWeb) this.repoIntfaces
					.obtenerInterfazUso("ComunicacionWeb1");
			itfSentenceGenerator = (ItfUsoSentenceGenerator) this.repoInterfaces.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoSentenceGenerator);
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
			if (session.isCerrar()) {
				msg = itfSentenceGenerator.generateEndUnderstandSentence();
				itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,
						mensaje.getUser());
				itfUsComunicacionoWeb.terminarConversacion(mensaje.getUser());
				getEnvioHechos().eliminarHechoWithoutFireRules(evento);
			} else {
				if (despedida != null) {
					session.setCerrar(true);
					msg = itfSentenceGenerator.generateGoodbyKnownUser(session
							.getUser());
					itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,
							mensaje.getUser());
					itfUsComunicacionoWeb.terminarConversacion(mensaje
							.getUser());
				} else if (saludo != null) {
					msg = itfSentenceGenerator.generateHelloKnownUser(session.getUser());
					itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,
							mensaje.getUser());
					this.generarInformeOK(getIdentTarea(), null,
							getIdentAgente(), "Estudio preliminar");
				} else {
					this.generarInformeOK(getIdentTarea(), null,
							getIdentAgente(), "Estudio preliminar");
				}
				if (session.getIntentos() == 3) {
					getEnvioHechos().eliminarHechoWithoutFireRules(evento);
					Objetivo objetivo = focus.getFoco();
					if (objetivo instanceof ObtenerConocimientoInicial) {
						ObtenerConocimientoInicial conocimiento = (ObtenerConocimientoInicial) objetivo;
						if (conocimiento.getIntentos() == 2) {
							session.setCerrar(true);
							itfUsComunicacionoWeb
									.enviarMensageAlUsuario(
											itfSentenceGenerator.generateEndUnderstandSentence(),
											session.getUser());
						} else {
							itfUsComunicacionoWeb
									.enviarMensageAlUsuario(
											itfSentenceGenerator.generateNotEndUnderstandSentence(),
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
						session.setCerrar(true);
						itfUsComunicacionoWeb
								.enviarMensageAlUsuario(
										itfSentenceGenerator.generateEndUnderstandSentence(),
										session.getUser());
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
