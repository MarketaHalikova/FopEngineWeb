package de.irs.fopengine.fopengineweb.services;

import de.irs.fopengine.fopengineweb.model.ProjectFileMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GeneratingServiceImpl implements GeneratingService {

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileMapper generateMetricsFile(Set<ProjectFileMapper> embedFont) {
        // todo use FOP metrics generator
        return new ProjectFileMapper("toBeImplemetedMetrics", "fonts", "fonts");
    }
}
