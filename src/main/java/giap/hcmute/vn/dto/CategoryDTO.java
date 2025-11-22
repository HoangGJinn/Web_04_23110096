package giap.hcmute.vn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int categoryld;
    private String categoryname;
    private String categorycode;
    private String images;
    private Boolean status;
    private String username; // Foreign key to User (Username)
    
    // Thêm mối quan hệ OneToMany dưới dạng DTO
    private List<VideoDTO> videos;
}
