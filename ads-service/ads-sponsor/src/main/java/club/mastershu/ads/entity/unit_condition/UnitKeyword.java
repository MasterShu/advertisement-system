package club.mastershu.ads.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "unit_keyword")
public class UnitKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Basic
    @Column(name = "keyword", nullable = false)
    private String keyword;

    @Basic
    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @Basic
    @Column(name = "updated_time", nullable = false)
    private Date updatedTime;

    public UnitKeyword(Long unitId, String keyword) {
        this.unitId = unitId;
        this.keyword = keyword;
        this.createdTime = new Date();
        this.updatedTime = this.createdTime;
    }
}

