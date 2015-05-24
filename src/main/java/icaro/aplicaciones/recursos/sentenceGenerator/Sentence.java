package icaro.aplicaciones.recursos.sentenceGenerator;

public abstract class Sentence {
	private WordSuggester suggester;
	
	public Sentence(WordSuggester sug) {
		suggester = sug;
	}
	
	public String generateBeggining() {
		return suggester.generateBeggining();
	}
	
	public String generateVerb() {
		return suggester.generateVerb();
	}
	
	public String generateEnding() {
		return suggester.generateEnding();
	}
	
	public String conjugate(String beggining, String verb) {
		return suggester.conjugate(beggining, verb);
	}
	
	public abstract String toString();
	
//	public boolean fit(String beggining, String verb, String ending) 
}
