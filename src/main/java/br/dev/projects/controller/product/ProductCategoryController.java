package br.dev.projects.controller.product;

import br.dev.projects.bean.product.ProductCategory;
import br.dev.projects.service.product.ProductCategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/products-category")
public class ProductCategoryController {

    private final ProductCategoryService service;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/")
    ProductCategory save(@RequestBody ProductCategory category) {
        return service.save(category);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    Set<ProductCategory> getAll() {
        return service.getAll();
    }
}
