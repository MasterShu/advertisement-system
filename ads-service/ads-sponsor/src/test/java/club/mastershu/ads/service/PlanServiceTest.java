package club.mastershu.ads.service;

import club.mastershu.ads.Application;
import club.mastershu.ads.entity.Plan;
import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.vo.PlanGetRequest;
import club.mastershu.ads.vo.PlanRequest;
import club.mastershu.ads.vo.PlanResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PlanServiceTest {
    @Autowired
    private IPlanService planService;

    @Test
    public void testCreatePlan() throws AdsException {
        PlanData plan = new PlanData();
        PlanResponse result = planService.createPlan(new PlanRequest(plan.id, plan.userId, plan.name, plan.startTime, plan.endTime));
        log.debug(String.valueOf(result));
    }

    @Test
    public void testGetPlan() throws AdsException {
        PlanData plan = new PlanData();
        PlanGetRequest request = new PlanGetRequest(plan.userId, Collections.singletonList(plan.id));
        log.info(String.valueOf(request));
        List<Plan> plans = planService.getPlanByIds(request);
        log.info(String.valueOf(plans));
    }

    @Data
    private static class PlanData {
        private Long id;
        private Long userId;
        private String name;
        private String startTime;
        private String endTime;

        PlanData() {
            this.userId = 1L;
            this.id = 2L;
            this.name = "Dio";
            this.startTime = "2019-09-01";
            this.endTime = "2019-10-01";
        }
    }
}
