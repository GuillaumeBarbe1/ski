package net.ent.etrs.ski.filters;


import net.ent.etrs.ski.model.entities.references.Role;
import net.ent.etrs.ski.view.index.LoginBean;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

@WebFilter()
public class RequestFilter implements Filter
{
    @Inject
    private LoginBean loginBean;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {

        if(loginBean.getRole()!=null && loginBean.getRole().equals(Role.ADMIN))
        {
            LocalDateTime start = LocalDateTime.now();
            chain.doFilter(request, response);
            LocalDateTime stop = LocalDateTime.now();

            System.out.println(request.getLocalAddr());
            System.out.println(Duration.between(start, stop));
        }
        else
        {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect("../login.xhtml");
        }
    }
}
