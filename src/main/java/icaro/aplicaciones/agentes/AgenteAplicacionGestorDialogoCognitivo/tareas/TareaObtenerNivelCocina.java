package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

import icaro.aplicaciones.informacion.dominioRecipe2Me.DialogoInicial;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones.InformacionExtraida;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeDelUsuario;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.aplicaciones.recursos.persistenciaMongo.ItfUsoPersistenciaMongo;
import icaro.aplicaciones.recursos.persistenciaMongo.imp.PersistenciaAccesoMongo;
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
			DialogoInicial dialogo = (DialogoInicial) params[1];
			String contenido = mensaje.getMensaje();
			InformacionExtraida informacionExtraida;
			informacionExtraida=itfUsoExtractorSemantico.extraerAnotaciones(contenido);
			Map<String, List<String>> anotaciones = informacionExtraida.getInformacionPorAnotacion();
			List<String> negativo = anotaciones.get("Negacion");//OJO cambiar por la anotaci�n correcta
			//GUARDA Y ACTUALIZA LOS DATOS DEL USUARIO
			ItfUsoPersistenciaMongo mongo = (ItfUsoPersistenciaMongo) this.repoInterfaces.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoPersistenciaMongo);
			UserProfile auxUsuario = mongo.getUserByeUsername(mensaje.getUser());
			PersistenciaAccesoMongo ds = new PersistenciaAccesoMongo(); 
			//GUARDA Y ACTUALIZA LOS DATOS DEL USUARIO
			String msg="";
			if(negativo!=null){
				if(negativo.isEmpty()){
					dialogo.setSabeCocinar(true);
					msg = "Me alegra saber que tienes cierto nivel de cocina, eso ampliar� el abanico de recetas."
							+ "Ahora dime los ingredientes que quieres que aparezcan en la receta.";
					itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
					this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_NivelCocina");
					//GUARDA Y ACTUALIZA LOS DATOS DEL USUARIO
					auxUsuario.setSabeCocinar(true);
					auxUsuario.setAlergias(dialogo.getAlergias());
					auxUsuario.setGusto(dialogo.getIngredientesFavoritos());
					auxUsuario.setNoGusto(dialogo.getIngredientesOdiados());
				}
				else{
					dialogo.setSabeCocinar(false);
					msg = "No te preocupes, te recomendar� una receta de preparaci�n m�s sencilla."
							+ "Ahora dime los ingredientes que quieres que aparezcan en la receta.";
					itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
					this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_NivelCocina");
					//GUARDA Y ACTUALIZA LOS DATOS DEL USUARIO
					auxUsuario.setSabeCocinar(false);
					auxUsuario.setAlergias(dialogo.getAlergias());
					auxUsuario.setGusto(dialogo.getIngredientesFavoritos());
					auxUsuario.setNoGusto(dialogo.getIngredientesOdiados());
				}
			}
			else{
				msg = "Me alegra saber que tienes cierto nivel de cocina, eso ampliar� el abanico de recetas."
						+ "Ahora dime los ingredientes que quieres que aparezcan en la receta.";
				itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
				this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_NivelCocina");
				//GUARDA Y ACTUALIZA LOS DATOS DEL USUARIO
				auxUsuario.setSabeCocinar(true);
				auxUsuario.setAlergias(dialogo.getAlergias());
				auxUsuario.setGusto(dialogo.getIngredientesFavoritos());
				auxUsuario.setNoGusto(dialogo.getIngredientesOdiados());
			}
			
			ds.saveUserUpdate(auxUsuario);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
