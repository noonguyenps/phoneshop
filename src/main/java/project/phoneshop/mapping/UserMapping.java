package project.phoneshop.mapping;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.phoneshop.model.entity.UserEntity;
import project.phoneshop.model.payload.request.user.AddNewUserRequest;
import project.phoneshop.model.payload.request.user.ChangeInfoRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserMapping {
    public static UserEntity registerToEntity(AddNewUserRequest registerRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return new UserEntity(registerRequest.getPhone(), registerRequest.getPassword());
    }

    public static UserEntity updateInfoUser(UserEntity user,ChangeInfoRequest changeInfoRequest){
        user.setFullName(changeInfoRequest.getFullName());
        user.setGender(changeInfoRequest.getGender());
        user.setNickName(changeInfoRequest.getNickName());
        user.setBirthDate(changeInfoRequest.getBirthDate());
        user.setCountry(changeInfoRequest.getCountry());
        return user;
    }

}
