package club.mastershu.ads.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequest {
    private Long id;
    private Long userId;
    private String name;
    private String startTime;
    private String endTime;

    public boolean createValidate() {
        return userId != null
                && !StringUtils.isEmpty(name)
                && !StringUtils.isEmpty(startTime)
                && !StringUtils.isEmpty(endTime);
    }

    public boolean updateValidate() {
        return id != null && userId != null;
    }

    public boolean deleteValidate() {
        return id != null && userId != null;
    }
}
