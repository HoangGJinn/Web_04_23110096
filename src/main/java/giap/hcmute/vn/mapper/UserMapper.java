package giap.hcmute.vn.mapper;

import giap.hcmute.vn.model.UserEntity;
import giap.hcmute.vn.dto.UserDTO;

import java.util.stream.Collectors;

public class UserMapper {
    
    /**
     * Convert từ UserEntity sang UserDTO (bao gồm categories)
     */
    public static UserDTO toDTO(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setPhone(entity.getPhone());
        dto.setFullname(entity.getFullname());
        dto.setEmail(entity.getEmail());
        dto.setAdmin(entity.getAdmin());
        dto.setActive(entity.getActive());
        dto.setImages(entity.getImages());

        // ⭐ Map CategoryEntity → CategoryDTO
        try {
            if (entity.getCategories() != null && !entity.getCategories().isEmpty()) {
                dto.setCategories(
                        entity.getCategories().stream()
                                .map(CategoryMapper::toDTO)
                                .collect(Collectors.toList())
                );
            }
        } catch (Exception e) {
            // Nếu có lỗi khi map categories, set null để tránh crash
            dto.setCategories(null);
            e.printStackTrace();
        }
        
        return dto;
    }
    
    /**
     * Convert từ UserDTO sang UserEntity
     * (CATEGORY KHÔNG map vì tránh vòng lặp và vì Entity luôn được tạo từ Service)
     */
    public static UserEntity toEntity(UserDTO model) {
        if (model == null) {
            return null;
        }
        
        UserEntity entity = new UserEntity();
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setPassword(model.getPassword());
        entity.setPhone(model.getPhone());
        entity.setFullname(model.getFullname());
        entity.setEmail(model.getEmail());
        entity.setAdmin(model.getAdmin());
        entity.setActive(model.getActive());
        entity.setImages(model.getImages());
        // Category xử lý riêng trong service layer
        
        return entity;
    }
    
    /**
     * Update entity có sẵn bằng DTO
     * KHÔNG update categories tại đây.
     */
    public static void updateEntity(UserEntity entity, UserDTO model) {
        if (entity == null || model == null) {
            return;
        }
        
        if (model.getPassword() != null && !model.getPassword().trim().isEmpty()) {
            entity.setPassword(model.getPassword());
        }
        
        if (model.getPhone() != null) {
            entity.setPhone(model.getPhone());
        }
        
        if (model.getFullname() != null && !model.getFullname().trim().isEmpty()) {
            entity.setFullname(model.getFullname());
        }
        
        if (model.getEmail() != null && !model.getEmail().trim().isEmpty()) {
            entity.setEmail(model.getEmail());
        }
        
        if (model.getAdmin() != null) {
            entity.setAdmin(model.getAdmin());
        }
        
        if (model.getActive() != null) {
            entity.setActive(model.getActive());
        }
        
        if (model.getImages() != null) {
            entity.setImages(model.getImages());
        }
        
        // → Categories được xử lý bởi CategoryService hoặc UserService riêng
    }
}
