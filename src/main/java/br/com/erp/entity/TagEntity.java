package br.com.erp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.TABLE;

@Data
@NoArgsConstructor
@Table(name = "tag", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "user_id"})})
@Entity
@Audited
public class TagEntity {

    public TagEntity(Long id, String name, UserEntity user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = TABLE, generator = "tagGenerator")
    @TableGenerator(name = "tagGenerator", table = "hibernate_sequences")
    private Long id;

    @Column
    private String name;

//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @ManyToMany(mappedBy = "tags")
//    @Column
//    private Set<FinancialRecordEntity> financialRecords;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}