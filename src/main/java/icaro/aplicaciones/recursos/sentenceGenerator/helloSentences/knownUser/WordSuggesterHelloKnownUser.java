package icaro.aplicaciones.recursos.sentenceGenerator.helloSentences.knownUser;

import icaro.aplicaciones.recursos.sentenceGenerator.helloSentences.WordSuggesterHelloSentence;

import java.util.Random;

public class WordSuggesterHelloKnownUser extends WordSuggesterHelloSentence {

	public String generateVerb() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 3;
		
		switch (randomNum) {
			case 0:
				principio = "bienvenido de nuevo a";
				break;
			case 1:
				principio = "me alegro de volver a verte por";
				break;
			case 2:
				principio = "gracias por volver a";
				break;
			default:
				break;
		}
		
		return principio;
	}

	public String generateEnding() {
		Random rand = new Random();
		String pregunta = "";
		int randomNum1 = rand.nextInt(1000) % 3;
				
		switch (randomNum1) {
		case 0:
			pregunta = " ¿Qué te apetece comer?";
			break;
		case 1:
			pregunta = " ¿Te apetece algo en particular?";
			break;
		case 2:
			pregunta = " ¿Qué puedo recomendarte?";
			break;
		default:
			break;
	}
		
		return pregunta;
	}

}
