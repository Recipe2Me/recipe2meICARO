package icaro.aplicaciones.recursos.sentences;

public interface WordSuggester {
	public String generateBeggining();
	public String generateVerb();
	public String generateEnding();
	public String conjugate(String beggining, String verb);
}
