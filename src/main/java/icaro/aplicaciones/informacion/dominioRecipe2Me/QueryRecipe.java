package icaro.aplicaciones.informacion.dominioRecipe2Me;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

public class QueryRecipe {
	
	private List<String> ingredientes;
	private List<String> alergias;
	private boolean sabeCocinar;
	private Map<String,Double> gusto;
	private List<ObjectId> listaRecetasRechazadas;
	private List<Recipe> listaRecomendaciones;
	
	public QueryRecipe(UserProfile perfil) {
		ingredientes = new ArrayList<String>();
		alergias = perfil.getAlergias();
		if (alergias==null)
			alergias = new ArrayList<String>();
		sabeCocinar = perfil.isSabeCocinar();
		gusto = perfil.getGusto();
		listaRecetasRechazadas = perfil.getRecetasNoGusta();
		listaRecomendaciones = new ArrayList<Recipe>();
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

	public Map<String, Double> getGusto() {
		return gusto;
	}

	public void setGusto(Map<String, Double> gusto) {
		this.gusto = gusto;
	}

	public List<ObjectId> getListaRecetasRechazadas() {
		return listaRecetasRechazadas;
	}

	public void setListaRecetasRechazadas(List<ObjectId> listaRecetasRechazadas) {
		this.listaRecetasRechazadas = listaRecetasRechazadas;
	}

	public List<Recipe> getListaRecomendaciones() {
		return listaRecomendaciones;
	}

	public void setListaRecomendaciones(List<Recipe> listaRecomendaciones) {
		this.listaRecomendaciones = listaRecomendaciones;
	}
	
}
