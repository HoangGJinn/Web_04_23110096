package giap.hcmute.vn.mapper;

import giap.hcmute.vn.model.CategoryEntity;
import giap.hcmute.vn.dto.CategoryDTO;
import java.util.stream.Collectors;

/**
 * Mapper để convert giữa CategoryDTO và CategoryEntity (JPA Entity)
 */
public class CategoryMapper {
    
    /**
     * Convert từ CategoryEntity sang CategoryDTO
     */
    public static CategoryDTO toDTO(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryld(entity.getCategoryld());
        dto.setCategoryname(entity.getCategoryname());
        dto.setCategorycode(entity.getCategorycode());
        dto.setImages(entity.getImages());
        dto.setStatus(entity.getStatus());
        
        // Map username từ quan hệ ManyToOne
        if (entity.getUser() != null) {
            dto.setUsername(entity.getUser().getUsername());
        }
        
        // Map videos từ quan hệ OneToMany (chỉ khi cần thiết, tránh lazy loading issues)
        try {
            if (entity.getVideos() != null && !entity.getVideos().isEmpty()) {
                dto.setVideos(
                    entity.getVideos().stream()
                        .map(VideoMapper::toDTO)
                        .collect(Collectors.toList())
                );
            }
        } catch (Exception e) {
            // Nếu có lỗi khi map videos (lazy loading, etc), bỏ qua
            dto.setVideos(null);
        }
        
        return dto;
    }
    
    /**
     * Convert từ CategoryDTO sang CategoryEntity
     * Lưu ý: User relationship sẽ được set ở Service layer
     */
    public static CategoryEntity toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        
        CategoryEntity entity = new CategoryEntity();
        entity.setCategoryld(dto.getCategoryld());
        entity.setCategoryname(dto.getCategoryname());
        entity.setCategorycode(dto.getCategorycode());
        entity.setImages(dto.getImages());
        entity.setStatus(dto.getStatus());
        // User relationship được set trong service layer
        
        return entity;
    }
    
    /**
     * Update một entity có sẵn từ DTO (dùng cho update operations)
     * Chỉ update các field không null và không rỗng từ DTO
     */
    public static void updateEntity(CategoryEntity entity, CategoryDTO dto) {
        if (entity == null || dto == null) {
            return;
        }
        
        // Tên Category: bắt buộc phải có và không được rỗng
        if (dto.getCategoryname() != null && !dto.getCategoryname().trim().isEmpty()) {
            entity.setCategoryname(dto.getCategoryname());
        }
        
        // Categorycode
        if (dto.getCategorycode() != null) {
            entity.setCategorycode(dto.getCategorycode());
        }
        
        // Images
        if (dto.getImages() != null) {
            entity.setImages(dto.getImages());
        }
        
        // Status
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        
        // categoryld KHÔNG bao giờ update (primary key không được thay đổi)
    }
}
