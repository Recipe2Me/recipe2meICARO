package icaro.aplicaciones.recursos.sentenceGenerator.helloSentences;

import icaro.aplicaciones.recursos.sentenceGenerator.Sentence;
import icaro.aplicaciones.recursos.sentenceGenerator.WordSuggester;

public abstract class HelloSentence extends Sentence {

	public HelloSentence(WordSuggester sug) {
		super(sug);
	}

	public abstract String toString();

}
