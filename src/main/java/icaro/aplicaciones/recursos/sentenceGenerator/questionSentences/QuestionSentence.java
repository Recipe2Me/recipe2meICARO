package icaro.aplicaciones.recursos.sentenceGenerator.questionSentences;

import icaro.aplicaciones.recursos.sentenceGenerator.Sentence;
import icaro.aplicaciones.recursos.sentenceGenerator.WordSuggester;

public class QuestionSentence extends Sentence {

	public QuestionSentence(WordSuggester sug) {
		super(sug);
	}

	public String toString() {
		String beggining = "";
		
		beggining = generateBeggining();
		
		return beggining + " " + conjugate(beggining, generateVerb()) + " " + generateEnding();
	}

	
}
