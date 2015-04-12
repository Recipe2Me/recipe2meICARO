package icaro.infraestructura.patronRecursoWeb.config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import com.codahale.metrics.MetricRegistry;

import net.sourceforge.html5val.Html5ValDialect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * The SpringMVC application context.
 *
 * This is the annotation variation of configuring the SpringMVC application
 * context. An XML configuration is imported so XML based configuration can
 * still be used.
 *
 * Any @Controller classes will be picked up by component scanning. All other
 * components are ignored as they will be picked up by the root application
 * context.
 */
@Configuration
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {
	
	@Autowired
	private ApplicationContext context;

	final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
    }
//    	
//    @Bean
//    public HomeController getHomeController() {
//    	return new HomeController();
//    }
//    
//    @Bean
//    public AdminController getAdminController() {
//    	return new AdminController();
//    }

    @Bean
    public ServletContextTemplateResolver thymeleafTemplateResolver() {
        ServletContextTemplateResolver resolver =
                new ServletContextTemplateResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCacheable(false); // TODO True para produccion
        return resolver;
    }

    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(thymeleafTemplateResolver());
        engine.addDialect(thymeleafSecurityDialect());
        engine.addDialect(thymeleafValidationHtml5Dialect());
        return engine;
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(thymeleafTemplateEngine());
        return resolver;
    }
    
    @Bean
    public SpringSecurityDialect thymeleafSecurityDialect() {
        return new SpringSecurityDialect();
    }
	
	@Bean
	public SecurityExpressionHandler getHandler() {
		return new DefaultWebSecurityExpressionHandler();
	}
	
	@Bean
	public Html5ValDialect thymeleafValidationHtml5Dialect() {
		return new Html5ValDialect();
	}
	
	 @Bean
    public RequestMappingHandlerMapping mapping() {
	 RequestMappingHandlerMapping m = new RequestMappingHandlerMapping();
        m.setDetectHandlerMethodsInAncestorContexts(true);
        return m;
    }
	
}
