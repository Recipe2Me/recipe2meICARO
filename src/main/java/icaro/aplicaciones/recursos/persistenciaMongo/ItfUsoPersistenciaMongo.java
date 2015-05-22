package icaro.aplicaciones.recursos.persistenciaMongo;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import icaro.aplicaciones.informacion.dominioRecipe2Me.QueryRecipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.Recipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfileForm;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserSession;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoPersistenciaMongo extends ItfUsoRecursoSimple {
	public List<UserProfile> getAllUsers() throws Exception;
	public UserProfile getUserByeUsername(String username) throws Exception;
	public UserProfile insertaUsuario(UserProfileForm form) throws Exception;
	public UserProfile actualizarUsuario(UserProfile user) throws Exception;
	public Recipe findOne(String idRecipe) throws Exception;
	public List<Recipe> getRecipeWithCriteria(QueryRecipe consulta) throws Exception;
	public UserSession guardarSesionDeUsuario(UserSession session) throws Exception;
}