package icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologHelloSentences.prologHelloKnownUser;

import gnu.prolog.term.AtomTerm;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.term.Term;
import gnu.prolog.term.VariableTerm;
import gnu.prolog.vm.Environment;
import gnu.prolog.vm.Interpreter;
import gnu.prolog.vm.Interpreter.Goal;
import gnu.prolog.vm.PrologCode;
import icaro.aplicaciones.recursos.sentenceGenerator.prologSentences.prologHelloSentences.PrologHelloSentence;

import java.util.ArrayList;
import java.util.Random;

public class PrologHelloKnownUserSentence extends PrologHelloSentence {
	
	public PrologHelloKnownUserSentence(String user) {
		try {
			Environment env = new Environment();

			// get the filename relative to the class file
			env.ensureLoaded(AtomTerm.get("frase_saludo_usuario.pl"));

			// Get the interpreter.
			Interpreter interpreter = env.createInterpreter();
			// Run the initialization
			env.runInitialization(interpreter);

			VariableTerm x = new VariableTerm("X");
			AtomTerm t = AtomTerm.get(user);
			Term [] ar = {x, t};

			CompoundTerm goalTerm = new CompoundTerm(AtomTerm.get("frase_saludo_usuario_conocido"), ar);		
			Goal g = interpreter.prepareGoal(goalTerm);
			saludos = new ArrayList<String>();

			int rc = interpreter.execute(g);
			while (rc == PrologCode.SUCCESS || rc == PrologCode.SUCCESS_LAST) {
				saludos.add(trataFrase(x.toString()));
				x.dereference();
				rc = interpreter.execute(g);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String genera_frase() {
		Random r = new Random();
		int rand = r.nextInt(100000) % saludos.size();
		
		String string_frase_saludo = saludos.get(rand);
		
		r = new Random();
		rand = r.nextInt(100000) % recomendaciones.size();
		
		String string_recomendacion = recomendaciones.get(rand);
		

		return string_frase_saludo + ". " + string_recomendacion;
	}

}
