package club.mastershu.ads.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanTable {
    private Long id;
    private Long userId;
    private Integer status;
    private Date startTime;
    private Date endTime;
}
