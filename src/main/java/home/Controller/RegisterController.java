package home.Controller;

import home.service.UserService;
import home.service.impl.UserServiceImpl;
import home.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/register")
public class RegisterController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String username = req.getParameter("username");
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");

        UserService service = new UserServiceImpl();
        String alertMsg = "";
        String alertType = "alert-danger";

        if (service.register(email, password, username, fullname, phone)) {
            alertMsg = "Đăng ký thành công! Vui lòng kiểm tra email để xác nhận.";
            alertType = "alert-success";
            req.setAttribute("alert", alertMsg);
            req.setAttribute("alertType", alertType);
            req.getRequestDispatcher(Constant.Path.LOGIN).forward(req, resp); // Chuyển hướng về login.jsp
        } else {
            alertMsg = "Email, username hoặc số điện thoại đã tồn tại!";
            req.setAttribute("alert", alertMsg);
            req.setAttribute("alertType", alertType);
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
        }
    }
}