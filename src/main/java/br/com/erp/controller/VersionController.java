package br.com.erp.controller;


import br.com.erp.bean.Version;
import br.com.erp.service.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/version")
public class VersionController {

    private final VersionService versionService;

    @GetMapping
    @Operation(summary = "Detalhes da versão atual")
    public Version getVersion() {
        return versionService.getVersion();
    }

    @GetMapping("/{gitVersion}")
    @Operation(summary = "Verifica se a versão enviada por parâmetro é a versão atual")
    public Version getVersionByGitVersion(@PathVariable(value = "gitVersion") String gitVersion) {
        return versionService.getVersionByGitVersion(gitVersion);
    }

}
