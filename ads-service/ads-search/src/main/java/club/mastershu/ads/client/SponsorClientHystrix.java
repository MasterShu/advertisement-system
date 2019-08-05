package club.mastershu.ads.client;

import club.mastershu.ads.client.vo.Plan;
import club.mastershu.ads.client.vo.PlanGetRequest;
import club.mastershu.ads.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SponsorClientHystrix implements SponsorClient{

    @Override
    public CommonResponse<List<Plan>> getPlans(PlanGetRequest request) {
        return new CommonResponse<>(-1, "eureka-client-ads-sponsor error");
    }
}
