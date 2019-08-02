package club.mastershu.ads.service.impl;

import club.mastershu.ads.constant.Constants;
import club.mastershu.ads.dao.PlanRepository;
import club.mastershu.ads.dao.UnitRepository;
import club.mastershu.ads.entity.Plan;
import club.mastershu.ads.entity.Unit;
import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.service.IUnitService;
import club.mastershu.ads.vo.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UnitService implements IUnitService {
    private final UnitRepository unitRepository;
    private final PlanRepository planRepository;

    public UnitService(UnitRepository unitRepository, PlanRepository planRepository) {
        this.unitRepository = unitRepository;
        this.planRepository = planRepository;
    }

    @Override
    public UnitResponse createUnit(UnitRequest request) throws AdsException {
        if (request.createValidate()) {
            throw new AdsException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<Plan> plan = planRepository.findById(request.getPlanId());
        if (!plan.isPresent()) {
            throw new AdsException(Constants.ErrorMsg.CANNOT_FIND_RECORD);
        }
        Unit oldUnit = unitRepository.findByPlantIdAndName(request.getPlanId(), request.getName());
        if (oldUnit != null) {
            throw new AdsException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }

        Unit unit = unitRepository.save(new Unit(
                request.getPlanId(), request.getName(), request.getPositionType(), request.getBudget()
        ));
        return new UnitResponse(unit.getId(), unit.getName());
    }

    @Override
    public UnitKeywordResponse createUnitKeyword(UnitKeywordRequest request) throws AdsException {
        return null;
    }

    @Override
    public UnitItResponse createUnit(UnitItRequest request) throws AdsException {
        return null;
    }

    @Override
    public UnitDistrictResponse createUnitDistrict(UnitDistrictRequest request) throws AdsException {
        return null;
    }
}
