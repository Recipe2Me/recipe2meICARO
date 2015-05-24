package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;

import org.bson.types.ObjectId;

import icaro.aplicaciones.informacion.dominioRecipe2Me.Recipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserSession;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.recursos.persistenciaMongo.ItfUsoPersistenciaMongo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.AdaptadorRegRMI;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;

public class PeticionTerminacion extends TareaSincrona {
	// private String identDeEstaTarea= "PermitirAcceso";
	private String identAgenteOrdenante;
	private Objetivo contextoEjecucionTarea = null;

	// private String identRecursoVisualizacionAcceso = "VisualizacionAcceso1";
	@Override
	public void ejecutar(Object... params) {
		UserProfile user = (UserProfile) params[0];
		UserSession session = (UserSession) params[1];
		ItfUsoPersistenciaMongo mongo = null;
		try {
			mongo = (ItfUsoPersistenciaMongo) this.repoInterfaces
					.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoPersistenciaMongo);
			if (session.getRecipeAccept() != null) {
				Recipe recipe = mongo.findOne(session.getRecipeAccept()
						.toString());
				user.setPendiente(true);
				user.setRecetaPendiente(session.getRecipeAccept());
				user.updateGustos(recipe.getIngredientes());
			}
			if (session.getRecipesReject().size() > 0) {
				user.getRecetasNoGusta().addAll(session.getRecipesReject());
				for (ObjectId id : session.getRecipesReject()) {
					Recipe recipe = mongo.findOne(session.getRecipeAccept()
							.toString());
					user.updateNoMeGusta(recipe.getIngredientes());
				}
			}
			Date now = new Date();
			user.setUltimoAcceso(now);
			session.setEnd(now);
			try {
				mongo.actualizarUsuario(user);
				mongo.guardarSesionDeUsuario(session);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String identDeEstaTarea = getClass().getSimpleName();
			Collection<Object> memoria = this.getItfMotorDeReglas()
					.getStatefulKnowledgeSession().getObjects();
			for (Object objeto : memoria) {
				this.getEnvioHechos().eliminarHecho(objeto);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
