package icaro.aplicaciones.recursos.sentenceGenerator.questionSentences.favorite;

import icaro.aplicaciones.recursos.sentenceGenerator.questionSentences.QuestionSentence;

public class FavoriteIngredientsQuestionSentence extends QuestionSentence{

	public FavoriteIngredientsQuestionSentence() {
		super(new WordSuggesterFavoriteIngredients());
	}

}
