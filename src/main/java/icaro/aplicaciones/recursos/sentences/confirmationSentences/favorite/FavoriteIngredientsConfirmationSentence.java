package icaro.aplicaciones.recursos.sentences.confirmationSentences.favorite;

import icaro.aplicaciones.recursos.sentences.confirmationSentences.IngredientSentence;

public class FavoriteIngredientsConfirmationSentence extends IngredientSentence {
	
	public FavoriteIngredientsConfirmationSentence(String ingredients) {
		super(ingredients, new WordSuggesterFavoriteIngredientsConfirmation());
	}
	
}
