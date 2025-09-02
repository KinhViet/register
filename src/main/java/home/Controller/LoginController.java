package home.Controller;

import home.Models.User;
import home.service.UserService;
import home.service.impl.UserServiceImpl;
import home.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            req.getRequestDispatcher(Constant.Path.ADMIN).forward(req, resp); // Chuyển đến admin.jsp
            return;
        }
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    session = req.getSession(true);
                    session.setAttribute("username", cookie.getValue());
                    req.getRequestDispatcher(Constant.Path.ADMIN).forward(req, resp); // Chuyển đến admin.jsp
                    return;
                }
            }
        }
        req.getRequestDispatcher(Constant.Path.LOGIN).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        UserService service = new UserServiceImpl();
        User user = service.login(username, password);
        String alertMsg = "";

        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("username", user.getUserName());
            session.setAttribute("user", user);
            session.setAttribute("successMsg", "Đăng nhập thành công!");

            if (remember != null) {
                Cookie cookie = new Cookie("username", user.getUserName());
                cookie.setMaxAge(60 * 60 * 24 * 7); // 7 days
                resp.addCookie(cookie);
            }

            req.getRequestDispatcher(Constant.Path.ADMIN).forward(req, resp); // Chuyển đến admin.jsp
        } else {
            alertMsg = "Tài khoản hoặc mật khẩu không đúng!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(Constant.Path.LOGIN).forward(req, resp);
        }
    }
}