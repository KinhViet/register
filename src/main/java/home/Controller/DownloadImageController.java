package home.Controller;

import home.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/image")
public class DownloadImageController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(DownloadImageController.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fname");
        if (fileName == null || fileName.trim().isEmpty()) {
            LOGGER.warning("Invalid or missing fname parameter");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số fname");
            return;
        }

        LOGGER.info("Accessing /image with fname: " + fileName);
        File file = new File(Constant.DIR + "\\" + fileName);
        if (!file.exists()) {
            LOGGER.warning("Image not found: " + file.getAbsolutePath());
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File không tồn tại");
            return;
        }

        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        String contentType;
        switch (ext) {
            case "jpg":
            case "jpeg":
                contentType = "image/jpeg";
                break;
            case "png":
                contentType = "image/png";
                break;
            case "gif":
                contentType = "image/gif";
                break;
            default:
                LOGGER.warning("Unsupported file format: " + ext);
                resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Định dạng file không được hỗ trợ");
                return;
        }

        resp.setContentType(contentType);
        try (FileInputStream fis = new FileInputStream(file)) {
            IOUtils.copy(fis, resp.getOutputStream());
            LOGGER.info("Served image: " + file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.severe("Error serving image: " + e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải file");
        }
    }
}