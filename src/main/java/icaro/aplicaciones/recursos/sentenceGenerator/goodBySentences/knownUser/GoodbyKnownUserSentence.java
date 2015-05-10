package icaro.aplicaciones.recursos.sentenceGenerator.goodBySentences.knownUser;

import icaro.aplicaciones.recursos.sentenceGenerator.goodBySentences.GoodbySentence;

public class GoodbyKnownUserSentence extends GoodbySentence {

	private String user;
	
	public GoodbyKnownUserSentence(String user) {
		super(new WordSuggesterGoodbyKnownUser());
		this.user = user;
	}

	public String toString() {
		return generateBeggining() + " " + generateVerb() +  " " + generateEnding() + " " + user + ".";
	}

}
