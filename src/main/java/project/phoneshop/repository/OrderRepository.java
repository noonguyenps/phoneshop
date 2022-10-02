package project.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.phoneshop.model.entity.OrderEntity;
import project.phoneshop.model.entity.UserEntity;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {
    Optional<OrderEntity> findByOrderId(int id);
    @Query(value = "SELECT * FROM products WHERE LOWER(products.product_name) LIKE %?1%",
            countQuery = "SELECT * FROM products WHERE LOWER(products.product_name) LIKE %?1%",
            nativeQuery = true)
    OrderEntity getOderByUser(UserEntity user);
    @Query(value = "SELECT SUM(orders.total) FROM orders",
            countQuery = "SELECT SUM(orders.total) FROM orders",
            nativeQuery = true)
    int countPrice();
}
