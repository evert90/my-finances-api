package br.com.erp.bean.financialrecord;

import java.math.BigDecimal;

public record FinancialRecordTotal(FinancialRecordType type, BigDecimal total) { }