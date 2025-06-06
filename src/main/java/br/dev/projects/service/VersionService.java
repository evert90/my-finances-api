package br.dev.projects.service;


import br.dev.projects.bean.Version;
import br.dev.projects.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class VersionService {

    public static final String NOT_FOUND_MESSAGE = "Versão não encontrada";

    @Value("${SOURCE_COMMIT:0.0.0}")
    private String version;

    private final BuildProperties buildProperties;

    public Version getVersion() {
        return Version
                .builder()
                .gitVersion(version)
                .dateTime(buildProperties.getTime())
                .build();
    }

    public Version getVersionByGitVersion(@PathVariable(value = "gitVersion") String gitVersion) {

        if(version.equals(gitVersion)) {
            return Version
                    .builder()
                    .gitVersion(version)
                    .dateTime(buildProperties.getTime())
                    .build();
        }

        throw new NotFoundException(NOT_FOUND_MESSAGE);
    }
}

