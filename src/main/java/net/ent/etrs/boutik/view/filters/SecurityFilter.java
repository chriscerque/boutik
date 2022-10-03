package net.ent.etrs.boutik.view.filters;

import net.ent.etrs.boutik.view.security.SessionBean;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(filterName = "SecurityFilter", urlPatterns = "*")
public class SecurityFilter implements Filter {
    
    @Inject
    private SessionBean sessionBean;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        String uri = req.getRequestURI().substring(req.getContextPath().length());
        if (!this.sessionBean.getAlwaysAuthorized().contains(uri) && !uri.startsWith("/javax.faces.resource")) {
            if (Objects.isNull(this.sessionBean.getUser())) {
                resp.sendRedirect(req.getContextPath() + "/security/login.xhtml");
            } else if (!this.sessionBean.isAuthorized(uri)) {
                resp.sendRedirect(req.getContextPath() + "/index.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
