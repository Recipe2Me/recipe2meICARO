package icaro.aplicaciones.informacion.dominioRecipe2Me;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity("users")
public class UserProfile implements Serializable, UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6478074814572550011L;
	@Id
	private ObjectId id;
	@Property("name")
	private String userName;
	@Property("pass")
	private String password;
	private String email;
	private boolean init = false;
	private List<String> alergias;
	private Map<String, Double> gusto;
	private boolean sabeCocinar = false;
	private List<ObjectId> recetasNoGusta;
	private boolean pendiente = false;
	private ObjectId recetaPendiente;
	private Date ultimoAcceso;

	public UserProfile() {
		alergias = new ArrayList<String>();
		gusto = new HashMap<String, Double>();
		recetasNoGusta = new ArrayList<ObjectId>();
	}

	public UserProfile(UserProfileForm from) {
		this.userName = from.getUserName();
		this.password = from.getPassword();
		this.email = from.getEmail();
		this.init = false;
	}

	public UserProfile(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
		this.init = false;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
				"ROLE_USER");
		Set<GrantedAuthority> auth = new HashSet<>();
		auth.add(authority);
		return auth;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public List<String> getAlergias() {
		return alergias;
	}

	public void setAlergias(List<String> alergias) {
		this.alergias = alergias;
	}

	public Map<String, Double> getGusto() {
		return gusto;
	}

	public void setGusto(List<String> gustos) {// Map<String, Double> gusto) {

		for (String i : gustos)
			this.gusto.put(i, 5.0);
		// this.gusto = gusto;
	}

	public void setNoGusto(List<String> gustos) {// Map<String, Double> gusto) {

		for (String i : gustos)
			this.gusto.put(i, -2.5);
		// this.gusto = gusto;
	}

	public boolean isSabeCocinar() {
		return sabeCocinar;
	}

	public void setSabeCocinar(boolean sabeCocinar) {
		this.sabeCocinar = sabeCocinar;
	}

	public List<ObjectId> getRecetasNoGusta() {
		return recetasNoGusta;
	}

	public void setRecetasNoGusta(List<ObjectId> recetasNoGusta) {
		this.recetasNoGusta = recetasNoGusta;
	}

	public boolean isPendiente() {
		return pendiente;
	}

	public void setPendiente(boolean pendiente) {
		this.pendiente = pendiente;
	}

	public ObjectId getRecetaPendiente() {
		return recetaPendiente;
	}

	public void setRecetaPendiente(ObjectId recetaPendiente) {
		this.recetaPendiente = recetaPendiente;
	}

	public void setGusto(Map<String, Double> gusto) {
		this.gusto = gusto;
	}

	public Date getUltimoAcceso() {
		return ultimoAcceso;
	}

	public void setUltimoAcceso(Date ultimoAcceso) {
		this.ultimoAcceso = ultimoAcceso;
	}

	// METODO QUE INCREMENTA EN 5.0 EL VALOR DEL INGREDIENTE QUE "GUSTA" AL
	// CLIENTE, EN LA LISTA DE SUS PREFERIDOS, CUANDO A LA RECETA DA "ME GUSTA"
	public void updateGustos(List<Ingrediente> pIngredientes) {

		for (Ingrediente i : pIngredientes) {

			String pIngedName = i.getNombre();

			if (this.gusto.containsKey(pIngedName)) {
				if (this.gusto.get(pIngedName) != 0.0) {
					double aux = this.gusto.get(pIngedName);
					this.gusto.put(pIngedName, aux + 5.0);
				} else {
					this.gusto.put(pIngedName, 5.0);
				}
			}
		}
	}

	// METODO QUE DECREMENTA EN 5.0 EL VALOR DEL INGREDIENTE QUE "GUSTA" AL
	// CLIENTE, EN LA LISTA DE SUS PREFERIDOS, CUANDO A LA RECETA DA
	// "NO ME GUSTA"
	public void updateNoMeGusta(List<Ingrediente> pIngredientes) {

		for (Ingrediente i : pIngredientes) {
			String pIngedName = i.getNombre();
			if (this.gusto.containsKey(pIngedName)) {
				double aux = this.gusto.get(pIngedName);
				this.gusto.put(pIngedName, aux - 2.5);
			} else {
				this.gusto.put(pIngedName, -2.5);
			}

		}

	}

	public void updateValoracion(Recipe recipe, Double valoracion) {

		for (Ingrediente i : recipe.getIngredientes()) {
			String pIngedName = i.getNombre();
			if (this.gusto.containsKey(pIngedName)) {
				double aux = this.gusto.get(pIngedName);
				this.gusto.put(pIngedName, aux + valoracion);
			} else {
				this.gusto.put(pIngedName, valoracion);
			}
		}
	}
}
