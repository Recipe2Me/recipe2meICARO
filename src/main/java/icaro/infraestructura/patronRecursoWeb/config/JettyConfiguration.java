package icaro.infraestructura.patronRecursoWeb.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.xml.transform.sax.SAXSource;

import java.io.IOException;

public class JettyConfiguration {

	final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationContext applicationContext;

    private int jettyPort=8080;

    @Bean
    public WebAppContext webAppContext() throws IOException {

        WebAppContext ctx = new WebAppContext();
        ctx.setContextPath("/");
        ctx.setWar(new ClassPathResource("webapp").getURI().toString());

        /* Disable directory listings if no index.html is found. */
        ctx.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed",
                "false");

        /* Create the root web application context and set it as a servlet
         * attribute so the dispatcher servlet can find it. */
        GenericWebApplicationContext webApplicationContext =
                new GenericWebApplicationContext();
        webApplicationContext.setParent(applicationContext);
        webApplicationContext.refresh();
        ctx.setAttribute(
                WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
                webApplicationContext);

        ctx.addEventListener(new WebAppInitializer());
        
        BeanDefinition securityExpressionHandler = new RootBeanDefinition(DefaultWebSecurityExpressionHandler.class, Autowire.BY_TYPE.value(), false);
        webApplicationContext.registerBeanDefinition("securityExpressionHandler", securityExpressionHandler);

        return ctx;
    }

    /**
     * Jetty Server bean.
     * <p/>
     * Instantiate the Jetty server.
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server jettyServer() throws IOException {

        /* Create the server. */
        Server server = new Server();

        /* Create a basic connector. */
        ServerConnector httpConnector = new ServerConnector(server);
        httpConnector.setPort(jettyPort);
        server.addConnector(httpConnector);

        server.setHandler(webAppContext());


        return server;
    }
    
}
