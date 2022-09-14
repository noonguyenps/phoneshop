package project.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import project.phoneshop.model.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByPhone(String phone);
    Optional<UserEntity> findByPhone(String phone);
}
