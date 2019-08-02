package club.mastershu.ads.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitRequest {
    private Long id;
    private Long planId;
    private String name;
    private Integer positionType;
    private Long budget;

    public boolean createValidate() {
        return planId != null
                && !StringUtils.isEmpty(name)
                && positionType != null
                && budget != null;
    }

    public boolean updateValidate() {
        return id != null && planId != null;
    }

    public boolean deleteValidate() {
        return id != null && planId != null;
    }
}
