package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

import java.util.List;
import java.util.Map;

import icaro.aplicaciones.informacion.dominioRecipe2Me.Criterio;
import icaro.aplicaciones.informacion.dominioRecipe2Me.Recipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones.InformacionExtraida;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeDelUsuario;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.aplicaciones.recursos.persistenciaMongo.ItfUsoPersistenciaMongo;
import icaro.aplicaciones.recursos.sentenceGenerator.SentenceFactory;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

public class TareaObtenerIngredientesOdiados extends TareaSincrona{
	
	public ItfUsoRepositorioInterfaces repoIntfaces;
	private ItfUsoExtractorSemantico itfUsoExtractorSemantico;
	private ItfUsoComunicacionWeb itfUsComunicacionoWeb;
	
	public TareaObtenerIngredientesOdiados(){
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
			Criterio criterio = (Criterio) params[1];
			String contenido = mensaje.getMensaje();
			InformacionExtraida informacionExtraida;
			informacionExtraida=itfUsoExtractorSemantico.extraerAnotaciones(contenido);
			Map<String, List<String>> anotaciones = informacionExtraida.getInformacionPorAnotacion();
			List<String> ingredientes = anotaciones.get("Ingrediente");
			String msg="";
			if(ingredientes!=null){
				if(ingredientes.isEmpty()){
					msg = SentenceFactory.generateIngredientsComplain();
					itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
				}
				else{
					criterio.setNegativo(ingredientes);
					msg = "Te he encontrado esta receta";
					ItfUsoPersistenciaMongo mongo = (ItfUsoPersistenciaMongo) this.repoInterfaces.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoPersistenciaMongo);
					List<Recipe> recipes = mongo.getRecipeWithCriteria(criterio.getPositivo(), criterio.getNegativo());
					itfUsComunicacionoWeb.enviarRecetaAlUsuario(msg,recipes.get(0),mensaje.getUser());
					this.generarInformeOK(getIdentTarea(),null,getIdentAgente(),"Zanjar_Ingredientes_Od");
				}
			}
			else{
				msg = SentenceFactory.generateIngredientsComplain();
				itfUsComunicacionoWeb.enviarMensageAlUsuario(msg,mensaje.getUser());
			}
			ItfUsoPersistenciaMongo mongo = (ItfUsoPersistenciaMongo) this.repoInterfaces.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoPersistenciaMongo);
			Recipe recipe = mongo.findOne("54f8edb18c30d0033cd70078");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
