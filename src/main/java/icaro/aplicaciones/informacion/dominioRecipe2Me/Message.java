package icaro.aplicaciones.informacion.dominioRecipe2Me;

import java.io.Serializable;
import java.util.Date;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Message implements Serializable {
	
	private String content;
	private Date send = new Date();
	
	public Message(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSend() {
		return send;
	}
	public void setSend(Date send) {
		this.send = send;
	}
	
	

}
