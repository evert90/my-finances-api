package br.dev.projects.controller;

import br.dev.projects.bean.financialrecord.FinancialRecord;
import br.dev.projects.service.FinancialRecordRecurrenceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/financial-record-recurrences")
public class FinancialRecordRecurrenceController {

    private final FinancialRecordRecurrenceService service;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    List<FinancialRecord> getAll() {
        return service.getAll();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    void delete(@PathVariable(name = "id") Long id) {
        service.delete(id);
    }

}
