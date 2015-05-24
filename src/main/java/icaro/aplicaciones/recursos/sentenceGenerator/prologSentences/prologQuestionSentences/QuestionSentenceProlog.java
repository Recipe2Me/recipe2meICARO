package icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologQuestionSentences;

import gnu.prolog.term.AtomTerm;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.term.Term;
import gnu.prolog.term.VariableTerm;
import gnu.prolog.vm.Environment;
import gnu.prolog.vm.Interpreter;
import gnu.prolog.vm.Interpreter.Goal;
import gnu.prolog.vm.PrologCode;
import icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.PrologSentence;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionSentenceProlog extends PrologSentence {

	List<String> frases_previas;
	List<String> frases_preguntas;
	
	public QuestionSentenceProlog(String objeto) {
		try {
			Environment env = new Environment();

			// get the filename relative to the class file
			env.ensureLoaded(AtomTerm.get("frase_previa_pregunta_formulario.pl"));

			// Get the interpreter.
			Interpreter interprete_frase_previa = env.createInterpreter();
			// Run the initialization
			env.runInitialization(interprete_frase_previa);

			VariableTerm x = new VariableTerm("X");
			AtomTerm t = objeto.equalsIgnoreCase("ingredientes") ? AtomTerm.get("gustos") : AtomTerm.get(objeto);
			Term [] ar = {x, t};

			CompoundTerm goalTerm = new CompoundTerm(AtomTerm.get("frase_previa_pregunta_formulario"), ar);		
			Goal g = interprete_frase_previa.prepareGoal(goalTerm);
			frases_previas = new ArrayList<String>();

			int rc = interprete_frase_previa.execute(g);
			while (rc == PrologCode.SUCCESS || rc == PrologCode.SUCCESS_LAST) {
				frases_previas.add(trataFrase(x.toString()));
				x.dereference();
				rc = interprete_frase_previa.execute(g);
			} 

			env = new Environment();

			// get the filename relative to the class file
			env.ensureLoaded(AtomTerm.get("preguntas_formulario.pl"));

			// Get the interpreter.
			Interpreter interprete_pregunta = env.createInterpreter();
			// Run the initialization
			env.runInitialization(interprete_pregunta);

			x = new VariableTerm("X");
			t = AtomTerm.get(objeto);
			Term [] ar2 = {x, t};

			goalTerm = new CompoundTerm(AtomTerm.get("frase_pregunta_formulario"), ar2);		
			g = interprete_pregunta.prepareGoal(goalTerm);
			frases_preguntas = new ArrayList<String>();

			rc = interprete_pregunta.execute(g);
			while (rc == PrologCode.SUCCESS || rc == PrologCode.SUCCESS_LAST) {
				frases_preguntas.add(trataFrase(x.toString()));
				x.dereference();
				rc = interprete_pregunta.execute(g);
			} 

		}catch(Exception e) {}
	}
	
	public String genera_frase() {
		Random r = new Random();
		int rand = r.nextInt(100000) % frases_previas.size();
		
		String string_frase_previa = frases_previas.get(rand);
		
		r = new Random();
		rand = r.nextInt(100000) % frases_preguntas.size();
		
		String string_pregunta = frases_preguntas.get(rand);
		

		return string_frase_previa + ". " + string_pregunta;
	}

	
}
