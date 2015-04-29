package icaro.aplicaciones.recursos.persistenciaMongo.imp;

import java.util.regex.Pattern;

import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class UserProfileRepository extends BasicDAO<UserProfile, ObjectId> {

	public UserProfileRepository(Datastore ds) {
		super(ds);
	}
	
	public UserProfile findByUsername(String username) {
	    Pattern regExp = Pattern.compile(username + ".*", Pattern.CASE_INSENSITIVE);
	    return getDatastore().find(UserProfile.class).filter("name", regExp).get();
	}

}
