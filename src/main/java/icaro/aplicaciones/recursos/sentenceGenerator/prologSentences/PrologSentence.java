package icaro.aplicaciones.recursos.sentenceGenerator.prologSentences;

public abstract class PrologSentence {
	protected String trataFrase(String frase) {
		frase = frase.replace(',', ' ');
		frase = frase.replace("[", "");
		frase = frase.replace("]", "");
		frase = frase.replace("'", "");
		frase = frase.replace("  ", " ");
		frase = frase.replace(" ?", "?");
		frase = frase.replace(" !", "!");
		frase = frase.replace(" .", ".");
		if (frase.charAt(0) == ' ')
			frase = frase.substring(1, frase.length());
		frase = frase.substring(0, 1).toUpperCase() + frase.substring(1, frase.length());
		
		return frase;
	}
	
	public abstract String genera_frase();
}
