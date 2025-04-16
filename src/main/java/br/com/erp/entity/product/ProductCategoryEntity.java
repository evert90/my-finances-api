package br.com.erp.entity.product;

import br.com.erp.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.Set;

import static jakarta.persistence.GenerationType.TABLE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_category", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "user_id"})})
@Entity
public class ProductCategoryEntity {

    public ProductCategoryEntity(Long id, String name, UserEntity user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = TABLE, generator = "productCategoryGenerator")
    @TableGenerator(name = "productCategoryGenerator", table = "hibernate_sequences")
    private Long id;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_categories",
            joinColumns = {@JoinColumn(name = "id_category")},
            inverseJoinColumns = {@JoinColumn(name = "id_product")})
    @Column
    private Set<ProductEntity> products;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
