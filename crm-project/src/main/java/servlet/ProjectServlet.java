package servlet;

import service.ProjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProjectServlet",urlPatterns = {"/projects","/project-add","/project-details"})
public class ProjectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath();
        switch (url) {
            case "/projects":
                ProjectService projectService = new ProjectService();
                req.setAttribute("projects",projectService.getAllProjects());
                req.getRequestDispatcher("groupwork.jsp").forward(req,resp);
                break;
            case "/project-add":
                req.getRequestDispatcher("groupwork-add.jsp").forward(req,resp);
                break;
            case "/project-details":
                req.getRequestDispatcher("groupwork-details.jsp").forward(req,resp);
            default:
                break;
        }
    }
}
