package project.phoneshop.mapping;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.phoneshop.model.entity.UserEntity;
import project.phoneshop.model.payload.request.user.AddNewUserRequest;

public class UserMapping {
    public static UserEntity registerToEntity(AddNewUserRequest registerRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return new UserEntity(registerRequest.getPhone(), registerRequest.getPassword());
    }

}
