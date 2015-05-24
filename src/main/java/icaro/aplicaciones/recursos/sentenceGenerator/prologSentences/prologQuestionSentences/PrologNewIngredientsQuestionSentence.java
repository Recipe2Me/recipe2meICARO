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

public abstract class PrologNewIngredientsQuestionSentence extends PrologSentence {

	protected List<String> peticiones;
	
	public PrologNewIngredientsQuestionSentence() {
		try {
			Environment env = new Environment();

			// get the filename relative to the class file
			env.ensureLoaded(AtomTerm.get("frase_pide_nuevos_ingredientes.pl"));

			// Get the interpreter.
			Interpreter interpreter = env.createInterpreter();
			// Run the initialization
			env.runInitialization(interpreter);

			VariableTerm x = new VariableTerm("X");
			Term [] ar = {x};

			CompoundTerm goalTerm = new CompoundTerm(AtomTerm.get("frase"), ar);		
			Goal g = interpreter.prepareGoal(goalTerm);
			peticiones = new ArrayList<String>();

			int rc = interpreter.execute(g);
			while (rc == PrologCode.SUCCESS || rc == PrologCode.SUCCESS_LAST) {
				peticiones.add(trataFrase(x.toString()));
				x.dereference();
				rc = interpreter.execute(g);
			} 
		} catch (Exception e) {}
	}
}
