package icaro.aplicaciones.recursos.comunicacionWeb.config;

import icaro.aplicaciones.recursos.persistenciaMongo.ItfUsoPersistenciaMongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private MongoUserDetailsService service;
	
	public SecurityConfiguration() {
		this.service = new MongoUserDetailsService();
	}
	
	public MongoUserDetailsService getService() {
		return service;
	}

	public void setService(MongoUserDetailsService service) {
		this.service = service;
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        //Configuramos los parametros del login
            .formLogin()
            .permitAll()
        //Configuramos los parametros del logout
        .and()
            .logout()
        	.deleteCookies("JSESSIONID")
        	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        	.logoutSuccessUrl("/")
            .permitAll()
        //Configuramos los permisos de cada ruta
        .and()
            .authorizeRequests()
            .antMatchers("/admin/**").hasRole("USER")
            .antMatchers("/**").permitAll()
        .and().csrf().disable();
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service);
	}
}
