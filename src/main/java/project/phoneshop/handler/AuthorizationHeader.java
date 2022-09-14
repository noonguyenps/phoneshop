package project.phoneshop.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import project.phoneshop.model.entity.UserEntity;
import project.phoneshop.security.JWT.JwtUtils;
import project.phoneshop.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
@Component
public class AuthorizationHeader {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    public UserEntity AuthorizationHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken))
                throw new BadCredentialsException("access token is  expired");
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user.getId().toString().length()<1)
                throw new BadCredentialsException("User not found");
            else
                return user;
        }
        else
            return null;
    }
}
