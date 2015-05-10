package icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.favorite;

import icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.WordSuggesterIngredientSentence;

import java.util.Random;

public class WordSuggesterFavoriteIngredientsConfirmation extends WordSuggesterIngredientSentence {
	public String generateVerb() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 4;
		
		switch (randomNum) {
			case 0:
				principio = "te gustan";
				break;
			case 1:
				principio = "son de tu agrado";
				break;
			case 2:
				principio = "prefieres";
				break;
			case 3:
				principio = "te encantan";
				break;
			default:
				break;
		}
		
		return principio;
	}
}
