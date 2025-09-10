package home.Controller;

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
@WebServlet(urlPatterns = "/home")
public class HomeController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(HomeController.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            LOGGER.warning("Unauthorized access to /home");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        LOGGER.info("Accessing /home GET for user: " + session.getAttribute("username"));
        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
    }
}