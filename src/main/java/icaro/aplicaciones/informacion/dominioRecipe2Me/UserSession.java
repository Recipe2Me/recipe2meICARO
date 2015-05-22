package icaro.aplicaciones.informacion.dominioRecipe2Me;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

@Entity("session")
public class UserSession implements Serializable {
	
	@Id
	public ObjectId id;
	public String user;
	public Date start;
	public Date end;
	public Date update;
	public boolean first=true;
	@Embedded("messages") 
	public List<Message> messages = new ArrayList<Message>();
	@Transient
	public List<Recipe> recipes = new ArrayList<Recipe>();
	public List<ObjectId> recipesReject = new ArrayList<ObjectId>();
	public ObjectId recipeAccept;
	public boolean finished;
	
	public UserSession(String user) {
		this.user = user;
		this.start = new Date();
		this.update = (Date) start.clone();
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Date getUpdate() {
		return update;
	}
	public void setUpdate(Date update) {
		this.update = update;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public List<Recipe> getRecipes() {
		return recipes;
	}
	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}
	public ObjectId getRecipeAccept() {
		return recipeAccept;
	}
	public void setRecipeAccept(ObjectId recipeAccept) {
		this.recipeAccept = recipeAccept;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public List<ObjectId> getRecipesReject() {
		return recipesReject;
	}

	public void setRecipesReject(List<ObjectId> recipesReject) {
		this.recipesReject = recipesReject;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

}
