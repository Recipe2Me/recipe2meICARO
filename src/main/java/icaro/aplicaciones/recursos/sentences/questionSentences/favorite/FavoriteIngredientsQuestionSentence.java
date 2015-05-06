package icaro.aplicaciones.recursos.sentences.questionSentences.favorite;

import icaro.aplicaciones.recursos.sentences.questionSentences.QuestionSentence;

public class FavoriteIngredientsQuestionSentence extends QuestionSentence{

	public FavoriteIngredientsQuestionSentence() {
		super(new WordSuggesterFavoriteIngredientsQuestion());
	}

}
