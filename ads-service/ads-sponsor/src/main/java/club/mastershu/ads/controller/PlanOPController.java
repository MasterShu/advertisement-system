package club.mastershu.ads.controller;

import club.mastershu.ads.entity.Plan;
import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.service.IPlanService;
import club.mastershu.ads.vo.PlanGetRequest;
import club.mastershu.ads.vo.PlanRequest;
import club.mastershu.ads.vo.PlanResponse;
import com.alibaba.fastjson.JSON;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class PlanOPController {
    private final IPlanService planService;

    public PlanOPController(IPlanService planService) {
        this.planService = planService;
    }

    @PostMapping("/create/plan")
    public PlanResponse createPlan(@RequestBody PlanRequest request) throws AdsException {
        log.info("ads-sponsor: createPlan -> {}", JSON.toJSONString(request));
        return planService.createPlan(request);
    }

    @PostMapping("/get/plan")
    public List<Plan> getPlanByIdS(@RequestBody PlanGetRequest request) throws AdsException {
        log.info("ads-sponsor: getPlanByIds -> {}", JSON.toJSONString(request));
        return planService.getPlanByIds(request);
    }

    @PutMapping("/update/plan")
    public PlanResponse updatePlan(@RequestBody PlanRequest request) throws AdsException {
        log.info("ads-sponsor: updatePlan -> {}", JSON.toJSONString(request));
        return planService.updatePlan(request);
    }

    @DeleteMapping("/delete/plan")
    public void deletePlan(@RequestBody PlanRequest request) throws AdsException {
        log.info("ads-sponsor: deletePlan -> {}", JSON.toJSONString(request));
        planService.deletePlan(request);
    }
}
