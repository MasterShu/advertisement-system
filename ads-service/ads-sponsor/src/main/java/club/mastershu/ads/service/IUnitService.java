package club.mastershu.ads.service;

import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.vo.*;

public interface IUnitService {
    UnitResponse createUnit(UnitRequest request) throws AdsException;

    UnitKeywordResponse createUnitKeyword(UnitKeywordRequest request) throws AdsException;

    UnitItResponse createUnitIt(UnitItRequest request) throws AdsException;

    UnitDistrictResponse  createUnitDistrict(UnitDistrictRequest request) throws AdsException;

    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdsException;
}
