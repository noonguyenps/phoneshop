package project.phoneshop.model.payload.request.user;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhoneLoginRequest {
    @NotEmpty
    String phone;
    @NotEmpty
    String password;
}
