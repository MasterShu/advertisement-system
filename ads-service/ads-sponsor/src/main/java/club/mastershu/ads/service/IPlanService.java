package club.mastershu.ads.service;

import club.mastershu.ads.entity.Plan;
import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.vo.PlanGetRequest;
import club.mastershu.ads.vo.PlanRequest;
import club.mastershu.ads.vo.PlanResponse;

import java.util.List;

public interface IPlanService {
    PlanResponse createPlan(PlanRequest request) throws AdsException;

    List<Plan> getPlanByIds(PlanGetRequest request) throws AdsException;

    PlanResponse updatePlan(PlanRequest request) throws AdsException;

    void deletePlan(PlanRequest request) throws AdsException;
}
