package project.phoneshop.model.payload.request.voucher;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@NoArgsConstructor
@Setter
@Getter
public class UpdateVoucherRequest {
    private int amount;
    private boolean status;
    private String value;
    private String type;
    private Date fromDate;
    private Date toDate;
    private Date expiredDate;
}
