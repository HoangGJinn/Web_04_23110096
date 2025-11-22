package giap.hcmute.vn.service.impl;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import giap.hcmute.vn.model.CategoryEntity;
import giap.hcmute.vn.model.UserEntity;
import giap.hcmute.vn.mapper.CategoryMapper;
import giap.hcmute.vn.dto.CategoryDTO;
import giap.hcmute.vn.repository.CategoryRepository;
import giap.hcmute.vn.repository.impl.CategoryRepositoryImpl;
import giap.hcmute.vn.repository.impl.UserRepositoryImpl;
import giap.hcmute.vn.service.CategoryService;
import giap.hcmute.vn.repository.UserRepository;
import giap.hcmute.vn.constant.Constant;


public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void insert(CategoryDTO dto) {

        // 1. Convert DTO → Entity
        CategoryEntity cate = CategoryMapper.toEntity(dto);

        // 2. Tìm user của category
        UserEntity user = userRepository.findByUsername(dto.getUsername())
                                        .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Set quan hệ 2 chiều
        cate.setUser(user);
        user.getCategories().add(cate);

        // 4. Save category (cascade cũng sẽ hoạt động)
        categoryRepository.save(cate);
    }


    @Override
    public void edit(CategoryDTO newCategoryDTO){
        Optional<CategoryEntity> optionalEntity = categoryRepository.findById(newCategoryDTO.getCategoryld());
        
        if (!optionalEntity.isPresent()) {
            throw new RuntimeException("Category not found with id: " + newCategoryDTO.getCategoryld());
        }
        
        CategoryEntity oldEntity = optionalEntity.get();
        
        // Nếu có upload icon mới, xóa ảnh cũ trước
        if (newCategoryDTO.getImages() != null && !newCategoryDTO.getImages().isEmpty()) {
            // Xóa ảnh cũ nếu có (chỉ lưu tên file trong database)
            if (oldEntity.getImages() != null && !oldEntity.getImages().isEmpty()) {
                // Sử dụng đường dẫn từ Constant (D:/uploads/)
                File oldFile = new File(Constant.UPLOAD_PATH + oldEntity.getImages());
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
        }
        
        // Sử dụng Mapper để update entity
        CategoryMapper.updateEntity(oldEntity, newCategoryDTO);
        
        // Nếu username thay đổi, cập nhật quan hệ
        if (newCategoryDTO.getUsername() != null && !newCategoryDTO.getUsername().trim().isEmpty()) {
            UserEntity newUser = userRepository.findByUsername(newCategoryDTO.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Xóa khỏi user cũ nếu cần
            if (oldEntity.getUser() != null && 
                !oldEntity.getUser().getUsername().equals(newCategoryDTO.getUsername())) {
                oldEntity.getUser().getCategories().remove(oldEntity);
            }
            
            // Set user mới
            oldEntity.setUser(newUser);
            if (!newUser.getCategories().contains(oldEntity)) {
                newUser.getCategories().add(oldEntity);
            }
        }
        
        categoryRepository.update(oldEntity);
    }

    @Override
    public void delete(int categoryld){
        // Lấy category trước khi xóa để xóa ảnh
        Optional<CategoryEntity> optionalEntity = categoryRepository.findById(categoryld);
        if (optionalEntity.isPresent()) {
            CategoryEntity entity = optionalEntity.get();
            // Xóa ảnh nếu có (chỉ lưu tên file trong database)
            if (entity.getImages() != null && !entity.getImages().isEmpty()) {
                File imageFile = new File(Constant.UPLOAD_PATH + entity.getImages());
                if (imageFile.exists()) {
                    imageFile.delete();
                }
            }
        }
        categoryRepository.deleteById(categoryld);
    }

    @Override
    public CategoryDTO get(int categoryld){
        Optional<CategoryEntity> entity = categoryRepository.findById(categoryld);
        return entity.map(CategoryMapper::toDTO).orElse(null);
    }

    @Override
    public CategoryDTO get(String categoryname){
        Optional<CategoryEntity> entity = categoryRepository.findByName(categoryname);
        return entity.map(CategoryMapper::toDTO).orElse(null);
    }

    @Override
    public List<CategoryDTO> getAll(){
        List<CategoryEntity> entities = categoryRepository.findAll();
        return entities.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> search(String keyword){
        List<CategoryEntity> entities = categoryRepository.search(keyword);
        return entities.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CategoryDTO> getByUserId(int userId) {
        // Method này cần được cập nhật để sử dụng username
        // Tạm thời trả về empty list
        return java.util.Collections.emptyList();
    }
    
    /**
     * Lấy tất cả categories theo username
     */
    public List<CategoryDTO> getByUsername(String username) {
        List<CategoryEntity> entities = categoryRepository.findByUsername(username);
        return entities.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
