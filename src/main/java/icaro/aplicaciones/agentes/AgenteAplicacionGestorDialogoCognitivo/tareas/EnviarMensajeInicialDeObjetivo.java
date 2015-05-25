package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

/**
 * <p>Title: Agenda de citas vocal</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Telef�nica  I+D</p>
 * @author Jorge Gonz�lez
 * @version 1.0
 */

import java.util.Date;

import org.hamcrest.core.IsInstanceOf;

import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerAlergia;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerConocimientoInicial;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerIngredienteOPlato;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerIngredientesFavoritos;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerIngredientesOdiados;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerNivelCocina;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerValoracionUsuario;
import icaro.aplicaciones.informacion.dominioRecipe2Me.Message;
import icaro.aplicaciones.informacion.dominioRecipe2Me.Recipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserSession;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeHaciaUsuario;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.aplicaciones.recursos.persistenciaMongo.ItfUsoPersistenciaMongo;
import icaro.aplicaciones.recursos.sentenceGenerator.ItfUsoSentenceGenerator;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaAsincrona;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

public class EnviarMensajeInicialDeObjetivo extends TareaAsincrona {
	
	public ItfUsoRepositorioInterfaces repoIntfaces;
	private ItfUsoExtractorSemantico itfUsoExtractorSemantico;
	private ItfUsoComunicacionWeb itfUsComunicacionoWeb;
	private ItfUsoPersistenciaMongo itfUsoPersistenciaMongo;
	private ItfUsoSentenceGenerator itfSentenceGenerator;

	public EnviarMensajeInicialDeObjetivo(){
		this.repoIntfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
		//Definimos el recurso extractor semantico
		try {
			itfUsoExtractorSemantico = (ItfUsoExtractorSemantico) this.repoIntfaces
					.obtenerInterfazUso("ExtractorSemantico1");
			itfUsComunicacionoWeb = (ItfUsoComunicacionWeb) this.repoIntfaces
					.obtenerInterfazUso("ComunicacionWeb1");
			itfUsoPersistenciaMongo = (ItfUsoPersistenciaMongo) this.repoInterfaces.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoPersistenciaMongo);
			itfSentenceGenerator = (ItfUsoSentenceGenerator) this.repoInterfaces.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoSentenceGenerator);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void ejecutar(Object... params) {
		String identTarea = this.getIdentTarea();
        String nombreAgenteEmisor = this.getIdentAgente();
		Objetivo objetivo = (Objetivo) params[0];
		UserSession session = (UserSession) params[1];
		try {
		if (objetivo instanceof ObtenerConocimientoInicial) {
			ObtenerConocimientoInicial conocimiento = (ObtenerConocimientoInicial) objetivo;
			if (conocimiento.getSubFocus() instanceof ObtenerIngredientesFavoritos) {
				itfUsComunicacionoWeb.enviarMensageAlUsuario(itfSentenceGenerator.generateFavoriteQuestion(),session.getUser());
			} else if (conocimiento.getSubFocus() instanceof ObtenerIngredientesOdiados) {
				itfUsComunicacionoWeb.enviarMensageAlUsuario(itfSentenceGenerator.generateHatedQuestion(),session.getUser());
			} else if (conocimiento.getSubFocus() instanceof ObtenerAlergia) {
				itfUsComunicacionoWeb.enviarMensageAlUsuario(itfSentenceGenerator.generateAllergiesQuestion(),session.getUser());
			} else if (conocimiento.getSubFocus() instanceof ObtenerNivelCocina) {
				itfUsComunicacionoWeb.enviarMensageAlUsuario(itfSentenceGenerator.generateLevelQuestion(),session.getUser());
			}
		} else if (objetivo instanceof ObtenerIngredienteOPlato) {
			if (session.isNoReceta()) {
				itfUsComunicacionoWeb.enviarMensageAlUsuario(itfSentenceGenerator.generateNoRecipeComplainSentence(),session.getUser());
			} else if (session.isNoMasRecetas()) {
				itfUsComunicacionoWeb.enviarMensageAlUsuario(itfSentenceGenerator.generateNoRecipeComplainSentence(),session.getUser());
			} else {
				itfUsComunicacionoWeb.enviarMensageAlUsuario(itfSentenceGenerator.generateRecipeIngredientsQuestionSentence(),session.getUser());
			}
		} else if (objetivo instanceof ObtenerValoracionUsuario) {
			UserProfile user = (UserProfile) params[2];
			Recipe recipe = itfUsoPersistenciaMongo.findOne(user.getRecetaPendiente().toString());
			itfUsComunicacionoWeb.enviarMensageAlUsuario(itfSentenceGenerator.generateValorationQuestion(recipe.getTitle()),session.getUser());
		} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
