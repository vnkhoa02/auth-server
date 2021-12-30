package com.vnk.authserver.Filter;

import com.vnk.authserver.Util.Constants;
import com.vnk.authserver.Util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomFilter extends GenericFilterBean {

    JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (jwtUtil == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            jwtUtil = webApplicationContext.getBean(JwtUtil.class);
        }
        String jwt = ((HttpServletRequest) request).getHeader("Authorization");
        String roleName = jwtUtil.extractRoles(jwt);

        if (roleName != null) {
            if (roleName.equals(Constants.getConstant("Admin")) || roleName.equals(Constants.getConstant("Manager"))) {
                filterChain.doFilter(request, response);
            }else {
                ((HttpServletResponse) response).sendError(401, "Unauthorized");
            }
        }
    }
}