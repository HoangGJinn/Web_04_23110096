package giap.hcmute.vn.controller;

import giap.hcmute.vn.dto.VideoDTO;
import giap.hcmute.vn.service.VideoService;
import giap.hcmute.vn.service.impl.VideoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    
    private final VideoService videoService = new VideoServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<VideoDTO> videos;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            videos = videoService.search(keyword);
            request.setAttribute("keyword", keyword);
        } else {
            videos = videoService.getActiveVideos();
        }
        
        request.setAttribute("videos", videos);
        request.getRequestDispatcher("/WEB-INF/views/product/list.jsp").forward(request, response);
    }
}

