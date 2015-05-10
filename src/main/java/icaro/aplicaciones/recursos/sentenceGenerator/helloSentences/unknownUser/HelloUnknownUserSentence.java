package icaro.aplicaciones.recursos.sentenceGenerator.helloSentences.unknownUser;

import icaro.aplicaciones.recursos.sentenceGenerator.helloSentences.HelloSentence;

public class HelloUnknownUserSentence extends HelloSentence {

	public HelloUnknownUserSentence() {
		super(new WordSuggesterHelloUnknownUser());
	}

	public String toString() {
		return generateBeggining() + " " + generateVerb() + " Recipe2Me " + generateEnding();
	}

}
