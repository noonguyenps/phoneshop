package project.phoneshop.model.payload.request.cart;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@NoArgsConstructor
@Setter
@Getter
public class UpdateCartRequest {
    private int quantity;
    private Boolean status;
}
