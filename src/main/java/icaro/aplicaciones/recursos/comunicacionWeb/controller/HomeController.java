package icaro.aplicaciones.recursos.comunicacionWeb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import icaro.aplicaciones.informacion.dominioRecipe2Me.Greeting;
import icaro.aplicaciones.informacion.dominioRecipe2Me.HelloMessage;
import icaro.aplicaciones.informacion.dominioRecipe2Me.Recipe;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfileForm;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;
import icaro.aplicaciones.recursos.persistenciaMongo.ItfUsoPersistenciaMongo;

import java.security.Principal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private ItfUsoPersistenciaMongo repository;
	private ItfUsoComunicacionWeb web;
	@Autowired private SimpMessagingTemplate template;
	
	private List<String> info = new ArrayList<String>();
	private List<String> error = new ArrayList<String>();
	
	@ModelAttribute("info")
	public List<String> getInfoMessage() {
		List<String> copy = new ArrayList(info);
		info.clear();
		return copy;
	}
	
	@ModelAttribute("error")
	public List<String> getErrorMessage() {
		List<String> copy = new ArrayList(error);
		error.clear();
		return copy;
	}

    private static final Logger logger = LoggerFactory
            .getLogger(HomeController.class);


    /**
     * Simple controller for "/" that returns a Thymeleaf view.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
    	model.addAttribute("menu", "home");
        return "home";
    } 
    
    @RequestMapping(value = "/recipe/{idRecipe}", method = RequestMethod.GET)
    public String homeWithRecipe(Locale locale, @PathVariable("idRecipe") String idRecipe, Model model) {
    	try {
			//Recipe recipe = repository.findOne(idRecipe);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	model.addAttribute("menu", "home");
        return "home";
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signin(Model model) {
    	model.addAttribute("user", new UserProfileForm());
    	model.addAttribute("menu", "signin");
        return "signup";
    }
    
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(Model model) {
    	model.addAttribute("menu", "about");
        return "about";
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signin(Model model, @Valid UserProfileForm form,BindingResult result) {
    	if (result.hasErrors()) {
            return "signup";
        }
    	try {
			repository.insertaUsuario(form);
		} catch (Exception e) {
			error.add("Ha ocurrido un error. Si el error persiste contacte con los administradores.");
			return "signup";
		}
    	info.add("Se ha registrado correctamente.");
        return "redirect:/";
    }
    
    @MessageMapping("/hello")
    public void greeting(Message msg, HelloMessage message, Principal principal) throws Exception {
        web.recibirMensajeDelUsuario(message.getName(), principal.getName());
    	//sendMessageToUser(message.getName(), message.getName());
    }
    
    @SubscribeMapping("/user/{userName}/chat")
    public void greeting(Message message, Principal principal, @DestinationVariable String userName ) throws Exception {
        web.notificarConexion((UserProfile) ((UsernamePasswordAuthenticationToken) principal).getPrincipal());
    }
    
    public void sendMessageToUser(String message, String user) {
    	template.convertAndSendToUser(user,"/chat", new Greeting( message ));
    }
    
    public void sendRecipeToUser(String message, String user, Recipe recipe) {
    	template.convertAndSendToUser(user,"/chat", new Greeting( message , recipe ));
    }

	public ItfUsoPersistenciaMongo getRepository() {
		return repository;
	}

	public void setRepository(ItfUsoPersistenciaMongo repository) {
		this.repository = repository;
	}

	public List<String> getInfo() {
		return info;
	}

	public List<String> getError() {
		return error;
	}

	public ItfUsoComunicacionWeb getWeb() {
		return web;
	}

	public void setWeb(ItfUsoComunicacionWeb web) {
		this.web = web;
	}
    
}
