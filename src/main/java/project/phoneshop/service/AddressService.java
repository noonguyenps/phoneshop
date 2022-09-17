package project.phoneshop.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.phoneshop.model.entity.AddressEntity;
import project.phoneshop.model.entity.AddressTypeEntity;

import java.util.List;

@Component
@Service
public interface AddressService {
    AddressEntity findById(String id);
    List<AddressEntity> getAll();
    AddressEntity saveAddress(AddressEntity address);
    AddressTypeEntity findByTid(int id);
    void deleteAddress(String id);
}
