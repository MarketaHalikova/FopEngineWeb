package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.model.ProjectFileMapper;

import java.util.Set;

public interface GeneratingService {

    ProjectFileMapper generateMetricsFile(Set<ProjectFileMapper> embedFont);
}
