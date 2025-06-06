package br.dev.projects.controller;

import br.dev.projects.bean.tag.TagTotal;
import br.dev.projects.bean.financialrecord.FinancialRecord;
import br.dev.projects.bean.financialrecord.FinancialRecordReadonly;
import br.dev.projects.bean.financialrecord.FinancialRecordTotal;
import br.dev.projects.service.FinancialRecordService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/financial-records")
public class FinancialRecordController {

    private final FinancialRecordService service;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/")
    FinancialRecordReadonly save(@RequestBody FinancialRecord financialRecord) {
        return service.save(financialRecord);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/search")
    Set<FinancialRecordReadonly> getByPeriod(@RequestParam(value = "start") @DateTimeFormat(iso = DATE) LocalDate start,
                                   @RequestParam(value = "end") @DateTimeFormat(iso = DATE) LocalDate end) {
        return service.getByPeriod(start, end);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/report/total")
    Set<FinancialRecordTotal> getTotal() {
        return service.getTotal();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/report/total/period")
    Set<FinancialRecordTotal> getTotalByPeriod(
            @RequestParam(value = "start") @DateTimeFormat(iso = DATE) LocalDate start,
            @RequestParam(value = "end") @DateTimeFormat(iso = DATE) LocalDate end) {
        return service.getTotalByPeriod(start, end);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/report/total/period/tag")
    Set<TagTotal> getTotalByPeriodAndTags(
            @RequestParam(value = "start") @DateTimeFormat(iso = DATE) LocalDate start,
            @RequestParam(value = "end") @DateTimeFormat(iso = DATE) LocalDate end,
            @RequestParam(value = "tagIds") Set<Long> tagIds) {
        return service.getTotalByPeriodAndTags(start, end, tagIds);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    List<FinancialRecordReadonly> getAll() {
        return service.getAll();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    void delete(@PathVariable(name = "id") Long id) {
        service.delete(id);
    }

}
