package sec.project.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomUrlAuthenticationFailureHandler implements AuthenticationFailureHandler{
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
			throws IOException, ServletException {

		handle(request, response);
		
	}
	
	protected void handle(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		redirectStrategy.sendRedirect(request, response, "/login");
	}

}
