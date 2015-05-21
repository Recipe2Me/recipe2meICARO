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

public class TareaActualizarPerfilUsuario extends TareaSincrona{
	
	public ItfUsoRepositorioInterfaces repoIntfaces;
	private ItfUsoExtractorSemantico itfUsoExtractorSemantico;
	private ItfUsoComunicacionWeb itfUsComunicacionoWeb;
	
	public TareaActualizarPerfilUsuario(){
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
		
	}

}
