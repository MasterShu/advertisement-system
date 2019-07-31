package club.mastershu.ads.dao;

import club.mastershu.ads.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<AdPlan, Long> {
    AdPlan findByIdAndUserId(Long id, Long userId);
    List<AdPlan> findByIdInAndUserId(List<Long> ids, Long userId);
    AdPlan findByUserIdAndName(Long userId, String name);
    List<AdPlan> findAllByStatus(Integer status);
}
