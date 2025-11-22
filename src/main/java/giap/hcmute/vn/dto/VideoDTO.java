package giap.hcmute.vn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int videold;
    private String title;
    private String poster;
    private Integer views;
    private String description;
    private Boolean active;
    private int categoryld; // Foreign key to Category
}


