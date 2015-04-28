package icaro.aplicaciones.recursos.comunicacionWeb;

import icaro.aplicaciones.informacion.dominioRecipe2Me.Recipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import icaro.infraestructura.patronRecursoWeb.ItfUsoRecursoWeb;

public interface ItfUsoComunicacionWeb extends ItfUsoRecursoWeb {
	public void recibirMensajeDelUsuario(String mensaje, String usuario) throws Exception;
	public void notificarConexion(UserProfile user) throws Exception;
	public void notificarDesconexion(UserProfile user) throws Exception;
	public void enviarMensageAlUsuario(String mensaje, String usuario) throws Exception;
	public void enviarRecetaAlUsuario(String mensaje, Recipe recipe, String usuario) throws Exception;
}