package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

import icaro.aplicaciones.informacion.dominioRecipe2Me.DialogoInicial;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserSession;
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
	private ItfUsoComunicacionWeb itfUsComunicacionoWeb;
	private ItfUsoPersistenciaMongo itfPersistenciaMongo;
	
	public TareaActualizarPerfilUsuario(){
		this.repoIntfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
		//Definimos el recurso extractor semantico
		try {
			itfPersistenciaMongo = (ItfUsoPersistenciaMongo) this.repoIntfaces
					.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoPersistenciaMongo);
			itfUsComunicacionoWeb = (ItfUsoComunicacionWeb) this.repoIntfaces
					.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoComunicacionWeb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void ejecutar(Object... params) {
		DialogoInicial dialogo = (DialogoInicial) params[0];
		UserSession session = (UserSession) params[1];
		UserProfile user = (UserProfile) params[2];
		
		user.setAlergias(dialogo.getAlergias());
		user.setSabeCocinar(dialogo.isSabeCocinar());
		user.setGusto(dialogo.getIngredientesFavoritos());
		user.setNoGusto(dialogo.getIngredientesOdiados());
		user.setInit(true);
		session.setFirst(false);
		try {
			itfPersistenciaMongo.actualizarUsuario(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
