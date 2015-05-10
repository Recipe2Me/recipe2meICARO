package icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.favorite;

import icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.IngredientSentence;

public class FavoriteIngredientsConfirmationSentence extends IngredientSentence {
	
	public FavoriteIngredientsConfirmationSentence(String ingredients) {
		super(ingredients, new WordSuggesterFavoriteIngredientsConfirmation());
	}
	
}
