/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronRecursoWeb.imp;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.ItfUsoAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.imp.ClaseGeneradoraAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescRecursoAplicacion;
import icaro.infraestructura.patronRecursoWeb.FactoriaRecursoWeb;
import icaro.infraestructura.patronRecursoWeb.ItfGestionRecursoWeb;
import icaro.infraestructura.patronRecursoWeb.ItfUsoRecursoWeb;
import icaro.infraestructura.patronRecursoWeb.config.JettyConfiguration;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class FactoriaRecursoSimpleWeb extends FactoriaRecursoWeb {

//	private static final String PAQUETE_RECURSOS_APLICACION = "icaro.aplicaciones.recursos.";
        String msgError;
	/**
	 * @uml.property  name="logger"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Logger logger = Logger
			.getLogger(this.getClass().getCanonicalName());
	/**
	 * @uml.property  name="trazas"
	 * @uml.associationEnd  readOnly="true"
	 */
	private ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        private ItfUsoRepositorioInterfaces repoIntfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
        private ItfUsoAutomataEFsinAcciones itfAutomata;
        private NombresPredefinidos.TipoEntidad tipoEntidad = NombresPredefinidos.TipoEntidad.Recurso ;
        private String idRecurso;
        private AnnotationConfigWebApplicationContext applicationContext;
        static boolean webApplicationContextInitialized = false;
        
    public FactoriaRecursoSimpleWeb() {
    	this.applicationContext = new AnnotationConfigWebApplicationContext();
    	applicationContext
        .addApplicationListener(new ApplicationListener<ContextRefreshedEvent>() {
            @Override
            public void onApplicationEvent(
                    ContextRefreshedEvent event) {
                ApplicationContext ctx = event.getApplicationContext();
                if (ctx instanceof AnnotationConfigWebApplicationContext) {
                    webApplicationContextInitialized = true;
                }
            }
        });
    	applicationContext.registerShutdownHook();
    	applicationContext.register(JettyConfiguration.class);
	}

	@Override
	public void crearRecursoWeb(DescInstanciaRecursoAplicacion recurso) {
		idRecurso = recurso.getId();
		try {
			// obtengo la clase generadora del recurso
		    trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,tipoEntidad,
					"Factoria de recurso simple: Creando el recurso "
					+ idRecurso,
					InfoTraza.NivelTraza.debug));
			
			 ImplRecursoWeb objRecurso = obtenerInstClaseGeneradora(recurso,applicationContext);

                         if (objRecurso != null) {
                            itfAutomata = (ItfUsoAutomataEFsinAcciones) ClaseGeneradoraAutomataEFsinAcciones.instance(NombresPredefinidos.FICHERO_AUTOMATA_CICLO_VIDA_COMPONENTE);
                            objRecurso.setItfAutomataCicloDeVida(itfAutomata);
                            //Tras iniciar el objeto iniciamos la actualizacion del contexto
                            applicationContext.refresh();
                            if (!webApplicationContextInitialized) {
                                logger.error("Web application context not initialized. Exiting.");
                                System.exit(1);
                            }
                            //Tras iniciar correctamente el contexto volvemos a pasarlo al recurso para que realice varias acciones post inicio
                            objRecurso.postInitContext(applicationContext);
			// Guardamos el id de la instancia
		//	objRecurso.setId(idRecurso);

			// End Logging
			//logger.debug("Factoria de recurso simple: recurso " + recurso+ " creado.");
			trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,
					"Factoria de recurso simple: recurso " + idRecurso+ " creado.",
					InfoTraza.NivelTraza.debug));
			objRecurso.setItfUsoRepositorioInterfaces(ClaseGeneradoraRepositorioInterfaces
					.instance());

			// registramos ambos objetos en el repositorio
			/*logger.debug("Factoria de recurso simple: Registrando el recurso "
					+ idRecurso + " en el repositorio de interfaces.");*/
			trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,
					"Factoria de recurso simple: Registrando el recurso "
					+ idRecurso + " en el repositorio de interfaces.",
					InfoTraza.NivelTraza.debug));
			repoIntfaces.registrarInterfaz(
					NombresPredefinidos.ITF_GESTION + idRecurso, (ItfGestionRecursoWeb)objRecurso);
			repoIntfaces.registrarInterfaz(
					NombresPredefinidos.ITF_USO + idRecurso, (ItfUsoRecursoWeb)objRecurso);
                        objRecurso.setItfUsoRepositorioInterfaces(repoIntfaces);
                        objRecurso.setItfUsoRecursoTrazas(trazas);
                    } else {
                             trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,
                    idRecurso + ":No se puede crear el recurso. La clase generadora no esta bien definida ",
                    NivelTraza.error));
                    System.err.println(" No se puede crear  el recurso. La clase generadora no esta bien definida.");
                    }
             

		} catch (Exception e) {
			trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,
					"Factoria de recurso simple: Error al crear el recurso "+ idRecurso+e,
					InfoTraza.NivelTraza.error));
			logger.error("Factoria de recurso simple: Error al crear el recurso "+ recurso,e);
		}
	}

    private ImplRecursoWeb obtenerInstClaseGeneradora(DescInstanciaRecursoAplicacion instRecurso, AnnotationConfigWebApplicationContext applicationContext) {

		DescRecursoAplicacion descComportamiento = instRecurso.getDescRecurso();
                if ( descComportamiento == null){    
                    msgError = "Factoria de recurso simple: Error al crear el recurso "+ idRecurso + "la descripcion del comportamiento es null. \n" ;
                    trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,msgError,InfoTraza.NivelTraza.error));
                    System.err.println(msgError);
                        }
                else {
                String rutaClase = descComportamiento.getLocalizacionClaseGeneradora();

		try {	
                    Integer posicion = rutaClase.indexOf(".class");
                    if ( posicion < 0) posicion = rutaClase.indexOf(".java");
                    if ( posicion >= 0) rutaClase = rutaClase.substring(0,posicion);
                    Class claseGenradoraRecurso = Class.forName(rutaClase);
                    ImplRecursoWeb objRecurso = (ImplRecursoWeb) claseGenradoraRecurso.getConstructor(String.class,AnnotationConfigWebApplicationContext.class).newInstance(idRecurso,applicationContext);	
			return objRecurso;
		} catch (InstantiationException ex) {
                    msgError = "La clase: " + rutaClase
					  + " que debe implementar la generacion del recurso, no puede instanciarse. \n" ;
                    trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,msgError,InfoTraza.NivelTraza.error));
                    System.err.println(msgError);
		} catch (IllegalAccessException ex) {
                    msgError = "La clase: " + rutaClase
					  + " que debe implementar la generacion del recurso, no tiene un constructor sin parmetros. \n" ;
                    trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,msgError,InfoTraza.NivelTraza.error));
                    System.err.println(msgError);
			
		} catch (ClassNotFoundException ex) {
                    msgError = "La clase: " + rutaClase
					  + " que debe implementar la generacion del recurso, no se encuentra. \n" ;
                    trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,msgError,InfoTraza.NivelTraza.error));
                    System.err.println(msgError);
        } catch (InvocationTargetException x) {
                    Throwable cause = x.getCause();
              //      err.format("invocation of %s failed: %s%n", mname, cause.getMessage());
                   msgError = "La invocation de la clase " + rutaClase + " que debe implementar la generacion del recurso "
                           + " produce  un error \n Cuya causa es : " + cause.getMessage() +"\n";
                   trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,msgError,InfoTraza.NivelTraza.error));
                   System.err.println(msgError);
		} catch (Exception e) {
                    msgError = "Factoria de recurso simple: Error al crear el recurso "+ idRecurso+e + "\n" ;
                    trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,msgError,InfoTraza.NivelTraza.error));
                    System.err.println(msgError);
		}
             }
		// si falla algo, devuelvo un null
		return null;
	}
  private String normalizarRuta(String ruta){
	/*Esta funcin cambia la primera letra del nombre y la pone en minsculas*/
		String primero = ruta.substring(0,1).toLowerCase(); //obtengo el primer carcter en minsculas
		String rutaNormalizada = primero + ruta.substring(1, ruta.length());

		return rutaNormalizada;
	}

}

