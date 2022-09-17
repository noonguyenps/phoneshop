package project.phoneshop.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import project.phoneshop.model.entity.ImageProductEntity;
import project.phoneshop.model.entity.ProductEntity;

import java.util.List;
import java.util.UUID;

@EnableJpaRepositories
public interface ImageProductRepository extends JpaRepository<ImageProductEntity, UUID> {
    List<ImageProductEntity> findByProduct(ProductEntity product);
}
