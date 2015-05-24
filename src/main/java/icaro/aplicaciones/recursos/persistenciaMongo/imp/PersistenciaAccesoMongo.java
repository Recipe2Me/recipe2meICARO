package icaro.aplicaciones.recursos.persistenciaMongo.imp;

import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;

import java.net.UnknownHostException;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class PersistenciaAccesoMongo {
	
	private Morphia morphia;
	private Datastore ds;
	
	public PersistenciaAccesoMongo() throws UnknownHostException {
		morphia = new Morphia();
		ds = morphia.createDatastore(new MongoClient(), "recipe2me");
		morphia.map(UserProfile.class);
//		ds.save(new UserProfile("admin","admin"));
	}
	
	public UserProfile getUser(String userName) {
		return ds.find(UserProfile.class).field("name").equal(userName).get();
	}
	
	public List<UserProfile> getAllUsers() {
		return ds.find(UserProfile.class).asList();
	}
	
	public UserProfile createUser(UserProfile user) {
		Key<UserProfile> key = ds.save(user);
		user.setId((ObjectId) key.getId());
		return user;
	}

	public void saveUserUpdate(UserProfile user) {
		ds.save(user);
	}
}
