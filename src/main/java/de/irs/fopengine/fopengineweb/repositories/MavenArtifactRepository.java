package de.irs.fopengine.fopengineweb.repositories;

import de.irs.fopengine.fopengineweb.model.MavenArtifact;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface MavenArtifactRepository extends CrudRepository<MavenArtifact, Long> {

    Optional<MavenArtifact> findMavenArtifactByGroupAndArtifactAndVersion(String group, String artifact, String version);
}