package giap.hcmute.vn.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "Videos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Videold")
    private int videold;
    
    @Column(name = "Title", nullable = false, length = 255)
    private String title;
    
    @Column(name = "Poster", length = 255)
    private String poster;
    
    @Column(name = "Views")
    private Integer views;
    
    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "Active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "Categoryld", referencedColumnName = "Categoryld")
    private CategoryEntity category;
}


