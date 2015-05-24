package icaro.aplicaciones.recursos.sentenceGenerator.complainSentences.ingredients;

import icaro.aplicaciones.recursos.sentenceGenerator.complainSentences.WordSuggesterComplainSentence;

import java.util.Random;

public class WordSuggesterComplainIngredients extends WordSuggesterComplainSentence {

	public String generateEnding() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 3;
		
		switch (randomNum) {
			case 0:
				principio = "Si no me dices ingredientes, no podré ajustarme a tus gustos";
				break;
			case 1:
				principio = "Es importante que me digas ingredientes, de lo contrario no sabré qué recetarte";
				break;
			case 2:
				principio = "Necesito saberlos, para que la receta sea de tu gusto y vuelvas a verme ;)";
				break;
			default:
				break;
		}
		
		return principio;
	}

}
