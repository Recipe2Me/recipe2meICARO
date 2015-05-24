package icaro.aplicaciones.recursos.sentenceGenerator.complainSentences;

import icaro.aplicaciones.recursos.sentenceGenerator.WordSuggester;

import java.util.Random;

public abstract class WordSuggesterComplainSentence implements WordSuggester {

	public String generateBeggining() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 4;
		
		switch (randomNum) {
			case 0:
				principio = "Perdona";
				break;
			case 1:
				principio = "Disculpa";
				break;
			case 2:
				principio = "Siento molestar";
				break;
			case 3:
				principio = "Lo siento";
				break;
			default:
				break;
		}
		
		return principio;
	}

	public String generateVerb() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 4;
		
		switch (randomNum) {
			case 0:
				principio = "no has introducido";
				break;
			case 1:
				principio = "lo que me has dicho no son";
				break;
			case 2:
				principio = "debes decirme";
				break;
			case 3:
				principio = "necesito saber";
				break;
			default:
				break;
		}
		
		return principio;
	}

	public abstract String generateEnding();

	public String conjugate(String beggining, String verb) {
		return verb;
	}

}
