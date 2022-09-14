package project.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import project.phoneshop.model.entity.RoleEntity;

import java.util.Optional;
import java.util.UUID;

@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByName(String roleName);
    Boolean existsByName(String roleName);
}
