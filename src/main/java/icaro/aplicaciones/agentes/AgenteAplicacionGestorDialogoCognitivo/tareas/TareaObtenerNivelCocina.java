package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

import icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones.InformacionExtraida;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeDelUsuario;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

import java.util.List;
import java.util.Map;

public class TareaObtenerNivelCocina extends TareaSincrona{
	
	public ItfUsoRepositorioInterfaces repoIntfaces;
	private ItfUsoExtractorSemantico itfUsoExtractorSemantico;
	private ItfUsoComunicacionWeb itfUsComunicacionoWeb;
	
	public TareaObtenerNivelCocina(){
		this.repoIntfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
		//Definimos el recurso extractor semantico
		try {
			itfUsoExtractorSemantico = (ItfUsoExtractorSemantico) this.repoIntfaces
					.obtenerInterfazUso("ExtractorSemantico1");
			itfUsComunicacionoWeb = (ItfUsoComunicacionWeb) this.repoIntfaces
					.obtenerInterfazUso("ComunicacionWeb1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void ejecutar(Object... params) {
		// TODO Auto-generated method stub
		try {
			EventoMensajeDelUsuario mensaje=(EventoMensajeDelUsuario) params[0];
			String contenido = mensaje.getMensaje();
			InformacionExtraida informacionExtraida;
			informacionExtraida=itfUsoExtractorSemantico.extraerAnotaciones(contenido);
			Map<String, List<String>> anotaciones = informacionExtraida.getInformacionPorAnotacion();
			List<String> negativo = anotaciones.get("Negacion");//OJO cambiar por la anotaci�n correcta
			String msg="";
			if(negativo!=null){
				if(negativo.isEmpty()){
					msg = "Me alegra saber que tienes cierto nivel de cocina, eso ampliar� el abanico de recetas."
							+ "Ahora dime los ingredientes que quieres que aparezcan en la receta.";
					itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
					this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_NivelCocina");
				}
				else{
					msg = "No te preocupes, te recomendar� una receta de preparaci�n m�s sencilla."
							+ "Ahora dime los ingredientes que quieres que aparezcan en la receta.";
					itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
					this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_NivelCocina");
				}
			}
			else{
				msg = "Me alegra saber que tienes cierto nivel de cocina, eso ampliar� el abanico de recetas."
						+ "Ahora dime los ingredientes que quieres que aparezcan en la receta.";
				itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
				this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_NivelCocina");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}