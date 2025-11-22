package giap.hcmute.vn.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "Category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Categoryld")
    private int categoryld;
    
    @Column(name = "Categoryname", nullable = false, length = 100)
    private String categoryname;
    
    @Column(name = "Categorycode", length = 50)
    private String categorycode;
    
    @Column(name = "Images", length = 255)
    private String images;
    
    @Column(name = "Status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "Id")
    private UserEntity user;
    
    @OneToMany(
        mappedBy = "category",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<VideoEntity> videos = new ArrayList<>();
}
