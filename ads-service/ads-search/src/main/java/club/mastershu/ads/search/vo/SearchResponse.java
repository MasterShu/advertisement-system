package club.mastershu.ads.search.vo;

import club.mastershu.ads.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    public Map<String, List<Creative>> adSlotToAds = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Creative {
        private Long id;
        private String url;
        private Integer width;
        private Integer height;
        private Integer type;
        private Integer materialType;

        private List<String> showMonitorUrl = Arrays.asList("", "");
        private List<String> clickMonitorUrl = Arrays.asList("", "");
    }

    public static Creative convert(CreativeObject object) {
        Creative creative = new Creative();
        creative.setId(object.getId());
        creative.setUrl(object.getUrl());
        creative.setWidth(object.getWidth());
        creative.setHeight(object.getHeight());
        creative.setType(object.getType());
        creative.setMaterialType(object.getMaterialType());
        return creative;
    }
}
