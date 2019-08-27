package club.mastershu.ads.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdSlot {
    // advertisement slot code
    private String adSlotCode;

    private Integer positionType;

    private Integer width;
    private Integer height;

    private List<Integer> type;
    private Integer minCpm;
}
