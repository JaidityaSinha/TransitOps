package com.transitops.backend.controller;

import com.transitops.backend.service.AnalyticsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','FINANCIAL_ANALYST','FLEET_MANAGER')")
    @GetMapping("/api/analytics")
    public Map<String, Object> getAnalytics() {
        return analyticsService.getAnalytics();
    }
}