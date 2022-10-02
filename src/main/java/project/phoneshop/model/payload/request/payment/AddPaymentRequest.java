package project.phoneshop.model.payload.request.payment;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddPaymentRequest {
    @NotBlank(message = "Empty Payment")
    private String payment;
}
