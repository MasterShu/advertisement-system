package club.mastershu.ads.entity;

import club.mastershu.ads.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "unit")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "status", nullable = false)
    private Integer status;

    @Basic
    @Column(name = "position_type", nullable = false)
    private Integer positionType;

    @Basic
    @Column(name = "budget", nullable = false)
    private Long budget;
    @Basic
    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @Basic
    @Column(name = "updated_time", nullable = false)
    private Date updatedTime;

    public Unit(Long planId, String name, Integer positionType, Long budget) {
        this.planId = planId;
        this.name = name;
        this.positionType = positionType;
        this.budget = budget;
        this.createdTime = new Date();
        this.updatedTime = this.createdTime;
        this.status = CommonStatus.VALID.getStatus();
    }
}
