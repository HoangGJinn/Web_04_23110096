package giap.hcmute.vn.controller;

import giap.hcmute.vn.dto.CategoryDTO;
import giap.hcmute.vn.service.CategoryService;
import giap.hcmute.vn.service.impl.CategoryServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    
    private final CategoryService categoryService = new CategoryServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<CategoryDTO> categories = categoryService.getAll();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/category/list.jsp").forward(request, response);
    }
}

