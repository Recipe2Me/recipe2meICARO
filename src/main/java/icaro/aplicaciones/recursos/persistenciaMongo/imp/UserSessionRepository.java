package icaro.aplicaciones.recursos.persistenciaMongo.imp;

import java.util.regex.Pattern;

import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserSession;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class UserSessionRepository extends BasicDAO<UserSession, ObjectId> {

	public UserSessionRepository(Datastore ds) {
		super(ds);
	}

}
