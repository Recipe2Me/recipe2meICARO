package icaro.aplicaciones.recursos.sentenceGenerator.complainSentences;

import icaro.aplicaciones.recursos.sentenceGenerator.Sentence;
import icaro.aplicaciones.recursos.sentenceGenerator.WordSuggester;

public abstract class ComplainSentence extends Sentence {

	public ComplainSentence(WordSuggester sug) {
		super(sug);
	}

	public abstract String toString();

}
