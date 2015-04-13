package icaro.aplicaciones.agentes.AgenteAplicacionGestorConocimientoInicialCognitivo.tareas;

/**
 * <p>Title: Agenda de citas vocal</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Telef�nica  I+D</p>
 * @author Jorge Gonz�lez
 * @version 1.0
 */

import icaro.aplicaciones.agentes.AgenteAplicacionGestorConocimientoInicialCognitivo.objetivos.ObtenerInfoInterlocutor;
import icaro.aplicaciones.informacion.dominioRecipe2Me.ControlEstadoConocimientoInicial;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeHaciaUsuario;
import icaro.aplicaciones.recursos.web.ItfUsoComunicacionWeb;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

public class SaludoInicial extends TareaSincrona {

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
		String user = (String) params[0];
		ControlEstadoConocimientoInicial estado = new ControlEstadoConocimientoInicial(user);
		this.getEnvioHechos().insertarHecho(estado);
		String mensaje = VocabularioRecipe2Me.SaludoInicial2
				+ "  " + user + "  "
				+ VocabularioRecipe2Me.InfoGeneralFuncionalidad;
		this.comunicator.enviarInfoAotroAgente(new EventoMensajeHaciaUsuario(user, mensaje), VocabularioRecipe2Me.IdentAgenteAplicacionGestorDialogo);
	}

}
