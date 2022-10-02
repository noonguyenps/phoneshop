package project.phoneshop.mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.phoneshop.model.entity.PaymentEntity;
import project.phoneshop.model.payload.request.payment.AddPaymentRequest;
import project.phoneshop.service.PaymentService;

@Component
public class PaymentMapping {

    @Autowired
    PaymentService paymentService;

    public PaymentEntity modelToEntity(AddPaymentRequest addPaymentRequest){
        PaymentEntity newPayment = new PaymentEntity();
        newPayment.setPaymentName(addPaymentRequest.getPayment());
        return newPayment;
    }

    public PaymentEntity updateToEntity(AddPaymentRequest addPaymentRequest,int id){
        PaymentEntity payment = paymentService.getPaymentById(id);
        payment.setPaymentName(addPaymentRequest.getPayment());
        return payment;
    }
}
