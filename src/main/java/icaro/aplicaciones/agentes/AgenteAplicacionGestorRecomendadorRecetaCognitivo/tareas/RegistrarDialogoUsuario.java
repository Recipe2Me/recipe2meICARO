package icaro.aplicaciones.agentes.AgenteAplicacionGestorRecomendadorRecetaCognitivo.tareas;

/**
 * <p>Title: Agenda de citas vocal</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Telef�nica  I+D</p>
 * @author Jorge Gonz�lez
 * @version 1.0
 */


import icaro.aplicaciones.agentes.AgenteAplicacionGestorConocimientoInicialCognitivo.objetivos.ObtenerInfoInterlocutor;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.recursos.web.ItfUsoComunicacionWeb;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

public class RegistrarDialogoUsuario extends TareaSincrona{

  /**
   *  Constructor
   *
   *@param           Description of the Parameter
   *@param    Description of the Parameter
   */
    private Objetivo contextoEjecucionTarea = null;
  @Override
	public void ejecutar(Object... params) {
  /**
   * Produce un saludo inicial y una presentacion de funcionalidad inicial al entrar en el sistema
   */
   String identDeEstaTarea=this.getIdentTarea();
            String identAgenteOrdenante = this.getIdentAgente();
          String identInterlocutor = (String)params[0];
                    try {
//         // Se busca la interfaz del recurso en el repositorio de interfaces 
		ItfUsoComunicacionWeb recComunicacionChat = (ItfUsoComunicacionWeb) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfazUso(
						VocabularioRecipe2Me.IdentRecursoComunicacionWeb);          
                if (recComunicacionChat!=null){
                    String mensajeAenviar = VocabularioRecipe2Me.SaludoInicial2+ "  "+ identInterlocutor + "  "+
                            VocabularioRecipe2Me.InfoGeneralFuncionalidad + "  "+
                            VocabularioRecipe2Me.PeticionInformacionGeneral1;
                    recComunicacionChat.enviarMensageAlUsuario(mensajeAenviar,identInterlocutor);
                }
                else {
                    identAgenteOrdenante = this.getAgente().getIdentAgente();
                     this.generarInformeConCausaTerminacion(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-AlObtener:Interfaz:"+
                             VocabularioRecipe2Me.IdentRecursoComunicacionWeb, CausaTerminacionTarea.ERROR);
                        }
                    } catch(Exception e) {
                        this.generarInformeConCausaTerminacion(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-Acceso:Interfaz:"+
                                VocabularioRecipe2Me.IdentRecursoComunicacionWeb, CausaTerminacionTarea.ERROR);
			e.printStackTrace();
		}
	}
  

}
