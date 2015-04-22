package icaro.aplicaciones.recursos.accesoMongo.imp;

import java.util.List;

import icaro.aplicaciones.informacion.dominioRecipe2Me.Ingrediente;
import icaro.aplicaciones.informacion.dominioRecipe2Me.Recipe;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class RecetaRepository {

	private Datastore ds;
	private DBCollection collection;

	public RecetaRepository(Datastore ds) {
		this.ds = ds;
		collection = ds.getDB().getCollection("recipes");
	}

	public Recipe getRecipeById(String id) {
		Recipe recipe = new Recipe();
		DBObject rObject = collection.findOne(new ObjectId(id));
		recipe.setId((ObjectId) rObject.get("_id"));
		recipe.setDescription((String) rObject.get("description"));
		recipe.setUrl((String) rObject.get("url"));
		DBObject data = (DBObject) rObject.get("data");
		recipe.setDificultad(((List<String>) data.get("receta_dificultad"))
				.get(0));
		recipe.setCategoria(((List<String>) data.get("receta_categoria"))
				.get(0));
		recipe.setComensales(((List<Integer>) data.get("receta_comensales"))
				.get(0));
		recipe.setImage(((List<String>) data.get("imagen")).get(0));
		recipe.setTitle(((List<String>) data.get("titulo")).get(0));
		recipe.setCuerpo(((List<String>) data.get("cuerpo")).get(0));
		for (DBObject ingrediente : (List<DBObject>) data
				.get("receta_ingredientes")) {
			Ingrediente ing = new Ingrediente();
			ing.setNombre((String) ingrediente.get("nombre"));
			if (!ingrediente.containsField("es-excepcion")) {
				ing.setCantidad((Integer) ingrediente.get("cantidad"));
				ing.setUnidad((String) ingrediente.get("unidad"));
			}
			recipe.getIngredientes().add(ing);
		}
		return recipe;
	}

}
