package com.transitops.backend.controller;

import com.transitops.backend.service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','FLEET_MANAGER','DISPATCHER','SAFETY_OFFICER','FINANCIAL_ANALYST')")
    @GetMapping("/api/dashboard")
    public Map<String, Object> getDashboard() {
        return dashboardService.getDashboardSummary();
    }
}