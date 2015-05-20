package icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologHelloSentences;

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

public abstract class PrologHelloSentence extends PrologSentence {

	protected List<String> saludos;
	protected List<String> recomendaciones;
	
	public PrologHelloSentence() {
		try {
			Environment env = new Environment();

			// get the filename relative to the class file
			env.ensureLoaded(AtomTerm.get("pregunta_recomendacion.pl"));

			// Get the interpreter.
			Interpreter interpreter = env.createInterpreter();
			// Run the initialization
			env.runInitialization(interpreter);

			VariableTerm x = new VariableTerm("X");
			Term [] ar = {x};

			CompoundTerm goalTerm = new CompoundTerm(AtomTerm.get("frase_pregunta_recomendacion"), ar);		
			Goal g = interpreter.prepareGoal(goalTerm);
			recomendaciones = new ArrayList<String>();

			int rc = interpreter.execute(g);
			while (rc == PrologCode.SUCCESS || rc == PrologCode.SUCCESS_LAST) {
				recomendaciones.add(trataFrase(x.toString()));
				x.dereference();
				rc = interpreter.execute(g);
			} 
		} catch (Exception e) {}
	}
	
}
