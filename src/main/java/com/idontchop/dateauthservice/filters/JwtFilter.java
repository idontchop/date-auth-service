package com.idontchop.dateauthservice.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.idontchop.dateauthservice.services.JwtService;
import com.idontchop.dateauthservice.services.UserDetailServiceImpl;

public class JwtFilter extends GenericFilterBean {
	
	Logger logger = LoggerFactory.getLogger(JwtFilter.class);
	
	private UserDetailServiceImpl userDetailsService;
	
	private JwtService jwtService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// make sure needed services are init
		initUserDetailsService(request);
		
		String username = jwtService.getAuthentication((HttpServletRequest) request);
		
		if ( username != null ) {
			try {
				
				UsernamePasswordAuthenticationToken auth =
						new UsernamePasswordAuthenticationToken
						( username, null, AuthorityUtils.createAuthorityList("USER") );
				
				SecurityContextHolder.getContext().setAuthentication(auth);
				
			} catch (Exception e) {
				logger.debug ("Service load user: " + e.getMessage());
			}
		}
		
		
	}

	/**
	 * Necessary as App context may not be available.
	 * 
	 * @param req
	 */
	private void initUserDetailsService (ServletRequest req) {
		if ( jwtService == null ) {
            ServletContext servletContext = req.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            jwtService = webApplicationContext.getBean(JwtService.class);
		}
		if ( userDetailsService == null ) {
            ServletContext servletContext = req.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            userDetailsService = webApplicationContext.getBean(UserDetailServiceImpl.class);
		}
	}
}
