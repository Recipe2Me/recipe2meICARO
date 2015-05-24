package icaro.aplicaciones.recursos.sentenceGenerator.goodBySentences.knownUser;

import icaro.aplicaciones.recursos.sentenceGenerator.goodBySentences.WordSuggesterGoodbySentence;

import java.util.Random;

public class WordSuggesterGoodbyKnownUser extends WordSuggesterGoodbySentence {

	public String generateEnding() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 3;
		
		switch (randomNum) {
			case 0:
				principio = "Espero volver a verte muy pronto";
				break;
			case 1:
				principio = "Que pases un buen día";
				break;
			case 2:
				principio = "Gracias por usar Recipe2Me";
				break;
			default:
				break;
		}
		
		return principio;
	}

}
