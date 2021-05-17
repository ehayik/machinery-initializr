package com.gitub.eljaiek.machinery.initializr;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.metadata.DefaultMetadataElement;
import io.spring.initializr.web.support.InitializrMetadataUpdateStrategy;
import io.spring.initializr.web.support.SaganInitializrMetadataUpdateStrategy;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * An {@link InitializrMetadataUpdateStrategy} that performs additional filtering of versions
 * available on spring.io.
 */
class StartInitializrMetadataUpdateStrategy extends SaganInitializrMetadataUpdateStrategy {

    StartInitializrMetadataUpdateStrategy(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper);
    }

    @Override
    protected List<DefaultMetadataElement> fetchSpringBootVersions(String url) {
        return Optional.of(super.fetchSpringBootVersions(url)).stream()
                .flatMap(Collection::stream)
                .filter(this::isCompatibleVersion)
                .collect(toList());
    }

    private boolean isCompatibleVersion(DefaultMetadataElement versionMetadata) {
        var version = Version.parse(versionMetadata.getId());
        var qualifier = version.getQualifier();
        var isReleased = qualifier == null || "RELEASE".equals(qualifier.getId());
        return (version.getMajor() >= 2 && version.getMinor() > 2) && isReleased;
    }
}
