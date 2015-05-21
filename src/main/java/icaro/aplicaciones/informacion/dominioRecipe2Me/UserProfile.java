package icaro.aplicaciones.informacion.dominioRecipe2Me;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
	private boolean init=false;
	private List<String> alergias;
	private Map<String,Double> gusto;
	private boolean sabeCocinar = false;
	private List<String> recetasNoGusta;
	
	public UserProfile() {
		alergias = new ArrayList<String>();
		gusto = new HashMap<String,Double>();
	}
	
	public UserProfile(UserProfileForm from) {
		this.userName = from.getUserName();
		this.password = from.getPassword();
		this.email = from.getEmail();
		this.init=false;
	}
	
	public UserProfile(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
		this.init=false;
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
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
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

	public void setGusto(List<String> gustos){//Map<String, Double> gusto) {
		
		for (String i : gustos)
			this.gusto.put(i, 5.0);
		//this.gusto = gusto;
	}
	public void setNoGusto(List<String> gustos){//Map<String, Double> gusto) {
		
		for (String i : gustos)
			this.gusto.put(i, 0.0);
		//this.gusto = gusto;
	}

	public boolean isSabeCocinar() {
		return sabeCocinar;
	}

	public void setSabeCocinar(boolean sabeCocinar) {
		this.sabeCocinar = sabeCocinar;
	}
	
	public void setRecetasNoGusto(List<String> recetas){//Map<String, Double> gusto) {
		
		this.recetasNoGusta = recetas;
	}
	
	public List<String> getRecetasNoGusto(){//Map<String, Double> gusto) {
		
		return this.recetasNoGusta;
	}
	
	
	//METODO QUE INCREMENTA EN 5.0 EL VALOR DEL INGREDIENTE QUE "GUSTA" AL CLIENTE, EN LA LISTA DE SUS PREFERIDOS, CUANDO A LA RECETA DA "ME GUSTA"
	public void updateGustos(List<Ingrediente> pIngredientes){
		
		for(Ingrediente i : pIngredientes){
			
			String pIngedName = i.getNombre();
			
			if(this.gusto.containsKey(pIngedName)){
				if(this.gusto.get(pIngedName)!=0.0){
					double aux = this.gusto.get(pIngedName);
					this.gusto.replace(pIngedName, aux, aux+5.0);
				}
			}
		}
	}
	//METODO QUE DECREMENTA EN 5.0 EL VALOR DEL INGREDIENTE QUE "GUSTA" AL CLIENTE, EN LA LISTA DE SUS PREFERIDOS, CUANDO A LA RECETA DA "NO ME GUSTA"
	public void updateNoMeGusta(List<Ingrediente> pIngredientes){
		
		for(Ingrediente i : pIngredientes){
			String pIngedName = i.getNombre();
			if(this.gusto.containsKey(pIngedName)){
				if((this.gusto.get(pIngedName)!=0.0)||(this.gusto.get(pIngedName)>5.0)){
					double aux = this.gusto.get(pIngedName);
					this.gusto.replace(pIngedName, aux, aux-5.0);
				}
			}
			
		}
			
		
	}
	
}
