/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorConocimientoInicialCognitivo.objetivos.ObtenerInfoInterlocutor;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.recursos.web.ItfUsoComunicacionWeb;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */
public class InicializarInfoWorkMem extends TareaSincrona{

   @Override
   public void ejecutar(Object... params) {
	   try {
        //     Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
             String identTarea = this.getIdentTarea();
             String nombreAgenteEmisor = this.getIdentAgente();
             this.getItfConfigMotorDeReglas().setDepuracionActivationRulesDebugging(true);
             this.getItfConfigMotorDeReglas().setfactHandlesMonitoring_afterActivationFired_DEBUGGING(true);
             InterfazUsoAgente agenteConocimiento = (InterfazUsoAgente) repoInterfaces.obtenerInterfazUso(VocabularioRecipe2Me.IdentAgenteAplicacionGestorConocimientoInicial);
             InterfazUsoAgente agenteRecomendador = (InterfazUsoAgente) repoInterfaces.obtenerInterfazUso(VocabularioRecipe2Me.IdentAgenteAplicacionGestorRecomendadorReceta);
             ItfUsoComunicacionWeb recursoWeb= (ItfUsoComunicacionWeb) repoInterfaces.obtenerInterfazUso(VocabularioRecipe2Me.IdentRecursoComunicacionWeb);
             this.getItfMotorDeReglas().addGlobalVariable("agenteConocimiento", agenteConocimiento);
             this.getItfMotorDeReglas().addGlobalVariable("agenteRecomendador", agenteRecomendador);
             this.getItfMotorDeReglas().addGlobalVariable("recursoWeb", recursoWeb);
//             this.getEnvioHechos().insertarHechoWithoutFireRules(new Focus());
       } catch (Exception e) {
			 e.printStackTrace();
                         trazas.aceptaNuevaTraza(new InfoTraza(this.getIdentAgente(), "Error al ejecutar la tarea : "+this.getIdentTarea() + e, InfoTraza.NivelTraza.error));
       }
   }

}
