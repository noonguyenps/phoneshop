package project.phoneshop.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.phoneshop.model.entity.BrandEntity;
import project.phoneshop.model.entity.DiscountProgramEntity;

import java.util.List;

@Component
@Service
public interface DiscountProgramService {
    DiscountProgramEntity findByName(String name);

    List<DiscountProgramEntity> getAllDiscountProgram();

    DiscountProgramEntity saveDiscountProgram(DiscountProgramEntity discountProgram);

    Boolean existsByName(String name);

    void delete(Long id);

    DiscountProgramEntity findByDiscountId(Long id);

    DiscountProgramEntity findByIdAndProductBrand(Long id, BrandEntity brand);


}