package servlet;

import config.MysqlConfig;
import service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        session.setAttribute("username","Trần Văn A");
        session.setMaxInactiveInterval(8 * 60 * 60); //set thời gian hết hạn của session

        String data = (String) session.getAttribute("username");

        System.out.println("Giá trị của session: "+data);

        Connection connection = MysqlConfig.getConnection();
        String query = "select * from users";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                String email = resultSet.getString("email");
                int roleId = resultSet.getInt("role_id");

                System.out.println("Email là "+email);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi thực thi câu truy vấn login: "+e.getMessage());
        }finally {
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        req.getRequestDispatcher("login.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        System.out.println("Email: "+email+" - "+"Password: "+password);

        LoginService loginService = new LoginService();
        boolean isSuccess = loginService.checkLogin(email,password);

        if(isSuccess) {
            Cookie cookie = new Cookie("username",email);
            cookie.setMaxAge(8*60*60);
            resp.addCookie(cookie);

            resp.sendRedirect(req.getContextPath()+"/roles");
        }else {
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
        System.out.println(isSuccess);

    }
}