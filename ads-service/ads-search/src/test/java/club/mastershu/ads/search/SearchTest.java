package club.mastershu.ads.search;

import club.mastershu.ads.Application;
import club.mastershu.ads.search.vo.SearchRequest;
import club.mastershu.ads.search.vo.SearchResponse;
import club.mastershu.ads.search.vo.feature.DistrictFeature;
import club.mastershu.ads.search.vo.feature.FeatureRelation;
import club.mastershu.ads.search.vo.feature.ItFeature;
import club.mastershu.ads.search.vo.feature.KeywordFeature;
import club.mastershu.ads.search.vo.media.AdSlot;
import club.mastershu.ads.search.vo.media.App;
import club.mastershu.ads.search.vo.media.Device;
import club.mastershu.ads.search.vo.media.Geo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {
    @Autowired
    private ISearch search;

    @Test
    public void testFetchAds() {
        SearchRequest request = new SearchRequest();
        request.setMediaId("ad-test");
        request.setRequestInfo(new SearchRequest.RequestInfo(
                "hello001", Collections.singletonList(new AdSlot("ad-001", 1, 1080, 720, Arrays.asList(1, 2), 100)),
                buildExampleApp(), buildExampleGeo(), buildExampleDevice()
        ));
        request.setFeatureInfo(buildExampleFeatureInfo(
                Arrays.asList("Dio", "test"),
                Collections.singletonList(new DistrictFeature.ProvinceAndCity("广西", "桂林")),
                Arrays.asList("开心", "test"),
                FeatureRelation.OR
        ));
        log.debug(String.valueOf(request));
        SearchResponse result = search.fetchAds(request);
        log.info(String.valueOf(result));
    }

    private App buildExampleApp() {
        return new App("hospital", "hospital", "club.mastershu", "video");
    }

    private Geo buildExampleGeo() {
        return new Geo((float) 98, (float) 101, "广州", "广东");
    }

    private Device buildExampleDevice() {
        return new Device("Android P", "129xxx1dk", "192.168.2.1", "x", "1080 720", "1080 720", "13549869482134");
    }

    private SearchRequest.FeatureInfo buildExampleFeatureInfo(
            List<String> keywords,
            List<DistrictFeature.ProvinceAndCity> provinceAndCities,
            List<String> its,
            FeatureRelation relation
    ) {
        return new SearchRequest.FeatureInfo(
                new KeywordFeature(keywords),
                new ItFeature(its),
                new DistrictFeature(provinceAndCities),
                relation
        );
    }
}
