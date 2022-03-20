package br.com.erp.controller.product;

import br.com.erp.bean.product.Product;
import br.com.erp.service.product.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/products")
public class ProductController {

    private final ProductService service;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/")
    Product save(@RequestBody Product product) {
        return service.save(product);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    Set<Product> getAll() {
        return service.getAll();
    }
}
