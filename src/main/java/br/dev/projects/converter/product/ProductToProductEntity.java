package br.dev.projects.converter.product;

import br.dev.projects.bean.product.Product;
import br.dev.projects.entity.product.ProductEntity;
import br.dev.projects.repository.product.ProductCategoryRepository;
import br.dev.projects.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class ProductToProductEntity implements Function<Product, ProductEntity> {

    private final ProductCategoryRepository categoryRepository;

    private final UserService userService;

    @Override
    public ProductEntity apply(Product product) {
        return new ProductEntity(
                product.id(),
                product.name(),
                product.categoriesIds()
                        .stream()
                        .map(it -> categoryRepository.findById(it)
                                .orElseThrow(() -> new RuntimeException("Categoria " + it + " não encontrada")))
                        .collect(toSet()),
                product.details(),
                userService.getCurrentUser()
        );

    }
}
