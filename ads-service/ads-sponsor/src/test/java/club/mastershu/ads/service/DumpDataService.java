package club.mastershu.ads.service;

import club.mastershu.ads.Application;
import club.mastershu.ads.constant.CommonStatus;
import club.mastershu.ads.dao.CreativeRepository;
import club.mastershu.ads.dao.PlanRepository;
import club.mastershu.ads.dao.UnitRepository;
import club.mastershu.ads.dao.unit_condition.CreativeUnitRepository;
import club.mastershu.ads.dao.unit_condition.UnitDistrictRepository;
import club.mastershu.ads.dao.unit_condition.UnitItRepository;
import club.mastershu.ads.dao.unit_condition.UnitKeywordRepository;
import club.mastershu.ads.dump.Constant;
import club.mastershu.ads.dump.table.*;
import club.mastershu.ads.entity.Creative;
import club.mastershu.ads.entity.Plan;
import club.mastershu.ads.entity.Unit;
import club.mastershu.ads.entity.unit_condition.CreativeUnit;
import club.mastershu.ads.entity.unit_condition.UnitDistrict;
import club.mastershu.ads.entity.unit_condition.UnitIt;
import club.mastershu.ads.entity.unit_condition.UnitKeyword;
import club.mastershu.ads.vo.UnitKeywordRequest;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;
    @Autowired
    private UnitDistrictRepository districtRepository;
    @Autowired
    private UnitItRepository itRepository;
    @Autowired
    private UnitKeywordRepository keywordRepository;

    @Test
    public void dumpTableData() {
        dumpPlanTable(String.format("%s%s", Constant.DATA_ROOT_DIR, Constant.PLAN));
        dumpUnitTable(String.format("%s%s", Constant.DATA_ROOT_DIR, Constant.UNIT));
        dumpCreativeTable(String.format("%s%s", Constant.DATA_ROOT_DIR, Constant.CREATIVE));
        dumpCreativeUnitTable(String.format("%s%s", Constant.DATA_ROOT_DIR, Constant.CREATIVE_UNIT));
        dumpUnitDistrict(String.format("%s%s", Constant.DATA_ROOT_DIR, Constant.UNIT_DISTRICT));
        dumpUnitItTable(String.format("%s%s", Constant.DATA_ROOT_DIR, Constant.UNIT_IT));
        dumpUnitKeywordTable(String.format("%s%s", Constant.DATA_ROOT_DIR, Constant.UNIT_KEYWORD));
    }
    private void dumpPlanTable(String filename) {
        List<Plan> plans = planRepository.findAllByStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(plans)) {
            return;
        }
        List<PlanTable> planTables = new ArrayList<>();
        plans.forEach(plan -> planTables.add(new PlanTable(
                        plan.getId(),
                        plan.getUserId(),
                        plan.getStatus(),
                        plan.getStartTime(),
                        plan.getEndTime()
                )
        ));
        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (PlanTable planTable : planTables) {
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("dumpPlanTable error");
        }
    }

    public void dumpUnitTable(String filename) {
        List<Unit> units = unitRepository.findAllByStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(units)) {
            return;
        }
        List<UnitTable> unitTables = new ArrayList<>();
        units.forEach(unit -> unitTables.add(
                new UnitTable(
                        unit.getId(),
                        unit.getPlanId(),
                        unit.getStatus(),
                        unit.getPositionType()
                )
        ));
        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (UnitTable unitTable : unitTables) {
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("dumpPlanTable error");
        }
    }

    private void dumpCreativeTable(String filename) {
        List<Creative> creatives = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }
        List<CreativeTable> creativeTables = new ArrayList<>();
        creatives.forEach(creative -> creativeTables.add(
                new CreativeTable(
                        creative.getId(),
                        creative.getName(),
                        creative.getType(),
                        creative.getMaterialType(),
                        creative.getHeight(),
                        creative.getWidth(),
                        creative.getAuditStatus(),
                        creative.getUrl()
                )
        ));
        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (CreativeTable creativeTable : creativeTables) {
                writer.write(JSON.toJSONString(creativeTable));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("dumpPlanTable error");
        }
    }

    private void dumpCreativeUnitTable(String filename) {
        List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return;
        }
        List<CreativeUnitTable> creativeUnitTables = new ArrayList<>();
        creativeUnits.forEach(creativeUnit -> creativeUnitTables.add(
                new CreativeUnitTable(
                        creativeUnit.getCreativeId(),
                        creativeUnit.getUnitId()
                )
        ));
        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (CreativeUnitTable creativeUnitTable : creativeUnitTables) {
                writer.write(JSON.toJSONString(creativeUnitTable));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("dumpCreativeUnitTable error");
        }
    }

    private void dumpUnitDistrict(String filename) {
        List<UnitDistrict> unitDistricts = districtRepository.findAll();
        if (CollectionUtils.isEmpty(unitDistricts)) {
            return;
        }
        List<UnitDistrictTable> unitDistrictTables = new ArrayList<>();
        unitDistricts.forEach(unitDistrict -> unitDistrictTables.add(
                new UnitDistrictTable(
                        unitDistrict.getUnitId(),
                        unitDistrict.getProvince(),
                        unitDistrict.getCity()
                )
        ));
        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (UnitDistrictTable unitDistrictTable : unitDistrictTables) {
                writer.write(JSON.toJSONString(unitDistrictTable));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("dumpUnitDistrictTable error");
        }
    }

    private void dumpUnitItTable(String filename) {
        List<UnitIt> unitIts = itRepository.findAll();
        if (CollectionUtils.isEmpty(unitIts)) {
            return;
        }
        List<UnitItTable> unitItTables = new ArrayList<>();
        unitIts.forEach(unitIt -> unitItTables.add(
                new UnitItTable(
                        unitIt.getUnitId(),
                        unitIt.getItTag()
                )
        ));
        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (UnitItTable unitItTable : unitItTables) {
                writer.write(JSON.toJSONString(unitItTable));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("dumpUnitItTable error");
        }
    }

    private void dumpUnitKeywordTable(String filename) {
        List<UnitKeyword> unitKeywords = keywordRepository.findAll();
        if (CollectionUtils.isEmpty(unitKeywords)) {
            return;
        }
        List<UnitKeywordTable> unitKeywordTables = new ArrayList<>();
        unitKeywords.forEach(unitKeyword -> unitKeywordTables.add(
                new UnitKeywordTable(
                        unitKeyword.getUnitId(),
                        unitKeyword.getKeyword()
                )
        ));
        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (UnitKeywordTable unitKeywordTable : unitKeywordTables) {
                writer.write(JSON.toJSONString(unitKeywordTable));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("dumpUnitKeywordTable error");
        }
    }
}
