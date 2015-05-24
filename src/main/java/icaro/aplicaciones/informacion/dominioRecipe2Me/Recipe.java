package icaro.aplicaciones.informacion.dominioRecipe2Me;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

@Entity("recipes")
public class Recipe implements Comparable<Recipe> {
	
	@Id
	private ObjectId id;
	private String title;
	private String description;
	private String url;
	private String categoria;
	private String dificultad;
	private Integer comensales;
	private List<String> enfermedades;
	private List<String> temporadas;
	private List<Ingrediente> ingredientes;
	private String cuerpo;
	private String image;
	@Transient
	private Double score;
	
	public Recipe() {
		this.enfermedades = new ArrayList<String>();
		this.temporadas = new ArrayList<String>();
		this.ingredientes = new ArrayList<Ingrediente>();
	}
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getDificultad() {
		return dificultad;
	}
	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}
	public Integer getComensales() {
		return comensales;
	}
	public void setComensales(Integer comensales) {
		this.comensales = comensales;
	}
	public List<String> getEnfermedades() {
		return enfermedades;
	}
	public void setEnfermedades(List<String> enfermedades) {
		this.enfermedades = enfermedades;
	}
	public List<String> getTemporadas() {
		return temporadas;
	}
	public void setTemporadas(List<String> temporadas) {
		this.temporadas = temporadas;
	}
	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}
	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Override
	public int compareTo(Recipe o) {
		return this.score.compareTo(o.getScore());
	}

	
}
