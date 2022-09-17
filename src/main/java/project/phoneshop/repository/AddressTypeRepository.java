package project.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import project.phoneshop.model.entity.AddressTypeEntity;

@EnableJpaRepositories
public interface AddressTypeRepository extends JpaRepository<AddressTypeEntity,Integer> {
}
