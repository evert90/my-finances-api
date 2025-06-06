package br.dev.projects.converter.asset;

import br.dev.projects.entity.AssetEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssetEntityToAssetReadonlyTest {

    private AssetEntityToAssetReadonly converter;

    @BeforeEach
    void setUp() {
        converter = new AssetEntityToAssetReadonly();
    }

    @Test
    void apply() {
        //given
        final Long ID = 1L;
        final String NAME = "Teste";
        var entity = AssetEntity
                .builder()
                .id(ID)
                .name(NAME)
                .build();

        //when
        var result = converter.apply(entity);

        //then
        assertEquals(ID, result.id());
        assertEquals(NAME, result.name());
    }
}