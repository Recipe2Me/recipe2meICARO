package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

import icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones.InformacionExtraida;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeDelUsuario;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

public class TareaObtenerIngredientesFavoritos extends TareaSincrona{
	
	public ItfUsoRepositorioInterfaces repoIntfaces;
	private ItfUsoExtractorSemantico itfUsoExtractorSemantico;
	
	public TareaObtenerIngredientesFavoritos(){
		this.repoIntfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
		//Definimos el recurso extractor semantico
		try {
			itfUsoExtractorSemantico = (ItfUsoExtractorSemantico) this.repoIntfaces
					.obtenerInterfazUso("ExtractorSemantico1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void ejecutar(Object... params) {//Params[0] sera el evento de tipo EventoMensajeUsuario
		// TODO Auto-generated method stub
		try {
			EventoMensajeDelUsuario mensaje=(EventoMensajeDelUsuario) params[0];
			String contenido = mensaje.getMensaje();
			InformacionExtraida informacionExtraida;
			informacionExtraida=itfUsoExtractorSemantico.extraerAnotaciones(contenido);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
