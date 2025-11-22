package giap.hcmute.vn.controller;

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
import java.util.UUID;

@WebServlet("/profile")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,  // 1MB
    maxFileSize = 1024 * 1024 * 10,    // 10MB
    maxRequestSize = 1024 * 1024 * 50  // 50MB
)
public class ProfileServlet extends HttpServlet {
    
    private final UserService userService = new UserServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(Constant.SESSION_ACCOUNT);
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Lấy thông tin user mới nhất
        user = userService.get(user.getUsername());
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDTO currentUser = (UserDTO) session.getAttribute(Constant.SESSION_ACCOUNT);
        
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String imageFileName = null;
        
        // Xử lý upload file
        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            String submittedFileName = filePart.getSubmittedFileName();
            if (submittedFileName != null && !submittedFileName.isEmpty()) {
                // Tạo tên file unique để tránh trùng
                String extension = "";
                int lastDot = submittedFileName.lastIndexOf('.');
                if (lastDot > 0) {
                    extension = submittedFileName.substring(lastDot);
                }
                imageFileName = UUID.randomUUID().toString() + extension;
                
                // Đảm bảo thư mục upload tồn tại
                File uploadDir = new File(Constant.UPLOAD_PATH);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                
                // Lưu file
                String filePath = Constant.UPLOAD_PATH + imageFileName;
                Files.copy(filePart.getInputStream(), Paths.get(filePath));
            }
        }
        
        // Cập nhật profile
        boolean success = userService.updateProfile(
            currentUser.getUsername(), 
            fullname, 
            phone, 
            imageFileName
        );
        
        if (success) {
            // Cập nhật session với thông tin mới
            UserDTO updatedUser = userService.get(currentUser.getUsername());
            session.setAttribute(Constant.SESSION_ACCOUNT, updatedUser);
            request.setAttribute("message", "Cập nhật profile thành công!");
            request.setAttribute("user", updatedUser);
        } else {
            request.setAttribute("error", "Cập nhật profile thất bại!");
            request.setAttribute("user", currentUser);
        }
        
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
}

