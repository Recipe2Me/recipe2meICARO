package icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologComplainSentences;

import java.util.Random;

public class PrologNotEndUnderstandSentence extends PrologUnderstandSentence {

	public PrologNotEndUnderstandSentence() {
		super();
	}
	
	@Override
	public String genera_frase() {
		Random r = new Random();
		int rand = r.nextInt(100000) % no_entiendo.size();
		
		String string_frase_entiendo = no_entiendo.get(rand);

		return string_frase_entiendo + ". Pasemos a otra cosa.";
	}

}
