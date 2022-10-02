package project.phoneshop.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.phoneshop.handler.AuthorizationHeader;
import project.phoneshop.model.entity.DiscountProgramEntity;
import project.phoneshop.model.entity.UserEntity;
import project.phoneshop.model.payload.request.discountProgram.DiscountProgramRequest;
import project.phoneshop.model.payload.response.SuccessResponse;
import project.phoneshop.service.DiscountProgramService;
import project.phoneshop.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DiscountProgramController {
    private final UserService userService;
    @Autowired
    private final DiscountProgramService discountProgramService;
    @Autowired
    AuthorizationHeader authorizationHeader;
    @PostMapping("/admin/discountProgram/insert")
    public ResponseEntity<SuccessResponse> addDiscountProgram(HttpServletRequest request, @RequestBody @Valid DiscountProgramRequest discountProgramRequest) {
        UserEntity user = authorizationHeader.AuthorizationHeader(request);
        if(user != null){
            if (discountProgramService.existsByName(discountProgramRequest.getName())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            DiscountProgramEntity discountProgram = new DiscountProgramEntity(discountProgramRequest.getName(), discountProgramRequest.getPercent(), discountProgramRequest.getFromDate(), discountProgramRequest.getToDate(), discountProgramRequest.getDescription());
            SuccessResponse response = new SuccessResponse();
            try {
                discountProgramService.saveDiscountProgram(discountProgram);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("successfully add seller");
                response.setSuccess(true);
                response.getData().put("name", discountProgram.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/admin/discountProgram/all")
    public ResponseEntity<SuccessResponse> getAllDiscountProgram(HttpServletRequest request) {
        UserEntity user = authorizationHeader.AuthorizationHeader(request);
        if(user != null){
            SuccessResponse response = new SuccessResponse();
            try {
                List<DiscountProgramEntity> discountProgramList = discountProgramService.getAllDiscountProgram();
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("successfully get all programs");
                response.setSuccess(true);
                response.getData().put("discount_programs", discountProgramList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getDiscountProgramByName(@PathVariable Long id) {
        DiscountProgramEntity discountProgram = discountProgramService.findByDiscountId(id);
        SuccessResponse response = new SuccessResponse();
        if (discountProgram == null) {
            response.setMessage("Program not found");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.setMessage("Get user success");
            response.setSuccess(true);
            response.setStatus(HttpStatus.OK.value());
            response.getData().put("discount_program", discountProgram);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }
    //update
    @PutMapping("admin/discountProgram/{id}")
    ResponseEntity<SuccessResponse> updateDiscountProgram(HttpServletRequest request,@RequestBody DiscountProgramEntity newDiscountProgram, @PathVariable Long id) {
        UserEntity user = authorizationHeader.AuthorizationHeader(request);
        if(user != null){
            DiscountProgramEntity updateDiscountProgram = discountProgramService.findByDiscountId(id);
            SuccessResponse response = new SuccessResponse();
            if (updateDiscountProgram == null) {
                response.setMessage("Program not found");
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setSuccess(false);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

            } else {
                try {
                    updateDiscountProgram.setName(newDiscountProgram.getName());
                    updateDiscountProgram.setPercent(newDiscountProgram.getPercent());
                    updateDiscountProgram.setFromDate(newDiscountProgram.getFromDate());
                    updateDiscountProgram.setToDate(newDiscountProgram.getToDate());
                    updateDiscountProgram.setDescription(newDiscountProgram.getDescription());
                    discountProgramService.saveDiscountProgram(updateDiscountProgram);
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("successfully update program");
                    response.setSuccess(true);
                    response.getData().put("discount_programs", discountProgramService.findByName(newDiscountProgram.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //DELETE
    @DeleteMapping("admin/discountProgram/{id}")
    ResponseEntity<SuccessResponse> deleteProduct(HttpServletRequest request,@PathVariable Long id) {
        UserEntity user = authorizationHeader.AuthorizationHeader(request);
        if(user != null){
            DiscountProgramEntity deleteDiscountProgram = discountProgramService.findByDiscountId(id);
            SuccessResponse response = new SuccessResponse();
            if (deleteDiscountProgram == null) {
                response.setMessage("Program not found");
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setSuccess(false);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                try {
                    discountProgramService.delete(deleteDiscountProgram.getId());
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("successfully delete program");
                    response.setSuccess(true);
                    response.getData().put("discount_program", deleteDiscountProgram);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}