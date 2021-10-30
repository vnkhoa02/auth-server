package com.vnk.authserver.Filter;

import com.vnk.authserver.Util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class RolesPermissionFilter extends GenericFilterBean {

    JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (jwtUtil == null) {
            // idk why it's working
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            jwtUtil = webApplicationContext.getBean(JwtUtil.class);
        }
        String jwt = ((HttpServletRequest) request).getHeader("Authorization").substring(7);
        String roles = jwtUtil.extractRoles(jwt);
        if (roles.equals("Manager") || roles.equals("Admin")) {
            filterChain.doFilter(request, response);
        }

    }

}