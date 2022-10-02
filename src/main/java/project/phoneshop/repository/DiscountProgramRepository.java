package project.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import project.phoneshop.model.entity.BrandEntity;
import project.phoneshop.model.entity.DiscountProgramEntity;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface DiscountProgramRepository extends JpaRepository<DiscountProgramEntity, Long> {
    Optional<DiscountProgramEntity> findByName(String name);
    void deleteById(Long id);
    @Override
    List<DiscountProgramEntity> findAll();
    Boolean existsByName(String name);

    Optional<DiscountProgramEntity> findById(Long id);
    Optional<DiscountProgramEntity> findByIdAndBrandEntities(Long id, BrandEntity brand);
}
