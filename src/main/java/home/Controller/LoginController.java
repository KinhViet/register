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
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Accessing /login GET");
        req.getRequestDispatcher(Constant.Path.LOGIN).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        LOGGER.info("Processing login for username: " + username);

        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html; charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");

            User user = userService.login(username, password);
            if (user == null) {
                LOGGER.warning("Login failed for username: " + username);
                req.setAttribute("alert", "Tên đăng nhập hoặc mật khẩu không đúng!");
                req.setAttribute("alertType", "alert-danger");
                req.getRequestDispatcher(Constant.Path.LOGIN).forward(req, resp);
                return;
            }

            session.setAttribute("username", user.getUserName());
            session.setAttribute("role", user.getRoleid());

            if ("admin".equals(user.getRoleid())) {
                LOGGER.info("Admin login successful, redirecting to /admin/category/list");
                resp.sendRedirect(req.getContextPath() + "/admin/category/list");
            } else {
                LOGGER.info("User login successful, redirecting to /home");
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } catch (Exception e) {
            LOGGER.severe("Error during login: " + e.getMessage());
            req.setAttribute("alert", "Đăng nhập thất bại: " + e.getMessage());
            req.setAttribute("alertType", "alert-danger");
            req.getRequestDispatcher(Constant.Path.LOGIN).forward(req, resp);
        }
    }
}