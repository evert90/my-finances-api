package br.dev.projects.converter.product;

import br.dev.projects.bean.product.ProductCategory;
import br.dev.projects.entity.product.ProductCategoryEntity;
import br.dev.projects.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class ProductCategoryToProductCategoryEntity implements Function<ProductCategory, ProductCategoryEntity> {

    private final UserService userService;

    @Override
    public ProductCategoryEntity apply(ProductCategory productCategory) {
        return new ProductCategoryEntity(
                productCategory.id(),
                productCategory.name(),
                userService.getCurrentUser());
    }
}
