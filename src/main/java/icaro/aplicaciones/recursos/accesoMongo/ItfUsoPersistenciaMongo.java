package icaro.aplicaciones.recursos.accesoMongo;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfileForm;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoPersistenciaMongo extends ItfUsoRecursoSimple {
	public List<UserProfile> getAllUsers() throws Exception;
	public UserProfile getUserByeUsername(String username) throws Exception;
	public UserProfile insertaUsuario(UserProfileForm form) throws Exception;
}