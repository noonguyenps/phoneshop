package project.phoneshop.model.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangeInfoRequest {
    private String fullName;
    private String nickName;
    private String birthDay;
    private String gender;
    private String country;
}
