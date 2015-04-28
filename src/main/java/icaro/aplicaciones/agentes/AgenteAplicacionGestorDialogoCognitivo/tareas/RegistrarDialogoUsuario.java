package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

/**
 * <p>Title: Agenda de citas vocal</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Telef�nica  I+D</p>
 * @author Jorge Gonz�lez
 * @version 1.0
 */

import icaro.aplicaciones.informacion.dominioRecipe2Me.Recipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserSession;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoConexion;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.persistenciaMongo.ItfUsoPersistenciaMongo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

public class RegistrarDialogoUsuario extends TareaSincrona {

	/**
	 * Constructor
	 *
	 * @param Description
	 *            of the Parameter
	 * @param Description
	 *            of the Parameter
	 */
	private Objetivo contextoEjecucionTarea = null;

	@Override
	public void ejecutar(Object... params) {
		String identDeEstaTarea = this.getIdentTarea();
		String identAgenteOrdenante = this.getIdentAgente();
		EventoConexion evento = (EventoConexion) params[0];
		UserProfile interlocutor = evento.getUser();
		UserSession sesion = new UserSession(interlocutor.getUserName());
		//Controla si ya ha realizado el formulario inicial
		sesion.setFirst(!interlocutor.isInit());
		
		try {
			ItfUsoComunicacionWeb web = (ItfUsoComunicacionWeb) this.repoInterfaces.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoComunicacionWeb);
			ItfUsoPersistenciaMongo mongo = (ItfUsoPersistenciaMongo) this.repoInterfaces.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoPersistenciaMongo);
			Recipe recipe = mongo.findOne("54f8edb18c30d0033cd70078");
			web.enviarRecetaAlUsuario("Prueba de recepcion de receta", recipe, interlocutor.getUserName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getEnvioHechos().insertarHecho(sesion);
		this.generarInformeOK(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Sesion del usuario " + interlocutor.getUserName() + " registrada.");
	}

}
