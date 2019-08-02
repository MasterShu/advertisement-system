package club.mastershu.ads.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanGetRequest {
    private Long userId;
    private List<Long> ids;

    public boolean validate() {
        return userId != null
                && !CollectionUtils.isEmpty(ids);
    }
}
