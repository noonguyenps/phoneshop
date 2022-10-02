package project.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.phoneshop.model.entity.ProductRatingImageEntity;

public interface ProductRatingImageRepository extends JpaRepository<ProductRatingImageEntity,Integer> {
}
