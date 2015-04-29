package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

import java.util.List;
import java.util.Map;

import icaro.aplicaciones.informacion.dominioRecipe2Me.Criterio;
import icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones.InformacionExtraida;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeDelUsuario;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

public class TareaObtenerIngredientesFavoritos extends TareaSincrona{
	
	public ItfUsoRepositorioInterfaces repoIntfaces;
	private ItfUsoExtractorSemantico itfUsoExtractorSemantico;
	private ItfUsoComunicacionWeb itfUsComunicacionoWeb;
	
	public TareaObtenerIngredientesFavoritos(){
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
	public void ejecutar(Object... params) {//Params[0] sera el evento de tipo EventoMensajeUsuario
		// TODO Auto-generated method stub
		try {
			EventoMensajeDelUsuario mensaje=(EventoMensajeDelUsuario) params[0];
			String contenido = mensaje.getMensaje();
			InformacionExtraida informacionExtraida;
			informacionExtraida=itfUsoExtractorSemantico.extraerAnotaciones(contenido);
			Map<String, List<String>> anotaciones = informacionExtraida.getInformacionPorAnotacion();
			List<String> ingredientes = anotaciones.get("Ingrediente");
			String msg="";
			if(ingredientes!=null){
				if(ingredientes.isEmpty()){
					msg = "No has introducido ningún ingrediente,por favor, dime tus ingredientes favoritos";
					itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
				}
				else{
					Criterio criterio = new Criterio();
					criterio.setPositivo(ingredientes);
					this.getEnvioHechos().insertarHechoWithoutFireRules(criterio);
					msg = "Muy bien, ahora dime los ingredientes que no quieres que aparezcan";
					itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
					this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_Ingredientes_Fav");
				}
			}
			else{
				msg = "No has introducido ningún ingrediente,por favor, dime tus ingredientes favoritos";
				itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
