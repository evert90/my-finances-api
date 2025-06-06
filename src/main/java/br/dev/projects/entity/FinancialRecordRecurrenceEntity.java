package br.dev.projects.entity;

import br.dev.projects.bean.financialrecord.FinancialRecordType;
import br.dev.projects.bean.recurrence.RecurrencePeriod;
import lombok.*;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static jakarta.persistence.GenerationType.TABLE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "financial_record_recurrence")
@Entity
@Audited
public class FinancialRecordRecurrenceEntity {

    @Id
    @GeneratedValue(strategy = TABLE, generator = "financialRecordRecurrenceGenerator")
    @TableGenerator(name = "financialRecordRecurrenceGenerator", table = "hibernate_sequences")
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
    @JoinTable(name = "financial_record_tags_recurrence",
            joinColumns = {@JoinColumn(name = "id_financial_record_recurrence")},
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
    @Enumerated(EnumType.STRING)
    private RecurrencePeriod period;

    @Column
    private Integer periodQuantity;

    @Column
    private Boolean emptyValue;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

}
