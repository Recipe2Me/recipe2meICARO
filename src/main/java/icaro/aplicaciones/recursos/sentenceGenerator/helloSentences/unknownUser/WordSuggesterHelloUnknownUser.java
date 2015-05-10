package icaro.aplicaciones.recursos.sentenceGenerator.helloSentences.unknownUser;

import icaro.aplicaciones.recursos.sentenceGenerator.helloSentences.WordSuggesterHelloSentence;

import java.util.Random;

public class WordSuggesterHelloUnknownUser extends WordSuggesterHelloSentence{

	public String generateVerb() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 3;
		
		switch (randomNum) {
			case 0:
				principio = "soy";
				break;
			case 1:
				principio = "me llamo";
				break;
			case 2:
				principio = "estás hablando con";
				break;
			default:
				break;
		}
		
		return principio;
	}

	public String generateEnding() {
		String principio = "", registrate = "", pregunta = "";
		Random rand = new Random();
		int randomNum1 = rand.nextInt(1000) % 3, randomNum2 = rand.nextInt(1000) % 3, randomNum3 = rand.nextInt(1000) % 3;
		
		switch (randomNum1) {
			case 0:
				principio = "me dedico a recomendar recetas";
				break;
			case 1:
				principio = "ayudo a elegir recetas";
				break;
			case 2:
				principio = "soy un recomendador de recetas de cocina";
				break;
			default:
				break;
		}
		
		switch (randomNum2) {
			case 0:
				registrate = ", registrate o logearte para que pueda aprender de tus gustos.";
				break;
			case 1:
				registrate = ", si quieres que pueda ir conociendote mejor a medida que recomiendo, debes registrarte o logearte.";
				break;
			case 2:
				registrate = ", si te registras o logeas, podré aprender de tí con el tiempo.";
				break;
			default:
				break;
		}
		
		switch (randomNum3) {
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
		
		return principio + registrate + pregunta;
	}

}
