package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.model.ProjectFileMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GeneratingServiceImpl implements GeneratingService {

    @Override
    public ProjectFileMapper generateMetricsFile(Set<ProjectFileMapper> embedFont) {
        return new ProjectFileMapper("toBeImplemetedMetrics", "fonts", "fonts");
    }
}
