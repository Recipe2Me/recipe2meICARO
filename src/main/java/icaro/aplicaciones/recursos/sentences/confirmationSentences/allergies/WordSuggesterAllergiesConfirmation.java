package icaro.aplicaciones.recursos.sentences.confirmationSentences.allergies;

import icaro.aplicaciones.recursos.sentences.confirmationSentences.WordSuggesterIngredientSentence;

import java.util.Random;

public class WordSuggesterAllergiesConfirmation extends WordSuggesterIngredientSentence{

	public String generateVerb() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 3;
		
		switch (randomNum) {
			case 0:
				principio = "eres alérgico";
				break;
			case 1:
				principio = "tienes alergia";
				break;
			case 2:
				principio = "te dan alergia";
				break;
			default:
				break;
		}
		
		return principio;
	}
}
