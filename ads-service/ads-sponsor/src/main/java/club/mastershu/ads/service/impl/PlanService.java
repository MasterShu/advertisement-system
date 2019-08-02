package club.mastershu.ads.service.impl;

import club.mastershu.ads.constant.CommonStatus;
import club.mastershu.ads.constant.Constants;
import club.mastershu.ads.dao.PlanRepository;
import club.mastershu.ads.dao.UserRepository;
import club.mastershu.ads.entity.Plan;
import club.mastershu.ads.entity.User;
import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.service.IPlanService;
import club.mastershu.ads.utils.CommonUtils;
import club.mastershu.ads.vo.PlanGetRequest;
import club.mastershu.ads.vo.PlanRequest;
import club.mastershu.ads.vo.PlanResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService implements IPlanService {
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    public PlanService(UserRepository userRepository, PlanRepository planRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    @Override
    @Transactional
    public PlanResponse createPlan(PlanRequest request) throws AdsException {
        if (!request.createValidate()){
            throw new AdsException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<User> user = userRepository.findById(request.getUserId());
        if (!user.isPresent()) {
            throw new AdsException(Constants.ErrorMsg.CANNOT_FIND_RECORD);
        }
        Plan oldPlan = planRepository.findByUserIdAndName(
                request.getUserId(), request.getName()
        );
        if (oldPlan != null) {
            throw new AdsException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }
        Plan newPlan = planRepository.save(new Plan(
                request.getUserId(), request.getName(),
                CommonUtils.parseStringDate(request.getStartTime()),
                CommonUtils.parseStringDate(request.getEndTime())
        ));
        return new PlanResponse(
                newPlan.getId(), newPlan.getName()
        );
    }

    @Override
    public List<Plan> getPlanByIds(PlanGetRequest request) throws AdsException {
        if (!request.validate()) {
            throw new AdsException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        return planRepository.findAllByIdAndUserId(request.getIds(), request.getUserId());
    }

    @Override
    @Transactional
    public PlanResponse updatePlan(PlanRequest request) throws AdsException {
        if (!request.updateValidate()) {
            throw new AdsException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Plan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdsException(Constants.ErrorMsg.CANNOT_FIND_RECORD);
        }

        if (request.getName() != null) {
            plan.setName(request.getName());
        }
        if (request.getStartTime() != null) {
            plan.setStartTime(CommonUtils.parseStringDate(request.getStartTime()));
        }
        if (request.getEndTime() != null) {
            plan.setEndTime(CommonUtils.parseStringDate(request.getEndTime()));
        }
        plan.setUpdatedTime(new Date());
        Plan newPlan = planRepository.save(plan);
        return new PlanResponse(plan.getId(), plan.getName());
    }

    @Override
    @Transactional
    public void deletePlan(PlanRequest request) throws AdsException {
        if (!request.updateValidate()) {
            throw new AdsException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Plan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdsException(Constants.ErrorMsg.CANNOT_FIND_RECORD);
        }
        plan.setStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdatedTime(new Date());
        planRepository.save(plan);
    }
}
