package giap.hcmute.vn.repository;

import giap.hcmute.vn.model.VideoEntity;
import java.util.List;
import java.util.Optional;

public interface VideoRepository {
    
    /**
     * Tìm VideoEntity theo ID
     */
    Optional<VideoEntity> findById(int videold);
    
    /**
     * Tìm VideoEntity theo title
     */
    Optional<VideoEntity> findByTitle(String title);
    
    /**
     * Lấy tất cả videos
     */
    List<VideoEntity> findAll();
    
    /**
     * Tìm kiếm VideoEntity theo keyword
     */
    List<VideoEntity> search(String keyword);
    
    /**
     * Tìm tất cả videos theo categoryld
     */
    List<VideoEntity> findByCategoryld(int categoryld);
    
    /**
     * Tìm tất cả videos đang active
     */
    List<VideoEntity> findActiveVideos();
    
    /**
     * Thêm VideoEntity mới
     */
    void save(VideoEntity video);
    
    /**
     * Cập nhật VideoEntity
     */
    void update(VideoEntity video);
    
    /**
     * Xóa VideoEntity theo ID
     */
    void deleteById(int videold);
    
    /**
     * Đếm tổng số VideoEntity
     */
    long count();
    
    /**
     * Tăng số lượt xem của video
     */
    void incrementViews(int videold);
}


