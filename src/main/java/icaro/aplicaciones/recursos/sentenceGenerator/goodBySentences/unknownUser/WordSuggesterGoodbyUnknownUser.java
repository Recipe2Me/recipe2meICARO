package icaro.aplicaciones.recursos.sentenceGenerator.goodBySentences.unknownUser;

import icaro.aplicaciones.recursos.sentenceGenerator.goodBySentences.WordSuggesterGoodbySentence;

import java.util.Random;

public class WordSuggesterGoodbyUnknownUser extends WordSuggesterGoodbySentence {

	public String generateEnding() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 3;
		
		switch (randomNum) {
			case 0:
				principio = "Recuerda que si te registras podré aprender de ti.";
				break;
			case 1:
				principio = "Para que la experiencia sea mas satisfactoria, deberías registrarte.";
				break;
			case 2:
				principio = "Para exprimir todo mi potencial, debes estar registrado.";
				break;
			default:
				break;
		}
		
		return principio;
	}

}
