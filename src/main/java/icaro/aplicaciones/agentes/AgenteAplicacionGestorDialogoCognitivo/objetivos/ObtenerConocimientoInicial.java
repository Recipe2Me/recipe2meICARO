package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class ObtenerConocimientoInicial extends Objetivo {
	
	public Objetivo subFocus = null;
	
	public boolean isComplete = false;
	
	public List<Objetivo> subObjetivos;
	
	public ObtenerConocimientoInicial() {
        super.setgoalId("ObtenerConocimientoInicial");
        subObjetivos = new ArrayList<Objetivo>();
        subObjetivos.add(new ObtenerIngredientesFavoritos());
        subObjetivos.add(new ObtenerIngredientesOdiados());
        subObjetivos.add(new ObtenerNivelCocina());
        subObjetivos.add(new ObtenerAlergia());
        subFocus = new Objetivo();
    }
	
	public Objetivo getSubObjetivoAleatorio() {
		Objetivo next = new Objetivo();
		if (subFocus.getState() == Objetivo.SOLVED) {
			subObjetivos.remove(subFocus);
		}
		if (subObjetivos.isEmpty()) {
			isComplete = true;
		} else {
			Random rand = new Random();
			int randomNum = rand.nextInt(1000) % subObjetivos.size();
			next = subObjetivos.get(randomNum);
		}
		subFocus = next;
		return next;
	}

	public Objetivo getSubFocus() {
		return subFocus;
	}

	public void setSubFocus(Objetivo subFocus) {
		this.subFocus = subFocus;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public List<Objetivo> getSubObjetivos() {
		return subObjetivos;
	}

	public void setSubObjetivos(List<Objetivo> subObjetivos) {
		this.subObjetivos = subObjetivos;
	}
	
	
}
