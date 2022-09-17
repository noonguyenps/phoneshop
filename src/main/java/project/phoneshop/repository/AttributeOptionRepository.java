package project.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import project.phoneshop.model.entity.AttributeOptionEntity;

@EnableJpaRepositories
public interface AttributeOptionRepository extends JpaRepository<AttributeOptionEntity, String> {
}
