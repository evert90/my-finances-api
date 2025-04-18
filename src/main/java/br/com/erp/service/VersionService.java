package br.com.erp.service;


import br.com.erp.bean.Version;
import br.com.erp.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.GitProperties;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionService {

    public static final String NOT_FOUND_MESSAGE = "Versão não encontrada";

    private final GitProperties gitProperties;

    public Version getVersion() {
        return Version
                .builder()
                .gitVersion(gitProperties.getCommitId())
                .dateTime(gitProperties.getCommitTime())
                .build();
    }

    public Version getVersionByGitVersion(String version) {

        if(gitProperties.getCommitId().equals(version)) {
            return Version
                    .builder()
                    .gitVersion(gitProperties.getCommitId())
                    .dateTime(gitProperties.getCommitTime())
                    .build();
        }

        throw new NotFoundException(NOT_FOUND_MESSAGE);
    }
}

