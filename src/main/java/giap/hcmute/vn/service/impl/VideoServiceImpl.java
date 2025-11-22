package giap.hcmute.vn.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import giap.hcmute.vn.model.VideoEntity;
import giap.hcmute.vn.model.CategoryEntity;
import giap.hcmute.vn.mapper.VideoMapper;
import giap.hcmute.vn.dto.VideoDTO;
import giap.hcmute.vn.repository.VideoRepository;
import giap.hcmute.vn.repository.impl.VideoRepositoryImpl;
import giap.hcmute.vn.repository.CategoryRepository;
import giap.hcmute.vn.repository.impl.CategoryRepositoryImpl;
import giap.hcmute.vn.service.VideoService;

public class VideoServiceImpl implements VideoService {
    
    private final VideoRepository videoRepository = new VideoRepositoryImpl();
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();

    @Override
    public void insert(VideoDTO dto) {
        // 1. Convert DTO → Entity
        VideoEntity video = VideoMapper.toEntity(dto);

        // 2. Tìm category của video
        CategoryEntity category = categoryRepository.findById(dto.getCategoryld())
                                        .orElseThrow(() -> new RuntimeException("Category not found"));

        // 3. Set quan hệ 2 chiều
        video.setCategory(category);
        category.getVideos().add(video);

        // 4. Save video (cascade cũng sẽ hoạt động)
        videoRepository.save(video);
    }

    @Override
    public void edit(VideoDTO newVideoDTO) {
        Optional<VideoEntity> optionalEntity = videoRepository.findById(newVideoDTO.getVideold());
        
        if (!optionalEntity.isPresent()) {
            throw new RuntimeException("Video not found with id: " + newVideoDTO.getVideold());
        }
        
        VideoEntity oldEntity = optionalEntity.get();
        
        // Sử dụng Mapper để update entity
        VideoMapper.updateEntity(oldEntity, newVideoDTO);
        
        // Nếu categoryld thay đổi, cập nhật quan hệ
        if (newVideoDTO.getCategoryld() > 0) {
            CategoryEntity newCategory = categoryRepository.findById(newVideoDTO.getCategoryld())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            
            // Xóa khỏi category cũ nếu cần
            if (oldEntity.getCategory() != null && 
                oldEntity.getCategory().getCategoryld() != newVideoDTO.getCategoryld()) {
                oldEntity.getCategory().getVideos().remove(oldEntity);
            }
            
            // Set category mới
            oldEntity.setCategory(newCategory);
            if (!newCategory.getVideos().contains(oldEntity)) {
                newCategory.getVideos().add(oldEntity);
            }
        }
        
        videoRepository.update(oldEntity);
    }

    @Override
    public void delete(int videold) {
        videoRepository.deleteById(videold);
    }

    @Override
    public VideoDTO get(int videold) {
        Optional<VideoEntity> entity = videoRepository.findById(videold);
        return entity.map(VideoMapper::toDTO).orElse(null);
    }

    @Override
    public VideoDTO get(String title) {
        Optional<VideoEntity> entity = videoRepository.findByTitle(title);
        return entity.map(VideoMapper::toDTO).orElse(null);
    }

    @Override
    public List<VideoDTO> getAll() {
        List<VideoEntity> entities = videoRepository.findAll();
        return entities.stream()
                .map(VideoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoDTO> search(String keyword) {
        List<VideoEntity> entities = videoRepository.search(keyword);
        return entities.stream()
                .map(VideoMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<VideoDTO> getByCategoryld(int categoryld) {
        List<VideoEntity> entities = videoRepository.findByCategoryld(categoryld);
        return entities.stream()
                .map(VideoMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<VideoDTO> getActiveVideos() {
        List<VideoEntity> entities = videoRepository.findActiveVideos();
        return entities.stream()
                .map(VideoMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public void incrementViews(int videold) {
        videoRepository.incrementViews(videold);
    }
    
    @Override
    public long count() {
        return videoRepository.count();
    }
}


