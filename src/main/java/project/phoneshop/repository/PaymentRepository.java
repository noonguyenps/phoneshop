package project.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import project.phoneshop.model.entity.PaymentEntity;

import java.util.Optional;

@EnableJpaRepositories
public interface PaymentRepository extends JpaRepository<PaymentEntity,Integer> {
    Optional<PaymentEntity> findByPaymentId(int payment_id);
}
