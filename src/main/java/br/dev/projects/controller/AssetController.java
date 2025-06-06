package br.dev.projects.controller;

import br.dev.projects.bean.asset.Asset;
import br.dev.projects.bean.asset.AssetReadonly;
import br.dev.projects.service.AssetService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/assets")
public class AssetController {

    private final AssetService service;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/")
    AssetReadonly save(@RequestBody Asset asset) {
        return service.save(asset);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    Set<AssetReadonly> getAll() {
        return service.getAll();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    void delete(@PathVariable(name = "id") Long id) {
        service.delete(id);
    }
}
