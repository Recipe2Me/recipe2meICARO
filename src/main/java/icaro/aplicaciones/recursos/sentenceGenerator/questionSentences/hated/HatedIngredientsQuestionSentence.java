package icaro.aplicaciones.recursos.sentenceGenerator.questionSentences.hated;

import icaro.aplicaciones.recursos.sentenceGenerator.questionSentences.QuestionSentence;

public class HatedIngredientsQuestionSentence extends QuestionSentence{

	public HatedIngredientsQuestionSentence() {
		super(new WordSuggesterHatedIngredientsQuestion());
	}

}
