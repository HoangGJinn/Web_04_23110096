package giap.hcmute.vn.controller.admin;

import giap.hcmute.vn.constant.Constant;
import giap.hcmute.vn.dto.UserDTO;
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
import java.util.logging.Logger;

@WebServlet("/admin/user")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class AdminUserServlet extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(AdminUserServlet.class.getName());
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
            // List all users
            try {
                logger.info("Loading users for admin panel...");
                List<UserDTO> users = userService.getAll();
                logger.info("Loaded " + (users != null ? users.size() : 0) + " users");
                request.setAttribute("users", users);
                request.getRequestDispatcher("/WEB-INF/views/admin/user/list.jsp").forward(request, response);
            } catch (Exception e) {
                logger.severe("Error loading users: " + e.getMessage());
                e.printStackTrace();
                request.setAttribute("error", "Lỗi khi tải danh sách users: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading users: " + e.getMessage());
            }
        } else if ("create".equals(action)) {
            // Show create form
            request.getRequestDispatcher("/WEB-INF/views/admin/user/form.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            // Show edit form
            String username = request.getParameter("username");
            UserDTO editUser = userService.get(username);
            if (editUser != null) {
                request.setAttribute("user", editUser);
                request.setAttribute("action", "edit");
                request.getRequestDispatcher("/WEB-INF/views/admin/user/form.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/user");
            }
        } else if ("delete".equals(action)) {
            // Delete user
            String username = request.getParameter("username");
            userService.deleteByUsername(username);
            response.sendRedirect(request.getContextPath() + "/admin/user?message=delete_success");
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String adminStr = request.getParameter("admin");
        String activeStr = request.getParameter("active");
        Boolean admin = adminStr != null && "on".equals(adminStr);
        Boolean active = activeStr != null && "on".equals(activeStr);
        
        UserDTO dto = new UserDTO();
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setEmail(email);
        dto.setFullname(fullname);
        dto.setPhone(phone);
        dto.setAdmin(admin);
        dto.setActive(active);
        
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
            userService.insert(dto);
            response.sendRedirect(request.getContextPath() + "/admin/user?message=create_success");
        } else if ("edit".equals(action)) {
            userService.update(dto);
            response.sendRedirect(request.getContextPath() + "/admin/user?message=edit_success");
        }
    }
}

