package project.phoneshop.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import project.phoneshop.model.entity.AddressEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@EnableJpaRepositories
public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {
    Optional<AddressEntity> findById(UUID id);
    @Override
    List<AddressEntity> findAll();
}
