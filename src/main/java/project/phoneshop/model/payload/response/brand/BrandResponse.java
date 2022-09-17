package project.phoneshop.model.payload.response.brand;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@NoArgsConstructor
@Setter
@Getter
public class BrandResponse {
    private UUID id;
    private String name;
    private String description;
    private String brandCountry;
    private String phone;
    private String brandCommune;
    private String brandDistrict;
    private String brandProvince;
    private String addressDetails;
    private Integer yearCreate;
    private UUID parentId;
}
