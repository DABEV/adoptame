package mx.edu.utez.adoptame.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,  Authentication authentication) throws IOException, ServletException {

		boolean hasAdministradorRole = false;
		boolean hasAdoptadorRole = false;
		boolean hasVoluntarioRole = false;

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		for (GrantedAuthority grantedAuthority : authorities) {
            hasAdministradorRole = grantedAuthority.getAuthority().equals("ROL_ADMINISTRADOR") || hasAdministradorRole;
            hasAdoptadorRole = grantedAuthority.getAuthority().equals("ROL_ADOPTADOR") || hasAdoptadorRole;
            hasVoluntarioRole = grantedAuthority.getAuthority().equals("ROL_VOLUNTARIO") || hasVoluntarioRole;
		}

		if (hasAdministradorRole || hasVoluntarioRole || hasAdoptadorRole) {
			redirectStrategy.sendRedirect(request, response, "/index");
		} else {
			redirectStrategy.sendRedirect(request, response, "/login");
		}
	}

}
