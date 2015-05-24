package icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologComplainSentences;

import java.util.Random;

public class PrologEndUnderstandSentence extends PrologUnderstandSentence {

	public PrologEndUnderstandSentence() {
		super();
	}
	
	@Override
	public String genera_frase() {
		Random r = new Random();
		int rand = r.nextInt(100000) % no_entiendo.size();
		
		String string_frase_entiendo = no_entiendo.get(rand);

		return string_frase_entiendo + ". Mejor vuelve en otro momento. Adios";
	}

}
