package icaro.aplicaciones.informacion.dominioRecipe2Me.eventos;

import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;

public class EventoDesconexion {

	private UserProfile user;
	
	public EventoDesconexion(UserProfile user) {
		this.user = user;
	}

	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}
}
