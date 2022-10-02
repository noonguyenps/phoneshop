package project.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.phoneshop.model.entity.ProductEntity;
import project.phoneshop.model.entity.ProductRatingEntity;
import project.phoneshop.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRatingRepository extends JpaRepository<ProductRatingEntity,Integer> {
    List<ProductRatingEntity> getAllByProduct(ProductEntity product);
    List<ProductRatingEntity> getAllByUser(UserEntity user);
    Optional<ProductRatingEntity> getByUserAndProduct(UserEntity user, ProductEntity product);
}
