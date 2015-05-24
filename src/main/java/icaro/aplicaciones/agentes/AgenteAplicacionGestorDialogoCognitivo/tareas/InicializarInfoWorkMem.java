/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.tareas;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerConfirmacionUsuario;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerEstudioPreliminar;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerAlergia;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerConocimientoInicial;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerInfoSesion;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerIngredienteOPlato;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerIngredientesFavoritos;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerIngredientesOdiados;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerNivelCocina;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerRecetasSegunRequisitos;
import icaro.aplicaciones.agentes.AgenteAplicacionGestorDialogoCognitivo.objetivos.ObtenerValoracionUsuario;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
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
             this.getEnvioHechos().insertarHechoWithoutFireRules(new Focus());
             this.getEnvioHechos().insertarHecho(new ObtenerInfoSesion());
             this.getEnvioHechos().insertarHecho(new ObtenerConocimientoInicial());
     		 this.getEnvioHechos().insertarHecho(new ObtenerIngredienteOPlato());
     		 this.getEnvioHechos().insertarHecho(new ObtenerValoracionUsuario());
     		 this.getEnvioHechos().insertarHecho(new ObtenerEstudioPreliminar());
     		 this.getEnvioHechos().insertarHecho(new ObtenerRecetasSegunRequisitos());
     		 this.getEnvioHechos().insertarHecho(new ObtenerConfirmacionUsuario());
     		 this.getEnvioHechos().insertarHecho(new ObtenerValoracionUsuario());
       } catch (Exception e) {
			 e.printStackTrace();
                         trazas.aceptaNuevaTraza(new InfoTraza(this.getIdentAgente(), "Error al ejecutar la tarea : "+this.getIdentTarea() + e, InfoTraza.NivelTraza.error));
       }
   }

}
