package home.Controller;

import home.Models.Category;
import home.service.CategoryService;
import home.service.impl.CategoryServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/admin/category/list")
public class CategoryListController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CategoryListController.class.getName());
    private CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null || !"admin".equals(session.getAttribute("role"))) {
            LOGGER.warning("Unauthorized access to /admin/category/list");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        LOGGER.info("Accessing /admin/category/list GET");
        try {
            List<Category> categories = cateService.getAll();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/views/admin/list-category.jsp").forward(req, resp);
        } catch (Exception e) {
            LOGGER.severe("Error retrieving category list: " + e.getMessage());
            req.setAttribute("alert", "Lấy danh sách danh mục thất bại: " + e.getMessage());
            req.setAttribute("alertType", "alert-danger");
            req.getRequestDispatcher("/views/admin/list-category.jsp").forward(req, resp);
        }
    }
}