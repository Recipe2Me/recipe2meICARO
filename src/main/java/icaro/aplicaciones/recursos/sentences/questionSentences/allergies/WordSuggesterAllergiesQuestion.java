package icaro.aplicaciones.recursos.sentences.questionSentences.allergies;

import icaro.aplicaciones.recursos.sentences.questionSentences.WordSuggesterQuestionSentence;

import java.util.Random;

public class WordSuggesterAllergiesQuestion extends WordSuggesterQuestionSentence {

	public String generateEnding() {
		String fin = "lo tendré en cuenta a la hora de recomendarte", principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 3;
		
		switch (randomNum) {
			case 0:
				principio = "si tienes alguna alergia";
				break;
			case 1:
				principio = "las alergias que tengas";
				break;
			case 2:
				principio = "a qué eres alérgico";
				break;
			default:
				break;
		}
		
		return principio + ", " + fin;
	}

}
