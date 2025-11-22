package giap.hcmute.vn.service;

import giap.hcmute.vn.dto.VideoDTO;
import java.util.List;

public interface VideoService {
    /**
     * Thêm video mới
     */
    void insert(VideoDTO videoDTO);
    
    /**
     * Cập nhật video
     */
    void edit(VideoDTO videoDTO);
    
    /**
     * Xóa video theo ID
     */
    void delete(int videold);
    
    /**
     * Lấy video theo ID
     */
    VideoDTO get(int videold);
    
    /**
     * Lấy video theo title
     */
    VideoDTO get(String title);
    
    /**
     * Lấy tất cả videos
     */
    List<VideoDTO> getAll();
    
    /**
     * Tìm kiếm videos theo keyword
     */
    List<VideoDTO> search(String keyword);
    
    /**
     * Lấy tất cả videos theo categoryld
     */
    List<VideoDTO> getByCategoryld(int categoryld);
    
    /**
     * Lấy tất cả videos đang active
     */
    List<VideoDTO> getActiveVideos();
    
    /**
     * Tăng số lượt xem của video
     */
    void incrementViews(int videold);
    
    /**
     * Đếm tổng số videos
     */
    long count();
}


