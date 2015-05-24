package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

/**
 * <p>Title: Agenda de citas vocal</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Telefï¿½nica  I+D</p>
 * @author Jorge Gonzï¿½lez
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
import icaro.aplicaciones.recursos.sentenceGenerator.SentenceFactory;
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

	public EnviarMensajeInicialDeObjetivo(){
		this.repoIntfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
		//Definimos el recurso extractor semantico
		try {
			itfUsoExtractorSemantico = (ItfUsoExtractorSemantico) this.repoIntfaces
					.obtenerInterfazUso("ExtractorSemantico1");
			itfUsComunicacionoWeb = (ItfUsoComunicacionWeb) this.repoIntfaces
					.obtenerInterfazUso("ComunicacionWeb1");
			itfUsoPersistenciaMongo = (ItfUsoPersistenciaMongo) this.repoInterfaces.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoPersistenciaMongo);
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
		UserProfile user = null;
		try {
		if (objetivo instanceof ObtenerConocimientoInicial) {
			ObtenerConocimientoInicial conocimiento = (ObtenerConocimientoInicial) objetivo;
			if (conocimiento.getSubFocus() instanceof ObtenerIngredientesFavoritos) {
				itfUsComunicacionoWeb.enviarMensageAlUsuario(SentenceFactory.generateFavoriteQuestion(),session.getUser());
			} else if (conocimiento.getSubFocus() instanceof ObtenerIngredientesOdiados) {
				itfUsComunicacionoWeb.enviarMensageAlUsuario(SentenceFactory.generateHatedQuestion(),session.getUser());
			} else if (conocimiento.getSubFocus() instanceof ObtenerAlergia) {
				itfUsComunicacionoWeb.enviarMensageAlUsuario(SentenceFactory.generateAllergiesQuestion(),session.getUser());
			} else if (conocimiento.getSubFocus() instanceof ObtenerNivelCocina) {
				itfUsComunicacionoWeb.enviarMensageAlUsuario(SentenceFactory.generateLevelQuestion(),session.getUser());
			}
		} else if (objetivo instanceof ObtenerIngredienteOPlato) {
			itfUsComunicacionoWeb.enviarMensageAlUsuario("Objetivo obtener ingredientes o plato",session.getUser());
		} else if (objetivo instanceof ObtenerValoracionUsuario) {
			itfUsComunicacionoWeb.enviarMensageAlUsuario("La ultima vez que accediste elegiste cocinar la receta x, ¿te gustaria valorarla?",session.getUser());
		} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
