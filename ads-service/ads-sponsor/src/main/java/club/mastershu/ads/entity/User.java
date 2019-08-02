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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "username", nullable = false)
    private String username;

    @Basic
    @Column(name = "token", nullable = false)
    private String token;

    @Basic
    @Column(name = "status", nullable = false)
    private Integer status;

    @Basic
    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @Basic
    @Column(name = "updated_time", nullable = false)
    private Date updatedTime;

    public User(String username, String token) {
        this.username = username;
        this.token = token;
        this.status = CommonStatus.VALID.getStatus();
        this.createdTime = new Date();
        this.updatedTime = this.createdTime;
    }
}
