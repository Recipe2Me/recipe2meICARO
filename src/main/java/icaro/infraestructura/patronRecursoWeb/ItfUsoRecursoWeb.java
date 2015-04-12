package icaro.infraestructura.patronRecursoWeb;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 *@author     Francisco J Garijo
 *@created    30 de septiembre de 2009
 */

public interface ItfUsoRecursoWeb extends Remote{
    public void setIdentAgenteAReportar(String identAgenteAReportar)throws RemoteException;
}
