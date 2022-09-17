package project.phoneshop.model.payload.request.product;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
public class AddAttributeRequest {
    @NotEmpty(message = "Tên Attribute Không được bỏ trống")
    String name;
    @NotNull(message = "Giá trị không được để trống")
    int value;
    @NotEmpty(message = "Id category không được để trống")
    UUID categoryId;
    String suffix;
}
