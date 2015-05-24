package icaro.aplicaciones.recursos.sentenceGenerator;

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

public class SentenceFactory {
	private static QuestionSentenceProlog pregunta_gustos = new QuestionSentenceProlog("ingredientes");
	private static QuestionSentenceProlog pregunta_alergias = new QuestionSentenceProlog("alergias");
	private static QuestionSentenceProlog pregunta_nivel = new QuestionSentenceProlog("nivel");
	private static PrologHelloSentence saludo_conocido = new PrologHelloKnownUserSentence("user_name");
	private static PrologHelloSentence saludo_desconocido = new PrologHelloUnknownUserSentence();
	private static ValorationSentenceProlog valora_receta = new ValorationSentenceProlog();
	private static PrologUnderstandSentence no_entiendo = new PrologNotEndUnderstandSentence();
	private static PrologRecipeIngredientsQuestionSentence peticion_ingredientes = new PrologRecipeIngredientsQuestionSentence();
	private static PrologUnderstandSentence no_entiendo_fin = new PrologEndUnderstandSentence();
	private static PrologNoRecipesComplainSentence no_recetas = new PrologNoRecipesComplainSentence();
	
	
	public static String generateNoRecipeComplainSentence() {
		return no_recetas.genera_frase();
	}
	
	public static String generateRecipeIngredientsQuestionSentence() {
		return peticion_ingredientes.genera_frase();
	}
	
	public static String generateNotEndUnderstandSentence() {
		return no_entiendo.genera_frase();
	}
	
	public static String generateEndUnderstandSentence() {
		return no_entiendo_fin.genera_frase();
	}
	
	public static String generateValorationQuestion(String nombre_receta) {
		return valora_receta.genera_frase().replaceAll("nombre_de_la_receta", nombre_receta);
	}
	
	public static String generateAllergiesQuestion() {
		return pregunta_alergias.genera_frase(); 
	}
	
	public static String generateFavoriteQuestion() {
		return pregunta_gustos.genera_frase(); 
	}
	
	public static String generateLevelQuestion() {
		return pregunta_nivel.genera_frase(); 
	}
	
	public static String generateHatedQuestion() {
		return (new HatedIngredientsQuestionSentence()).toString(); 
	}
	
	public static String generateAllergiesConfirmation(String allergies) {
		return (new AllergiesConfirmationSentence(allergies)).toString(); 
	}
	
	public static String generateFavoriteConfirmation(String ingredients) {
		return (new FavoriteIngredientsConfirmationSentence(ingredients)).toString(); 
	}
	
	public static String generateHatedQuestion(String ingredients) {
		return (new HatedIngredientsConfirmationSentence(ingredients)).toString(); 
	}
	
	public static String generateHelloKnownUser(String user) {
		return saludo_conocido.genera_frase().replaceAll("user_name", user); 
	}
	
	public static String generateHelloUnknownUser() {
		return saludo_desconocido.genera_frase(); 
	}
	
	public static String generateGoodbyKnownUser(String user) {
		return (new GoodbyKnownUserSentence(user)).toString(); 
	}
	
	public static String generateGoodbyUnknownUser() {
		return (new GoodbyUnknownUserSentence()).toString(); 
	}
	
	public static String generateAllergiesComplain() {
		return (new ComplainAllergiesSentence()).toString(); 
	}
	
	public static String generateIngredientsComplain() {
		return (new ComplainIngredientsSentence()).toString(); 
	}
}

