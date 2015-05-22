package icaro.aplicaciones.informacion.dominioRecipe2Me.eventos;

import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;

public class DecisionUsuario {
	private String user;
	private Decisiones decision;

	public DecisionUsuario(String user, Decisiones decision) {
		super();
		this.user = user;
		this.decision = decision;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Decisiones getDecision() {
		return decision;
	}

	public void setDecision(Decisiones decision) {
		this.decision = decision;
	}

}
