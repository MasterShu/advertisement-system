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
@Table(name = "unit_district")
public class UnitDistrict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Basic
    @Column(name = "province", nullable = false)
    private String province;

    @Basic
    @Column(name = "city", nullable = false)
    private String city;

    @Basic
    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @Basic
    @Column(name = "updated_time", nullable = false)
    private Date updatedTime;

    public UnitDistrict(Long unitId, String province, String city) {
        this.unitId = unitId;
        this.city = city;
        this.province = province;
        this.createdTime = new Date();
        this.updatedTime = this.createdTime;
    }
}

