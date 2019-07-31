package club.mastershu.ads.constant;

import club.mastershu.ads.vo.CommonResponse;
import lombok.Getter;

@Getter
public enum CommonStatus {
    VALID(1, "正常状态"),
    INVALID(0, "无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
