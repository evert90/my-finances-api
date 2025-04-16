package br.com.erp.service;

import br.com.erp.bean.asset.Asset;
import br.com.erp.bean.asset.AssetReadonly;
import br.com.erp.converter.asset.AssetEntityToAssetReadonly;
import br.com.erp.converter.asset.AssetToAssetEntity;
import br.com.erp.exception.NotFoundException;
import br.com.erp.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toCollection;

@RequiredArgsConstructor
@Service
public class AssetService {
    private final AssetRepository repository;

    private final AssetToAssetEntity toEntity;

    private final AssetEntityToAssetReadonly toApi;

    private final UserService userService;

    public AssetReadonly save(Asset asset) {
        return ofNullable(asset)
                .map(toEntity)
                .map(repository::save)
                .map(toApi)
                .orElseThrow(() -> new RuntimeException("Erro ao salvar/retornar o bem"));
    }

    public Set<AssetReadonly> getAll() {
        return repository.findByUserOrderByEndDateAsc(userService.getCurrentUser())
                .stream()
                .map(toApi)
                .collect(toCollection(LinkedHashSet::new));
    }

    @Transactional
    public void delete(Long id) {
        var entity = repository.findByUserAndId(userService.getCurrentUser(), id)
                .orElseThrow(() -> new NotFoundException("Bem não encontrado"));

        entity.getTags().clear();
        repository.delete(entity);
    }
}
