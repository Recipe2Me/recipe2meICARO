package icaro.aplicaciones.recursos.sentenceGenerator.goodBySentences;

import icaro.aplicaciones.recursos.sentenceGenerator.Sentence;
import icaro.aplicaciones.recursos.sentenceGenerator.WordSuggester;

public abstract class GoodbySentence extends Sentence {

	public GoodbySentence(WordSuggester sug) {
		super(sug);
	}

	public abstract String toString();

}
