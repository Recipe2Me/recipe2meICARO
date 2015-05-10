package icaro.aplicaciones.recursos.sentenceGenerator.helloSentences;

import icaro.aplicaciones.recursos.sentenceGenerator.WordSuggester;

import java.util.Random;

public abstract class WordSuggesterHelloSentence implements WordSuggester {

	public String generateBeggining() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 4;
		
		switch (randomNum) {
			case 0:
				principio = "Saludos!";
				break;
			case 1:
				principio = "Muy buenas!";
				break;
			case 2:
				principio = "Qué tal?";
				break;
			case 3:
				principio = "Hola!";
				break;
			default:
				break;
		}
		
		return principio;
	}

	public abstract String generateVerb();

	public abstract String generateEnding();

	public String conjugate(String beggining, String verb) {
		return verb;
	}

}
