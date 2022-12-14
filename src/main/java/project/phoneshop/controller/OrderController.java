package project.phoneshop.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.phoneshop.handler.AuthorizationHeader;
import project.phoneshop.mapping.OrderMapping;
import project.phoneshop.model.entity.*;
import project.phoneshop.model.payload.request.order.AddOrderRequest;
import project.phoneshop.model.payload.response.SuccessResponse;
import project.phoneshop.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    public static final String SUCCESS_URL = "/api/order/pay/success";
    public static final String CANCEL_URL = "/api/order/pay/cancel";

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;
    private final ShipService shipService;
    private final PaymentService paymentService;
    private final AddressService addressService;
    private final VoucherService voucherService;
//    private final PaypalService paypalService;

    private final DiscountProgramService discountProgramService;

    @Autowired
    AuthorizationHeader authorizationHeader;

    @Autowired
    OrderMapping orderMapping;

    private static final Logger LOGGER = LogManager.getLogger(AddressController.class);

    @PostMapping("")
//    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<SuccessResponse> saveOrder(@RequestBody AddOrderRequest addOrderRequest, BindingResult errors, HttpServletRequest request) throws Exception {
        UserEntity user = authorizationHeader.AuthorizationHeader(request);
        if(user != null){
            try {
                ShipEntity ship = shipService.findShipById(addOrderRequest.getIdShip());
                if (ship == null)
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NO_CONTENT.value(), "Ship not found", null), HttpStatus.NO_CONTENT);
                PaymentEntity payment = paymentService.getPaymentById(addOrderRequest.getIdPayment());
                if (payment == null)
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NO_CONTENT.value(), "Payment not found", null), HttpStatus.NO_CONTENT);
                AddressEntity address = addressService.findById(addOrderRequest.getIdAddress());
                if (address == null || address.getUser() != user)
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NO_CONTENT.value(), "Address not found", null), HttpStatus.NO_CONTENT);
                if (addOrderRequest.getCartItem().isEmpty())
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NO_CONTENT.value(), "Cart is empty", null), HttpStatus.NO_CONTENT);
                else{
                    List<CartEntity> cartEntities = new ArrayList<>();
                    for(UUID cartId : addOrderRequest.getCartItem()){
                        CartEntity cartEntity = cartService.findByCartId(cartId);
                        if(cartEntity.getUserCart()!=user||!cartEntity.getStatus()||!cartEntity.getActive())
                            return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(), "Cart is not found", null), HttpStatus.NOT_FOUND);
                        if(cartEntity.getProductCart().getInventory()< cartEntity.getQuantity()){
                            return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(), "Product quantity is out of inventory", null), HttpStatus.NOT_FOUND);
                        }
                        else
                            cartEntities.add(cartEntity);
                    }
                    if (!addOrderRequest.getVoucher().isEmpty()) {
                        List<String> listType = new ArrayList<>();
                        List<VoucherEntity> voucherEntities = new ArrayList<>();
                        for (UUID idVoucher : addOrderRequest.getVoucher()) {
                            VoucherEntity voucher = voucherService.findById(idVoucher);
                            if (voucher == null || !voucher.getUserEntities().contains(user))
                                return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(), "Voucher not found", null), HttpStatus.NOT_FOUND);
                            else {
                                if (listType.contains(voucher.getType()))
                                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.CONFLICT.value(), "Voucher is conflict", null), HttpStatus.CONFLICT);
                                listType.add(voucher.getType());
                                voucherEntities.add(voucher);
                            }
                        }
                        OrderEntity order = orderMapping.ModelToEntity(addOrderRequest, user,cartEntities, address, ship, payment, voucherEntities);
                        orderService.save(order);
                    } else {
                        OrderEntity order = orderMapping.ModelToEntity(addOrderRequest, user,cartEntities, address, ship, payment);
                        orderService.save(order);
                    }
                    return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(),"Order successfully",null),HttpStatus.OK);
                }
            }
            catch (Exception e){
                return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage(), null),HttpStatus.NOT_ACCEPTABLE);
            }
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("")
    public ResponseEntity<SuccessResponse> getOrderByUser(HttpServletRequest request) throws Exception {
        UserEntity user = authorizationHeader.AuthorizationHeader(request);
        if(user != null){
            Map<String,Object> data = new HashMap<>();
            data.put("List Order",user.getListCart());
            return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(),"List Order",data),HttpStatus.OK);
//            try {
//                if (addOrderRequest.getCartItem().isEmpty())
//                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NO_CONTENT.value(), "Cart is empty", null), HttpStatus.NO_CONTENT);
//                ShipEntity ship = shipService.findShipById(addOrderRequest.getIdShip());
//                if (ship == null)
//                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NO_CONTENT.value(), "Ship not found", null), HttpStatus.NO_CONTENT);
//                PaymentEntity payment = paymentService.getPaymentById(addOrderRequest.getIdPayment());
//                if (payment == null)
//                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NO_CONTENT.value(), "Payment not found", null), HttpStatus.NO_CONTENT);
//                AddressEntity address = addressService.findById(addOrderRequest.getIdAddress());
//                if (address == null || address.getUser() != user)
//                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NO_CONTENT.value(), "Address not found", null), HttpStatus.NO_CONTENT);
//                if (!addOrderRequest.getVoucher().isEmpty()) {
//                    List<String> listType = new ArrayList<>();
//                    List<VoucherEntity> voucherEntities = new ArrayList<>();
//                    for (UUID idVoucher : addOrderRequest.getVoucher()) {
//                        VoucherEntity voucher = voucherService.findById(idVoucher);
//                        if (voucher == null || !voucher.getUserEntities().contains(user))
//                            return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(), "Voucher not found", null), HttpStatus.NOT_FOUND);
//                        else {
//                            if (listType.contains(voucher.getType()))
//                                return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.CONFLICT.value(), "Voucher is conflict", null), HttpStatus.CONFLICT);
//                            listType.add(voucher.getType());
//                            voucherEntities.add(voucher);
//                        }
//                    }
//                    OrderEntity order = orderMapping.ModelToEntity(addOrderRequest, user, address, ship, payment, voucherEntities);
//                    orderService.save(order);
//                    return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(),"Order successfully",null),HttpStatus.OK);
//                } else {
//                    OrderEntity order = orderMapping.ModelToEntity(addOrderRequest, user, address, ship, payment);
//                    orderService.save(order);
//                    return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(),"Order successfully",null),HttpStatus.OK);
//                }
//            }
//            catch (Exception e){
//                return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.NOT_ACCEPTABLE.value(), "Product out of inventory",null),HttpStatus.NOT_ACCEPTABLE);
//            }
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
//    @GetMapping("/pay/success/{id}")
//    @ResponseBody
//    public ResponseEntity<SuccessResponse> paypalSuccess(@PathVariable("id") int id,@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId)
//    {
//        try {
//            Payment payment = paypalService.executePayment(paymentId, payerId);
//            System.out.println(payment.toJSON());
//            if (payment.getState().equals("approved")) {
//                SuccessResponse response = new SuccessResponse();
//                response.setSuccess(true);
//                response.setStatus(HttpStatus.OK.value());
//                response.setMessage("Thanh to??n th??nh c??ng");
//                response.getData().put("Order",orderService.findById(id));
//                return new ResponseEntity<>(response,HttpStatus.OK);
//            }
//        } catch (PayPalRESTException e) {
//            System.out.println(e.getMessage());
//        }
//        orderService.delete(id);
//        SuccessResponse response = new SuccessResponse();
//        response.setSuccess(false);
//        response.setStatus(HttpStatus.FAILED_DEPENDENCY.value());
//        response.setMessage("Thanh to??n th???t b???i");
//        return new ResponseEntity<>(response,HttpStatus.FAILED_DEPENDENCY);
//    }
//
//    @GetMapping("/pay/cancel/{id}")
//    public ResponseEntity<SuccessResponse> paypalCancel(@PathVariable("id") int id) {
//        orderService.delete(id);
//        SuccessResponse response = new SuccessResponse();
//        response.setSuccess(false);
//        response.setStatus(HttpStatus.FAILED_DEPENDENCY.value());
//        response.setMessage("Thanh to??n th???t b???i (cancel)");
//        return new ResponseEntity<>(response,HttpStatus.FAILED_DEPENDENCY);
//    }
//    private ResponseEntity SendErrorValid(String field, String message,String title){
//        ErrorResponseMap errorResponseMap = new ErrorResponseMap();
//        Map<String,String> temp =new HashMap<>();
//        errorResponseMap.setMessage(title);
//        temp.put(field,message);
//        errorResponseMap.setStatus(HttpStatus.BAD_REQUEST.value());
//        errorResponseMap.setDetails(temp);
//        return ResponseEntity
//                .badRequest()
//                .body(errorResponseMap);
//    }
//
//    private double PayMoney(CartEntity cart,ShipEntity ship, VoucherEntity voucher,AddOrderRequest request) {
//        double totalOrderProduct = PriceProduct(cart,request);
//
//        double totalOrder = 0.0;
//        double voucherValue=0.0;
//
//
//        Date today = new Date();
//
//        if(voucher==null){
//            voucherValue = 0.0;
//        }
//        else if(voucher.getFromDate().compareTo(today)<0 && voucher.getToDate().compareTo(today)>0){
//            if (voucher.getValue().contains("%")) {
//                double value = Double.parseDouble(voucher.getValue().substring(0,voucher.getValue().length()-1));
//                voucherValue = totalOrderProduct * value / 100;
//            }else {
//                voucherValue = Double.parseDouble(voucher.getValue());
//            }
//        }
//        totalOrder = totalOrderProduct + ship.getShipPrice() - voucherValue;
//        return totalOrder;
//    }
//
//    private void processCartItem(AddOrderRequest request, CartEntity cart, UserEntity user){
//        List<CartItemEntity> listPick = new ArrayList<CartItemEntity>();
//        List<CartItemEntity> listLeft = new ArrayList<CartItemEntity>();
//
//
//        for (int i : request.getCartItem())
//        {
//            CartItemEntity cartItem =cartService.getItemByIdAndCart(i,cart);
//            listPick.add(cartItem);
//            cart.getCartItem().remove(cartItem);
//        }
//
//        listLeft = cart.getCartItem();
//        cart.setCartItem(listPick);
//        cart.setStatus(false);
//        CartEntity newCart = new CartEntity(0.0,true,user);
//        newCart.setCartItem(listLeft);
//        for(CartItemEntity cartItem : listLeft)
//        {
//            cartItem.setCart(newCart);
////            cartService.saveCartItem(cartItem);
//        }
//        cartService.saveCart(newCart);
//    }
//    private String paypalPayment(OrderEntity order)
//    {
//        try {
//            orderService.save(order);
//            Payment paypalPayment = paypalService.createPayment(order, "USD", "paypal",
//                    "sale", "https://nhom3-tiki.herokuapp.com" + CANCEL_URL+"/"+order.getOrderId(),
//                    "https://nhom3-tiki.herokuapp.com" + SUCCESS_URL +"/"+order.getOrderId());
//            for (Links link : paypalPayment.getLinks()) {
//                if (link.getRel().equals("approval_url")) {
//                    return link.getHref();
//                }
//            }
//        }
//        catch (PayPalRESTException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    private double PriceProduct(CartEntity cart,AddOrderRequest request){
//        double totalOrderProduct = 0.0;
//        Date today = new Date();
//
//        for (CartItemEntity item : cart.getCartItem()) {
//            DiscountProgramEntity discountProgram = discountProgramService.findByIdAndProductBrand(request.getDiscountId(), item.getProduct().getProductBrand());
//            if(request.getDiscountId()==null){
//                totalOrderProduct += item.getProduct().getPrice() * item.getQuantity();
//                item.getProduct().setInventory(item.getProduct().getInventory()-item.getQuantity());
//                item.getProduct().setSellAmount(item.getQuantity()+ item.getProduct().getSellAmount());
//            }else if (discountProgram != null && discountProgram.getFromDate().compareTo(today) < 0 && discountProgram.getToDate().compareTo(today) > 0) {
//                double discount = item.getProduct().getPrice() * discountProgram.getPercent() / 100;
//                totalOrderProduct += (item.getProduct().getPrice() - discount) * item.getQuantity();
//                item.getProduct().setInventory(item.getProduct().getInventory() - item.getQuantity());
//                item.getProduct().setSellAmount(item.getQuantity() + item.getProduct().getSellAmount());
//            }
//        }
//        return totalOrderProduct;
//    }
}
