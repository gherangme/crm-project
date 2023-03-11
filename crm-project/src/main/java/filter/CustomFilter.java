package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/roles","/role-add","/users","/user-add","/user-details","/projects","/project-add","/project-details","/tasks","/task-add"})
public class CustomFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Đây là filter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length>0) {
            boolean isLogin = false;
            for (Cookie cookie : cookies) {
                if("username".equals(cookie.getName())) {
                    isLogin = true;
                    break;
                }else {
                    isLogin = false;
                }
            }

            if(isLogin) {
                filterChain.doFilter(request,response);
            }else {
                response.sendRedirect(request.getContextPath()+"/login");
            }
        }else {
            response.sendRedirect(request.getContextPath()+"/login");
        }

    }
}
