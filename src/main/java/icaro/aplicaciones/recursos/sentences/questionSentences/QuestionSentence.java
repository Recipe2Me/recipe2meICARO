package icaro.aplicaciones.recursos.sentences.questionSentences;

import icaro.aplicaciones.recursos.sentences.Sentence;
import icaro.aplicaciones.recursos.sentences.WordSuggester;

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
