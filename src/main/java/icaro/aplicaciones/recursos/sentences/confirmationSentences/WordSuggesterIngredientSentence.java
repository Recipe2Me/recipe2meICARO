package icaro.aplicaciones.recursos.sentences.confirmationSentences;

import icaro.aplicaciones.recursos.sentences.WordSuggester;

import java.util.Random;

public abstract class WordSuggesterIngredientSentence implements WordSuggester {
	public String generateBeggining() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 4;
		
		switch (randomNum) {
			case 0:
				principio = "Así que";
				break;
			case 1:
				principio = "De modo que";
				break;
			case 2:
				principio = "Entiendo que";
				break;
			case 3:
				principio = "Entonces";
				break;
			default:
				break;
		}
		
		return principio;
	}

	public abstract String generateVerb();

	public String generateEnding() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 4;
		
		switch (randomNum) {
			case 0:
				principio = "cierto?";
				break;
			case 1:
				principio = "no es así?";
				break;
			case 2:
				principio = "me equivoco?";
				break;
			case 3:
				principio = "correcto?";
				break;
			default:
				break;
		}
		
		return principio;
	}
	
	public String conjugate(String beggining, String verb) {return verb;}
}
