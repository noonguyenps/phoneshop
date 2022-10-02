package project.phoneshop.mapping;

import project.phoneshop.model.entity.CartEntity;
import project.phoneshop.model.entity.ProductEntity;
import project.phoneshop.model.entity.UserEntity;
import project.phoneshop.model.payload.request.cart.AddNewCartRequest;

public class CartMapping {
    public static CartEntity getCartByRequest(AddNewCartRequest addNewCartRequest, UserEntity user, ProductEntity product){
        CartEntity cartEntity = new CartEntity();
        cartEntity.setProductCart(product);
        cartEntity.setQuantity(addNewCartRequest.getQuantity());
        cartEntity.setStatus(false);
        cartEntity.setActive(true);
        cartEntity.setUserCart(user);
        return cartEntity;
    }
}
