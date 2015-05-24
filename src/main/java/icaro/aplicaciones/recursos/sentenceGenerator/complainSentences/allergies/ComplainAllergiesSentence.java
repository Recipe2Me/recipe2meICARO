package icaro.aplicaciones.recursos.sentenceGenerator.complainSentences.allergies;

import icaro.aplicaciones.recursos.sentenceGenerator.complainSentences.ComplainSentence;

public class ComplainAllergiesSentence extends ComplainSentence {

	public ComplainAllergiesSentence() {
		super(new WordSuggesterComplainAllergies());
	}

	public String toString() {
		return generateBeggining() + ", pero " + generateVerb() + " alergias. " + generateEnding();
	}

}
