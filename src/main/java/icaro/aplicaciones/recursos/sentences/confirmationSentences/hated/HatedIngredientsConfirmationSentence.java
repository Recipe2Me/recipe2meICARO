package icaro.aplicaciones.recursos.sentences.confirmationSentences.hated;

import icaro.aplicaciones.recursos.sentences.confirmationSentences.IngredientSentence;

public class HatedIngredientsConfirmationSentence extends IngredientSentence {

	public HatedIngredientsConfirmationSentence(String ingredients) {
		super(ingredients, new WordSuggesterHatedIngredientsConfirmation()); 
	}

}
