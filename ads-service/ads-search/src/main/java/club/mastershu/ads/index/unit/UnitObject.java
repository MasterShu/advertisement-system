package club.mastershu.ads.index.unit;

import club.mastershu.ads.index.plan.PlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitObject {
    private Long unitId;
    private Integer status;
    private Integer positionType;
    private Long planId;

    private PlanObject planObject;

    void update(UnitObject newObject) {
        if (null != newObject.getUnitId()) {
            this.unitId = newObject.unitId;
        }
        if (null != newObject.getStatus()) {
            this.status = newObject.status;
        }
        if (null != newObject.getPositionType()) {
            this.positionType = newObject.positionType;
        }
        if (null != newObject.getPlanId()) {
            this.planId = newObject.planId;
        }
        if (null != newObject.getPlanObject()) {
            this.planObject = newObject.planObject;
        }
    }
}
