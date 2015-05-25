package icaro.aplicaciones.recursos.sentenceGenerator;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import icaro.aplicaciones.informacion.dominioRecipe2Me.Recipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.DecisionUsuario;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoConexion;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoDesconexion;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeDelUsuario;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.ValoracionUsuario;
import icaro.aplicaciones.recursos.comunicacionWeb.config.DisconnectInterceptor;
import icaro.aplicaciones.recursos.comunicacionWeb.config.SecurityConfiguration;
import icaro.aplicaciones.recursos.comunicacionWeb.config.WebSocketConfig;
import icaro.aplicaciones.recursos.comunicacionWeb.controller.HomeController;
import icaro.aplicaciones.recursos.persistenciaMongo.ItfUsoPersistenciaMongo;
import icaro.aplicaciones.recursos.sentenceGenerator.complainSentences.allergies.ComplainAllergiesSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.complainSentences.ingredients.ComplainIngredientsSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.allergies.AllergiesConfirmationSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.favorite.FavoriteIngredientsConfirmationSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.hated.HatedIngredientsConfirmationSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.goodBySentences.knownUser.GoodbyKnownUserSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.goodBySentences.unknownUser.GoodbyUnknownUserSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologComplainSentences.PrologEndUnderstandSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologComplainSentences.PrologNoRecipesComplainSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologComplainSentences.PrologNotEndUnderstandSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologComplainSentences.PrologUnderstandSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologHelloSentences.PrologHelloSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologHelloSentences.prologHelloKnownUser.PrologHelloKnownUserSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologHelloSentences.prologHelloUnknownUser.PrologHelloUnknownUserSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologQuestionSentences.PrologRecipeIngredientsQuestionSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologQuestionSentences.QuestionSentenceProlog;
import icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologValoration.ValorationSentenceProlog;
import icaro.aplicaciones.recursos.sentenceGenerator.questionSentences.hated.HatedIngredientsQuestionSentence;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgenteAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.FactoriaAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.ItfGestPercepcionAgenteCognitivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.patronRecursoWeb.config.JettyConfiguration;
import icaro.infraestructura.patronRecursoWeb.imp.ImplRecursoWeb;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ClaseGeneradoraSentenceGenerator extends ImplRecursoSimple implements ItfUsoSentenceGenerator {

	private String identRecurso;
	private String identificadorAgenteGestorDialogo;
	public InterfazUsoAgente itfUsoAgenteGestDialogo;
	
	private QuestionSentenceProlog pregunta_gustos = new QuestionSentenceProlog("ingredientes");
	private QuestionSentenceProlog pregunta_alergias = new QuestionSentenceProlog("alergias");
	private QuestionSentenceProlog pregunta_nivel = new QuestionSentenceProlog("nivel");
	private PrologHelloSentence saludo_conocido = new PrologHelloKnownUserSentence("user_name");
	private PrologHelloSentence saludo_desconocido = new PrologHelloUnknownUserSentence();
	private ValorationSentenceProlog valora_receta = new ValorationSentenceProlog();
	private PrologUnderstandSentence no_entiendo = new PrologNotEndUnderstandSentence();
	private PrologRecipeIngredientsQuestionSentence peticion_ingredientes = new PrologRecipeIngredientsQuestionSentence();
	private PrologUnderstandSentence no_entiendo_fin = new PrologEndUnderstandSentence();
	private PrologNoRecipesComplainSentence no_recetas = new PrologNoRecipesComplainSentence();

	public ClaseGeneradoraSentenceGenerator(String id) throws Exception {
		super(id);
	}
	
	@Override
	public void termina() {
		trazas.aceptaNuevaTraza(new InfoTraza(this.getId(),
  				"Terminando recurso",
  				InfoTraza.NivelTraza.debug));
		try {
			super.termina();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String generateNoRecipeComplainSentence() {
		return no_recetas.genera_frase();
	}
	
	public String generateRecipeIngredientsQuestionSentence() {
		return peticion_ingredientes.genera_frase();
	}
	
	public String generateNotEndUnderstandSentence() {
		return no_entiendo.genera_frase();
	}
	
	public String generateEndUnderstandSentence() {
		return no_entiendo_fin.genera_frase();
	}
	
	public String generateValorationQuestion(String nombre_receta) {
		return valora_receta.genera_frase().replaceAll("nombre_de_la_receta", nombre_receta);
	}
	
	public String generateAllergiesQuestion() {
		return pregunta_alergias.genera_frase(); 
	}
	
	public String generateFavoriteQuestion() {
		return pregunta_gustos.genera_frase(); 
	}
	
	public String generateLevelQuestion() {
		return pregunta_nivel.genera_frase(); 
	}
	
	public String generateHatedQuestion() {
		return (new HatedIngredientsQuestionSentence()).toString(); 
	}
	
	public String generateAllergiesConfirmation(String allergies) {
		return (new AllergiesConfirmationSentence(allergies)).toString(); 
	}
	
	public String generateFavoriteConfirmation(String ingredients) {
		return (new FavoriteIngredientsConfirmationSentence(ingredients)).toString(); 
	}
	
	public String generateHatedQuestion(String ingredients) {
		return (new HatedIngredientsConfirmationSentence(ingredients)).toString(); 
	}
	
	public String generateHelloKnownUser(String user) {
		return saludo_conocido.genera_frase().replaceAll("user_name", user); 
	}
	
	public String generateHelloUnknownUser() {
		return saludo_desconocido.genera_frase(); 
	}
	
	public String generateGoodbyKnownUser(String user) {
		return (new GoodbyKnownUserSentence(user)).toString(); 
	}
	
	public String generateGoodbyUnknownUser() {
		return (new GoodbyUnknownUserSentence()).toString(); 
	}
	
	public String generateAllergiesComplain() {
		return (new ComplainAllergiesSentence()).toString(); 
	}
	
	public String generateIngredientsComplain() {
		return (new ComplainIngredientsSentence()).toString(); 
	}

}
