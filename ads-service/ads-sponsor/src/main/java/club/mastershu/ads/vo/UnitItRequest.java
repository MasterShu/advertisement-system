package club.mastershu.ads.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitItRequest {
    private List<UnitItTemp> unitIts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitItTemp{
        private Long unitId;
        private String tag;

    }
}
