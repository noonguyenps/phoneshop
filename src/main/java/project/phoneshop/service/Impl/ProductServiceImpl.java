package project.phoneshop.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.phoneshop.model.entity.*;
import project.phoneshop.repository.AttributeOptionRepository;
import project.phoneshop.repository.ImageProductRepository;
import project.phoneshop.repository.ProductRepository;
import project.phoneshop.service.ProductService;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final AttributeOptionRepository attributeOptionRepository;
    private final ImageProductRepository imageProductRepository;
    @Override
    public List<ProductEntity> findAllProduct(){
        List<ProductEntity> productEntityList = productRepository.findAll();
        return productEntityList;
    }
    @Override
    public ProductEntity saveProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public ProductEntity findById(UUID id){
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        if(productEntity.isEmpty()){
            return null;
        }
        return productEntity.get();
    }
    @Override
    public void deleteProduct(UUID id){
        productRepository.deleteById(id);
    }
    @Override
    public void saveListImageProduct(List<String> listUrl, ProductEntity product){
        for (String url : listUrl){
            ImageProductEntity imageProductEntity = new ImageProductEntity();
            imageProductEntity.setUrl(url);
            imageProductEntity.setProduct(product);
            imageProductRepository.save(imageProductEntity);
        }
    }
    @Override
    public void deleteListImgProduct(ProductEntity product){
        List<ImageProductEntity> imageProductEntityList = imageProductRepository.findByProduct(product);
        for (ImageProductEntity imageProductEntity : imageProductEntityList){
            imageProductRepository.delete(imageProductEntity);
        }
    }

    @Override
    public void addAttribute(ProductEntity product, String attributeOptionId){
        Optional<AttributeOptionEntity> attribute = attributeOptionRepository.findById(attributeOptionId);
        product.getAttributeOptionEntities().add(attribute.get());
        productRepository.save(product);
    }
    @Override
    public void deleteAttribute(ProductEntity product, String attributeId){
        Optional<AttributeOptionEntity> attribute = attributeOptionRepository.findById(attributeId);
        product.getAttributeOptionEntities().remove(attribute.get());
        productRepository.save(product);
    }

    @Override
    public List<ProductEntity> findPaginated(int pageNo, int pageSize, String sort) {
        Pageable paging = null;
        switch (sort){
            case "product_price_up" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").descending());break;
            case "product_price_down" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").ascending());break;
            default : paging = PageRequest.of(pageNo, pageSize, Sort.by(sort).descending());
        }
        Page<ProductEntity> pagedResult = productRepository.findAllProduct(paging);
        return pagedResult.toList();
    }
    @Override
    public List<ProductEntity> findProductByCategory(CategoryEntity category, int pageNo, int pageSize, String sort){
        Pageable paging = null;
        switch (sort){
            case "product_price_up" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").descending());break;
            case "product_price_down" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").ascending());break;
            default : paging = PageRequest.of(pageNo, pageSize, Sort.by(sort).descending());
        }
        List<UUID> listCategory = new ArrayList<>();
        getAllCategory(category,listCategory);
        Page<ProductEntity> pagedResult = productRepository.findByCategory(listCategory,paging);
        return pagedResult.toList();
    }
    public void getAllCategory(CategoryEntity category, List<UUID> categoryEntities){
        if(category.getCategoryEntities() != null){
            categoryEntities.add(category.getId());
            for (CategoryEntity categoryEntity : category.getCategoryEntities())
                getAllCategory(categoryEntity,categoryEntities);
        }else
            categoryEntities.add(category.getId());
    }
    @Override
    public List<ProductEntity> findProductByBrand(BrandEntity brand, int pageNo, int pageSize, String sort){
        Pageable paging = null;
        switch (sort){
            case "product_price_up" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").descending());break;
            case "product_price_down" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").ascending());break;
            default : paging = PageRequest.of(pageNo, pageSize, Sort.by(sort).descending());
        }
        Page<ProductEntity> pagedResult = productRepository.findByBrand(brand.getId(),paging);
        return pagedResult.toList();
    }
    @Override
    public List<ProductEntity> findProductByAttributes(CategoryEntity category, List<String> listAttribute,int pageNo, int pageSize, String sort){
        Pageable paging = null;
        switch (sort){
            case "product_price_up" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").descending());break;
            case "product_price_down" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").ascending());break;
            default : paging = PageRequest.of(pageNo, pageSize, Sort.by(sort).descending());
        }
        List<UUID> listCategory = new ArrayList<>();
        getAllCategory(category,listCategory);
        Page<ProductEntity> pageResult = productRepository.findByAttributes(listCategory,listAttribute,paging);
        return pageResult.toList();
    }
    @Override
    public List<ProductEntity> findProductByKeyword(String keyword,int pageNo, int pageSize, String sort){
        Pageable paging = null;
        switch (sort){
            case "product_price_up" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").descending());break;
            case "product_price_down" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").ascending());break;
            default : paging = PageRequest.of(pageNo, pageSize, Sort.by(sort).descending());
        }
        Page<ProductEntity> pageResult = productRepository.findByKeyword(keyword.toLowerCase(),paging);
        return pageResult.toList();
    }
    @Override
    public List<ProductEntity> searchByBrand(BrandEntity brand){
        return brand.getListProduct();
    }
    @Override
    public void minusProduct(ProductEntity product, int quantity){
        product.setInventory(product.getInventory()-quantity);
        product.setSellAmount(product.getSellAmount() + quantity);
        productRepository.save(product);
    }
    @Override
    public long countProduct() {
        return productRepository.count();
    }

}
