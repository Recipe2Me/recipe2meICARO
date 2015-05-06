package icaro.aplicaciones.recursos.sentences.questionSentences.hated;

import icaro.aplicaciones.recursos.sentences.questionSentences.WordSuggesterQuestionSentence;

import java.util.Random;

public class WordSuggesterHatedIngredientsQuestion extends WordSuggesterQuestionSentence {

	public String generateEnding() {
		String fin = "lo tendré en cuenta a la hora de recomendarte", principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 3;
		
		switch (randomNum) {
			case 0:
				principio = "tus ingredientes odiados";
				break;
			case 1:
				principio = "que ingredientes te gustan menos";
				break;
			case 2:
				principio = "que ingredientes prefieres no comer";
				break;
			default:
				break;
		}
		
		return principio + ", " + fin;
	}

}
