package club.mastershu.ads.service.impl;

import club.mastershu.ads.constant.Constants;
import club.mastershu.ads.dao.CreativeRepository;
import club.mastershu.ads.dao.PlanRepository;
import club.mastershu.ads.dao.UnitRepository;
import club.mastershu.ads.dao.unit_condition.CreativeUnitRepository;
import club.mastershu.ads.dao.unit_condition.UnitDistrictRepository;
import club.mastershu.ads.dao.unit_condition.UnitItRepository;
import club.mastershu.ads.dao.unit_condition.UnitKeywordRepository;
import club.mastershu.ads.entity.Plan;
import club.mastershu.ads.entity.Unit;
import club.mastershu.ads.entity.unit_condition.CreativeUnit;
import club.mastershu.ads.entity.unit_condition.UnitDistrict;
import club.mastershu.ads.entity.unit_condition.UnitIt;
import club.mastershu.ads.entity.unit_condition.UnitKeyword;
import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.service.IUnitService;
import club.mastershu.ads.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UnitService implements IUnitService {
    private final UnitRepository unitRepository;
    private final PlanRepository planRepository;
    private final UnitKeywordRepository unitKeywordRepository;
    private final UnitItRepository unitItRepository;
    private final UnitDistrictRepository unitDistrictRepository;
    private final CreativeRepository creativeRepository;
    private final CreativeUnitRepository creativeUnitRepository;


    public UnitService(UnitRepository unitRepository,
                       PlanRepository planRepository,
                       UnitKeywordRepository unitKeywordRepository,
                       UnitItRepository unitItRepository,
                       UnitDistrictRepository unitDistrictRepository,
                       CreativeRepository creativeRepository,
                       CreativeUnitRepository creativeUnitRepository) {
        this.unitRepository = unitRepository;
        this.planRepository = planRepository;
        this.unitKeywordRepository = unitKeywordRepository;
        this.unitItRepository = unitItRepository;
        this.unitDistrictRepository = unitDistrictRepository;
        this.creativeRepository = creativeRepository;
        this.creativeUnitRepository = creativeUnitRepository;
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
        Unit oldUnit = unitRepository.findByPlanIdAndName(request.getPlanId(), request.getName());
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
        List<Long> unitIds = request.getUnitKeywords().stream().map(UnitKeywordRequest.UnitKeywordTemp::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdsException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<Long> ids = Collections.emptyList();
        List<UnitKeyword> unitKeywords = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {
            request.getUnitKeywords().forEach(i -> unitKeywords.add(
                    new UnitKeyword(i.getUnitId(), i.getKeyword())
            ));
            ids = unitKeywordRepository.saveAll(unitKeywords).stream().map(UnitKeyword::getId).collect(Collectors.toList());
        }
        return new UnitKeywordResponse(ids);
    }

    @Override
    public UnitItResponse createUnitIt(UnitItRequest request) throws AdsException {
        List<Long> unitIds = request.getUnitIts().stream().map(UnitItRequest.UnitItTemp::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdsException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<UnitIt> unitIts = new ArrayList<>();
        request.getUnitIts().forEach(i -> unitIts.add(
                new UnitIt(i.getUnitId(), i.getTag())
        ));

        List<Long> ids = unitItRepository.saveAll(unitIts).stream().map(UnitIt::getId).collect(Collectors.toList());
        return new UnitItResponse(ids);
    }

    @Override
    public UnitDistrictResponse createUnitDistrict(UnitDistrictRequest request) throws AdsException {
        List<Long> unitIds = request.getUnitDistricts().stream()
                .map(UnitDistrictRequest.UnitDistrictTemp::getUnitId).collect(Collectors.toList());
        if (isRelatedUnitExist(unitIds)) {
            throw new AdsException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<UnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistricts().forEach(d -> unitDistricts.add(
                new UnitDistrict(d.getUnitId(), d.getProvince(), d.getCity())
        ));
        List<Long> ids = unitDistrictRepository.saveAll(unitDistricts).stream()
                .map(UnitDistrict::getId).collect(Collectors.toList());
        return new UnitDistrictResponse(ids);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdsException {
        List<Long> unitIds = request.getCreativeUnitItems().stream().map(CreativeUnitRequest.CreativeUnitItem::getUnitId).collect(Collectors.toList());
        List<Long> creativeIds = request.getCreativeUnitItems().stream().map(CreativeUnitRequest.CreativeUnitItem::getCreativeId).collect(Collectors.toList());
        if (!(isRelatedUnitExist(unitIds) && isRelatedCreativeExist(creativeIds))) {
            throw new AdsException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<CreativeUnit> creativeUnits = new ArrayList<>();
        request.getCreativeUnitItems().forEach(i->creativeUnits.add(
                new CreativeUnit(i.getUnitId(), i.getCreativeId())
        ));
        List<Long> ids = creativeUnitRepository.saveAll(creativeUnits).stream().map(CreativeUnit::getId).collect(Collectors.toList());
        return new CreativeUnitResponse(ids);
    }

    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (!CollectionUtils.isEmpty(unitIds)) {
            return false;
        }
        return unitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
    }

    private boolean isRelatedCreativeExist(List<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }
        return creativeRepository.findAllById(creativeIds).size() == new HashSet<>(creativeIds).size();
    }
}
