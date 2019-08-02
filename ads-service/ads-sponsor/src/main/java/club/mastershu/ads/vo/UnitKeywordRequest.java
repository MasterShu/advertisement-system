package club.mastershu.ads.vo;

import club.mastershu.ads.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitKeywordRequest {

    private List<UnitKeywordTemp> unitKeywords;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitKeywordTemp {
        private Long id;
        private String keyword;
    }
}
