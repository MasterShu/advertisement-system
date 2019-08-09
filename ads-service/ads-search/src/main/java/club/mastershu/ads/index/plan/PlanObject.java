package club.mastershu.ads.index.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanObject {
    private Long planId;
    private Long userId;
    private Integer status;
    private Date startTime;
    private Date endTime;

    public void update(PlanObject newObject) {
        if (null != newObject.getPlanId()) {
            this.planId = newObject.planId;
        }
        if (null != newObject.getUserId()) {
            this.userId = newObject.userId;
        }
        if (null != newObject.getStatus()) {
            this.status = newObject.status;
        }
        if (null != newObject.getStartTime()) {
            this.startTime = newObject.startTime;
        }
        if (null != newObject.getEndTime()) {
            this.endTime = newObject.endTime;
        }
    }
}
