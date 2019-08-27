package club.mastershu.ads.index.unit;

import club.mastershu.ads.index.plan.PlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.PortableServer.POA;

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

    private static boolean isLaunchingScreen(int positionType) {
        return (positionType & UnitConstants.POSITION_TYPE.LAUNCHING_SCREEN) > 0;
    }

    private static boolean isPaster(int positionType) {
        return (positionType & UnitConstants.POSITION_TYPE.PASTER) > 0;
    }

    private static boolean isPasterMiddle(int positionType) {
        return (positionType & UnitConstants.POSITION_TYPE.PASTER_MIDDLE) > 0;
    }

    private static boolean isPasterPause(int positionType) {
        return (positionType & UnitConstants.POSITION_TYPE.PASTER_PAUSE) > 0;
    }

    private static boolean isPasterStop(int positionType) {
        return (positionType & UnitConstants.POSITION_TYPE.PASTER_STOP) > 0;
    }

    public static boolean isAdSlotTypeOk(int adSlotType, int positionType) {
        switch (adSlotType) {
            case UnitConstants.POSITION_TYPE.LAUNCHING_SCREEN:
                return isLaunchingScreen(positionType);
            case UnitConstants.POSITION_TYPE.PASTER:
                return isPaster(positionType);
            case UnitConstants.POSITION_TYPE.PASTER_MIDDLE:
                return isPasterMiddle(positionType);
            case UnitConstants.POSITION_TYPE.PASTER_PAUSE:
                    return isPasterPause(positionType);
            case UnitConstants.POSITION_TYPE.PASTER_STOP:
                return isPasterStop(positionType);
            default:
                return false;
        }
    }
}
