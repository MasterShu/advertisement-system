package club.mastershu.ads.search.vo;

import club.mastershu.ads.search.vo.feature.DistrictFeature;
import club.mastershu.ads.search.vo.feature.FeatureRelation;
import club.mastershu.ads.search.vo.feature.ItFeature;
import club.mastershu.ads.search.vo.feature.KeywordFeature;
import club.mastershu.ads.search.vo.media.AdSlot;
import club.mastershu.ads.search.vo.media.App;
import club.mastershu.ads.search.vo.media.Device;
import club.mastershu.ads.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private String mediaId;
    private RequestInfo requestInfo;
    private FeatureInfo featureInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestInfo{
        private String requestId;
        private List<AdSlot> adSlots;
        private App app;
        private Geo geo;
        private Device device;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureInfo{
        private KeywordFeature keywordFeature;
        private ItFeature itFeature;
        private DistrictFeature districtFeature;

        private FeatureRelation relation = FeatureRelation.AND;
    }
}
