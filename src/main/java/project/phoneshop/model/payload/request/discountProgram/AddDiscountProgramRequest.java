package project.phoneshop.model.payload.request.discountProgram;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddDiscountProgramRequest {
    @NotEmpty(message = "Nhập brand Id")
    private UUID[] brandId;
    @NotEmpty(message = "Nhập Id")
    private Long discountId;
}
