package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

/**
 * <p>Title: Agenda de citas vocal</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Telef�nica  I+D</p>
 * @author Jorge Gonz�lez
 * @version 1.0
 */

import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerInfoSesion;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerIngredientesFavoritos;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserSession;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoConexion;
import icaro.aplicaciones.recursos.web.ItfUsoComunicacionWeb;
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
		this.getEnvioHechos().insertarHecho(sesion);
		this.generarInformeOK(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Sesion del usuario " + interlocutor.getUserName() + " registrada.");
	}

}
