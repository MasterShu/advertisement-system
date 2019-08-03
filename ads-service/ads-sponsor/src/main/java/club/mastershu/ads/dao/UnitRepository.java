package club.mastershu.ads.dao;

import club.mastershu.ads.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    Unit findByPlanIdAndName(Long planId, String name);
    List<Unit> findAllByStatus(Integer status);
}
