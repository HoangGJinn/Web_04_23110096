package giap.hcmute.vn.controller.admin;

import giap.hcmute.vn.constant.Constant;
import giap.hcmute.vn.dto.CategoryDTO;
import giap.hcmute.vn.dto.UserDTO;
import giap.hcmute.vn.dto.VideoDTO;
import giap.hcmute.vn.service.CategoryService;
import giap.hcmute.vn.service.impl.CategoryServiceImpl;
import giap.hcmute.vn.service.VideoService;
import giap.hcmute.vn.service.impl.VideoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@WebServlet("/admin/video")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class AdminVideoServlet extends HttpServlet {
    
    private final VideoService videoService = new VideoServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(Constant.SESSION_ACCOUNT);
        
        if (user == null || !Boolean.TRUE.equals(user.getAdmin())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        String keyword = request.getParameter("keyword");
        
        if (action == null || action.isEmpty()) {
            // List all videos hoặc tìm kiếm
            List<VideoDTO> videos;
            if (keyword != null && !keyword.trim().isEmpty()) {
                videos = videoService.search(keyword);
                request.setAttribute("keyword", keyword);
            } else {
                videos = videoService.getAll();
            }
            request.setAttribute("videos", videos);
            request.getRequestDispatcher("/WEB-INF/views/admin/video/list.jsp").forward(request, response);
        } else if ("create".equals(action)) {
            // Show create form
            List<CategoryDTO> categories = categoryService.getAll();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/WEB-INF/views/admin/video/form.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            // Show edit form
            int id = Integer.parseInt(request.getParameter("id"));
            VideoDTO video = videoService.get(id);
            if (video != null) {
                List<CategoryDTO> categories = categoryService.getAll();
                request.setAttribute("categories", categories);
                request.setAttribute("video", video);
                request.setAttribute("action", "edit");
                request.getRequestDispatcher("/WEB-INF/views/admin/video/form.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/video");
            }
        } else if ("delete".equals(action)) {
            // Delete video
            int id = Integer.parseInt(request.getParameter("id"));
            videoService.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/video?message=delete_success");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(Constant.SESSION_ACCOUNT);
        
        if (user == null || !Boolean.TRUE.equals(user.getAdmin())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String categoryldStr = request.getParameter("categoryld");
        String activeStr = request.getParameter("active");
        int categoryld = Integer.parseInt(categoryldStr);
        Boolean active = activeStr != null && "on".equals(activeStr);
        
        VideoDTO dto = new VideoDTO();
        dto.setTitle(title);
        dto.setDescription(description);
        dto.setCategoryld(categoryld);
        dto.setActive(active);
        dto.setViews(0);
        
        // Xử lý upload file
        Part filePart = request.getPart("poster");
        if (filePart != null && filePart.getSize() > 0) {
            String submittedFileName = filePart.getSubmittedFileName();
            if (submittedFileName != null && !submittedFileName.isEmpty()) {
                String extension = "";
                int lastDot = submittedFileName.lastIndexOf('.');
                if (lastDot > 0) {
                    extension = submittedFileName.substring(lastDot);
                }
                String posterFileName = UUID.randomUUID().toString() + extension;
                
                File uploadDir = new File(giap.hcmute.vn.constant.Constant.UPLOAD_PATH);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                
                String filePath = giap.hcmute.vn.constant.Constant.UPLOAD_PATH + posterFileName;
                Files.copy(filePart.getInputStream(), Paths.get(filePath));
                dto.setPoster(posterFileName);
            }
        }
        
        if ("create".equals(action)) {
            videoService.insert(dto);
            response.sendRedirect(request.getContextPath() + "/admin/video?message=create_success");
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dto.setVideold(id);
            videoService.edit(dto);
            response.sendRedirect(request.getContextPath() + "/admin/video?message=edit_success");
        }
    }
}

