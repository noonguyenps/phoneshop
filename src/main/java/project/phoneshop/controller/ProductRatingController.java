package project.phoneshop.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.phoneshop.handler.AuthorizationHeader;
import project.phoneshop.handler.HttpMessageNotReadableException;
import project.phoneshop.handler.MethodArgumentNotValidException;
import project.phoneshop.mapping.ProductRatingMapping;
import project.phoneshop.model.entity.ProductEntity;
import project.phoneshop.model.entity.ProductRatingEntity;
import project.phoneshop.model.entity.UserEntity;
import project.phoneshop.model.payload.request.productRating.AddNewRatingRequest;
import project.phoneshop.model.payload.response.SuccessResponse;
import project.phoneshop.service.ImageStorageService;
import project.phoneshop.service.ProductRatingService;
import project.phoneshop.service.ProductService;
import project.phoneshop.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/productRating")
public class ProductRatingController {
    final ProductRatingService productRatingService;
    final ImageStorageService imageStorageService;
    final ProductService productService;
    final UserService userService;
    private static final Logger LOGGER = LogManager.getLogger(AddressController.class);
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AuthorizationHeader authorizationHeader;
    @PostMapping(value = "/add",consumes = {"multipart/form-data"})
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public ResponseEntity<SuccessResponse> addNewRating(@Valid AddNewRatingRequest addNewRatingRequest, @RequestPart MultipartFile[] multipartFiles, HttpServletRequest request) throws Exception {
        UserEntity user = authorizationHeader.AuthorizationHeader(request);
        if(user != null){
            ProductEntity product = productService.findById(UUID.fromString(addNewRatingRequest.getProductId()));
            ProductRatingEntity productRating = productRatingService.getByUserAndProduct(user,product);
            if(productRating!=null)
                return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.CONFLICT.value(), "Invalid Input",null),HttpStatus.CONFLICT);
            productRating= ProductRatingMapping.addReqToEntity(addNewRatingRequest,product,user);
            productRating=productRatingService.saveRating(productRating);
            List<String> urls = imageStorageService.saveProductRatingList(multipartFiles, productRating.getId());
            productRatingService.saveListRatingImage(urls,productRating);
            Map<String,Object> data = new HashMap<>();
            data.put("star",productRating.getRatingPoint());
            return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(), "Save Rating Successfully",data), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
//    @GetMapping("")
//    @ResponseBody
//    public ResponseEntity<SuccessResponse> getUserRating(HttpServletRequest httpServletRequest) throws Exception
//    {
//        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
//        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String accessToken = authorizationHeader.substring("Bearer ".length());
//
//            if (jwtUtils.validateExpiredToken(accessToken) == true) {
//                throw new BadCredentialsException("access token is  expired");
//            }
//            try {
//                UserEntity user = userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
//                SuccessResponse response = new SuccessResponse();
//                if (user == null)
//                {
//                    response.setStatus(HttpStatus.NOT_FOUND.value());
//                    response.setSuccess(false);
//                    response.setMessage("Can't find user with id provided");
//                    return new ResponseEntity<SuccessResponse>(response,HttpStatus.NOT_FOUND);
//                }
//                List<ProductRatingEntity> list = productRatingService.getAllRatingByUser(user);
//                if(list.isEmpty())
//                {
//                    response.setStatus(HttpStatus.NOT_FOUND.value());
//                    response.setSuccess(false);
//                    response.setMessage("You don't have any rating");
//                    return new ResponseEntity<SuccessResponse>(response,HttpStatus.NOT_FOUND);
//                }
//                response.setStatus(HttpStatus.OK.value());
//                response.setMessage("Get Rating List Success!");
//                response.setSuccess(true);
//                response.getData().put("listRating",list);
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            }
//            catch (Exception e)
//            {
//                throw new Exception(e.getMessage());
//            }
//        }
//        else throw new BadCredentialsException("access token is missing");
//    }
//
//    @GetMapping("/product/{id}")
//    @ResponseBody
//    public ResponseEntity<SuccessResponse> getProductRating(@PathVariable("id") UUID id)
//    {
//        ProductEntity product = productService.findById(id);
//        SuccessResponse response = new SuccessResponse();
//        if (product==null)
//        {
//            response.setStatus(HttpStatus.NOT_FOUND.value());
//            response.setSuccess(false);
//            response.setMessage("Can't find product with id provided:" + id);
//            return new ResponseEntity<SuccessResponse>(response,HttpStatus.NOT_FOUND);
//        }
//        List<ProductRatingEntity> list = productRatingService.getAllRatingByProduct(product);
//        if (list.isEmpty())
//        {
//            response.setStatus(HttpStatus.NOT_FOUND.value());
//            response.setSuccess(false);
//            response.setMessage("Product don't have any rating");
//            return new ResponseEntity<SuccessResponse>(response,HttpStatus.NOT_FOUND);
//        }
//        response.setStatus(HttpStatus.OK.value());
//        response.setSuccess(true);
//        response.setMessage("Get rating list success");
//        response.getData().put("listRating",list);
//        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
//    }
//    @PostMapping("/like/{id}")
//    @ResponseBody
//    public ResponseEntity<SuccessResponse> likeRating(@PathVariable("id") int id, HttpServletRequest httpServletRequest) throws Exception
//    {
//        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
//        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String accessToken = authorizationHeader.substring("Bearer ".length());
//
//            if (jwtUtils.validateExpiredToken(accessToken) == true) {
//                throw new BadCredentialsException("access token is  expired");
//            }
//            UserEntity user = userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
//            SuccessResponse response = new SuccessResponse();
//            if (user == null)
//            {
//                response.setStatus(HttpStatus.NOT_FOUND.value());
//                response.setSuccess(false);
//                response.setMessage("Can't find user with id provided");
//                return new ResponseEntity<SuccessResponse>(response,HttpStatus.NOT_FOUND);
//            }
//            ProductRatingEntity ratingEntity = productRatingService.getRatingById(id);
//            if (ratingEntity==null)
//            {
//                response.setStatus(HttpStatus.NOT_FOUND.value());
//                response.setSuccess(false);
//                response.setMessage("Invalid Rating id");
//                return new ResponseEntity<SuccessResponse>(response,HttpStatus.NOT_FOUND);
//            }
//            if (ratingEntity.getUser().getId()==user.getId())
//            {
//                response.setStatus(HttpStatus.CONFLICT.value());
//                response.setSuccess(false);
//                response.setMessage("You can't like your own rating");
//                return new ResponseEntity<SuccessResponse>(response,HttpStatus.CONFLICT);
//            }
//            ProductRatingLikeEntity likeEntity = productRatingService.getLikeByRatingAndUser(ratingEntity,user);
//            if (likeEntity==null) {
//                likeEntity=new ProductRatingLikeEntity();
//                likeEntity.setUser(user);
//                likeEntity.setProductRating(ratingEntity);
//                productRatingService.saveLike(likeEntity);
//                response.setStatus(HttpStatus.OK.value());
//                response.setSuccess(true);
//                response.setMessage("Liked success");
//                response.getData().put("likedUser", user.getPhone());
//                return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
//            }
//            else {
//                productRatingService.deleteLike(likeEntity.getId());
//                response.setStatus(HttpStatus.OK.value());
//                response.setSuccess(true);
//                response.setMessage("Unlike success");
//                return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
//            }
//
//        }
//        else
//            throw new BadCredentialsException("access token is missing");
//    }
//    @GetMapping("/likes/{id}")
//    public ResponseEntity<SuccessResponse> getRatingLikes(@PathVariable("id") int id)
//    {
//        ProductRatingEntity ratingEntity = productRatingService.getRatingById(id);
//        SuccessResponse response = new SuccessResponse();
//        if (ratingEntity == null)
//        {
//            response.setStatus(HttpStatus.NOT_FOUND.value());
//            response.setSuccess(false);
//            response.setMessage("Invalid Rating id");
//            return new ResponseEntity<SuccessResponse>(response,HttpStatus.NOT_FOUND);
//        }
//        else {
//            int amount = productRatingService.countRatingLike(ratingEntity);
//            response.setStatus(HttpStatus.OK.value());
//            response.setSuccess(true);
//            response.setMessage("Get rating like amount success");
//            response.getData().put("likeAmount",amount);
//            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
//        }
//    }
//    @PostMapping("/comment")
//    @ResponseBody
//    public ResponseEntity<SuccessResponse> addComment(@RequestBody @Valid AddNewRatingComment addNewRatingComment, HttpServletRequest httpServletRequest,BindingResult errors) throws Exception
//    {
//        if(errors.hasErrors())
//        {
//            throw new MethodArgumentNotValidException(errors);
//        }
//        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
//        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String accessToken = authorizationHeader.substring("Bearer ".length());
//
//            if (jwtUtils.validateExpiredToken(accessToken) == true) {
//                throw new BadCredentialsException("access token is  expired");
//            }
//            UserEntity user = userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
//            SuccessResponse response = new SuccessResponse();
//            if (user == null)
//            {
//                response.setStatus(HttpStatus.NOT_FOUND.value());
//                response.setSuccess(false);
//                response.setMessage("Can't find user with id provided");
//                return new ResponseEntity<SuccessResponse>(response,HttpStatus.NOT_FOUND);
//            }
//            ProductRatingEntity ratingEntity = productRatingService.getRatingById(addNewRatingComment.getRatingId());
//            if (ratingEntity==null)
//            {
//                response.setStatus(HttpStatus.NOT_FOUND.value());
//                response.setSuccess(false);
//                response.setMessage("Invalid rating id");
//                return new ResponseEntity<SuccessResponse>(response,HttpStatus.NOT_FOUND);
//            }
//            ProductRatingCommentEntity commentEntity = new ProductRatingCommentEntity(user,ratingEntity,addNewRatingComment.getComment());
//            productRatingService.saveComment(commentEntity);
//            response.setStatus(HttpStatus.OK.value());
//            response.setSuccess(true);
//            response.setMessage("Add comment success");
//            response.getData().put("commentUser", user.getPhone());
//            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
//
//        }
//        else
//            throw new BadCredentialsException("access token is missing");
//    }
}
