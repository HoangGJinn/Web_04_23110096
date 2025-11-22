package giap.hcmute.vn.mapper;

import giap.hcmute.vn.model.VideoEntity;
import giap.hcmute.vn.dto.VideoDTO;

/**
 * Mapper để convert giữa VideoDTO và VideoEntity (JPA Entity)
 */
public class VideoMapper {
    
    /**
     * Convert từ VideoEntity sang VideoDTO
     */
    public static VideoDTO toDTO(VideoEntity entity) {
        if (entity == null) {
            return null;
        }
        
        VideoDTO dto = new VideoDTO();
        dto.setVideold(entity.getVideold());
        dto.setTitle(entity.getTitle());
        dto.setPoster(entity.getPoster());
        dto.setViews(entity.getViews());
        dto.setDescription(entity.getDescription());
        dto.setActive(entity.getActive());
        
        // Map categoryld từ quan hệ ManyToOne
        if (entity.getCategory() != null) {
            dto.setCategoryld(entity.getCategory().getCategoryld());
        }
        
        return dto;
    }
    
    /**
     * Convert từ VideoDTO sang VideoEntity
     * Lưu ý: Category relationship sẽ được set ở Service layer
     */
    public static VideoEntity toEntity(VideoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        VideoEntity entity = new VideoEntity();
        entity.setVideold(dto.getVideold());
        entity.setTitle(dto.getTitle());
        entity.setPoster(dto.getPoster());
        entity.setViews(dto.getViews());
        entity.setDescription(dto.getDescription());
        entity.setActive(dto.getActive());
        // Category relationship được set trong service layer
        
        return entity;
    }
    
    /**
     * Update một entity có sẵn từ DTO (dùng cho update operations)
     * Chỉ update các field không null và không rỗng từ DTO
     */
    public static void updateEntity(VideoEntity entity, VideoDTO dto) {
        if (entity == null || dto == null) {
            return;
        }
        
        // Title: bắt buộc phải có và không được rỗng
        if (dto.getTitle() != null && !dto.getTitle().trim().isEmpty()) {
            entity.setTitle(dto.getTitle());
        }
        
        // Poster cho phép null hoặc rỗng
        if (dto.getPoster() != null) {
            entity.setPoster(dto.getPoster());
        }
        
        // Views
        if (dto.getViews() != null) {
            entity.setViews(dto.getViews());
        }
        
        // Description
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        
        // Active
        if (dto.getActive() != null) {
            entity.setActive(dto.getActive());
        }
        
        // videold KHÔNG bao giờ update (primary key không được thay đổi)
    }
}


