package giap.hcmute.vn.service;

import giap.hcmute.vn.dto.UserDTO;

public interface UserService {
	UserDTO login(String username, String password);
	UserDTO get(String username);
	
	/**
	 * Lấy tất cả users (dùng cho admin)
	 */
	java.util.List<UserDTO> getAll();
	
	void insert(UserDTO UserDTO);
	boolean register(String email, String password, String username, String fullname, String phone);
	boolean checkExistEmail(String email);
	boolean checkExistUsername(String username);
	boolean checkExistPhone(String phone);
	void updatePasswordByEmail(String email, String newPassword);
	
	/**
	 * Đổi mật khẩu cho user đang logged in
	 * @param userId ID của user
	 * @param oldPassword Mật khẩu cũ
	 * @param newPassword Mật khẩu mới
	 * @return true nếu đổi thành công, false nếu mật khẩu cũ không đúng
	 */
	boolean changePassword(int userId, String oldPassword, String newPassword);
	
	/**
	 * Cập nhật profile: fullname, phone, images
	 * @param username Username của user cần update
	 * @param fullname Tên đầy đủ mới
	 * @param phone Số điện thoại mới
	 * @param imageFileName Tên file ảnh mới (chỉ tên file, không có đường dẫn)
	 * @return true nếu update thành công, false nếu user không tồn tại
	 */
	boolean updateProfile(String username, String fullname, String phone, String imageFileName);
	
	/**
	 * Cập nhật user (dùng cho admin)
	 */
	void update(UserDTO userDTO);
	
	/**
	 * Xóa user theo username (dùng cho admin)
	 */
	void deleteByUsername(String username);
}