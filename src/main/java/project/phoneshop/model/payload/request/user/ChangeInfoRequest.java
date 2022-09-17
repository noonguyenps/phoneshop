package project.phoneshop.model.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangeInfoRequest {
    private String fullName;
    private String nickName;
    private Date birthDate;
    private String gender;
    private String country;
}
