package icaro.aplicaciones.recursos.sentenceGenerator.complainSentences.allergies;

import icaro.aplicaciones.recursos.sentenceGenerator.complainSentences.WordSuggesterComplainSentence;

import java.util.Random;

public class WordSuggesterComplainAllergies extends WordSuggesterComplainSentence{

	public String generateEnding() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 3;
		
		switch (randomNum) {
			case 0:
				principio = "Si no me dices tus alergias, puede que te recete algo que te mate";
				break;
			case 1:
				principio = "Es importante que me las digas, ya que si no puede ser peligroso";
				break;
			case 2:
				principio = "Necesito saberlas, de esta forma no tendrás que estar atento a los ingredientes";
				break;
			default:
				break;
		}
		
		return principio;
	}

}
