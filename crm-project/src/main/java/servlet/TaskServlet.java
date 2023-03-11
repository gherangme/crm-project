package servlet;

import model.TaskModel;
import repository.UsersRepository;
import service.ProjectService;
import service.TaskService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "TaskServlet",urlPatterns = {"/tasks","/task-add"})
public class TaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath();
        switch (url) {
            case "/tasks":
                TaskService taskService = new TaskService();
                req.setAttribute("tasks",taskService.getAllTasks());
                req.getRequestDispatcher("task.jsp").forward(req,resp);
                break;
            case "/task-add":
                UserService userService = new UserService();
                ProjectService projectService = new ProjectService();
                req.setAttribute("projects",projectService.getAllProjects());
                req.setAttribute("users",userService.getAllUsers());
                req.getRequestDispatcher("task-add.jsp").forward(req,resp);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

    }
}
