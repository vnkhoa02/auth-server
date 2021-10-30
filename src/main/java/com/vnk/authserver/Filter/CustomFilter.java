package com.vnk.authserver.Filter;

import com.vnk.authserver.Service.RolesService;
import com.vnk.authserver.Util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class CustomFilter extends GenericFilterBean {

    JwtUtil jwtUtil;

    RolesService rolesService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (jwtUtil == null || rolesService == null) {
            // idk why it's working
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            jwtUtil = webApplicationContext.getBean(JwtUtil.class);
            rolesService = webApplicationContext.getBean(RolesService.class);
        }
        String jwt = ((HttpServletRequest) request).getHeader("Authorization").substring(7);
        String rolesId = jwtUtil.extractRoles(jwt);
        String roles = rolesService.getById(Long.valueOf(rolesId)).getName();
        if (roles.equals("Manager") || roles.equals("Admin")) {
            filterChain.doFilter(request, response);
        }
    }

}