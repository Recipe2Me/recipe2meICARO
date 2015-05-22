package icaro.aplicaciones.recursos.persistenciaMongo.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
					ing.setCantidad(Integer.parseInt(((String) ingrediente.get("cantidad"))));
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
		/*db.recipes.find(
		 * 					{
		 * 						$text:{
		 * 								$search:
		 * 										"-\"Croquetas de calamares en su tinta\"Croquetas de bacalao\"leche harina -huevo -sal"
		 * 							  }
		 * 					},
		 * 					{
		 * 						score:{
		 * 								$meta:
		 * 									   "textScore"
		 * 							  }
		 *                  }
		 *                ).sort({score:{$meta:"textScore"}}).limit(2).pretty()*/
		
		
		
		List<Recipe> recipes = new ArrayList<Recipe>();
		String busqueda = "";
		if(!consulta.getListaRecetasRechazadas().isEmpty()){
			for(ObjectId receta : consulta.getListaRecetasRechazadas()){
				busqueda = busqueda.concat("-\"".concat(receta.toString()).concat("\""));
					}
		}
		
		for (String ing : consulta.getIngredientes()) {
			busqueda=busqueda.concat("\"".concat(ing).concat("\" "));
		}
		
		for (String ing : consulta.getAlergias()) {
			busqueda=busqueda.concat(" -".concat(ing));
		}
		DBObject textSearch = new BasicDBObject("$text", new BasicDBObject("$search",busqueda));
		DBObject resultQuery = new BasicDBObject("score",new BasicDBObject("$meta","textScore"));
		List<DBObject> result = collection.find(textSearch,resultQuery).sort(resultQuery).limit(5).toArray();
		for (DBObject r : result) {
			recipes.add(parseDBObjectToRecipe(r));
		}
		return recipes;
	}

}
