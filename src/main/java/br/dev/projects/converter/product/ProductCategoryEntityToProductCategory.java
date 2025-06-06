package br.dev.projects.converter.product;

import br.dev.projects.bean.product.ProductCategory;
import br.dev.projects.entity.product.ProductCategoryEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductCategoryEntityToProductCategory implements Function<ProductCategoryEntity, ProductCategory> {
    @Override
    public ProductCategory apply(ProductCategoryEntity category) {
        return new ProductCategory(
                category.getId(),
                category.getName()
        );
    }
}
