package icaro.aplicaciones.recursos.sentenceGenerator.complainSentences.ingredients;

import icaro.aplicaciones.recursos.sentenceGenerator.complainSentences.ComplainSentence;

public class ComplainIngredientsSentence extends ComplainSentence {

	public ComplainIngredientsSentence() {
		super(new WordSuggesterComplainIngredients());
	}

	public String toString() {
		return generateBeggining() + ", pero " + generateVerb() + " ingredientes. " + generateEnding();
	}

}
