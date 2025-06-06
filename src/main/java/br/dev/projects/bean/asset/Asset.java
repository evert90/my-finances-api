package br.dev.projects.bean.asset;

import br.dev.projects.bean.tag.Tag;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record Asset (
    Long id,
    String name,
    String details,
    BigDecimal initialValue,
    BigDecimal endValue,
    LocalDate initialDate,
    LocalDate endDate,
    LocalDate redemptionDate,
    AssetType type,
    AssetRendaFixaType rendaFixaType,
    AssetRendaFixaRateType rendaFixaRateType,
    String bank,
    BigDecimal rate,
    Boolean liquidez,
    Set<Tag> tags
) { }
