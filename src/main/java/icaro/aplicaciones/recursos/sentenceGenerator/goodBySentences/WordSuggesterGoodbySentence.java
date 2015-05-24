package icaro.aplicaciones.recursos.sentenceGenerator.goodBySentences;

import icaro.aplicaciones.recursos.sentenceGenerator.WordSuggester;

import java.util.Random;

public abstract class WordSuggesterGoodbySentence implements WordSuggester {

	public String generateBeggining() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 3;
		
		switch (randomNum) {
			case 0:
				principio = "Adi�s!";
				break;
			case 1:
				principio = "Hasta luego!";
				break;
			case 2:
				principio = "Hasta la pr�xima!";
				break;
			case 3:
				principio = "Ciao!";
				break;
			default:
				break;
		}
		
		return principio;
	}

	public String generateVerb() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 3;
		
		switch (randomNum) {
			case 0:
				principio = "espero haberte sido de utilidad.";
				break;
			case 1:
				principio = "ojala te haya sido �til mi ayuda.";
				break;
			case 2:
				principio = "gracias por dejar que te ayude.";
				break;
			default:
				break;
		}
		
		return principio;
	}

	public abstract String generateEnding();

	public String conjugate(String beggining, String verb) {return verb;}

}
