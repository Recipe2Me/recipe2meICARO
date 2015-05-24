package icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologComplainSentences;

import gnu.prolog.term.AtomTerm;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.term.Term;
import gnu.prolog.term.VariableTerm;
import gnu.prolog.vm.Environment;
import gnu.prolog.vm.Interpreter;
import gnu.prolog.vm.Interpreter.Goal;
import gnu.prolog.vm.PrologCode;
import gnu.prolog.vm.PrologException;
import icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologQuestionSentences.PrologNewIngredientsQuestionSentence;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrologNoRecipesComplainSentence extends PrologNewIngredientsQuestionSentence {

	private List<String> no_recetas;
	
	public PrologNoRecipesComplainSentence() {
		super();
		
		try {
			Environment env = new Environment();

			// get the filename relative to the class file
			env.ensureLoaded(AtomTerm.get("no_recetas.pl"));

			// Get the interpreter.
			Interpreter interpreter = env.createInterpreter();
			// Run the initialization
			env.runInitialization(interpreter);

			VariableTerm x = new VariableTerm("X");
			Term [] ar = {x};

			CompoundTerm goalTerm = new CompoundTerm(AtomTerm.get("frase"), ar);		
			Goal g = interpreter.prepareGoal(goalTerm);
			no_recetas = new ArrayList<String>();

			int rc;

			rc = interpreter.execute(g);

			while (rc == PrologCode.SUCCESS || rc == PrologCode.SUCCESS_LAST) {
				no_recetas.add(trataFrase(x.toString()));
				x.dereference();
				rc = interpreter.execute(g);
			} 

		} catch (PrologException e) {}
	}
	
	@Override
	public String genera_frase() {
		Random r = new Random();
		int rand = r.nextInt(100000) % no_recetas.size();
		
		String string_frase_no_recetas = no_recetas.get(rand);

		r = new Random();
		rand = r.nextInt(100000) % peticiones.size();
		
		String string_frase_peticion = peticiones.get(rand);

		
		return string_frase_no_recetas + ". " + string_frase_peticion;
	}

}
