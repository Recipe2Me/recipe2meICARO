package icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.allergies;

import icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.IngredientSentence;

public class AllergiesConfirmationSentence extends IngredientSentence {

	public AllergiesConfirmationSentence(String allergies) {
		super(allergies, new WordSuggesterAllergiesConfirmation());
	}
	
}
