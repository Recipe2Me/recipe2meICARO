package icaro.aplicaciones.informacion.dominioRecipe2Me;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

public class UserProfileForm {
	@NotNull(message="Tienes que introducir un nombre de usuario.")
	private String userName;
	@NotNull(message="Tienes que introducir una contraseña.")
	private String password;
	@NotNull(message="Tienes que introducir un email.")
	@Email(message="Tienes que introducir un email valido.")
	private String email;
	
	public UserProfileForm() {
	
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
