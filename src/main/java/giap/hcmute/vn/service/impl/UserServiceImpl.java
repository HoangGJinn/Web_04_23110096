package giap.hcmute.vn.service.impl;

import giap.hcmute.vn.model.UserEntity;
import giap.hcmute.vn.mapper.UserMapper;
import giap.hcmute.vn.dto.UserDTO;
import giap.hcmute.vn.repository.UserRepository;
import giap.hcmute.vn.repository.impl.UserRepositoryImpl;
import giap.hcmute.vn.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public UserDTO login(String username, String password) {
        // Xác thực password trực tiếp từ repository
        if (userRepository.verifyPassword(username, password)) {
            return this.get(username);
        }
        return null;
    }

    @Override
    public UserDTO get(String username) {
        Optional<UserEntity> entity = userRepository.findByUsername(username);
        return entity.map(UserMapper::toDTO).orElse(null);
    }
    
    @Override
    public java.util.List<UserDTO> getAll() {
        java.util.List<UserEntity> entities = userRepository.findAll();
        return entities.stream()
                .map(UserMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public boolean register(String email, String password, String username, String fullname, String phone) {

        // Nếu username đã tồn tại → thất bại
        if (userRepository.existsByUsername(username)) {
            return false;
        }

        // Tạo entity mới
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setEmail(email);
        entity.setFullname(fullname);
        entity.setPhone(phone);
        entity.setAdmin(false); // Mặc định không phải admin
        entity.setActive(true); // Mặc định active
        entity.setImages(null); // Chưa có ảnh

        userRepository.save(entity);
        return true;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public void insert(UserDTO UserDTO) {
        UserEntity entity = UserMapper.toEntity(UserDTO);
        userRepository.save(entity);
    }

    @Override
    public void updatePasswordByEmail(String email, String newPassword) {
        userRepository.updatePasswordByEmail(email, newPassword);
    }
    
    @Override
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        // Method này cần được cập nhật để sử dụng username thay vì userId
        // Tạm thời giữ nguyên signature để không phá vỡ code hiện có
        // Có thể cần thêm overload method với username
        return false; // Cần implement lại với username
    }
    
    @Override
    public boolean updateProfile(String username, String fullname, String phone, String imageFileName) {
        Optional<UserEntity> optionalEntity = userRepository.findByUsername(username);
        if (!optionalEntity.isPresent()) {
            return false;
        }
        
        UserEntity entity = optionalEntity.get();
        
        // Cập nhật fullname
        if (fullname != null && !fullname.trim().isEmpty()) {
            entity.setFullname(fullname);
        }
        
        // Cập nhật phone
        if (phone != null && !phone.trim().isEmpty()) {
            entity.setPhone(phone);
        }
        
        // Cập nhật images (chỉ lưu tên file)
        if (imageFileName != null && !imageFileName.trim().isEmpty()) {
            // Xóa ảnh cũ nếu có
            if (entity.getImages() != null && !entity.getImages().isEmpty()) {
                java.io.File oldFile = new java.io.File(giap.hcmute.vn.constant.Constant.UPLOAD_PATH + entity.getImages());
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
            entity.setImages(imageFileName);
        }
        
        userRepository.update(entity);
        return true;
    }
    
    @Override
    public void update(UserDTO userDTO) {
        Optional<UserEntity> optionalEntity = userRepository.findByUsername(userDTO.getUsername());
        if (optionalEntity.isPresent()) {
            UserEntity entity = optionalEntity.get();
            UserMapper.updateEntity(entity, userDTO);
            userRepository.update(entity);
        }
    }
    
    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}