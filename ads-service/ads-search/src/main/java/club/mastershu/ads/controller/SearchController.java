package club.mastershu.ads.controller;

import club.mastershu.ads.annotation.IgnoreResponseAdvice;
import club.mastershu.ads.client.SponsorClient;
import club.mastershu.ads.client.vo.Plan;
import club.mastershu.ads.client.vo.PlanGetRequest;
import club.mastershu.ads.vo.CommonResponse;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
public class SearchController {
    private final RestTemplate restTemplate;
    private final SponsorClient sponsorClient;

    public SearchController(RestTemplate restTemplate, SponsorClient sponsorClient) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
    }

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/getPlanByRibbon")
    public CommonResponse<List<Plan>> getPlansByRibbon(@RequestBody PlanGetRequest request) {
        log.info("ads-search: getPlanByRibbon -> {}", JSON.toJSONString(request));
        return restTemplate.postForEntity("http://eureka-client-ads-sponsor/ads-sponsor/get/plan", request, CommonResponse.class).getBody();
    }

    @IgnoreResponseAdvice
    @PostMapping("/getPlans")
    public CommonResponse<List<Plan>> getPlans(@RequestBody PlanGetRequest request) {
        log.info("ads-search: getPlans -> {}", JSON.toJSONString(request));
        return sponsorClient.getPlans(request);
    }
}
