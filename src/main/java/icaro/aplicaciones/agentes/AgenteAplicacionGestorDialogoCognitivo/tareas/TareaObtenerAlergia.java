package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

import icaro.aplicaciones.informacion.dominioRecipe2Me.DialogoInicial;
import icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones.InformacionExtraida;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeDelUsuario;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.aplicaciones.recursos.sentenceGenerator.SentenceFactory;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

import java.util.List;
import java.util.Map;

public class TareaObtenerAlergia extends TareaSincrona{
	
	public ItfUsoRepositorioInterfaces repoIntfaces;
	private ItfUsoExtractorSemantico itfUsoExtractorSemantico;
	private ItfUsoComunicacionWeb itfUsComunicacionoWeb;
	
	public TareaObtenerAlergia(){
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
			DialogoInicial dialogo = (DialogoInicial) params[1];
			String contenido = mensaje.getMensaje();
			InformacionExtraida informacionExtraida;
			informacionExtraida=itfUsoExtractorSemantico.extraerAnotaciones(contenido);
			Map<String, List<String>> anotaciones = informacionExtraida.getInformacionPorAnotacion();
			List<String> negativo = anotaciones.get("Negacion");//OJO cambiar por la anotaci�n correcta
			String msg="";
			if(negativo!=null){
				if(negativo.isEmpty()){				
					List<String> ingredientes = anotaciones.get("Ingrediente");
					if(ingredientes!=null){
						dialogo.setAlergias(ingredientes);
						msg="Veo que eres al�rgico a:";
					    for(int i=0;i<ingredientes.size();i++){
						     String ingr = ingredientes.get(i);
						     msg=msg+" "+ingr;
						
					     }
					     this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_Alergia");
					}
					else{
						msg="El ingrediente no est� contemplado en mis recetas, puedes estar tranquilo.";
						itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
						this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_Alergia");
					}					
				}
				else{
					msg = "Muy bien, veo que estas mas sano que una manzana jejeje.";
					itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
					this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_Alergia");
				}
			}
			else{
				List<String> ingredientes = anotaciones.get("Ingrediente");				
				if(ingredientes!=null){
					dialogo.setAlergias(ingredientes);
					msg="Veo que eres al�rgico a:";
				    for(int i=0;i<ingredientes.size();i++){
					     String ingr = ingredientes.get(i);
					     msg=msg+" "+ingr;
					
				     }
				     itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
				     this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_Alergia");
				}
				else{
					msg="El ingrediente no est� contemplado en mis recetas, puedes estar tranquilo."
							+ "Por cierto, �se te da bien cocinar?";
					itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
					this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_Alergia");
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
