package club.mastershu.ads.entity;

import club.mastershu.ads.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_plan")
public class AdPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "status", nullable = false)
    private Integer status;

    @Basic
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Basic
    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @Basic
    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @Basic
    @Column(name = "updated_time", nullable = false)
    private Date updatedTime;

    public AdPlan(Long userId, String name, Date startTime, Date endTime){
        this.userId = userId;
        this.name = name;
        this.status = CommonStatus.VALID.getStatus();
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdTime = new Date();
        this.updatedTime = this.createdTime;
    }
}
