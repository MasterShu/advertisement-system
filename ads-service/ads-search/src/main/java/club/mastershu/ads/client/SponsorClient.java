package club.mastershu.ads.client;

import club.mastershu.ads.client.vo.Plan;
import club.mastershu.ads.client.vo.PlanGetRequest;
import club.mastershu.ads.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "eureka-client-ads-sponsor", fallback = SponsorClientHystrix.class)
public interface SponsorClient {
    @RequestMapping(value = "/ads-sponsor/get/plan", method = RequestMethod.POST)
    CommonResponse<List<Plan>> getPlans(@RequestBody PlanGetRequest request);
}
