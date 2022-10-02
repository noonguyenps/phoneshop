package project.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.phoneshop.model.entity.ProductRatingEntity;
import project.phoneshop.model.entity.ProductRatingLikeEntity;
import project.phoneshop.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRatingLikeRepository extends JpaRepository<ProductRatingLikeEntity,Integer> {
    List<ProductRatingLikeEntity> findAllByProductRating(ProductRatingEntity productRating);
    Optional<ProductRatingLikeEntity> findByProductRatingAndUser(ProductRatingEntity product, UserEntity user);
}
