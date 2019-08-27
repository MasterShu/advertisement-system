package club.mastershu.ads.index;

import lombok.Getter;

@Getter
public enum CommonStatus {
    VALID(1, "有效"),
    INVALID(0, "无效");

    private Integer status;
    private String description;

    CommonStatus(Integer status, String description) {
        this.status = status;
        this.description = description;
    }
}
