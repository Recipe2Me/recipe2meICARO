package icaro.aplicaciones.informacion.dominioRecipe2Me.eventos;

import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;

public class ValoracionUsuario {
	private String user;
	private Double valoracion;

	public ValoracionUsuario(String user, Double valoracion) {
		super();
		this.user = user;
		this.valoracion = valoracion;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Double getValoracion() {
		return valoracion;
	}

	public void setValoracion(Double valoracion) {
		this.valoracion = valoracion;
	}


}
