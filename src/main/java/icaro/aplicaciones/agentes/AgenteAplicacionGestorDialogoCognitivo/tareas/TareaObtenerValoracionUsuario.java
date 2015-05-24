package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

import icaro.aplicaciones.informacion.dominioRecipe2Me.DialogoInicial;
import icaro.aplicaciones.informacion.dominioRecipe2Me.QueryRecipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.Recipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserSession;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones.InformacionExtraida;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.DecisionUsuario;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.Decisiones;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeDelUsuario;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.ValoracionUsuario;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.aplicaciones.recursos.persistenciaMongo.ItfUsoPersistenciaMongo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaAsincrona;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TareaObtenerValoracionUsuario extends TareaAsincrona {
	public ItfUsoRepositorioInterfaces repoIntfaces;
	private ItfUsoExtractorSemantico itfUsoExtractorSemantico;
	private ItfUsoComunicacionWeb itfUsComunicacionoWeb;
	private ItfUsoPersistenciaMongo itfUsoPersistenciaMongo;

	public TareaObtenerValoracionUsuario() {
		this.repoIntfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
		// Definimos el recurso extractor semantico
		try {
			itfUsoExtractorSemantico = (ItfUsoExtractorSemantico) this.repoIntfaces
					.obtenerInterfazUso("ExtractorSemantico1");
			itfUsComunicacionoWeb = (ItfUsoComunicacionWeb) this.repoIntfaces
					.obtenerInterfazUso("ComunicacionWeb1");
			itfUsoPersistenciaMongo = (ItfUsoPersistenciaMongo) this.repoInterfaces
					.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoPersistenciaMongo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ejecutar(Object... params) {
		UserProfile user = (UserProfile) params[0];
		ValoracionUsuario valoracion = null;
		EventoMensajeDelUsuario mensaje = null;
		try {
			if (params[1] instanceof EventoMensajeDelUsuario) {
				mensaje = (EventoMensajeDelUsuario) params[1];
				boolean valorar = false;
				Recipe recipe = itfUsoPersistenciaMongo.findOne(user
						.getRecetaPendiente().toString());
				InformacionExtraida informacionExtraida;
				informacionExtraida = itfUsoExtractorSemantico
						.extraerAnotaciones(mensaje.getMensaje());
				Map<String, List<String>> anotaciones = informacionExtraida
						.getInformacionPorAnotacion();
				List<String> negativo = anotaciones.get("Negacion");
				List<String> afirmativo = anotaciones.get("Afirmacion");
				String msg = "";
				if (negativo != null) {
					if (negativo.isEmpty()) {
						if (afirmativo != null) {
							itfUsComunicacionoWeb.enviarRecetaAlUsuario(recipe,
									user.getUsername(),true);
						} else {
							msg = "no me has contestado a la pregunta!!, ¿quieres valorar la receta?";
							itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,
									mensaje.getUser());
						}
					} else {
						msg = "De acuerdo, la proxima vez quizas.";
						itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,
								mensaje.getUser());
						this.generarInformeOK(getIdentTarea(), null,
								getIdentAgente(),
								"Zanjar_ObtenerValoracionUsuario");
					}
				} else {
					if (afirmativo != null) {
						itfUsComunicacionoWeb.enviarRecetaAlUsuario(recipe,
								user.getUsername(),true);
					} else {
						msg = "no me has contestado a la pregunta!!, ¿quieres valorar la receta?";
						itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,
								mensaje.getUser());
					}

				}
			} else {
				valoracion = (ValoracionUsuario) params[1];
				QueryRecipe consulta = (QueryRecipe) params[2];
				Recipe recipe = itfUsoPersistenciaMongo.findOne(user
						.getRecetaPendiente().toString());
				user.updateValoracion(recipe, valoracion.getValoracion());
				getEnvioHechos().eliminarHechoWithoutFireRules(consulta);
				consulta = new QueryRecipe(user);
				getEnvioHechos().actualizarHechoWithoutFireRules(consulta);
				String msg = "Gracias por tu valoracion, la tendremos en cuanta para recomendarte nuevas.";
				itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,
						valoracion.getUser());
				this.generarInformeOK(getIdentTarea(), null,
						getIdentAgente(),
						"Zanjar_ObtenerValoracionUsuario");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
