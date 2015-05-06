package icaro.aplicaciones.recursos.sentences.questionSentences.allergies;

import icaro.aplicaciones.recursos.sentences.questionSentences.QuestionSentence;

public class AllergiesQuestionSentence extends QuestionSentence {

	public AllergiesQuestionSentence() {
		super(new WordSuggesterAllergiesQuestion());
	}
}
