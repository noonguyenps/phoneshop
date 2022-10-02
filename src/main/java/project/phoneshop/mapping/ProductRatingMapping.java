package project.phoneshop.mapping;

import project.phoneshop.model.entity.ProductEntity;
import project.phoneshop.model.entity.ProductRatingEntity;
import project.phoneshop.model.entity.UserEntity;
import project.phoneshop.model.payload.request.productRating.AddNewRatingRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ProductRatingMapping {
    public static ProductRatingEntity addReqToEntity(AddNewRatingRequest addNewRatingRequest, ProductEntity product, UserEntity user)
    {
        ProductRatingEntity productRating = new ProductRatingEntity();
        productRating.setProduct(product);
        productRating.setRatingPoint(addNewRatingRequest.getRatingPoint());
        productRating.setMessage(addNewRatingRequest.getMessage());
        productRating.setDate(new Date());
        productRating.setUser(user);
        return productRating;
    }
}

