package project.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import project.phoneshop.model.entity.ShipEntity;

import java.util.Optional;

@EnableJpaRepositories
public interface ShipRepository extends JpaRepository<ShipEntity,Integer> {

    Optional<ShipEntity> findByShipId(int ship_id);
}
