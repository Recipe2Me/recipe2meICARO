package icaro.aplicaciones.recursos.sentenceGenerator.confirmationSentences;

import icaro.aplicaciones.recursos.sentenceGenerator.Sentence;
import icaro.aplicaciones.recursos.sentenceGenerator.WordSuggester;

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
