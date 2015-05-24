package icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologValoration;

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

public class ValorationSentenceProlog extends PrologSentence {

	private List<String> valoracion;
	
	public ValorationSentenceProlog() {
		try {
			Environment env = new Environment();

			// get the filename relative to the class file
			env.ensureLoaded(AtomTerm.get("frase_valora_receta.pl"));

			// Get the interpreter.
			Interpreter interpreter = env.createInterpreter();
			// Run the initialization
			env.runInitialization(interpreter);

			VariableTerm x = new VariableTerm("X");
			AtomTerm t = AtomTerm.get("nombre_de_la_receta");
			Term [] ar = {x, t};

			CompoundTerm goalTerm = new CompoundTerm(AtomTerm.get("frase"), ar);		
			Goal g = interpreter.prepareGoal(goalTerm);
			valoracion = new ArrayList<String>();

			int rc = interpreter.execute(g);
			while (rc == PrologCode.SUCCESS || rc == PrologCode.SUCCESS_LAST) {
				valoracion.add(trataFrase(x.toString()));
				x.dereference();
				rc = interpreter.execute(g);
			} 
		} catch (Exception e) {}
	}
	
	@Override
	public String genera_frase() {
		Random r = new Random();
		int rand = r.nextInt(100000) % valoracion.size();
		
		String string_frase_saludo = valoracion.get(rand);

		return string_frase_saludo + ". ¿Quieres valorarla?";
	}

}
