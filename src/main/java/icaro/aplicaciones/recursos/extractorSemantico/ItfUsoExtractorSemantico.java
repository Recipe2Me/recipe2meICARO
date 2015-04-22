package icaro.aplicaciones.recursos.extractorSemantico;

import gate.AnnotationSet;
import icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones.InformacionExtraida;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

import java.util.List;
import java.util.HashSet;

public interface ItfUsoExtractorSemantico extends ItfUsoRecursoSimple {
	public InformacionExtraida extraerAnotaciones(String textoUsuario)throws Exception;
}