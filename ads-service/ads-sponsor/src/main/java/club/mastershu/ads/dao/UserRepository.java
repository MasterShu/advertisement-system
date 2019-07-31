package club.mastershu.ads.dao;

import club.mastershu.ads.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AdUser, Long> {
    AdUser findByUsername(String username);
}
