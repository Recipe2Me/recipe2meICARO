package icaro.aplicaciones.recursos.sentenceGenerator;

import icaro.aplicaciones.recursos.sentenceGenerator.complainSentences.allergies.ComplainAllergiesSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.complainSentences.ingredients.ComplainIngredientsSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.allergies.AllergiesConfirmationSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.favorite.FavoriteIngredientsConfirmationSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.hated.HatedIngredientsConfirmationSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.goodBySentences.knownUser.GoodbyKnownUserSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.goodBySentences.unknownUser.GoodbyUnknownUserSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.helloSentences.knownUser.HelloKnownUserSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.helloSentences.unknownUser.HelloUnknownUserSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.questionSentences.allergies.AllergiesQuestionSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.questionSentences.favorite.FavoriteIngredientsQuestionSentence;
import icaro.aplicaciones.recursos.sentenceGenerator.questionSentences.hated.HatedIngredientsQuestionSentence;

public class SentenceFactory {
	
	public static String generateAllergiesQuestion() {
		return (new AllergiesQuestionSentence()).toString(); 
	}
	
	public static String generateFavoriteQuestion() {
		return (new FavoriteIngredientsQuestionSentence()).toString(); 
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
		return (new HelloKnownUserSentence(user)).toString(); 
	}
	
	public static String generateHelloUnknownUser() {
		return (new HelloUnknownUserSentence()).toString(); 
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
