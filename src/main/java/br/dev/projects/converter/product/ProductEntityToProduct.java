package br.dev.projects.converter.product;

import br.dev.projects.bean.product.Product;
import br.dev.projects.entity.product.ProductCategoryEntity;
import br.dev.projects.entity.product.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

@Service
public class ProductEntityToProduct implements Function<ProductEntity, Product> {
    @Override
    public Product apply(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getCategories().stream().map(ProductCategoryEntity::getId).collect(toSet()),
                productEntity.getDetails()
        );
    }
}
