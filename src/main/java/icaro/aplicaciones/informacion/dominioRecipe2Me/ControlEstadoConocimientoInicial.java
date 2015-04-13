package icaro.aplicaciones.informacion.dominioRecipe2Me;

import java.util.ArrayList;
import java.util.List;

public class ControlEstadoConocimientoInicial {

	private String user;
	private Integer step = 0;
	private List<String> ingredientes = new ArrayList<String>();
	private List<String> alergiaIngredientes = new ArrayList<String>();
	private List<String> enfermedades = new ArrayList<String>();
	private Integer conocimientosCocina = 0;
	
	public ControlEstadoConocimientoInicial(String user) {
		
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public List<String> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<String> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public List<String> getAlergiaIngredientes() {
		return alergiaIngredientes;
	}

	public void setAlergiaIngredientes(List<String> alergiaIngredientes) {
		this.alergiaIngredientes = alergiaIngredientes;
	}

	public List<String> getEnfermedades() {
		return enfermedades;
	}

	public void setEnfermedades(List<String> enfermedades) {
		this.enfermedades = enfermedades;
	}

	public Integer getConocimientosCocina() {
		return conocimientosCocina;
	}

	public void setConocimientosCocina(Integer conocimientosCocina) {
		this.conocimientosCocina = conocimientosCocina;
	}
	
	
}
