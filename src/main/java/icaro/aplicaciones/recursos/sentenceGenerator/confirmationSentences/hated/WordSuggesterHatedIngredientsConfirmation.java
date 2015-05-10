package icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.hated;

import icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences.WordSuggesterIngredientSentence;

import java.util.Random;

public class WordSuggesterHatedIngredientsConfirmation extends WordSuggesterIngredientSentence {	
	public String generateVerb() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 4;
		
		switch (randomNum) {
			case 0:
				principio = "odias";
				break;
			case 1:
				principio = "no te gustan";
				break;
			case 2:
				principio = "aborreces";
				break;
			case 3:
				principio = "nunca comerías";
				break;
			default:
				break;
		}
		
		return principio;
	}
}
