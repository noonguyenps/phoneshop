package project.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import project.phoneshop.model.entity.BrandEntity;

import java.util.UUID;

@EnableJpaRepositories
public interface BrandRepository extends JpaRepository<BrandEntity, UUID> {

}
