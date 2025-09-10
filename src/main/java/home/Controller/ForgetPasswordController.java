package home.Controller;

import home.Models.User;
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
@WebServlet(urlPatterns = {"/forgetpassword", "/resetpassword"})
public class ForgetPasswordController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        if (token != null) {
            // form đặt lại mật khẩu
            UserService service = new UserServiceImpl();
            User user = service.findByResetToken(token);
            if (user != null) {
                req.setAttribute("token", token);
                req.getRequestDispatcher("/views/resetpassword.jsp").forward(req, resp);
            } else {
                req.setAttribute("alert", "Liên kết không hợp lệ hoặc đã hết hạn!");
                req.setAttribute("alertType", "alert-danger");
                req.getRequestDispatcher(Constant.Path.FORGET_PASSWORD).forward(req, resp);
            }
        } else {
            // Hiển thị form nhập email
            req.getRequestDispatcher(Constant.Path.FORGET_PASSWORD).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String token = req.getParameter("token");
        String newPassword = req.getParameter("newPassword");

        UserService service = new UserServiceImpl();
        String alertMsg = "";
        String alertType = "alert-danger";

        if (email != null) {
            // Email request
            boolean success = service.sendResetPasswordEmail(email);
            if (success) {
                alertMsg = "Liên kết khôi phục đã được gửi đến email của bạn!";
                alertType = "alert-success";
            } else {
                alertMsg = "Email không tồn tại!";
            }
            req.setAttribute("alert", alertMsg);
            req.setAttribute("alertType", alertType);
            req.getRequestDispatcher(Constant.Path.FORGET_PASSWORD).forward(req, resp);
        } else if (token != null && newPassword != null) {
            // password reset processing
            User user = service.findByResetToken(token);
            if (user != null) {
                user.setPassWord(newPassword);
                user.setResetToken(null); // reset token sau sử dụng
                service.updatePassword(user);
                alertMsg = "Mật khẩu đã được cập nhật thành công! Vui lòng đăng nhập lại.";
                alertType = "alert-success";
                req.setAttribute("alert", alertMsg);
                req.setAttribute("alertType", alertType);
                req.getRequestDispatcher(Constant.Path.LOGIN).forward(req, resp);
            } else {
                alertMsg = "Liên kết không hợp lệ hoặc đã hết hạn!";
                req.setAttribute("alert", alertMsg);
                req.setAttribute("alertType", alertType);
                req.getRequestDispatcher(Constant.Path.FORGET_PASSWORD).forward(req, resp);
            }
        }
    }
}