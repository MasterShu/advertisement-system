package club.mastershu.ads.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {
    private Long id;
    private Long userId;
    private String name;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private Date createdTime;
    private Date updatedTime;
}
