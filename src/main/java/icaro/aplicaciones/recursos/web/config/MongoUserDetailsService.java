package icaro.aplicaciones.recursos.web.config;

import icaro.aplicaciones.recursos.accesoMongo.ItfUsoPersistenciaMongo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MongoUserDetailsService implements UserDetailsService {
	
	private ItfUsoPersistenciaMongo service;

	public MongoUserDetailsService(
			ItfUsoPersistenciaMongo itfUsoPersistenciaMongo) {
		this.service = itfUsoPersistenciaMongo;
	}

	public MongoUserDetailsService() {
	}

	public ItfUsoPersistenciaMongo getService() {
		return service;
	}

	public void setService(ItfUsoPersistenciaMongo service) {
		this.service = service;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return service.getUserByeUsername(username);
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}

}
