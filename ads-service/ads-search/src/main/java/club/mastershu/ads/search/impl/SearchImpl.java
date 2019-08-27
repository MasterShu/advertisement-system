package club.mastershu.ads.search.impl;

import club.mastershu.ads.index.CommonStatus;
import club.mastershu.ads.index.DataTable;
import club.mastershu.ads.index.creative.CreativeIndex;
import club.mastershu.ads.index.creative.CreativeObject;
import club.mastershu.ads.index.creativeUnit.CreativeUnitIndex;
import club.mastershu.ads.index.district.UnitDistrictIndex;
import club.mastershu.ads.index.interest.UnitItIndex;
import club.mastershu.ads.index.keyword.UnitKeywordIndex;
import club.mastershu.ads.index.unit.UnitIndex;
import club.mastershu.ads.index.unit.UnitObject;
import club.mastershu.ads.search.ISearch;
import club.mastershu.ads.search.vo.SearchRequest;
import club.mastershu.ads.search.vo.SearchResponse;
import club.mastershu.ads.search.vo.feature.DistrictFeature;
import club.mastershu.ads.search.vo.feature.FeatureRelation;
import club.mastershu.ads.search.vo.feature.ItFeature;
import club.mastershu.ads.search.vo.feature.KeywordFeature;
import club.mastershu.ads.search.vo.media.AdSlot;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class SearchImpl implements ISearch {
    @Override
    public SearchResponse fetchAds(SearchRequest request) {
        // get ad slot info
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();
        // get feature info
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();
        FeatureRelation relation = request.getFeatureInfo().getRelation();

        // Build response
        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlotToAds = response.getAdSlotToAds();
        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIdSet;
            Set<Long> unitIdSet = DataTable.of(UnitIndex.class).match(adSlot.getPositionType());
            if (relation == FeatureRelation.AND) {
                filterKeywordFeature(unitIdSet, keywordFeature);
                filterDistrictFeature(unitIdSet, districtFeature);
                filterItFeature(unitIdSet, itFeature);
                targetUnitIdSet = unitIdSet;
            } else {
                targetUnitIdSet = getORRelationUnitIds(unitIdSet, keywordFeature, districtFeature, itFeature);
            }
            List<UnitObject> unitObjects = DataTable.of(UnitIndex.class).fetch(targetUnitIdSet);
            filterUnitAndPlanStatus(unitObjects, CommonStatus.VALID);
            List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            List<CreativeObject> creativeObjects = DataTable.of(CreativeIndex.class).fetch(adIds);
            filterCreativeByAdSlot(
                    creativeObjects, adSlot.getWidth(), adSlot.getHeight(), adSlot.getType()
            );
            adSlotToAds.put(adSlot.getAdSlotCode(), buildCreativeResponse(creativeObjects));
        }
        log.info("fetchAds: {} - {}", JSON.toJSONString(request), JSON.toJSONString(response));
        return response;
    }

    private void filterKeywordFeature(Collection<Long> unitIds, KeywordFeature keywordFeature) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {
            CollectionUtils.filter(
                    unitIds,
                    unitId -> DataTable.of(UnitKeywordIndex.class).match(unitId, keywordFeature.getKeywords())
            );
        }
    }

    private void filterDistrictFeature(Collection<Long> unitIds, DistrictFeature districtFeature) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {
            CollectionUtils.filter(
                    unitIds,
                    unitId -> DataTable.of(UnitDistrictIndex.class).match(unitId, districtFeature.getDistricts())
            );
        }
    }

    private void filterItFeature(Collection<Long> unitIds, ItFeature itFeature) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {
            CollectionUtils.filter(
                    unitIds,
                    unitId -> DataTable.of(UnitItIndex.class).match(unitId, itFeature.getIts())
            );
        }
    }

    private Set<Long> getORRelationUnitIds(
            Set<Long> unitIdSet, KeywordFeature keywordFeature, DistrictFeature districtFeature, ItFeature itFeature) {
        if (CollectionUtils.isEmpty(unitIdSet)) {
            return Collections.emptySet();
        }

        Set<Long> keywordUnitIdSet = new HashSet<>(unitIdSet);
        Set<Long> districtUnitIdSet = new HashSet<>(unitIdSet);
        Set<Long> itUnitIdSet = new HashSet<>(unitIdSet);

        filterKeywordFeature(keywordUnitIdSet, keywordFeature);
        filterDistrictFeature(districtUnitIdSet, districtFeature);
        filterItFeature(itUnitIdSet, itFeature);
        return new HashSet<>(
                CollectionUtils.union(CollectionUtils.union(keywordUnitIdSet, districtUnitIdSet), itUnitIdSet)
        );
    }

    private void filterUnitAndPlanStatus(List<UnitObject> unitObjects, CommonStatus status) {
        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }
        CollectionUtils.filter(
                unitObjects,
                unitObject -> unitObject.getStatus().equals(status.getStatus())
                        && unitObject.getPlanObject().getStatus().equals(status.getStatus())
        );
    }

    private void filterCreativeByAdSlot(List<CreativeObject> creativeObjects,
                                        Integer width,
                                        Integer height,
                                        List<Integer> type) {
        if (CollectionUtils.isEmpty(creativeObjects)) {
            return;
        }
        CollectionUtils.filter(
                creativeObjects,
                creativeObject -> creativeObject.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                        && creativeObject.getWidth().equals(width)
                        && creativeObject.getHeight().equals(height)
                        && type.contains(creativeObject.getType())
        );
    }

    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creativeObjects) {
        if (CollectionUtils.isEmpty(creativeObjects)) {
            return Collections.emptyList();
        }
        CreativeObject randomObject = creativeObjects.get(Math.abs(new Random().nextInt()) % creativeObjects.size());
        return Collections.singletonList(SearchResponse.convert(randomObject));
    }
}
