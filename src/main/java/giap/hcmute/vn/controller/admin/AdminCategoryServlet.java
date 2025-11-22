package giap.hcmute.vn.controller.admin;

import giap.hcmute.vn.constant.Constant;
import giap.hcmute.vn.dto.CategoryDTO;
import giap.hcmute.vn.dto.UserDTO;
import giap.hcmute.vn.service.CategoryService;
import giap.hcmute.vn.service.impl.CategoryServiceImpl;
import giap.hcmute.vn.service.UserService;
import giap.hcmute.vn.service.impl.UserServiceImpl;
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

@WebServlet("/admin/category")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class AdminCategoryServlet extends HttpServlet {
    
    private final CategoryService categoryService = new CategoryServiceImpl();
    private final UserService userService = new UserServiceImpl();
    
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
        
        if (action == null || action.isEmpty()) {
            // List all categories
            List<CategoryDTO> categories = categoryService.getAll();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/WEB-INF/views/admin/category/list.jsp").forward(request, response);
        } else if ("create".equals(action)) {
            // Show create form
            List<UserDTO> users = userService.getAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/views/admin/category/form.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            // Show edit form
            int id = Integer.parseInt(request.getParameter("id"));
            CategoryDTO category = categoryService.get(id);
            if (category != null) {
                List<UserDTO> users = userService.getAll();
                request.setAttribute("users", users);
                request.setAttribute("category", category);
                request.setAttribute("action", "edit");
                request.getRequestDispatcher("/WEB-INF/views/admin/category/form.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/category");
            }
        } else if ("delete".equals(action)) {
            // Delete category
            int id = Integer.parseInt(request.getParameter("id"));
            categoryService.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/category?message=delete_success");
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
        String categoryname = request.getParameter("categoryname");
        String categorycode = request.getParameter("categorycode");
        String username = request.getParameter("username");
        String statusStr = request.getParameter("status");
        Boolean status = statusStr != null && "on".equals(statusStr);
        
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryname(categoryname);
        dto.setCategorycode(categorycode);
        dto.setStatus(status);
        dto.setUsername(username);
        
        // Xử lý upload file
        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            String submittedFileName = filePart.getSubmittedFileName();
            if (submittedFileName != null && !submittedFileName.isEmpty()) {
                String extension = "";
                int lastDot = submittedFileName.lastIndexOf('.');
                if (lastDot > 0) {
                    extension = submittedFileName.substring(lastDot);
                }
                String imageFileName = UUID.randomUUID().toString() + extension;
                
                File uploadDir = new File(giap.hcmute.vn.constant.Constant.UPLOAD_PATH);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                
                String filePath = giap.hcmute.vn.constant.Constant.UPLOAD_PATH + imageFileName;
                Files.copy(filePart.getInputStream(), Paths.get(filePath));
                dto.setImages(imageFileName);
            }
        }
        
        if ("create".equals(action)) {
            categoryService.insert(dto);
            response.sendRedirect(request.getContextPath() + "/admin/category?message=create_success");
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dto.setCategoryld(id);
            categoryService.edit(dto);
            response.sendRedirect(request.getContextPath() + "/admin/category?message=edit_success");
        }
    }
}

