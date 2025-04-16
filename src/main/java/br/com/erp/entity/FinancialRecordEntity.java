package br.com.erp.entity;

import br.com.erp.bean.tag.TagTotalDTO;
import br.com.erp.bean.financialrecord.FinancialRecordType;
import lombok.*;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.TABLE;

@NamedNativeQuery(name = "FinancialRecordEntity.getTotalReportByPeriodAndTagIds" ,
        query = "SELECT t.id, t.name, SUM(fr.value) as total " +
                "FROM tag as t " +
                "INNER JOIN financial_record_tags as frt ON frt.id_tag = t.id " +
                "INNER JOIN financial_record as fr ON frt.id_financial_record = fr.id " +
                "WHERE fr.user_id = :userId " +
                "AND fr.date BETWEEN :start AND :end " +
                "AND t.id IN :tagIds " +
                "GROUP BY t.id, t.name",
        resultSetMapping = "tagTotalMapping")
@SqlResultSetMapping(name = "tagTotalMapping", classes = @ConstructorResult(
        targetClass = TagTotalDTO.class,
        columns = {
                @ColumnResult(name = "id", type = Long.class),
                @ColumnResult(name = "name", type = String.class),
                @ColumnResult(name = "total", type = BigDecimal.class),
        }
))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "financial_record")
@Entity
@Audited
public class FinancialRecordEntity {

    @Id
    @GeneratedValue(strategy = TABLE, generator = "financialRecordGenerator")
    @TableGenerator(name = "financialRecordGenerator", table = "hibernate_sequences")
    private Long id;

    @Column
    private String name;

    @Column
    private String details;

    @Column
    private BigDecimal value;

    @Column
    @Enumerated(EnumType.STRING)
    private FinancialRecordType type;

    @Column
    private LocalDate date;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "financial_record_tags",
            joinColumns = {@JoinColumn(name = "id_financial_record")},
            inverseJoinColumns = {@JoinColumn(name = "id_tag")})
    @Column
    private Set<TagEntity> tags;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column
    private Boolean paid;

    @Column
    private Boolean notification;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

}
