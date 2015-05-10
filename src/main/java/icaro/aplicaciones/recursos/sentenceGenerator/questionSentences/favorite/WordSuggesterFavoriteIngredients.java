package icaro.aplicaciones.recursos.sentenceGenerator.questionSentences.favorite;

import icaro.aplicaciones.recursos.sentenceGenerator.questionSentences.WordSuggesterQuestionSentence;

import java.util.Random;

public class WordSuggesterFavoriteIngredients extends WordSuggesterQuestionSentence {

	public String generateEnding() {
		String fin = "lo tendré en cuenta a la hora de recomendarte", principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 3;
		
		switch (randomNum) {
			case 0:
				principio = "tus ingredientes favoritos";
				break;
			case 1:
				principio = "que ingredientes te gustan mas";
				break;
			case 2:
				principio = "que ingredientes prefieres";
				break;
			default:
				break;
		}
		
		return principio + ", " + fin;
	}

}
