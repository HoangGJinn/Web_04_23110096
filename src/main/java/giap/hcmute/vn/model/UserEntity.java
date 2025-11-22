package giap.hcmute.vn.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Username", nullable = false, unique = true, length = 50)
    private String username;
    
    @Column(name = "Password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "Phone", length = 20)
    private String phone;
    
    @Column(name = "Fullname", length = 100)
    private String fullname;
    
    @Column(name = "Email", length = 100)
    private String email;
    
    @Column(name = "Admin")
    private Boolean admin;
    
    @Column(name = "Active")
    private Boolean active;
    
    @Column(name = "Images", length = 255)
    private String images;

    @OneToMany(
        mappedBy = "user", // tên thuộc tính trong CategoryEntity tham chiếu đến UserEntity
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<CategoryEntity> categories = new ArrayList<>();
}
