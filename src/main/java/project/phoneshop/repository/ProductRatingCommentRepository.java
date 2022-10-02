package project.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.phoneshop.model.entity.ProductRatingCommentEntity;

public interface ProductRatingCommentRepository extends JpaRepository<ProductRatingCommentEntity,Integer> {
}
