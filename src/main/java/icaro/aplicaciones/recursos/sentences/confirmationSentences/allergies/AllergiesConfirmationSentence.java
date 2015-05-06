package icaro.aplicaciones.recursos.sentences.confirmationSentences.allergies;

import icaro.aplicaciones.recursos.sentences.confirmationSentences.IngredientSentence;

public class AllergiesConfirmationSentence extends IngredientSentence {

	public AllergiesConfirmationSentence(String allergies) {
		super(allergies, new WordSuggesterAllergiesConfirmation());
	}
	
}
