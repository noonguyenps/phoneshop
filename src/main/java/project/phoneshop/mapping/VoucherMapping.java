package project.phoneshop.mapping;

import project.phoneshop.model.entity.VoucherEntity;
import project.phoneshop.model.payload.request.voucher.AddNewVoucherRequest;
import project.phoneshop.model.payload.request.voucher.UpdateVoucherRequest;

public class VoucherMapping {
    public static VoucherEntity updateVoucher(VoucherEntity voucher, UpdateVoucherRequest updateVoucherRequest) {
        voucher.setAmount(updateVoucherRequest.getAmount());
        voucher.setType(updateVoucherRequest.getType());
        voucher.setExpiredDate(updateVoucherRequest.getExpiredDate());
        voucher.setToDate(updateVoucherRequest.getToDate());
        voucher.setFromDate(updateVoucherRequest.getFromDate());
        return voucher;
    }
}
