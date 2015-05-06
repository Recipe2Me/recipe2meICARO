package icaro.aplicaciones.recursos.sentences.confirmationSentences;

import icaro.aplicaciones.recursos.sentences.Sentence;
import icaro.aplicaciones.recursos.sentences.WordSuggester;

public class IngredientSentence extends Sentence {
	private String ingredients;
	
	public IngredientSentence(String ingredients, WordSuggester w) {
		super(w);
		this.ingredients = ingredients; 
	}

	public String toString() {
		return generateBeggining() + ", " + generateVerb() + " " + getIngredients() + ", " + generateEnding();
	}

	public String getIngredients() {
		return ingredients;
	}
}
