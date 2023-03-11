package api;

import com.google.gson.Gson;
import model.RoleModel;
import payload.BasicResponse;
import service.RoleService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="RolesApi", urlPatterns = {"/api/roles","/api/roles/delete","/api/roles/add"})
public class RolesApi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath(); //trả ra urlPattern mà user đang gọi
        BasicResponse basicResponse = new BasicResponse();
        switch (url) {
            case "/api/roles":
                basicResponse = getAllRole();
                break;
            case "/api/roles/delete":
                int id = Integer.parseInt(req.getParameter("id"));
                basicResponse = deleteRoleById(id);
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
        String url = req.getServletPath(); //trả ra urlPattern mà user đang gọi
        BasicResponse basicResponse = new BasicResponse();
        switch (url) {
            case "/api/roles/add":
                String name = req.getParameter("name");
                String desc = req.getParameter("desc");
                basicResponse = addNewRole(name,desc);
                break;
//            case "/api/roles/delete":
//                int id = Integer.parseInt(req.getParameter("id"));
//                basicResponse = deleteRoleById(id);
//                break;
            default:
                basicResponse.setStatusCode(404);
                basicResponse.setMessage("Đường dẫn không tồn tại !");
                break;
        }

        Gson gson = new Gson();
        String dataJson = gson.toJson(basicResponse);

        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(dataJson);
        printWriter.flush();
        printWriter.close();
    }

    private BasicResponse addNewRole(String name, String desc) {
        BasicResponse response = new BasicResponse();
        RoleService roleService = new RoleService();
        response.setStatusCode(200);
        response.setMessage("Thêm thành công");
        response.setData(roleService.addNewRole(name,desc));

        return response;
    }

    private BasicResponse deleteRoleById(int id) {
        BasicResponse response = new BasicResponse();
        RoleService roleService = new RoleService();
        response.setStatusCode(200);
        response.setMessage("Xóa thành công");
        response.setData(roleService.deleteRoleById(id));

        return response;
    }

    private BasicResponse getAllRole() {
        BasicResponse response = new BasicResponse();
        RoleService roleService = new RoleService();
        List<RoleModel> list = roleService.getAllRoles();
        response.setStatusCode(200);
        response.setData(list);

        return response;
    }
}
