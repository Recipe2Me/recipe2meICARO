package icaro.aplicaciones.recursos.persistenciaMongo;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.QueryFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mongodb.MongoClient;

import icaro.aplicaciones.informacion.dominioRecipe2Me.QueryRecipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.Recipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfileForm;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserSession;
import icaro.aplicaciones.recursos.persistenciaMongo.imp.RecetaRepository;
import icaro.aplicaciones.recursos.persistenciaMongo.imp.UserProfileRepository;
import icaro.aplicaciones.recursos.persistenciaMongo.imp.UserSessionRepository;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;



public class ClaseGeneradoraPersistenciaAccesoBD extends ImplRecursoSimple implements ItfUsoPersistenciaMongo {

	private static final long serialVersionUID = 1L;
    private UserProfileRepository userRepository;
    private RecetaRepository recetaRepository;
    private UserSessionRepository userSessionRepository;
    private Morphia morphia;
	private Datastore ds;

	public ClaseGeneradoraPersistenciaAccesoBD(String id) throws Exception {	
		super(id);
		try {
			morphia = new Morphia();
			ds = morphia.createDatastore(new MongoClient(), "recipe2me");//"test"
			morphia.map(UserProfile.class);
			userRepository = new UserProfileRepository(ds);
			recetaRepository = new RecetaRepository(ds);
			userSessionRepository = new UserSessionRepository(ds);
            trazas.aceptaNuevaTraza(new InfoTraza(this.getId(),
  				"Creando el esquema "+id,
  				InfoTraza.NivelTraza.debug));
		

		} catch (Exception e) {
                    this.trazas.aceptaNuevaTraza(new InfoTraza(id,
  				" Se ha producido un error en la creación del esquema de la BD : "+e.getMessage()+
                                ": Verificar el que el nombre de la BD definido en el Scrip de creacion Coincida con"
                                + "el nombre de la BD definido en la propiedad: MYSQL_NAME_BD",
  				InfoTraza.NivelTraza.error));
                    this.itfAutomata.transita("error");
			throw e;
		}

	}


	@Override
	public void termina() {
		trazas.aceptaNuevaTraza(new InfoTraza(this.getId(),
  				"Terminando recurso",
  				InfoTraza.NivelTraza.debug));
		try {
			super.termina();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public List<UserProfile> getAllUsers() {
		return userRepository.find().asList();
	}


	@Override
	public UserProfile insertaUsuario(UserProfileForm form) {
		UserProfile newUser = new UserProfile(form);
		Key<UserProfile> key = userRepository.save(newUser);
		newUser.setId((ObjectId) key.getId());
		return newUser;
	}


	@Override
	public UserProfile getUserByeUsername(String username) throws Exception {
		return userRepository.findByUsername(username);
	}


	@Override
	public Recipe findOne(String idRecipe) throws Exception {
		return recetaRepository.getRecipeById(idRecipe);
	}
	
	
	public List<Recipe> getRecipeWithCriteria(List<String> ingredientesAfirmativo) {
		return recetaRepository.getRecipeWithCriteria(ingredientesAfirmativo);
	}


	@Override
	public UserProfile actualizarUsuario(UserProfile user) throws Exception {
		userRepository.save(user);
		return user;
	}


	@Override
	public List<Recipe> getRecipeWithCriteria(QueryRecipe consulta) throws Exception {
		return recetaRepository.getRecipeWithQueryRecipe(consulta);
	}


	@Override
	public UserSession guardarSesionDeUsuario(UserSession session)
			throws Exception {
		Key<UserSession> key = userSessionRepository.save(session);
		session.setId((ObjectId) key.getId());
		return session;
	}


}