package icaro.aplicaciones.recursos.persistenciaMongo.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import gate.creole.annic.apache.lucene.search.Sort;
import icaro.aplicaciones.informacion.dominioRecipe2Me.Ingrediente;
import icaro.aplicaciones.informacion.dominioRecipe2Me.QueryRecipe;
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
		recipe = parseDBObjectToRecipe(rObject);
		return recipe;
	}
	
	public List<Recipe> getRecipeWithCriteria(List<String> ingredientesAfirmativo) {
		List<Recipe> recipes = new ArrayList<Recipe>();
		String busqueda = "";
		for (String ing : ingredientesAfirmativo) {
			busqueda=busqueda.concat("\"".concat(ing).concat("\" "));
		}
//		for (String ing : ingredientesNegativos) {
//			busqueda=busqueda.concat(" -".concat(ing));
//		}
		DBObject textSearch = new BasicDBObject("$text", new BasicDBObject("$search",busqueda));
		DBObject resultQuery = new BasicDBObject("score",new BasicDBObject("$meta","textScore"));
		List<DBObject> result = collection.find(textSearch,resultQuery).sort(resultQuery).limit(5).toArray();
		for (DBObject r : result) {
			recipes.add(parseDBObjectToRecipe(r));
		}
		return recipes;
	}
	
	public Recipe parseDBObjectToRecipe(DBObject rObject) {
		Recipe recipe = new Recipe();
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
		if (data.containsField("imagen"))
			if (data.get("imagen")!=null)
				recipe.setImage(((List<String>) data.get("imagen")).get(0));
		recipe.setTitle(((List<String>) data.get("titulo")).get(0));
		recipe.setCuerpo(((List<String>) data.get("cuerpo")).get(0));
		for (DBObject ingrediente : (List<DBObject>) data
				.get("receta_ingredientes")) {
			Ingrediente ing = new Ingrediente();
			ing.setNombre((String) ingrediente.get("nombre"));
			if (!ingrediente.containsField("es-excepcion")) {
				try {
					ing.setCantidad((Integer) ingrediente.get("cantidad"));
				} catch(ClassCastException ex) {
					ing.setCantidad(Double.valueOf(Double.parseDouble(((String) ingrediente.get("cantidad")).replace(",", "."))).intValue());
				}
				try {
					ing.setUnidad((String) ingrediente.get("unidad"));
				} catch(ClassCastException ex) {
					ing.setUnidad(((Integer) ingrediente.get("unidad")).toString());
				}
			}
			recipe.getIngredientes().add(ing);
		}
		return recipe;
	}
	
	public List<Recipe> getRecipeWithQueryRecipe(QueryRecipe consulta) {	
		List<Recipe> recipes = new ArrayList<Recipe>();
		String busqueda = "";
		
		for (String ing : consulta.getGusto().keySet()) {
			if (consulta.getGusto().get(ing)>5.0)
				busqueda=busqueda.concat(ing.concat(" "));
		}
		
		for (String ing : consulta.getIngredientes()) {
			if (!consulta.getGusto().containsKey(ing)) {
				busqueda=busqueda.concat(ing.concat(" "));
			}
			else {
				if (consulta.getGusto().get(ing)<=5.0)
					busqueda=busqueda.concat(ing.concat(" "));
			}
		}
		
		for (String ing : consulta.getAlergias()) {
			busqueda=busqueda.concat(" -\"".concat(ing).concat("\""));
		}
		DBObject textSearch = new BasicDBObject("$text", new BasicDBObject("$search",busqueda));
		DBObject noRecipes = new BasicDBObject("_id", new BasicDBObject("$nin",consulta.getListaRecetasRechazadas()));
		textSearch.putAll(noRecipes);
		DBObject resultQuery = new BasicDBObject("score",new BasicDBObject("$meta","textScore"));
		List<DBObject> result = collection.find(textSearch,resultQuery).sort(resultQuery).limit(100).toArray();
		for (DBObject r : result) {
			recipes.add(parseDBObjectToRecipe(r));
		} 
		for (Recipe recipe : recipes) {
			Double count = 0.0;
			for (Ingrediente ing : recipe.getIngredientes()) {
				if (consulta.getGusto().containsKey(ing.getNombre())) {
					count += consulta.getGusto().get(ing.getNombre());
				}
			}
			recipe.setScore(count);
		}
		Collections.sort(recipes);
		return recipes;
	}

}
