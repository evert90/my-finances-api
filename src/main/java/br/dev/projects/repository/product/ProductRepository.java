package br.dev.projects.repository.product;

import br.dev.projects.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
