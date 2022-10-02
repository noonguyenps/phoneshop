package project.phoneshop.model.payload.request.order;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddOrderRequest {
    @NotBlank(message = "ID Address Not Null")
    private UUID idAddress;
    @NotBlank(message = "ID Payment Not Null")
    private int idPayment;
    @NotBlank(message = "ID Ship Not Null")
    private int idShip;
    private List<UUID> cartItem;
    private List<UUID> voucher;
}
