package de.irs.fopengine.fopengineweb.services;

import de.irs.fopengine.fopengineweb.model.ProjectFileMapper;

import java.util.Set;

public interface GeneratingService {

    /**
     * Method generates a metrics file using embed font
     * @param embedFont
     * @return
     */
    ProjectFileMapper generateMetricsFile(Set<ProjectFileMapper> embedFont);
}
