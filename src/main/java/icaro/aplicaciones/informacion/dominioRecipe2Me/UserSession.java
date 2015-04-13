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

@Entity("session")
public class UserSession implements Serializable {
	
	@Id
	public ObjectId id;
	public String user;
	public Date start;
	public Date end;
	public Date update;
	public boolean first=true;
	@Embedded(concreteClass=Message.class) public List<Message> messages = new ArrayList<Message>();
	public List<String> recipes = new ArrayList<String>();
	public String recipeAccept;
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
	public List<String> getRecipes() {
		return recipes;
	}
	public void setRecipes(List<String> recipes) {
		this.recipes = recipes;
	}
	public String getRecipeAccept() {
		return recipeAccept;
	}
	public void setRecipeAccept(String recipeAccept) {
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
	

}
