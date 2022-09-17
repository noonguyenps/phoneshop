package project.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import project.phoneshop.model.entity.AttributeEntity;

@EnableJpaRepositories
public interface AttributeRepository extends JpaRepository<AttributeEntity, String> {
}
