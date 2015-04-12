package icaro.infraestructura.patronRecursoWeb;


import java.rmi.RemoteException;

import org.springframework.context.ApplicationContext;

import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;

/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public interface ItfGestionRecursoWeb extends InterfazGestion {
	public void postInitContext(ApplicationContext context) throws RemoteException;
	/*public String obtenerEstado()
		throws Exception;*/
}
