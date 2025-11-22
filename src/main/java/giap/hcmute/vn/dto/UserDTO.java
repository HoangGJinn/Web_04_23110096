package giap.hcmute.vn.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String fullname;
    private String email;
    private Boolean admin;
    private Boolean active;
    private String images;

    // Thêm mối quan hệ OneToMany dưới dạng DTO
    private List<CategoryDTO> categories;

}
