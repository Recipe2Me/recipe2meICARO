package icaro.aplicaciones.recursos.sentenceGenerator;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoSentenceGenerator extends ItfUsoRecursoSimple {
	public String generateNoRecipeComplainSentence() throws Exception;
	
	public String generateRecipeIngredientsQuestionSentence() throws Exception;
	
	public String generateNotEndUnderstandSentence() throws Exception;
	
	public String generateEndUnderstandSentence() throws Exception;
	
	public String generateValorationQuestion(String nombre_receta) throws Exception;
	
	public String generateAllergiesQuestion() throws Exception;
	
	public String generateFavoriteQuestion() throws Exception;
	
	public String generateLevelQuestion() throws Exception;
	
	public String generateHatedQuestion() throws Exception;
	
	public String generateAllergiesConfirmation(String allergies) throws Exception;
	
	public String generateFavoriteConfirmation(String ingredients) throws Exception;
	
	public String generateHatedQuestion(String ingredients) throws Exception;
	
	public String generateHelloKnownUser(String user) throws Exception;
	
	public String generateHelloUnknownUser() throws Exception;
	
	public String generateGoodbyKnownUser(String user) throws Exception;
	
	public String generateGoodbyUnknownUser() throws Exception;
	
	public String generateAllergiesComplain() throws Exception;
	
	public String generateIngredientsComplain() throws Exception;
}