package project.phoneshop.model.payload.request.attribute;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class AttributeOptionRequest {
    private String id;
    private String idType;
    private String value;

}
