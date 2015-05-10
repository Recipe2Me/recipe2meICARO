package icaro.aplicaciones.recursos.sentenceGenerator.questionSentences.allergies;

import icaro.aplicaciones.recursos.sentenceGenerator.questionSentences.QuestionSentence;

public class AllergiesQuestionSentence extends QuestionSentence {

	public AllergiesQuestionSentence() {
		super(new WordSuggesterAllergiesQuestion());
	}
}
