package icaro.aplicaciones.agentes.AgenteAplicacionGestorConocimientoInicialCognitivo.tareas;

/**
 * <p>Title: Agenda de citas vocal</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Telef�nica  I+D</p>
 * @author Jorge Gonz�lez
 * @version 1.0
 */

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gate.Annotation;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorConocimientoInicialCognitivo.objetivos.ObtenerInfoInterlocutor;
import icaro.aplicaciones.informacion.dominioRecipe2Me.ControlEstadoConocimientoInicial;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones.InformacionExtraida;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeDelUsuario;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeHaciaUsuario;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.aplicaciones.recursos.web.ItfUsoComunicacionWeb;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;

public class SegundoMensaje extends TareaSincrona {

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
		EventoMensajeDelUsuario evento = (EventoMensajeDelUsuario) params[1];
		Set<String> tiposAnotacionesRelevantes = new HashSet<String>();
        tiposAnotacionesRelevantes.add("Saludo");
        tiposAnotacionesRelevantes.add("Negacion");
        tiposAnotacionesRelevantes.add("Afirmacion");
        tiposAnotacionesRelevantes.add("Funcion");
        tiposAnotacionesRelevantes.add("Ingrediente");
        tiposAnotacionesRelevantes.add("Despedida");
		String mensaje = VocabularioRecipe2Me.RespuestaNoEniendo1
				+ "  " + user;
		InformacionExtraida informacionExtraida;
		try {
			ItfUsoExtractorSemantico extractor = (ItfUsoExtractorSemantico) repoInterfaces.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoExtractorSemantico);
			informacionExtraida = extractor.extraerAnotaciones(evento.getMensaje());
			this.trazas.aceptaNuevaTraza(new InfoTraza(identDeEstaTarea, informacionExtraida.toString(), NivelTraza.info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.comunicator.enviarInfoAotroAgente(new EventoMensajeHaciaUsuario(user, mensaje), VocabularioRecipe2Me.IdentAgenteAplicacionGestorDialogo);
	}

}
