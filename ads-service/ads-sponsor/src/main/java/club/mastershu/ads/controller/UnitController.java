package club.mastershu.ads.controller;

import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.service.IUnitService;
import club.mastershu.ads.vo.*;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UnitController {
    private final IUnitService unitService;

    public UnitController(IUnitService unitService) {
        this.unitService = unitService;
    }


    @PostMapping("/create/unit")
    public UnitResponse createUnit(@RequestBody UnitRequest request) throws AdsException {
        log.info("ads-sponsor: createUnit -> {}", JSON.toJSONString(request));
        return unitService.createUnit(request);
    }

    @PostMapping("/create/unitKeyword")
    public UnitKeywordResponse createUnitKeyword(@RequestBody UnitKeywordRequest request) throws AdsException {
        log.info("ads-sponsor: createUnitKeyword -> {}", JSON.toJSONString(request));
        return unitService.createUnitKeyword(request);
    }

    @PostMapping("/create/unitIt")
    public UnitItResponse createUnitIt(@RequestBody UnitItRequest request) throws AdsException {
        log.info("ads-sponsor: createUnitIt -> {}", JSON.toJSONString(request));
        return unitService.createUnitIt(request);
    }

    @PostMapping("/create/unitDistrict")
    public UnitDistrictResponse createUnitDistrict(@RequestBody UnitDistrictRequest request) throws AdsException {
        log.info("ads-sponsor: createUnitDistrict-> {}", JSON.toJSONString(request));
        return unitService.createUnitDistrict(request);
    }


    @PostMapping("/create/creativeUnit")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest request) throws AdsException {
        log.info("ads-sponsor: createCreativeUnit-> {}", JSON.toJSONString(request));
        return unitService.createCreativeUnit(request);
    }
}
