package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

import icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones.InformacionExtraida;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeDelUsuario;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaAsincrona;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

import java.util.List;
import java.util.Map;

public class TareaObtenerIngredienteOPlato extends TareaAsincrona{
	public ItfUsoRepositorioInterfaces repoIntfaces;
	private ItfUsoExtractorSemantico itfUsoExtractorSemantico;
	private ItfUsoComunicacionWeb itfUsComunicacionoWeb;
	
	public TareaObtenerIngredienteOPlato(){
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
			List<String> ingredientes = anotaciones.get("Ingrediente");//OJO cambiar por la anotación correcta
			String msg="";
			if(ingredientes!=null){
				if(ingredientes.isEmpty()){
					msg="Lo siento, para que pueda recomendarle algo necesito que me digas "
							+ "los ingredientes que deseas tomar";
					itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());					
				}
				else{
					msg = "Muy bien, veo que has elegido los ingredientes :";
					for(int i=0;i<ingredientes.size();i++){
						String ingr = ingredientes.get(i);
						msg=msg+" "+ingr;
						
					}
					itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
					this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_ObtenerIngredientesOPlato");
				}
			}
			else{
				msg="Lo siento, para que pueda recomendarle algo necesito que me digas "
						+ "los ingredientes que deseas tomar";
				itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
