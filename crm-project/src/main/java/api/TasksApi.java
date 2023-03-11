package api;

import com.google.gson.Gson;
import model.TaskModel;
import payload.BasicResponse;
import service.ProjectService;
import service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet(name = "TasksApi", urlPatterns = {"/api/tasks","/api/tasks/add","/api/tasks/delete"})
public class TasksApi extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath();
        BasicResponse basicResponse = new BasicResponse();
        switch (url) {
            case "/api/tasks":
                basicResponse = getAllTasks();
                break;
            case "/api/tasks/delete":
                int id = Integer.parseInt(req.getParameter("id"));
                basicResponse = deleteTaskById(id);
                break;
            default:
                basicResponse.setStatusCode(404);
                basicResponse.setMessage("Đường dẫn không tồn tại !");
                break;
        }
        Gson gson = new Gson();
        String dataJson = gson.toJson(basicResponse);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(dataJson);
        printWriter.flush();
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath();
        BasicResponse basicResponse = new BasicResponse();
        switch (url) {
            case "/api/tasks/add":
                String name = req.getParameter("addtask-nametask");
                Date start_date = Date.valueOf(req.getParameter("addtask-startdate"));
                Date end_date = Date.valueOf(req.getParameter("addtask-enddate"));
                int user_id = Integer.parseInt(req.getParameter("addtask-userid"));
                String projectName = req.getParameter("addtask-projectname");

                basicResponse = addNewTask(name,start_date,end_date,user_id,projectName);
                break;
            default:
                basicResponse.setStatusCode(404);
                basicResponse.setMessage("Đường dẫn không tồn tại !");
                break;
        }
        Gson gson = new Gson();
        String dataJson = gson.toJson(basicResponse);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(dataJson);
        printWriter.flush();
        printWriter.close();
    }

    private BasicResponse getAllTasks() {
        BasicResponse response = new BasicResponse();
        TaskService taskService = new TaskService();

        response.setData(taskService.getAllTasks());
        response.setStatusCode(200);

        return response;
    }

    private BasicResponse addNewTask(String name, Date start_date, Date end_date,int user_id,String projectName) {
        BasicResponse response = new BasicResponse();
        TaskService taskService = new TaskService();
        response.setData(taskService.addNewTask(name,start_date,end_date,user_id,projectName));
        response.setStatusCode(200);

        return response;
    }

    private BasicResponse deleteTaskById(int id) {
        BasicResponse response = new BasicResponse();
        TaskService taskService = new TaskService();
        response.setData(taskService.deleteTaskById(id));
        response.setStatusCode(200);
        response.setMessage("Xoa task thanh cong");

        return response;
    }

}
