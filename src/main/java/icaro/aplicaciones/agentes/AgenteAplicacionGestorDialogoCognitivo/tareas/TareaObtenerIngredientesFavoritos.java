package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import icaro.aplicaciones.informacion.dominioRecipe2Me.Criterio;
import icaro.aplicaciones.informacion.dominioRecipe2Me.DialogoInicial;
import icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones.InformacionExtraida;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeDelUsuario;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.aplicaciones.recursos.sentences.questionSentences.hated.HatedIngredientsQuestionSentence;
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
			DialogoInicial dialogo = (DialogoInicial) params[1];
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
					dialogo.setIngredientesFavoritos(ingredientes);
					List<String> favoritos=dialogo.getIngredientesFavoritos();
					List<String> odiados=dialogo.getIngredientesOdiados();
					ArrayList<String> errores=new ArrayList<String>();
					boolean control=false;
					if(odiados!=null){
						for(int i=0;i<favoritos.size();i++){
							String ingFav=favoritos.get(i);
							for(int j=0;j<odiados.size();j++){
								if(ingFav.equalsIgnoreCase(odiados.get(j))){
									errores.add(ingFav);
									control=true;
								}
							}
						}
					}
					if(control==false){
						msg = "Muy bien, ahora dime los ingredientes que no te gustan";
						itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
						this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_Ingredientes_Od");
					}
					else{
						msg="Me has dicho que no te gustaban los siguientes ingredientes :";
						for(int i=0;i<errores.size();i++){
							String ing=errores.get(i);
							msg = msg+" "+ing;
						}
						msg = msg+", ¿Me puedes repetir los ingredientes que te gustan?";
						itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
					}
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
