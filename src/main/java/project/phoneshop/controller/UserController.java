package project.phoneshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.phoneshop.handler.AuthorizationHeader;
import project.phoneshop.mapping.UserMapping;
import project.phoneshop.model.entity.UserEntity;
import project.phoneshop.model.payload.request.user.AddNewUserRequest;
import project.phoneshop.model.payload.request.user.ChangePasswordRequest;
import project.phoneshop.model.payload.response.SuccessResponse;
import project.phoneshop.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Autowired
    AuthorizationHeader authorizationHeader;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> registerAccount(@RequestBody @Valid AddNewUserRequest request) {
        UserEntity user= UserMapping.registerToEntity(request);
        if(userService.existsByPhone(user.getPhone()))
            return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.CONFLICT.value(),"Register Failure! Phone number is exist",null), HttpStatus.CONFLICT);;
        try{
            userService.saveUser(user,"USER");
            return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(),"Register Successfully",null), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.NOT_ACCEPTABLE.value(),"Register Failure",null), HttpStatus.NOT_ACCEPTABLE);
    }
    @GetMapping("/profile")
    public ResponseEntity<SuccessResponse> getUserProfile(HttpServletRequest request) throws Exception{
        UserEntity user = authorizationHeader.AuthorizationHeader(request);
        if(user != null){
            Map<String,Object> data = new HashMap<>();
            data.put("user",user);
            return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(),"Profile User",data), HttpStatus.NOT_ACCEPTABLE);
        }
        else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @PutMapping("/profile/changePassword")
    public ResponseEntity<SuccessResponse> changePassword(HttpServletRequest request, @RequestBody @Valid ChangePasswordRequest changePasswordRequest) throws Exception{
        UserEntity user = authorizationHeader.AuthorizationHeader(request);
        if(user==null)
            throw new BadCredentialsException("User not found");
        else{
            if(!passwordEncoder.matches(changePasswordRequest.getOldPassword(),user.getPassword()))
                return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.NOT_ACCEPTABLE.value(),"Old password is incorrect",null), HttpStatus.NOT_ACCEPTABLE);
            if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword()))
                return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.NOT_ACCEPTABLE.value(),"Confirm password is incorrect",null), HttpStatus.NOT_ACCEPTABLE);
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userService.saveInfo(user);
            return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(),"Change password successfully",null), HttpStatus.OK);
        }
    }
}
