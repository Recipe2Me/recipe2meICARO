package icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.hated;

import icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.IngredientSentence;

public class HatedIngredientsConfirmationSentence extends IngredientSentence {

	public HatedIngredientsConfirmationSentence(String ingredients) {
		super(ingredients, new WordSuggesterHatedIngredientsConfirmation()); 
	}

}
