package icaro.aplicaciones.recursos.sentenceGenerator.goodBySentences.unknownUser;

import icaro.aplicaciones.recursos.sentenceGenerator.goodBySentences.GoodbySentence;

public class GoodbyUnknownUserSentence extends GoodbySentence {

	public GoodbyUnknownUserSentence() {
		super(new WordSuggesterGoodbyUnknownUser());
	}

	public String toString() {
		return generateBeggining() + " " + generateVerb() + " " + generateEnding();
	}

}
