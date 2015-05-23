package icaro.aplicaciones.recursos.comunicacionWeb;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import icaro.aplicaciones.informacion.dominioRecipe2Me.Recipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.VocabularioRecipe2Me;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.DecisionUsuario;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoConexion;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoDesconexion;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.EventoMensajeDelUsuario;
import icaro.aplicaciones.informacion.dominioRecipe2Me.eventos.ValoracionUsuario;
import icaro.aplicaciones.recursos.comunicacionWeb.config.DisconnectInterceptor;
import icaro.aplicaciones.recursos.comunicacionWeb.config.SecurityConfiguration;
import icaro.aplicaciones.recursos.comunicacionWeb.config.WebSocketConfig;
import icaro.aplicaciones.recursos.comunicacionWeb.controller.HomeController;
import icaro.aplicaciones.recursos.persistenciaMongo.ItfUsoPersistenciaMongo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgenteAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.FactoriaAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.ItfGestPercepcionAgenteCognitivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.patronRecursoWeb.config.JettyConfiguration;
import icaro.infraestructura.patronRecursoWeb.imp.ImplRecursoWeb;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ClaseGeneradoraComunicacionWeb extends ImplRecursoWeb implements ItfUsoComunicacionWeb {

	private String identRecurso;
	private String identificadorAgenteGestorDialogo;
	public InterfazUsoAgente itfUsoAgenteGestDialogo;
	private ItfUsoPersistenciaMongo itfUsoPersistenciaMongo;
	private HomeController controller;

	public ClaseGeneradoraComunicacionWeb(String idRecurso, AnnotationConfigWebApplicationContext applicationContext)
			throws RemoteException {
		super(idRecurso);
		try {
			//Definimos el agente con el que se comunica
			ItfUsoConfiguracion config = (ItfUsoConfiguracion) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.CONFIGURACION);
			DescInstanciaRecursoAplicacion descRecurso = config.getDescInstanciaRecursoAplicacion(idRecurso);
			identificadorAgenteGestorDialogo = descRecurso.getValorPropiedad("identAgenteAReportar");
			//DescInstanciaAgenteAplicacion descAgente = config.getDescInstanciaAgenteAplicacion(VocabularioRecipe2Me.IdentAgenteAplicacionGestorDialogo);
			//String id = descAgente.getId();
			//descAgente.setId("AgentePruebaDinamico");
			//FactoriaAgenteCognitivo.instance().crearAgenteCognitivo(descAgente);
			//ItfGestPercepcionAgenteCognitivo intfAgenteCognitivo = (ItfGestPercepcionAgenteCognitivo)this.itfUsoRepositorioInterfaces.obtenerInterfazGestion("AgentePruebaDinamico");
			//intfAgenteCognitivo.arranca();
			//descAgente.setId(id);
			//Definimos el recurso de persistencia de mongo
			itfUsoPersistenciaMongo = (ItfUsoPersistenciaMongo) this.repoIntfaces
					.obtenerInterfazUso("PersistenciaMongo1");
			if (itfUsoPersistenciaMongo == null) {
				this.generarErrorCreacionComponente("itfUsoPersistenciaMongo es null");
			}
            applicationContext.register(SecurityConfiguration.class);
            applicationContext.register(WebSocketConfig.class);
		    applicationContext.register(HomeController.class);
            logger.info("Running.");
        } catch (Exception e) {
            logger.error("Error starting application", e);
            System.exit(1);
        }
	}

	@Override
	public void postInitContext(ApplicationContext context) {
		super.postInitContext(context);
        SecurityConfiguration sec = context.getBean(SecurityConfiguration.class);
        sec.getService().setService(itfUsoPersistenciaMongo);  
        controller = context.getBean(HomeController.class);
        controller.setRepository(itfUsoPersistenciaMongo);
        controller.setWeb(this);
        DisconnectInterceptor interceptor = context.getBean(DisconnectInterceptor.class);
        interceptor.setWeb(this);
	}



	private void generarErrorCreacionComponente(String textoMensaje) {
		this.trazas.aceptaNuevaTraza(new InfoTraza(id,
				"Se ha producido un error al crear el extractor semantico  "
						+ textoMensaje
						+ ": Verificar los parametros de creacion ",
				InfoTraza.NivelTraza.error));
		this.itfAutomata.transita("error");

	}

	
	public void recibirMensajeDelUsuario(String mensaje, String usuario) {
		if (getAgente() != null) {
			try {
				EventoMensajeDelUsuario nuevoMensaje = new EventoMensajeDelUsuario(usuario,mensaje);
				MensajeSimple mensajeAEnviar = new MensajeSimple(nuevoMensaje,usuario,identificadorAgenteGestorDialogo);
				itfUsoAgenteGestDialogo.aceptaMensaje(mensajeAEnviar);
			} catch (RemoteException ex) {
				Logger.getLogger(
						ClaseGeneradoraComunicacionWeb.class.getName())
						.log(Level.SEVERE, null, ex);
			}
		}
	}
	
	public void notificarConexion(UserProfile user) {
		if (getAgente() != null) {
			try {
				EventoConexion nuevaConexion = new EventoConexion(user);
				MensajeSimple mensaje = new MensajeSimple(nuevaConexion,user.getUserName(),identificadorAgenteGestorDialogo);
				itfUsoAgenteGestDialogo.aceptaMensaje(mensaje);
			} catch (RemoteException ex) {
				Logger.getLogger(
						ClaseGeneradoraComunicacionWeb.class.getName())
						.log(Level.SEVERE, null, ex);
			}
		}
	}
	
	@Override
    public void setIdentAgenteAReportar(String identAgte ){
        identAgenteAReportar = identAgte;
        InterfazUsoAgente itfAgteControlador = null;
        try {
            itfAgteControlador = (InterfazUsoAgente) this.repoIntfaces.obtenerInterfazUso(identAgenteAReportar);
        } catch (Exception ex) {
            Logger.getLogger(ClaseGeneradoraComunicacionWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
       if (itfAgteControlador == null) this.generarErrorCreacionComponente("itfAgteAreportar es null");
    }
	
	private InterfazUsoAgente getAgente() {
		if (itfUsoAgenteGestDialogo==null) {
			try {
				InterfazUsoAgente itfAgteControlador = (InterfazUsoAgente) this.repoIntfaces
						.obtenerInterfazUso(identificadorAgenteGestorDialogo);
				if (itfAgteControlador == null) {
					this.generarErrorCreacionComponente("itfAgteControlador es null");
				} else
					itfUsoAgenteGestDialogo = itfAgteControlador;
			} catch (Exception ex) {
				Logger.getLogger(ClaseGeneradoraComunicacionWeb.class.getName())
						.log(Level.SEVERE, null, ex);
			}
		}
		return itfUsoAgenteGestDialogo;
	}

	@Override
	public void enviarMensageAlUsuario(String mensaje, String usuario)
			throws Exception {
		controller.sendMessageToUser(mensaje, usuario);
	}

	@Override
	public void notificarDesconexion(UserProfile user) throws Exception {
		if (getAgente() != null) {
			try {
				EventoDesconexion nuevaConexion = new EventoDesconexion(user);
				MensajeSimple mensaje = new MensajeSimple(nuevaConexion,user.getUserName(),identificadorAgenteGestorDialogo);
				itfUsoAgenteGestDialogo.aceptaMensaje(mensaje);
			} catch (RemoteException ex) {
				Logger.getLogger(
						ClaseGeneradoraComunicacionWeb.class.getName())
						.log(Level.SEVERE, null, ex);
			}
		}
	}

	@Override
	public void enviarRecetaAlUsuario(Recipe recipe,
			String usuario) throws Exception {
		controller.sendRecipeToUser(usuario, recipe);
		
	}

	@Override
	public void notificarDecisionUsuario(DecisionUsuario decision)
			throws Exception {
		if (getAgente() != null) {
			try {
				MensajeSimple mensaje = new MensajeSimple(decision,decision.getUser(),identificadorAgenteGestorDialogo);
				itfUsoAgenteGestDialogo.aceptaMensaje(mensaje);
			} catch (RemoteException ex) {
				Logger.getLogger(
						ClaseGeneradoraComunicacionWeb.class.getName())
						.log(Level.SEVERE, null, ex);
			}
		}
	}

	@Override
	public void terminarConversacion(String usuario) throws Exception {
		controller.sendMessageToUser("end", usuario);
	}

	@Override
	public void notificarValoracionUsuario(ValoracionUsuario valoracion) throws Exception {
		
		if (getAgente() != null) {
			try {
				MensajeSimple mensaje = new MensajeSimple(valoracion,valoracion.getUser(),identificadorAgenteGestorDialogo);
				itfUsoAgenteGestDialogo.aceptaMensaje(mensaje);
			} catch (RemoteException ex) {
				Logger.getLogger(
						ClaseGeneradoraComunicacionWeb.class.getName())
						.log(Level.SEVERE, null, ex);
			}
		}
	}

}
