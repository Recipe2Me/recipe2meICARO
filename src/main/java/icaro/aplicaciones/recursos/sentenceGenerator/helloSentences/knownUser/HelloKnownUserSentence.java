package icaro.aplicaciones.recursos.sentenceGenerator.helloSentences.knownUser;

import icaro.aplicaciones.recursos.sentenceGenerator.helloSentences.HelloSentence;

public class HelloKnownUserSentence extends HelloSentence {

	private String user;
	
	public HelloKnownUserSentence(String user) {
		super(new WordSuggesterHelloKnownUser());
		this.user = user;
	}

	public String toString() {
		return generateBeggining() + " " + generateVerb() + " Recipe2Me " + user + generateEnding();
	}

}
