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
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerInfoSesion;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerIngredientesFavoritos;
import icaro.aplicaciones.informacion.dominioRecipe2Me.DialogoInicial;
import icaro.aplicaciones.informacion.dominioRecipe2Me.Message;
import icaro.aplicaciones.informacion.dominioRecipe2Me.QueryRecipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserSession;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoConexion;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.persistenciaMongo.ItfUsoPersistenciaMongo;
import icaro.aplicaciones.recursos.sentenceGenerator.SentenceFactory;
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
		String mensaje = "";
		//Controla si ya ha realizado el formulario inicial
		sesion.setFirst(!interlocutor.isInit());
		if (sesion.isFirst()) {
			//La primera vez enviamos un saludo de presentacion y introducimos el objeto 
			//donde almacenar el dialogo inicial mientras lo vamos recopilando
			mensaje = SentenceFactory.generateHelloUnknownUser();
			this.getEnvioHechos().insertarHechoWithoutFireRules(new DialogoInicial());
		} else {
			//La proximas veces enviamos un saludo y introducimos el objeto 
			//donde almacenar los datos para la consulta
			mensaje = SentenceFactory.generateHelloKnownUser(interlocutor.getUsername());
			this.getEnvioHechos().insertarHechoWithoutFireRules(new QueryRecipe(interlocutor));
		}
		sesion.messages.add(new Message(mensaje));
		//Introducimos los objetios en la memoria de trabajo
		this.getEnvioHechos().insertarHechoWithoutFireRules(interlocutor);
		this.getEnvioHechos().insertarHechoWithoutFireRules(sesion);
		
		try {
			ItfUsoComunicacionWeb web = (ItfUsoComunicacionWeb) this.repoInterfaces.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoComunicacionWeb);
			web.enviarMensageAlUsuario(mensaje, interlocutor.getUserName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//this.getEnvioHechos().insertarHecho(sesion);
		this.generarInformeOK(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Sesion_Registrada.");
	}

}
