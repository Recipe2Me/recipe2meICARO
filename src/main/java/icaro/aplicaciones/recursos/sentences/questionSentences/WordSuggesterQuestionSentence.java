package icaro.aplicaciones.recursos.sentences.questionSentences;

import icaro.aplicaciones.recursos.sentences.WordSuggester;

import java.util.Random;

public abstract class WordSuggesterQuestionSentence implements WordSuggester {
	
	public String generateBeggining() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 4;
		
		switch (randomNum) {
			case 0:
				principio = "Por favor";
				break;
			case 1:
				principio = "Ahora";
				break;
			case 2:
				principio = "En este momento";
				break;
//			case 3:
//				principio = "Tienes que";
//				break;
			default:
				break;
		}
		
		return principio;
	}

	public String generateVerb() {
		String principio = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 4;
		
		switch (randomNum) {
			case 0:
				principio = "decir";
				break;
			case 1:
				principio = "listar";
				break;
			case 2:
				principio = "enumerar";
				break;
			case 3:
				principio = "informar";
				break;
			default:
				break;
		}
		
		return principio;
	}
	
	public String conjugate(String beggining, String verb) {
		String result = "";
		Random rand = new Random();
		int randomNum = rand.nextInt(1000) % 4;
		
		if(verb.equalsIgnoreCase("decir")) 
			result = "di";
		else if(verb.equalsIgnoreCase("listar")) 
			result = "lista";
		else if(verb.equalsIgnoreCase("informar"))
			result = "informa";
		else if(verb.equalsIgnoreCase("enumerar"))
			result = "enumera";
		
		if(beggining.isEmpty())
			result = result.substring(0, 1).toUpperCase() + result.substring(1);
		
		return randomNum % 2 == 0 ? result : result + "me";
	}
	
	// Devuelve el sujeto
//	private String getSubject(String beggining) {
//		String[] words = beggining.split(" ");
//		String result = "";
//		
//		if (isVerb(words[0]))
//			result = "yo";
//		else 
//			result = "tu";
//		
//		return result;
//	}
//	
//	private boolean isVerb(String text) {
//		boolean result = false;
//		
//		return result;
//	}

	public abstract String generateEnding();
}
