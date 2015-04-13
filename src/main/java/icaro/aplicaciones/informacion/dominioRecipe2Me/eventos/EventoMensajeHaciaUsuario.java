package icaro.aplicaciones.informacion.dominioRecipe2Me.eventos;

public class EventoMensajeHaciaUsuario {

	private String user;
	private String mensaje;
	
	public EventoMensajeHaciaUsuario(String user, String mensaje) {
		this.user = user;
		this.mensaje = mensaje;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
