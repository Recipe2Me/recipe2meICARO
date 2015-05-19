package icaro.aplicaciones.informacion.dominioRecipe2Me;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryRecipe {
	
	private List<String> ingredientes;
	private List<String> alergias;
	private boolean sabeCocinar;
	private Map<String,Double> ingredientesPuntuados;
	
	public QueryRecipe(UserProfile perfil) {
		ingredientes = new ArrayList<String>();
		alergias = perfil.getAlergias();
		sabeCocinar = perfil.isSabeCocinar();
		ingredientesPuntuados = perfil.getGusto();
	}

	public List<String> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<String> ingredientes) {
		this.ingredientes = ingredientes;
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

	public Map<String, Double> getIngredientesPuntuados() {
		return ingredientesPuntuados;
	}

	public void setIngredientesPuntuados(Map<String, Double> ingredientesPuntuados) {
		this.ingredientesPuntuados = ingredientesPuntuados;
	}

	
}
