package br.dev.projects.entity;

import br.dev.projects.bean.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.TABLE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Entity
@Audited
public class UserEntity {

    public UserEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = TABLE, generator = "userGenerator")
    @TableGenerator(name = "userGenerator", table = "hibernate_sequences")
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.ROLE_USER;

//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<ProductCategoryEntity> productCategories;
//
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<ProductEntity> products;

//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<FinancialRecordEntity> financialRecords;

//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<TagEntity> tags;

}
