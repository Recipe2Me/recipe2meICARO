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
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.aplicaciones.recursos.persistenciaMongo.ItfUsoPersistenciaMongo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaAsincrona;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TareaObtenerConfirmacionUsuario extends TareaAsincrona {
	public ItfUsoRepositorioInterfaces repoIntfaces;
	private ItfUsoComunicacionWeb itfUsComunicacionoWeb;

	public TareaObtenerConfirmacionUsuario() {
		this.repoIntfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
		// Definimos el recurso extractor semantico
		try {
			itfUsComunicacionoWeb = (ItfUsoComunicacionWeb) this.repoIntfaces
					.obtenerInterfazUso("ComunicacionWeb1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ejecutar(Object... params) {
		QueryRecipe consulta = (QueryRecipe) params[0];
		UserSession session = (UserSession) params[1];
		DecisionUsuario decision = null;
		session.setUpdate(new Date());
		if (params.length > 2)
			decision = (DecisionUsuario) params[2];
		try {
			if (decision != null) {
				if (decision.getDecision().equals(Decisiones.gusta)) {
					session.setRecipeAccept(consulta.getListaRecomendaciones()
							.get(0).getId());
					session.setFinished(true);
					String msg = "Muy bien, has escogido "
							+ consulta.getListaRecomendaciones().get(0)
									.getTitle()
							+ ", la proxima vez que accedas te preguntare tu valoración, así que espero que te guste y ya me cuentas. Adioos.";
					itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,
							session.getUser());
					this.generarInformeOK(getIdentTarea(), null,
							getIdentAgente(),
							"Zanjar_TareaObtenerConfirmacionUsuario");
				} else if (decision.getDecision().equals(Decisiones.no_gusta)) {
					session.getRecipesReject().add(
							consulta.getListaRecomendaciones().remove(0)
									.getId());
					itfUsComunicacionoWeb.enviarRecetaAlUsuario(consulta
							.getListaRecomendaciones().get(0), session
							.getUser(),false);
				} else {
					consulta.getListaRecomendaciones().remove(0);
					if (consulta.getListaRecomendaciones().size() > 0) {
						itfUsComunicacionoWeb.enviarRecetaAlUsuario(consulta
								.getListaRecomendaciones().get(0), session
								.getUser(),false);
					} else {
						session.setNoMasRecetas(true);
						getEnvioHechos().actualizarHecho(session);
					}
				}
			} else {
				itfUsComunicacionoWeb.enviarRecetaAlUsuario(consulta
						.getListaRecomendaciones().get(0), session.getUser(),false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
