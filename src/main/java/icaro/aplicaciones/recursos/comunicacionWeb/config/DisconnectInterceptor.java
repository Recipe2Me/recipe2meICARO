package icaro.aplicaciones.recursos.comunicacionWeb.config;

import icaro.aplicaciones.informacion.dominioRecipe2Me.UserProfile;
import icaro.aplicaciones.recursos.comunicacionWeb.ItfUsoComunicacionWeb;

import java.security.Principal;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class DisconnectInterceptor extends ChannelInterceptorAdapter {
	
	private ItfUsoComunicacionWeb web;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {

	    MessageHeaders headers = message.getHeaders();
	    SimpMessageType type = (SimpMessageType) headers.get("simpMessageType");
	    if (type.equals(SimpMessageType.DISCONNECT)) {
	    	UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) headers.get("simpUser");
	    	UserProfile user = (UserProfile) principal.getPrincipal();
	    	try {
				web.notificarDesconexion(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    

	    return message;
	}

	public void setWeb(ItfUsoComunicacionWeb web) {
		this.web = web;
	}
	
}
