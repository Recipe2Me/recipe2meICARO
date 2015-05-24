package icaro.aplicaciones.informacion.dominioRecipe2Me;

import java.util.List;

public class DialogoInicial {
	
	private List<String> ingredientesFavoritos;
	private List<String> ingredientesOdiados;
	private List<String> alergias;
	private boolean sabeCocinar;

	public DialogoInicial() {
		// TODO Auto-generated constructor stub
	}

	public List<String> getIngredientesFavoritos() {
		return ingredientesFavoritos;
	}

	public void setIngredientesFavoritos(List<String> ingredientesFavoritos) {
		this.ingredientesFavoritos = ingredientesFavoritos;
	}

	public List<String> getIngredientesOdiados() {
		return ingredientesOdiados;
	}

	public void setIngredientesOdiados(List<String> ingredientesOdiados) {
		this.ingredientesOdiados = ingredientesOdiados;
	}

	public List<String> getAlergias() {
		return alergias;
	}

	public void setAlergias(List<String> alergias) {
		this.alergias = alergias;
	}

	public boolean isSabeCocinar() {
		return sabeCocinar;
	}

	public void setSabeCocinar(boolean sabeCocinar) {
		this.sabeCocinar = sabeCocinar;
	}
	
	
}
