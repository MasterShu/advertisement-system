package club.mastershu.ads.dao;

import club.mastershu.ads.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Plan findByIdAndUserId(Long id, Long userId);
    Plan findByUserIdAndName(Long userId, String name);
    List<Plan> findAllByStatus(Integer status);

    List<Plan> findAllByIdAndUserId(List<Long> ids, Long userId);
}
