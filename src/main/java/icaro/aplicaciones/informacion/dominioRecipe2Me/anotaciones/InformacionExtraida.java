package icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones;

import gate.Annotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InformacionExtraida {

	private String textoOriginal;
	private List<AnotacionInformacion> paresOrdenados; 
	private Map<String,List<String>> informacionPorAnotacion;
	
	public InformacionExtraida(String texto) {
		this.textoOriginal = texto;
		this.paresOrdenados = new ArrayList<AnotacionInformacion>();
		this.informacionPorAnotacion = new HashMap<String,List<String>>();
	}

	public String getTextoOriginal() {
		return textoOriginal;
	}

	public void setTextoOriginal(String textoOriginal) {
		this.textoOriginal = textoOriginal;
	}

	public List<AnotacionInformacion> getParesOrdenados() {
		return paresOrdenados;
	}

	public void setParesOrdenados(List<AnotacionInformacion> paresOrdenados) {
		this.paresOrdenados = paresOrdenados;
	}

	public Map<String, List<String>> getInformacionPorAnotacion() {
		return informacionPorAnotacion;
	}

	public void setInformacionPorAnotacion(
			Map<String, List<String>> informacionPorAnotacion) {
		this.informacionPorAnotacion = informacionPorAnotacion;
	}

	public void obtenerInformacionDeAnotaciones(Set<Annotation> anotaciones) {
		int orden = 1;
		int finAnterior = Integer.MIN_VALUE; 
		for (Annotation anotacion : anotaciones) {
			int posicionComienzoTexto = anotacion.getStartNode().getOffset().intValue();
	        int posicionFinTexto = anotacion.getEndNode().getOffset().intValue();
	        if (finAnterior<posicionFinTexto)
	        	finAnterior = posicionFinTexto;
	        else if (finAnterior==posicionFinTexto)
	        	continue;
	        String informacion = textoOriginal.substring(posicionComienzoTexto, posicionFinTexto);
	        AnotacionInformacion par = new AnotacionInformacion(anotacion.getType(), informacion, orden, posicionComienzoTexto);
	        paresOrdenados.add(par);
	        if (informacionPorAnotacion.containsKey(anotacion.getType())) {
	        	informacionPorAnotacion.get(anotacion.getType()).add(informacion);
	        } else {
	        	List<String> listaInformacion = new ArrayList<String>();
	        	listaInformacion.add(informacion);
	        	informacionPorAnotacion.put(anotacion.getType(), listaInformacion);
	        }
	        orden++;
		}
	}

	@Override
	public String toString() {
		return "InformacionExtraida [Anotaciones="
				+ informacionPorAnotacion + "]";
	}
	
	
}
