package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;

import java.rmi.RemoteException;
import java.util.Collection;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.AdaptadorRegRMI;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;




public class PeticionTerminacion extends TareaSincrona {
//        private String identDeEstaTarea= "PermitirAcceso";
        private String identAgenteOrdenante;
        private Objetivo contextoEjecucionTarea = null;
//        private String identRecursoVisualizacionAcceso = "VisualizacionAcceso1";
	@Override
	public void ejecutar(Object... params) {
	
	// Cerramos el visualizador y pedimos al gestor de agentes que termine
             String identDeEstaTarea=getClass().getSimpleName();
             Collection<Object> memoria = this.getItfMotorDeReglas().getStatefulKnowledgeSession().getObjects();
             for (Object objeto : memoria) {
            	 this.getEnvioHechos().eliminarHecho(objeto);
             }
             /*try {
				this.getAgente().termina();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		
	}



}
