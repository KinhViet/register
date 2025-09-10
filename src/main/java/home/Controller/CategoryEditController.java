package home.Controller;

import home.Models.Category;
import home.service.CategoryService;
import home.service.impl.CategoryServiceImpl;
import home.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.fileupload2.jakarta.JakartaServletDiskFileUpload;
import org.apache.commons.fileupload2.core.DiskFileItem;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/admin/category/edit")
public class CategoryEditController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CategoryEditController.class.getName());
    private CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            LOGGER.warning("Unauthorized access to /admin/category/edit");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String id = req.getParameter("id");
        LOGGER.info("Accessing /admin/category/edit GET with id: " + id);
        try {
            int cateId = Integer.parseInt(id);
            Category category = cateService.get(cateId);
            if (category == null) {
                LOGGER.warning("Category not found: id=" + id);
                req.setAttribute("alert", "Danh mục không tồn tại!");
                req.setAttribute("alertType", "alert-danger");
                req.getRequestDispatcher("/views/admin/list-category.jsp").forward(req, resp);
                return;
            }
            req.setAttribute("category", category);
            req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid category id: " + id);
            req.setAttribute("alert", "ID danh mục không hợp lệ!");
            req.setAttribute("alertType", "alert-danger");
            req.getRequestDispatcher("/views/admin/list-category.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            LOGGER.warning("Unauthorized access to /admin/category/edit POST");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Category category = new Category();
        DiskFileItemFactory factory = DiskFileItemFactory.builder()
                .setPath(Files.createTempFile("upload", ".tmp"))
                .get();
        JakartaServletDiskFileUpload upload = new JakartaServletDiskFileUpload(factory);

        try {
            resp.setContentType("text/html; charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
            req.setCharacterEncoding("UTF-8");

            List<DiskFileItem> items = upload.parseRequest(req);
            for (DiskFileItem item : items) {
                if (item.isFormField()) {
                    if (item.getFieldName().equals("id")) {
                        category.setCateId(Integer.parseInt(item.getString()));
                    } else if (item.getFieldName().equals("name")) {
                        String cateName = item.getString(StandardCharsets.UTF_8);
                        if (cateName == null || cateName.trim().isEmpty()) {
                            throw new IllegalArgumentException("Tên danh mục không được để trống");
                        }
                        category.setCateName(cateName);
                        LOGGER.info("Category name: " + cateName);
                    }
                } else if (item.getFieldName().equals("icon") && item.getSize() > 0) {
                    String originalFileName = item.getName();
                    if (originalFileName != null && !originalFileName.isEmpty()) {
                        int index = originalFileName.lastIndexOf(".");
                        if (index == -1) {
                            throw new IllegalArgumentException("File không có định dạng hợp lệ");
                        }
                        String ext = originalFileName.substring(index + 1).toLowerCase();
                        if (!ext.matches("jpg|jpeg|png|gif")) {
                            throw new IllegalArgumentException("Chỉ hỗ trợ định dạng JPG, PNG, GIF");
                        }
                        String fileName = System.currentTimeMillis() + "." + ext;
                        Path uploadDir = Paths.get(Constant.DIR, Constant.CATEGORY_DIR);
                        Files.createDirectories(uploadDir);
                        if (!Files.isWritable(uploadDir)) {
                            throw new IOException("Thư mục không có quyền ghi: " + uploadDir);
                        }
                        Path filePath = uploadDir.resolve(fileName);
                        item.write(filePath);
                        category.setIcons(Constant.CATEGORY_DIR + "/" + fileName);
                        LOGGER.info("Uploaded new file: " + filePath.toString());
                    }
                }
            }

            if (category.getCateName() == null || category.getCateName().trim().isEmpty()) {
                throw new IllegalArgumentException("Tên danh mục là bắt buộc");
            }

            cateService.edit(category);
            LOGGER.info("Category updated successfully: id=" + category.getCateId());
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error: " + e.getMessage());
            req.setAttribute("alert", e.getMessage());
            req.setAttribute("alertType", "alert-danger");
            req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
        } catch (IOException e) {
            LOGGER.severe("IO error editing category: " + e.getMessage());
            req.setAttribute("alert", "Cập nhật danh mục thất bại: Lỗi ghi file - " + e.getMessage());
            req.setAttribute("alertType", "alert-danger");
            req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
        } catch (Exception e) {
            LOGGER.severe("Error editing category: " + e.getMessage());
            req.setAttribute("alert", "Cập nhật danh mục thất bại: " + e.getMessage());
            req.setAttribute("alertType", "alert-danger");
            req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
        }
    }
}