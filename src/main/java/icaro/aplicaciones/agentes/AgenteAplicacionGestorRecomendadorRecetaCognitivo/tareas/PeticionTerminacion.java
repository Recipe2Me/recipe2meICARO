package icaro.aplicaciones.agentes.AgenteAplicacionGestorRecomendadorRecetaCognitivo.tareas;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.AdaptadorRegRMI;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;




public class PeticionTerminacion extends Tarea {
//        private String identDeEstaTarea= "PermitirAcceso";
        private String identAgenteOrdenante;
        private Objetivo contextoEjecucionTarea = null;
//        private String identRecursoVisualizacionAcceso = "VisualizacionAcceso1";
	@Override
	public void ejecutar(Object... params) {
	
	// Cerramos el visualizador y pedimos al gestor de agentes que termine
             String identDeEstaTarea=getClass().getSimpleName();
             String identRecursoVisualizacionAcceso = (String)params[0];
               try {
                  
		} catch (Exception e) {
			// TODO Auto-generated catch block
                      this.generarInformeConCausaTerminacion(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-AlUtilizar:Interfaces_Recurso:"+identRecursoVisualizacionAcceso, CausaTerminacionTarea.ERROR);
			e.printStackTrace();
		}
		
	}



}
